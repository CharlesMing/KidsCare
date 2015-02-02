package com.lanbiao.youxiaoyunfamily.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.AppAppliction;
import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.adapter.DynamicAdapter;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.ImageAndText;
import com.lanbiao.youxiaoyunfamily.entity.MyBabyDynamic;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.refresh.PullDownListView;
import com.lanbiao.youxiaoyunfamily.refresh.PullDownListView.OnRefreshListioner;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class BabyDynamicActivity extends Activity implements OnRefreshListioner {
	@SuppressWarnings("unused")
	private static final String TAG = "BabyDynamic";
	private ListView lv;
	private RelativeLayout rl_nodata, rl_showdata;
	private TextView tv_nodata;
	private MyBabyDynamic dynamic;
	private DynamicAdapter adapter;
	private PullDownListView mPullDownView;
	private Handler mHandler = new Handler();
	List<ImageAndText> list = new ArrayList<ImageAndText>();
	private Website website = new Website();
	private String path = "";
	private AppAppliction appliction;
	private List<FamliyInfo> infos;
	private String str_fid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_baby_dynamic_layout);
		appliction = (AppAppliction) getApplication();
		InitView();
		InitData(list.size());

	}

	public void btn_back(View view) {
		finish();
	}

	public void InitView() {
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		rl_nodata = (RelativeLayout) findViewById(R.id.rl_nodata);
		rl_showdata = (RelativeLayout) findViewById(R.id.rl_showdata);
		mPullDownView = (PullDownListView) findViewById(R.id.sreach_list);
		lv = (ListView) this.findViewById(R.id.lv_dynamic);
		mPullDownView.setRefreshListioner(this);
	}

	public void InitData(int n) {
		n += list.size();
		try {
			infos = appliction.infos;
			for (FamliyInfo info : infos) {
				str_fid = info.getFamilyId();
			}
			path = website.getPath() + website.getQuerytrends()
					+ website.getFamilyid() + str_fid;
			String strFamilyid = HttpUtils.getJsonContent(path);
			dynamic = JsonTools.getBabyDynamic("results", strFamilyid);

			String evaluate = dynamic.getEvaluate();// 消息内容
			String sendtime = dynamic.getSendTime();// 发送时间
			String contentAndimg = dynamic.getContentAndimg();// 内容和照片
			String contentAndDynamic = dynamic.getContentAnddynamic();// 内容和宝贝动态

			/************* --NOUPDATA--START- *************/
			String head = dynamic.getStuhead();// 头像
			// String stuImage = dynamic.getStuImage();// 学习状态照片
			/************* --NOUPDATA--END- *************/
			if (evaluate == null) {
				rl_showdata.setVisibility(View.GONE);
				rl_nodata.setVisibility(View.VISIBLE);
				tv_nodata.setText("暂无动态信息");
			} else {
				rl_nodata.setVisibility(View.GONE);
				rl_showdata.setVisibility(View.VISIBLE);
				// String[] strEvaluate = evaluate.split("/");
				String[] strSendtime = sendtime.split("=");
				// 内容==生活照##内容==生活照
				String[] contentAndimg_Arrays = contentAndimg.split("##");
				// 内容==动态##内容==动态
				String[] contentAndDynamic_Arrays = contentAndDynamic
						.split("##");
				for (int i = 0; i < strSendtime.length; i++) {
					// 内容==生活照
					String contentAndimg_Array = contentAndimg_Arrays[i];
					// 内容==动态
					String contentAndDynamic_Array = contentAndDynamic_Arrays[i];
					String[] oneContentAndImg = contentAndimg_Array.split("==");
					String[] oneContentAndDynamic = contentAndDynamic_Array
							.split("==");
					// 内容
					String content = oneContentAndImg[0];
					// 照片
					String imgs = oneContentAndImg[1];
					String dynamic = oneContentAndDynamic[1];
					// Log.v(TAG, "img---" + contentAndimg_Array);
					// list.add(new ImageAndText(head, content, strSendtime[i],
					// imgs, strTwo[i]));
					list.add(new ImageAndText(head, content, strSendtime[i],
							imgs, dynamic));
				}
				adapter = new DynamicAdapter(this, lv, list);
				lv = mPullDownView.mListView;
				mPullDownView.setMore(true);//
				// 这里设置true表示还有更多加载，设置为false底部将不显示更多
				lv.setAdapter(adapter);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {

			public void run() {
				list.clear();
				// addLists(10);// 下拉时加载的数量 这里为10条
				InitData(5);
				mPullDownView.onRefreshComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
				mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
				adapter.notifyDataSetChanged();

			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				// addLists(5);// 每次加载五项新内容
				// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
				mPullDownView.onLoadMoreComplete();
				if (list.size() < list.size())
					// 判断当前list中已添加的数据是否小于最大值maxAount，是那么久显示更多否则不显示
					mPullDownView.setMore(true);//
				// 这里设置true表示还有更多加载，设置为false底部将不显示更多
				else
					mPullDownView.setMore(false);
				adapter.notifyDataSetChanged();

			}
		}, 1500);
	}
}
