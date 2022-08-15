package com.yice.edu.cn.common.pojo.ts.jMessage;

public class IMReturnResult<T> {
	
	private String code;
	
	private String msg;
	
	private Boolean success;
	
	private T data;
	
	public IMReturnResult(T data) {
		this.code = "0";
		this.success=true;
		this.data = data;
	}
	
	public IMReturnResult(Boolean success,String code,String msg) {
		this.code = code;
		this.success=success;
		this.msg = msg;
	}
	public IMReturnResult() {
		this.code="0";
		this.success = true;
	}
	public IMReturnResult(String code,String msg) {
		this.code = code;
		this.success=false;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
