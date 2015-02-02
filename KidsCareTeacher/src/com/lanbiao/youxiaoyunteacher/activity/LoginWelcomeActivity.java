package com.lanbiao.youxiaoyunteacher.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.util.NetworkUtil;
import com.lanbiao.youxinteacher.R;

public class LoginWelcomeActivity extends Activity {
	protected static final String TAG = "MainActivity";
	private EditText et_username, et_password;
	private CheckBox cb;
	private String userName;
	private String userPwd;
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_login_layout);
		et_username = (EditText) findViewById(R.id.editv_login_uanem_id);
		et_password = (EditText) findViewById(R.id.editv_login_upwd_id);
		cb = (CheckBox) findViewById(R.id.cb_login_ischeck_id);
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String name = sp.getString("username", "");
		et_username.setText(name);
		String pwd = sp.getString("password", "");
		et_password.setText(pwd);
		if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)) {
			cb.setChecked(false);
		} else {
			cb.setChecked(true);
		}
	}

	/**
	 * 登陆
	 * 
	 * @param view
	 */
	public void login(View view) {
		if (NetworkUtil.isConnectingToInternet(getApplicationContext())) {
			final Intent intent = new Intent();
			userName = et_username.getText().toString().trim();
			userPwd = et_password.getText().toString().trim();
			intent.putExtra("username", userName);
			intent.putExtra("pwd", userPwd);
			if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPwd)) {
				Toast.makeText(LoginWelcomeActivity.this, "用户名和密码不能为空！！！", 0)
						.show();
				return;
			} else {
				new Thread() {
					public void run() {
						// result为json数据
						result = LoginService.login(userName, userPwd);
						JSONObject j;
						int inttype = 0;
						try {
							// json解析 用来判断用户、密码
							j = new JSONObject(result);
							int code = Integer.parseInt(j
									.getString("responseCode"));
							if (code != -3) {
								String results = j.getString("results");
								JSONArray array = new JSONArray(results);
								for (int i = 0; i < array.length(); i++) {
									JSONObject object = array.getJSONObject(i);
									int type = object.getInt("type");
									inttype = type;
								}
							}

							if (code == -3) {// 如果等于-3 则是用户名、密码错误
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										Toast.makeText(
												LoginWelcomeActivity.this,
												"用户名或密码错误！！！", 1).show();
									}
								});
								return;
							} else if (code == 0) {// 登陆成功
								if (inttype == 1) {// 判断是否是教师
									try {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												// 跳转到教师页面
												intent.setClass(
														LoginWelcomeActivity.this,
														TeacherActivity.class);
												startActivity(intent);
												finish();
												// 是否记住用户和密码
												if (cb.isChecked()) {
													LoginService
															.saveUser(
																	LoginWelcomeActivity.this,
																	userName,
																	userPwd);
													Log.i(TAG,
															"The username and password has been saved successfully!!");
												} else {
													String userpwd = "";
													LoginService
															.saveUserName(
																	LoginWelcomeActivity.this,
																	userName,
																	userpwd);
													Log.i(TAG,
															"The username has been saved successfully!! ");
												}
												return;
											}
										});
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											Toast.makeText(
													LoginWelcomeActivity.this,
													"请使用教师账号登陆！！！", 1).show();
										}
									});
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		} else {
			Toast.makeText(LoginWelcomeActivity.this, "请检查网络是否连接...", 0).show();
		}

	}
}
