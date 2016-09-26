package cn.edu.just.service;


import cn.edu.just.pojo.Course;

import java.util.List;

public interface ICourseService {
    int insertCourse(Course course);
    void deleteCourseBatch(int[] ids);
    List<Course> getCourses(String username, int actor, String courseName, String teacherName, String companyName);
    void updateAdditionUrl(String url,int id);
    Integer queryCourse(Course course);
}
