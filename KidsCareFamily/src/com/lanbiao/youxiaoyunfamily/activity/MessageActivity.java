package com.lanbiao.youxiaoyunfamily.activity;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.ChatMsgViewAdapter;
import com.lanbiao.youxiaoyunfamily.entity.ChatMsgEntity;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.LeaveInfo;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class MessageActivity extends Activity implements OnClickListener {
	private Button btn_back;// 返回btn
	private Button btn_send;// 发送btn
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	private LeaveInfo leaveInfo;
	@SuppressWarnings("unused")
	private static final String TAG = "MessageActivity";
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strFamliy_Id;
	private String strTeacher_Id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.liaotian);
		appliction = (AppAppliction) getApplication();
		initView();
		initData();
		if (mAdapter != null && mAdapter.getCount() > 0) {
			mListView.setSelection(mAdapter.getCount() - 1);
		}

	}

	public void initView() {
		btn_back = (Button) findViewById(R.id.btn_p_message_back_id);
		mListView = (ListView) findViewById(R.id.listview);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initData() {
		try {
			initLogin();
			String leaveinfo = FamilyofBabyDynamicService.getleaveInfo(
					strFamliy_Id, strTeacher_Id);
			JSONObject jsonObject = new JSONObject(leaveinfo);
			int code = jsonObject.getInt("responseCode");
			// 提示无消息
			// String strerrormsg = jsonObject.getString("msg");
			if (code == -2) {
				// Toast.makeText(getApplicationContext(), strerrormsg,
				// 0).show();
				return;
			} else {
				leaveInfo = JsonTools.getLeaveAllInfo("results", leaveinfo);
				String msg = leaveInfo.getMsg();
				String[] msgArray = msg.split("##");
				for (int i = 0; i < msgArray.length; i++) {
					ChatMsgEntity entity = new ChatMsgEntity();
					String[] currentMsgArray = msgArray[i].split("=");
					String strTime = currentMsgArray[0];// 时间
					String strName = currentMsgArray[1];// 名字
					String strLogo = currentMsgArray[2];// 头像
					String strType = currentMsgArray[3];// 类型
					String strContent = currentMsgArray[4];// 内容
					if (strType.equals("1")) // 判断type 1是收到 2是发送
						entity.setMsgType(false);// 收到的消息
					else
						entity.setMsgType(true);// 自己发送的消息
					entity.setMessage(strContent);
					entity.setName(strName);
					entity.setDate(strTime);
					entity.setUserheadurl(strLogo);
					mDataArrays.add(entity);
				}
			}

			/***************** 测试代码Start ********************/
			// for (int i = 0; i < COUNT; i++) {
			// ChatMsgEntity entity = new ChatMsgEntity();
			// entity.setDate(dataArray[i]);// 时间
			// if (i % 2 == 0) {
			// entity.setName("肖B");// 名字
			// entity.setMsgType(true);// 收到的消息
			// } else {
			// entity.setName("必败");
			// entity.setMsgType(false);// 自己发送的消息
			// }
			// entity.setMessage(msgArray[i]);
			// mDataArrays.add(entity);
			// }
			/***************** 测试代码End ********************/

			mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
			mListView.setAdapter(mAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initLogin() {
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				strFamliy_Id = info.getFamilyId();
				strTeacher_Id = info.getTeacher_id();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:// 发送按钮点击事件
			send();
			break;
		case R.id.btn_p_message_back_id:// 返回按钮点击事件
			finish();// 结束,实际开发中，可以返回主界面
			break;
		}
	}

	/**
	 * 发送消息
	 */
	@SuppressWarnings("deprecation")
	private void send() {
		String strcontent = mEditTextContent.getText().toString();

		if (strcontent.length() > 0) {
			try {
				ChatMsgEntity entity = new ChatMsgEntity();
				initLogin();
				String content = strcontent;
				content = URLEncoder.encode(content);
				String result = FamilyofBabyDynamicService.sendMsg(
						strFamliy_Id, strTeacher_Id, content);
				JSONObject jsonObject = new JSONObject(result);
				int code = jsonObject.getInt("responseCode");
				if (code == 0) {
					entity.setDate(getDate());
					entity.setMessage(strcontent);
					// entity.setName(leaveInfo.getStudent_region());
					entity.setMsgType(false);
					mDataArrays.add(entity);
					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
					} else if (mAdapter == null) {
						initData();
						mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
					}
					mEditTextContent.setText("");// 清空编辑框数据
					// 发送一条消息时，ListView显示选择最后一项
					mListView.setSelection(mListView.getCount() - 1);

				}
			} catch (Exception e) {
				// Toast.makeText(getApplicationContext(), "发送失败...", 0).show();
			}

		}
	}

	/**
	 * 发送消息时，获取当前事件
	 * 
	 * @return 当前时间
	 */
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
}
