package com.example.regfood.payload;

public class ResponseData {
    private int status=200;
    private String desc;
    private Object data;
    private boolean isSuccess = true;
    
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getStatus() {
        return status;
    }
    
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
