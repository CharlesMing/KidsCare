package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.ImageAndText;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.AsyncImageLoader.ImageCallback;
import com.lanbiao.youxinteacher.R;

public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {

	private static final String TAG = "ImageAndTextListAdapter";
	private ListView listView;
	private AsyncImageLoader asyncImageLoader;
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	List<ImageAndText> listData;

	public ImageAndTextListAdapter(Activity activity,
			List<ImageAndText> imageAndTexts, ListView listView) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		this.listData = imageAndTexts;
		asyncImageLoader = new AsyncImageLoader();
	}

	/**
	 * 全选
	 */
	public void checkAll() {
		for (int i = 0; i < listData.size(); i++) {
			state.put(i, true);
		}
		notifyDataSetChanged();
	}

	/**
	 * 全不选
	 */
	public void checkNone() {
		for (int i = 0; i < listData.size(); i++) {
			state.remove(i);
		}
		notifyDataSetChanged();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		View rowView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.t_remind_listview_item_layout,
					null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		ImageAndText imageAndText = getItem(position);

		String imageUrl = imageAndText.getImageUrl();// 图片网址
		holder.img = (ImageView) rowView
				.findViewById(R.id.imgv_remind_lv_item_icon_id);
		holder.img.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			holder.img.setImageResource(R.drawable.icon);
		} else {
			holder.img.setImageDrawable(cachedImage);
		}
		holder.tv = (TextView) rowView
				.findViewById(R.id.txtv_remind_lv_item_name_id);
		holder.tv.setText(imageAndText.getName());
		holder.tv_id = (TextView) rowView.findViewById(R.id.tv_id);
		holder.tv_id.setText(imageAndText.getStuId());

		holder.cb = (CheckBox) rowView
				.findViewById(R.id.imgv_remind_lv_item_select_id);
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					state.put(position, isChecked);
				} else {
					state.remove(position);
				}
			}
		});
		holder.cb.setChecked((state.get(position) == null ? false : true));

		return rowView;
	}

	static class ViewHolder {
		TextView tv, tv_id;
		CheckBox cb;
		ImageView img;
		LinearLayout ll_cb;
	}

}