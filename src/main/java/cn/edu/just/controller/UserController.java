package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.User;
import cn.edu.just.service.IAdministratorService;
import cn.edu.just.service.ICompanyService;
import cn.edu.just.service.IStudentService;
import cn.edu.just.service.ITeacherService;
import cn.edu.just.util.ApplicationContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 处理四种角色用户的登陆验证,修改密码等操作
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/user")
public class UserController {

    // 生成随机数
    private Random random = new Random();

    /**
     * 用户登陆验证
     * @param data Bean 包含用户名username,密码password,角色actor
     * @return 结果信息,验证成果status success,失败 status error
     */
    @RequestMapping(value="/verify",method = RequestMethod.POST)
    public ResponseEntity<Map> verify(@RequestBody Data<User> data,
                                 HttpServletRequest request,
                                 HttpServletResponse response){

        String username = data.getData().get(0).getUsername();
        String password = data.getData().get(0).getPassword();
        Integer actor = data.getData().get(0).getActor();

        Map<String,Object> map = new HashMap<>();

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();

        boolean isLegalUser = false;

        // 判断角色
        switch (actor){
            case 1:
                IAdministratorService administratorService = (IAdministratorService) appContext.getBean("administratorService");
                isLegalUser = administratorService.verifyUser(username,password);
                break;
            case 2:
                ICompanyService companyService = (ICompanyService) appContext.getBean("companyService");
                isLegalUser = companyService.verifyUser(username,password);
                break;
            case 3:
                ITeacherService teacherService = (ITeacherService) appContext.getBean("teacherService");
                isLegalUser = teacherService.verifyUser(username,password);
                break;
            case 4:
                IStudentService studentService = (IStudentService) appContext.getBean("studentService");
                isLegalUser = studentService.verifyUser(username,password);
                break;
        }

        map.clear();
        if(isLegalUser){
            long randomNum = random.nextLong();

            request.getSession().setAttribute(username,randomNum);
            Cookie cookie = new Cookie("username",username+"#"+randomNum);
            cookie.setMaxAge(1000);//1000sec
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            map.put("status","success");
            map.put("info","成功");
        }else{
            map.put("status","error");
            map.put("info","失败");
        }
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    /**
     * 更改用户密码
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public Map<String,Object> modify(@RequestBody Data<ModifyUser> data){
        Map<String,Object> map = new HashMap<>();
        // 获取用户名，新旧密码，角色
        String username = data.getData().get(0).getUsername();
        String oldPassword = data.getData().get(0).getOldPassword();
        String newPassword = data.getData().get(0).getNewPassword();
        Integer actor = data.getData().get(0).getActor();

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();

        boolean isLegalUser = false;
        switch (actor){
            case 1:
                // 验证用户名和密码
                IAdministratorService administratorService = (IAdministratorService) appContext.getBean("administratorService");
                isLegalUser = administratorService.verifyUser(username,oldPassword);
                if(isLegalUser){
                    administratorService.modifyPwd(username,oldPassword,newPassword);
                }
                break;
            case 2:
                ICompanyService companyService = (ICompanyService) appContext.getBean("companyService");
                isLegalUser = companyService.verifyUser(username,oldPassword);
                if(isLegalUser){
                    companyService.modifyPwd(username,oldPassword,newPassword);
                }
                break;
            case 3:
                ITeacherService teacherService = (ITeacherService) appContext.getBean("teacherService");
                isLegalUser = teacherService.verifyUser(username,oldPassword);
                if(isLegalUser){
                    teacherService.modifyPwd(username,oldPassword,newPassword);
                }
                break;
            case 4:
                IStudentService studentService = (IStudentService) appContext.getBean("studentService");
                isLegalUser = studentService.verifyUser(username,oldPassword);
                if(isLegalUser){
                    studentService.modifyPwd(username,oldPassword,newPassword);
                }
                break;
        }

        map.put("status","success");
        map.put("info","修改成功");
        return map;
    }

    //包含角色，用户名，新密码，旧密码
    static class ModifyUser{
        private String username;
        private String oldPassword;
        private String newPassword;
        private int actor;

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getOldPassword() {
            return oldPassword;
        }
        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }
        public String getNewPassword() {
            return newPassword;
        }
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
        public int getActor() {
            return actor;
        }
        public void setActor(int actor) {
            this.actor = actor;
        }
    }
}
