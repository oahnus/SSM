package cn.edu.just.service.impl;

import cn.edu.just.dao.IStudentDao;
import cn.edu.just.dao.ITaskDao;
import cn.edu.just.pojo.Student;
import cn.edu.just.pojo.Task;
import cn.edu.just.service.IStudentService;
import cn.edu.just.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentDao studentDao;

    @Autowired
    private ITaskDao taskDao;

    /**
     * 登陆验证
     * @param username 用户名
     * @param password 密码
     * @return true or false
     */
    @Override
    public boolean verifyUser(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password", MD5Util.encode2hex(password));

        Student student = this.studentDao.verifyUser(map);
        if(student!=null) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取学生列表
     * @return 学生列表
     */
    @Override
    public List<Student> getStudentList() {
        return this.studentDao.getStudentList();
    }

    /**
     * 教师获取某门课下有哪些学生选
     * @param courseId 课程id
     * @return 学生信息
     */
    @Override
    public List<Student> getStudentListByCourseId(Integer courseId) {
        return this.studentDao.getStudentListByCourseId(courseId);
    }


    @Override
    public void insertStudentBatch(List<Student> list) {
        this.studentDao.insertStudentBatch(list);
    }

    /**
     * 批量删除
     * @param ids id数组
     */
    @Override
    public void deleteStudentBatch(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.studentDao.deleteStudentBatch(idList);
    }

    @Override
    public void deleteTaskResult(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.studentDao.deleteTaskResult(idList);
    }

    /**
     * 更改密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void modifyPwd(String username, String oldPassword, String newPassword) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("oldPassword",MD5Util.encode2hex(oldPassword));
        map.put("newPassword",MD5Util.encode2hex(newPassword));

        this.studentDao.modifyPwd(map);
    }

    /**
     * 导入学生信息
     * @param studentList 学生列表
     */
    @Override
    public List<Student> insertStudent(List<Student> studentList) {
        // 定义id数组,保存插入的教师数据在数据库中的id
        int[] ids = new int[studentList.size()];

        List<Student> students = new ArrayList<>();

        for(int i=0;i<studentList.size();i++){
            Student student = studentList.get(i);
            // 过滤掉无效数据
            if(
                    (student.getName()==null||student.getName().equals(""))||
                            (student.getStudentId()==null||student.getStudentId().equals(""))||
                            (student.getName()==null||student.getName().equals(""))||
                            (student.getProfession()==null||student.getProfession().equals(""))||
                            (student.getDepart()==null||student.getDepart().equals(""))
                    ){
                continue;
            }
            student.setPwd(MD5Util.encode2hex(student.getStudentId()));
            this.studentDao.insertStudent(student);
            ids[i] = student.getId();
        }

        for(int i=0;i<ids.length;i++){
            if(ids[i]!=0){
                Student student = this.studentDao.queryById(ids[i]);
                student.setPwd("");
                students.add(student);
            }
        }

        return students;
    }

    /**
     * 更新成果文件
     * @param map
     */
    @Override
    public void updateTaskResult(Map<String, Object> map) {
        this.studentDao.updateTaskResult(map);
    }


    /**
     * 添加任务成果文件
     * @param map
     */
    @Override
    public void insertTaskResult(Map<String, Object> map) {
        this.studentDao.insertTaskResult(map);
    }

    /**
     * 根据学号和任务id检索数据
     * @param map 保存学号和任务id的map
     * @return 在数据库中的id
     */
    @Override
    public Integer queryByTaskIdAndStudentId(Map<String, Object> map) {
        return this.studentDao.queryByTaskIdAndStudentId(map);
    }

    /**
     * 获取学生成果提交情况
     * @param username 学生学号
     * @param courseId 课程id
     * @return 成果信息
     */
    @Override
    public List<Task> getResultList(String username, Integer courseId) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("courseId",courseId);

        List<Task> tasks = taskDao.getTaskList(courseId);
        for(Task task:tasks){
            Task t = this.studentDao.getResult(task.getId());
            if(t!=null){
                task.setResultMemo(t.getResultMemo());
                task.setResultURL(t.getResultURL());
            }
        }

        return tasks;
    }


    public void setStudentDao(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }
}
