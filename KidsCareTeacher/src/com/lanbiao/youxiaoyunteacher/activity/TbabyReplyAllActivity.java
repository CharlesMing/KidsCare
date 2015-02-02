package com.lanbiao.youxiaoyunteacher.activity;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.ChatMsgEntity;
import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.MessageInfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MsgServcie;
import com.lanbiao.youxinteacher.R;

public class TbabyReplyAllActivity extends Activity implements OnClickListener {
	private TextView tv_replyid, tv_name;
	private EditText mEditTextContent;
	private Button btn_send, btn_back;
	private Classinfo classinfo;
	private MessageInfo info;
	private String strCid = "";
	private String strregion = "";
	private Button btn_more;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_replyall_layout);
		initView();
		initData();

	}

	public void initView() {
		tv_name = (TextView) findViewById(R.id.tv_name_id);
		tv_replyid = (TextView) findViewById(R.id.tv_replyno_id);
		mEditTextContent = (EditText) findViewById(R.id.et_evaluate);
		btn_send = (Button) findViewById(R.id.btn_t_remind_detail_send_id);
		btn_back = (Button) findViewById(R.id.btn_t_message_back_id);
		btn_more = (Button) findViewById(R.id.btn_more);
		btn_send.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_more.setOnClickListener(this);

	}

	public void initData() {
		try {
			Intent intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			String strteacherinfo = LoginService.login(userName, userPwd);
			classinfo = JsonTools.getClassId("results", strteacherinfo);// 得到教师的个人信息
			String StrReplylist = MsgServcie.getReplyList(classinfo.getId());// 得到教师的id
			info = JsonTools.getNoReply("results", StrReplylist);// 得到教师的消息列表
			String Msgs = info.getMsgs();
			String region = info.getSturegion();
			String[] strMsgArray = Msgs.split("##");// 0--关系;1--会话id;2--家长id;3--状态
			int alls = 0;

			for (int i = 0; i < strMsgArray.length; i++) {
				alls = i + 1;
				String[] currentMsgArray = strMsgArray[i].split("=");
				strCid += currentMsgArray[1] + ",";
			}
			if (strCid.length() > -1) {
				strCid = strCid.substring(0, strCid.length() - 1);
			}
			strregion = region;
			if (strregion.length() > -1) {
				strregion = strregion.substring(0, strregion.length() - 1);
			}
			tv_replyid.setText("共计回复给" + alls + "人");
			tv_name.setText(strregion);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_t_remind_detail_send_id:
			send();
			break;
		case R.id.btn_t_message_back_id:
			Intent intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			intent.putExtra("username", userName);
			intent.putExtra("pwd", userPwd);
			intent.setClass(TbabyReplyAllActivity.this,
					TbabyQueryListMsgActivity.class);
			startActivity(intent);
			finish();
			break;
		// case R.id.btn_more:
		// new AlertDialog.Builder(this).setMessage(strregion)
		// .setPositiveButton("确定", null).show();
		// break;
		}
	}

	@SuppressWarnings("deprecation")
	public void send() {
		String strcontent = mEditTextContent.getText().toString();
		if (strcontent.length() > 0) {
			try {
				strcontent = URLEncoder.encode(strcontent);
				String result = MsgServcie.sendMsg(strCid, classinfo.getId(),
						strcontent);
				JSONObject jsonObject = new JSONObject(result);
				int code = jsonObject.getInt("responseCode");
				if (code == 0) {
					// mEditTextContent.setText("");// 清空编辑框数据
					Intent intent = getIntent();
					String userName = intent.getStringExtra("username");
					String userPwd = intent.getStringExtra("pwd");
					intent.putExtra("username", userName);
					intent.putExtra("pwd", userPwd);
					intent.setClass(TbabyReplyAllActivity.this,
							TbabyQueryListMsgActivity.class);
					startActivity(intent);
					finish();

				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "发送失败...", 0).show();
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
