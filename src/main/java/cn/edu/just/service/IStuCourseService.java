package cn.edu.just.service;


import cn.edu.just.pojo.StuCourse;

import java.util.List;

public interface IStuCourseService {
    List<StuCourse> insertStuCourse(Integer courseId, String studentId, String editTIme);
    void deleteStuCourse(int[] ids);
    List<StuCourse> getStudentCourseList(String studentId);
    Integer queryByCourseIdAndStudentId(Integer courseId,Integer studentId);
    StuCourse queryById(int id);
}
