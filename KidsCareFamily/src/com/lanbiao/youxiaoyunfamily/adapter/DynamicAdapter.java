package com.lanbiao.youxiaoyunfamily.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.activity.ImagePagerActivity;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoadCallback;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoader;
import com.lanbiao.youxiaoyunfamily.cacheimg.ImageLoaderInfo;
import com.lanbiao.youxiaoyunfamily.entity.BabydyNamic;
import com.lanbiao.youxiaoyunfamily.entity.Head;
import com.lanbiao.youxiaoyunfamily.entity.ImageAndText;

public class DynamicAdapter extends ArrayAdapter<ImageAndText> implements
		ImageLoadCallback {

	@SuppressWarnings("unused")
	private static final String TAG = "DynamicAdapter";
	private Context context;
	GridAdapter adapter;

	public Context getContext() {
		return context;
	}

	@SuppressWarnings("unused")
	private List<ImageAndText> listData;
	LayoutInflater inflater = null;
	ImageLoader mLoader;

	// 加载缓存
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ImageLoaderInfo info = (ImageLoaderInfo) msg.obj;
			mLoader.loadImage(info.m_url, info.m_view, info.m_callback);
		}

	};

	public DynamicAdapter(Context context, ListView listView,
			List<ImageAndText> objects) {
		super(context, 0, objects);
		this.context = context;
		this.listData = objects;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageAndText imageAndText = getItem(position);
		View view = null;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView != null) {
			view = convertView;
		} else {
			view = inflater.inflate(R.layout.p_baby_dynamic_item, null);
			view.setTag(holder);
		}
		holder.iv_head = (ImageView) view.findViewById(R.id.iv_babyhead);
		holder.tv_msg = (TextView) view.findViewById(R.id.tv_teahcermsg);
		holder.ll_showImg = (LinearLayout) view
				.findViewById(R.id.ll_showImg_id);
		holder.tv_time = (TextView) view.findViewById(R.id.tv_showtime_id);
		holder.gv_id = (GridView) view.findViewById(R.id.grv_content);
		holder.gv_show_id = (GridView) view.findViewById(R.id.gv_show_id);

		String imageUrl = imageAndText.getHeadUrl();// 图片网址
		String content = imageAndText.getContent();// 内容
		String strTime = imageAndText.getSendtime();// 时间
		final String strStuImageUrl = imageAndText.getDynamicImage();// 生活照
		final String strStuDynamic = imageAndText.getTrendstwo();// 动态

		holder.tv_msg.setText(content);// 内容
		holder.iv_head.setTag(imageUrl);

		/***** ==========--显示头像--START========== *****/
		ImageLoaderInfo info = new ImageLoaderInfo(imageUrl, holder.iv_head,
				null, DynamicAdapter.this);
		if (mHandler.hasMessages(position)) {
			mHandler.removeMessages(position);
		}
		Message msg = mHandler.obtainMessage(position, info);
		mHandler.sendMessageDelayed(msg, 500);
		/***** ==========--显示头像--END========== *****/

		/***** ==========--先将时间转换为指定格式，再显示--START========== *****/
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date;
			date = format.parse(strTime);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			holder.tv_time.setText(format1.format(date));// 时间
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		/***** ==========--先将时间转换为指定格式，再显示--END========== *****/

		/***** ==========--判断是否有图片，如果有就显示没有就隐藏--START========== *****/
		if (!strStuImageUrl.equals("NODATA")) {
			holder.ll_showImg.setVisibility(View.VISIBLE);
		} else {
			holder.ll_showImg.setVisibility(View.GONE);
		}
		/***** ==========--判断是否有图片，如果有就显示没有就隐藏--END========== *****/

		/***** ==========--将生活照添加到Gridview中--START========== *****/
		String[] imgArray = strStuImageUrl.split("==");
		try {
			final List<Head> datas = new ArrayList<Head>();
			for (int i = 0; i < imgArray.length; i++) {
				String[] imgArrsays = imgArray[i].split(",");
				for (int j = 0; j < imgArrsays.length; j++) {
					String imgs = imgArrsays[j];// 数组
					datas.add(new Head(imgs));
				}
			}
			adapter = new GridAdapter(getContext(), datas, holder.gv_id);
			holder.gv_id.setAdapter(adapter);
			// Gridview点击事件
			holder.gv_id.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					String[] img = strStuImageUrl.split("==");

					Intent intent = new Intent(getContext(),
							ImagePagerActivity.class);
					for (int i = 0; i < img.length; i++) {
						String[] imgs = img[i].split(",");
						intent.putExtra("img", imgs);
					}
					intent.putExtra("p", position);
					getContext().startActivity(intent);

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		/***** ==========--将生活照添加到Gridview中--END========== *****/

		/**** =========--宝贝动态--START======== *****/
		try {
			String[] dynamicArray = strStuDynamic.split("==");
			List<BabydyNamic> list_dynamic = new ArrayList<BabydyNamic>();
			for (int i = 0; i < dynamicArray.length; i++) {
				String[] dynamicArrays = dynamicArray[i].split(",");
				for (int j = 0; j < dynamicArrays.length; j++) {
					String dynamic = dynamicArrays[j];
					list_dynamic.add(new BabydyNamic(dynamic));
				}
			}
			ShowDynamicAdapter dynamicAdapter = new ShowDynamicAdapter(
					getContext(), list_dynamic, holder.gv_show_id);
			holder.gv_show_id.setAdapter(dynamicAdapter);
			/**** =========--宝贝动态--END======== *****/
		} catch (Exception e) {
			e.printStackTrace();
		}
		// holder.tv_trendtwo = (TextView) view.findViewById(R.id.tv_trendone);
		// holder.tv_trendtwo.setText(imageAndText.getTrendstwo());

		return view;
	}

	static class ViewHolder {
		LinearLayout ll_showImg;
		ImageView iv_head;
		TextView tv_msg;
		TextView tv_time;
		ImageView iv_stuImage;
		GridView gv_id, gv_show_id;
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