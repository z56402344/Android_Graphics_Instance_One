package com.example.testdraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义控件圆形
 * @author DuGuang
 * blog地址：http://blog.csdn.net/duguang77 
 */
public class CustomCircle extends View {

	private float mCenterX; // 圆形X轴中心
	private float mCenterY;	//圆形Y轴中心
	private float mCircleSize;	//圆形直径大小
	private Context mContext; 
	private int mWidth;	//画布的宽 == 手机屏幕的宽
	private int mHeight;//画布的高 == 手机屏幕的高 - ActionBar - 顶部title

	public CustomCircle(Context context) {
		super(context);
		init(context);
	}

	public CustomCircle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CustomCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	/**
	 * 初始化数据
	 * @param context 
	 */
	private void init(Context context) {
		this.mContext = context;
		this.mCenterX = 350f;
		this.mCenterY = 350f;
		this.mCircleSize = 285f;
	
	}


	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();
		mCenterX = mWidth/2;
		mCenterY = mHeight/4;
		mCircleSize = mHeight/6;
		
		//第一个画笔，画出一个空心圆
		Paint paint = new Paint();
		paint.setAntiAlias(true); //消除齿距
		paint.setStyle(Style.STROKE); //空心画笔
		paint.setStrokeWidth(30f);	//画笔宽度
		paint.setDither(true);	//消除脱颖
		paint.setColor(getResources().getColor(R.color.cril)); //设置画笔颜色

		//通过Path 用canvas在画布上画出圆形
		Path path = new Path();
		path.addCircle(mCenterX, mCenterY, mCircleSize, Path.Direction.CCW);
		canvas.drawPath(path, paint);

		
		//第二个画笔，画出一个实心圆
		Paint paint_white = new Paint();
		Path path_white = new Path();
		paint_white.setAntiAlias(true);
		paint_white.setStyle(Style.FILL);
		paint_white.setDither(true);
		paint_white.setColor(getResources().getColor(R.color.white));
		path_white.addCircle(mCenterX, mCenterY, mCircleSize-15, Path.Direction.CCW);
		canvas.drawPath(path_white, paint_white);
		
		
		//第三个画笔，画出一个空心圆
		Paint paint_STROKE = new Paint();
		Path path_STROKE = new Path();
		paint_STROKE.setAntiAlias(true);
		paint_STROKE.setStyle(Style.STROKE);
		paint.setStrokeWidth(5f);	//画笔宽度
		paint_STROKE.setDither(true);
		paint_STROKE.setColor(getResources().getColor(R.color.cril));
		path_STROKE.addCircle(mCenterX, mCenterY, mCircleSize-25, Path.Direction.CCW);
		canvas.drawPath(path_STROKE, paint_STROKE);
		
		
		
	}

}
