package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.ImageAndText;
import com.lanbiao.youxiaoyunteacher.entity.Studentinfo;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.ImageAndTextListAdapter;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MyClassService;
import com.lanbiao.youxiaoyunteacher.service.MyStudentService;
import com.lanbiao.youxinteacher.R;

public class TbabyMyStudentsActivity extends Activity implements
		OnClickListener, OnItemClickListener {
	private static final String TAG = "MyStudents";
	private ListView lv;
	private Button btn_select_all, btn_select_opposite;
	private Button btn_sure;
	private TextView tv_show_School;
	private JSONObject jo;
	// private Studentinfo studentinfo;
	private Classinfo classinfo;

	private String userName;
	private String userPwd;
	// 适配器
	ImageAndTextListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_dynamic_layout);
		initView();
		initData();
	}

	// 把学生名字和头像存为一个数组
	private void initData() {
		try {
			Intent intent = getIntent();
			userName = intent.getStringExtra("username");
			userPwd = intent.getStringExtra("pwd");
			String result = LoginService.login(userName, userPwd);
			classinfo = JsonTools.getClassId("results", result);
			String myStus = MyClassService.myClassGetId(classinfo.getId());// 得到教师的所有学生
			classinfo = JsonTools.getClasslistKeyMaps("results", myStus);// 解析教师的所欲数据
			String classIdInfo = classinfo.getClassid().substring(0,
					classinfo.getClassid().length() - 1);// 去掉最后一位“，”
			String myclassId = MyStudentService.myStudentInfo(classIdInfo);
			Studentinfo studentinfo = JsonTools.getStudentlistKeyMaps(
					"results", myclassId);

			String classname = classinfo.getClassname().substring(0,
					classinfo.getClassname().length() - 1);
			tv_show_School.setText(classname);
			List<ImageAndText> list = new ArrayList<ImageAndText>();
			String url = studentinfo.getHead();
			String name = studentinfo.getName();
			String stuId = studentinfo.getSid();
			String[] strStuId = stuId.split(",");
			String[] strUrl = url.split(",");
			String[] strName = name.split(",");
			for (int i = 0; i < strName.length; i++) {
				list.add(new ImageAndText(strUrl[i], strStuId[i], strName[i]));
			}
			adapter = new ImageAndTextListAdapter(this, list, lv);
			lv.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initView() {
		tv_show_School = (TextView) findViewById(R.id.tv_schoolname);
		lv = (ListView) findViewById(R.id.lv_t_dynamic_bottom_id);// listview
		btn_select_all = (Button) findViewById(R.id.btn_t_dynamic_select_all_id);// 全选
		btn_select_opposite = (Button) findViewById(R.id.btn_t_dynamic_select_opposite_id);// 反选
		btn_sure = (Button) findViewById(R.id.btn_t_dynamic_sure_id);
		btn_sure.setOnClickListener(this);
		btn_select_all.setOnClickListener(this);
		btn_select_opposite.setOnClickListener(this);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Map<Integer, Boolean> state = adapter.state;
		switch (v.getId()) {
		case R.id.btn_t_dynamic_sure_id:// 确定
			String name = "";
			String id = "";
			for (int i = 0; i < adapter.getCount(); i++) {
				if (state.get(i) != null) {
					ImageAndText imageAndText = adapter.getItem(i);
					String text = imageAndText.getName();
					String sid = imageAndText.getStuId();
					if (name.indexOf(text) < 0) {
						name += text + ",";
					}
					if (id.indexOf(sid) < 0) {
						id += sid + ",";
					}
				}
			}
			if (name == "") {
				Toast.makeText(getApplicationContext(), "请选择宝贝", 1).show();
			} else {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				// 解析到classid putInt ("id")
				bundle.putString("username", userName);
				bundle.putString("pwd", userPwd);
				intent.putExtras(bundle);
				intent.putExtra("ids", id);
				intent.putExtra("names", name);
				intent.setClass(getApplicationContext(),
						TbabyDynamicDetailActivity.class);
				adapter.checkNone();
				startActivity(intent);

			}
			break;
		case R.id.btn_t_dynamic_select_all_id:// 全选
			adapter.checkAll();
			break;
		case R.id.btn_t_dynamic_select_opposite_id:// 全不选
			adapter.checkNone();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(this, "a", 0).show();
	}

}
