package com.lanbiao.youxiaoyunfamily.entity;

/**
 * Familyboyinfo entity. @author MyEclipse Persistence Tools
 */

public class Familyboyinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String familyboyLogo;
	private String title;
	private String classId;
	private String content;
	private String teacherId;
	private String createTime;
	private String babyname;
	private String birthday;
	private String familyid;
	private String StudentId;
	private String schoolname;
	private String schoolId;
	private String type;
	private String moring_signin;
	private String after_signin;

	// Constructors

	public String getMoring_signin() {
		return moring_signin;
	}

	public void setMoring_signin(String moring_signin) {
		this.moring_signin = moring_signin;
	}

	public String getAfter_signin() {
		return after_signin;
	}

	public void setAfter_signin(String after_signin) {
		this.after_signin = after_signin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getStudentId() {
		return StudentId;
	}

	public void setStudentId(String studentId) {
		StudentId = studentId;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	/** default constructor */
	public Familyboyinfo() {
	}

	/** full constructor */
	public Familyboyinfo(String familyboyLogo, String title, String classId,
			String content, String teacherId, String createTime) {
		this.familyboyLogo = familyboyLogo;
		this.title = title;
		this.classId = classId;
		this.content = content;
		this.teacherId = teacherId;
		this.createTime = createTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamilyboyLogo() {
		return this.familyboyLogo;
	}

	public void setFamilyboyLogo(String familyboyLogo) {
		this.familyboyLogo = familyboyLogo;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}