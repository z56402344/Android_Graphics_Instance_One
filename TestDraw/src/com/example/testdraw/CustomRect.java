package com.example.testdraw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义空间，带圆角的柱状体
 * @author DuGuang
 * blog地址：http://blog.csdn.net/duguang77
 */
public class CustomRect extends View {

	//圆角柱状体4个角的值
	private float[] radii = { 12f, 12f, 12f, 12f, 0f, 0f, 0f, 0f };
	
	//柱状体的颜色
	private int[] colors = { R.color.rect_cril_leve1, R.color.rect_cril_leve2,
			R.color.rect_cril_leve3, R.color.rect_cril_leve4,
			R.color.rect_cril_leve5, R.color.rect_cril_leve6 };
	
	private int mWidth; //画布的宽 == 手机屏幕的宽
	private int mHeight;//画布的高 == 手机屏幕的高 - ActionBar - 顶部title
	private int mRectWidth;	//矩形宽
	private int mRectHeight;//矩形高
	private Paint mPaint;
	private String mLevel;	//画的L1-L3 字样
	private String mName;	//画的初级，高级，专家字样
	private static float mToY = 15f; //小于1,整体往下移动;大于1，整体往上移动
	private static float mRectY = 4;//往1方向，矩形长度拉长，往10方向，矩形长度缩短
	private ArrayList<String> mPointList;	//柱状体顶部中心坐标的集合
			
	public CustomRect(Context context) {
		super(context);
		init(context);
	}

	public CustomRect(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CustomRect(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPointList = new ArrayList<String>();
		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();
		
		mRectWidth = (int) (mWidth / 9.5);
		mRectHeight = mHeight/2;
		
		//循环出6个柱状体
		for (int i = 0; i < 6; i++) {
			initBitmaps(canvas,i);
		}

	}

	/**
	 * 画矩形
	 * @param canvas
	 * @param index
	 */
	private void initBitmaps(Canvas canvas,int index) {

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mPaint.setStrokeWidth(30f);
		mPaint.setDither(true);
		mPaint.setColor(getResources().getColor(colors[index]));
		
		//通过Path路径，计算出每个柱状体宽高，并把柱状体顶部中心坐标放入集合
		//柱状体顶部中心坐标放入集合，用于和圆心中央的三角，通过中心坐标和柱状体坐标，画出三角形
		Path path = new Path();
		int width = (int) (mRectWidth/2+(index*mRectWidth*1.5));
		int height_top = (int) (mRectHeight+(mRectHeight/15)*(6-index)+mRectWidth*1.5);
		int height_bootom = height_top-mRectHeight/10+(mRectHeight/15)*index;
		height_top = (int) (height_top - mRectHeight/mRectY);//高度起始位置向0方向移动1/10屏幕
		path.addRoundRect(new RectF(width, height_top, width+mRectWidth, height_bootom), radii,
				Path.Direction.CCW);
		canvas.drawPath(path, mPaint);
		String RectX = String.valueOf(width+mRectWidth/2);
		String RectY = String.valueOf(height_top);
		mPointList.add(RectX+"-"+RectY);
		Log.i("dg", "mPointList >>> "+ mPointList.size());
		
		Path path1 = new Path();
		path1.addRoundRect(new RectF(width, height_bootom+10, width+mRectWidth, height_bootom+12), radii,
				Path.Direction.CCW);
		canvas.drawPath(path1, mPaint);
		
		switch (index) {
		case 0:
			mLevel = "L1-L3";
			mName = "入门";
			break;
		case 1:
			mLevel = "L4-L6";
			mName = "初级";
			break;
		case 2:
			mLevel = "L7-L9";
			mName = "中级";
			break;
		case 3:
			mLevel = "L10-L12";
			mName = "中高级";
			break;
		case 4:
			mLevel = "L13-L15";
			mName = "高级";
			break;
		case 5:
			mLevel = "L16";
			mName = "专家";
			break;

		default:
			break;
		}
		drawLevel(canvas, index, width, height_bootom,mLevel);
		drawText(canvas, index, width, height_bootom,mName);
		
	}

	/**
	 * 画名称
	 * @param canvas
	 * @param index
	 * @param width
	 * @param height_bootom
	 * @param name
	 */
	private void drawText(Canvas canvas, int index, int width, int height_bootom, String name) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(30f);
		paint.setDither(true);
		paint.setColor(getResources().getColor(colors[index]));
		paint.setTextSize(30);
		canvas.drawText(name , width+mRectWidth/5, height_bootom+100, paint);
	}

	/**
	 * 画等级
	 * @param canvas
	 * @param index
	 * @param width
	 * @param height_bootom
	 * @param level
	 */
	private void drawLevel(Canvas canvas, int index, int width,
			int height_bootom, String level) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(30f);
		paint.setDither(true);
		paint.setColor(getResources().getColor(colors[index]));
		paint.setTextSize(30);
		if(index ==5){
			canvas.drawText(level , width+mRectWidth/4, height_bootom+60, paint);
		}else if(index == 4 || index ==3 ){
			canvas.drawText(level , width+mRectWidth/20, height_bootom+60, paint);
		}else{
			canvas.drawText(level , width+mRectWidth/6, height_bootom+60, paint);
		}
	}

	public ArrayList<String> getPointList() {
		return mPointList;
	}

	public void setPointList(ArrayList<String> mPointList) {
		this.mPointList = mPointList;
	}

}
