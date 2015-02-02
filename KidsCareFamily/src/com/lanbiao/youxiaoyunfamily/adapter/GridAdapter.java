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
import android.widget.GridView;
import android.widget.ImageView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoadCallback;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoader;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoaderInfo;
import com.lanbiao.youxiaoyunfamily.entity.Head;

public class GridAdapter extends ArrayAdapter<Head> implements
		ImageLoadCallback {
	private static final String TAG = "GridAdapter";
	private Context context;
	@SuppressWarnings("unused")
	private List<Head> listData;
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

	public GridAdapter(Context context, List<Head> list, GridView gridView) {
		super(context, 0, list);
		this.context = context;
		this.listData = list;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);

	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.v(TAG, "postion--------------" + position);
		Head head = getItem(position);
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_item_img, null);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv = (ImageView) convertView.findViewById(R.id.iv_img_id);
		String strStuImageUrl = head.getHeadurl();
		holder.iv.setTag(strStuImageUrl);

		ImageLoaderInfo info = new ImageLoaderInfo(strStuImageUrl, holder.iv,
				null, GridAdapter.this);
		if (mHandler.hasMessages(position)) {
			mHandler.removeMessages(position);
		}
		Message msg = mHandler.obtainMessage(position, info);
		mHandler.sendMessageDelayed(msg, 500);

		return convertView;
	}

	static class ViewHolder {
		ImageView iv;
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