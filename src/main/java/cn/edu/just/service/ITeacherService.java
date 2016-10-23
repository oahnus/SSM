package cn.edu.just.service;


import cn.edu.just.pojo.Teacher;

import java.util.List;

public interface ITeacherService {
    boolean verifyUser(String username,String password);
    List<Teacher> getTeacherList(String depart);
    Teacher getTeacherInfo(String username);
    void insertTeacherBatch(List<Teacher> list);
    void deleteTeacherBatch(int[] ids);
    void modifyPwd(String username,String oldPassword,String newPassword);
    List<Teacher> insertTeacher(List<Teacher> teacherList);
    List<String> getProfession();
    List<String> getDepart();
}
