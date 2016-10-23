package cn.edu.just.dao;

import cn.edu.just.pojo.Company;
import cn.edu.just.pojo.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITeacherDao {
    Teacher verifyUser(Map<String, Object> map);
    List<Teacher> getTeacherList(String depart);
    Teacher getTeacherInfo(String username);
    void insertTeacherBatch(List<Teacher> list);
    void deleteTeacherBatch(List<Integer> list);
    void modifyPwd(Map<String, Object> paramMap);
    int insertTeacher(Teacher teacher);
    Teacher queryById(int id);
    List<String> getProfession();
    List<String> getDepart();
}
