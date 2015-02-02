package com.lanbiao.youxiaoyunfamily.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Notice;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class KindergartenDynamicMsgDetailActivity extends Activity {
	private Button btn_join;
	private TextView tv_title, tv_begintime, tv_endtime, tv_contactphone,
			tv_named, tv_content;
	private Notice notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_school_notice_detail);
		initView();
		initData();
	}

	/**
	 * 初始化视图
	 */
	public void initView() {
		tv_named = (TextView) findViewById(R.id.tv_named_id);
		btn_join = (Button) findViewById(R.id.btn_t_main_join_id);
		tv_title = (TextView) findViewById(R.id.tv_msg_title);
		tv_begintime = (TextView) findViewById(R.id.tv_begin_time);
		tv_endtime = (TextView) findViewById(R.id.tv_end_time);
		tv_contactphone = (TextView) findViewById(R.id.tv_acitivityphone);
		tv_content = (TextView) findViewById(R.id.tv_activity_content);
		btn_join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				String bname = bundle.getString("name");
				LayoutInflater layoutInflater = LayoutInflater
						.from(KindergartenDynamicMsgDetailActivity.this);
				final View vfamilyinfo = layoutInflater.inflate(
						R.layout.activity_dialog, null);
				TextView tv_name = (TextView) vfamilyinfo
						.findViewById(R.id.et_name);
				tv_name.setText(bname);
				showDialogInfo(vfamilyinfo);
			}
		});
	}

	/**
	 * 点击报名时 弹出对话框
	 * 
	 * @param view
	 */
	public void showDialogInfo(View view) {
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("活动报名");
		dialog.setView(view);
		dialog.setPositiveButton("确定", positiveListener);
		dialog.setNegativeButton("取消", negativeListener);
		dialog.show();
	}

	/**
	 * 点击确定报名时返回数据
	 */
	public DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			getBackData();
		}
	};
	/**
	 * 点击取消时，弹出吐司
	 */
	public DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			showToast("取消报名！");
		}
	};

	/**
	 * 得到报名时返回的数据 报名成功----报名失败
	 */
	public void getBackData() {
		Bundle bundle = getIntent().getExtras();
		String strfamilyid = bundle.getString("familyid");
		String strstuid = bundle.getString("stuid");
		String re = FamilyofBabyDynamicService.activityJoin(// 报名接口
				notice.getActivityid(), strfamilyid, strstuid);
		try {
			JSONObject j = new JSONObject(re);
			int code;
			code = Integer.parseInt(j.getString("responseCode"));// 解析Json
			if (code == 1) {
				showToast("报名失败！");
			} else {
				showToast("报名成功！");
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 显示提示消息 ---吐司
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		Toast.makeText(this, msg, 0).show();
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		try {
			Bundle bundle = this.getIntent().getExtras();
			String stractivityid = bundle.getString("itemid");// 得到上一个activity传过来的活动id
			String strfamilyid = bundle.getString("familyid");// 得到上一个activity传过来的家长id
			String strActivityDetail = FamilyofBabyDynamicService// 通知接口返回来的数据
					.getMsgInfoDetail(stractivityid, strfamilyid);
			notice = JsonTools.getMsgInfoDataDetail("results",// 使用Json解析通知接口返回来的数据
					strActivityDetail);
			String contactphone = notice.getContactphone();// 联系电话
			String title = notice.getTitle();// 标题
			String content = notice.getContent();// 内容
			String begintime = notice.getBegintime();// 活动开始时间
			String endtime = notice.getEndtime();// 活动结束时间
			String siginstatus = notice.getSiginstatus();// 参加状态 已报名|未报名
			String type = notice.getType();// 活动通知类型
			if (type.equals("2")) {// 如果type=2，则是通知，不显示报名按钮
				tv_named.setVisibility(View.GONE);// 隐藏“已报名”字体
				btn_join.setVisibility(View.GONE);// 隐藏“报名”按钮
				tv_title.setText(title);
			} else if (type.equals("1")) {// 显示报名按钮
				if (siginstatus.equals("1")) {// 报名状态 1是已报名 0是未报名
					tv_named.setVisibility(View.VISIBLE);// 显示为“已报名”
					btn_join.setVisibility(View.GONE);// 隐藏“报名”按钮
					tv_named.setText("已报名");// 显示到界面中去
				} else {
					tv_named.setVisibility(View.GONE);// 隐藏“已报名”字体
					btn_join.setVisibility(View.VISIBLE);// 显示“报名按钮”
				}

			}
			tv_title.setText(title);
			tv_begintime.setText(begintime);
			tv_endtime.setText(endtime);
			tv_contactphone.setText(contactphone);
			tv_content.setText(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
