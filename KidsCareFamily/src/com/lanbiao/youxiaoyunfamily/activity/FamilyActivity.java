package com.lanbiao.youxiaoyunfamily.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Familyboyinfo;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Messages;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class FamilyActivity extends Activity implements OnClickListener {
	private static final String TAG = "FamilyActivity";
	private ImageView iv_head;
	private TextView tv_babyName, tv_babyAge;
	private TextView tv_gotoSchoolTime, tv_leaveSchoolTime, tv_schoolname;
	private Button bt_exit;
	private Button bt_babyDynamic, btn_share;
	private Button bt_schoolDynamic, bt_babyCourse, bt_babyCookbook,
			bt_schoolActivity, bt_getTeachermessage, bt_childcare, bt_setting;
	private long mExitTime;
	private Familyboyinfo familyinfo;
	private Messages messages;
	private String m;
	private String a;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String stu_id;
	private String boby_head;
	private String boby_name;
	private String school_name;
	private String birth_day;
	private String teacher_id;

	// private final String APP_ID = "wx06095e4dd495444d";
	private static ProgressDialog dialog = null;
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				dialog.setTitle("数据加载中...");
				dialog.setMessage("     请稍后...");
				dialog.setCancelable(false);
				dialog.show();
			} else if (msg.what == 1) {
				// 登录失败
				dialog.cancel();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parents_main_layout);
		appliction = (AppAppliction) getApplication();
		dialog = new ProgressDialog(this);
		InitView();
		InitData();

	}

	public void InitView() {
		tv_schoolname = (TextView) findViewById(R.id.txtv_p_main_schoolname_id);
		iv_head = (ImageView) this.findViewById(R.id.imgv_p_baby_icon_id);
		tv_babyName = (TextView) this.findViewById(R.id.txtv_p_baby_name_id);
		tv_babyAge = (TextView) this.findViewById(R.id.txtv_p_baby_age_id);
		tv_gotoSchoolTime = (TextView) this
				.findViewById(R.id.txtv_p_go_school_time_id);
		tv_leaveSchoolTime = (TextView) this
				.findViewById(R.id.txtv_p_leval_school_time_id);
		bt_exit = (Button) this.findViewById(R.id.btn_p_main_quit_id);
		bt_babyDynamic = (Button) this.findViewById(R.id.btn_p_baby_dynamic_id);
		bt_schoolDynamic = (Button) this
				.findViewById(R.id.btn_p_main_school_id);
		bt_babyCourse = (Button) this.findViewById(R.id.btn_p_main_class_id);
		bt_babyCookbook = (Button) this.findViewById(R.id.btn_p_main_food_id);
		bt_schoolActivity = (Button) this
				.findViewById(R.id.btn_p_main_activity_id);
		bt_getTeachermessage = (Button) this
				.findViewById(R.id.btn_p_main_message_id);
		bt_childcare = (Button) this.findViewById(R.id.btn_p_main_shop_id);
		bt_setting = (Button) this.findViewById(R.id.btn_p_main_setup_id);
		btn_share = (Button) findViewById(R.id.btn_share);
		bt_babyCookbook.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		bt_babyDynamic.setOnClickListener(this);
		bt_schoolDynamic.setOnClickListener(this);
		bt_babyCourse.setOnClickListener(this);
		bt_schoolActivity.setOnClickListener(this);
		bt_getTeachermessage.setOnClickListener(this);
		bt_childcare.setOnClickListener(this);
		bt_setting.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_share.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {

		final Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_p_baby_dynamic_id:// 宝贝动态
			intent.setClass(getApplicationContext(), BabyDynamicActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_school_id:// 幼儿园动态
			intent.setClass(getApplicationContext(),
					KindergartenDynamicTabActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_class_id:// 宝贝课程
			myHandler.sendEmptyMessage(1);
			intent.setClass(getApplicationContext(),
					CourseViewPagerActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_food_id:// 宝贝食谱
			// myHandler.sendEmptyMessage(0);
			// new Handler().postDelayed(new Runnable() {
			// @Override
			// public void run() {
			// myHandler.sendEmptyMessage(1);
			intent.setClass(getApplicationContext(),
					FoodViewPagerActivity.class);
			startActivity(intent);
			// }
			// 延迟过后再开始跳转
			// }, 5000);

			break;
		case R.id.btn_p_main_activity_id:// 亲子活动
			intent.setClass(getApplicationContext(),
					ChildactivityActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_message_id:// 给老师留言
			intent.setClass(getApplicationContext(), MessageActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_shop_id:// 育儿中心
			intent.setClass(getApplicationContext(),
					ChildCareCentersActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_p_main_setup_id:// 设置
			intent.setClass(FamilyActivity.this, SetUpActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_share:// 分享到朋友圈
			shareApp();
			// final IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID, false);
			// api.registerApp(APP_ID);
			// /***** --------分享到朋友圈START------- **/
			// WXWebpageObject webpage = new WXWebpageObject();
			// webpage.webpageUrl = "http://www.kidyun.com";
			// WXMediaMessage msg = new WXMediaMessage(webpage);
			// msg.title = "幼校云";
			// msg.description = "幼校云分享";
			// try {
			// // 随便弄的一张图
			// Bitmap bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.share_img);
			// Bitmap thumbBmp = Bitmap
			// .createScaledBitmap(bmp, 150, 150, true);
			// bmp.recycle();
			// msg.setThumbImage(thumbBmp);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// SendMessageToWX.Req req = new SendMessageToWX.Req();
			// req.transaction = String.valueOf(System.currentTimeMillis());
			// req.message = msg;
			// req.scene = SendMessageToWX.Req.WXSceneTimeline;
			// api.sendReq(req);
			/***** --------分享到朋友圈END------- **/

			/** -----分享到指定朋友START------- */
			// String text = "share our appliction";
			// WXTextObject textObject = new WXTextObject();
			// textObject.text = text;
			// WXMediaMessage msg = new WXMediaMessage(textObject);
			// msg.mediaObject = textObject;
			// msg.description = text;
			// SendMessageToWX.Req req = new SendMessageToWX.Req();
			// req.transaction = String.valueOf(System.currentTimeMillis());
			// req.message = msg;
			// api.sendReq(req);
			/** -----分享到指定朋友END------- */
			break;
		case R.id.btn_p_main_quit_id:// 退出
			new AlertDialog.Builder(FamilyActivity.this)
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

	/**
	 * 分享应用
	 */
	public void shareApp() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT,
				getString(R.string.setting_share_app_subject));
		intent.putExtra(Intent.EXTRA_TEXT,
				getString(R.string.setting_share_app_body)
						+ "http://www.hao123.com");
		startActivity(Intent.createChooser(intent,
				getString(R.string.setting_share_app_title)));
	}

	public void InitData() {
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				stu_id = info.getStudentId();
				teacher_id = info.getTeacher_id();
				boby_head = info.getStudentHead();
				boby_name = info.getStudentName();
				birth_day = info.getBirth();
				school_name = info.getSchoolName();
			}
			String result = FamilyofBabyDynamicService.getsigin(stu_id);
			Familyboyinfo info = JsonTools.getSignin("results", result);
			String strBabyBirthMsg = FamilyofBabyDynamicService.getTMsg(stu_id,
					teacher_id);
			messages = JsonTools.getTSendbyStuMsg("results", strBabyBirthMsg);
			final String remark = messages.getContent();
			if (remark != null) {
				runOnUiThread(new Runnable() {
					public void run() {
						showDialogInfo(remark);
					}
				});

			}

			m = info.getMoring_signin();
			a = info.getAfter_signin();
			if (m == null) {
				m = "未到校";
				tv_gotoSchoolTime.setText(m);
			} else {
				tv_gotoSchoolTime.setText(m);
			}
			if (a == null) {
				a = "未离校";
				tv_leaveSchoolTime.setText(a);
			} else {
				tv_leaveSchoolTime.setText(a);
			}

			tv_babyName.setText(boby_name);
			tv_babyAge.setText(birth_day);
			tv_schoolname.setText(school_name);
			runOnUiThread(new Runnable() {
				public void run() {
					ImageDownloadTask imgtask = new ImageDownloadTask();
					imgtask.execute(boby_head, iv_head);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
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

	public void showDialogInfo(String msg) {
		new AlertDialog.Builder(FamilyActivity.this)
				.setTitle("提示")
				.setMessage("老师发来祝福：" + msg)
				.setPositiveButton("谢谢老师",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String result = FamilyofBabyDynamicService
										.updateBirthMsg(
												familyinfo.getStudentId(),
												familyinfo.getTeacherId(),
												messages.getBirthid());
								Log.v(TAG, result);
							}
						}).show();
	}
}
