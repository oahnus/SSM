package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.Student;
import cn.edu.just.pojo.Task;
import cn.edu.just.service.IStudentService;
import cn.edu.just.service.ITaskService;
import cn.edu.just.util.ApplicationContextConfig;
import cn.edu.just.util.ExcelReader;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/student")
public class StudentController {

    // 定义一个字符串常量数组，存放上传文件的后缀名,用于在下载文件时从服务器中搜索文件
    public static final String[] EXTENSION = {".doc",".docx",".pdf"};

    /**
     * 获取学生列表
     * @return 结果信息
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Map list(@RequestParam(value = "courseId",required = false) Integer courseId){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        List<Student> studentList = new ArrayList<>();

        // 如果传过来的课程id为空,返回所有学生列表
        if(courseId == null) {
            studentList = studentService.getStudentList();
        }
        // courseId 非空，根据课程id筛选出选择了这门课的学生
        else{
            studentList = studentService.getStudentListByCourseId(courseId);
        }
        map.put("status","success");
        map.put("info","获取学生信息成功");
        map.put("data",studentList);

        return map;
    }

    /**
     * 批量添加学生信息
     * @param excelFile excel文件
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Map insert(@RequestBody MultipartFile excelFile, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/temp/");

        // 路径不存在则创建
        File testFile = new File(path);
        if(!testFile.exists()) testFile.mkdirs();

        Map<String,Object> map = new HashMap<>();

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        if(!excelFile.isEmpty()) {
            // 获取文件后缀
            String filename = excelFile.getOriginalFilename();
            // 将Excel文件保存到本地后，进行读取
            FileUtils.copyInputStreamToFile(excelFile.getInputStream(), new File(path,filename));

            File file = new File(path,filename);

            // 导入学生数据，2代表学生角色
            List<Student> list = ExcelReader.readExcelFile(file,2);

            if(list != null) {
                List<Student> insertStudentList = studentService.insertStudent(list);
                map.put("status","success");
                map.put("info","导入学生信息成功");
                map.put("data",insertStudentList);
            }else{
                map.put("status","error");
                map.put("info","Excel解析失败,请检查Excel格式");
            }
            file.delete();
        }else{
            map.put("status","error");
            map.put("info","文件内容为空");
        }
        return map;
    }

    /**
     * 根据id批量删除
     * @param data Bean 用于接收JSON数据中的id对象
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map delete(@RequestBody Data<ID> data){
        Map<String,Object> map = new HashMap<>();
        List<ID> idList = data.getData();
        int[] ids = new int[idList.size()];

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        studentService.deleteStudentBatch(ids);

        map.put("status","success");
        map.put("info","导入学生信息成功");

        return map;
    }

    /**
     * ----------------------------
     * 处理学生任务成果的提交功能
     */

    /**
     * 处理实训结果的上传，上传文件保存在WEB-INF/upload/result/
     * @param request request请求信息
     * @return 返回状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/result/upload",method = RequestMethod.POST)
    public Map uploadResult(HttpServletRequest request) throws IOException {
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        // 获取成果文件的存储路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/result");
        Map<String,Object> map = new HashMap<>();
        // 获取上传的文件
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile resultFile = multipartHttpServletRequest.getFile("resultFile");

        // 判断路径是否存在
        File file = new File(path);
        if(!file.exists()){file.mkdirs();}

        // 获取request中的课程id，根据课程id，学生id将成果文件路径保存在学生选课表中
        String taskId = request.getParameter("taskId");
        String studentId = request.getParameter("studentId");
        String memo = request.getParameter("memo");

        // 如果获取的参数无效，返回错误信息
        if(taskId==null||taskId.equals("")||studentId==null||studentId.equals("")){
            map.put("status","error");
            map.put("info","参数错误");
        }else{
            // 如果文件内容不为空
            if(!resultFile.isEmpty()){
                String filename = resultFile.getOriginalFilename();
                String extension = filename.substring(filename.lastIndexOf('.'));

                // 使用“课程id-学生id”做文件名
                filename = taskId+"-"+studentId+extension;

                // 保存文件到本地
                FileUtils.copyInputStreamToFile(resultFile.getInputStream(),new File(path,filename));

                // 生成url
                String url = new String(request.getRequestURL());
                url = url.substring(0,url.lastIndexOf('/'))+"/download/"+taskId+"/"+studentId;

                // 搜索数据库中是否已存储此任务成果
                map.put("taskId",taskId);
                map.put("studentId",studentId);
                if(memo!=null) {
                    map.put("memo", memo);
                }else{
                    map.put("memo","");
                }
                //如果学生在此次上传前已经上传过文件，并查询到数据库中记录，放弃本次插入操作
                Integer id = studentService.queryByTaskIdAndStudentId(map);

                if(id == null) {
                    map.put("url",url);
                    studentService.insertTaskResult(map);
                }else{
                    map.put("id",id);
                    studentService.updateTaskResult(map);
                }
                // 封装参数到Map
                map.clear();
                map.put("status","success");
                map.put("info","上传成果文件成功");
            }else{
                map.put("status","error");
                map.put("info","成果文件为空");
            }
        }
        return map;
    }

    /**
     * 下载学生的成果文件
     * @param request request
     * @param response response
     * @param courseId 学生选课的课程号
     * @param studentId 学生的学号
     */
    @RequestMapping(value = "/result/download/{courseId}/{studentId}")
    public void downloadResultFile(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String courseId,@PathVariable String studentId){
        //设置下载路径
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/result/");

        String baseFilename = courseId+"-"+studentId;

        // 测试文件是否存在
        String filename = "";
        for(int i=0;i<EXTENSION.length;i++){
            filename = "";
            filename = baseFilename+EXTENSION[i];
            File file = new File(path,filename);
            if(file.exists()){
                break;
            }
        }

        if(!filename.equals("")){
            try {
                response.setContentType("multipart/form-data");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));

                byte[] buffer = new byte[1024];
                BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(new File(path,filename)));
                OutputStream os = response.getOutputStream();

                int i = bis.read(buffer);
                while(i!=-1){
                    os.write(buffer,0,i);
                    os.flush();
                    i = bis.read(buffer);
                }

                os.close();
                bis.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                response.getWriter().write("<html><h2>文件不存在</h2></html>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "/result/list",method = RequestMethod.GET)
    public Map getResultList(@RequestParam("courseId")Integer courseId,
                                            @RequestParam("username")String username){
        Map<String,Object> map = new HashMap<>();

        if(courseId == null || username==null){
            map.put("status","error");
            map.put("info","参数错误");
            return map;
        }

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        List<Task> taskList = studentService.getResultList(username,courseId);
        map.put("status","success");
        map.put("info","获取成功");
        map.put("data",taskList);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/result/delete")
    public Map deleteResult(@RequestBody Data<ID> data){
        Map<String,Object> map = new HashMap<>();
        List<ID> idList = data.getData();
        int[] ids = new int[idList.size()];

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStudentService studentService = (IStudentService) appContext.getBean("studentService");

        studentService.deleteTaskResult(ids);

        map.put("status","success");
        map.put("info","删除成果信息成功");

        return map;
    }

    /**
     *
     * ------------------------------
     */

    // 接收json中的学生id,实现批量删除
    static class ID{
        private int id;

        public ID(){super();}

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
    }


}
