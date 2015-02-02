package com.lanbiao.youxiaoyunteacher.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.UpdatePwdService;
import com.lanbiao.youxinteacher.R;

public class TbabySetUpActivity extends Activity implements OnClickListener {
	private static final String TAG = "SetUpActivity";
	private TextView tv_about, tv_clean, tv_gotomain, tv_advice, tv_updatepwd;
	private Button btn_back, btn_exit;
	private Classinfo classinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_layout);
		initView();
		initData();
	}

	public void initView() {
		tv_about = (TextView) findViewById(R.id.txtv_setup_about_id);
		tv_clean = (TextView) findViewById(R.id.txtv_setup_clean_id);
		tv_gotomain = (TextView) findViewById(R.id.txtv_setup_welcome_id);
		tv_advice = (TextView) findViewById(R.id.txtv_setup_backmsg_id);
		tv_updatepwd = (TextView) findViewById(R.id.txtv_updatepassword_id);
		btn_back = (Button) findViewById(R.id.btn_setup_back_id);
		btn_exit = (Button) findViewById(R.id.btn_setup_quit_login_id);
		tv_about.setOnClickListener(this);
		tv_clean.setOnClickListener(this);
		tv_gotomain.setOnClickListener(this);
		tv_advice.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		tv_updatepwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent = getIntent();
		String userName = intent.getStringExtra("username");
		String userPwd = intent.getStringExtra("pwd");
		intent.putExtra("username", userName);
		intent.putExtra("pwd", userPwd);
		switch (v.getId()) {
		case R.id.txtv_setup_about_id:// 关于
			intent.setClass(TbabySetUpActivity.this, TbabyAboutActivity.class);
			startActivity(intent);
			break;
		case R.id.txtv_setup_clean_id:// 清除
			break;
		case R.id.txtv_setup_welcome_id:// 去欢迎界面
			intent.setClass(TbabySetUpActivity.this, TeacherActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.txtv_setup_backmsg_id:// 意见
			intent.setClass(TbabySetUpActivity.this, TbabyAdviceActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_setup_back_id:
			// finish();
			intent.setClass(TbabySetUpActivity.this, TeacherActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_setup_quit_login_id:
			new AlertDialog.Builder(TbabySetUpActivity.this)
					.setTitle("提示！")
					.setMessage("确定要退出吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									System.exit(0);
								}

							}).setNegativeButton("取消", null).show();
			break;
		case R.id.txtv_updatepassword_id:
			LayoutInflater layoutInflater = LayoutInflater
					.from(TbabySetUpActivity.this);
			final View myupdateView = layoutInflater.inflate(
					R.layout.t_baby_update, null);
			final EditText et_old,
			et_newpwd,
			et_repetition;
			et_old = (EditText) myupdateView.findViewById(R.id.et_useroldpwd);
			et_newpwd = (EditText) myupdateView.findViewById(R.id.et_newpwd);
			et_repetition = (EditText) myupdateView
					.findViewById(R.id.et_repetition);

			new AlertDialog.Builder(this)
					.setTitle("修改密码")
					.setView(myupdateView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String oldpwd = et_old.getText().toString()
											.trim();
									String newpwd = et_newpwd.getText()
											.toString().trim();
									String repetition = et_repetition.getText()
											.toString().trim();

									if (newpwd.length() < 6) {
										Toast.makeText(TbabySetUpActivity.this,
												"密码长度最大为6位！", 0).show();
										return;
									}
									if (newpwd.equals(repetition)) {
										String phone = classinfo.getPhone();
										String pwd = classinfo.getPassword();
										String stroldpwd = getMd5(oldpwd);
										String result = UpdatePwdService
												.updateUser(phone, pwd, newpwd,
														repetition, stroldpwd);
										try {
											JSONObject jsonObject = new JSONObject(
													result);
											int code = jsonObject
													.getInt("responseCode");
											if (code == 0) {
												new AlertDialog.Builder(
														TbabySetUpActivity.this)
														.setTitle("提示！")
														.setMessage(
																"修改密码成功，请退出程序，重新登陆")
														.setPositiveButton(
																"确定",
																new DialogInterface.OnClickListener() {

																	@Override
																	public void onClick(
																			DialogInterface dialog,
																			int which) {
																		finish();
																		System.exit(0);
																	}
																}).show();
											} else {
												Toast.makeText(
														TbabySetUpActivity.this,
														"密码修改失败！", 0).show();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										Toast.makeText(TbabySetUpActivity.this,
												"两次输入的密码不一致！", 0).show();
										return;
									}
								}
							}).setNegativeButton("取消", null).show();
			break;
		}
	}

	public void initData() {
		try {
			Intent intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			String strteacherinfo = LoginService.login(userName, userPwd);
			classinfo = JsonTools.getClassId("results", strteacherinfo);// 得到教师的个人信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			intent.putExtra("username", userName);
			intent.putExtra("pwd", userPwd);
			intent.setClass(TbabySetUpActivity.this, TeacherActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * MD5加密算法
	 * 
	 * @param plainText
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return plainText;
	}

}
