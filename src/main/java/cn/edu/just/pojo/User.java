package cn.edu.just.pojo;

/**
 * 登陆验证的User Bean
 */
public class User {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色
    private int actor;

    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActor() {
        return actor;
    }

    public void setActor(int actor) {
        this.actor = actor;
    }
}
