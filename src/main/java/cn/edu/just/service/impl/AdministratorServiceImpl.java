package cn.edu.just.service.impl;

import cn.edu.just.dao.IAdministratorDao;
import cn.edu.just.pojo.Administrator;
import cn.edu.just.service.IAdministratorService;
import cn.edu.just.util.ApplicationContextConfig;
import cn.edu.just.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("administratorService")
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private IAdministratorDao iAdministratorDao;

    @Override
    public boolean verifyUser(String username,String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",MD5Util.encode2hex(password));

        Administrator a = this.iAdministratorDao.verifyUser(map);
        if(a!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void modifyPwd(String username,String oldPassword,String newPassword) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("oldPassword",MD5Util.encode2hex(oldPassword));
        map.put("newPassword",MD5Util.encode2hex(newPassword));

        this.iAdministratorDao.modifyPwd(map);
    }

    public void setiAdministratorDao(IAdministratorDao iAdministratorDao) {
        this.iAdministratorDao = iAdministratorDao;
    }
}
