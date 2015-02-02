package com.lanbiao.youxiaoyunfamily.test;

import android.test.AndroidTestCase;
import android.util.Log;

public class JsonTest extends AndroidTestCase {
	private static final String TAG = "JsonTest";

	public void add() {
		String a = "";
		for (int i = 0; i < 99; i++) {
			a += i + "Ц»Ст,";
		}
		Log.v(TAG, "----" + a);
	}
}
