package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class DynamicSelectService {
	private static final String tag = "DynamicSelectService";
	private static WebSite url;
	private static String path;

	public static String Dynamic(String shoolId) {
		url = new WebSite();
		try {
			path = url.getPath() + url.getTrendsmenu() + url.getSchoolid()
					+ shoolId;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
		}
		return null;
	}
}
