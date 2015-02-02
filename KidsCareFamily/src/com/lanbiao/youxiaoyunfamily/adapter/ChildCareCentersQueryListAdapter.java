package com.lanbiao.youxiaoyunfamily.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoadCallback;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoader;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoaderInfo;
import com.lanbiao.youxiaoyunfamily.entity.BabyStudyRaise;

public class ChildCareCentersQueryListAdapter extends ArrayAdapter<BabyStudyRaise>
		implements ImageLoadCallback {

	@SuppressWarnings("unused")
	private static final String TAG = "TbabyRaiseListAdapter";
	@SuppressWarnings("unused")
	private ListView listView;
	List<BabyStudyRaise> listData;
	private Context context;
	LayoutInflater inflater = null;
	ImageLoader mLoader;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			ImageLoaderInfo info = (ImageLoaderInfo) msg.obj;
			mLoader.loadImage(info.m_url, info.m_view, info.m_callback);
		}

	};

	public Context getContext() {
		return context;
	}

	public ChildCareCentersQueryListAdapter(Context context,
			List<BabyStudyRaise> babyStudyRaises, ListView listView) {
		super(context, 0, babyStudyRaises);
		this.listView = listView;
		this.listData = babyStudyRaises;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Activity activity = (Activity) getContext();
		BabyStudyRaise babyStudyRaise = getItem(position);
		// View convertView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		// if (convertView == null) {
		// LayoutInflater inflater = activity.getLayoutInflater();
		// convertView = inflater.inflate(
		// R.layout.t_baby_raise_listview_items_layout, null);
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.t_baby_raise_listview_items_layout, null);
		}
		holder.img_id = (ImageView) convertView
				.findViewById(R.id.imgv_t_raise_lv_item_img_id);// 照片
		holder.tv_title = (TextView) convertView
				.findViewById(R.id.txtv_t_raise_lv_item_title_id);// 标题
		holder.tv_content = (TextView) convertView
				.findViewById(R.id.txtv_t_raise_lv_item_content_id);// 内容
		holder.tv_classid = (TextView) convertView
				.findViewById(R.id.txtv_t_raise_lv_item_class_id);// 适用班级
		holder.tv_time = (TextView) convertView
				.findViewById(R.id.txtv_t_raise_lv_item_date_id);// 发布时间
		holder.tv_item_id = (TextView) convertView
				.findViewById(R.id.tv_item_id);

		String imgUrl = babyStudyRaise.getBhead();
		holder.img_id.setTag(imgUrl);
		ImageLoaderInfo info = new ImageLoaderInfo(imgUrl, holder.img_id, null,// 图片双缓存
				ChildCareCentersQueryListAdapter.this);
		if (mHandler.hasMessages(position)) {
			mHandler.removeMessages(position);
		}
		Message msg = mHandler.obtainMessage(position, info);
		mHandler.sendMessageDelayed(msg, 500);
		holder.tv_title.setText(babyStudyRaise.getBtitle());
		holder.tv_content.setText(babyStudyRaise.getBcontent());
		holder.tv_classid.setText("适合年龄：" + babyStudyRaise.getBage());
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
		holder.tv_item_id.setText(babyStudyRaise.getBcontentid());

		return convertView;
	}

	static class ViewHolder {
		TextView tv_title, tv_content, tv_classid, tv_time, tv_item_id;
		ImageView img_id;
	}

	@Override
	public void onLoadImageComplete(String url, View view, Bitmap bitmap) {
		if (view != null) {
			if (view instanceof ImageView) {
				if (bitmap != null) {
					((ImageView) view).setImageBitmap(bitmap);
				}
			}
		}
	}
}