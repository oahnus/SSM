package cn.edu.just.dao;

import cn.edu.just.pojo.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ICourseDao {
    Integer queryCourse(Course course);

    int insertCourse(Course course);

    void updateAdditionUrl(Map<String, Object> map);

    void deleteCourseBatch(List<Integer> paramList);

//    List<Course> getCourses(Map<String, Object> map);
    List<Course> teacherGetCourses(Map<String, Object> map);
    List<Course> studentGetCourses(Map<String, Object> map);
    List<Course> companyGetCourses(Map<String, Object> map);
    Course queryById(Integer courseId);
}
