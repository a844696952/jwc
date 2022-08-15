package com.yice.edu.cn.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJson {
    private Result result;
    private Object data;
    private Object data2;
    private Object data3;
    private Object data4;
    private Object data5;
    private Long totalCount;//总的数据数量
    private Long weight;//最大的权重值

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseJson() {
        this.result = new Result();
    }

    public ResponseJson(Object data) {
        this.result = new Result();
        this.data = data;
    }
    public ResponseJson(Object data, Long totalCount) {
        this.result = new Result();
        this.data = data;
        this.totalCount=totalCount;
    }
    public ResponseJson(Object data, Object data2, Long totalCount) {
        this.result = new Result();
        this.data = data;
        this.data2=data2;
        this.totalCount=totalCount;
    }
    public ResponseJson(Object data, Object data2) {
        this.result = new Result();
        this.data = data;
        this.data2=data2;
    }

    public ResponseJson(Object data, Object data2,Object data3,Object data4) {
        this.result = new Result();
        this.data = data;
        this.data2=data2;
        this.data3=data3;
        this.data4=data4;
    }

    public ResponseJson(Object data, Object data2,Object data3,Object data4,Object data5) {
        this.result = new Result();
        this.data = data;
        this.data2=data2;
        this.data3=data3;
        this.data4=data4;
        this.data5=data5;
    }

    public ResponseJson(Object data, Object data2,Object data3) {
        this.result = new Result();
        this.data = data;
        this.data2=data2;
        this.data3=data3;
    }

    public ResponseJson(Object data, Long totalCount,Object data2) {
        this.result = new Result();
        this.data = data;
        this.totalCount=totalCount;
        this.data2=data2;
    }
    public ResponseJson(Object data,Long totalCount,Long weight){
        this.result = new Result();
        this.data = data;
        this.totalCount = totalCount;
        this.weight = weight;
    }
    public ResponseJson(boolean success,String msg) {
        this.result = new Result();
        this.result.setSuccess(success);
        if(success && msg == null){
            this.result.setMsg("success");
        }else{
            this.result.setMsg(msg);
        }
    }

    public ResponseJson(boolean success,Integer code){
        this.result = new Result();
        this.result.setSuccess(success);
        this.result.setCode(code);
    }
    public ResponseJson(boolean success,int code,String msg){
        this.result = new Result();
        this.result.success=success;
        this.result.setCode(code);
        this.result.setMsg(msg);
    }
    public ResponseJson(boolean success,int code){
        this.result = new Result();
        this.result.setSuccess(success);
        this.result.setCode(code);
    }

    public Object getData3() {
        return data3;
    }

    public Object getData4() {
        return data4;
    }
    public Object getData5() { return data5; }
    public void setData3(Object data3) {
        this.data3 = data3;
    }
    public void setData4(Object data4) {
        this.data4 = data4;
    }
    public void setData5(Object data5) { this.data5 = data5; }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
    }

    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight){
        this.weight = weight;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {
        private boolean success=true;
        private String msg;
        private Integer code;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
