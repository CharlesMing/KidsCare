package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.loadimgandadapter.LanguageAdapter;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.TbabySelectStuAdapter.ViewHolder;
import com.lanbiao.youxinteacher.R;

public class TestActivity extends Activity implements OnClickListener {
	static String[] str = { "data1", "data2", "data3", "data4", "data5",
			"data6", "7", "data8", "data9", "data10", "data11", "data12",
			"data13" };
	private int checkNum; // 记录选中的条目数量
	private ListView lv;
	private Button btn_select_all, btn_select_opposite;
	private Button btn_sure;
	private TextView tv_show_School;
	private LanguageAdapter adapter;
	ArrayList<HashMap<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_dynamic_layout);
		initView();
		initData();
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

		lv.setItemsCanFocus(false);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				ViewHolder h = (ViewHolder) view.getTag();
				/* 模拟使点击Item触发CheckBox事件 */
				if (h.cb.isChecked()) {
					adapter.mCBFlag.put(position, false);
				} else {
					adapter.mCBFlag.put(position, true);
				}
				adapter.notifyDataSetChanged();
			}

		});

	}

	public void initData() {
		final List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		adapter = new LanguageAdapter(getApplicationContext(), list);
		lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_t_dynamic_sure_id:
			int count = adapter.getCount();
			/* 与本Demo无关 */
			List<Integer> isCheckedCount = new ArrayList<Integer>();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < count; i++) {
				if (adapter.mCBFlag.get(i)) {
					sb.append("position：= " + i + " 内容 ：" + list.get(i))
							.append("\n");
					isCheckedCount.add(i);
				}
			}
			if (isCheckedCount.size() == 0) {
				showInfo("亲，你还没有选择哦~");
			} else {
				showInfo(sb.toString());
			}
			break;

		}
	}

	/**
	 * 提示
	 * 
	 * @param msg
	 */
	void showInfo(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
		builder.setTitle("提示信息");
		builder.setMessage(msg);
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

}
