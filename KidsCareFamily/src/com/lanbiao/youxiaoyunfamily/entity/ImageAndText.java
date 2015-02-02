package com.lanbiao.youxiaoyunfamily.entity;

public class ImageAndText {
	private String headUrl;
	private String content;
	private String sendtime;
	private String dynamicImage;
	private String trendsone;
	private String trendstwo;
	private String trendsthree;
	private String threndsfour;
	private String threndsfive;
	private String threndssix;

	/**
	 * 头像 内容 时间 生活照 状态1、2
	 * 
	 * @param headUrl
	 * @param content
	 * @param sendtime
	 * @param dynamicImage
	 * @param trendsone
	 * @param trendstwo
	 */
	public ImageAndText(String headUrl, String content, String sendtime,
			String dynamicImage, String trendsone, String trendstwo) {
		super();
		this.headUrl = headUrl;
		this.content = content;
		this.sendtime = sendtime;
		this.dynamicImage = dynamicImage;
		this.trendsone = trendsone;
		this.trendstwo = trendstwo;
	}

	/**
	 * 头像 内容 时间
	 * 
	 * @param headUrl
	 * @param content
	 * @param sendtime
	 */
	public ImageAndText(String headUrl, String content, String sendtime) {
		super();
		this.headUrl = headUrl;
		this.content = content;
		this.sendtime = sendtime;
	}

	public ImageAndText(String headUrl) {
		super();
		this.headUrl = headUrl;
	}

	/**
	 * 头像 内容 时间 生活照
	 * 
	 * @param headUrl
	 * @param content
	 * @param sendtime
	 * @param dynamicImage
	 */
	public ImageAndText(String headUrl, String content, String sendtime,
			String dynamicImage) {
		super();
		this.headUrl = headUrl;
		this.content = content;
		this.sendtime = sendtime;
		this.dynamicImage = dynamicImage;
	}

	public ImageAndText(String headUrl, String content, String sendtime,
			String dynamicImage, String trendstwo) {
		super();
		this.headUrl = headUrl;
		this.content = content;
		this.sendtime = sendtime;
		this.dynamicImage = dynamicImage;
		this.trendstwo = trendstwo;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getDynamicImage() {
		return dynamicImage;
	}

	public void setDynamicImage(String dynamicImage) {
		this.dynamicImage = dynamicImage;
	}

	public String getTrendsone() {
		return trendsone;
	}

	public void setTrendsone(String trendsone) {
		this.trendsone = trendsone;
	}

	public String getTrendstwo() {
		return trendstwo;
	}

	public void setTrendstwo(String trendstwo) {
		this.trendstwo = trendstwo;
	}

	public String getTrendsthree() {
		return trendsthree;
	}

	public void setTrendsthree(String trendsthree) {
		this.trendsthree = trendsthree;
	}

	public String getThrendsfour() {
		return threndsfour;
	}

	public void setThrendsfour(String threndsfour) {
		this.threndsfour = threndsfour;
	}

	public String getThrendsfive() {
		return threndsfive;
	}

	public void setThrendsfive(String threndsfive) {
		this.threndsfive = threndsfive;
	}

	public String getThrendssix() {
		return threndssix;
	}

	public void setThrendssix(String threndssix) {
		this.threndssix = threndssix;
	}

}