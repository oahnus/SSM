package cn.edu.just.service.impl;


import cn.edu.just.dao.ISummaryDao;
import cn.edu.just.pojo.Summary;
import cn.edu.just.service.ISummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("summaryService")
public class SummaryServiceImpl implements ISummaryService{

    @Autowired
    private ISummaryDao summaryDao;

    @Override
    public List<Summary> getSummaryList(Map<String, Object> map) {
        Integer actor = (Integer) map.get("actor");
        List<Summary> list = new ArrayList();
        switch (actor){
            case 1:
                list = this.summaryDao.getSummaryList(map);
                break;
            case 2:
                list = this.summaryDao.companyGetSummaryList(map);
                break;
            case 3:
                list = this.summaryDao.teacherGetSummaryList(map);
                break;
        }

        for(Summary summary:list){
            float teacherScore = summary.getTeacherScore();
            float companyScore = summary.getCompanyScore();
            if(teacherScore!=0&&companyScore!=0){
                summary.setScore((teacherScore+companyScore)/2);
            }else if(teacherScore != 0){
                summary.setScore(teacherScore);
            }else{
                summary.setScore(companyScore);
            }
        }

        return list;
    }

    public void setSummaryDao(ISummaryDao summaryDao) {
        this.summaryDao = summaryDao;
    }
}
