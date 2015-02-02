package com.lanbiao.youxiaoyunfamily.entity;

import java.util.List;

public class Famliy {
	private List<FamliyInfo> results;
	private int responseCode;

	public List<FamliyInfo> getResults() {
		return results;
	}

	public void setResults(List<FamliyInfo> results) {
		this.results = results;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

}
