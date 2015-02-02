package com.lanbiao.youxiaoyunteacher.entity;

public class BabyNamed {
	private String sid;
	private String nameid;
	private String imgurl;
	private String name;

	public BabyNamed(String sid, String nameid, String imgurl, String name) {
		super();
		this.sid = sid;
		this.nameid = nameid;
		this.imgurl = imgurl;
		this.name = name;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getNameid() {
		return nameid;
	}

	public void setNameid(String nameid) {
		this.nameid = nameid;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
