package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxinteacher.R;

public class TbabySelectStuAdapter extends BaseAdapter {

	// 填充数据的list
	private ArrayList<HashMap<String, String>> list;
	// 上下文
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;

	// 构造器
	public TbabySelectStuAdapter(ArrayList<HashMap<String, String>> list,
			Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(R.layout.listviewitem, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.img = (ImageView) convertView.findViewById(R.id.item_iv);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置list中TextView的显示
		holder.tv.setText(list.get(position).get("content").toString());
		// 根据flag来设置checkbox的选中状况
		holder.cb.setChecked(list.get(position).get("flag").equals("true"));
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv;
		public CheckBox cb;
		public ImageView img;
	}
}
