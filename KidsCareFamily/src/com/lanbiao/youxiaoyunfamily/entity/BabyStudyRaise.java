package com.lanbiao.youxiaoyunfamily.entity;

public class BabyStudyRaise {
	private String bhead;
	private String btitle;
	private String bcontent;
	private String bclass;
	private String btime;
	private String bcontentid;
	private String bage;

	public BabyStudyRaise() {
	}

	public BabyStudyRaise(String bhead, String btitle, String bcontent,
			String btime, String bcontentid, String bage) {
		super();
		this.bhead = bhead;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.btime = btime;
		this.bcontentid = bcontentid;
		this.bage = bage;
	}

	public String getBcontentid() {
		return bcontentid;
	}

	public void setBcontentid(String bcontentid) {
		this.bcontentid = bcontentid;
	}

	public String getBhead() {
		return bhead;
	}

	public void setBhead(String bhead) {
		this.bhead = bhead;
	}

	public String getBage() {
		return bage;
	}

	public void setBage(String bage) {
		this.bage = bage;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public String getBclass() {
		return bclass;
	}

	public void setBclass(String bclass) {
		this.bclass = bclass;
	}

	public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

}
