package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.entity.BabyStudyRaise;
import com.lanbiao.youxiaoyunteacher.entity.Menu;
import com.lanbiao.youxiaoyunteacher.entity.StudyRaise;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.TbabyRaiseListAdapter;
import com.lanbiao.youxiaoyunteacher.service.StudyRaiseService;
import com.lanbiao.youxinteacher.R;

public class TbabyServiceListItemActivity extends Activity {
	private static final String TAG = "TbabyRaiseListItemActivity";
	private ListView lv_raise;
	private TextView tv_head;
	private TbabyRaiseListAdapter adapter;
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_service_item_layout);
		initView();
		initData();
	}

	public void initView() {
		lv_raise = (ListView) findViewById(R.id.lv_t_raise_type_id);
		tv_head = (TextView) findViewById(R.id.tv_head_title);
	}

	public void initData() {
		Bundle bundle = this.getIntent().getExtras();
		String strSecondid = bundle.getString("secondid");
		String strtitle = bundle.getString("title");
		tv_head.setText(strtitle);
		String result = StudyRaiseService.getChildCareDetail(strSecondid);
		menu = JsonTools.getRaiseListData("results", result);

		String title = menu.getName();
		String remark = menu.getRemark();
		String logo = menu.getLogo();
		String time = menu.getTime();
		String age = menu.getAge();
		String shopid = menu.getShoppingid();

		String[] strTitle = title.split("=");
		String[] strRemark = remark.split("=");
		String[] strLogo = logo.split("=");
		String[] strTime = time.split("=");
		String[] strAge = age.split("=");
		String[] strShopId = shopid.split("=");

		// List<BabyStudyRaise> list = new ArrayList<BabyStudyRaise>();
		// for (int i = 0; i < strTitle.length; i++) {
		// list.add(new BabyStudyRaise(strLogo[i], strTitle[i], strContent[i],
		// strApplyToClass[i], strTime[i], strcontentid[i]));
		// }

		List<BabyStudyRaise> list = new ArrayList<BabyStudyRaise>();
		for (int i = 0; i < strTitle.length; i++) {
			// list.add(new BabyStudyRaise(strLogo[i], strTitle[i],
			// strRemark[i],
			// strTime[i], strShopId[i], strAge[i]));
			list.add(new BabyStudyRaise(strLogo[i], strTitle[i], strRemark[i],
					strAge[i], strTime[i], strShopId[i]));
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
				intent.setClass(TbabyServiceListItemActivity.this,
						TbabyServiceNewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("itemid", (String) tv_item_id.getText());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
