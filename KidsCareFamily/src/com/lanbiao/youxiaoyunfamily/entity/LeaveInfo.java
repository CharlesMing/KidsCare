package com.lanbiao.youxiaoyunfamily.entity;

public class LeaveInfo {
	private String strtname;
	private String family_time;
	private String student_region;
	private String student_logo;
	private String family_content;
	private String type;
	private String tcontent;
	private String tlogo;
	private String ttime;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LeaveInfo() {
		super();
	}

	public LeaveInfo(String family_time, String student_region,
			String family_content) {
		super();
		this.family_time = family_time;
		this.student_region = student_region;
		this.family_content = family_content;
	}

	public String getTtime() {
		return ttime;
	}

	public void setTtime(String ttime) {
		this.ttime = ttime;
	}

	private String scontentandtypeandtime;
	private String fcontentandtypeandtime;

	public String getStrtname() {
		return strtname;
	}

	public String getTlogo() {
		return tlogo;
	}

	public void setTlogo(String tlogo) {
		this.tlogo = tlogo;
	}

	public void setStrtname(String strtname) {
		this.strtname = strtname;
	}

	public String getFamily_time() {
		return family_time;
	}

	public String getTcontent() {
		return tcontent;
	}

	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	public void setFamily_time(String family_time) {
		this.family_time = family_time;
	}

	public String getStudent_region() {
		return student_region;
	}

	public void setStudent_region(String student_region) {
		this.student_region = student_region;
	}

	public String getStudent_logo() {
		return student_logo;
	}

	public void setStudent_logo(String student_logo) {
		this.student_logo = student_logo;
	}

	public String getFamily_content() {
		return family_content;
	}

	public void setFamily_content(String family_content) {
		this.family_content = family_content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScontentandtypeandtime() {
		return scontentandtypeandtime;
	}

	public void setScontentandtypeandtime(String scontentandtypeandtime) {
		this.scontentandtypeandtime = scontentandtypeandtime;
	}

	public String getFcontentandtypeandtime() {
		return fcontentandtypeandtime;
	}

	public void setFcontentandtypeandtime(String fcontentandtypeandtime) {
		this.fcontentandtypeandtime = fcontentandtypeandtime;
	}

}
