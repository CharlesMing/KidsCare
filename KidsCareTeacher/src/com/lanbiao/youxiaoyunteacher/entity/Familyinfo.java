package com.lanbiao.youxiaoyunteacher.entity;

import java.sql.Timestamp;

/**
 * Familyinfo entity. @author MyEclipse Persistence Tools
 */

public class Familyinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String phone;
	private String password;
	private String remark;
	private Integer childRegion;
	private String createTime;
	private String childId;

	// Constructors

	/** default constructor */
	public Familyinfo() {
	}

	public Familyinfo(String phone, Integer childRegion) {
		this.phone = phone;
		this.childRegion = childRegion;
	}

	/** full constructor */
	public Familyinfo(String name, String phone, String password,
			String remark, Integer childRegion, String createTime,
			String childId) {
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.remark = remark;
		this.childRegion = childRegion;
		this.createTime = createTime;
		this.childId = childId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getChildRegion() {
		return this.childRegion;
	}

	public void setChildRegion(Integer childRegion) {
		this.childRegion = childRegion;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getChildId() {
		return this.childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

}