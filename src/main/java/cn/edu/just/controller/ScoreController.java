package cn.edu.just.controller;


import cn.edu.just.pojo.Data;
import cn.edu.just.pojo.Score;
import cn.edu.just.service.IScoreService;
import cn.edu.just.util.ApplicationContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理教师,企业评分的增,删,查
 */

@Controller
@RequestMapping("/score")
@CrossOrigin(maxAge = 3600)
public class ScoreController {

    /**
     * 教师,企业给学生的课程评分
     * 分数数据在学生选课后同时插入数据到分数表中
     * 此处到调用此函数时,只将对应的分数更新到数据库中
     * @param data Bean 获取courseId,studentId,score,actor
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Map updateScore(@RequestBody Data<Score> data){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IScoreService scoreService = (IScoreService) appContext.getBean("scoreService");

        Score score = data.getData().get(0);
        //将分数更新到数据库中
        scoreService.updateScore(score);

        map.put("status","success");
        map.put("info","添加分数成功");

        return map;
    }

    /**
     * 通过id批量删除分数信息
     * @param data Bean
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map deleteScoreById(@RequestParam Data<ID> data){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IScoreService scoreService = (IScoreService) appContext.getBean("scoreService");

        List<ID> idList = data.getData();
        int[] ids = new int[idList.size()];

        scoreService.deleteScoreById(ids);

        map.put("status","success");
        map.put("info","删除成功");

        return map;
    }

    /**
     * 分角色查询id
     * @param username 教师工号,学生学号,公司名
     * @param actor 角色
     * @param courseId 课程id
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map getScoreList(@RequestParam("username") String username,
                                           @RequestParam("actor") int actor,
                                           @RequestParam(value = "courseId",required = false) Integer courseId){
        Map<String,Object> map = new HashMap<>();
        ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
        IScoreService scoreService = (IScoreService) appContext.getBean("scoreService");
        List<Score> scoreList = scoreService.getScoreList(username,actor,courseId);

        map.put("status","success");
        map.put("info","查询成功");
        map.put("data",scoreList);

        return map;
    }

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
