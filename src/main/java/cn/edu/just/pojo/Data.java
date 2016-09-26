package cn.edu.just.pojo;

/**
 * 接收JSON 参数
 * {"data":[{"username","123"}]}
 * {"data":[{"id","1"},{"id","2"}]}
 */

import java.util.List;

public class Data<T>{
    public Data(){
        super();
    }

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}