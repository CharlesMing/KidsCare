package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class UpdatePwdService {
	private static final String TAG = "UpdatePwdService";
	private static WebSite webSite;
	private static String path;

	/**
	 * –ﬁ∏ƒ√‹¬Î
	 */
	public static String updateUser(String strusername, String struserpwd,
			String strnewuserpwd, String strrepetition, String stroldpwd) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getUpdateuser()
					+ webSite.getStrusername() + strusername
					+ webSite.getStruserpwd() + struserpwd
					+ webSite.getStrold() + stroldpwd + webSite.getStrusernew()
					+ strnewuserpwd + webSite.getStrrepetition()
					+ strrepetition;
			Log.v(TAG, path);
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
}
