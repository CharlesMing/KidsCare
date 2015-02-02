package com.lanbiao.youxiaoyunteacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.Contact;
import com.lanbiao.youxiaoyunteacher.entity.Familyinfo;
import com.lanbiao.youxiaoyunteacher.entity.TestListphone;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.ListTestAdapter;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MyStudentService;
import com.lanbiao.youxinteacher.R;

public class TestListView extends Activity {
	private static final String TAG = "TestListView";
	private ListTestAdapter adapter;
	private Familyinfo familyinfo;
	private Classinfo classinfo;
	private Contact contact;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		lv = (ListView) findViewById(R.id.lv_test);
		initData();
	}

	public void initData() {
		try {
			Intent intent = getIntent();
			Bundle bundle = this.getIntent().getExtras();
			String strSecondid = bundle.getString("secondid");
			// String userName = intent.getStringExtra("username");
			// String userPwd = intent.getStringExtra("pwd");
			String strteacherinfo = LoginService.login("13637973041", "123456");
			classinfo = JsonTools.getClassId("results", strteacherinfo);

			String familyphone = MyStudentService
					.getstubyfamilycontactinfo(classinfo.getId());
			contact = JsonTools.getStufamilinfo("results", familyphone);
			String IdandphoneandchildRegion = contact
					.getIdandphoneandchildRegion();

			List<TestListphone> list = new ArrayList<TestListphone>();
			String[] strIdandphoneandchildRegion = IdandphoneandchildRegion
					.split("¡£");
			String phoneandids = "";

			for (int j = 0; j < strIdandphoneandchildRegion.length; j++) {
				String[] str = strIdandphoneandchildRegion[j].split(",");
				String ids = str[1];
				if (strSecondid.equals(ids)) {
					phoneandids += str[0] + ",";
				}
			}
			String[] strname = phoneandids.split(",");
			for (int j = 0; j < strname.length; j++) {
				String[] phoneandid = strname[j].split("=");
				String phone = phoneandid[0];
				String phoneregion = phoneandid[1];
				int judge = Integer.parseInt(phoneregion);
				switch (judge) {
				case 0:
					phoneregion = "ÆäËû";
					list.add(new TestListphone(phone, phoneregion));
					break;
				case 1:
					phoneregion = "°Ö°Ö";
					list.add(new TestListphone(phone, phoneregion));
					break;
				case 2:
					phoneregion = "ÂèÂè";
					break;
				case 3:
					phoneregion = "Ò¯Ò¯";
					list.add(new TestListphone(phone, phoneregion));
					break;
				case 4:
					phoneregion = "ÄÌÄÌ";
					list.add(new TestListphone(phone, phoneregion));
					break;
				case 5:
					phoneregion = "Íâ¹«";
					list.add(new TestListphone(phone, phoneregion));
					break;
				case 6:
					phoneregion = "ÍâÆÅ";
					list.add(new TestListphone(phone, phoneregion));
					break;

				}

			}
			// }
			adapter = new ListTestAdapter(this, list, lv);
			lv.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
