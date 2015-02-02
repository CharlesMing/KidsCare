package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class CompanyService {
	private static final String TAG = "BabyDynamicService";
	private static WebSite url;
	private static String path;

	public static String getCompanyInfo() {
		try {
			url = new WebSite();
			path = url.getPath() + url.getQuerycompany();
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				// «Î«Û≥…π¶
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "«Î«Û ß∞‹";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "«Î«Û ß∞‹";
	}

	public static String sendAdvice(String strtype, String strteacherid,
			String strphone, String remark) {
		try {
			url = new WebSite();
			path = url.getPath() + url.getAddsuggestion() + url.getStrtype()
					+ strtype + url.getStrphone() + strphone
					+ url.getStrremark() + remark + url.getTeacherid()
					+ strteacherid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "∑¢ÀÕ ß∞‹!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "’à«Û ßî°";
	}
}
