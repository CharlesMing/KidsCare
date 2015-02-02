package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoadCallback;
import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoader;
import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoaderInfo;
import com.lanbiao.youxiaoyunteacher.entity.BabyNamed;
import com.lanbiao.youxinteacher.R;

public class TbabyNamedAdapter extends ArrayAdapter<BabyNamed> implements
		ImageLoadCallback {
	private static final String TAG = "TbabyNamedAdapter";
	private ListView listView;
	List<BabyNamed> listData;
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

	public TbabyNamedAdapter(Context context, List<BabyNamed> babyNameds,
			ListView listView) {
		super(context, 0, babyNameds);
		this.listView = listView;
		this.listData = babyNameds;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Activity activity = (Activity) getContext();
		getCount();
		BabyNamed babyNamed = getItem(position);
		ViewHolder holder = null;
		holder = new ViewHolder();
		// if (rowView == null) {
		// LayoutInflater inflater = activity.getLayoutInflater();
		// rowView = inflater.inflate(R.layout.t_baby_named_item, null);
		// rowView.setTag(holder);
		// } else {
		// holder = (ViewHolder) rowView.getTag();
		// }
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.t_baby_named_item, null);
		}

		ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
		String imageUrl = babyNamed.getImgurl();// Í¼Æ¬ÍøÖ·
		holder.img = (ImageView) convertView.findViewById(R.id.iv_titleimg_id);
		holder.img.setTag(babyNamed.getImgurl());
		// ImageLoaderInfo info = new ImageLoaderInfo(babyNamed.getImgurl(),
		// holder.img, null, TbabyNamedAdapter.this);
		// if (mHandler.hasMessages(position)) {
		// mHandler.removeMessages(position);
		// }
		// Message msg = mHandler.obtainMessage(position, info);
		// mHandler.sendMessageDelayed(msg, 500);

		imageDownloadTask.execute(imageUrl, holder.img);

		holder.tv_id = (TextView) convertView.findViewById(R.id.tv_nameid_id);
		holder.tv_id.setText(babyNamed.getNameid());
		holder.tv = (TextView) convertView.findViewById(R.id.tv_name_id);
		holder.tv.setText(babyNamed.getName());
		holder.tv_vid = (TextView) convertView.findViewById(R.id.tv_vid);
		holder.tv_vid.setText(babyNamed.getSid());

		return convertView;
	}

	static class ViewHolder {
		TextView tv, tv_id, tv_vid;
		CheckBox cb;
		ImageView img;
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
