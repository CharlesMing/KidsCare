package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanbiao.youxinteacher.R;

public class ShowToast extends Activity {
	/*
	 * 从布局文件中加载布局并且自定义显示Toast
	 */
	public  void showCustomToast() {
		// 获取LayoutInflater对象，该对象可以将布局文件转换成与之一致的view对象
		LayoutInflater inflater = getLayoutInflater();
		// 将布局文件转换成相应的View对象
		View layout = inflater.inflate(R.layout.custome_toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));
		// 从layout中按照id查找imageView对象
		ImageView imageView = (ImageView) layout.findViewById(R.id.ivForToast);
		// 设置ImageView的图片
		imageView.setBackgroundResource(R.drawable.ic_ok);
		// 实例化一个Toast对象
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(5);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setView(layout);
		toast.show();
	}
}
