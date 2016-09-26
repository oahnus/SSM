package cn.edu.just.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台测试功能页
 */

@Controller
@RequestMapping("/test")
public class Test {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/insert")
    public String insert(){
        return "insert";
    }

    @RequestMapping("/deleteStuCourse")
    public String deleteStuCourse(){return "deleteStuCourse";}

    @RequestMapping("/release")
    public String releaseCourse(){
        return "form";
    }

    @RequestMapping("/modify")
    public String modify(){
        return "modify";
    }

    @RequestMapping("/task")
    public String releaseTask(){
        return "releaseTask";
    }

    @RequestMapping("/stucourse")
    public String studentCourse(){ return "stucourse";}

    @RequestMapping("/result")
    public String uploadResult(){return "result";}

    @RequestMapping("/score")
    public String score(){return "score";}

    @RequestMapping("/get")
    public String getCourse(){return "getCourse";}
}
