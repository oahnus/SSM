package cn.edu.just.service;


import cn.edu.just.pojo.Summary;

import java.util.List;
import java.util.Map;

public interface ISummaryService {
    List<Summary> getSummaryList(Map<String, Object> map);
}
