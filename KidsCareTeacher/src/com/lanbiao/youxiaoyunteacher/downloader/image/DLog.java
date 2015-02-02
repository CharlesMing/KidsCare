/** 
 * -------------------------------------------------------------
 * Copyright (c) 2011 TCL, All Rights Reserved.
 * ---------------------------------------------------------
 *
 * @author: zhaoyan01/zhaoyan01@tcl.com/2013-2-6
 * @brief: TODO 类功能描�?
 */

package com.lanbiao.youxiaoyunteacher.downloader.image;

import android.util.Log;

public class DLog
{
	public final static boolean LOG_OPEN = true;
	public final static String LOG_TAG = "Rock";

	public static void d(String msg)
	{
		if (LOG_OPEN)
		{
			Log.d(LOG_TAG, msg);
		}
	}
}
