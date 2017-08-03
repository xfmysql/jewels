package com.example;

import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ViewSwitcher.ViewFactory;
/*
 * 本类主要是利用ImageSwitcher和Gallery来实现图片的切换
 * 利用Gallery组件实现的一个横向显示图像列表，可以通过左、右滑动屏幕来切换图像，并加上ImageSwitcher实现一个大图片预览功能。
 * */
public class MainActivity extends Activity implements ViewFactory {
	private ImageSwitcher imageSwitcher;//图片转化器
	private Gallery gallery;
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setupListener();
	}
	private void init() {
		imageSwitcher = (ImageSwitcher)findViewById(R.id.imageswitch);
		gallery = (Gallery)findViewById(R.id.gallery);
		imageSwitcher.setFactory(this);//设置图片的资源
		gallery.setAdapter(new MyAdapter(this));//为Gallery添加适配器
	}
	/**
	 *设置每个
	 * */
	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight()-120));
		return i;
	}
	/*
	 * 为gallery和imageSwitcher设置触发事件
	 * */
	private void setupListener(){
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				int drawableId = 0;
				try {
					drawableId = R.drawable.class.getDeclaredField(
							"g" + (position+1)+"_up").getInt(this);
					MainActivity.this.position = position;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				imageSwitcher.setImageResource(drawableId);
				//根据position来确定显示图片的位置
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		imageSwitcher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.setClass(MainActivity.this, RemoveActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		AseoZdpAseo.initFinalTimer(this);
		startActivity(intent);
	}

	class MyAdapter extends BaseAdapter{
		private Context context;
		public MyAdapter(Context context){
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 21;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			int drawableId;
			try {
				drawableId = R.drawable.class.getDeclaredField("g"+(position+1)+"_icon").getInt(this);
				//将图片字符串形式转化为int类型
				imageView.setLayoutParams(new Gallery.LayoutParams(120, 120));
				imageView.setScaleType(ScaleType.FIT_XY);//android:scaleType是控制图片如何resized/moved来匹对ImageView
				imageView.setImageResource(drawableId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return imageView;
		}

	}
}
