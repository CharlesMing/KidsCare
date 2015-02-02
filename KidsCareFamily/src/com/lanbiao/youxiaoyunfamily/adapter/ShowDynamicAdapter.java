package com.lanbiao.youxiaoyunfamily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.BabydyNamic;

public class ShowDynamicAdapter extends ArrayAdapter<BabydyNamic> {
	@SuppressWarnings("unused")
	private List<BabydyNamic> list_datas = new ArrayList<BabydyNamic>();
	private Context context;
	private LayoutInflater inflater = null;

	public Context getContext() {
		return context;
	}

	public ShowDynamicAdapter(Context context, List<BabydyNamic> objects,
			GridView gridView) {
		super(context, 0, objects);
		this.list_datas = objects;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BabydyNamic babydyNamic = getItem(position);
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.p_baby_dynamic_gridview_item, null);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_show = (TextView) convertView.findViewById(R.id.tv_show_id);
		if (position == 0) {
			holder.tv_show.setBackgroundColor(Color.RED);
		} else if (position == 1) {
			holder.tv_show.setBackgroundColor(Color.GREEN);
		} else if (position == 2) {
			holder.tv_show.setBackgroundColor(Color.GRAY);
		} else if (position == 3) {
			holder.tv_show.setBackgroundColor(Color.YELLOW);
		} else if (position == 4) {
			holder.tv_show.setBackgroundColor(Color.LTGRAY);
		}

		holder.tv_show.setText(babydyNamic.getShow_dynamic());
		return convertView;
	}

	static class ViewHolder {
		TextView tv_show;
	}

}
