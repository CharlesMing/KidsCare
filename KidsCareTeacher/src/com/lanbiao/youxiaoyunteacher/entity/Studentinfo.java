package com.lanbiao.youxiaoyunteacher.entity;

import java.sql.Timestamp;

import android.graphics.Bitmap;

/**
 * Studentinfo entity. @author MyEclipse Persistence Tools
 */

public class Studentinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String sid;
	private String name;
	private String birthtime;
	private Integer sex;
	private String classname;
	private String head;
	private String remark;
	private String classId;
	private String address;
	private Integer familyId;
	private String createTime;
	private String fphone;
	private String childeIdandphone;

	public String getChildeIdandphone() {
		return childeIdandphone;
	}

	public void setChildeIdandphone(String childeIdandphone) {
		this.childeIdandphone = childeIdandphone;
	}

	// Constructors
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	/** default constructor */
	public Studentinfo() {
	}

	public String getId() {
		return id;
	}

	public String getClassname() {
		return classname;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	/** full constructor */
	public Studentinfo(String name, String birthtime, Integer sex, String head,
			String remark, String classId, String address, Integer familyId,
			String createTime) {
		this.name = name;
		this.birthtime = birthtime;
		this.sex = sex;
		this.head = head;
		this.remark = remark;
		this.classId = classId;
		this.address = address;
		this.familyId = familyId;
		this.createTime = createTime;
	}

	// Property accessors

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthtime() {
		return this.birthtime;
	}

	public void setBirthtime(String birthtime) {
		this.birthtime = birthtime;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}