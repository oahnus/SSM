package cn.edu.just.service;


import cn.edu.just.pojo.Company;

import java.util.List;


public interface ICompanyService {
    boolean verifyUser(String username,String password);
    List<Company> getCompanyList();
    Company getCompanyInfo(String username);
    void insertCompanyBatch(List<Company> list);
    void deleteCompanyBatch(int[] ids);
    void modifyPwd(String username,String oldPassword,String newPassword);
    List<Company> insertCompany(List<Company> companyList);
}
