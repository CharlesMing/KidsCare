package com.lanbiao.youxiaoyunfamily.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class LoginService {
	@SuppressWarnings("unused")
	private static final String tag = "LoginService";
	private static Website url;
	private static String path;
	private static Context context;
	public static Map<String, String> map = new HashMap<String, String>();

	public static Context getContext() {
		return context;
	}

	/**
	 * 保存
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveUser(Context context, String username,
			String password) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	/**
	 * 只保存用户
	 * 
	 * @param context
	 * @param username
	 */
	public static void saveUserName(Context context, String username,
			String password) {
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	/**
	 * 返回null为登陆失败
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String login(String username, String password) {
		// 提交数据到服务器
		url = new Website();
		try {
			// 接口
			path = url.getPath() + url.getLogin() + url.getUsername()
					+ username + url.getPassword() + password;
			// Log.v(tag, path);
			URL url = new URL(path);
			map.put("path", path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				// Log.v(tag, "text-=-----" + text);
				return text;
			} else {
				// 请求失败
				map.put("error", "登陆失败");
				return "登陆失败";
			}
		} catch (Exception e) {
			map.put("info", "服务器已断开,请稍后再试");
		}
		return "登陆失败";
	}

}
