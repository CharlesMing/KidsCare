package com.lanbiao.youxiaoyunteacher.entity;

public class MessageInfo {
	private String content;
	private String sturegion;
	private String stulogo;
	private String status;
	private String conversation_id;
	private String msgtime;
	private String familyid;
	private String Msgs;

	public String getMsgs() {
		return Msgs;
	}

	public void setMsgs(String msgs) {
		Msgs = msgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSturegion() {
		return sturegion;
	}

	public void setSturegion(String sturegion) {
		this.sturegion = sturegion;
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

	public String getConversation_id() {
		return conversation_id;
	}

	public void setConversation_id(String conversation_id) {
		this.conversation_id = conversation_id;
	}

	public String getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

}
