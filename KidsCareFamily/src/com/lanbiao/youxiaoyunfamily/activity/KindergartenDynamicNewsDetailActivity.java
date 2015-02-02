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
import com.lanbiao.youxiaoyunfamily.entity.News;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class KindergartenDynamicNewsDetailActivity extends Activity {
	private TextView tv_title, tv_content;
	private ImageView iv_head;
	private Button btn_back;
	private Website website = new Website();
	private String path = "";
	private int typeno = 1;
	private News news;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_school_news_detail);
		initView();
		initData();

	}

	public void initView() {
		btn_back = (Button) findViewById(R.id.btn_t_main_quit_id);
		tv_title = (TextView) findViewById(R.id.tv_new_title);
		tv_content = (TextView) findViewById(R.id.tv_new_content);
		iv_head = (ImageView) findViewById(R.id.iv_new_img);

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
		// String strnewsDetail = FamilyofBabyDynamicService
		// .getNewsInfoDetail(strSecondid);
		path = website.getPath() + website.getQuerynewsdetail()
				+ website.getTypeno() + typeno + website.getStrnewsid()
				+ strSecondid;
		String strnewsDetail = HttpUtils.getJsonContent(path);
		news = JsonTools.getNewsDataDetail("results", strnewsDetail);
		String title = news.getTitle();
		String content = news.getContent();
		final String imgurl = news.getImgurl();

		tv_title.setText(title);
		tv_content.setText(content);
		runOnUiThread(new Runnable() {
			public void run() {
				ImageDownloadTask imgtask = new ImageDownloadTask();
				imgtask.execute(imgurl, iv_head);
			}
		});

	}
}
