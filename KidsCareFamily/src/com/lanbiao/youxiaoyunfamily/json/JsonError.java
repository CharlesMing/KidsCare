package com.lanbiao.youxiaoyunfamily.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lanbiao.youxiaoyunfamily.entity.JError;

public class JsonError {
	/**
	 * 返回各种code 并提示各种信息
	 * 
	 * @param strJson
	 * @return
	 */
	public static JError getBackExceptionInfo(String strJson) {
		JError jError = new JError();
		try {
			JSONObject j = new JSONObject(strJson);
			int code = Integer.parseInt(j.getString("responseCode"));
			jError.setCode(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jError;
	}

	/**
	 * 解析出登陆时返回的类型 是教师还是家长
	 * 
	 * @param key
	 * @param strJson
	 * @return
	 */
	public static JError getBackTypeInfo(String key, String strJson) {
		JError jError = new JError();
		try {
			JSONObject jObject = new JSONObject(strJson);
			JSONArray jsonArray = jObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				int type = object.getInt("type");
				jError.setType(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jError;
	}

}
