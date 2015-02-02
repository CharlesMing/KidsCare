/** 
 * -------------------------------------------------------------

 * Copyright (c) 2011 TCL, All Rights Reserved.
 * ---------------------------------------------------------
 *
 * @author: zhaoyan01/zhaoyan01@tcl.com/2013-2-6
 * @brief: TODO 绫诲姛鑳芥弿杩�
 */

package com.lanbiao.youxiaoyunfamily.cacheimg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ImageLoader {
	private static final int MSG_FIND_IMAGE = 201;

	private HashMap<String, SoftReference<Bitmap>> mRamCache = new HashMap<String, SoftReference<Bitmap>>();
	private Context mContext;
	private ExecutorService mExecutorSrv;
	private int nThreads = 3;
	private static String mSDCachePath;
	private static final int mReadTimeout = 7 * 1000;
	private static final int mConnTimeout = 5 * 1000;
	private static final int mRefreshTime = 60;

	private static Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FIND_IMAGE:
				onFindImage(msg);
				break;

			default:
				break;
			}
		}

	};

	public ImageLoader(Context context) {
		mContext = context;
		mExecutorSrv = Executors.newFixedThreadPool(nThreads);
		mRamCache.clear();
		mSDCachePath = Environment.getExternalStorageDirectory().getPath()
				+ "/KidsCares/cache"; //$NON-NLS-1$

		if (Utils.isSDReady()) {
			File fp = new File(mSDCachePath);
			if (!fp.exists()) {
				fp.mkdirs();
			}
		}
	}

	public void loadImage(String url, View view, ImageLoadCallback callback) {
		mExecutorSrv.submit(new LoadTask(url, view, callback));
	}

	private Bitmap checkInRam(String url) {
		if (mRamCache.containsKey(url)) {
			Bitmap bmp = mRamCache.get(url).get();
			if (bmp == null) {
				mRamCache.remove(url);
				return null;
			}

			return bmp;
		}
		return null;
	}

	private Bitmap checkInSD(String url) {
		if (!Utils.isSDReady()) {
			return null;
		}

		String fileName = Utils.getFileNameByURL(url);
		String path = mSDCachePath + "/" + fileName; //$NON-NLS-1$
		File fp = new File(path);
		if (fp.exists()) {
			long fileTime = fp.lastModified();
			long curTime = System.currentTimeMillis();
			long gap = Math.abs((curTime - fileTime) / 1000 / 60);
			if (gap > mRefreshTime) {
				DLog.d("delete cache gap time = " + gap); //$NON-NLS-1$
				fp.delete();
			} else {
				Bitmap bmp = BitmapFactory.decodeFile(fp.getPath());
				if (bmp != null) {
					return bmp;
				}
			}
		}

		return null;
	}

	private static void onFindImage(Message msg) {
		if (msg == null) {
			return;
		}
		ImageLoaderInfo info = (ImageLoaderInfo) msg.obj;
		if (info.m_callback != null) {
			info.m_callback.onLoadImageComplete(info.m_url, info.m_view,
					info.m_bmp);
		}
	}

	private class WriteBitmapToFile implements Runnable {
		private String mPath;
		private Bitmap mBmp;
		private FileOutputStream fos;

		public WriteBitmapToFile(String path, Bitmap bmp) {
			mPath = path;
			mBmp = bmp;
		}

		@Override
		public void run() {
			try {
				fos = new FileOutputStream(mPath);
				mBmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class LoadTask implements Runnable {
		private String m_url;
		private View m_view;
		private ImageLoadCallback m_callback;

		public LoadTask(String url, View view, ImageLoadCallback callback) {
			m_url = url;
			m_view = view;
			m_callback = callback;
		}

		@Override
		public void run() {
			if (!Utils.checkURL(m_url)) {
				DLog.d("check url error......"); //$NON-NLS-1$
				return;
			}

			Bitmap mBmp = checkInRam(m_url);
			if (mBmp != null) {
				DLog.d("find bitmap in ram [url = " + m_url + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				Message msg = mHandler.obtainMessage(MSG_FIND_IMAGE,
						new ImageLoaderInfo(m_url, m_view, mBmp, m_callback));
				mHandler.sendMessage(msg);
				return;
			}

			mBmp = checkInSD(m_url);
			if (mBmp != null) {
				if (!mRamCache.containsKey(m_url)) {
					mRamCache.put(m_url, new SoftReference<Bitmap>(mBmp));
				}
				DLog.d("find bitmap in sd card [url = " + m_url + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				Message msg = mHandler.obtainMessage(MSG_FIND_IMAGE,
						new ImageLoaderInfo(m_url, m_view, mBmp, m_callback));
				mHandler.sendMessage(msg);
				return;
			}

			if (!Utils.isNetworkReady(mContext)) {
				DLog.d("no net work......");
				return;
			}

			try {
				URL mURL = new URL(m_url);
				HttpURLConnection mConn = (HttpURLConnection) mURL
						.openConnection();
				mConn.setConnectTimeout(mConnTimeout);
				mConn.setReadTimeout(mReadTimeout);
				mConn.connect();
				if (mConn.getResponseCode() == 200) {
					mBmp = BitmapFactory.decodeStream(mConn.getInputStream());
					if (!mRamCache.containsKey(m_url)) {
						mRamCache.put(m_url, new SoftReference<Bitmap>(mBmp));
					}

					String fileName = Utils.getFileNameByURL(m_url);
					String path = mSDCachePath + "/" + fileName; //$NON-NLS-1$
					new WriteBitmapToFile(path, mBmp).run();

					DLog.d("download bitmap from net [url = " + m_url + "]"); //$NON-NLS-1$ //$NON-NLS-2$
					Message msg = mHandler
							.obtainMessage(MSG_FIND_IMAGE, new ImageLoaderInfo(
									m_url, m_view, mBmp, m_callback));
					mHandler.sendMessage(msg);
					mConn.disconnect();
					return;
				} else {
					DLog.d("response error: get response code = " //$NON-NLS-1$
							+ mConn.getResponseCode());
				}
				mConn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
