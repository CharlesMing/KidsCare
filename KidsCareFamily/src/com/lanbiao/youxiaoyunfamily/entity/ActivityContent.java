package com.lanbiao.youxiaoyunfamily.entity;

public class ActivityContent {
	private String activityimg;
	private String activitytitle;
	private String activitycontents;
	private String activitycreatetime;
	private String activityid;
	private String activityapplytoclass;
	private String activitysendteacher;

	public ActivityContent() {
		super();
	}

	public ActivityContent(String activityimg) {
		super();
		this.activityimg = activityimg;
	}

	public ActivityContent(String activityimg, String activitytitle,
			String activitycontents, String activitycreatetime,
			String activityid) {
		super();
		this.activityimg = activityimg;
		this.activitytitle = activitytitle;
		this.activitycontents = activitycontents;
		this.activitycreatetime = activitycreatetime;
		this.activityid = activityid;
	}

	public String getActivityapplytoclass() {
		return activityapplytoclass;
	}

	public String getActivitysendteacher() {
		return activitysendteacher;
	}

	public void setActivitysendteacher(String activitysendteacher) {
		this.activitysendteacher = activitysendteacher;
	}

	public void setActivityapplytoclass(String activityapplytoclass) {
		this.activityapplytoclass = activityapplytoclass;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getActivityimg() {
		return activityimg;
	}

	public void setActivityimg(String activityimg) {
		this.activityimg = activityimg;
	}

	public String getActivitytitle() {
		return activitytitle;
	}

	public void setActivitytitle(String activitytitle) {
		this.activitytitle = activitytitle;
	}

	public String getActivitycontents() {
		return activitycontents;
	}

	public void setActivitycontents(String activitycontents) {
		this.activitycontents = activitycontents;
	}

	public String getActivitycreatetime() {
		return activitycreatetime;
	}

	public void setActivitycreatetime(String activitycreatetime) {
		this.activitycreatetime = activitycreatetime;
	}

}
