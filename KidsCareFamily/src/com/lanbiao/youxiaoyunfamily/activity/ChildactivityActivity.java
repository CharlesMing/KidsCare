package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.ChildactivityAdapter;
import com.lanbiao.youxiaoyunfamily.entity.ActivityContent;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;

public class ChildactivityActivity extends Activity {
	private Button btn_back;
	private ListView lv_list;
	private ChildactivityAdapter adapter;
	private ActivityContent activityContent;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.p_baby_activity_layout);
		appliction = (AppAppliction) getApplication();
		initView();
		initData();
	}

	public void initView() {
		btn_back = (Button) findViewById(R.id.btn_p_activity_back_id);
		lv_list = (ListView) findViewById(R.id.listv_pba_id);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_item_id = (TextView) view
						.findViewById(R.id.tv_activity_id);
				Intent intent = new Intent();
				intent.setClass(ChildactivityActivity.this,
						ChildactivityDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("itemid", (String) tv_item_id.getText());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	public void initData() {
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				strStu_id = info.getStudentId();
			}

			String strActivitylist = FamilyofBabyDynamicService
					.getActivityList(strStu_id);

			activityContent = JsonTools.getActivityList("results",
					strActivitylist);

			String title = activityContent.getActivitytitle();
			String content = activityContent.getActivitycontents();
			String logo = activityContent.getActivityimg();
			String time = activityContent.getActivitycreatetime();
			String id = activityContent.getActivityid();
			if (title == null) {
				showDialogInfo("暂无数据信息");
			} else {

				String[] strtitle = title.split("=");
				String[] strcontent = content.split("=");
				String[] strlogo = logo.split("=");
				String[] strtime = time.split("=");
				String[] strid = id.split("=");

				List<ActivityContent> list = new ArrayList<ActivityContent>();
				for (int i = 0; i < strtitle.length; i++) {
					list.add(new ActivityContent(strlogo[i], strtitle[i],
							strcontent[i], strtime[i], strid[i]));
				}

				adapter = new ChildactivityAdapter(this, list);
				lv_list.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂无数据提示框
	 * 
	 * @param msg
	 */
	private void showDialogInfo(String msg) {
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示");
		dialog.setMessage(msg);
		dialog.setPositiveButton("确定", noDataListener);
		dialog.setCancelable(false);
		dialog.show();
	}

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
