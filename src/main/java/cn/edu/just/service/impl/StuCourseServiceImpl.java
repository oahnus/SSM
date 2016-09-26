package cn.edu.just.service.impl;


import cn.edu.just.dao.ICourseDao;
import cn.edu.just.dao.IScoreDao;
import cn.edu.just.dao.IStuCourseDao;
import cn.edu.just.pojo.Course;
import cn.edu.just.pojo.Score;
import cn.edu.just.pojo.StuCourse;
import cn.edu.just.service.IStuCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service("stuCourseService")
public class StuCourseServiceImpl implements IStuCourseService{
    @Autowired
    private IStuCourseDao stuCourseDao;
    @Autowired
    private IScoreDao scoreDao;
    @Autowired
    private ICourseDao courseDao;

    /**
     * 增加选课信息
     * @param courseId 选课id
     * @param studentId 学生学号
     * @param editTime 编辑时间
     * @return 选课信息
     */
    @Override
    public List<StuCourse> insertStuCourse(Integer courseId, String studentId, String editTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("courseId",courseId);
        map.put("studentId",studentId);

        StuCourse stuCourse = new StuCourse();
        stuCourse.setCourseId(courseId);
        stuCourse.setStudentId(studentId);
        stuCourse.setEditTime(editTime);

        Integer id = this.stuCourseDao.queryByCourseIdAndStudentId(map);

        //数据库中没有这条选课信息，则插入
        if(id == null){
            this.stuCourseDao.insertStuCourse(stuCourse);

            // 同时将分数信息存储到数据库中
            Score score = new Score();
            score.setCourseId(courseId);
            score.setStudentId(studentId);

            this.scoreDao.insertScore(score);
        }else{
            stuCourse.setId(id);
        }

        List<StuCourse> list = new ArrayList<>();
        list.add(stuCourse);
        return list;
    }

    /**
     * 批量删除选课信息,传递id数组,在删除选课信息的同时,将学生在分数表中的数据一并删除
     * @param ids id array
     */
    @Override
    public void deleteStuCourse(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
            StuCourse stuCourse = this.stuCourseDao.queryById(ids[i]);
            map.clear();
            map.put("courseId",stuCourse.getCourseId());
            map.put("studentId",stuCourse.getStudentId());
            this.scoreDao.deleteScoreByCourseIdAndStudentId(map);
        }
        this.stuCourseDao.deleteStuCourse(idList);
    }

    /**
     * 根据学号获取学生选课信息
     * @param studentId 学号
     * @return 学生选课信息
     */
    @Override
    public List<StuCourse> getStudentCourseList(String studentId) {

        List<StuCourse> stuCourseList = this.stuCourseDao.getStudentCourseList(studentId);
        for(StuCourse stuCourse:stuCourseList){
            int courseId = stuCourse.getCourseId();
            Course course = this.courseDao.queryById(courseId);

            if(course!=null) {
                stuCourse.setCompanyName(course.getCompany());
                stuCourse.setTeacherName(course.getTeacherName());
                stuCourse.setCourseName(course.getName());
            }
        }

        return stuCourseList;
    }

    /**
     * 根据课程id和学生id
     * @param courseId 课程id
     * @param studentId 学生学号
     * @return
     */
    @Override
    public Integer queryByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return this.queryByCourseIdAndStudentId(courseId,studentId);
    }

    /**
     * 根据di查询选课信息
     * @param id id
     * @return
     */
    @Override
    public StuCourse queryById(int id) {
        return this.stuCourseDao.queryById(id);
    }

    public void setStuCourseDao(IStuCourseDao stuCourseDao) {
        this.stuCourseDao = stuCourseDao;
    }

    public void setScoreDao(IScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }
}
