package com.yice.edu.cn.common.pojo.cc.cloudCourse;


import lombok.Data;

@Data
public class CCWebSocketResponse<T> {
    public static final int FAIL = 0;//失败
    public static final int SUCCESS = 1;//成功

    private int type;
    private int code;
    private T data;
    private String msg;

    public CCWebSocketResponse(int type, int code, T data) {
        this.type = type;
        this.code = code;
        this.data = data;
    }

    public CCWebSocketResponse(int type, int code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public CCWebSocketResponse(int type, int code, T data, String msg) {
        this.type = type;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T>CCWebSocketResponse <T>create(int type,int code, T data, String msg){
        return new CCWebSocketResponse(type,code,data,msg);
    }

    public static CCWebSocketResponse createSuccess(int type,String msg){
        return new CCWebSocketResponse(type,SUCCESS,msg);
    }

    public static <T>CCWebSocketResponse <T>createSuccessDataMsg(int type,T data,String msg){
        return new CCWebSocketResponse(type,SUCCESS,msg);
    }

    public static <T>CCWebSocketResponse <T>createSuccessData(int type,T data){
        return new CCWebSocketResponse(type,SUCCESS,data);
    }

    public static CCWebSocketResponse createFail(String msg){
        return new CCWebSocketResponse(-1,FAIL,msg);
    }

}
