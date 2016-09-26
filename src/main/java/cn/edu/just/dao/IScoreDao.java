package cn.edu.just.dao;


import cn.edu.just.pojo.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IScoreDao {
    int insertScore(Score score);

    void updateScore(Score score);

    void deleteScoreByCourseIdAndStudentId(Map<String, Object> map);
    void deleteScoreById(List<Integer> idList);

    List<Score> studentGetScoreList(String username);
    List<Score> teacherGetScoreList(Map<String, Object> map);
    List<Score> companyGetScoreList(Map<String, Object> map);

    List<Score> getScoreList(Map<String, Object> map);
}
