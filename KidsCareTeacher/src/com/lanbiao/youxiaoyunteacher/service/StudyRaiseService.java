package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class StudyRaiseService {
	private static final String TAG = "StudyRaiseService";
	private static WebSite webSite;
	private static String path;

	/**
	 * 一级菜单
	 * 
	 * @return
	 */
	public static String getOneMeu() {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getSturaiseonemeu();
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
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

	/**
	 * 二级菜单
	 * 
	 * @return
	 */
	public static String getSecondByIdMeu(String secondId) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getSturaisetwomeu()
					+ webSite.getStrsecondid() + secondId;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
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

	public static String getDetailData(String detailid) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getSturaisedetail()
					+ webSite.getStrdetailid() + detailid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
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

	/**
	 * 育儿中心一级菜单
	 * 
	 * @param strSchoolid
	 * @return
	 */
	public static String getChildCareMenu(String strSchoolid) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getQueryshoppingmenu()
					+ webSite.getStrSchoolid() + strSchoolid;
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
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 育儿中心一级菜单下的列表显示
	 * 
	 * @param secondId
	 * @return
	 */
	public static String getChildCareDetail(String secondId) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getShoppingdetail()
					+ webSite.getSecondmunuid() + secondId;
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
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 育儿中心的详情
	 * 
	 * @param strshoppingid
	 * @return
	 */
	public static String getChildCareByIdDetail(String strshoppingid) {
		try {
			webSite = new WebSite();
			path = webSite.getPath() + webSite.getShoppingbyid()
					+ webSite.getShoppingid() + strshoppingid;
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
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

}
