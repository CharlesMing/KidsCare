package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.OptionsAdapter;
import com.lanbiao.youxiaoyunfamily.data.GsonRequest;
import com.lanbiao.youxiaoyunfamily.db.UserInfoOpenHelper;
import com.lanbiao.youxiaoyunfamily.db.dao.UserInfoDao;
import com.lanbiao.youxiaoyunfamily.entity.Famliy;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.SelectPhone;
import com.lanbiao.youxiaoyunfamily.entity.UserInfo;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.service.LoginService;
import com.lanbiao.youxiaoyunfamily.util.NetworkUtils;

public class LoginWelcomeActivity extends Activity implements Callback {
	protected static final String TAG = "MainActivity";
	private EditText et_username, et_password;
	private CheckBox cb;

	// PopupWindow对象
	private PopupWindow selectPopupWindow = null;
	// 自定义Adapter
	private OptionsAdapter optionsAdapter = null;
	// 下拉框选项数据源
	private ArrayList<SelectPhone> phoneDatas = new ArrayList<SelectPhone>();
	// 下拉框依附组件
	private LinearLayout parent;
	// 下拉框依附组件宽度，也将作为下拉框的宽度
	private int pwidth;
	// 下拉箭头图片组件
	private ImageButton image;
	// 恢复数据源按钮
	// 展示所有下拉选项的ListView
	private ListView listView = null;
	// 用来处理选中或者删除下拉项消息
	private Handler handlerData;
	// 是否初始化完成标志
	private boolean flag = false;
	private String path = "";
	private String userName;
	private String userPwd;
	private static ProgressDialog dialog = null;// 记载进度框
	private Intent intent = new Intent();
	private Website url = new Website();
	private List<FamliyInfo> infos;
	private final int SHOWDIALOG = 0;
	private final int CLOSEDIALOG = 1;
	private int code;
	private int type;
	private AppAppliction appliction;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (!NetworkUtils.isConnectingToInternet(LoginWelcomeActivity.this)) {
				ShowToast.getToastInfo("请检查网络是否连接", LoginWelcomeActivity.this);
			} else {
				switch (msg.what) {
				case 0:
					// showDialogInfo("请稍后...", "正在登陆");
					dialog.setTitle("正在登陆");
					dialog.setMessage("请稍后...");
					dialog.setCancelable(false);
					dialog.show();

					break;
				case 1:
					// 登录失败
					dialog.cancel();
					break;
				}
			}
		}
	};
	public static Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		et_username = (EditText) findViewById(R.id.editv_login_uanem_id);
		et_password = (EditText) findViewById(R.id.editv_login_upwd_id);
		setContentView(R.layout.login_layout);
		dialog = new ProgressDialog(this);
		appliction = (AppAppliction) getApplication();
		appliction.mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(appliction.mReceiver, appliction.mFilter);

		cb = (CheckBox) findViewById(R.id.cb_login_ischeck_id);
		initPwdAndUser();
		createDataBase();

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(appliction.mReceiver);
		super.onDestroy();
	}

	/**
	 * 登陆
	 * 
	 * @param view
	 */
	public void login(View view) {
		userName = et_username.getText().toString().trim();
		userPwd = et_password.getText().toString().trim();
		intent.putExtra("username", userName);
		intent.putExtra("pwd", userPwd);
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPwd)) {
			ShowToast.getToastInfo("用户名和密码不能为空！", LoginWelcomeActivity.this);
			return;
		} else {
			handler.sendEmptyMessage(SHOWDIALOG);
			LoginResultInfo();
		}

	}

	/**
	 * <p>
	 * 点击登陆时得到的数据信息
	 * </p>
	 * <p>
	 * 进行判断
	 * </p>
	 */
	private void LoginResultInfo() {

		// 接口
		path = url.getPath() + url.getLogin() + url.getUsername() + userName
				+ url.getPassword() + userPwd;
		System.out.println(path);
		try {

			RequestQueue mQueue = Volley.newRequestQueue(this);
			GsonRequest<Famliy> gsonRequest = new GsonRequest<Famliy>(path,
					Famliy.class, new Listener<Famliy>() {

						@Override
						public void onResponse(Famliy famliy) {
							infos = famliy.getResults();

							code = famliy.getResponseCode();
							if (code == -3) {// 如果等于-3 则是用户名、密码错误
								ShowToast.getToastInfo("用户名或密码错误！",
										LoginWelcomeActivity.this);
								handler.sendEmptyMessage(CLOSEDIALOG);
							} else if (code == 0) {// 登陆成功
								for (FamliyInfo info : infos) {
									type = info.getType();
								}
								if (type == 2) {// 判断是否是家长登陆
									startIntent();
								}
							} else
								ShowToast.getToastInfo("请使用家长账号登陆！",
										LoginWelcomeActivity.this);
							handler.sendEmptyMessage(CLOSEDIALOG);
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e(TAG, error.getMessage(), error);
						}
					});
			mQueue.add(gsonRequest);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 登陆成功之后的页面跳转及记住密码操作
	 */
	public void startIntent() {
		appliction.infos = infos;
		UserInfoDao dao = new UserInfoDao(this);
		// 是否保存用户和密码
		if (cb.isChecked()) {
			LoginService.saveUser(LoginWelcomeActivity.this, userName, userPwd);
			dao.addContact(userName, userPwd);

		} else {
			String userpwd = "";
			LoginService.saveUserName(LoginWelcomeActivity.this, userName,
					userpwd);
		}
		// 设置要跳转到的目标页面
		intent.setClass(LoginWelcomeActivity.this, FamilyActivity.class);
		startActivity(intent);
		finish();
		handler.sendEmptyMessage(CLOSEDIALOG);

		return;
	}

	/**
	 * 初始化之前保存好的用户名和密码
	 */
	public void initPwdAndUser() {
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

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			initView();
			flag = true;
		}
	}

	public void initView() {
		// 初始化Handler,用来处理消息
		handlerData = new Handler(LoginWelcomeActivity.this);

		// 初始化界面组件
		parent = (LinearLayout) findViewById(R.id.parent);
		et_username = (EditText) findViewById(R.id.editv_login_uanem_id);
		et_password = (EditText) findViewById(R.id.editv_login_upwd_id);
		image = (ImageButton) findViewById(R.id.btn_select);

		// 获取下拉框依附的组件宽度
		int width = parent.getWidth();
		pwidth = width;

		// 设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					// 显示PopupWindow窗口
					popupWindwShowing();
				}
			}
		});
		// 初始化PopupWindow
		initPopuWindow();
	}

	public void initDatas() {
		try {
			UserInfoDao dao = new UserInfoDao(this);
			List<UserInfo> info = dao.findAll();
			for (UserInfo uinfo : info) {
				// phoneDatas.add(uinfo.getPhone());
				phoneDatas.add(new SelectPhone(uinfo.getUsername(), uinfo
						.getUserpwd()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化PopupWindow
	 */
	private void initPopuWindow() {
		initDatas();
		// PopupWindow浮动下拉框布局
		View loginwindow = (View) this.getLayoutInflater().inflate(
				R.layout.options, null);
		listView = (ListView) loginwindow.findViewById(R.id.list);
		// 设置自定义Adapter
		optionsAdapter = new OptionsAdapter(this, handlerData, phoneDatas,
				listView);
		listView.setAdapter(optionsAdapter);

		selectPopupWindow = new PopupWindow(loginwindow, pwidth,
				LayoutParams.WRAP_CONTENT, true);

		selectPopupWindow.setOutsideTouchable(true);

		// 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 显示PopupWindow窗口
	 * 
	 * @param popupwindow
	 */
	public void popupWindwShowing() {
		// 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
		// 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
		// （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
		selectPopupWindow.showAsDropDown(parent, 0, -3);
	}

	@Override
	public boolean handleMessage(Message msg) {
		Bundle data = msg.getData();
		UserInfoDao dao = new UserInfoDao(this);
		switch (msg.what) {
		case 1:
			// 选中下拉项，下拉框消失
			int selIndex = data.getInt("selIndex");
			et_username.setText(phoneDatas.get(selIndex).getName());
			et_password.setText(phoneDatas.get(selIndex).getPwd());
			dismiss();
			break;
		case 2:
			// 移除下拉项数据
			int delIndex = data.getInt("delIndex");
			phoneDatas.remove(delIndex);
			dao.delbyid(Integer.toString(delIndex + 1));
			// 刷新下拉列表
			optionsAdapter.notifyDataSetChanged();
			break;
		}
		return false;
	}

	/**
	 * PopupWindow消失
	 */
	public void dismiss() {
		selectPopupWindow.dismiss();
	}

	public void createDataBase() {
		UserInfoOpenHelper helper = new UserInfoOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.close();
	}

}
