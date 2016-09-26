package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.StuCourse;
import cn.edu.just.service.IStuCourseService;
import cn.edu.just.util.ApplicationContextConfig;
import com.sun.media.sound.SoftTuning;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 学生选课的增,删,查
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/stucourse")
public class StuCourseController {

    /**
     * 检索学生选课信息
     * @param actor 角色
     * @param studentId 学号
     * @return 结果信息,包含选课信息
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> getStudentCourseList(@RequestParam("username") String studentId,
                                                   @RequestParam("actor") int actor){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStuCourseService stuCourseService = (IStuCourseService) appContext.getBean("stuCourseService");

        if(actor!=4){
            map.put("status","error");
            map.put("info","非学生角色无法获取选课信息");
            return map;
        } else {
            List<StuCourse> stuCourseList = stuCourseService.getStudentCourseList(studentId);
            map.put("status","success");
            map.put("info","获取选课信息成功");
            map.put("data",stuCourseList);

            return map;
        }
    }

    /**
     * 增添学生选课信息
     * @param data Bean 从中获取courseId,studentId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Map<String,Object> insertStudentCourse(@RequestBody Data<StuCourse> data){
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStuCourseService stuCourseService = (IStuCourseService) appContext.getBean("stuCourseService");

        // 接收参数
        Integer courseId = data.getData().get(0).getCourseId();
        String studentId = data.getData().get(0).getStudentId();

        for(int i=0;i<data.getData().size();i++){
            if(courseId == null||studentId == null){
                continue;
            }
            List<StuCourse> list = stuCourseService.insertStuCourse(courseId,studentId,sdf.format(new Date()));
        }
        map.put("status","success");
        map.put("info","选课成功");

        return map;
    }

    /**
     *
     * @param data Bean 获取删除的选课信息的id数组
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestBody Data<ID> data){
        List<ID> idList = data.getData();
        int[] ids = new int[idList.size()];

        for(int i=0;i<idList.size();i++){
            ids[i] = idList.get(i).getId();
        }

        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IStuCourseService stuCourseService = (IStuCourseService) appContext.getBean("stuCourseService");

        stuCourseService.deleteStuCourse(ids);

        map.put("status","success");
        map.put("info","删除信息成功");

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
