package com.lanbiao.youxiaoyunteacher.service;

import java.net.URL;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;

public class DynamicSend {
	private static WebSite url;
	private static String path;

	public static String sendBabyDynamic(String childId, String base64_logo,
			String logo_name, String Trendsfirstid, String Trendstwoid,
			String Teachremark) {
		url = new WebSite();
		try {
			path = url.getPath() + url.getAddtrends() + url.getChildids()
					+ childId + url.getBase64_logo() + base64_logo
					+ url.getLogo_name() + logo_name + url.getTrendsfirstid()
					+ Trendsfirstid + url.getTrendstwoid() + Trendstwoid
					+ url.getTeachremark() + Teachremark;

			// URL url = new URL(path);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "";

	}

}
