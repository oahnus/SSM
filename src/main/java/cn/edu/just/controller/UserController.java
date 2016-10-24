package cn.edu.just.controller;

import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.User;
import cn.edu.just.service.IAdministratorService;
import cn.edu.just.service.ICompanyService;
import cn.edu.just.service.IStudentService;
import cn.edu.just.service.ITeacherService;
import cn.edu.just.util.ApplicationContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 处理四种角色用户的登陆验证,修改密码等操作
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // 生成随机数
    private Random random = new Random();

    private ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();

    private IAdministratorService administratorService = (IAdministratorService) appContext.getBean("administratorService");
    private ICompanyService companyService = (ICompanyService) appContext.getBean("companyService");
    private ITeacherService teacherService = (ITeacherService) appContext.getBean("teacherService");
    private IStudentService studentService = (IStudentService) appContext.getBean("studentService");

    /**
     * 用户登陆验证
     * @param data 包含用户名username,密码password,角色actor
     * @param request 获取session
     * @param response 设置cookie
     * @return 结果信息,验证成果status success,失败 status error
     */
    @RequestMapping(value = "/1",method = RequestMethod.POST,headers = {"method=verify"})
    @ResponseBody
    private Map verifyUser(@RequestBody Data<User> data,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String,Object> map = new HashMap<>();
        User user = data.getData().get(0);
        boolean isLegalUser = false;

        String username = user.getUsername();
        String password = user.getPassword();
        Integer actor = user.getActor();
        isLegalUser = isLegalUser(username, password, actor);

        if(isLegalUser){
            // 将用户登录状态记录到Cookie中
            long randomNum = random.nextLong();

            // 设置编码方式
            username = URLEncoder.encode(username,"UTF-8");
            request.getSession().setAttribute(username,randomNum);
            Cookie cookie = new Cookie("username",username+"#"+randomNum);
            cookie.setMaxAge(1000);//1000sec
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            map.put("status","success");
            map.put("info","登录成功");
        }else{
            map.put("status","error");
            map.put("info","用户名或密码错误");
        }
        return map;
    }

    /**
     * 更改用户密码
     * @param data 包含用户旧密码，新密码
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=modify"})
    private Map modify(@RequestBody Data<User> data){
        User user = data.getData().get(0);
        Map<String,Object> map = new HashMap<>();
        // 获取用户名，新旧密码，角色
        String username = user.getUsername();
        String oldPassword = user.getPassword();
        String newPassword = user.getNewPassword();
        Integer actor = user.getActor();

        boolean isLegalUser = false;
        isLegalUser = isLegalUser(username,oldPassword,actor);
        if(isLegalUser){
            switch (actor){
                case 1:
                    // 验证用户名和密码
                    administratorService.modifyPwd(username,oldPassword,newPassword);
                    break;
                case 2:
                    companyService.modifyPwd(username,oldPassword,newPassword);
                    break;
                case 3:
                    teacherService.modifyPwd(username,oldPassword,newPassword);
                    break;
                case 4:
                    studentService.modifyPwd(username,oldPassword,newPassword);
                    break;
            }
            map.put("status","success");
            map.put("info","修改成功");
        }else{
            map.put("status","error");
            map.put("info","用户名密码错误");
        }
        return map;
    }


    /**
     * 验证用户是否是合法用户
     * @param username 用户名
     * @param password 密码
     * @param actor 角色
     * @return true or false
     */
    private boolean isLegalUser(String username, String password, Integer actor) {
        boolean isLegalUser = false;
        // 判断角色
        switch (actor){
            case 1:
                isLegalUser = administratorService.verifyUser(username,password);
                break;
            case 2:
                isLegalUser = companyService.verifyUser(username,password);
                break;
            case 3:
                isLegalUser = teacherService.verifyUser(username,password);
                break;
            case 4:
                isLegalUser = studentService.verifyUser(username,password);
                break;
        }
        return isLegalUser;
    }
}
