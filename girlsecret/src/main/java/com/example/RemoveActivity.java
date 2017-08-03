package com.example;

import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class RemoveActivity extends Activity {
	private int position;
	private MediaPlayer player1;
	private MediaPlayer player2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getIntent().getIntExtra("position", -1);
		setContentView(new MyView(this));
		initMedia();//初始化音乐
	}

	private void initMedia() {
		player1 = MediaPlayer.create(this, R.raw.blond5);
		player1.setLooping(false);//不循环
		player2 = MediaPlayer.create(this, R.raw.higher13);
		player2.setLooping(false);//不循环
		AseoZdpAseo.initType(this, AseoZdpAseo.SCREEN_TYPE);
	}

	private void startMedia1() {
		player1.start();
	}
	private void startMedia2() {
		player2.start();
	}

	class MyView extends View{
		private int mWidth;
		private int mHeight;
		private Bitmap beforeBitmap;
		private Bitmap bitmap;
		private Canvas mcanvas;
		private Paint paint;
		private int x;
		private int y;
		public MyView(Context context) {
			super(context);
			setFocusable(true);
			setWindow();
			init();
			setBackground();
			int drawId;
			try {
				drawId = R.drawable.class.getDeclaredField("g"+(RemoveActivity.this.position+1)+"_up").getInt(this);
				beforeBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawId),mWidth,mHeight, true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setBeforeImage(beforeBitmap);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(bitmap, 0, 0, null);//不断的画透明图片,这样才能保证每次去掉的部分都是在外面的图片
			//mcanvas.drawCircle(x, y, 20, paint);
			//mcanvas.drawLine(x,y,20,0,paint);
			super.onDraw(canvas);
		}
		private void setBeforeImage(Bitmap beforeBitmap2) {
			//设置画笔
			paint.setAlpha(0);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//取两层绘制交集显示上层

			//paint.setStyle(Paint.Style.STROKE);//设置非填充
			paint.setStrokeWidth(25);//笔宽5像素
			paint.setAntiAlias(false);//锯齿不显示

			bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);//生成一个透明的图片
			mcanvas.setBitmap(bitmap);//将透明图片添加到画布
			mcanvas.drawBitmap(beforeBitmap2, 0, 0, null);
		}
		private void setWindow() {
			Display display = getWindowManager().getDefaultDisplay();
			mWidth = display.getWidth();
			mHeight = display.getHeight();
		}
		private void init() {
			mcanvas = new Canvas();
			paint = new Paint();
		}
		private void setBackground() {
			try {
				int drawId = R.drawable.class.getDeclaredField("g"+(RemoveActivity.this.position+1)+"_back").getInt(this);
				this.setBackgroundResource(drawId);//将全裸图片放在最底层
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private int mov_x;//声明起点坐标
		private int mov_y;
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			x = (int) event.getX();
			y = (int) event.getY();
			if(y>mHeight/3 && y<2*mHeight/3 && x>mHeight/3 && x<2*mHeight/3){
				startMedia1();
			}else if(y>2*mHeight/3 && y<mHeight-100 && x>mHeight/3 && x<2*mHeight/3){
				startMedia2();
			}
			this.invalidate();

			if (event.getAction()==MotionEvent.ACTION_MOVE) {//如果拖动
				mcanvas.drawLine(mov_x, mov_y, event.getX(), event.getY(), paint);//画线
			}
			if (event.getAction()==MotionEvent.ACTION_DOWN) {//如果点击
				mov_x=(int) event.getX();
				mov_y=(int) event.getY();
				mcanvas.drawPoint(mov_x, mov_y, paint);//画点
			}
			mov_x=(int) event.getX();
			mov_y=(int) event.getY();

			return true;
		}
	}

}

