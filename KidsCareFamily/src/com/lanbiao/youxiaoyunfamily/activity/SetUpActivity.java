package com.lanbiao.youxiaoyunfamily.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.InstallInfo;
import com.lanbiao.youxiaoyunfamily.hint.ShowToast;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.service.FamilyofBabyDynamicService;
import com.lanbiao.youxiaoyunfamily.util.NetworkUtils;
import com.lanbiao.youxiaoyunfamily.util.PackageUtils;

public class SetUpActivity extends Activity implements
		android.view.View.OnClickListener {
	private TextView tv_about, tv_clean, tv_gotomain, tv_advice, tv_update,
			tv_version_id;
	private Button btn_back, btn_exit;
	private RelativeLayout rl_check_id;
	private String path = Environment.getExternalStorageDirectory().getPath()
			+ "/KidsCares/";
	private PackageUtils packageUtils;// 包
	private ProgressDialog dialog = null;// 记载进度框
	private int oldVersion;// 旧版本号
	private int newVersion;// 新版本号
	private InstallInfo installInfo;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (!NetworkUtils.isConnectingToInternet(SetUpActivity.this)) {
				ShowToast.getToastInfo("请检查网络是否连接", SetUpActivity.this);
			} else {
				switch (msg.what) {
				case 0:
					// showDialogInfo("请稍后...", "正在登陆");
					dialog.setTitle("正在登陆");
					dialog.setMessage("请稍后...");
					dialog.setCancelable(false);
					dialog.show();

					break;
				case 1:
					// 登录失败
					dialog.cancel();
					break;
				case 2:
					// showDialogInfo("请稍后...", "正在下载");
					dialog.setTitle("正在下载");
					dialog.setMessage("请稍后...");
					dialog.show();

					break;
				case 3:

					break;
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_layout);
		initView();
	}

	public void initView() {
		tv_version_id = (TextView) findViewById(R.id.txtv_check_version_id);
		rl_check_id = (RelativeLayout) findViewById(R.id.rlayout_setup_check_id);
		tv_update = (TextView) findViewById(R.id.txtv_updatepassword_id);
		tv_about = (TextView) findViewById(R.id.txtv_setup_about_id);
		tv_clean = (TextView) findViewById(R.id.txtv_setup_clean_id);
		tv_gotomain = (TextView) findViewById(R.id.txtv_setup_welcome_id);
		tv_advice = (TextView) findViewById(R.id.txtv_setup_backmsg_id);
		btn_back = (Button) findViewById(R.id.btn_setup_back_id);
		btn_exit = (Button) findViewById(R.id.btn_setup_quit_login_id);
		tv_about.setOnClickListener(this);
		tv_clean.setOnClickListener(this);
		tv_gotomain.setOnClickListener(this);
		tv_advice.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		tv_update.setOnClickListener(this);
		rl_check_id.setOnClickListener(this);
		tv_version_id.setText("当前版本" + getShowCode());
	}

	/**
	 * 客户端显示的版本号
	 * 
	 * @return
	 */
	public String getShowCode() {
		PackageUtils packageUtils = new PackageUtils(this);
		// 得到版本号
		int code = packageUtils.getVersionCode();
		// 将版本号int转为字符串
		String strCode = Integer.toString(code);
		String strCodes = "";
		// 将字符串拆分
		String[] codes = strCode.split("");
		for (int i = 0; i < codes.length; i++) {
			strCodes += codes[i] + ".";
		}
		// 去掉最后一位
		String strDelLast = strCodes.substring(0, strCodes.length() - 1);
		// 去掉第一位
		String strShowCode = strDelLast.substring(1);
		return strShowCode;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent = getIntent();
		String userName = intent.getStringExtra("username");
		String userPwd = intent.getStringExtra("pwd");
		intent.putExtra("username", userName);
		intent.putExtra("pwd", userPwd);
		switch (v.getId()) {
		case R.id.txtv_setup_about_id:// 关于
			intent.setClass(SetUpActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.txtv_setup_clean_id:// 清除
			File file = new File(path);
			DeleteFile(file);
			mHandler.sendEmptyMessage(1);
			break;
		case R.id.txtv_setup_welcome_id:// 去欢迎界面
			intent.setClass(SetUpActivity.this, FamilyActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.txtv_setup_backmsg_id:// 意见
			intent.setClass(SetUpActivity.this, AdviceActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_setup_back_id:
			intent.setClass(SetUpActivity.this, FamilyActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_setup_quit_login_id:
			exitApp();
			break;

		case R.id.txtv_updatepassword_id:
			changePwd();
			break;
		case R.id.rlayout_setup_check_id:
			initVersionDatas();
			break;
		}
	}

	/******************* ---------START---------- *******************/
	/**
	 * 得到服务器中的数据
	 */
	public void initVersionDatas() {
		packageUtils = new PackageUtils(this);
		oldVersion = packageUtils.getVersionCode();

		try {
			new Thread() {
				public void run() {
					String result = FamilyofBabyDynamicService
							.checkUpdateUrl(oldVersion);
					installInfo = JsonTools.checkUpdateDatas("results", result);
					newVersion = installInfo.getNewVersionCode();
					final String msg = installInfo.getNewContent();
					final String showTime = installInfo.getUpdateTime();
					final String newTime = toTime(showTime);
					final String showVCode = installInfo.getShowVersionCode();
					if (newVersion > oldVersion) {
						runOnUiThread(new Runnable() {
							public void run() {
								// 显示更新的信息
								showDialogInfo(msg + "\n" + "更新时间:" + newTime
										+ "\n" + "版本号：" + showVCode, "版本更新");
							}
						});
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								ShowToast.getToastInfo("已是最新版本！",
										SetUpActivity.this);
							}
						});
					}
				};
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新提示
	 * 
	 * @param contentMsg
	 * @param titleMsg
	 */
	public void showDialogInfo(String contentMsg, String titleMsg) {
		Activity activity = SetUpActivity.this;
		while (activity.getParent() != null) {
			activity = activity.getParent();
		}
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titleMsg);
		builder.setMessage(contentMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 弹出下载框
				// showDownloadDialog();
				handler.sendEmptyMessage(2);
				downloadApk();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();

	}

	/**
	 * 转化时间格式
	 * 
	 * @param time
	 * @return
	 */
	public String toTime(String time) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(time);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			return format1.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/******** -----------------------TODO-------------------------------- **********/
	// // 进度条显示数值
	// private int progress = 100;
	// // 下载进度条
	// private ProgressBar progressBar;
	// 是否终止下载
	private boolean isInterceptDownload = false;

	/**
	 * 下载apk
	 */
	private void downloadApk() {
		// 开启另一线程下载
		Thread downLoadThread = new Thread(downApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 从服务器下载新版apk的线程
	 */
	private Runnable downApkRunnable = new Runnable() {
		@Override
		public void run() {
			if (!android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				// 如果没有SD卡
				Builder builder = new Builder(SetUpActivity.this);
				builder.setTitle("提示");
				builder.setMessage("当前设备无SD卡，数据无法下载");
				builder.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
				return;
			} else {
				FileOutputStream fos = null;
				try {
					// 服务器上新版apk地址
					URL url = new URL(installInfo.getApkUrl());
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					File file = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/KidsCares/updateApkFile/");
					if (!file.exists()) {
						// 如果文件夹不存在,则创建
						file.mkdir();
					}
					// 下载服务器中新版本软件（写文件）
					String apkFile = Environment.getExternalStorageDirectory()
							.getAbsolutePath()
							+ "/KidsCares/updateApkFile/"
							+ "KidsCareFamily.apk";
					File ApkFile = new File(apkFile);
					fos = new FileOutputStream(ApkFile);
					// int count = 0;
					byte buf[] = new byte[1024];
					do {
						int numRead = is.read(buf);
						// count += numRead;
						// 更新进度条
						// progress = (int) (((float) count / length) * 100);
						handlerd.sendEmptyMessage(1);
						if (numRead <= 0) {
							// 下载完成通知安装
							handlerd.sendEmptyMessage(0);
							break;
						}
						fos.write(buf, 0, numRead);
						// 当点击取消时，则停止下载
					} while (!isInterceptDownload);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	};

	/**
	 * 声明一个handler来跟进进度条
	 */
	private Handler handlerd = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 更新进度情况
				// progressBar.setProgress(progress);
				handler.sendEmptyMessage(2);
				break;
			case 0:
				// progressBar.setVisibility(View.INVISIBLE);
				handler.sendEmptyMessage(1);
				// 安装apk文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 安装apk
	 */
	private void installApk() {
		// 获取当前sdcard存储路径
		File apkfile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/KidsCares/updateApkFile/"
				+ "KidsCareFamily.apk");
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		// 安装，如果签名不一致，可能出现程序未安装提示
		i.setDataAndType(Uri.fromFile(new File(apkfile.getAbsolutePath())),
				"application/vnd.android.package-archive");
		this.startActivity(i);
	}

	/******************* ---------END---------- *******************/

	/**
	 * 退出程序
	 */
	public void exitApp() {
		new AlertDialog.Builder(SetUpActivity.this).setTitle("提示！")
				.setMessage("确定要退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}

				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 修改密码
	 */
	public void changePwd() {
		LayoutInflater layoutInflater = LayoutInflater.from(SetUpActivity.this);
		final View myupdateView = layoutInflater.inflate(
				R.layout.t_baby_update, null);
		final EditText et_old, et_newpwd, et_repetition;
		et_old = (EditText) myupdateView.findViewById(R.id.et_useroldpwd);
		et_newpwd = (EditText) myupdateView.findViewById(R.id.et_newpwd);
		et_repetition = (EditText) myupdateView
				.findViewById(R.id.et_repetition);

		new AlertDialog.Builder(this).setTitle("修改密码").setView(myupdateView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String oldpwd = et_old.getText().toString().trim();
						String newpwd = et_newpwd.getText().toString().trim();
						String repetition = et_repetition.getText().toString()
								.trim();

						if (newpwd.length() < 6) {
							Toast.makeText(SetUpActivity.this, "密码长度最大为6位！", 0)
									.show();
							return;
						}
						if (newpwd.equals(repetition)) {
							// String phone = classinfo.getPhone();
							// String pwd = classinfo.getPassword();

							Intent intent = getIntent();
							String userName = intent.getStringExtra("username");
							String userPwd = intent.getStringExtra("pwd");

							String stroldpwd = getMd5(oldpwd);
							userPwd = getMd5(userPwd);
							String result = FamilyofBabyDynamicService
									.updateUser(userName, userPwd, newpwd,
											repetition, stroldpwd);
							try {
								JSONObject jsonObject = new JSONObject(result);
								int code = jsonObject.getInt("responseCode");
								if (code == 0) {
									new AlertDialog.Builder(SetUpActivity.this)
											.setTitle("提示！")
											.setMessage("修改密码成功，请退出程序，重新登陆")
											.setPositiveButton(
													"确定",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															finish();
															System.exit(0);
														}
													}).show();
								} else {
									Toast.makeText(SetUpActivity.this,
											"密码修改失败！", 0).show();
									return;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(SetUpActivity.this, "两次输入的密码不一致！", 0)
									.show();
							return;
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent = getIntent();
			String userName = intent.getStringExtra("username");
			String userPwd = intent.getStringExtra("pwd");
			intent.putExtra("username", userName);
			intent.putExtra("pwd", userPwd);
			intent.setClass(SetUpActivity.this, FamilyActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * MD5加密算法
	 * 
	 * @param plainText
	 *            要加密的字符串
	 * 
	 * @return 加密后的字符串
	 */
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return plainText;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "文件或文件夹不存在",
						Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "删除成功！",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public void DeleteFile(File file) {
		if (file.exists() == false) {
			mHandler.sendEmptyMessage(0);
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				for (File f : childFile) {
					DeleteFile(f);
				}
				file.delete();
			}
		}
	}

}
