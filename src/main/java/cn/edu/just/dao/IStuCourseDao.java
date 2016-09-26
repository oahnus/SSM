package cn.edu.just.dao;


import cn.edu.just.pojo.StuCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IStuCourseDao {
    int insertStuCourse(StuCourse stuCourse);
    void deleteStuCourse(List<Integer> paramList);
    List<StuCourse> getStudentCourseList(String studentId);
    Integer queryByCourseIdAndStudentId(Map<String,Object> map);
    StuCourse queryById(int id);
}
