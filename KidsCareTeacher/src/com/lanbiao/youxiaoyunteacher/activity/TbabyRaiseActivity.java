package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.StudyRaise;
import com.lanbiao.youxiaoyunteacher.entity.TitleAndId;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.StudyRaiseService;
import com.lanbiao.youxinteacher.R;

public class TbabyRaiseActivity extends Activity {
	private static final String TAG = "TbabyRaiseActivity";
	private ExpandableListView elistview;
	private List<String> groupData;
	private List<List<TitleAndId>> childrenData;
	StudyRaise studyRaise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_raise_expandable_layout);
		elistview = (ExpandableListView) findViewById(R.id.elist);

		loadData();
		// 系统自带的图标去掉
		elistview.setGroupIndicator(null);
		// elistview.setAdapter(new TbabyRaiseAdapter(this));
		elistview.setAdapter(new ExpandableAdapter());
	}

	private void loadData() {
		// 得到菜单项
		String results = StudyRaiseService.getOneMeu();
		// 解析菜单项
		studyRaise = JsonTools.getoneMenu("results", results);
		// 得到父级菜单的名称和id
		String fNameAndId = studyRaise.getFirstnameandid();
		// 得到子级菜单和id
		String sNameAndId = studyRaise.getNameandid();
		// 二级菜单的名称和所对应的id 流行疾病=2efd154e-098e-4ddb-b808-d9ba4071d504
		String twoNameAndId = studyRaise.getSecondnameandid();

		// 得到 流行疾病=2efd154e-098e-4ddb-b808-d9ba4071d504 格式
		String[] strTwoNameAndId = twoNameAndId.split(",");
		String[] strFNameAndId = fNameAndId.split(",");
		String[] strSNameAndId = sNameAndId.split(",");

		// 父级(一级)菜单
		groupData = new ArrayList<String>();
		// 子(二级)菜单数组
		// childrenData = new ArrayList<List<String>>();
		childrenData = new ArrayList<List<TitleAndId>>();
		// 父级菜单
		for (int i = 0; i < strFNameAndId.length; i++) {
			String[] strFNameAndIds = strFNameAndId[i].split("=");
			// 这里是得到父级菜单的id 如果为0时是父级菜单的名称
			String fid = strFNameAndIds[1];
			// 定义一个二级菜单变量
			String sname = "";
			String strtwoid = "";
			// 这里得到子菜单的长度
			for (int j = 0; j < strSNameAndId.length; j++) {
				// 截取子菜单id
				String[] strSNameAndIds = strSNameAndId[j].split("=");
				String[] strTwoNameAndIds = strTwoNameAndId[j].split("=");

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
			groupData.add(strFNameAndIds[0]);
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
					TbabyRaiseActivity.this).inflate(
					R.layout.t_raise_child_layout, null);

			final TextView tv = (TextView) clayout.findViewById(R.id.ctv);
			final TextView tv_cid = (TextView) clayout
					.findViewById(R.id.tv_cid);
			tv.setText(((TitleAndId) getChild(groupPosition, childPosition))
					.getStitle());
			tv_cid.setText(((TitleAndId) getChild(groupPosition, childPosition))
					.getSid());
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(TbabyRaiseActivity.this,
							TbabyRaiseListItemActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString("secondid", (String) tv_cid.getText());
					bundle.putString("title", (String) tv.getText());
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
					TbabyRaiseActivity.this).inflate(
					R.layout.t_raise_group_layout, null);
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

}
