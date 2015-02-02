package com.lanbiao.youxiaoyunfamily.entity;

public class InstallInfo {
	private int newVersionCode;
	private String newContent;
	private String showVersionCode;
	private String updateTime;
	private String apkUrl;
	private String apkName;

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public int getNewVersionCode() {
		return newVersionCode;
	}

	public void setNewVersionCode(int newVersionCode) {
		this.newVersionCode = newVersionCode;
	}

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public String getShowVersionCode() {
		return showVersionCode;
	}

	public void setShowVersionCode(String showVersionCode) {
		this.showVersionCode = showVersionCode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

}
