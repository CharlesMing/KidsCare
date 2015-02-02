/** 
 * -------------------------------------------------------------
 * Copyright (c) 2011 TCL, All Rights Reserved.
 * ---------------------------------------------------------
 *
 * @author: zhaoyan01/zhaoyan01@tcl.com/2013-2-6
 * @brief: TODO 绫诲姛鑳芥弿杩�
 */

package com.lanbiao.youxiaoyunteacher.downloader.image;

import android.graphics.Bitmap;
import android.view.View;

public class ImageLoaderInfo {
	public String m_url;
	public View m_view;
	public Bitmap m_bmp;
	public ImageLoadCallback m_callback;

	public ImageLoaderInfo(String url, View view, Bitmap bmp,
			ImageLoadCallback callback) {
		m_url = url;
		m_view = view;
		m_bmp = bmp;
		m_callback = callback;
	}
}
