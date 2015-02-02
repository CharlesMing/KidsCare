package com.lanbiao.youxiaoyunfamily.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.MyPageAdapter;
import com.lanbiao.youxiaoyunfamily.entity.Course;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

@SuppressWarnings("deprecation")
public class CourseViewPagerActivity extends Activity {

	private static final String TAG = null;

	List<View> listViews;

	Context context = null;

	LocalActivityManager manager = null;
	private Website website = new Website();
	private String path = "";
	private Course course;
	private TabHost tabHost = null;
	private TextView tv_monday, tv_tuesday, tv_wednesday, tv_thursday,
			tv_friday;
	static TextView tv_mondaytime;
	private TextView tv_tuesdaytime;
	private TextView tv_wednesdaytime;
	private TextView tv_thursdaytime;
	private TextView tv_fridaytime;
	private RelativeLayout tabIndicator_monday, tabIndicator_tuesday,
			tabIndicator_wednesday, tabIndicator_thursday, tabIndicator_friday;
	private ViewPager pager = null;
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strStu_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.course_viewpager);
		context = CourseViewPagerActivity.this;
		pager = (ViewPager) findViewById(R.id.viewpager);
		appliction = (AppAppliction) getApplication();
		// 定放一个放view的list，用于存放viewPager用到的view
		listViews = new ArrayList<View>();
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initView();
		InitData();
	}

	public void btn_back(View view) {
		finish();
	}

	public void initView() {
		Intent monday = new Intent(context, CourseMondayActivity.class);
		listViews.add(getView("monday", monday));

		Intent tuesday = new Intent(context, CourseTuesdayActivity.class);
		listViews.add(getView("tuesday", tuesday));

		Intent wednesday = new Intent(context, CourseWednesdayActivity.class);
		listViews.add(getView("wednesday", wednesday));

		Intent thursday = new Intent(context, CourseThursdayActivity.class);
		listViews.add(getView("thursday", thursday));

		Intent friday = new Intent(context, CourseFridayActivity.class);
		listViews.add(getView("friday", friday));

		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);

		// 这儿主要是自定义一下tabhost中的tab的样式
		tabIndicator_monday = (RelativeLayout) LayoutInflater.from(
				CourseViewPagerActivity.this).inflate(R.layout.tabwidget, null);
		tv_monday = (TextView) tabIndicator_monday.findViewById(R.id.tv_title);
		tv_mondaytime = (TextView) tabIndicator_monday
				.findViewById(R.id.tv_timeandday);

		tabIndicator_tuesday = (RelativeLayout) LayoutInflater.from(
				CourseViewPagerActivity.this).inflate(R.layout.tabwidget, null);
		tv_tuesday = (TextView) tabIndicator_tuesday
				.findViewById(R.id.tv_title);
		tv_tuesdaytime = (TextView) tabIndicator_tuesday
				.findViewById(R.id.tv_timeandday);

		tabIndicator_wednesday = (RelativeLayout) LayoutInflater.from(
				CourseViewPagerActivity.this).inflate(R.layout.tabwidget, null);
		tv_wednesday = (TextView) tabIndicator_wednesday
				.findViewById(R.id.tv_title);
		tv_wednesdaytime = (TextView) tabIndicator_wednesday
				.findViewById(R.id.tv_timeandday);

		tabIndicator_thursday = (RelativeLayout) LayoutInflater.from(
				CourseViewPagerActivity.this).inflate(R.layout.tabwidget, null);
		tv_thursday = (TextView) tabIndicator_thursday
				.findViewById(R.id.tv_title);
		tv_thursdaytime = (TextView) tabIndicator_thursday
				.findViewById(R.id.tv_timeandday);

		tabIndicator_friday = (RelativeLayout) LayoutInflater.from(
				CourseViewPagerActivity.this).inflate(R.layout.tabwidget, null);
		tv_friday = (TextView) tabIndicator_friday.findViewById(R.id.tv_title);
		tv_fridaytime = (TextView) tabIndicator_friday
				.findViewById(R.id.tv_timeandday);

		Intent intent = new Intent(context, EmptyActivity.class);
		// 注意这儿Intent中的activity不能是自身
		// 比如“A”对应的是T1Activity，后面intent就new的T3Activity的。
		tabHost.addTab(tabHost.newTabSpec("monday")
				.setIndicator(tabIndicator_monday).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("tuesday")
				.setIndicator(tabIndicator_tuesday).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("wednesday")
				.setIndicator(tabIndicator_wednesday).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("thursday")
				.setIndicator(tabIndicator_thursday).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("friday")
				.setIndicator(tabIndicator_friday).setContent(intent));

		pager.setAdapter(new MyPageAdapter(listViews));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 当viewPager发生改变时，同时改变tabhost上面的currentTab
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// 点击tabhost中的tab时，要切换下面的viewPager
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				if ("monday".equals(tabId)) {
					pager.setCurrentItem(0);
				}
				if ("tuesday".equals(tabId)) {

					pager.setCurrentItem(1);
				}
				if ("wednesday".equals(tabId)) {
					pager.setCurrentItem(2);
				}

				if ("thursday".equals(tabId)) {
					pager.setCurrentItem(3);
				}
				if ("friday".equals(tabId)) {
					pager.setCurrentItem(4);
				}
			}
		});

	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	public void InitData() {
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				strStu_id = info.getStudentId();
			}

			path = website.getPath() + website.getQuerycourse()
					+ website.getChildid() + strStu_id;
			String strFamilyid = HttpUtils.getJsonContent(path);
			course = JsonTools.getCourseInfo("results", strFamilyid);
			String time = course.getCoursetime();
			String[] strTime = time.split("=");
			for (int j = 0; j < strTime.length; j++) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = format.parse(strTime[j]);
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				getWeek(format1.format(date));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getWeek(String pTime) throws Exception {

		String Week = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		try {

			c.setTime(format.parse(pTime));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {

			Log.v(TAG, Week += "星期天");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			tv_mondaytime.setText(pTime.substring(5));
			tv_monday.setText(Week += "星期一");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			tv_tuesdaytime.setText(pTime.substring(5));
			tv_tuesday.setText(Week += "星期二");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			tv_wednesdaytime.setText(pTime.substring(5));
			tv_wednesday.setText(Week += "星期三");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			tv_thursdaytime.setText(pTime.substring(5));
			tv_thursday.setText(Week += "星期四");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			tv_fridaytime.setText(pTime.substring(5));
			tv_friday.setText(Week += "星期五");
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Log.v(TAG, Week += "星期六");
		}

		return Week;
	}
}
