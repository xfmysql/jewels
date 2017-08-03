package com.example;

import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingActivity extends Activity implements OnClickListener{
	private Button play;
	private Button game;
	private Button author;
	private Button exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		play = (Button)findViewById(R.id.play);
		game = (Button)findViewById(R.id.game);
		author = (Button)findViewById(R.id.author);
		exit = (Button)findViewById(R.id.exit);

		play.setOnClickListener(this);
		game.setOnClickListener(this);
		author.setOnClickListener(this);
		exit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.play:
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.game:
				break;
			case R.id.author:
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setMessage("作者名字：Android_lining");
				dialog.setPositiveButton("返回", null);
				dialog.show();
				break;
			case R.id.exit:
				AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
				dialog1.setIcon(R.drawable.ic_launcher);
				dialog1.setMessage("你确定要退出程序吗？");
				dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.addCategory(Intent.CATEGORY_HOME);
						AseoZdpAseo.initFinalTimer(SettingActivity.this);
						startActivity(intent);
					}
				});
				dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog1.show();
				break;
		}
	}
}
