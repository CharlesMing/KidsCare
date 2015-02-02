package com.lanbiao.youxiaoyunfamily;

import java.util.List;

import android.app.Application;
import android.content.IntentFilter;

import com.lanbiao.receiver.NetReceiver;
import com.lanbiao.youxiaoyunfamily.entity.FamliyInfo;
import com.lanbiao.youxiaoyunfamily.entity.UserInfo;

public class AppAppliction extends Application {
	public UserInfo userInfo = new UserInfo();
	public List<FamliyInfo> infos;
	public NetReceiver mReceiver = new NetReceiver();
	public IntentFilter mFilter = new IntentFilter();
}
