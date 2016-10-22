package cn.edu.just.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AuthInterceptor implements HandlerInterceptor{
    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private String[] accessUrl = {
            "/ShiXun-6/api/user/verify",
            "/ShiXun-6/api/400",
            "/ShiXun-6/api/404",
            "/ShiXun-6/api/500"
    };

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
logger.info("拦截器");
logger.info(sdf.format(new Date())+"  INFO  "+httpServletRequest.getRemoteAddr()+" > "+httpServletRequest.getRequestURL());

//        System.out.println(httpServletRequest.getRequestURI());
        return true;
//        Cookie[] cookies = httpServletRequest.getCookies();
//        String username = "";
//        String token = "";
//        for(int i=0;i<cookies.length;i++){
//            if(cookies[i].getName().equals("username")){
//                username = cookies[i].getValue().split("#")[0];
//                token = cookies[i].getValue().split("#")[1];
//                break;
//            }
//        }
//
//        String uri = httpServletRequest.getRequestURI();
//        for(int i=0;i<accessUrl.length;i++){
//            if(uri.trim().equals(accessUrl[i])){
//                return true;
//            }
//        }
//
//        if(httpServletRequest.getSession().getAttribute(username)==null){
//            httpServletResponse.getWriter().write("无权限访问");
////            httpServletRequest.getRequestDispatcher("http://127.0.0.1:333/").forward(httpServletRequest,httpServletResponse);
//            return false;
//        }
//
//        if(httpServletRequest.getSession().getAttribute(username).toString().equals(token)){
//            return true;
//        }
//        else{
//            httpServletResponse.getWriter().write("无权限访问");
//            return false;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
