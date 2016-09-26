package cn.edu.just.service.impl;

import cn.edu.just.dao.ICourseDao;
import cn.edu.just.dao.ITaskDao;
import cn.edu.just.pojo.Course;
import cn.edu.just.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private ICourseDao courseDao;

    @Autowired
    private ITaskDao taskDao;

    /**
     * 发布课程
     * @param course 课程信息
     * @return 课程id
     */
    @Override
    public int insertCourse(Course course) {
        return this.courseDao.insertCourse(course);
    }

    /**
     * 批量删除
     * @param ids id数组
     */
    @Override
    public void deleteCourseBatch(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }

        //同时删除task
        this.courseDao.deleteCourseBatch(idList);
        this.taskDao.deleteTaskBatchByCourseId(idList);
    }

    /**
     * 获取课程列表
     * @param username 登陆用户名,学号,教师工号,公司名
     * @param actor 角色
     * @param courseName 课程名
     * @param teacherName 教师名
     * @param companyName 公司名
     * @return 课程信息
     */
    @Override
    public List<Course> getCourses(String username, int actor, String courseName, String teacherName, String companyName) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("courseName",courseName);
        map.put("teacherName",teacherName);
        map.put("companyName",companyName);

        List<Course> courseList = new ArrayList<>();

        switch (actor){
            case 1:
                //TODO 管理员 查看课程?
                break;
            case 2:
                courseList = this.courseDao.companyGetCourses(map);
                break;
            case 3:
                courseList = this.courseDao.teacherGetCourses(map);
                break;
            case 4:
                courseList = this.courseDao.studentGetCourses(map);
                break;
        }
        return courseList;
    }

    /**
     * 更新数据库中课程附件的下载地址
     * @param url 下载地址
     * @param id 课程id
     */
    @Override
    public void updateAdditionUrl(String url,int id) {
        Map<String,Object> map = new HashMap<>();
        map.put("url",url);
        map.put("id",id);

        this.courseDao.updateAdditionUrl(map);
    }

    /**
     * 查询课程信息,判断课程是否存在
     * @param course 课程信息
     * @return 课程id
     */
    @Override
    public Integer queryCourse(Course course) {
        return this.courseDao.queryCourse(course);
    }

    public void setTaskDao(ITaskDao taskDao) {
        this.taskDao = taskDao;
    }
    public void setCourseDao(ICourseDao courseDao) {
        this.courseDao = courseDao;
    }
}
