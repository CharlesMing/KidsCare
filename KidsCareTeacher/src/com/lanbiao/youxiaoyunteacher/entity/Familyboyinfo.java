package com.lanbiao.youxiaoyunteacher.entity;
import java.sql.Timestamp;

/**
 * Familyboyinfo entity. @author MyEclipse Persistence Tools
 */

public class Familyboyinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String familyboyLogo;
	private String title;
	private Integer classId;
	private String content;
	private Integer teacherId;
	private String createTime;

	// Constructors

	/** default constructor */
	public Familyboyinfo() {
	}

	/** full constructor */
	public Familyboyinfo(String familyboyLogo, String title, Integer classId,
			String content, Integer teacherId, String createTime) {
		this.familyboyLogo = familyboyLogo;
		this.title = title;
		this.classId = classId;
		this.content = content;
		this.teacherId = teacherId;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}