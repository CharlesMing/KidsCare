package com.lanbiao.youxiaoyunfamily.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.NewsTitleAndContent;

public class NewsListAdapter extends ArrayAdapter<NewsTitleAndContent> {
	@SuppressWarnings("unused")
	private static final String TAG = "ImageAndTextListAdapter";
	@SuppressWarnings("unused")
	private ListView listView;
	List<NewsTitleAndContent> listData;

	public NewsListAdapter(Activity activity,
			List<NewsTitleAndContent> imageAndTexts, ListView listView) {
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
			rowView = inflater.inflate(R.layout.p_school_news_lv_item_layout,
					null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		NewsTitleAndContent titleAndContent = getItem(position);
		holder.tv_title = (TextView) rowView
				.findViewById(R.id.txtv_p_snews_lv_item_title_id);
		holder.tv_title.setText(titleAndContent.getTitle());
		holder.tv_content = (TextView) rowView
				.findViewById(R.id.txtv_p_snews_lv_item_content_id);
		holder.tv_content.setText(titleAndContent.getContent());
		holder.tv_newsid = (TextView) rowView.findViewById(R.id.tv_newsid);
		holder.tv_newsid.setText(titleAndContent.getNewid());
		return rowView;
	}

	static class ViewHolder {
		TextView tv_title, tv_content, tv_newsid;
	}

}