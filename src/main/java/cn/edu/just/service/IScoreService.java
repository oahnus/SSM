package cn.edu.just.service;


import cn.edu.just.pojo.Score;

import java.util.List;
import java.util.Map;

public interface IScoreService {
    int insertScore(Score score);
    void updateScore(Score score);
    void deleteScoreByCourseIdAndStudentId(Integer courseId,String studentId);
    void deleteScoreById(int[] ids);
    List<Score> getScoreList(String username,Integer actor,Integer courseId);
}
