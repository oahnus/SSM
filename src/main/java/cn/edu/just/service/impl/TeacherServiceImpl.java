package cn.edu.just.service.impl;

import cn.edu.just.dao.ITeacherDao;
import cn.edu.just.pojo.Teacher;
import cn.edu.just.service.ITeacherService;
import cn.edu.just.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("teacherService")
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private ITeacherDao teacherDao;

    /**
     * 教师登陆验证
     * @param username 用户名 工号
     * @param password 密码
     * @return true or false
     */
    @Override
    public boolean verifyUser(String username,String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password", MD5Util.encode2hex(password));

        Teacher teacher = this.teacherDao.verifyUser(map);
        if(teacher!=null) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据学院获取教师列表
     * @param depart 学院
     * @return 教师列表
     */
    @Override
    public List<Teacher> getTeacherList(String depart) {
        return this.teacherDao.getTeacherList(depart);
    }

    @Override
    public Teacher getTeacherInfo(String username) {
        return teacherDao.getTeacherInfo(username);
    }

    @Override
    public void insertTeacherBatch(List<Teacher> list) {
        this.teacherDao.insertTeacherBatch(list);
    }

    /**
     * 批量删除教师数据
     * @param ids 要删除的id数组
     */
    @Override
    public void deleteTeacherBatch(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.teacherDao.deleteTeacherBatch(idList);
    }

    /**
     * 更改密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void modifyPwd(String username,String oldPassword,String newPassword) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("oldPassword",MD5Util.encode2hex(oldPassword));
        map.put("newPassword",MD5Util.encode2hex(newPassword));

        this.teacherDao.modifyPwd(map);
    }

    /**
     * 导入教师数据
     * @param teacherList 教师列表
     * @return 返回从数据库中检索出的刚导入的教师列表
     */
    @Override
    public List<Teacher> insertTeacher(List<Teacher> teacherList) {
        // 定义id数组,保存插入的教师数据在数据库中的id
        int[] ids = new int[teacherList.size()];

        List<Teacher> teachers = new ArrayList<>();

        for(int i=0;i<teacherList.size();i++){
            Teacher teacher = teacherList.get(i);
            // 过滤掉无效数据
            if(
                    (teacher.getName()==null||teacher.getName().equals(""))||
                            (teacher.getWorkerId()==null||teacher.getWorkerId().equals(""))||
                            (teacher.getProfession()==null||teacher.getProfession().equals(""))||
                            (teacher.getDepart()==null||teacher.getDepart().equals(""))
                    ){
                continue;
            }
            teacher.setPwd(MD5Util.encode2hex(teacher.getWorkerId()));
            this.teacherDao.insertTeacher(teacher);
            ids[i] = teacher.getId();
        }

        for(int i=0;i<ids.length;i++){
            if(ids[i]!=0){
                Teacher teacher = this.teacherDao.queryById(ids[i]);
                teacher.setPwd("");
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    /**
     * 获取所有专业
     * @return 专业列表
     */
    @Override
    public List<String> getProfession() {
        return this.teacherDao.getProfession();
    }

    /**
     * 获取所有学院
     * @return 学院列表
     */
    @Override
    public List<String> getDepart() {
        return this.teacherDao.getDepart();
    }

    public void setTeacherDao(ITeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
}
