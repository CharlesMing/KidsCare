package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.WebSite;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class MsgServcie {
	private static final String TAG = "MsgServcie";
	private static WebSite webSite;
	private static String path;

	public static String getReplyList(String strteacherid) {
		try {

			webSite = new WebSite();
			path = webSite.getPath() + webSite.getReplylist()
					+ webSite.getStrtid() + strteacherid;
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
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 得到指定家长的留言信息
	 * 
	 * @param strfid
	 * @param strtid
	 * @return
	 */
	public static String getFamilyMsgInfo(String strfid, String strtid) {
		try {

			webSite = new WebSite();
			path = webSite.getPath() + webSite.getMsg()
					+ webSite.getStrfamilyid() + strfid + webSite.getStrtid()
					+ strtid;
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
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 回复家长留言
	 * 
	 * @param strconverid
	 * @param strtid
	 * @param strcontent
	 * @return
	 */
	public static String sendMsg(String strconverid, String strtid,
			String strcontent) {
		try {

			webSite = new WebSite();
			path = webSite.getPath() + webSite.getReply()
					+ webSite.getStrcontent() + strcontent
					+ webSite.getStrtid() + strtid + webSite.getStrconverid()
					+ strconverid;

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
