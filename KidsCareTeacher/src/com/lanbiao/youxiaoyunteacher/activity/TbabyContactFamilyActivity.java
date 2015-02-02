package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.Contact;
import com.lanbiao.youxiaoyunteacher.entity.Familyinfo;
import com.lanbiao.youxiaoyunteacher.entity.ImageAndText;
import com.lanbiao.youxiaoyunteacher.entity.Studentinfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.PhoneListAdapter;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MyStudentService;
import com.lanbiao.youxinteacher.R;

/**
 * 如何查询到phone
 * 
 * @author my
 * 
 */
public class TbabyContactFamilyActivity extends Activity {
	private static final String TAG = "MyClassPhoneActivity";
	private JSONObject jo;
	private Studentinfo studentinfo;
	private Familyinfo familyinfo;
	private Classinfo classinfo;
	private ListView lv_list;
	private Button btn_back;
	private Contact contact;
	private PhoneListAdapter adapter;

	// 适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_myclassphone_layout);
		initView();
		initData();

	}

	// 把学生名字和头像存为一个数组
	private void initData() {
		try {
			Intent intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			String strteacherinfo = LoginService.login(userName, userPwd);
			classinfo = JsonTools.getClassId("results", strteacherinfo);
			String familyphone = MyStudentService
					.getstubyfamilycontactinfo(classinfo.getId());
			contact = JsonTools.getStuinfo("results", familyphone);
			String name = contact.getName();
			String head = contact.getHead();
			String id = contact.getId();

			String[] strname = name.split("=");
			String[] strhead = head.split("=");
			String[] strid = id.split("=");
			List<ImageAndText> list = new ArrayList<ImageAndText>();
			for (int i = 0; i < strname.length; i++) {
				// list.add(new ImageAndText(strhead[i], strid[i], strname[i]));
				list.add(new ImageAndText(strhead[i], strid[i], strname[i],
						userName, userPwd));
			}
			adapter = new PhoneListAdapter(this, list, lv_list);
			lv_list.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initView() {
		lv_list = (ListView) findViewById(R.id.lv_t_myclass_phone_id);// listview
		btn_back = (Button) findViewById(R.id.btn_t_class_back_id);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
