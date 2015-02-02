package com.lanbiao.youxiaoyunfamily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.SelectPhone;

public class OptionsAdapter extends ArrayAdapter<SelectPhone> {

	@SuppressWarnings("unused")
	private List<SelectPhone> list = new ArrayList<SelectPhone>();
	private Activity activity = null;
	private Handler handler;

	/**
	 * 自定义构造方法
	 * 
	 * @param activity
	 * @param handler
	 * @param list
	 */
	public OptionsAdapter(Activity activity, Handler handler,
			List<SelectPhone> list, ListView lv) {
		super(activity, 0, list);
		this.activity = activity;
		this.handler = handler;
		this.list = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SelectPhone selectPhone = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 下拉项布局
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.option_item, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.btn = (ImageButton) convertView.findViewById(R.id.button1);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String phone = selectPhone.getName();
		holder.textView.setText(phone);

		// 为下拉框选项文字部分设置事件，最终效果是点击将其文字填充到文本框
		holder.textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				Bundle data = new Bundle();
				// 设置选中索引
				data.putInt("selIndex", position);
				msg.setData(data);
				msg.what = 1;
				// 发出消息
				handler.sendMessage(msg);
			}
		});

		// 为下拉框选项删除图标部分设置事件，最终效果是点击将该选项删除
		holder.btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				Bundle data = new Bundle();
				// 设置删除索引
				data.putInt("delIndex", position);
				msg.setData(data);
				msg.what = 2;
				// 发出消息
				handler.sendMessage(msg);
			}
		});

		return convertView;
	}

}

class ViewHolder {
	TextView textView;
	ImageButton btn;
	// ImageView imageView;
}