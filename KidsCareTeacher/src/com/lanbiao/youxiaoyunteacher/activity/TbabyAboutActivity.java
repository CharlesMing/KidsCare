package com.lanbiao.youxiaoyunteacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lanbiao.youxiaoyunteacher.entity.Company;
import com.lanbiao.youxiaoyunteacher.json.JsonTools;
import com.lanbiao.youxiaoyunteacher.service.CompanyService;
import com.lanbiao.youxinteacher.R;

public class TbabyAboutActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AboutActivity";
	private Button btn_back;
	private TextView tv_content, tv_companyinfo, tv_register;
	private String result;
	private Company company;

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
			result = CompanyService.getCompanyInfo();
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
