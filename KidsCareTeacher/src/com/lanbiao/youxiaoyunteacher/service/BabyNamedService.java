package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class BabyNamedService {
	private static final String TAG = "BabyNamedService";
	private static WebSite url;
	private static String path;
	private static int strtype = 1;

	public static String getStus(String strteacherid) {
		try {
			url = new WebSite();
			path = url.getPath() + url.getStudent() + url.getTeacherid()
					+ strteacherid + url.getType() + strtype;
			Log.v(TAG, path);
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

	public static String stuSignin(String strTeacherid, String strStuid) {
		try {

			Date studt = new Date();
			SimpleDateFormat fdt = new SimpleDateFormat("yyyy-MM-dd");
			String YMD_time = fdt.format(studt);
			SimpleDateFormat fdtHH = new SimpleDateFormat("HH");
			int HH_time = Integer.parseInt(fdtHH.format(studt));
			int type_num = 1;
			if (HH_time > 12) {
				type_num = 2;
			}
			url = new WebSite();
			path = url.getPath() + url.getAddsignin() + url.getStrsignintype()
					+ type_num + url.getTeacherid() + strTeacherid
					+ url.getStrstuid() + strStuid;
			Log.v(TAG, path);
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
