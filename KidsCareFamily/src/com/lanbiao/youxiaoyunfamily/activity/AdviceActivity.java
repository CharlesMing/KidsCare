package com.lanbiao.youxiaoyunfamily.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Familyboyinfo;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.service.LoginService;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

/**
 * 
 * @author my
 * 
 */
public class AdviceActivity extends Activity implements OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = "AdviceActivity";
	private Button btn_advice, btn_submit;
	private EditText et_phone, et_advicemsg;
	private Familyboyinfo familyboyinfo;
	private Website website = new Website();
	private String path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_backmsg_layout);
		initView();
		initData();
	}

	public void initView() {
		et_phone = (EditText) findViewById(R.id.editv_backmsg_phone_id);
		et_advicemsg = (EditText) findViewById(R.id.editv_backmsg_message_id);
		btn_advice = (Button) findViewById(R.id.btn_setup_backmsg_back_id);
		btn_submit = (Button) findViewById(R.id.btn_setup_backmsg_submit_id);
		btn_advice.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}

	public void initData() {
		Intent intent = getIntent();
		String userName = intent.getStringExtra("username");
		et_phone.setText(userName);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setup_backmsg_back_id:
			finish();
			break;
		case R.id.btn_setup_backmsg_submit_id:// 提交数据
			commit();
			break;
		}
	}

	/**
	 * 提交数据
	 * 
	 * @param v
	 */
	public void commit() {
		String strphone = et_phone.getText().toString().trim();
		String strcontent = et_advicemsg.getText().toString().trim();
		if (TextUtils.isEmpty(strphone) && TextUtils.isEmpty(strcontent)) {
			Toast.makeText(getApplicationContext(), "号码和内容不能为空！", 0).show();
			return;
		} else if (!strphone.substring(0, 1).equals("1")) {
			Toast.makeText(getApplicationContext(), "号码格式错误！", 0).show();
			return;
		} else if (strphone.length() < 8) {
			Toast.makeText(getApplicationContext(), "号码格式错误！", 0).show();
			return;
		} else {
			Intent intent = new Intent();
			intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			String familyresult = LoginService.login(userName, userPwd);
			familyboyinfo = JsonTools.getMybaby("results", familyresult);

			// String result = FamilyofBabyDynamicService.sendAdvice(
			// familyboyinfo.getType(), familyboyinfo.getFamilyid(),
			// strphone, strcontent);
			path = website.getPath() + website.getAddsuggestion()
					+ website.getStrtype() + familyboyinfo.getType()
					+ website.getStrphone() + strphone + website.getStrremark()
					+ strcontent + website.getFamilyid()
					+ familyboyinfo.getFamilyid();
			String result = HttpUtils.getJsonContent(path);
			try {
				JSONObject object = new JSONObject(result);
				int code = Integer.parseInt(object.getString("responseCode"));
				if (code == 0) {
					et_phone.setText("");
					et_advicemsg.setText("");
					Toast.makeText(AdviceActivity.this, "感谢您的意见反馈", 0).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
}
