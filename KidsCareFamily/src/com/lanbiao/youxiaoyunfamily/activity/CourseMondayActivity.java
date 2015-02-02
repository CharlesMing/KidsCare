package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Course;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

/**
 * 星期一
 * 
 * @author my
 * 
 */
public class CourseMondayActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "CourseMondayActivity";
	private ListView lv_am, lv_pm;
	private Website website = new Website();
	private String path = "";
	private Course course;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_baby_class_item_layout);
		appliction = (AppAppliction) getApplication();
		initView();
		InitData();
	}

	public void InitData() {
		new Thread() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						try {
							infos = appliction.infos;
							for (FamliyInfo info : infos) {
								strStu_id = info.getStudentId();
							}
							path = website.getPath() + website.getQuerycourse()
									+ website.getChildid() + strStu_id;
							String strFamilyid = HttpUtils.getJsonContent(path);
							course = JsonTools.getCourseInfo("results",
									strFamilyid);
							String am = course.getAm();
							String pm = course.getPm();
							if (am == null) {
								// showDialogInfo("暂无数据信息");
								ShowToast.getToastInfo("暂无数据信息",
										CourseMondayActivity.this);
							} else {

								String[] strAmAndDay = am.split("=");
								String[] strPmAndDay = pm.split("=");
								/*************************** 上午 *******************************/
								ArrayList<HashMap<String, Object>> anListItem = new ArrayList<HashMap<String, Object>>();
								for (int i = 0; i < strAmAndDay.length; i++) {
									// 解析上午的数据和星期
									String[] strAmAndDays = strAmAndDay[i]
											.split("##");
									String strDay = strAmAndDays[1];
									String strAms = strAmAndDays[0];
									if (strDay.equals("1")) {
										String[] strAm = strAms.split(",");
										for (int j = 0; j < strAm.length; j++) {
											HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("ItemTitleam", strAm[j]);
											anListItem.add(map);
										}
									}
								}
								SimpleAdapter listItemAdapteram = new SimpleAdapter(
										CourseMondayActivity.this, anListItem,
										R.layout.courseitem,
										new String[] { "ItemTitleam" },
										new int[] { R.id.tv });
								lv_am.setAdapter(listItemAdapteram);
								/*************************** 上午 *******************************/

								/*************************** 下午 *******************************/
								ArrayList<HashMap<String, Object>> pnListItem = new ArrayList<HashMap<String, Object>>();
								for (int i = 0; i < strPmAndDay.length; i++) {
									String[] strPmAndDays = strPmAndDay[i]
											.split("##");
									String strPms = strPmAndDays[0];
									String strDay = strPmAndDays[1];
									if (strDay.equals("1")) {
										String[] strPm = strPms.split(",");
										for (int j = 0; j < strPm.length; j++) {
											HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("ItemTitlepm", strPm[j]);
											pnListItem.add(map);
										}

									}
								}

								SimpleAdapter listItemAdapterpm = new SimpleAdapter(
										CourseMondayActivity.this, pnListItem,
										R.layout.courseitem,
										new String[] { "ItemTitlepm" },
										new int[] { R.id.tv });
								lv_pm.setAdapter(listItemAdapterpm);

								/*************************** 下午 *******************************/
							}
						} catch (Exception e) {
							e.printStackTrace();
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

	public void initView() {
		lv_am = (ListView) this.findViewById(R.id.lv_am_id);
		lv_pm = (ListView) this.findViewById(R.id.lv_pm_id);
	}

}
