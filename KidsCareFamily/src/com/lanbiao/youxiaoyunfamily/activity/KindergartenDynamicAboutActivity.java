package com.lanbiao.youxiaoyunfamily.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.AboutUs;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class KindergartenDynamicAboutActivity extends Activity {
	private Button btn_back;
	private AboutUs aboutUs;
	private TextView tv_title, tv_content, tv_phone, tv_website, tv_address;
	private ImageView iv_schoolimg;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_school_about_layout);
		appliction = (AppAppliction) getApplication();
		initView();
		initData();
	}

	public void initView() {
		iv_schoolimg = (ImageView) findViewById(R.id.iv_school_logo_id);
		tv_title = (TextView) findViewById(R.id.tv_school_name_id);
		tv_content = (TextView) findViewById(R.id.tv_content_id);
		tv_phone = (TextView) findViewById(R.id.tv_contactschool_id);
		tv_website = (TextView) findViewById(R.id.tv_website_id);
		tv_address = (TextView) findViewById(R.id.tv_address_id);
		btn_back = (Button) findViewById(R.id.btn_p_school_back_id);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initData() {
		try {
			new Thread() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							infos = appliction.infos;
							for (FamliyInfo info : infos) {
								strStu_id = info.getStudentId();
							}
							String straboutus = FamilyofBabyDynamicService
									.aboutUs(strStu_id);
							aboutUs = JsonTools.getAboutus("results",
									straboutus);
							tv_title.setText(aboutUs.getTitle());
							tv_content.setText(aboutUs.getRemark());
							tv_phone.setText("联系我们:" + aboutUs.getPhone());
							tv_address.setText("地址:" + aboutUs.getAddress());
							tv_website.setText("网址:"
									+ aboutUs.getSchoolwebsite());
							DisplayMetrics dm1 = new DisplayMetrics();
							ImageDownloadTask imgtask = new ImageDownloadTask();
							getWindowManager().getDefaultDisplay().getMetrics(
									dm1);
							imgtask.setDisplayWidth(dm1.widthPixels);
							imgtask.setDisplayHeight(dm1.heightPixels);
							imgtask.execute(aboutUs.getLogo(), iv_schoolimg);
						}
					});
					super.run();
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
