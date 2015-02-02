package com.lanbiao.youxiaoyunfamily.hint;

import android.content.Context;
import android.widget.Toast;

public class ShowToast {

	/**
	 * 提示信息
	 * 
	 * @param msg
	 * @param context
	 * @return
	 */
	public static String getToastInfo(String msg, Context context) {
		// 这样写据说可以解决重叠显示时延迟
		Toast toast = null;
		if (toast == null) {
			toast = Toast.makeText(context.getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
		}
		toast.show();
		return msg;
	}

}
