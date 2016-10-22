package cn.edu.just.controller;

import cn.edu.just.pojo.Summary;
import cn.edu.just.service.ISummaryService;
import cn.edu.just.util.ApplicationContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇总
 */

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/summary")
public class SummaryController {

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map getSummary(
            @RequestParam(value = "profession",required = false) String profession,
            @RequestParam(value = "studentId",required = false) String studentId,
            @RequestParam(value = "courseName",required = false) String courseName,
            @RequestParam(value = "teacherName",required = false) String teacherName,
            @RequestParam(value = "companyName",required = false) String companyName,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "actor") Integer actor){

        Map<String,Object> map = new HashMap<>();

        if(username == null || actor == null){
            map.put("status","error");
            map.put("info","参数错误");
        }

        map.put("profession",profession);
        map.put("studentId",studentId);
        map.put("courseName",courseName);
        map.put("teacherName",teacherName);
        map.put("companyName",companyName);
        map.put("username",username);
        map.put("actor",actor);

        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        ISummaryService summaryService = (ISummaryService) appContext.getBean("summaryService");

        List<Summary> summaryList = summaryService.getSummaryList(map);

        map.clear();
        map.put("status","success");
        map.put("info","获取成功");
        map.put("data",summaryList);
        return map;
    }
}
