package cn.edu.just.service;


import cn.edu.just.pojo.Task;

import java.util.List;
import java.util.Map;

public interface ITaskService {
    void updateTask(Task task);
    int insertTask(Task task);
    void deleteTaskBatchByCourseId(List<Integer> paramList);
    List<Task> getTaskList(int courseId);

    void deleteTaskBatchByTaskId(int[] ids);
    Integer queryByNameAndCourseId(String taskName,int courseId);
}
