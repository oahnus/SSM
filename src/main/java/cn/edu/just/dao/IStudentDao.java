package cn.edu.just.dao;

import cn.edu.just.pojo.Student;
import cn.edu.just.pojo.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IStudentDao {
    Student verifyUser(Map<String, Object> map);
    void modifyPwd(Map<String, Object> map);

    List<Student> getStudentList();
    List<Student> getStudentListByCourseId(Integer courseId);
    Student queryById(int id);
    Integer queryByTaskIdAndStudentId(Map<String, Object> map);
    Task getResult(Integer taskId);

    void insertStudentBatch(List<Student> list);
    void insertStudent(Student student);
    void insertTaskResult(Map<String, Object> map);

    void updateTaskResult(Map<String, Object> map);

    void deleteStudentBatch(List<Integer> list);
    void deleteTaskResult(List<Integer> list);
}
