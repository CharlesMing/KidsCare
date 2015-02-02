package com.lanbiao.youxiaoyunteacher.entity;

public class ImageAndText {
	private String imageUrl;
	private String text;
	private String phone;
	private String stuId;
	private String name;
	private String username;
	private String pwd;

	public ImageAndText() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageAndText(String imageUrl, String stuId, String name) {
		this.imageUrl = imageUrl;
		this.stuId = stuId;
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public ImageAndText(String imageUrl, String stuId, String name,
			String username, String pwd) {
		super();
		this.imageUrl = imageUrl;
		this.stuId = stuId;
		this.name = name;
		this.username = username;
		this.pwd = pwd;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getStuId() {
		return stuId;
	}

	public ImageAndText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getPhone() {
		return phone;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getText() {
		return text;
	}
}