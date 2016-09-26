package cn.edu.just.pojo;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 任务Bean
 */
public class Task {
    // id
    private int id;
    // 任务名
    private String name;
    // 开始时间
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startTime;
    // 结束时间
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String endTime;
    // 对应课程的id
    private int courseId;
    // 任务内容，存有任务文件在服务器上的url地址
    private String content;
    // 描述
    private String memo;
    // 任务成果下载链接
    private String resultURL;
    // result memo
    private String resultMemo;


    public String getResultMemo() {
        return resultMemo;
    }

    public void setResultMemo(String resultMemo) {
        this.resultMemo = resultMemo;
    }

    public String getResultURL() {
        return resultURL;
    }

    public void setResultURL(String resultURL) {
        this.resultURL = resultURL;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
