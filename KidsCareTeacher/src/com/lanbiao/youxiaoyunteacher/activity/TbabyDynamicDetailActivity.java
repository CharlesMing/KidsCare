package com.lanbiao.youxiaoyunteacher.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lanbiao.youxiaoyunteacher.entity.BabyDynimic;
import com.lanbiao.youxiaoyunteacher.entity.Classinfo;
import com.lanbiao.youxiaoyunteacher.entity.ImageBitMap;
import com.lanbiao.youxiaoyunteacher.entity.SendMsg;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.ImageAndTextListAdapter;
import com.lanbiao.youxiaoyunteacher.loadimgandadapter.ImageTools;
import com.lanbiao.youxiaoyunteacher.service.DynamicSelectService;
import com.lanbiao.youxiaoyunteacher.service.HttpRequest;
import com.lanbiao.youxiaoyunteacher.service.LoginService;
import com.lanbiao.youxinteacher.R;

@SuppressLint("WorldWriteableFiles")
public class TbabyDynamicDetailActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "TbabyDynamicDetailActivity";
	private Button btn_back, btn_send, btn_reelect, btn_more, btn_photo;
	private ImageButton ib_dynamicselection;
	private EditText et_evaluate;
	private TextView tv_babyNo, tv_relate;
	private RadioGroup group_db, group_tw, group_qx, group_cf, group_sj;
	private TextView tv_cf, tv_sj, tv_qx, tv_db, tv_tw;
	private Map<String, String> map = new HashMap<String, String>();
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	private static final int CROP = 2;
	private static final int CROP_PICTURE = 3;

	private static final int SCALE = 5;// 照片缩小比例
	private ImageView iv_image = null;
	private ImageBitMap ibm = new ImageBitMap();
	private String userName;
	private String userPwd;
	private JSONObject jo;
	private Classinfo classinfo;
	private BabyDynimic dynimic;
	// private HorizontalScrollView scrollView;
	private String path = "http://211.149.187.10:8080/KidsYun/dataExchange/addtrends.action";
	private SendMsg msg = new SendMsg();
	private ImageAndTextListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.t_baby_dynamic_detail_layout);
		findView();
		initData();
		setOnClick();
	}

	// 图片
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				// 将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory() + "/image.jpg");
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
						bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);

				// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();
				// byte[] c = Bitmap2Base64(newBitmap);
				// System.out.println(c);

				String ab = imgToBase64(newBitmap);// 发送至服务器
				ibm.setTobase64(ab);
				// 将处理过的图片显示在界面上，并保存到本地
				iv_image.setImageBitmap(newBitmap);
				ImageTools.savePhotoToSDCard(newBitmap, Environment
						.getExternalStorageDirectory().getAbsolutePath(),
						String.valueOf(System.currentTimeMillis()));

				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				// 照片的原始资源地址
				Uri originalUri = data.getData();
				try {
					// 使用ContentProvider通过URI获取原始图片
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					if (photo != null) {
						// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo,
								photo.getWidth() / SCALE, photo.getHeight()
										/ SCALE);
						// 释放原始图片占用的内存，防止out of memory异常发生
						photo.recycle();
						String a = imgToBase64(smallBitmap);// 转为字符串并发送给服务器
						// ImageBitMap ibma = new ImageBitMap();
						ibm.setTobase64(a);
						iv_image.setImageBitmap(smallBitmap);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case CROP:
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
				} else {
					System.out.println("File");
					String fileName = getSharedPreferences("temp",
							Context.MODE_WORLD_WRITEABLE).getString("tempName",
							"");
					uri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), fileName));
				}
				cropImage(uri, 500, 500, CROP_PICTURE);
				break;

			case CROP_PICTURE:
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
						photo = (Bitmap) extra.get("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					}
				}
				iv_image.setImageBitmap(photo);
				break;
			default:
				break;
			}
		}
	}

	// 图片
	public void showPicturePicker(Context context, boolean isCrop) {
		final boolean crop = isCrop;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[] { "拍照", "相册" },
				new DialogInterface.OnClickListener() {
					// 类型码
					int REQUEST_CODE;

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case TAKE_PICTURE:
							Uri imageUri = null;
							String fileName = null;
							Intent openCameraIntent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							if (crop) {
								REQUEST_CODE = CROP;
								// 删除上一次截图的临时文件
								SharedPreferences sharedPreferences = getSharedPreferences(
										"temp", Context.MODE_WORLD_WRITEABLE);
								ImageTools.deletePhotoAtPathAndName(Environment
										.getExternalStorageDirectory()
										.getAbsolutePath(), sharedPreferences
										.getString("tempName", ""));

								// 保存本次截图临时文件名字
								fileName = String.valueOf(System
										.currentTimeMillis()) + ".jpg";
								Editor editor = sharedPreferences.edit();
								editor.putString("tempName", fileName);
								editor.commit();
							} else {
								REQUEST_CODE = TAKE_PICTURE;
								fileName = "image.jpg";
							}
							imageUri = Uri.fromFile(new File(Environment
									.getExternalStorageDirectory(), fileName));
							// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
							openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									imageUri);
							startActivityForResult(openCameraIntent,
									REQUEST_CODE);
							break;

						case CHOOSE_PICTURE:
							Intent openAlbumIntent = new Intent(
									Intent.ACTION_GET_CONTENT);
							if (crop) {
								REQUEST_CODE = CROP;
							} else {
								REQUEST_CODE = CHOOSE_PICTURE;
							}
							openAlbumIntent
									.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
							startActivityForResult(openAlbumIntent,
									REQUEST_CODE);
							break;

						default:
							break;
						}
					}
				});
		builder.create().show();
	}

	// 截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	public void initData() {
		Intent intent = getIntent();
		String stuId = intent.getStringExtra("names");
		String[] str = stuId.split(",");

		int babyCount = 0;
		for (int i = 0; i < str.length; i++) {
			babyCount = i + 1;
		}
		tv_babyNo.setText("已选择" + babyCount + "个宝贝");
		Bundle bundle = this.getIntent().getExtras();
		userName = bundle.getString("username");
		userPwd = bundle.getString("pwd");
		String re = LoginService.login(userName, userPwd);
		try {

			jo = new JSONObject(re);// 教师个人信息
			classinfo = JsonTools.getClassId("results", re);
			String strSchoolId = DynamicSelectService.Dynamic(classinfo
					.getShoolId());
			jo = new JSONObject(strSchoolId);
			dynimic = JsonTools.getBabyDynimicselect("results", strSchoolId);
			String life = dynimic.getLifestatename();
			String lifestate = dynimic.getLifestate();
			String id = dynimic.getLifestateIds();
			String two = dynimic.getLifestatetwo();
			String[] strTwo = two.split(",");
			String[] strId = id.split(",");
			String[] strLife = life.split(",");
			String[] strLifestate = lifestate.split(",");

			tv_db.setText(strLife[0] + ":");
			tv_tw.setText(strLife[1] + ":");
			tv_qx.setText(strLife[2] + ":");
			tv_cf.setText(strLife[3] + ":");
			tv_sj.setText(strLife[4] + ":");

			String twodabians = strTwo[0] + "," + strTwo[1];
			String dabians = strLifestate[0] + "," + strLifestate[1];

			String twoTiwens = strTwo[2] + "," + strTwo[3] + "," + strTwo[4];
			String Tiwens = strLifestate[2] + "," + strLifestate[3] + ","
					+ strLifestate[4];

			String twoqingxus = strTwo[5] + "," + strTwo[6] + "," + strTwo[7];
			String Qingxus = strLifestate[5] + "," + strLifestate[6] + ","
					+ strLifestate[7];

			String twochifans = strTwo[8] + "," + strTwo[9] + "," + strTwo[10];

			String Chifans = strLifestate[8] + "," + strLifestate[9] + ","
					+ strLifestate[10];

			String twoSleeps = strTwo[11] + "," + strTwo[12] + "," + strTwo[13]
					+ "," + strTwo[14];
			String Sleeps = strLifestate[11] + "," + strLifestate[12] + ","
					+ strLifestate[13] + "," + strLifestate[14];

			String[] strtwodabians = twodabians.split(",");
			String[] strdabians = dabians.split(",");

			String[] strTwoTiwens = twoTiwens.split(",");
			String[] strTiwens = Tiwens.split(",");

			String[] strTwoqingxus = twoqingxus.split(",");
			String[] strQingxus = Qingxus.split(",");

			String[] strTwochifans = twochifans.split(",");
			String[] strChifans = Chifans.split(",");

			String[] strTwoSleeps = twoSleeps.split(",");
			String[] strSleeps = Sleeps.split(",");

			for (int i = 0; i < strChifans.length; i++) {
				RadioButton button = new RadioButton(
						TbabyDynamicDetailActivity.this);
				button.setText(strChifans[i]);
				map.put(strChifans[i], strTwochifans[i]);
				group_cf.addView(button);
				final String strCf = strId[3];
				button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// 吃得很好=
						String getCurrentmsg = (String) buttonView.getText();
						String strgetCfId = map.get(getCurrentmsg);
						if (isChecked) {
							msg.setCfid(strCf + ",");
							msg.setCfmsg(strgetCfId + ",");
						}
					}
				});
			}
			for (int i = 0; i < strdabians.length; i++) {
				RadioButton button = new RadioButton(
						TbabyDynamicDetailActivity.this);
				button.setText(strdabians[i]);
				map.put(strdabians[i], strtwodabians[i]);
				group_db.addView(button);
				final String strdb = strId[0];
				button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String getCurrentmsg = (String) buttonView.getText();
						String strgetdabiansId = map.get(getCurrentmsg);
						if (isChecked) {
							msg.setDbmsg(strgetdabiansId + ",");
							msg.setDbid(strdb + ",");
						} else {
							group_db.clearCheck();
						}

					}
				});
			}

			for (int i = 0; i < strQingxus.length; i++) {
				RadioButton button = new RadioButton(
						TbabyDynamicDetailActivity.this);
				button.setText(strQingxus[i]);
				map.put(strQingxus[i], strTwoqingxus[i]);
				group_qx.addView(button);
				final String strQx = strId[2];
				button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String getCurrentmsg = (String) buttonView.getText();
						String strgetqingxuId = map.get(getCurrentmsg);
						if (isChecked) {
							msg.setQxid(strQx + ",");
							msg.setQxmsg(strgetqingxuId + ",");
						}
					}
				});
			}
			for (int i = 0; i < strSleeps.length; i++) {
				RadioButton button = new RadioButton(
						TbabyDynamicDetailActivity.this);
				button.setText(strSleeps[i]);
				map.put(strSleeps[i], strTwoSleeps[i]);
				group_sj.addView(button);
				final String strSj = strId[4];
				button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String getCurrentmsg = (String) buttonView.getText();
						String strgetSleepsId = map.get(getCurrentmsg);
						if (isChecked) {
							msg.setSjid(strSj + ",");
							msg.setSjmsg(strgetSleepsId + ",");
						}
					}
				});
			}
			for (int i = 0; i < strTiwens.length; i++) {
				RadioButton button = new RadioButton(
						TbabyDynamicDetailActivity.this);
				button.setText(strTiwens[i]);
				map.put(strTiwens[i], strTwoTiwens[i]);
				group_tw.addView(button);
				final String strTw = strId[1];
				button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String getCurrentmsg = (String) buttonView.getText();
						String strgetTwId = map.get(getCurrentmsg);
						if (isChecked) {
							msg.setTwid(strTw + ",");
							msg.setTwmsg(strgetTwId + ",");
						}
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findView() {
		tv_cf = (TextView) findViewById(R.id.tv_cf);
		tv_db = (TextView) findViewById(R.id.tv_db);
		tv_qx = (TextView) findViewById(R.id.tv_qx);
		tv_sj = (TextView) findViewById(R.id.tv_sj);
		tv_tw = (TextView) findViewById(R.id.tv_tw);
		et_evaluate = (EditText) findViewById(R.id.et_evaluate);
		group_cf = (RadioGroup) findViewById(R.id.radio_group_cf);
		group_db = (RadioGroup) findViewById(R.id.radio_group_db);
		group_qx = (RadioGroup) findViewById(R.id.radio_group_qx);
		group_sj = (RadioGroup) findViewById(R.id.radio_group_sj);
		group_tw = (RadioGroup) findViewById(R.id.radio_group_tw);
		iv_image = (ImageView) this.findViewById(R.id.img);
		btn_send = (Button) findViewById(R.id.btn_t_remind_detail_send_id);
		btn_reelect = (Button) findViewById(R.id.btn_reelect);
		btn_photo = (Button) findViewById(R.id.btn_photo);
		ib_dynamicselection = (ImageButton) findViewById(R.id.ib_dynamicselection);
		et_evaluate = (EditText) findViewById(R.id.et_evaluate);
		tv_babyNo = (TextView) findViewById(R.id.tv_babyNo);
		tv_relate = (TextView) findViewById(R.id.txtv_t_dynamic_detail_relate_id);
		btn_more = (Button) findViewById(R.id.btn_more);
	}

	public void setOnClick() {
		btn_reelect.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		btn_photo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_more:
			Intent intent = getIntent();
			TextView tv = new TextView(TbabyDynamicDetailActivity.this);
			String name = intent.getStringExtra("names");// getInt("id");
			String subname = name.substring(0, name.length() - 1);
			tv.setText(subname);
			tv.setTextSize(20);
			new AlertDialog.Builder(this).setTitle("预览").setView(tv)
					.setPositiveButton("确定", null).show();
			break;
		case R.id.btn_t_remind_detail_send_id:
			try {
				String teacherMsg = et_evaluate.getText().toString().trim();

				String firstid = "";
				String strcfid = msg.getCfid();
				String strdbid = msg.getDbid();
				String strqxid = msg.getQxid();
				String strsjid = msg.getSjid();
				String strtwid = msg.getTwid();

				String twoid = "";
				String strTwodbId = msg.getDbmsg();
				String strTwoCfId = msg.getCfmsg();
				String strTwoQx = msg.getQxmsg();
				String strTwoTw = msg.getTwmsg();
				String strTwoSleep = msg.getSjmsg();

				String img = ibm.getTobase64();
				img = URLEncoder.encode(img);

				teacherMsg = URLEncoder.encode(teacherMsg);
				intent = getIntent();
				String stuId = intent.getStringExtra("ids");
				String strStuid = stuId.substring(0, stuId.length() - 1);

				firstid += strcfid + strdbid + strqxid + strsjid + strtwid;
				twoid += strTwodbId + strTwoCfId + strTwoQx + strTwoTw
						+ strTwoSleep;

				String strFirstidtoSub = firstid.substring(0,
						firstid.length() - 1);

				String strTwoIdtoSub = twoid.substring(0, twoid.length() - 1);
				String para = "requestCode=105&childids=" + strStuid
						+ "&base64_logo=" + img + "&logo_name=jpg"
						+ "&Trendsfirstid=" + strFirstidtoSub

						+ "&Trendstwoid=" + strTwoIdtoSub

						+ "&Teachremark=" + teacherMsg + "&teacherid="
						+ classinfo.getId();
				String str = HttpRequest.sendPost(path, para);
				try {
					JSONObject object = new JSONObject(str);
					int code = Integer.parseInt(object
							.getString("responseCode"));
					if (code == 0) {
						showCustomToast();
						setContentView(R.layout.t_main_layout);
						finish();

					} else {
						Toast.makeText(TbabyDynamicDetailActivity.this,
								"发送失败...", 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
			}

			break;
		case R.id.btn_reelect:
			setContentView(R.layout.t_main_layout);
			finish();
			break;
		case R.id.ib_dynamicselection:

			break;
		case R.id.btn_photo:
			// 即拍即显示
			showPicturePicker(TbabyDynamicDetailActivity.this, false);
			break;
		}
	}

	/**
	 * 照片转码
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String imgToBase64(Bitmap bitmap) {

		if (bitmap == null) {
			// bitmap not found!!
		}
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.flush();
			out.close();

			byte[] imgBytes = out.toByteArray();
			return Base64.encodeToString(imgBytes, Base64.DEFAULT);
		} catch (Exception e) {
			return "错误";
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 从布局文件中加载布局并且自定义显示Toast
	 */
	private void showCustomToast() {
		// 获取LayoutInflater对象，该对象可以将布局文件转换成与之一致的view对象
		LayoutInflater inflater = getLayoutInflater();
		// 将布局文件转换成相应的View对象
		View layout = inflater.inflate(R.layout.custome_toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));
		// 从layout中按照id查找imageView对象
		ImageView imageView = (ImageView) layout.findViewById(R.id.ivForToast);
		// 设置ImageView的图片
		imageView.setBackgroundResource(R.drawable.ic_ok);
		// 实例化一个Toast对象
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(5);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setView(layout);
		toast.show();
	}

}
