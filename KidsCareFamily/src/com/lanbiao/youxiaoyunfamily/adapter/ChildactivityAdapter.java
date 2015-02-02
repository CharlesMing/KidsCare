package com.lanbiao.youxiaoyunfamily.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoadCallback;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoader;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoaderInfo;
import com.lanbiao.youxiaoyunfamily.entity.ActivityContent;

public class ChildactivityAdapter extends ArrayAdapter<ActivityContent>
		implements ImageLoadCallback {

	private static final String TAG = "ImageAndTextListAdapter";
	List<ActivityContent> listData;
	private Context context;

	public Context getContext() {
		return context;
	}

	LayoutInflater inflater = null;
	ImageLoader mLoader;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			ImageLoaderInfo info = (ImageLoaderInfo) msg.obj;
			mLoader.loadImage(info.m_url, info.m_view, info.m_callback);
		}

	};

	public ChildactivityAdapter(Context context,
			List<ActivityContent> imageAndTexts) {
		super(context, 0, imageAndTexts);
		this.listData = imageAndTexts;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ActivityContent activityImgAndTxt = getItem(position);
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.p_baby_activity_lv_item_layout, null);
		}

		String imageUrl = activityImgAndTxt.getActivityimg();// 图片网址
		holder.iv_img = (ImageView) convertView
				.findViewById(R.id.imgv_lv_item_icon_id);// 活动图片

		ImageLoaderInfo info = new ImageLoaderInfo(imageUrl, holder.iv_img,
				null, ChildactivityAdapter.this);
		if (mHandler.hasMessages(position)) {
			Log.v(TAG, "remove message at " + position);
			mHandler.removeMessages(position);
		}
		Message msg = mHandler.obtainMessage(position, info);
		mHandler.sendMessageDelayed(msg, 500);

		holder.tv_title = (TextView) convertView
				.findViewById(R.id.txtv_pba_lv_item_title_id);// 标题
		holder.tv_title.setText(activityImgAndTxt.getActivitytitle());

		holder.tv_time = (TextView) convertView
				.findViewById(R.id.txtv_pba_lv_item_date_id);// 时间
		holder.tv_time.setText(activityImgAndTxt.getActivitycreatetime());

		holder.tv_content = (TextView) convertView
				.findViewById(R.id.txtv_pba_lv_item_content_id);// 内容
		holder.tv_content.setText(activityImgAndTxt.getActivitycontents());

		holder.tv_activityid = (TextView) convertView
				.findViewById(R.id.tv_activity_id);// 活动id(隐藏)
		holder.tv_activityid.setText(activityImgAndTxt.getActivityid());

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_img;
		TextView tv_title, tv_content, tv_time, tv_activityid;
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