package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.NoticeListAdapter;
import com.lanbiao.youxiaoyunfamily.entity.AboutUs;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Notice;
import com.lanbiao.youxiaoyunfamily.entity.NoticeTitleAndContent;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.loadimg.ImageDownloadTask;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class KindergartenDynamicMsgActivity extends Activity {
	// private static final String TAG = "KindergartenDynamicMsgActivity";
	private Button btn_back;
	private NoticeListAdapter adapter;
	private ListView lv_list;
	private ImageView iv_showimg;
	private TextView tv_schoolname, tv_schoolwel;
	private Notice notice;
	private AboutUs aboutUs;
	private Website website = new Website();
	private String path = "";
	private int type = 2;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;
	private String strSchool_name;
	private String strFamliy_id;
	private String strBaby_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_school_notice_layout);
		appliction = (AppAppliction) getApplication();
		initView();
		initData();
	}

	public void initView() {
		iv_showimg = (ImageView) findViewById(R.id.imgv_p_snews_id);
		tv_schoolname = (TextView) findViewById(R.id.tv_schoolname);
		tv_schoolwel = (TextView) findViewById(R.id.txtv_p_notice_id);
		lv_list = (ListView) findViewById(R.id.listv_p_notice_id);
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
								strSchool_name = info.getSchoolName();
								strFamliy_id = info.getFamilyId();
								strBaby_name = info.getStudentName();
							}
							path = website.getPath() + website.getQuerynews()
									+ website.getTypeno() + type// type=2
									+ website.getStrcurrentschool() + strStu_id;
							String strMsgInfo = HttpUtils.getJsonContent(path);
							notice = JsonTools.getMsgInfoData("results",
									strMsgInfo);
							String straboutus = FamilyofBabyDynamicService
									.aboutUs(strStu_id);
							aboutUs = JsonTools.getAboutus("results",
									straboutus);
							String name = notice.getName();
							String activityid = notice.getActivityid();
							String content = notice.getContent();
							if (name != null) {

								String[] strname = name.split("=");
								String[] stractivityid = activityid.split("=");
								String[] strcontent = content.split("=");
								List<NoticeTitleAndContent> list = new ArrayList<NoticeTitleAndContent>();
								tv_schoolwel.setText(strSchool_name + "»¶Ó­Äú!");
								tv_schoolname.setText(strSchool_name);
								DisplayMetrics dm1 = new DisplayMetrics();
								ImageDownloadTask imgtask = new ImageDownloadTask();
								getWindowManager().getDefaultDisplay()
										.getMetrics(dm1);
								imgtask.setDisplayWidth(dm1.widthPixels);
								imgtask.setDisplayHeight(dm1.heightPixels);
								imgtask.execute(aboutUs.getLogo(), iv_showimg);
								for (int i = 0; i < strname.length; i++) {
									list.add(new NoticeTitleAndContent(
											strname[i], strcontent[i],
											stractivityid[i]));
								}
								adapter = new NoticeListAdapter(
										KindergartenDynamicMsgActivity.this,
										list, lv_list);
								lv_list.setAdapter(adapter);
								lv_list.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										TextView tv_item_id = (TextView) view
												.findViewById(R.id.tv_newsid);
										Intent intent = new Intent();
										intent.setClass(
												KindergartenDynamicMsgActivity.this,
												KindergartenDynamicMsgDetailActivity.class);
										Bundle bundle = new Bundle();
										bundle.putString("familyid",
												strFamliy_id);
										bundle.putString("stuid", strStu_id);
										bundle.putString("name", strBaby_name);
										bundle.putString("itemid",
												(String) tv_item_id.getText());
										intent.putExtras(bundle);
										startActivity(intent);
									}
								});

							} else {
								ShowToast.getToastInfo("ÔÝÎÞÊý¾Ý",
										KindergartenDynamicMsgActivity.this);
							}
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
