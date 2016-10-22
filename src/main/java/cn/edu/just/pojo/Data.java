package cn.edu.just.pojo;

/**
 * 接收JSON 参数
 * {"data":[{"username","123"}],"method":"put"}
 * {"data":[{"id","1"},{"id","2"}],"method":"delete"}
 */

import java.util.List;

public class Data<T>{
    private List<T> data;

    public Data(){
        super();
    }

    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
}