package com.lanbiao.youxiaoyunfamily.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lanbiao.youxiaoyunfamily.R;
import com.lanbiao.youxiaoyunfamily.entity.Company;
import com.lanbiao.youxiaoyunfamily.entity.Website;
import com.lanbiao.youxiaoyunfamily.json.JsonTools;
import com.lanbiao.youxiaoyunfamily.util.HttpUtils;

/**
 * 关于公司
 * 
 * @author lanbiao
 * 
 */
public class AboutActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AboutActivity";
	private Button btn_back;
	private TextView tv_content, tv_companyinfo, tv_register;
	private String result;
	private Company company;
	private Website website = new Website();
	private String path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_about_layout);
		initView();
		initData();
	}

	public void initView() {
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_content = (TextView) findViewById(R.id.txtv_setup_about_content_id);
		tv_companyinfo = (TextView) findViewById(R.id.tv_companyinfo);
		btn_back = (Button) findViewById(R.id.btn_setup_about_back_id);
		btn_back.setOnClickListener(this);
	}

	public void initData() {
		try {

			// result = FamilyofBabyDynamicService.getCompanyInfo();
			path = website.getPath() + website.getQuerycompany();
			result = HttpUtils.getJsonContent(path);
			company = JsonTools.getConpanyInfo("results", result);
			String name = company.getCompany_name();
			String content = company.getCompany_synopsis();
			String registerInfo = company.getRegistrationMark();
			tv_companyinfo.setText(name);
			tv_content.setText(registerInfo);
			tv_register.setText(content);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setup_about_back_id:
			finish();
			break;

		}
	}

}
