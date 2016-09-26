package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.Task;
import cn.edu.just.service.ITaskService;
import cn.edu.just.util.ApplicationContextConfig;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 处理课程任务的增,删,查以及学生任务成果的提交,下载
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/task")
public class TaskController {
    // 定义一个字符串常量数组，存放上传文件的后缀名,用于在下载文件时从服务器中搜索文件
    public static final String[] EXTENSION = {".doc",".docx",".pdf"};


    /**
     * 根据课程id获取该课程下的所有任务信息
     * @param courseId 根据课程id获取任务信息
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> getTaskListByCourseId(@RequestParam("courseId") Integer courseId){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        ITaskService taskService = (ITaskService) appContext.getBean("taskService");

        if(courseId !=null) {
            List<Task> taskList = taskService.getTaskList(courseId);
            map.put("status","success");
            map.put("info","获取任务信息成功");
            map.put("data",taskList);

        }else{
            map.put("status","error");
            map.put("info","缺少参数课程id");
        }
        return map;
    }

    /**
     * 发布任务，一个课程对应多个任务
     * @param content 上传的包含任务内容的文件
     * @param request request请求体
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/release",method = RequestMethod.POST)
    public Map<String,Object> releaseTask(@RequestParam(value = "content",required = false) MultipartFile content, HttpServletRequest request) throws IOException {
        // 获取任务文件的保存路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/task/");
        Map<String,Object> map = new HashMap<>();

        // 路径不存在，则创建路径
        File file = new File(path);
        if(!file.exists()) {file.mkdirs();}

        String name = request.getParameter("name");
        long startTime = Long.parseLong(request.getParameter("startTime"));
        long endTime = Long.parseLong(request.getParameter("endTime"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Task task = new Task();

        // 封装信息
        task.setName(name);
        task.setStartTime(sdf.format(new Date(startTime)));
        task.setEndTime(sdf.format(new Date(endTime)));
        task.setCourseId(courseId);

        // 将任务信息插入到数据库中
        // 保存任务信息到数据库后，获取记录的id，将任务内容的文件名修改为id，再将路径信息更新到数据库中
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        ITaskService taskService = (ITaskService) appContext.getBean("taskService");

        Integer id = taskService.queryByNameAndCourseId(name,courseId);
        if(id == null){
            taskService.insertTask(task);
            id = task.getId();
        }else{
            task.setId(id);
            taskService.updateTask(task);
        }
        if(!content.isEmpty()) {
            // 获取文件后缀
            String filename = content.getOriginalFilename();
            String extension = filename.substring(filename.lastIndexOf('.'));

            // 将课程在数据库中的id作为文件名，存到本地文件夹下
            filename = id + extension;
            FileUtils.copyInputStreamToFile(content.getInputStream(), new File(path, filename));

            // 文件保存到本地后,将文件的存储url保存到本地
            String url = new String(request.getRequestURL());
            url = url.substring(0, url.lastIndexOf('/')) + "/download/" + id;


System.out.println(url);
            task = new Task();
            task.setId(id);
            task.setContent(url);
            taskService.updateTask(task);


            map.put("status", "success");
            map.put("info", "发布成功");
            map.put("data",url);
        } else {
            map.put("status", "error");
            map.put("info", "文件为空");
        }
        return map;
    }

    /**
     * 下载任务内容
     * @param request request请求体
     * @param response response返回体
     * @param filename 文件名 文件名为任务在表中的id值
     */
    @RequestMapping(value = "/download/{filename}",method = RequestMethod.GET)
    public void downloadTaskContent(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
        // 获取文件路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/task/");
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
     * @param data 用于接收JSON数据中的id
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> deleteTaskBatch(@RequestBody Data<ID> data){
        Map<String,Object> map = new HashMap<>();
        List<ID> idList = data.getData();
        int[] ids = new int[idList.size()];

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        ITaskService taskService = (ITaskService) appContext.getBean("taskService");

        taskService.deleteTaskBatchByTaskId(ids);

        map.put("status","success");
        map.put("info","删除成功");

        return map;
    }

    /**
     * 接收json中的课程id,实现批量删除
     */
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
