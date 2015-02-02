package com.lanbiao.youxiaoyunfamily.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

public class FamilyofBabyDynamicService {
	private static final String TAG = "BabyDynamicService";
	private static Website website;
	private static String path;
	// private static int typeno = 1;
	private static int type = 2;

	// /**
	// * 动态
	// *
	// * @param familyid
	// * @return
	// */
	// public static String getBDynamic(String familyid) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQuerytrends()
	// + website.getFamilyid() + familyid;
	// Log.v(TAG, path);
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setConnectTimeout(5 * 1000);
	// conn.setRequestMethod("GET");
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// // 请求成功
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// // 请求失败
	// return "请求失败";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "请求失败";
	// }

	// /**
	// * 食物
	// *
	// * @param byBabyId
	// * @return
	// */
	// public static String getFoodInfo(String byBabyId) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQueryfood()
	// + website.getChildid() + byBabyId;
	// Log.v(TAG, "====" + path);
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setConnectTimeout(5 * 1000);
	// conn.setRequestMethod("GET");
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// // 请求成功
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "请求失败";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "请求失败";
	// }

	// /**
	// * 课程
	// *
	// * @param byBabyId
	// * @return
	// */
	// public static String getCourseInfo(String byBabyId) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQuerycourse()
	// + website.getChildid() + byBabyId;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// // 请求成功
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "请求失败";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "请求失败";
	// }

	// public static String getCompanyInfo() {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQuerycompany();
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setConnectTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// // 请求成功
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "请求失败";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "请求失败";
	// }

	// /**
	// * 提交意见
	// *
	// * @param strtype
	// * @param strfamilyid
	// * @param strphone
	// * @param remark
	// * @return
	// */
	// public static String sendAdvice(String strtype, String strfamilyid,
	// String strphone, String remark) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getAddsuggestion()
	// + website.getStrtype() + strtype + website.getStrphone()
	// + strphone + website.getStrremark() + remark
	// + website.getFamilyid() + strfamilyid;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 育儿中心一级菜单
	// *
	// * @param strSchoolid
	// * @return
	// */
	// public static String getChildCareMenu(String strSchoolid) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQueryshoppingmenu()
	// + website.getStrSchoolid() + strSchoolid;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 育儿中心一级菜单下的列表显示
	// *
	// * @param secondId
	// * @return
	// */
	// public static String getChildCareDetail(String secondId) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getShoppingdetail()
	// + website.getSecondmunuid() + secondId;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 收索育儿中心
	// *
	// * @param strSecond_id
	// * @param strName
	// * @return
	// */
	// public static String queryServiceDatas(String strSecond_id, String
	// strName) {
	// website = new Website();
	// try {
	// path = website.getPath() + website.getShoppingdetail()
	// + website.getSecondmunuid() + strSecond_id
	// + website.getShopName() + strName;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setConnectTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "请求失败";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "请求失败";
	// }

	// /**
	// * 育儿中心的详情
	// *
	// * @param strshoppingid
	// * @return
	// */
	// public static String getChildCareByIdDetail(String strshoppingid) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getShoppingbyid()
	// + website.getShoppingid() + strshoppingid;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 新闻列表
	// *
	// * @param strSchool
	// * @return
	// */
	// public static String getNewsInfo(String strSchool) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQuerynews()
	// + website.getTypeno() + typeno// type=1
	// + website.getStrcurrentschool() + strSchool;
	// Log.v(TAG, "----news=------" + path);
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 新闻详情
	// *
	// * @param strNewsid
	// * @return
	// */
	// public static String getNewsInfoDetails(String strNewsid) {
	// try {
	//
	// website = new Website();
	// path = website.getPath() + website.getQuerynewsdetail()
	// + website.getTypeno() + typeno + website.getStrnewsid()
	// + strNewsid;
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	// /**
	// * 通知列表
	// *
	// * @param strSchool
	// * @return
	// */
	// public static String getMsgInfo(String strSchool) {
	// try {
	// website = new Website();
	// path = website.getPath() + website.getQuerynews()
	// + website.getTypeno() + type// type=2
	// + website.getStrcurrentschool() + strSchool;
	// Log.v(TAG, "-----" + path);
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setReadTimeout(5 * 1000);
	// int code = conn.getResponseCode();
	// if (code == 200) {
	// InputStream is = conn.getInputStream();
	// String text = HttpUtils.readInputStream(is);
	// return text;
	// } else {
	// return "发送失败!";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "請求失敗";
	// }

