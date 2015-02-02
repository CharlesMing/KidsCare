package com.lanbiao.youxiaoyunfamily.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtils {
	private static final String TAG = "HttpUtils";

	/**
	 * 把输入流的内容转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String readInputStream(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			// 试着解析result里面的字符串
			String temp = new String(result);
			return temp;
		} catch (Exception e) {
			return "获取失败";
		}
	}

	/**
	 * 定义url,并返回数据给客户端
	 * 
	 * @param url_path
	 * @return
	 */
	public static String getJsonContent(String url_path) {
		try {
			Log.v(TAG, "---" + url_path);
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(5 * 1000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			int code = connection.getResponseCode();
			if (code == 200) {
				// 表示连接成功
				return changeInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			Log.v(TAG, "连接超时...");

			e.printStackTrace();
		}
		return url_path;
	}

	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);// 从0开始
			}
			jsonString = new String(outputStream.toByteArray());// 构建一个字符串
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			Log.v(TAG, "获取失败...");
			// e.printStackTrace();
		}
		return jsonString;
	}
}
