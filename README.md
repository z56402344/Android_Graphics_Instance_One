#Android的Graphics类绘制图形 (below chart)
#（下面有效果图）


English Description 

This Demo is through the Canvas on the Graphics class Adnroid, Paint, the Path to map the instance of project, for your reference

BaseAnimation  QQ group：149581646

Author's blog: http://blog.csdn.net/duguang77




中文说明

这个Demo是通过Adnroid的Graphics类中的Canvas，Paint，Path绘制出的实例工程，供大家参考

BaseAnimation QQ群：149581646

作者博客：http://blog.csdn.net/duguang77



====


# ScreenShot

![Image][1]
.
![Image][2]
.
![Image][3]

![Image][4]



[1]: http://img.blog.csdn.net/20141106215620406
[2]: http://img.blog.csdn.net/20141107124210406
[3]: http://img.blog.csdn.net/20141106220033956
[4]: http://img.blog.csdn.net/20141106220153781

这样拆分出来的图，大家就应该知道这张图示怎么画的吧！
我们来细讲一下，圆心点坐标我们通过
protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();
		mCenterX = mWidth/2;
		mCenterY = mHeight/4;
	}

继承的View类 OnDraw（）方法中的Canvas获取出屏幕一半宽，1/4高的点的位置，这就是上图中的O点坐标，而柱状体我们也是通过自己给的坐标点画出的，所以这两个点都是已知的。

我们最重要的是控制好过圆心，画出三角形，
我们通过之前了解到通过Canvas+Path+Paint组合API可以画出三角形，但是我们并不知道点P和P'的坐标位置，

    //开始画三角形
		Path path = new Path();// 三角形
		
		
		path.moveTo((float)(x2), (float)(y2));//P点坐标
		path.lineTo((float)(mPointB.x), (float)(mPointB.y));//圆心点坐标
		path.lineTo((float)x1, (float)y1);//P'点坐标
		path.close();//闭合画笔路径
		canvas.drawPath(path, paint);//开始画

通过简化图，我们可以看出，其实就是一个数学问题，通过点O坐标和点H坐标，OP'和OP边长是自己给定的定值所以也是已知的，OH边长已知，PH和P'H通过勾三股四算出可得，有了这些参数我们就可以组成一个二元一次方程组来算出P和P'坐标如下所示
