package com.lanbiao.youxiaoyunteacher.test;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.Bundle;
import android.test.AndroidTestCase;
import android.util.Log;

import com.lanbiao.youxiaoyunteacher.entity.BabyDynimic;
import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.Contact;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.DynamicSelectService;
import com.lanbiao.youxiaoyunteacher.service.HttpRequest;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxiaoyunteacher.service.MyStudentService;
import com.lanbiao.youxiaoyunteacher.service.StudyRaiseService;

public class TestYouxinTeacher extends AndroidTestCase {

	private static final String TAG = "TestYouxinTeacher";

	private String userName;
	private String userPwd;
	private JSONObject jo;
	private Classinfo classinfo;
	private BabyDynimic dynimic;
	private Contact contact;

	public void initdata() {
		String familyphone = MyStudentService
				.getstubyfamilycontactinfo("9b0fc831-ada1-4b27-9a7f-8653fa33b866");
		contact = JsonTools.getStufamilinfo("results", familyphone);
		String IdandphoneandchildRegion = contact.getIdandphoneandchildRegion();
		String idandname = contact.getIdandname();

		String[] strIdandphoneandchildRegion = IdandphoneandchildRegion
				.split("。");
		String[] strid = idandname.split(",");
		for (int i = 0; i < strid.length; i++) {
			String[] stridandnames = strid[i].split("=");
			String id = stridandnames[0];
			String name = "";
			for (int j = 0; j < strIdandphoneandchildRegion.length; j++) {
				String[] str = strIdandphoneandchildRegion[j].split(",");
				String ids = str[1];
				if (id.equals(ids)) {
					name += str[0] + ",";
				}
			}
			Log.v(TAG, "外面的ID" + id);
			String[] strname = name.split(",");
			Log.v(TAG, "name-----" + name);
			for (int j = 0; j < strname.length; j++) {
				String[] phoneandid = strname[j].split("=");
				String phone = phoneandid[0];
				String phoneregion = phoneandid[1];
				Log.v(TAG, "phoneregion：" + phoneregion);
				Log.v(TAG, "phone:" + phone);
			}
		}

		// for (int i = 0; i < strIdandphoneandchildRegion.length; i++) {
		// String[] str = strIdandphoneandchildRegion[i].split(",");
		// String id = str[1];
		// String phoneandregion = str[0];
		// Log.v(TAG, "----" + phoneandregion);
		// }
	}

	public void getdata() {
		String re = LoginService.login("13883448332", "123456");
		try {
			jo = new JSONObject(re);// 教师个人信息
			classinfo = JsonTools.getClassId("results", re);
			String strSchoolId = DynamicSelectService.Dynamic(classinfo
					.getShoolId());
			jo = new JSONObject(strSchoolId);
			dynimic = JsonTools.getBabyDynimicselect("results", strSchoolId);
		} catch (Exception e) {
		}
	}
}
