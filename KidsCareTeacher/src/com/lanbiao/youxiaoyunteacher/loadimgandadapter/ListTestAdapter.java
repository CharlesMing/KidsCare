package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.entity.TestListphone;
import com.lanbiao.youxinteacher.R;

public class ListTestAdapter extends ArrayAdapter<TestListphone> {
	private static final String TAG = "ImageAndTextListAdapter";
	private ListView listView;
	List<TestListphone> listData;

	public ListTestAdapter(Activity activity,
			List<TestListphone> imageAndTexts, ListView listView) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		this.listData = imageAndTexts;
	}

	public View getView(final int position, final View convertView,
			ViewGroup parent) {
		final Activity activity = (Activity) getContext();
		View rowView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item, null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		TestListphone phone = getItem(position);

		holder.tv_phone = (TextView) rowView.findViewById(R.id.tv_no);// ºÅÂë
		holder.tv_phone.setText(phone.getPhoneno());

		holder.tv_guanxi = (TextView) rowView.findViewById(R.id.tv_guanxi);// id
		holder.tv_guanxi.setText(phone.getGuanxi());

		return rowView;
	}

	static class ViewHolder {
		TextView tv_phone, tv_guanxi;
	}

}