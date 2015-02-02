package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.activity.TestListView;
import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.Contact;
import com.lanbiao.youxiaoyunteacher.entity.ImageAndText;
import com.lanbiao.youxiaoyunteacher.entity.TestListphone;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MyStudentService;
import com.lanbiao.youxinteacher.R;

public class PhoneListAdapter extends ArrayAdapter<ImageAndText> {
	// private int selectedFruitIndex = 0;
	private static final String TAG = "ImageAndTextListAdapter";
	private ListView listView;
	// private AsyncImageLoader asyncImageLoader;
	List<ImageAndText> listData;
	private Classinfo classinfo;
	private Contact contact;

	public PhoneListAdapter(Activity activity,
			List<ImageAndText> imageAndTexts, ListView listView) {
		super(activity, 0, imageAndTexts);
		this.listView = listView;
		this.listData = imageAndTexts;
		// asyncImageLoader = new AsyncImageLoader();
	}

	public View getView(final int position, final View convertView,
			ViewGroup parent) {
		final Activity activity = (Activity) getContext();
		View rowView = convertView;
		ViewHolder holder = null;
		holder = new ViewHolder();
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(
					R.layout.t_baby_class_telphone_lv_item_dialog_layout, null);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		ImageAndText imageAndText = getItem(position);

		ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
		String imageUrl = imageAndText.getImageUrl();// 图片网址
		holder.img = (ImageView) rowView
				.findViewById(R.id.imgv_remind_lv_item_icon_id);// 头像
		holder.img.setTag(imageUrl);
		imageDownloadTask.execute(imageUrl, holder.img);

		holder.tv_name = (TextView) rowView
				.findViewById(R.id.txtv_lv_item_name_id);// 名字
		holder.tv_name.setText(imageAndText.getName());
		holder.tv_username = (TextView) rowView.findViewById(R.id.tv_username);// 用户名
		holder.tv_username.setText(imageAndText.getUsername());
		holder.tv_pwd = (TextView) rowView.findViewById(R.id.tv_pwd);// 密码
		holder.tv_pwd.setText(imageAndText.getPwd());

		holder.tv_no = (TextView) rowView
				.findViewById(R.id.txtv_remind_lv_item_phone_id);// id
		holder.tv_no.setText(imageAndText.getStuId());
		holder.ll = (LinearLayout) rowView.findViewById(R.id.ll_list);
		holder.ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView tv_item, tv_username, tv_pwd;
				// Intent intent = new Intent();
				// intent.setClass(getContext(), TestListView.class);
				// getContext().startActivity(intent);
				tv_item = (TextView) v
						.findViewById(R.id.txtv_remind_lv_item_phone_id);
				tv_username = (TextView) v.findViewById(R.id.tv_username);
				tv_pwd = (TextView) v.findViewById(R.id.tv_pwd);
				// Intent intent = new Intent(getContext(), TestListView.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("secondid", (String) tv_item.getText());
				// intent.putExtras(bundle);
				// getContext().startActivity(intent);
				// Toast.makeText(getContext(), tv_item.getText(), 0).show();
				LayoutInflater layoutInflater = LayoutInflater
						.from(getContext());
				View myupdateView = layoutInflater.inflate(
						R.layout.t_baby_class_telphone_lv_dialog_layout, null);
				// myupdateView.setBackgroundColor(Color.BLACK);
				ListView lv = (ListView) myupdateView
						.findViewById(R.id.lv_t_baby_class_phone_dialog_id);
				ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
				String username = (String) tv_username.getText();
				String pwd = (String) tv_pwd.getText();
				String strteacherinfo = LoginService.login(username, pwd);
				classinfo = JsonTools.getClassId("results", strteacherinfo);
				String familyphone = MyStudentService
						.getstubyfamilycontactinfo(classinfo.getId());
				contact = JsonTools.getStufamilinfo("results", familyphone);
				String IdandphoneandchildRegion = contact
						.getIdandphoneandchildRegion();
				String[] strIdandphoneandchildRegion = IdandphoneandchildRegion
						.split("。");
				String phoneandids = "";

				for (int j = 0; j < strIdandphoneandchildRegion.length; j++) {
					String[] str = strIdandphoneandchildRegion[j].split(",");
					String ids = str[1];
					if (tv_item.getText().equals(ids)) {
						phoneandids += str[0] + ",";// 号码和关系
					}
				}
				String[] strname = phoneandids.split(",");// 截取号码与家长的关系
				for (int j = 0; j < strname.length; j++) {
					HashMap<String, String> map = new HashMap<String, String>();
					String[] phoneandid = strname[j].split("=");
					String phone = phoneandid[0];// 号码
					String phoneregion = phoneandid[1];// 所属关系
					int judge = Integer.parseInt(phoneregion);
					switch (judge) {
					case 0:
						phoneregion = "其他：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 1:
						phoneregion = "爸爸：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 2:
						phoneregion = "妈妈：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 3:
						phoneregion = "爷爷：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 4:
						phoneregion = "奶奶：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 5:
						phoneregion = "外公：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					case 6:
						phoneregion = "外婆：";
						map.put("ItemPhone", phone);
						map.put("Itemregion", phoneregion);
						listItem.add(map);
						break;
					}

				}
				SimpleAdapter listAdapter = new SimpleAdapter(getContext(),
						listItem,
						R.layout.t_baby_class_telphone_item_dialog_layout,
						new String[] { "ItemPhone", "Itemregion" }, new int[] {
								R.id.tv_phone_no_id, R.id.tv_family_id });
				lv.setAdapter(listAdapter);

				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						TextView tv_item = (TextView) view
								.findViewById(R.id.tv_phone_no_id);
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ tv_item.getText()));
						getContext().startActivity(phoneIntent);
					}

				});
				new AlertDialog.Builder(getContext()).setView(myupdateView)
						.show();
			}
		});

		return rowView;
	}

	static class ViewHolder {
		TextView tv_name, tv_family, tv_username, tv_pwd;
		TextView tv_no, tv_phoneno;
		ImageView img, iv_imgcall;
		ImageButton ib_call;
		LinearLayout ll, ll_phone;

	}

}