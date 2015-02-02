package com.lanbiao.youxiaoyunteacher.entity;

import java.sql.Timestamp;

/**
 * Classinfo entity. @author MyEclipse Persistence Tools
 */

public class Classinfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7387861986948766385L;

	private String id;
	private String classname;
	private String createTime;
	private String remark;
	private Integer gradeId;
	private Integer sort;
	private String classid;
	private String shoolId;
	private String teachername;
	private String type;
	private String schoolname;
	private String phone;
	private String password;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getClassid() {
		return classid;
	}

	public String getShoolId() {
		return shoolId;
	}

	public void setShoolId(String shoolId) {
		this.shoolId = shoolId;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	/** default constructor */
	public Classinfo() {
	}

	/** full constructor */
	public Classinfo(String classname, String createTime, String remark,
			Integer gradeId, Integer sort) {
		this.classname = classname;
		this.createTime = createTime;
		this.remark = remark;
		this.gradeId = gradeId;
		this.sort = sort;
	}

	// Property accessors

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}