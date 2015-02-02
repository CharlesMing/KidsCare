package com.lanbiao.youxiaoyunfamily.prompt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class ShowDialogInfo extends Activity {
	private static Context context;

	public static Context getContext() {
		return context;
	}

	/**
	 * 暂无数据提示框
	 * 
	 * @param msg
	 */
	public void showDialogInfo(String msg) {
		Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle("提示");
		dialog.setMessage(msg);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 销毁当前的activity
				finish();
			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 提示暂无数据
	 */
	public DialogInterface.OnClickListener noDataListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// 销毁当前的activity
			finish();
		}
	};
}
