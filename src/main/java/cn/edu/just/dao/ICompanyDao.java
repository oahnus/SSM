package cn.edu.just.dao;


import cn.edu.just.pojo.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ICompanyDao {
    Company verifyUser(Map<String, Object> map);
    List<Company> getCompanyList();
    Company getCompanyInfo(String username);
    void insertCompanyBatch(List<Company> list);
    void deleteCompanyBatch(List<Integer> list);
    void modifyPwd(Map<String, Object> paramMap);
    void insertCompany(Company company);
    Company queryById(int id);
}
