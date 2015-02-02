package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoadCallback;
import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoader;
import com.lanbiao.youxiaoyunteacher.downloader.image.ImageLoaderInfo;
import com.lanbiao.youxiaoyunteacher.entity.ChatMsgEntity;
import com.lanbiao.youxiaoyunteacher.entity.ImageAndText;
import com.lanbiao.youxinteacher.R;

/**
 * 消息ListView的Adapter
 * 
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter implements
		ImageLoadCallback {

	private Context context;
	private List<ImageAndText> listData;
	private ImageLoader mLoader;
	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;

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

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {// 收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {// 自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ChatMsgEntity entity = coll.get(position);
			boolean isComMsg = entity.getMsgType();

			ViewHolder viewHolder = null;
			if (convertView == null) {
				if (isComMsg) {
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_left, null);
				} else {
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_right, null);
				}

				viewHolder = new ViewHolder();
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime);
				viewHolder.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_username);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent);
				viewHolder.iv_head = (ImageView) convertView
						.findViewById(R.id.iv_userhead);
				viewHolder.isComMsg = isComMsg;

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// ImageDownloadTask imgtask = new ImageDownloadTask();
			// imgtask.execute(entity.getHead(), viewHolder.iv_head);
			ImageLoaderInfo info = new ImageLoaderInfo(entity.getHead(),
					viewHolder.iv_head, null, ChatMsgViewAdapter.this);
			if (mHandler.hasMessages(position)) {
				mHandler.removeMessages(position);
			}
			Message msg = mHandler.obtainMessage(position, info);
			mHandler.sendMessageDelayed(msg, 500);

			viewHolder.tvSendTime.setText(entity.getDate());
			viewHolder.tvUserName.setText(entity.getName());
			viewHolder.tvContent.setText(entity.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_head;
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public boolean isComMsg = true;
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
