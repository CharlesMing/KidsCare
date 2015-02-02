package com.lanbiao.youxiaoyunfamily.entity;

public class ChatMsgEntity {
	private String name;// 发送人的名字
	private String date;// 日期
	private String message;// 内容
	private boolean isComMeg = true;//
	private String headurl;
	private String userheadurl;
	private String tname;// 教师名字
	private String tmessage;
	private String tdate;

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTmessage() {
		return tmessage;
	}

	public void setTmessage(String tmessage) {
		this.tmessage = tmessage;
	}

	public String getUserheadurl() {
		return userheadurl;
	}

	public void setUserheadurl(String userheadurl) {
		this.userheadurl = userheadurl;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	public ChatMsgEntity() {
	}

	// public ChatMsgEntity(String name, String date, String text, boolean
	// isComMsg) {
	// super();
	// this.name = name;
	// this.date = date;
	// this.message = text;
	// this.isComMeg = isComMsg;
	// }

	public ChatMsgEntity(String name, String date, String message,
			boolean isComMeg, String headurl, String userheadurl, String tname,
			String tmessage, String tdate) {
		super();
		this.name = name;
		this.date = date;
		this.message = message;
		this.isComMeg = isComMeg;
		this.headurl = headurl;
		this.userheadurl = userheadurl;
		this.tname = tname;
		this.tmessage = tmessage;
		this.tdate = tdate;
	}

}
