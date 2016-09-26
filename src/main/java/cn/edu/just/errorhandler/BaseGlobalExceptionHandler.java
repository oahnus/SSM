package cn.edu.just.errorhandler;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseGlobalExceptionHandler implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView mav = new ModelAndView();
        String message = "";

System.out.println("Error:"+e.getClass().getName()+"\n"+
        "Cause:"+e.getCause().getMessage());

        if(e instanceof MultipartException){
            message = e.getMessage();
        } else if(e instanceof ServletRequestBindingException){
            message = e.getMessage();
        } else if(e instanceof IOException){
            message = e.getMessage();
        }else {
            message = e.getMessage();
        }

        String json = "{\"status\":\"error\",\"info\":\""+message+"\"}";
        mav.addObject("message",json);
        mav.setViewName("error");
        return mav;
    }
}
