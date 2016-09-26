package cn.edu.just.dao;


import cn.edu.just.pojo.Summary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ISummaryDao {
    List<Summary> getSummaryList(Map<String, Object> map);
    List<Summary> teacherGetSummaryList(Map<String, Object> map);
    List<Summary> companyGetSummaryList(Map<String, Object> map);

}
