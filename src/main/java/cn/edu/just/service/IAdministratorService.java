package cn.edu.just.service;

import java.util.Map;

public interface IAdministratorService {
    boolean verifyUser(String username,String password);
    void modifyPwd(String username,String oldPassword,String newPassword);
}
