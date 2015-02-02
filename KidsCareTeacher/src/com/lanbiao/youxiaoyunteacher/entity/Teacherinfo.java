package com.lanbiao.youxiaoyunteacher.entity;

import java.sql.Timestamp;

/**
 * Teacherinfo entity. @author MyEclipse Persistence Tools
 */

public class Teacherinfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7034587806222822757L;
	private String id;
	private String teacherName;
	private String phone;
	private Integer sex;
	private String password;
	private String address;
	private String gradeId;
	private String classId;
	private String remark;
	private Timestamp createTime;
	private String schoolId;
	private String type;

	// Constructors

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** default constructor */
	public Teacherinfo() {
	}

	/** full constructor */
	public Teacherinfo(String teacherName, String phone, Integer sex,
			String password, String address, String gradeId, String classId,
			String remark, Timestamp createTime, String schoolId) {
		this.teacherName = teacherName;
		this.phone = phone;
		this.sex = sex;
		this.password = password;
		this.address = address;
		this.gradeId = gradeId;
		this.classId = classId;
		this.remark = remark;
		this.createTime = createTime;
		this.schoolId = schoolId;
	}

	// Property accessors

	public String getTeacherName() {
		return this.teacherName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

}