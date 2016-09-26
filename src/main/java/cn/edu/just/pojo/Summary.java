package cn.edu.just.pojo;

/**
 * 汇总信息Bean
 */
public class Summary {
    // 专业
    private String profession;
    // 课程名
    private String courseName;
    // 学号
    private String studentId;
    // 学生姓名
    private String studentName;
    // 教师姓名
    private String teacherName;
    // 公司名
    private String companyName;
    // 教师打分
    private Float teacherScore;
    // 公司打分
    private Float companyScore;
    // 总分
    private Float score;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Float getTeacherScore() {
        return teacherScore;
    }

    public void setTeacherScore(Float teacherScore) {
        this.teacherScore = teacherScore;
    }

    public Float getCompanyScore() {
        return companyScore;
    }

    public void setCompanyScore(Float companyScore) {
        this.companyScore = companyScore;
    }
}
