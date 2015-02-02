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
import com.lanbiao.youxiaoyunfamily.entity.Menu;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class ChildCareCentersNewActivity extends Activity {
	private Button btn_back;
	private TextView tv_title, tv_age, tv_price, tv_content;
	private ImageView iv_newsLogo;
	private Website website = new Website();
	private String path = "";
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_baby_shop_detail);
		initView();
		initData();
	}

	public void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_content = (TextView) findViewById(R.id.tv_introduce);
		iv_newsLogo = (ImageView) findViewById(R.id.iv_new_img);
		btn_back = (Button) findViewById(R.id.btn_t_main_quit_id);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initData() {
		Bundle bundle = this.getIntent().getExtras();
		String strSecondid = bundle.getString("itemid");
		// String result = FamilyofBabyDynamicService
		// .getChildCareByIdDetail(strSecondid);
		path = website.getPath() + website.getShoppingbyid()
				+ website.getShoppingid() + strSecondid;
		String result = HttpUtils.getJsonContent(path);
		menu = JsonTools.getListDatadetail("results", result);
		String title = menu.getName();
		String content = menu.getRemark();
		final String logo = menu.getLogo();
		// String time = menu.getTime();
		String price = menu.getPrice();
		String age = menu.getAge();
		tv_title.setText(title);
		tv_age.setText(age);
		tv_price.setText("гд" + price);
		runOnUiThread(new Runnable() {
			public void run() {
				ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
				iv_newsLogo.setTag(logo);
				imageDownloadTask.execute(logo, iv_newsLogo);
			}
		});

		tv_content.setText(content);
	}
}