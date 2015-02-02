package com.lanbiao.youxiaoyunteacher.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.CompanyService;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxinteacher.R;

public class TbabyAdviceActivity extends Activity implements OnClickListener {
	private static final String TAG = "AdviceActivity";
	private Button btn_advice, btn_submit;
	private EditText et_phone, et_advicemsg;
	private Classinfo classinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_backmsg_layout);
		initView();
	}

	public void initView() {
		et_phone = (EditText) findViewById(R.id.editv_backmsg_phone_id);
		et_advicemsg = (EditText) findViewById(R.id.editv_backmsg_message_id);
		btn_advice = (Button) findViewById(R.id.btn_setup_backmsg_back_id);
		btn_submit = (Button) findViewById(R.id.btn_setup_backmsg_submit_id);
		btn_advice.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = getIntent();
		String userName = intent.getStringExtra("username");
		String userPwd = intent.getStringExtra("pwd");
		intent.putExtra("username", userName);
		intent.putExtra("pwd", userPwd);
		switch (v.getId()) {
		case R.id.btn_setup_backmsg_back_id:// 返回
			intent.setClass(TbabyAdviceActivity.this, TbabySetUpActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_setup_backmsg_submit_id:// 提交数据
			String strphone = et_phone.getText().toString().trim();
			String strcontent = et_advicemsg.getText().toString().trim();
			if (TextUtils.isEmpty(strphone) && TextUtils.isEmpty(strcontent)) {
				Toast.makeText(getApplicationContext(), "号码和内容不能为空！", 0).show();
			} else if (!strphone.substring(0, 1).equals("1")) {
				Toast.makeText(getApplicationContext(), "号码格式错误！", 0).show();
				return;
			} else if (strphone.length() < 8) {
				Toast.makeText(getApplicationContext(), "号码格式错误！", 0).show();
				return;
			} else {
				intent = getIntent();
				String teacherresult = LoginService.login(userName, userPwd);
				classinfo = JsonTools.getClassId("results", teacherresult);
				String result = CompanyService.sendAdvice(classinfo.getType(),
						classinfo.getId(), strphone, strcontent);
				try {
					JSONObject object = new JSONObject(result);
					int code = Integer.parseInt(object
							.getString("responseCode"));
					if (code == 0) {
						// setContentView(R.layout.t_main_layout);
						et_phone.setText("");
						et_advicemsg.setText("");
						showCustomToast();
						// finish();
					} else {
						Toast.makeText(TbabyAdviceActivity.this, "发送失败...", 0)
								.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			break;
		}
	}

	/*
	 * 从布局文件中加载布局并且自定义显示Toast
	 */
	private void showCustomToast() {
		// 获取LayoutInflater对象，该对象可以将布局文件转换成与之一致的view对象
		LayoutInflater inflater = getLayoutInflater();
		// 将布局文件转换成相应的View对象
		View layout = inflater.inflate(R.layout.custome_toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));
		// 从layout中按照id查找imageView对象
		ImageView imageView = (ImageView) layout.findViewById(R.id.ivForToast);
		// 设置ImageView的图片
		imageView.setBackgroundResource(R.drawable.ic_ok);
		// 实例化一个Toast对象
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(5);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setView(layout);
		toast.show();
	}
}
