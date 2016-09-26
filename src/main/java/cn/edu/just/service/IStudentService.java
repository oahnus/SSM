package cn.edu.just.service;


import cn.edu.just.pojo.Student;
import cn.edu.just.pojo.Task;

import java.util.List;
import java.util.Map;

public interface IStudentService {
    boolean verifyUser(String username,String password);
    void modifyPwd(String username,String oldPassword,String newPassword);

    List<Student> getStudentList();
    List<Student> getStudentListByCourseId(Integer courseId);
    Integer queryByTaskIdAndStudentId(Map<String, Object> map);
    List<Task> getResultList(String username,Integer courseId);


    void insertStudentBatch(List<Student> list);
    void insertTaskResult(Map<String, Object> map);
    List<Student> insertStudent(List<Student> studentList);

    void updateTaskResult(Map<String, Object> map);

    void deleteStudentBatch(int[] ids);
    void deleteTaskResult(int[] ids);
}
