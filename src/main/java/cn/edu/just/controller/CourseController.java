package cn.edu.just.controller;

import cn.edu.just.pojo.Course;
import cn.edu.just.pojo.Data;
import cn.edu.just.service.ICourseService;
import cn.edu.just.util.ApplicationContextConfig;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布课程,查询课程,删除课程,处理上传课程附件,下载课程附件
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/course")
public class CourseController {
    private Logger logger = LoggerFactory.getLogger(CourseController.class);
    // 服务器上允许上传,下载的文件类型
    public static final String[] EXTENSION = {".doc",".docx",".pdf"};
    ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
    ICourseService courseService = (ICourseService) appContext.getBean("courseService");

    /**
     * 查询课程信息,公司获取本公司下的课程,教师获取本人教授的课程,学生获取本专业的课程
     * @return 结果信息,包含课程信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=get"})
    public Map getCourseList(@RequestParam("username") String username,
                                            @RequestParam("actor") int actor,
                                            @RequestParam(value = "courseName",required = false) String courseName,
                                            @RequestParam(value = "teacherName",required = false) String teacherName,
                                            @RequestParam(value = "companyName",required = false) String companyName){
        Map<String,Object> map = new HashMap<>();
        List<Course> courseList = courseService.getCourses(username,actor,courseName,teacherName,companyName);

        map.put("status","success");
        map.put("info","获取课程信息成功");
        map.put("data",courseList);

        return map;
    }

    /**
     * 处理发布课程表单,发布课程信息
     * @param addition 课程附件,附件不能为null
     * @param request 请求信息,从中获取表单中的字段
     * @return 返回json格式的上传信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=post"})
    public Map releaseCourse(@RequestParam(required = false,value = "addition") MultipartFile addition,
                                            HttpServletRequest request) throws Exception {

        // 获取课程附件存储路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/course/");
        // 路径不存在则创建
        File testFile = new File(path);
        if(!testFile.exists()) testFile.mkdirs();

        Map<String,Object> map = new HashMap<>();

        // 获取表单参数
        String name = request.getParameter("name");
        String profession = request.getParameter("profession");
        String teacher = request.getParameter("teacher");
        String company = request.getParameter("company");
        String memo = request.getParameter("memo");

        logger.info(addition.getOriginalFilename());
        logger.info(name);
        logger.info(profession);
        logger.info(company);
        logger.info(teacher);

        // 将课程信息封装在Course对象中
        Course course = new Course();
        course.setName(name);
        course.setProfession(profession);
        course.setTeacher(teacher);
        course.setCompany(company);
        course.setMemo(memo);

//        //保存课程信息到数据库中
        Integer id = courseService.queryCourse(course);
        if(id == null){
            courseService.insertCourse(course);
            id = course.getId();
        }

        // 附件内容不为空,保存文件到本地,并将附件路径保存到数据库
        if(!addition.isEmpty()) {
            // 获取文件后缀
            String filename = addition.getOriginalFilename();
            String extension = filename.substring(filename.lastIndexOf('.'));

            // 将课程在数据库中的id作为文件名，存到本地文件夹下
            filename = id+extension;

            FileUtils.copyInputStreamToFile(addition.getInputStream(), new File(path,filename));

            // 文件保存到本地后,将文件的存储url保存到本地
            String url = new String(request.getRequestURL());
            url = url.substring(0,url.lastIndexOf('/'))+"/download/"+id;

            logger.info(url);

            // 将url保存到数据库
            courseService.updateAdditionUrl(url,id);

            map.put("status","success");
            map.put("info","发布成功");
        }else{
            map.put("info","上传错误");
            map.put("status","error");
        }

        return map;
    }

    /**
     * 下载课程附件
     * @param request http request
     * @param response http response
     * @param filename 附件的文件名,附件在上传后重命名为id,
     */
    @RequestMapping(value = "/download/{filename}",method = RequestMethod.GET)
    public void downloadCourseAdditionFile(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @PathVariable String filename) {
        // 获取文件路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/course/");

        boolean isFindFile = false;

        // 测试文件是否存在
        for(int i=0;i<EXTENSION.length;i++){
            String name = filename+EXTENSION[i];
            File additionFile = new File(path,name);
            if(additionFile.exists()){
                filename = name;
                isFindFile =true;
                break;
            }
        }

        File downloadFile = new File(path, filename);
        if (isFindFile) {
            try {
                response.setContentType("multipart/form-data");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                fis = new FileInputStream(downloadFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }

                os.flush();
                bis.close();
                fis.close();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据id批量删除
     * @param data 用于接收JSON数据中的id数组
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=delete"})
    public Map deleteCourseByIdArray(@RequestBody Data<CourseID> data){
        Map<String,Object> map = new HashMap<>();
        List<CourseID> courseIdList = data.getData();
        int[] ids = new int[courseIdList.size()];

        for(int i = 0; i< courseIdList.size(); i++){
            ids[i] = courseIdList.get(i).getId();
        }

        courseService.deleteCourseBatch(ids);
        map.put("status","success");
        map.put("info","删除课程成功");
        return map;
    }

    /**
     * 接收json中的课程id,实现批量删除
     */
    static class CourseID {
        private int id;

        public CourseID(){super();}

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
    }
}

