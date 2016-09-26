package cn.edu.just.service.impl;

import cn.edu.just.dao.ITaskDao;
import cn.edu.just.pojo.Task;
import cn.edu.just.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("taskService")
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskDao taskDao;

    /**
     * 将任务信息更新到数据库
     * @param task 任务信息
     */
    @Override
    public void updateTask(Task task) {
        // 更新时string转datetime报null错
        this.taskDao.updateTask(task);
    }

    @Override
    public int insertTask(Task task) {
        return this.taskDao.insertTask(task);
    }

    @Override
    public void deleteTaskBatchByCourseId(List<Integer> paramList) {

    }

    /**
     * 根据课程id获取课程下的任务信息
     * @param courseId 课程id
     * @return 课程信息
     */
    @Override
    public List<Task> getTaskList(int courseId) {
        return this.taskDao.getTaskList(courseId);
    }

    /**
     * 批量删除
     * @param ids id数组
     */
    @Override
    public void deleteTaskBatchByTaskId(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.taskDao.deleteTaskBatchByTaskId(idList);
    }

    /**
     * 通过课程id和任务名称判断数据库中是否存在数据,如果存在,返回任务id
     * @param taskName 任务名
     * @param courseId 课程id
     * @return 任务id
     */
    @Override
    public Integer queryByNameAndCourseId(String taskName, int courseId) {
        Map<String,Object> map = new HashMap<>();
        map.put("taskName",taskName);
        map.put("courseId",courseId);
        return this.taskDao.queryByNameAndCourseId(map);
    }

    public void setTaskDao(ITaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
