package com.lanbiao.youxiaoyunteacher.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.Studentinfo;
import com.lanbiao.youxiaoyunteacher.http.HttpUtils;

public class HeadService {
	private static final String TAG = "HeadService";
	// private static Studentinfo stuInfo;
	private static String path;

	public static Bitmap getHead(String path_Url) {
		// stuInfo = new Studentinfo();
		try {
			path = path_Url;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				// Log.v(TAG, "bitmap-----" + bitmap);
				return bitmap;
			} else {
				// «Î«Û ß∞‹
				return null;
			}
		} catch (Exception e) {
			Log.v(TAG, "ªÒ»° ß∞‹...");
			e.printStackTrace();
		}

		return null;
	}
}
