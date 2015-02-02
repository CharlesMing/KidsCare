package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.BabyStudyRaise;
import com.lanbiao.youxiaoyunteacher.entity.StudyRaise;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.TbabyRaiseListAdapter;
import com.lanbiao.youxiaoyunteacher.service.StudyRaiseService;
import com.lanbiao.youxinteacher.R;

public class TbabyRaiseListItemActivity extends Activity {
	private static final String TAG = "TbabyRaiseListItemActivity";
	private ListView lv_raise;
	private TextView tv_title;
	private TbabyRaiseListAdapter adapter;
	private StudyRaise studyRaise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_raise_layout);
		initView();
		initData();
	}

	public void initView() {
		lv_raise = (ListView) findViewById(R.id.lv_t_raise_type_id);
		tv_title = (TextView) findViewById(R.id.tv_head_title);
	}

	public void initData() {
		Bundle bundle = this.getIntent().getExtras();
		String strSecondid = bundle.getString("secondid");
		String strtitle = bundle.getString("title");
		tv_title.setText(strtitle);
		String result = StudyRaiseService.getSecondByIdMeu(strSecondid);
		studyRaise = JsonTools.getListData("results", result);
		String title = studyRaise.getTitle();
		String content = studyRaise.getContent();
		String logo = studyRaise.getImgurl();
		String time = studyRaise.getTimes();
		String applytoclass = studyRaise.getApplytoclass();
		String contentid = studyRaise.getContentid();

		String[] strTitle = title.split("=");
		String[] strContent = content.split("=");
		String[] strLogo = logo.split("=");
		String[] strTime = time.split("=");
		String[] strApplyToClass = applytoclass.split("=");
		String[] strcontentid = contentid.split("=");

		List<BabyStudyRaise> list = new ArrayList<BabyStudyRaise>();
		for (int i = 0; i < strTitle.length; i++) {
			list.add(new BabyStudyRaise(strLogo[i], strTitle[i], strContent[i],
					strApplyToClass[i], strTime[i], strcontentid[i]));
		}
		adapter = new TbabyRaiseListAdapter(this, list, lv_raise);
		lv_raise.setAdapter(adapter);
		lv_raise.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_item_id = (TextView) view
						.findViewById(R.id.tv_item_id);
				Intent intent = new Intent();
				intent.setClass(TbabyRaiseListItemActivity.this,
						TbabyRaiseNewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("itemid", (String) tv_item_id.getText());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
