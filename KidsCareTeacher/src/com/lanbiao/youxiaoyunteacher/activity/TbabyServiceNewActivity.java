package com.lanbiao.youxiaoyunteacher.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.entity.Menu;
import com.lanbiao.youxiaoyunteacher.entity.StudyRaise;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.ImageDownloadTask;
import com.lanbiao.youxiaoyunteacher.service.StudyRaiseService;
import com.lanbiao.youxinteacher.R;

public class TbabyServiceNewActivity extends Activity {
	private Button btn_back;
	private TextView tv_title, tv_age, tv_price, tv_content;
	private ImageView iv_newsLogo;
	private Menu menu;

	// private TbabyRaiseNewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_babay_shop_detail);
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

	// public void initView() {
	// tv_title = (TextView) findViewById(R.id.tv_new_title);
	// tv_applytoclass = (TextView) findViewById(R.id.tv_new_classid);
	// tv_time = (TextView) findViewById(R.id.tv_new_time);
	// tv_content = (TextView) findViewById(R.id.tv_new_content);
	// iv_newsLogo = (ImageView) findViewById(R.id.iv_new_img);
	// btn_back = (Button) findViewById(R.id.btn_t_main_quit_id);
	// btn_back.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// finish();
	// }
	// });
	// }

	public void initData() {
		Bundle bundle = this.getIntent().getExtras();
		String strSecondid = bundle.getString("itemid");
		String result = StudyRaiseService.getChildCareByIdDetail(strSecondid);

		menu = JsonTools.getRaiseListDatadetail("results", result);

		String title = menu.getName();
		String content = menu.getRemark();
		String logo = menu.getLogo();
		// String time = menu.getTime();
		String price = menu.getPrice();
		String age = menu.getAge();
		tv_title.setText(title);
		tv_age.setText(age);
		tv_price.setText("£§" + price);
		ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
		iv_newsLogo.setTag(logo);
		imageDownloadTask.execute(logo, iv_newsLogo);
		tv_content.setText(content);

		// studyRaise = JsonTools.getListdataDetail("results", result);
		// String title = studyRaise.getTitle();
		// String content = studyRaise.getContent();
		// String logo = studyRaise.getImgurl();
		// String time = studyRaise.getTimes();
		// String applytoclass = studyRaise.getApplytoclass();
		// tv_title.setText(title);
		// tv_applytoclass.setText("  ∫œ:" + applytoclass);

		// try {
		// SimpleDateFormat format = new SimpleDateFormat(
		// "yyyy-MM-dd HH:mm:ss");
		// Date date = format.parse(time);
		// SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		// tv_time.setText(format1.format(date));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
		// iv_newsLogo.setTag(logo);
		// imageDownloadTask.execute(logo, iv_newsLogo);
		// tv_content.setText(content);
	}
}
