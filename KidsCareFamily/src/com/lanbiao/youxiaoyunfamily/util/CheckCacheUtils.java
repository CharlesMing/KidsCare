package com.lanbiao.youxiaoyunfamily.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.lanbiao.youxiaoyunfamily.cache.ConfigCache;
import com.lanbiao.youxiaoyunfamily.sync.http.AsyncHttpClient;
import com.lanbiao.youxiaoyunfamily.sync.http.AsyncHttpResponseHandler;

public class CheckCacheUtils {
	// 判断是什么网络
	public static int mNetWorkState = NetworkUtils.NETWORN_NONE;
	public static String mSdcardDataDir = null;
	private Context context;
	private String path;

	public CheckCacheUtils(Context context, String path) {
		super();
		this.context = context;
		this.path = path;
	}

	public void initEnv() {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/KidsCares/config/");
			if (!file.exists()) {
				if (file.mkdirs()) {
					mSdcardDataDir = file.getAbsolutePath();
				}
			} else {
				mSdcardDataDir = file.getAbsolutePath();
			}
		}
		// 得到网络类型
		mNetWorkState = NetworkUtils.getNetworkState(context
				.getApplicationContext());
		checkDomain(path, false);
	}

	public void checkDomain(final String domain, final boolean stop) {
		String cacheConfigString = ConfigCache.getUrlCache(domain);
		if (cacheConfigString != null) {
			// updateDomain(cacheConfigString);
			// getBabyDynamic("results", cacheConfigString);
		} else {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(domain, new AsyncHttpResponseHandler() {

				@Override
				public void onStart() {
				}

				@Override
				public void onSuccess(String result) {
					ConfigCache.setUrlCache(result, domain);
					// updateDomain(result);
					// getBabyDynamic("results", result);
				}

				@Override
				public void onFailure(Throwable arg0) {
					if (!stop) {
						checkDomain(path, true);
					}
				}

				@Override
				public void onFinish() {
				}
			});
		}
	}

}
