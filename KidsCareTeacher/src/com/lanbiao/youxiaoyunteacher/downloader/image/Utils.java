/** 
 * -------------------------------------------------------------
 * Copyright (c) 2011 TCL, All Rights Reserved.
 * ---------------------------------------------------------
 *
 * @author: zhaoyan01/zhaoyan01@tcl.com/2013-2-6
 * @brief: TODO 绫诲姛鑳芥弿杩�
 */

package com.lanbiao.youxiaoyunteacher.downloader.image;

import java.io.File;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Utils {
	public static boolean checkURL(String url) {
		if (url == null) {
			return false;
		}

		url = url.trim();
		if (url.equals("")) {
			return false;
		}

		return true;
	}

	public static String getFileNameByURL(String url) {
		int index = url.lastIndexOf('/');
		if (index != -1) {
			return url.substring(index + 1);
		}

		return "error_name";
	}

	public static boolean isSDReady() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? true : false;
	}

	public static boolean isNetworkReady(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo.State mState = connMgr.getActiveNetworkInfo().getState();
		if (mState.equals(NetworkInfo.State.CONNECTED)) {
			return true;
		}

		return false;
	}

	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}
}
