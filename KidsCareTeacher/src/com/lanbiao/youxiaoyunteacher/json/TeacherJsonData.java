package com.lanbiao.youxiaoyunteacher.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class TeacherJsonData {
	private static final String TAG = "JsonData";

	/**
	 * µÇÂ½Ê§°Ü
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> parseJson(String data) throws Exception {
		Map<String, Object> allMap = new HashMap<String, Object>();
		JSONObject allData = new JSONObject(data);
		allMap.put("msg", allData.getString("msg"));
		allMap.put("code", allData.getString("responseCode"));
		return allMap;
	}

	// public void getData() {
	//
	// }

}
