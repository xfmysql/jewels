package com.its.fbgamedemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Message;
import android.util.Log;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.SystemUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by its on 2016-08-04.
 */
public class TestSprite extends BaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    public Engine onLoadEngine() {
        Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions mEngineOptions = new EngineOptions(true,
                EngineOptions.ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(),
                mCamera);
        return new Engine(mEngineOptions);//需要播放音效;
    }

    private Texture mBackgroundTexture,mSpriteTexture,mTexture1;
    ChangeableText changeText;
    Font mPlokFont;
    protected TextureRegion mBackgroundTextureRegion,mSpriteTextureRegion;
    public void onLoadResources(){
        TextureRegionFactory.setAssetBasePath("gfx/");

//        this.background = new RepeatingSpriteBackground(800, 480,
//                getTextureManager(), AssetBitmapTextureAtlasSource.create(
//                this.getAssets(), "background.png"),
//                getVertexBufferObjectManager());
//
//        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(
//                getTextureManager(), 64, 32, TextureOptions.DEFAULT);
//        mSpriteTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
//                .createTiledFromAsset(mBitmapTextureAtlas, this,
//                        "face_circle_tiled.png", 0, 0, 2, 1);
//        mBitmapTextureAtlas.load();

        this.mBackgroundTexture = new Texture(1024, 512, TextureOptions.DEFAULT);
        this.mBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "background.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.mBackgroundTexture);

        this.mSpriteTexture = new Texture(64, 32, TextureOptions.DEFAULT);
        this.mSpriteTextureRegion = TextureRegionFactory.createFromAsset(this.mSpriteTexture, this, "face_circle_tiled.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(this.mSpriteTexture);

        this.mTexture1 = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        //FontFactory.setAssetBasePath("fonts/");
        this.mPlokFont = FontFactory.createFromAsset(this.mTexture1, this, "fonts/bluehigh.ttf", 20, true, Color.BLACK);
        this.mEngine.getTextureManager().loadTexture(this.mTexture1);
        this.mEngine.getFontManager().loadFonts(this.mPlokFont);


    }
    protected  Scene mMainScene;//主场景
    public Scene onLoadScene(){
        this.mMainScene = new Scene(2);
        this.mMainScene.setBackgroundEnabled(false);

        final Sprite background = new Sprite(0, 0, this.mBackgroundTextureRegion);
        this.mMainScene.getLayer(0).addEntity(background);

        final Sprite face = new Sprite(100, 100, mSpriteTextureRegion)
        {
            // 是否选中的标志
            boolean mGrabbed = false;

            float x=0,y=0;
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN://这里取到原来的坐标
                        mGrabbed = true;
                        // 将原图片放大到4.5倍（之前设置的是4倍）
                        setScale(4.5f);
                        break;

                    case TouchEvent.ACTION_MOVE:
                        if (mGrabbed) {
                            // 从新设置精灵的坐标
                            x = pSceneTouchEvent.getX();
                            y = pSceneTouchEvent.getY();
                            //setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());//跟着移动
                        }
                        break;

                    case TouchEvent.ACTION_UP://拖出本sprite的话就不执行了，通过scene的up事件
                        if (mGrabbed) {
                            setPosition(x,y);
                            mGrabbed = false;
                            // 将图片还原到之前的样子，4倍大小
                            setScale(4f);
                        }
                        break;
                }
                return true;
            }
        };
        // 为了点击区域大一些，我们将图片放大4倍
        face.setScale(4f);
        //face.animate(new long[]{200, 200}, 0, 1, true);

        changeText =new ChangeableText(140, 40, this.mPlokFont, "2017-09-24 10:10:00");
        mMainScene.getTopLayer().addEntity(changeText);


        this.mMainScene.getLayer(1).addEntity(face);
        // 注册精灵要实现触摸效果
        mMainScene.registerTouchArea(face);
        mMainScene.setTouchAreaBindingEnabled(true);
        // 为场景注册触摸监听事件
        mMainScene.setOnSceneTouchListener(new Scene.IOnSceneTouchListener() {

            public boolean onSceneTouchEvent(Scene pScene,TouchEvent pSceneTouchEvent) {
                switch (pSceneTouchEvent.getAction()) {
                    // 这里，我们只取了按下抬起时的效果，方便我们观察
                    case TouchEvent.ACTION_UP:
                        face.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
                        break;
                }
                return true;
            }
        });
        autoJisuan();
        return this.mMainScene;
    }

    @Override
    public void onLoadComplete(){

    }


    /**
     * 钻石自动发光提示(可消去的)
     */
    private void autoJisuan(){
        this.mMainScene.registerUpdateHandler(new TimerHandler(1f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");//设置日期格式
                String t = df.format(new Date());// new Date()为获取当前系统时间
                changeText.setText(t);
                Log.e("error","autoJisuan"+t);
            }

        }));
    }
}