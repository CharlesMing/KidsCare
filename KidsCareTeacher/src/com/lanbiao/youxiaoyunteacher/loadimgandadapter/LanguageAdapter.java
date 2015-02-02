package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.lanbiao.youxinteacher.R;

public class LanguageAdapter extends BaseAdapter {
	private Context mContext;
	// private String[] mListArrays;
	private List<String> list;
	private LayoutInflater mInflater;
	/* 用于存储CheckBox选中状态 */
	public Map<Integer, Boolean> mCBFlag = null;

	public LanguageAdapter(Context c, List<String> arrays) {
		this.mContext = c;
		this.list = arrays;
		mInflater = LayoutInflater.from(mContext);
		mCBFlag = new HashMap<Integer, Boolean>();
		init();
	}

	void init() {
		for (int i = 0; i < list.size(); i++) {
			mCBFlag.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listviewitem, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			holder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.item_cb);
			holder.img = (ImageView) convertView.findViewById(R.id.item_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							mCBFlag.put(position, true);
						} else {
							mCBFlag.put(position, false);
						}
					}
				});
		holder.mCheckBox.setChecked(mCBFlag.get(position));
		holder.tv.setText(list.get(position));

		return convertView;
	}

	public final class ViewHolder {
		public CheckBox mCheckBox;
		// public TextView mTextView;
		public TextView tv;
		public ImageView img;
	}
}
