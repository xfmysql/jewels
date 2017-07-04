package com.its.jewels.entity;

import android.util.Log;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import javax.microedition.khronos.opengles.GL10;

import com.its.jewels.constants.IConstants;

/**
 * 精灵接口的实现——钻石精灵
 * @author qingfeng
 * @since 2010-11-03
 */
public class JewelSprite extends ICell implements ISprite, IConstants {

	// ===========================================================
	// Fields
	// ===========================================================

	int mStyle;  //钻石形状
	int mState;  //钻石状态


	// ===========================================================
	// Constructors
	// ===========================================================

	public JewelSprite(int row, int col, TextureRegion mJewelTextureRegion){
		super(row * CELL_WIDTH, col * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, mJewelTextureRegion);

		//this.mSprite = new JewelCell(row, col, mJewelTextureRegion);
		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mState = STATE_NORMAL;//默认是正常状态
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================	
		
	@Override
	public int getRow() {
		return (int)this.getX()/CELL_WIDTH;
	}
	
	@Override
	public int getCol() {
		return (int)this.getY()/CELL_HEIGHT;
	}	
	
	@Override
	public void setMapPosition(int row, int col){
		this.setPosition(row * CELL_WIDTH, col * CELL_HEIGHT);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public void setStyle(final int style){
		this.mStyle = style;
	}
	
	public int getStyle(){
		return this.mStyle;
	}
	
	public Sprite getJewel(){
		return this;
	}	
	
	public void setState(int state){
		this.mState = state;
	}
	
	public int getState(){
		return this.mState;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	int step = 0;  //钻石缩小步骤
	/**
	 * 钻石消失
	 */
	public void doScale(){
		if(this.mState == STATE_SCALEINT)
		{
			if(step < 5)
			{
				step++;
				this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				this.setColor(1, 1, 1);
				switch (step) {
				case 0:
					this.setScale(0.7f);
					this.setAlpha(0.5f);
					break;
				case 1:
					this.setScale(0.7f);
					this.setAlpha(0.4f);
					break;
				case 2:
					this.setScale(0.7f);
					this.setAlpha(0.3f);
					break;
				case 3:
					this.setScale(0.7f);
					this.setAlpha(0.2f);
					break;
				case 4:
					this.setScale(0.7f);
					this.setAlpha(0);
					break;
				default:
					break;
				}
			}
			else
			{
				step = 0;
				this.mState= STATE_DEAD;
				Log.d("error","("+(int)(this.getX()/40)+"," +(int)(this.getY()/40)+")消失");
			}
		}//end if
	}
}