	/**
	 * 通知列表详情
	 * 
	 * @param stractivityid
	 * @param strfamilyid
	 * @return
	 */
	public static String getMsgInfoDetail(String stractivityid,
			String strfamilyid) {
		try {

			website = new Website();
			path = website.getPath() + website.getQuerynewsdetail()
					+ website.getTypeno() + type + website.getStractivityid()
					+ stractivityid + website.getStrfamilyid() + strfamilyid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 参加活动
	 * 
	 * @param activityid
	 * @param familyids
	 * @param stuid
	 * @return
	 */
	public static String activityJoin(String activityid, String familyids,
			String stuid) {
		try {

			website = new Website();
			path = website.getPath() + website.getJoinactivity()
					+ website.getStractivityid() + activityid
					+ website.getStrfamilyids() + familyids
					+ website.getStuid() + stuid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 关于我们
	 * 
	 * @param strchildid
	 * @return
	 */
	public static String aboutUs(String strchildid) {
		try {

			website = new Website();
			path = website.getPath() + website.getAboutbyid()
					+ website.getChildid() + strchildid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 亲子活动列表
	 * 
	 * @param strStuid
	 * @return
	 */
	public static String getActivityList(String strStuid) {
		try {

			website = new Website();
			path = website.getPath() + website.getActivityfamily()
					+ website.getActivitystuid() + strStuid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 亲子活动详情
	 * 
	 * @param strStuiddetail
	 * @return
	 */
	public static String getActivityDetail(String strStuiddetail) {
		try {

			website = new Website();
			path = website.getPath() + website.getActivityfamilydetail()
					+ website.getActivitystuiddetail() + strStuiddetail;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 查询留言信息
	 * 
	 * @param strfamilyid
	 * @param strteacher
	 * @return
	 */
	public static String getleaveInfo(String strfamilyid, String strteacher) {
		try {

			website = new Website();
			path = website.getPath() + website.getQuerymsg()
					+ website.getFamilyid() + strfamilyid
					+ website.getStrteachid() + strteacher;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 发送消息给老师
	 * 
	 * @param strfamilyid
	 * @param strteacherid
	 * @param strcontent
	 * @return
	 */
	public static String sendMsg(String strfamilyid, String strteacherid,
			String strcontent) {
		try {

			website = new Website();
			path = website.getPath() + website.getSendmsg()
					+ website.getStrteachid() + strteacherid
					+ website.getFamilyid() + strfamilyid
					+ website.getStrcontent() + strcontent;
			Log.v(TAG, "content----" + path);
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 签到查询
	 * 
	 * @param cid
	 * @return
	 */
	public static String getsigin(String cid) {
		try {
			int type = 2;

			website = new Website();
			path = website.getPath() + website.getSignin()
					+ website.getStrtype() + type + website.getStrchildid()
					+ cid;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "发送失败!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "請求失敗";
	}

	/**
	 * 修改密码
	 */
	public static String updateUser(String strusername, String struserpwd,
			String strnewuserpwd, String strrepetition, String stroldpwd) {
		try {
			website = new Website();
			path = website.getPath() + website.getUpdateuser()
					+ website.getStrusername() + strusername
					+ website.getStruserpwd() + struserpwd
					+ website.getStrold() + stroldpwd + website.getStrusernew()
					+ strnewuserpwd + website.getStrrepetition()
					+ strrepetition;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "请求失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	/**
	 * 查询是否有老师给孩子发送的生日祝福
	 * 
	 * @param strstuid
	 * @param strtid
	 * @return
	 */
	public static String getTMsg(String strstuid, String strtid) {
		try {
			website = new Website();
			path = website.getPath() + website.getQueryTMsg()
					+ website.getStrtid() + strtid + website.getStrStuid()
					+ strstuid;
			Log.v(TAG, path);
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "请求失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	/**
	 * 修改宝贝生日祝福查看状态 （只显示为一次）
	 * 
	 * @param strStuid
	 * @return
	 */
	public static String updateBirthMsg(String strStuid, String strTid,
			String strBid) {
		try {
			website = new Website();
			path = website.getPath() + website.getMsgstatus()
					+ website.getStrtid() + strTid + website.getStrStuid()
					+ strStuid + website.getStrbirthid() + strBid;
			Log.v(TAG, path);
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "请求失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	/**
	 * 得到版本更新的数据内容
	 * 
	 * @param num
	 * @return
	 */
	public static String checkUpdateUrl(int num) {
		try {
			website = new Website();
			path = website.getPath() + website.getCheckUpdate()
					+ website.getNum() + num + website.getStrType();
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = HttpUtils.readInputStream(is);
				return text;
			} else {
				return "请求失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求失败";
	}
}
