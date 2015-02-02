package com.lanbiao.youxiaoyunfamily.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4702376338422267035L;
	private String username;
	private String userpwd;
	private Integer id;
	public String familyInfo;

	public UserInfo() {
		super();
	}

	public UserInfo(String username, String userpwd, Integer id) {
		super();
		this.username = username;
		this.userpwd = userpwd;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

}
