package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class MyStudentService {
	private static final String TAG = "MyStudentService";
	private static WebSite website;
	private static String path;

	public static String myStudentInfo(String classID) {
		website = new WebSite();
		try {
			path = website.getPath() + website.getMystudent()
					+ website.getClassbyid() + classID;
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
			e.printStackTrace();
		}
		return "请求失败";
	}

	/**
	 * 得到学生的家长信息
	 * 
	 * @param strteacherid
	 * @return
	 */
	public static String getstubyfamilycontactinfo(String strteacherid) {
		website = new WebSite();
		try {
			path = website.getPath() + website.getContactfamily()
					+ website.getTeacherid() + strteacherid;
			Log.v(TAG, "website------" + path);
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
				return "请求失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求失败";
	}
}

// try {
// website = new Website();
// path = website.getPath() + website.getQuerytrends()
// + website.getFamilyid() + familyid;
// Log.v(TAG, path);
// URL url = new URL(path);
// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
// conn.setConnectTimeout(5 * 1000);
// conn.setRequestMethod("GET");
// int code = conn.getResponseCode();
// if (code == 200) {
// // 请求成功
// InputStream is = conn.getInputStream();
// String text = HttpUtils.readInputStream(is);
// return text;
// } else {
// // 请求失败
// return "请求失败";
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// return "请求失败";