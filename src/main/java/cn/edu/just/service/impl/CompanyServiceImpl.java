package cn.edu.just.service.impl;


import cn.edu.just.dao.ICompanyDao;
import cn.edu.just.pojo.Company;
import cn.edu.just.service.ICompanyService;
import cn.edu.just.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("companyService")
public class CompanyServiceImpl implements ICompanyService{

    @Autowired
    private ICompanyDao companyDao;

    /**
     * 公司登陆验证
     * @param username 用户名
     * @param password 密码
     * @return true or false
     */
    @Override
    public boolean verifyUser(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password", MD5Util.encode2hex(password));

        Company company = this.companyDao.verifyUser(map);
        if(company!=null) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取所有公司列表
     * @return 公司列表
     */
    @Override
    public List<Company> getCompanyList() {
        return this.companyDao.getCompanyList();
    }


    @Override
    public void insertCompanyBatch(List<Company> list) {
        this.companyDao.insertCompanyBatch(list);
    }

    /**
     * 批量删除公司
     * @param ids id数组
     */
    @Override
    public void deleteCompanyBatch(int[] ids) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            idList.add(ids[i]);
        }
        this.companyDao.deleteCompanyBatch(idList);
    }

    /**
     * 更改密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void modifyPwd(String username, String oldPassword, String newPassword) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("oldPassword",MD5Util.encode2hex(oldPassword));
        map.put("newPassword",MD5Util.encode2hex(newPassword));

        this.companyDao.modifyPwd(map);
    }

    /**
     * 批量导入公司
     * @param companyList 公司列表
     * @return 刚插入的数据
     */
    @Override
    public List<Company> insertCompany(List<Company> companyList) {
        // 定义id数组,保存插入的教师数据在数据库中的id
        int[] ids = new int[companyList.size()];

        List<Company> companys = new ArrayList<>();

        for(int i=0;i<companyList.size();i++){
            Company company = companyList.get(i);
            // 过滤掉无效数据
            if(
                    (company.getName()==null||company.getName().equals(""))||
                            (company.getContact()==null||company.getContact().equals(""))||
                            (company.getAddress()==null||company.getAddress().equals(""))
                    ){
                continue;
            }
            company.setPwd(MD5Util.encode2hex(company.getName()));
            this.companyDao.insertCompany(company);
            ids[i] = company.getId();
        }

        for(int i=0;i<ids.length;i++){
            if(ids[i]!=0){
                Company company = this.companyDao.queryById(ids[i]);
                company.setPwd("");
                companys.add(company);
            }
        }
        return companys;
    }

    public void setCompanyDao(ICompanyDao companyDao) {
        this.companyDao = companyDao;
    }
}
