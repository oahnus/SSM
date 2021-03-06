package cn.edu.just.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
@ResponseBody
public class ExceptionAdvice {

    private Map<String,Object> map;

//    NoHandlerFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Map handleNoHandlerFoundException(Exception e) {
        map = new HashMap<>();
        map.put("status","error");
        map.put("info",e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map handleHttpRequestMethodNotSupportedException(Exception e) {
        map = new HashMap<>();
        map.put("status","error");
        map.put("info","HttpRequestMethodNotSupportedException");
        return map;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map handleHttpMessageNotReadableException(Exception e) {
        map = new HashMap<>();
        map.put("status","error");
        map.put("info",e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    public Map handleServletRequestBindingException(Exception e) {
        map = new HashMap<>();
        map.put("status","error");
        map.put("info",e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnsupportedEncodingException.class)
    public Map handleUnsupportedEncodingException(Exception e){
        map = new HashMap<>();
        map.put("status","error");
        map.put("info",e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map handleException(Exception e) {
        map = new HashMap<>();
        map.put("status","error");
        map.put("info",e.getMessage());
        return map;
    }
}
