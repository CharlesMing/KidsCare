package com.lanbiao.youxiaoyunfamily.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.ChildCareCentersListAdapter;
import com.lanbiao.youxiaoyunfamily.adapter.ChildCareCentersQueryListAdapter;
import com.lanbiao.youxiaoyunfamily.entity.BabyStudyRaise;
import com.lanbiao.youxiaoyunfamily.entity.JError;
import com.lanbiao.youxiaoyunfamily.entity.Menu;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.json.JsonError;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

/**
 * 育儿中心
 * 
 * @author
 * 
 */
public class ChildCareCentersListItemActivity extends Activity implements
		OnClickListener {
	private ListView lv_raise, lv_queryList;
	private EditText etText;
	private Button btn_search, btn_commitSerach, btn_gone;
	private LinearLayout ll_search;
	private ChildCareCentersListAdapter adapter;
	private Menu menu;
	private Website website = new Website();
	private JError jError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_raise_layout);
		initView();
		initData();
	}

	public void initView() {
		btn_gone = (Button) findViewById(R.id.btn_t_main_quitclose_id);
		etText = (EditText) findViewById(R.id.et_serach_id);
		btn_commitSerach = (Button) findViewById(R.id.btn_t_content_quit_id);
		ll_search = (LinearLayout) findViewById(R.id.ll_serach_id);
		lv_queryList = (ListView) findViewById(R.id.lv_t_queryraise_type_id);
		lv_raise = (ListView) findViewById(R.id.lv_t_raise_type_id);
		btn_search = (Button) findViewById(R.id.btn_t_main_quit_id);
		btn_commitSerach.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		btn_gone.setOnClickListener(this);
		btn_search.setVisibility(View.VISIBLE);
		lv_raise.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_item_id = (TextView) view
						.findViewById(R.id.tv_item_id);
				Intent intent = new Intent();
				intent.setClass(ChildCareCentersListItemActivity.this,
						ChildCareCentersNewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("itemid", (String) tv_item_id.getText());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		lv_queryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				TextView tv_item_id = (TextView) view
						.findViewById(R.id.tv_item_id);
				Intent intent = new Intent();
				intent.setClass(ChildCareCentersListItemActivity.this,
						ChildCareCentersNewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("itemid", (String) tv_item_id.getText());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	public void initData() {

		Bundle bundle = this.getIntent().getExtras();
		String strSecondid = bundle.getString("secondid");
		// String result = FamilyofBabyDynamicService
		// .getChildCareDetail(strSecondid);

		String path = website.getPath() + website.getShoppingdetail()
				+ website.getSecondmunuid() + strSecondid;
		String result = HttpUtils.getJsonContent(path);
		jError = JsonError.getBackExceptionInfo(result);
		int code = jError.getCode();
		if (code == 1) {
			shoToastInfo("暂无数据");
			return;
		} else {
			menu = JsonTools.getListData("results", result);
			// 标题
			String title = menu.getName();
			// 内容
			String remark = menu.getRemark();
			// 图片
			String logo = menu.getLogo();
			// 发布时间
			String time = menu.getTime();
			// 适合年龄
			String age = menu.getAge();
			// id
			String shopid = menu.getShoppingid();

			String[] strTitle = title.split("=");
			String[] strRemark = remark.split("=");
			String[] strLogo = logo.split("=");
			String[] strTime = time.split("=");
			String[] strAge = age.split("=");
			String[] strShopId = shopid.split("=");

			List<BabyStudyRaise> list = new ArrayList<BabyStudyRaise>();
			for (int i = 0; i < strTitle.length; i++) {
				list.add(new BabyStudyRaise(strLogo[i], strTitle[i],
						strRemark[i], strTime[i], strShopId[i], strAge[i]));
			}

			adapter = new ChildCareCentersListAdapter(this, list, lv_raise);
			lv_raise.setAdapter(adapter);
			lv_raise.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * 提示信息
	 * 
	 * @param msg
	 */
	public void shoToastInfo(String msg) {
		Toast toast = null;
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		}
		toast.show();
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_t_content_quit_id:// 提交输入的数据，并搜索
			lv_raise.setVisibility(View.GONE);
			seracheDatas();
			lv_queryList.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_t_main_quit_id:
			// 点击搜索按钮 显示输入框
			btn_search.setVisibility(View.GONE);
			btn_gone.setVisibility(View.VISIBLE);
			// 点击搜索按钮 显示输入框
			ll_search.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_t_main_quitclose_id:
			btn_gone.setVisibility(View.GONE);
			btn_search.setVisibility(View.VISIBLE);
			// 点击搜索按钮 显示输入框
			ll_search.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 显示搜素结果
	 */
	@SuppressWarnings("deprecation")
	public void seracheDatas() {
		try {
			Bundle bundle = this.getIntent().getExtras();
			// 二级菜单id
			String strSecondid = bundle.getString("secondid");
			String serach_text = etText.getText().toString().trim();
			serach_text = URLEncoder.encode(serach_text);
			// 调用接口，并返回结果
			// String Strresult = FamilyofBabyDynamicService.queryServiceDatas(
			// strSecondid, serach_text);

			String path = website.getPath() + website.getShoppingdetail()
					+ website.getSecondmunuid() + strSecondid
					+ website.getShopName() + serach_text;
			String Strresult = HttpUtils.getJsonContent(path);
			jError = JsonError.getBackExceptionInfo(Strresult);
			int code = jError.getCode();
			if (code == 1) {
				ShowToast.getToastInfo("暂无数据信息",
						ChildCareCentersListItemActivity.this);
				finish();
				return;
			} else {
				Menu menu = JsonTools
						.getQueryServiceDatas("results", Strresult);
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

				List<BabyStudyRaise> list = new ArrayList<BabyStudyRaise>();
				for (int i = 0; i < strTitle.length; i++) {
					list.add(new BabyStudyRaise(strLogo[i], strTitle[i],
							strRemark[i], strTime[i], strShopId[i], strAge[i]));
				}
				// 写入适配器
				ChildCareCentersQueryListAdapter adapter = new ChildCareCentersQueryListAdapter(
						this, list, lv_queryList);
				lv_queryList.setAdapter(adapter);
				lv_queryList.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
