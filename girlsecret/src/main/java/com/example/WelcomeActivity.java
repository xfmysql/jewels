package com.example;

import com.zdp.aseo.content.AseoZdpAseo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 设置密码的界面
 * */
public class WelcomeActivity extends Activity implements OnClickListener {
	private EditText password;
	private Button enter;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		init();
	}

	private void init() {
		password = (EditText) findViewById(R.id.password);
		enter = (Button) findViewById(R.id.enter);
		cancel = (Button) findViewById(R.id.cancel);
		enter.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.enter) {
			String pass = password.getText().toString();
			savePassword(pass);
		} else if (v.getId() == R.id.cancel) {
			this.finish();
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void savePassword(String pass) {
		SharedPreferences sp = getSharedPreferences("password",
				Context.MODE_PRIVATE);
		String pass1 = sp.getString("pass", "");
		if ("".equals(pass1) && !pass.isEmpty()) {
			System.out.println("值为:"+pass1);
			Editor edit = sp.edit();
			edit.putString("pass", pass);
			edit.commit();
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			if (pass.equals(pass1)) {
				Intent intent = new Intent(this, SettingActivity.class);
				startActivity(intent);
				this.finish();
			} else {
				Toast.makeText(this, "密码不正确", 0).show();
			}
		}
	}

}