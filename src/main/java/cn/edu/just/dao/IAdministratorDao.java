package cn.edu.just.dao;

import cn.edu.just.pojo.Administrator;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IAdministratorDao {
    Administrator verifyUser(Map<String, Object> map);
    void modifyPwd(Map<String, Object> map);
}
