package com.lanbiao.youxiaoyunteacher.entity;

public class MessageInfoImgAndTxt {
	private String region_name;
	private String content;
	private String stulogo;
	private String status;
	private String converid;
	private String msg_time;
	private String famliyid;
	private String teacherid;
	private String tname;
	private String username;
	private String userpwd;

	public MessageInfoImgAndTxt() {
		super();
	}

	public MessageInfoImgAndTxt(String status) {
		super();
		this.status = status;
	}

	public MessageInfoImgAndTxt(String region_name, String content,
			String stulogo, String status, String converid, String msg_time,
			String famliyid, String teacherid, String tname, String username,
			String userpwd) {
		super();
		this.region_name = region_name;
		this.content = content;
		this.stulogo = stulogo;
		this.status = status;
		this.converid = converid;
		this.msg_time = msg_time;
		this.famliyid = famliyid;
		this.teacherid = teacherid;
		this.tname = tname;
		this.username = username;
		this.userpwd = userpwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStulogo() {
		return stulogo;
	}

	public void setStulogo(String stulogo) {
		this.stulogo = stulogo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConverid() {
		return converid;
	}

	public void setConverid(String converid) {
		this.converid = converid;
	}

	public String getMsg_time() {
		return msg_time;
	}

	public void setMsg_time(String msg_time) {
		this.msg_time = msg_time;
	}

	public String getFamliyid() {
		return famliyid;
	}

	public void setFamliyid(String famliyid) {
		this.famliyid = famliyid;
	}

}
