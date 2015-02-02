package com.lanbiao.youxiaoyunteacher.entity;

public class StudyRaise {
	private String title;
	private String content;
	private String imgurl;
	private String times;
	private String contentid;
	private String applytoclass;

	private String stusecondid;
	private String firstnameandid;
	private String secondnameandid;
	private String nameandid;

	public StudyRaise() {
	}

	public StudyRaise(String title, String content, String imgurl,
			String times, String contentid, String applytoclass) {
		this.title = title;
		this.content = content;
		this.imgurl = imgurl;
		this.times = times;
		this.contentid = contentid;
		this.applytoclass = applytoclass;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getSecondnameandid() {
		return secondnameandid;
	}

	public String getApplytoclass() {
		return applytoclass;
	}

	public void setApplytoclass(String applytoclass) {
		this.applytoclass = applytoclass;
	}

	public void setSecondnameandid(String secondnameandid) {
		this.secondnameandid = secondnameandid;
	}

	public String getFirstnameandid() {
		return firstnameandid;
	}

	public void setFirstnameandid(String firstnameandid) {
		this.firstnameandid = firstnameandid;
	}

	public String getNameandid() {
		return nameandid;
	}

	public void setNameandid(String nameandid) {
		this.nameandid = nameandid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStusecondid() {
		return stusecondid;
	}

	public void setStusecondid(String stusecondid) {
		this.stusecondid = stusecondid;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

}
