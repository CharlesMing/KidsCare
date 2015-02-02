package com.lanbiao.youxiaoyunteacher.entity;

import java.sql.Timestamp;

/**
 * Trendsinfo entity. @author MyEclipse Persistence Tools
 */

public class Trendsinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer trendsPeople;
	private String trendsContent;
	private String trendsLogo;
	private String trendsTime;
	private String teacherEvaluate;
	private Integer studentId;

	// Constructors

	/** default constructor */
	public Trendsinfo() {
	}

	/** full constructor */
	public Trendsinfo(Integer trendsPeople, String trendsContent,
			String trendsLogo, String trendsTime, String teacherEvaluate,
			Integer studentId) {
		this.trendsPeople = trendsPeople;
		this.trendsContent = trendsContent;
		this.trendsLogo = trendsLogo;
		this.trendsTime = trendsTime;
		this.teacherEvaluate = teacherEvaluate;
		this.studentId = studentId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrendsPeople() {
		return this.trendsPeople;
	}

	public void setTrendsPeople(Integer trendsPeople) {
		this.trendsPeople = trendsPeople;
	}

	public String getTrendsContent() {
		return this.trendsContent;
	}

	public void setTrendsContent(String trendsContent) {
		this.trendsContent = trendsContent;
	}

	public String getTrendsLogo() {
		return this.trendsLogo;
	}

	public void setTrendsLogo(String trendsLogo) {
		this.trendsLogo = trendsLogo;
	}

	public String getTrendsTime() {
		return this.trendsTime;
	}

	public void setTrendsTime(String trendsTime) {
		this.trendsTime = trendsTime;
	}

	public String getTeacherEvaluate() {
		return this.teacherEvaluate;
	}

	public void setTeacherEvaluate(String teacherEvaluate) {
		this.teacherEvaluate = teacherEvaluate;
	}

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

}