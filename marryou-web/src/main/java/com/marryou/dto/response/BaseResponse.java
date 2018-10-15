package com.marryou.dto.response;

import com.marryou.utils.Constants;

import java.io.Serializable;

/**
 * Created by linhy on 2018/6/3.
 */
public class BaseResponse<T> implements Serializable {
	private static final long serialVersionUID = 782105934872108403L;

	public static Integer CODE_SUCCESS = Constants.SUCCESS;

	public static Integer CODE_FAILED = Constants.FAILED;

	private T data;

	private Integer code;

	private String msg;

	private boolean isSuccess;

	public BaseResponse() {
	}

	public BaseResponse(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public BaseResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return getCode().equals(CODE_SUCCESS) ? true : false;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}
}
