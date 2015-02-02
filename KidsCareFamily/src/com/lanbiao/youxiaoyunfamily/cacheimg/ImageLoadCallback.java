/** 
 * -------------------------------------------------------------
 * Copyright (c) 2011 TCL, All Rights Reserved.
 * ---------------------------------------------------------
 *
 * @author: zhaoyan01/zhaoyan01@tcl.com/2013-2-6
 * @brief: TODO 类功能描�?
 */

package com.lanbiao.youxiaoyunfamily.cacheimg;

import android.graphics.Bitmap;
import android.view.View;

public interface ImageLoadCallback
{
	public void onLoadImageComplete(String url, View view, Bitmap bitmap);
}
