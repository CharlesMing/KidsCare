package com.lanbiao.youxiaoyunfamily.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.lanbiao.youxiaoyunfamily.R;

@SuppressWarnings("deprecation")
public class KindergartenDynamicTabActivity extends TabActivity implements
		OnCheckedChangeListener {
	private RadioGroup mainTab;
	// private RadioButton rb_news, rb_msg, rb_about;
	private TabHost tabhost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_school_frame_layout);
		initView();
	}

	// 初始化底部按钮
	public void initView() {
		mainTab = (RadioGroup) findViewById(R.id.select_tabl_group);
		this.tabhost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// 新闻
		intent = new Intent(this, KindergartenDynamicNewsActivity.class);
		spec = tabhost.newTabSpec("news").setIndicator("news")
				.setContent(intent);
		tabhost.addTab(spec);

		// 活动
		intent = new Intent(this, KindergartenDynamicMsgActivity.class);
		spec = tabhost.newTabSpec("msg").setIndicator("msg").setContent(intent);
		tabhost.addTab(spec);

		// 关于
		intent = new Intent(this, KindergartenDynamicAboutActivity.class);
		spec = tabhost.newTabSpec("about").setIndicator("about")
				.setContent(intent);
		tabhost.addTab(spec);

		tabhost.setCurrentTab(0);

		mainTab.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.tab_news_id:// 新闻
			tabhost.setCurrentTabByTag("news");
			break;

		case R.id.tab_notice_id:// 通知
			tabhost.setCurrentTabByTag("msg");
			break;
		case R.id.tab_about_id:// 关于
			tabhost.setCurrentTabByTag("about");
			break;
		}
	}

}
