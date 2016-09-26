package cn.edu.just.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class XSSInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        Enumeration<String> names = httpServletRequest.getParameterNames();

        while (names.hasMoreElements()){
            String[] values = httpServletRequest.getParameterValues(names.nextElement());
            for(String value:values){
                value = clean(value);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String clean(String value){
        value = value.replace("<","&lt;").replace(">","&gt;");
        value = value.replace("\\(","&#40;").replace("\\)","&#41;");
        value = value.replace("'","&#39;");
        value = value.replace("eval\\((.*)\\)","");
        value = value.replace("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
        value = value.replace("script","");

        return value;
    }
}
