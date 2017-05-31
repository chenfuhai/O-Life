package com.olife.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.CpuUsageInfo;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.handle;
import static android.R.attr.radius;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by chenfuhai on 2016/11/30 0030.
 */

public class MyView extends View {


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布填充黑色
        canvas.drawColor(Color.BLACK);
//        area1(canvas);
//        area2(canvas);
//        area3(canvas);
//        area4(canvas);
        area5(canvas);

    }

    /**
     * 画圆弧
     *
     * @param canvas
     */
    private void area5(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);//绘制的线条的颜色
        paint.setStyle(Paint.Style.STROKE);//绘制出来的图案填充 FILL 实心 Stroke 空心 同时实心或者空心
        paint.setStrokeWidth(4);//如果是空心的话 设置线的宽度
        int viewWidth = this.getWidth();//获取到这个View的宽

    /*
     * @param oval 画弧线矩形区域
     * @param startAngle 开始的角度 从右边开始为0度 向下画圆弧要向上就为负数 要想从左边开始 就把开始的角度定为180
     * @param sweepAngle 划过的角度
     * @param useCenter 如果为true 则为有连接圆心的圆弧 否则只有圆弧
     * @param paint     画笔*/
        int cx = 250,cy=250;
        int radius = (int) (70-paint.getStrokeWidth());


        RectF rectF = new RectF(cx/2-radius, cy/2-radius, cx/2+radius,cy/2+radius);
        canvas.drawRect(rectF, paint);
        paint.setColor(Color.GREEN);

        canvas.drawArc(rectF,180,90,true,paint);




        //canvas.drawArc();

    }

    /**
     * 填充效果
     *
     * @param canvas
     */

    private void area4(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);//绘制的线条的颜色
        paint.setStyle(Paint.Style.STROKE);//绘制出来的图案填充 FILL 实心 Stroke 空心 同时实心或者空心
        paint.setStrokeWidth(4);//如果是空心的话 设置线的宽度
        int viewWidth = this.getWidth();//获取到这个View的宽

        RectF rectArea3 = new RectF(400, viewWidth / 3 + 40, 500 + viewWidth / 2, viewWidth * 5 / 2 + 40);
        //--------------更换填充效果----------------------
        Shader shader = new LinearGradient(0, 0, 50, 30, new int[]{Color.RED, Color.BLUE, Color.GREEN}, null, Shader.TileMode.MIRROR);
        //填充效果 线性渐变 x1 y1 x2 y2 渐变的颜色 渐变的模式 MIRROR一种颜色从中间到两边镜像 REPEAT 重复 CLAMP夹住
        Shader shader2 = new RadialGradient(10, 20, 100, new int[]{Color.RED, Color.BLUE, Color.GREEN}, null, Shader.TileMode.REPEAT);
        //弧形渐变 中心点的位置 rx ry 相对整个图形来说 上面的那个也一样 r 弧度 radian
        Shader shader3 = new SweepGradient(10, 10, new int[]{Color.RED, Color.BLUE, Color.GREEN}, null);
        //扫描渲染
        paint.setShader(shader2);
        paint.setShadowLayer(25, 20, 20, Color.GRAY);

        canvas.drawRect(rectArea3, paint);
    }

    /**
     * 基本的绘制
     *
     * @param canvas
     */
    private void area1(Canvas canvas) {
        //初始化画笔(绘制的配置)
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        //抗锯齿（Anti-aliasing）：标准翻译为”抗图像折叠失真“。
        // 由于在3D图像中，受分辨的制约，物体边缘总会或多或少的呈现三角形的锯齿，
        // 而抗锯齿就是指对图像边缘进行柔化处理，使图像边缘看起来更平滑，更接近实物的物体。
        // 它是提高画质以使之柔和的一种方法。

        paint.setColor(Color.WHITE);//绘制的线条的颜色
        paint.setStyle(Paint.Style.STROKE);//绘制出来的图案填充 FILL 实心 Stroke 空心 同时实心或者空心
        paint.setStrokeWidth(4);//如果是空心的话 设置线的宽度

        int viewWidth = this.getWidth();//获取到这个View的宽

        canvas.drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10, viewWidth / 10, paint);//绘制圆
        //cx：圆心的x坐标。cy：圆心的y坐标。radius：圆的半径。
        canvas.drawRect(viewWidth / 10 + 150, viewWidth / 10, viewWidth / 10 + 300, viewWidth / 10 + 100, paint);
        //绘制矩形 参数为 x1 y1 x2 y2 paint 对角线的点存在的坐标

        RectF rectArea = new RectF(10, viewWidth / 2 + 40, 10 + viewWidth / 5, viewWidth * 3 / 5 + 40);//表示坐标系上的一块矩形区域
        //Rect 和其一样 前者提供的精度是Float 后者提供的精度是int
        paint.setColor(Color.RED);
        canvas.drawRect(rectArea, paint);

        paint.setColor(Color.WHITE);

        canvas.drawRoundRect(rectArea, 15, 15, paint);//绘制圆角矩形
        //rectArea：矩形区域。  rx：x方向上的圆角半径。 ry：y方向上的圆角半径。 paint：绘制时所使用的画笔。

        RectF rectArea2 = new RectF(200, viewWidth / 2 + 40, 200 + viewWidth / 5, viewWidth * 3 / 5 + 40);
        canvas.drawOval(rectArea2, paint);//绘制椭圆 在一个矩形区域内
    }

    /**
     * Path 的绘制
     *
     * @param canvas
     */
    private void area2(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.WHITE);//绘制的线条的颜色
        paint.setStyle(Paint.Style.STROKE);//绘制出来的图案填充 FILL 实心 Stroke 空心 同时实心或者空心
        paint.setStrokeWidth(4);//如果是空心的话 设置线的宽度
        int viewWidth = this.getWidth();//获取到这个View的宽

        //------------------------以下利用PATH提供路径点来绘制图形---------------------------------------
        paint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(100, 500);//移动到开始的点
        path.lineTo(20, 36);//从上一个点连线到这一个点
        path.lineTo(500, 300);
        path.lineTo(530, 800);
        path.lineTo(30, 390);
        path.lineTo(250, 400);
        path.close();//关闭 绘制结束 会连接到最开始的那个点 不使用这个方法就不会构成闭合的图形
        canvas.drawPath(path, paint);

        //------------更换填充的风格 实心填充----------------
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        RectF rectArea3 = new RectF(400, viewWidth / 3 + 40, 500 + viewWidth / 2, viewWidth * 5 / 2 + 40);
        canvas.drawRect(rectArea3, paint);
    }

    /**
     * 文字的绘制
     *
     * @param canvas
     */
    private void area3(Canvas canvas) {
        //绘制文字
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setTextSize(48);//设置大小

        canvas.drawText("几声红", 100, 300, paint1);

        //canvas.drawText("几声红",0,2,100,300,paint1); 显示 几声
        //int start 字符串中要绘制的字符的下标 int end 结束绘制的下标
        // 从小标为0的字符串位置的字符开始绘制 到小标为2的位置截止

        //这里的100,300是基线 就是写字的时候的4格线第三条所在的位置的起点

        paint1.setColor(Color.RED);
        canvas.drawLine(100, 300, 500, 300, paint1);//画出基线
        paint1.setColor(Color.GREEN);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(10);
        canvas.drawPoint(100, 300, paint1);//画出起点
    }


}
