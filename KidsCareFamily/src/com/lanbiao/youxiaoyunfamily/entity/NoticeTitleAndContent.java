package com.lanbiao.youxiaoyunfamily.entity;

public class NoticeTitleAndContent {
	private String msgtitle;
	private String msgcontent;
	private String msgactivityid;

	public NoticeTitleAndContent() {
		super();
	}

	public NoticeTitleAndContent(String msgtitle, String msgcontent,
			String msgactivityid) {
		super();
		this.msgtitle = msgtitle;
		this.msgcontent = msgcontent;
		this.msgactivityid = msgactivityid;
	}

	public String getMsgtitle() {
		return msgtitle;
	}

	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}

	public String getMsgcontent() {
		return msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}

	public String getMsgactivityid() {
		return msgactivityid;
	}

	public void setMsgactivityid(String msgactivityid) {
		this.msgactivityid = msgactivityid;
	}

}
