package com.lanbiao.youxiaoyunfamily.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class PackageUtils {
	private static final String TAG = "PackageUtils";
	private Context context;
	private PackageManager manager;
	private PackageInfo info;

	public PackageUtils(Context context) {
		this.context = context;
		init();
	}

	public void init() {
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到版本号
	 * 
	 * @return
	 */
	public int getVersionCode() {
		return info.versionCode;
	}

	/**
	 * 得到App名称
	 * 
	 * @return
	 */
	public String getVersionName() {
		return info.versionName;
	}

	/**
	 * 检查是否有新版本
	 * 
	 * @param oldVersionCode
	 * @param newVersionCode
	 * @return
	 */
	public boolean isUpgradeVersion(int oldVersionCode, int newVersionCode) {
		// boolean flag = false;
		if (newVersionCode > oldVersionCode) {
			// 下载
			return true;
		} else {
			return false;
		}
	}

	public boolean isUpDate(int oldCode, int newCode) {
		if (newCode > oldCode) {
			return true;
		} else {
			Log.v(TAG, "已是最新版本");
			return false;
		}
	}
}
