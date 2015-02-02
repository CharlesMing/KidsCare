package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.lanbiao.youxiaoyunteacher.activity.TbabyMessageInfoActivity;
import com.lanbiao.youxiaoyunteacher.entity.MessageInfoImgAndTxt;
import com.lanbiao.youxinteacher.R;

public class QueryMsgAdapter extends ArrayAdapter<MessageInfoImgAndTxt> {

	private static final String TAG = "ImageAndTextListAdapter";
	private ListView listView;
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	List<MessageInfoImgAndTxt> listData;

	public QueryMsgAdapter(Activity activity,
			List<MessageInfoImgAndTxt> imageAndTexts, ListView listView) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		this.listData = imageAndTexts;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		View rowView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.t_baby_message_item_layout,
					null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		final MessageInfoImgAndTxt imgAndTxt = getItem(position);

		holder.img_head = (ImageView) rowView.findViewById(R.id.iv_head_id);// 头像
		holder.tv_content = (TextView) rowView.findViewById(R.id.tv_content);// 内容
		holder.tv_name = (TextView) rowView.findViewById(R.id.tv_sturegion);// 关系名字
		holder.tv_time = (TextView) rowView.findViewById(R.id.tv_time_id);// 时间

		holder.btn_reply = (Button) rowView.findViewById(R.id.btn_reply_id);// 回复
		holder.btn_noreply = (Button) rowView.findViewById(R.id.btn_noreply_id);// 已回复
		holder.cb = (CheckBox) rowView.findViewById(R.id.cb_repleyall);

		String imgUrl = imgAndTxt.getStulogo();
		ImageDownloadTask imgtask = new ImageDownloadTask();
		imgtask.execute(imgUrl, holder.img_head);
		holder.tv_content.setText(imgAndTxt.getContent());
		holder.tv_name.setText(imgAndTxt.getRegion_name());
		holder.tv_time.setText(imgAndTxt.getMsg_time());
		if (imgAndTxt.getStatus().equals("0")) {// 判断是否是回复，如果是0 是未回复，否则显示已回复
			holder.btn_reply.setVisibility(View.VISIBLE);
			holder.btn_noreply.setVisibility(View.GONE);
			holder.btn_reply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.putExtra("fid", imgAndTxt.getFamliyid());
					intent.putExtra("tid", imgAndTxt.getTeacherid());
					intent.putExtra("converid", imgAndTxt.getConverid());
					intent.putExtra("tname", imgAndTxt.getTname());
					intent.putExtra("username", imgAndTxt.getUsername());
					intent.putExtra("userpwd", imgAndTxt.getUserpwd());
					intent.setClass(getContext(),
							TbabyMessageInfoActivity.class);
					getContext().startActivity(intent);
					((Activity) getContext()).finish();
				}
			});
		} else {
			holder.btn_reply.setVisibility(View.GONE);
			holder.btn_noreply.setVisibility(View.VISIBLE);
		}

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
		CheckBox cb;
		TextView tv_name, tv_content, tv_time, tv_id, tv_converid;
		Button btn_reply, btn_noreply;
		ImageView img_head;
	}

}