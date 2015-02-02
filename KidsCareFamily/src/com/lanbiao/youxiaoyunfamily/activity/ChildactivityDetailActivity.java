package com.lanbiao.youxiaoyunfamily.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.ActivityContent;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class ChildactivityDetailActivity extends Activity {
	private Button btn_back;
	private ImageView iv_img;
	private TextView tv_title, tv_content, tv_applytoclass, tv_time,
			tv_sendteacher;
	private ActivityContent activityContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_baby_activity_detail_layout);
		initView();
		initData();
	}

	public void initView() {
		btn_back = (Button) findViewById(R.id.btn_p_activity_detail_back_id);
		tv_title = (TextView) findViewById(R.id.txtv_pba_detail_title_id);
		tv_content = (TextView) findViewById(R.id.txtv_pba_detail_content_id);
		tv_applytoclass = (TextView) findViewById(R.id.txtv_pba_detail_class_id);
		tv_time = (TextView) findViewById(R.id.txtv_pba_detail_date_id);
		tv_sendteacher = (TextView) findViewById(R.id.txtv_pba_detail_teacher_id);
		iv_img = (ImageView) findViewById(R.id.imgv_pba_lv_item_icon_id);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initData() {
		Bundle bundle = this.getIntent().getExtras();
		String stractivityid = bundle.getString("itemid");
		String stractivitydetail = FamilyofBabyDynamicService
				.getActivityDetail(stractivityid);
		activityContent = JsonTools.getActivityDetail("results",
				stractivitydetail);
		tv_title.setText(activityContent.getActivitytitle());
		tv_content.setText(activityContent.getActivitycontents());
		tv_applytoclass.setText(activityContent.getActivityapplytoclass());
		tv_sendteacher.setText(activityContent.getActivitysendteacher());
		runOnUiThread(new Runnable() {
			public void run() {
				ImageDownloadTask imgtask = new ImageDownloadTask();
				imgtask.execute(activityContent.getActivityimg(), iv_img);
			}
		});

		tv_time.setText(activityContent.getActivitycreatetime());
	}
}
