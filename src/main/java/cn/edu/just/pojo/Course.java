package cn.edu.just.pojo;

/**
 * 课程 Bean
 */
public class Course {
    // id
    private int id;
    // 课程名
    private String name;
    // 多选专业
    private String profession;
    // 授课教师的workerId
    private String teacher;
    // 公司
    private String company;
    // 课程描述
    private String memo;
    // 用于一对一查询，检索课程信息时同时获取授课教师的姓名
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    // 课程附件下载地址
    private String addition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
