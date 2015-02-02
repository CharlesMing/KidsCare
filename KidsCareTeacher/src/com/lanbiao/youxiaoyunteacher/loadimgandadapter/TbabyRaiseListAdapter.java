package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.lanbiao.youxiaoyunteacher.entity.BabyStudyRaise;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.AsyncImageLoader.ImageCallback;
import com.lanbiao.youxinteacher.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TbabyRaiseListAdapter extends ArrayAdapter<BabyStudyRaise> {

	private static final String TAG = "TbabyRaiseListAdapter";
	private ListView listView;
	// private AsyncImageLoader asyncImageLoader;
	// public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	List<BabyStudyRaise> listData;

	public TbabyRaiseListAdapter(Activity activity,
			List<BabyStudyRaise> babyStudyRaises, ListView listView) {
		super(activity, 0, babyStudyRaises);
		this.listView = listView;
		this.listData = babyStudyRaises;
		// asyncImageLoader = new AsyncImageLoader();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		View rowView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(
					R.layout.t_baby_raise_listview_items_layout, null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		BabyStudyRaise babyStudyRaise = getItem(position);
		ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
		String imgUrl = babyStudyRaise.getBhead();
		holder.img_id = (ImageView) rowView
				.findViewById(R.id.imgv_t_raise_lv_item_img_id);// 照片

		holder.img_id.setTag(imgUrl);
		imageDownloadTask.execute(imgUrl, holder.img_id);

		holder.tv_title = (TextView) rowView
				.findViewById(R.id.txtv_t_raise_lv_item_title_id);// 标题
		holder.tv_title.setText(babyStudyRaise.getBtitle());

		holder.tv_content = (TextView) rowView
				.findViewById(R.id.txtv_t_raise_lv_item_content_id);// 内容
		holder.tv_content.setText(babyStudyRaise.getBcontent());

		holder.tv_classid = (TextView) rowView
				.findViewById(R.id.txtv_t_raise_lv_item_class_id);// 适用班级
		holder.tv_classid.setText("适合年龄：" + babyStudyRaise.getBclass());

		holder.tv_time = (TextView) rowView
				.findViewById(R.id.txtv_t_raise_lv_item_date_id);// 发布时间

		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date;
			date = format.parse(babyStudyRaise.getBtime());
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			holder.tv_time.setText(format1.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		holder.tv_item_id = (TextView) rowView.findViewById(R.id.tv_item_id);
		holder.tv_item_id.setText(babyStudyRaise.getBcontentid());

		return rowView;
	}

	static class ViewHolder {
		TextView tv_title, tv_content, tv_classid, tv_time, tv_item_id;
		ImageView img_id;
	}

}
