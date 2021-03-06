package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.Teacher;
import cn.edu.just.service.ITeacherService;
import cn.edu.just.util.ApplicationContextConfig;
import cn.edu.just.util.ExcelReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/teacher/")
public class TeacherController {
    private Logger logger = LoggerFactory.getLogger(TeacherController.class);

    ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
    ITeacherService teacherService = (ITeacherService) appContext.getBean("teacherService");

    /**
     * 获取学院信息，从人数较少的教师表中获取
     * @return 结果信息,包含学院列表
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=depart"})
    public Map getDeparts() {
        Map<String,Object> map = new HashMap<>();
        List<String> departList = teacherService.getDepart();

        List<Depart> departs = new ArrayList<>();
        for(int i=0;i<departList.size();i++){
            Depart depart = new Depart();
            depart.setDepart(departList.get(i));
            departs.add(depart);
        }

        map.put("status","success");
        map.put("info","获取学院信息成功");
        map.put("data",departs);
        return map;
    }

    /**
     * 获取专业信息，数据库中不存在专业表，从人数较少的教师表中获取
     * @return 结果信息,包含专业列表
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = "method=profession")
    public Map getProfessions() {
        Map<String,Object> map = new HashMap<>();
        List<String> professionList = teacherService.getProfession();

        List<Profession> professions = new ArrayList<>();
        for(int i=0;i<professionList.size();i++){
            Profession pro = new Profession();
            pro.setProfession(professionList.get(i));
            professions.add(pro);
        }

        map.put("status","success");
        map.put("info","获取专业信息成功");
        map.put("data",professions);

        return map;
    }

    /**
     * 获取教师信息
     * @param username 用户名
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/{username}/",method = RequestMethod.POST,headers = {"method=get"})
    public Map getTeacherInfo(@PathVariable String username){
        Map<String,Object> map = new HashMap<>();

        Teacher teacher = teacherService.getTeacherInfo(username);
        if(teacher != null){
            map.put("status","success");
            map.put("info","获取信息成功");
        }else{
            map.put("status","error");
            map.put("info","获取信息失败");
        }
        map.put("data",teacher);

        return map;
    }

    /**
     * 获取教师信息的列表
     * @return 结果信息
     */
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=get"})
    @ResponseBody
    public Map getTeacherList(@RequestParam(value = "depart",required = false) String depart){
        Map<String,Object> map = new HashMap<>();
        List<Teacher> teacherList = teacherService.getTeacherList(depart);

        map.put("status","success");
        map.put("info","获取专业信息成功");
        map.put("data",teacherList);

        return map;
    }

    /**
     * 批量添加教师信息
     * @param excelFile 包含教师信息的Excel文件
     * @return 刚添加的教师列表
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=post"})
    public Map insert(@RequestBody MultipartFile excelFile, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/temp/");

        // 路径不存在则创建
        File testFile = new File(path);
        if(!testFile.exists()) testFile.mkdirs();

        Map<String,Object> map = new HashMap<>();

        if(!excelFile.isEmpty()) {
            // 获取文件后缀
            String filename = excelFile.getOriginalFilename();
            // 将Excel文件保存到本地后，进行读取
            FileUtils.copyInputStreamToFile(excelFile.getInputStream(), new File(path, filename));

            File file = new File(path, filename);

            // 导入教师数据，1代表教师角色
            List<Teacher> list = ExcelReader.readExcelFile(file, 1);

            if(list != null) {
                List<Teacher> insertTeacherList = teacherService.insertTeacher(list);

                map.put("status","success");
                map.put("info","导入教师信息成功");
                map.put("data",insertTeacherList);
            }else{
                map.put("status","error");
                map.put("info","Excel解析失败,请检查Excel格式");
            }
            file.delete();

        }else{
            map.put("status","error");
            map.put("info","导入教师信息失败,文件内容为空");
        }
        return map;
    }

    /**
     * 根据id批量删除
     * @param data Bean 用于接收JSON数据中的id对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=delete"})
    public Map delete(@RequestBody Data<TeacherID> data){
        Map<String,Object> map = new HashMap<>();

        List<TeacherID> teacherIdList = data.getData();
        int[] ids = new int[teacherIdList.size()];

        for(int i = 0; i< teacherIdList.size(); i++){
            ids[i] = teacherIdList.get(i).getId();
        }

        teacherService.deleteTeacherBatch(ids);

        map.put("status","success");
        map.put("info","删除信息成功");

        return map;
    }

    /**
     * 将专业列表封装成独立对象
     */
    static class Profession{
        private String profession;
        public Profession(){super();}

        public String getProfession() {
            return profession;
        }
        public void setProfession(String profession) {
            this.profession = profession;
        }
    }

    /**
     * 将学院列表封装成对象
     */
    static class Depart{
        private String depart;
        public Depart(){super();}

        public String getDepart() {
            return depart;
        }
        public void setDepart(String depart) {
            this.depart = depart;
        }
    }

    /**
     * 接收json中的教师id,实现批量删除
     */
    static class TeacherID {
        private int id;

        public TeacherID(){super();}

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
    }
}
