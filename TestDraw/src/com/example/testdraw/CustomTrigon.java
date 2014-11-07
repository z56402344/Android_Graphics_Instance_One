package com.example.testdraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 通过柱状体顶部中心点坐标和圆心点坐标，画出过圆心的三角形
 * @author DuGuang
 * blog地址：http://blog.csdn.net/duguang77
 */
public class CustomTrigon extends View {

	PointBean mPointA;	//柱状体顶部中心坐标
	PointBean mPointB = new PointBean(760, 400); //初始化时,假设的一个圆心点坐标
	
	private float mCenterX;	//圆心点坐标X
	private float mCenterY;	//圆心点坐标Y
	private int mWidth;	//画布的宽 == 手机屏幕的宽
	private int mHeight;//画布的高 == 手机屏幕的高 - ActionBar - 顶部title
	
	public CustomTrigon(Context context) {
		super(context);
	}

	public CustomTrigon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomTrigon(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();
		mCenterX = mWidth/2;
		mCenterY = mHeight/4;
		
		mPointA = new PointBean((int)mCenterX, (int)mCenterY);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(30f);
		paint.setDither(true);
		paint.setColor(getResources().getColor(R.color.cril));


		// cosA=(b²+c²-a²)/(2bc)
		// cosB=(a²+c²-b²)/(2ac)
		// cosC=(a²+b²-c²)/(2ab)
		//
		// 则由反余弦函数的定义可知：
		// A=arccos(cosA)=arccos((b²+c²-a²)/(2bc))；
		// B=arccos(cosB)=arccos((a²+c²-b²)/(2ac))；
		// C=arccos(cosC)=arccos((a²+b²-c²)/(2ab))；
		double mLineB = 581d;
		double mLineC = 584d;
		double mLineA = 50d;

		double mAcosA = Math
				.acos(((mLineB * mLineB) + (mLineC * mLineC) - (mLineA * mLineA))
						/ (2d * mLineB * mLineC));

		double mAcosB = Math
				.acos(((mLineA * mLineA) + (mLineC * mLineC) - (mLineB * mLineB))
						/ (2d * mLineA * mLineC));

		double mAcosC = Math
				.acos(((mLineA * mLineA) + (mLineB * mLineB) - (mLineC * mLineC))
						/ (2d * mLineA * mLineB));

		
		getDot2(paint, canvas);

	}

	public void getDot2(Paint paint, Canvas canvas) {
		//下面公式通过圆心点坐标和柱状体顶部中心点坐标，通过二元一次方程组计算出其余两个三角形的坐标点位置
		// x=x1-a*sin{arctan[(y2-y1)/(x2-x1)]}
		// y=y1+a*cos{arctan[(y2-y1)/(x2-x1)]}
		
		
		//求出坐标点P
		double x1 = mPointA.x - 50 * Math.sin(Math.atan((mPointB.y - mPointA.y) / (mPointB.x - mPointA.x)));
		double y1 = mPointA.y + 50 * Math.cos(Math.atan((mPointB.y - mPointA.y) / (mPointB.x - mPointA.x)));
		
		//求出坐标点P'
		double x2 = mPointA.x + 50 * Math.sin(Math.atan((mPointB.y - mPointA.y) / (mPointB.x - mPointA.x)));
		double y2 = mPointA.y - 50 * Math.cos(Math.atan((mPointB.y - mPointA.y) / (mPointB.x - mPointA.x)));

		
		Log.i("dg", "x >>> " + x1 + "  y >>> " + y1);
		
		//开始画三角形
		Path path = new Path();// 三角形
		
		
		path.moveTo((float)(x2), (float)(y2));//P点坐标
		path.lineTo((float)(mPointB.x), (float)(mPointB.y));//圆心点坐标
		path.lineTo((float)x1, (float)y1);//P'点坐标
		path.close();//闭合画笔路径
		canvas.drawPath(path, paint);//开始画

	}
	
	/**
	 * 通过不同等级，塞入一个柱状体顶部中心点坐标
	 * @param pointB
	 */
	public void setData(PointBean pointB){
		mPointB = pointB;
		invalidate();
	}

	
}
