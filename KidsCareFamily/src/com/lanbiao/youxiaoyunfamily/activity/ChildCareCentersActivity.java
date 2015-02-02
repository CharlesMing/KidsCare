package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.Menu;
import com.lanbiao.youxiaoyunfamily.entity.TitleAndId;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class ChildCareCentersActivity extends Activity {
	private ExpandableListView elistview;
	private List<String> groupData;
	private Menu menu;
	private List<List<TitleAndId>> childrenData;
	private ExpandableAdapter adapter = new ExpandableAdapter();
	private Website website = new Website();
	private String path = "";
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String strSchool_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		appliction = (AppAppliction) getApplication();
		setContentView(R.layout.p_baby_shop_layout);
		elistview = (ExpandableListView) findViewById(R.id.expandlv_p_shop_type_id);

		loadData();
		if (menu.getFirstNameAndId() != null) {
			// 系统自带的图标去掉
			elistview.setGroupIndicator(null);
			elistview.setAdapter(adapter);
		}
	}

	private void loadData() {
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				strSchool_id = info.getSchoolId();
			}

			path = website.getPath() + website.getQueryshoppingmenu()
					+ website.getStrSchoolid() + strSchool_id;
			// 得到菜单项
			String results = HttpUtils.getJsonContent(path);
			// 解析菜单项
			menu = JsonTools.getMenu("results", results);
			// 得到父级菜单的名称和id
			String fNameAndId = menu.getFirstNameAndId();
			// 得到一级菜单id子级菜单name
			String sNameAndfId = menu.getSnameanfdid();
			// 二级菜单的名称和所对应的id 流行疾病=2efd154e-098e-4ddb-b808-d9ba4071d504
			String twoNameAndId = menu.getSecondNameAndId();
			if (fNameAndId == null) {
				showDialogInfo("暂无数据信息");
				return;
			} else {

				// 得到 二级菜单name、id
				String[] strTwoNameAndId = twoNameAndId.split(",");
				// 得到一级菜单name/id
				String[] strFNameAndId = fNameAndId.split(",");
				// 二级菜单name、一级菜单id
				String[] strSNameAndfId = sNameAndfId.split(",");

				// 父级(一级)菜单
				groupData = new ArrayList<String>();
				// 子(二级)菜单数组
				// childrenData = new ArrayList<List<String>>();
				childrenData = new ArrayList<List<TitleAndId>>();
				// 父级菜单
				for (int i = 0; i < strFNameAndId.length; i++) {
					String[] strFNameAndIds = strFNameAndId[i].split("=");
					// 这里是得到父级菜单的id 如果为0时是父级菜单的名称
					String fid = strFNameAndIds[0];
					// 定义一个二级菜单变量
					String sname = "";
					String strtwoid = "";
					// 这里得到子菜单的长度
					for (int j = 0; j < strSNameAndfId.length; j++) {
						// 截取子菜单id
						String[] strSNameAndIds = strSNameAndfId[j].split("=");
						String[] strTwoNameAndIds = strTwoNameAndId[j]
								.split("=");

						// STRing twoId =
						// 子菜单（二级菜单的id）
						String sid = strSNameAndIds[1];
						// 当子项id =父项id时将子项添加到父项菜单下
						if (sid.equals(fid)) {
							// 二级菜单的名称（标题）
							sname += strSNameAndIds[0] + ",";
							strtwoid += strTwoNameAndIds[1] + ",";
						}
					}
					// 生成父项(一级)菜单
					groupData.add(strFNameAndIds[1]);
					// 如果二级菜单的长度大于-1，则截取字符串左后一位数
					if (sname.length() > -1) {
						sname = sname.substring(0, sname.length() - 1);
					}
					String[] strtwoids = strtwoid.split(",");
					// 截取子菜单的字符串 [流行疾病, 日常生活, 急救知识]
					String[] strnames = sname.split(",");
					// 生成子项菜单

					List<TitleAndId> childTemp = new ArrayList<TitleAndId>();
					for (int j = 0; j < strnames.length; j++) {
						TitleAndId titleAndId = new TitleAndId();
						titleAndId.setStitle(strnames[j]);
						titleAndId.setSid(strtwoids[j]);
						childTemp.add(titleAndId);
					}
					childrenData.add(childTemp);

				}
			}
		} catch (Exception e) {
		}
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childrenData.get(groupPosition).get(childPosition);

		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			// 实例化布局文件
			LinearLayout clayout = (LinearLayout) LayoutInflater.from(
					ChildCareCentersActivity.this).inflate(
					R.layout.p_baby_shop_child_layout, null);

			TextView tv = (TextView) clayout.findViewById(R.id.ctv);
			final TextView tv_cid = (TextView) clayout
					.findViewById(R.id.tv_cid);
			tv.setText(((TitleAndId) getChild(groupPosition, childPosition))
					.getStitle());
			tv_cid.setText(((TitleAndId) getChild(groupPosition, childPosition))
					.getSid());
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ChildCareCentersActivity.this,
							ChildCareCentersListItemActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString("secondid", (String) tv_cid.getText());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

			return clayout;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childrenData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groupData.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return groupData.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			// 实例化布局文件
			RelativeLayout glayout = (RelativeLayout) LayoutInflater.from(
					ChildCareCentersActivity.this).inflate(
					R.layout.p_baby_shop_group_layout, null);
			ImageView iv = (ImageView) glayout.findViewById(R.id.giv);
			// 判断分组是否展开，分别传入不同的图片资源
			if (isExpanded) {
				iv.setImageResource(R.drawable.jian);
			} else {
				iv.setImageResource(R.drawable.jia);
			}
			TextView tv = (TextView) glayout.findViewById(R.id.gtv);
			tv.setText(this.getGroup(groupPosition).toString());
			return glayout;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
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
