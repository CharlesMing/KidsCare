package com.lanbiao.youxiaoyunteacher.entity;
import java.sql.Timestamp;

/**
 * Homeworkinfo entity. @author MyEclipse Persistence Tools
 */

public class Homeworkinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String homeworkLogo;
	private String homeworkContent;
	private Integer gradeId;
	private Integer classId;
	private String pubishPeople;
	private String publishTime;

	// Constructors

	/** default constructor */
	public Homeworkinfo() {
	}

	/** full constructor */
	public Homeworkinfo(String name, String homeworkLogo,
			String homeworkContent, Integer gradeId, Integer classId,
			String pubishPeople, String publishTime) {
		this.name = name;
		this.homeworkLogo = homeworkLogo;
		this.homeworkContent = homeworkContent;
		this.gradeId = gradeId;
		this.classId = classId;
		this.pubishPeople = pubishPeople;
		this.publishTime = publishTime;
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

	public String getHomeworkLogo() {
		return this.homeworkLogo;
	}

	public void setHomeworkLogo(String homeworkLogo) {
		this.homeworkLogo = homeworkLogo;
	}

	public String getHomeworkContent() {
		return this.homeworkContent;
	}

	public void setHomeworkContent(String homeworkContent) {
		this.homeworkContent = homeworkContent;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getPubishPeople() {
		return this.pubishPeople;
	}

	public void setPubishPeople(String pubishPeople) {
		this.pubishPeople = pubishPeople;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

}