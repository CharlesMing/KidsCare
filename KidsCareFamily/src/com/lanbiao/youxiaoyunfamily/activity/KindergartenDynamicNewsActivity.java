package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.NewsListAdapter;
import com.lanbiao.youxiaoyunfamily.entity.AboutUs;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.News;
import com.lanbiao.youxiaoyunfamily.entity.NewsTitleAndContent;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class KindergartenDynamicNewsActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "KindergartenDynamicNewsActivity";
	private Button btn_back;
	private TextView tv_school, tv_schoolwel;
	private ListView lv_list;
	private ImageView iv_img_id;
	private NewsListAdapter adapter;
	private News news;
	private Website website = new Website();
	private String path = "";
	private int typeno = 1;
	private AboutUs aboutUs;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;
	private String strSchool_name;
	private String strSchool_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_school_news_layout);
		appliction = (AppAppliction) getApplication();
		initView();
		initData();
	}

	public void initView() {
		iv_img_id = (ImageView) findViewById(R.id.imgv_p_snews_id);
		tv_schoolwel = (TextView) findViewById(R.id.txtv_p_news_id);
		tv_school = (TextView) findViewById(R.id.tv_schoolname);
		lv_list = (ListView) findViewById(R.id.listv_p_news_id);
		btn_back = (Button) findViewById(R.id.btn_p_school_back_id);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	public void initData() {
		new Thread() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						infos = appliction.infos;
						for (FamliyInfo info : infos) {
							strStu_id = info.getStudentId();
							strSchool_id = info.getSchoolId();
							strSchool_name = info.getSchoolName();
						}
						path = website.getPath() + website.getQuerynews()
								+ website.getTypeno() + typeno// type=1
								+ website.getStrcurrentschool() + strSchool_id;
						/** 新闻列表 */
						String newsInfo = HttpUtils.getJsonContent(path);

						String straboutus = FamilyofBabyDynamicService
								.aboutUs(strStu_id);
						aboutUs = JsonTools.getAboutus("results", straboutus);
						news = JsonTools.getNewsData("results", newsInfo);
						if (news.getTitle() == null) {
							// showDialogInfo("暂无数据信息");
							ShowToast.getToastInfo("暂无数据信息",
									KindergartenDynamicNewsActivity.this);
						} else {
							String title = news.getTitle();
							String content = news.getContent();
							String newsid = news.getNewsid();
							String[] strNewsId = newsid.split("=");
							String[] strTitle = title.split("=");
							String[] strContent = content.split("=");
							List<NewsTitleAndContent> list = new ArrayList<NewsTitleAndContent>();
							tv_schoolwel.setText(strSchool_name + "欢迎您！");
							tv_school.setText(strSchool_name);
							DisplayMetrics dm1 = new DisplayMetrics();
							ImageDownloadTask imgtask = new ImageDownloadTask();
							getWindowManager().getDefaultDisplay().getMetrics(
									dm1);
							imgtask.setDisplayWidth(dm1.widthPixels);
							imgtask.setDisplayHeight(dm1.heightPixels);
							imgtask.execute(aboutUs.getLogo(), iv_img_id);
							for (int i = 0; i < strTitle.length; i++) {
								list.add(new NewsTitleAndContent(strTitle[i],
										strContent[i], strNewsId[i]));
							}
							adapter = new NewsListAdapter(
									KindergartenDynamicNewsActivity.this, list,
									lv_list);
							lv_list.setAdapter(adapter);
							lv_list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									TextView tv_item_id = (TextView) view
											.findViewById(R.id.tv_newsid);
									Intent intent = new Intent();
									intent.setClass(
											KindergartenDynamicNewsActivity.this,
											KindergartenDynamicNewsDetailActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("itemid",
											(String) tv_item_id.getText());
									intent.putExtras(bundle);
									startActivity(intent);
								}
							});
						}
					}
				});
				super.run();
			}
		}.start();

	}

	/**
	 * 暂无数据提示框
	 * 
	 * @param msg
	 */
	// private void showDialogInfo(String msg) {
	// Builder dialog = new AlertDialog.Builder(this);
	// dialog.setTitle("提示");
	// dialog.setMessage(msg);
	// dialog.setPositiveButton("确定", noDataListener);
	// dialog.setCancelable(false);
	// dialog.show();
	// }

	/**
	 * 提示暂无数据
	 */
	public DialogInterface.OnClickListener noDataListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// 销毁当前的activity
			finish();
		}
	};
}
