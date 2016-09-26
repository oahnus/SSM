package cn.edu.just.dao;

import cn.edu.just.pojo.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITaskDao {
    void updateTask(Task task);

    List<Task> getTaskList(int courseId);

    int insertTask(Task task);

    Integer queryByNameAndCourseId(Map<String,Object> map);

    void deleteTaskBatchByCourseId(List<Integer> paramList);
    void deleteTaskBatchByTaskId(List<Integer> paramList);
}
