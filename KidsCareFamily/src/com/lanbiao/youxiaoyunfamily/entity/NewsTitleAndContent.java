package com.lanbiao.youxiaoyunfamily.entity;

public class NewsTitleAndContent {
	private String title;
	private String content;
	private String newid;

	public NewsTitleAndContent() {
	}

	public NewsTitleAndContent(String title, String content, String newid) {
		super();
		this.title = title;
		this.content = content;
		this.newid = newid;
	}

	public String getNewid() {
		return newid;
	}

	public void setNewid(String newid) {
		this.newid = newid;
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

}
