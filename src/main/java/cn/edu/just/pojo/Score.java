package cn.edu.just.pojo;

/**
 * 学生的课程分数Bean
 */
public class Score {
    // id
    private int id;
    // 课程id
    private int courseId;
    // 课程名
    private String courseName;
    // 学生学号
    private String studentId;
    // 学生姓名
    private String studentName;
    // 教师姓名
    private String teacherName;
    // 角色
    private int actor;
    // 公司名
    private String companyName;
    // 教师评分
    private float teacherScore;
    // 公司评分
    private float companyScore;
    // 总分=(教师评分+公司评分)/2
    private float score;

    public float getTeacherScore() {
        return teacherScore;
    }

    public void setTeacherScore(float teacherScore) {
        this.teacherScore = teacherScore;
    }

    public float getCompanyScore() {
        return companyScore;
    }

    public void setCompanyScore(float companyScore) {
        this.companyScore = companyScore;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public int getActor() {
        return actor;
    }

    public void setActor(int actor) {
        this.actor = actor;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
