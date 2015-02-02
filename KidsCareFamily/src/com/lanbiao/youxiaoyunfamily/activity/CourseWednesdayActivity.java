package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Course;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

/**
 * 星期三
 * 
 * @author my
 * 
 */
public class CourseWednesdayActivity extends Activity {
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
							String[] strAmAndDay = am.split("=");
							String[] strPmAndDay = pm.split("=");
							/**
							 * 1.先循环解析所有数据 2.判断下标是星期几 如果是当前页面的星期，显示当前页面星期
							 */

							/*************************** 上午 *******************************/
							ArrayList<HashMap<String, Object>> anListItem = new ArrayList<HashMap<String, Object>>();
							for (int i = 0; i < strAmAndDay.length; i++) {
								// 解析上午的数据和星期
								String[] strAmAndDays = strAmAndDay[i]
										.split("##");
								String strDay = strAmAndDays[1];
								String strAms = strAmAndDays[0];
								if (strDay.equals("3")) {
									String[] strAm = strAms.split(",");
									for (int j = 0; j < strAm.length; j++) {
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("ItemTitleam", strAm[j]);
										anListItem.add(map);
									}
								}
							}
							SimpleAdapter listItemAdapteram = new SimpleAdapter(
									CourseWednesdayActivity.this, anListItem,
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
								if (strDay.equals("3")) {
									String[] strPm = strPms.split(",");
									for (int j = 0; j < strPm.length; j++) {
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("ItemTitlepm", strPm[j]);
										pnListItem.add(map);
									}

								}
							}

							SimpleAdapter listItemAdapterpm = new SimpleAdapter(
									CourseWednesdayActivity.this, pnListItem,
									R.layout.courseitem,
									new String[] { "ItemTitlepm" },
									new int[] { R.id.tv });
							lv_pm.setAdapter(listItemAdapterpm);
							/*************************** 下午 *******************************/

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				super.run();
			}
		}.start();

	}

	public void initView() {
		lv_am = (ListView) this.findViewById(R.id.lv_am_id);
		lv_pm = (ListView) this.findViewById(R.id.lv_pm_id);
	}

}
