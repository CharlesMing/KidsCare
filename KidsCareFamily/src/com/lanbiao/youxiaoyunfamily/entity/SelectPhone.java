package com.lanbiao.youxiaoyunfamily.entity;

public class SelectPhone {
	private String name;
	private String pwd;

	public SelectPhone(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public SelectPhone() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
