package com.huan.demomaster.bean;

import java.io.Serializable;
public class Response implements Serializable{

	private boolean IsSuccess;
	private String Data;
	private String description;
	public boolean isIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}
	
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
