package com.lanbiao.youxiaoyunteacher.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.Teacherinfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxinteacher.R;

public class TeacherActivity extends Activity implements OnClickListener {
	private static final String TAG = "TeacherActivity";
	private Button btn_babyNamed, btn_babyDynamic, btn_myClass, btn_studyRaise,
			btn_assortsService, btn_ParentsMessage, btn_babyBirth, btn_setting,
			btn_exit;
	private TextView tv_teachername, tv_schoolname;
	private Teacherinfo teacherinfo;
	private String name;
	private String pwd;
	private String re;
	private JSONObject jo;
	private Classinfo myClass;
	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_main_layout);
		findView();// 初始化控件
		initdata();
	}

	public void initdata() {
		Intent intent = new Intent();
		intent = getIntent();
		name = intent.getStringExtra("username");
		pwd = intent.getStringExtra("pwd");
		re = LoginService.login(name, pwd);
		try {
			jo = new JSONObject(re);// 教师个人信息

			myClass = JsonTools.getClassId("results", re);
			tv_teachername.setText(myClass.getTeachername() + ",您好！");
			tv_schoolname.setText(myClass.getSchoolname());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 初始化控件
	public void findView() {
		btn_exit = (Button) findViewById(R.id.btn_t_main_quit_id);
		btn_setting = (Button) findViewById(R.id.btn_t_main_setup_id);
		btn_babyNamed = (Button) findViewById(R.id.btn_t_main_bnamed_id);
		btn_babyDynamic = (Button) findViewById(R.id.btn_t_main_bdynamic_id);
		btn_myClass = (Button) findViewById(R.id.btn_t_main_class_id);
		btn_studyRaise = (Button) findViewById(R.id.btn_t_main_raise_id);
		btn_assortsService = (Button) findViewById(R.id.btn_t_main_service_id);
		btn_ParentsMessage = (Button) findViewById(R.id.btn_t_main_message_id);
		btn_babyBirth = (Button) findViewById(R.id.btn_t_main_remind_id);
		tv_teachername = (TextView) findViewById(R.id.txtv_t_main_teachername_id);
		tv_schoolname = (TextView) findViewById(R.id.txtv_t_main_schoolname_id);
		btn_exit.setOnClickListener(this);
		btn_setting.setOnClickListener(this);
		btn_babyNamed.setOnClickListener(this);// 1
		btn_babyDynamic.setOnClickListener(this);// 2
		btn_myClass.setOnClickListener(this);// 3
		btn_studyRaise.setOnClickListener(this);// 4
		btn_assortsService.setOnClickListener(this);// 5
		btn_ParentsMessage.setOnClickListener(this);// 6
		btn_babyBirth.setOnClickListener(this);// 7
	}

	@Override
	public void onClick(View v) {
		Intent intent = getIntent();
		String userName = intent.getStringExtra("username");
		String userPwd = intent.getStringExtra("pwd");
		intent.putExtra("username", userName);
		intent.putExtra("pwd", userPwd);
		intent = getIntent();
		switch (v.getId()) {
		/**
		 * 宝贝点名
		 */
		case R.id.btn_t_main_bnamed_id:
			intent.setClass(TeacherActivity.this, TbabyNamedActivity.class);
			startActivity(intent);
			break;
		/**
		 * 宝贝动态
		 */
		case R.id.btn_t_main_bdynamic_id:
			intent.setClass(TeacherActivity.this, TbabyMyStudentsActivity.class);
			// intent.setClass(TeacherActivity.this, TestActivity.class);
			startActivity(intent);
			break;
		/**
		 * 联系家长
		 */
		case R.id.btn_t_main_class_id:
			intent.setClass(TeacherActivity.this,
					TbabyContactFamilyActivity.class);
			startActivity(intent);
			break;
		/**
		 * 学习提高
		 */
		case R.id.btn_t_main_raise_id:
			intent.setClass(getApplicationContext(), TbabyRaiseActivity.class);
			startActivity(intent);
			break;
		/**
		 * 配套服务
		 */
		case R.id.btn_t_main_service_id:
			intent.setClass(getApplicationContext(), TbabyServiceActivity.class);
			startActivity(intent);
			break;
		/**
		 * 家长留言
		 */
		case R.id.btn_t_main_message_id:
			intent.setClass(getApplicationContext(),
					TbabyQueryListMsgActivity.class);
			startActivity(intent);
			break;
		/**
		 * 宝贝生日
		 */
		case R.id.btn_t_main_remind_id:
			// Toast.makeText(TeacherActivity.this, "123456", 0).show();
			new AlertDialog.Builder(this).setMessage("      该功能正在努力开发中...")
					.show();
			break;
		/**
		 * 设置
		 */
		case R.id.btn_t_main_setup_id:
			intent.setClass(TeacherActivity.this, TbabySetUpActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_t_main_quit_id:
			new AlertDialog.Builder(TeacherActivity.this)
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
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
