package cn.edu.just.service.impl;

import cn.edu.just.dao.IScoreDao;
import cn.edu.just.pojo.Score;
import cn.edu.just.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scoreService")
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    private IScoreDao scoreDao;

    /**
     * 添加分数信息,在选课操作中添加
     * @param score 分数信息
     * @return id
     */
    @Override
    public int insertScore(Score score) {
        return this.scoreDao.insertScore(score);
    }

    /**
     * 更新分数到数据库,学生的分数数据在学生选课的同时,将分数信息保存到数据库
     * 在教师打分时,在根据学生学号和课程id更新分数到数据库中
     * @param score 分数
     */
    @Override
    public void updateScore(Score score) {
        this.scoreDao.updateScore(score);
    }

    /**
     * 根据课程id 和学生学号删除分数表数据
     * @param courseId 课程号
     * @param studentId 学号
     */
    @Override
    public void deleteScoreByCourseIdAndStudentId(Integer courseId,String studentId) {
        //TODO 同时删除学生在选课表中的记录和评分表中的记录？
        Map<String,Object> map = new HashMap<>();
        map.put("courseId",courseId);
        map.put("studentId",studentId);
        this.scoreDao.deleteScoreByCourseIdAndStudentId(map);
    }

    /**
     * 批量删除分数信息
     * @param ids id array
     */
    @Override
    public void deleteScoreById(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.scoreDao.deleteScoreById(idList);
    }

    /**
     * 按角色获取分数列表
     * @param username 用户名
     * @param actor 角色
     * @param courseId 课程id
     * @return 分数信息
     */
    @Override
    public List<Score> getScoreList(String username, Integer actor, Integer courseId) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("actor",actor);
        map.put("courseId",courseId);

        List<Score> list = new ArrayList<>();

        switch (actor){
            case 2:
                list = this.scoreDao.companyGetScoreList(map);
                break;
            case 3:
                list = this.scoreDao.teacherGetScoreList(map);
                break;
            case 4:
                list = this.scoreDao.studentGetScoreList(username);
                break;
        }

        for(Score score:list){
            float teacherScore = score.getTeacherScore();
            float companyScore = score.getCompanyScore();
            if(teacherScore!=0&&companyScore!=0){
                score.setScore((teacherScore+companyScore)/2);
            }else if(teacherScore != 0){
                score.setScore(teacherScore);
            }else{
                score.setScore(companyScore);
            }
        }
        return list;
    }

    public void setScoreDao(IScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }
}
