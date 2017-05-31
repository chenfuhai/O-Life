package com.olife.pointview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * version 1.0<br/>
 * <br/>
 * Created by chenfuhai on 2016/12/3 0003.
 * 扩展属性可在xml 里面设置<br/>
 * <br/>等待圈点的大小 waiting_strokewidth<br/>
 * <br/>等待圈的个数 waiting_pointcount<br/>
 * <br/>等待圈颜色 waiting_pointcolor<br/>
 * <br/>分数字体的颜色 score_fontcolor<br/>
 * <br/> 最大的分数 score_max<br/>
 * <br/> 分数字体的大小 score_size<br/>
 * <br/>分数单位 分 的字体颜色 unit_fontcolor<br/>
 * <br/>分数单位 分 的默认文字 unit_text<br/>
 * <br/> 提示文字的颜色 tip_fontcolor<br/>
 * <br/>未开始之前的提示文字 tip_before<br/>
 * <br/> 开始中的提示文字 tip_doing<br/>
 * <br/> 结束后的提示文字 tip_done<br/>
 * 4.4 以上 或者5.0以上使用
 */

public class PointViewFull extends View {



    //分为三个部分 中间部分的文字 背景色 等待圈 一些标记 对外的接口

    /**
     * 颜色改变接口
     */
   public  interface ColorChangeLisenter{
        /**
         * 当颜色改变的时候触发 提供当前背景的颜色的值
         * @param
         */
        void colorChanged(int color);
    }
    private ColorChangeLisenter mColorChangeLisenter;
    /**
     * 控件默认的大小
     */
    private int mDesiredSize;
    /**
     * 背景色画笔
     */
    private Paint mBgPaint;
    /**
     * 背景色所变化的渐变器 绿
     */
    private Shader shaderGreen;
    /**
     * 背景色所变化的渐变器 橙
     */
    private Shader shaderOragen;
    /**
     * 背景色所变化的渐变器 红
     */
    private Shader shaderRed;

    /**
     * 应用名称标题画笔 区域 默认文字 大小 颜色
     */
    private Paint mTitlePaint;
    /**
     * 应用名称标题的大小
     */
    private float mTitleFontSize;
    /**
     * 应用名称标题的颜色
     */
    private int mTitleColor;
    /**
     * 应用名称标题所在的矩形
     */
    private Rect mOvalTitle;
    /**
     * 应用名称标题的默认文字
     */
    private String mTitle;


    /**
     * 待圈画笔
     */
    private Paint mWaitingPaint;
    /**
     * 等待圈的点的大小
     */
    private float mWaitingStrokeWidth;
    /**
     * 等待圈的点的颜色
     */
    private int mWaitingPointColor;
    /**
     * 等待圈弧形所在的矩形
     */
    private RectF mOvalWaitting;
    /**
     * 等待圈的半径
     */
    private float mRadiusPoint;
    /**
     * 等待圈点的个数
     */
    private int mWaitingPointCount;
    /**
     * 等待圈点的集合
     */
    private ArrayList<Map<String, Float>> mPointList;
    /**
     * 分数文字画笔
     */
    private Paint mTextScorePaint;
    /**
     * 分数文字的大小
     */
    private float mTextScoreSize;
    /**
     * 分数文字的颜色
     */
    private int mTextScoreColor;
    /**
     * 分数文字所在的矩形
     */
    private Rect mOvalTextScore;
    /**
     * 分数文字的默认文字
     */
    private String mTextScore;
    /**
     * 分数文字最大的分数
     */
    private int mProgessMax;
    /**
     * 中间文字的实时分数
     */
    private int mProgessCurr;
    /**
     * 分这个字2的绘制
     */
    private Paint mTextUnitPaint;
    /**
     * 分这个字的大小
     */
    private float mTextUnitSize;
    /**
     * 分这个字的颜色
     */
    private int mTextUnitColor;
    /**
     * 分这个字所在的矩形
     */
    private Rect mOvalTextUnit;
    /**
     * 分这个字的默认文字
     */
    private String mTextUnit;

    /**
     * 正在检测这个字3的绘制  提示文字
     */
    private Paint mTextTipPaint;
    /**
     * 提示文字的大小
     */
    private float mTextTipSize;
    /**
     * 提示文字的颜色
     */
    private int mTextTipColor;
    /**
     * 提示文字所在的矩形
     */
    private Rect mOvalTextTip;
    /**
     * 提示文字的开始的时候的默认文字
     */
    private String mTextTipStart;
    /**
     * 提示文字 代码中用这个来控制
     */
    private String mTextTip;
    /**
     * 提示文字的正在做的时候的时候的默认文字
     */
    private String mTextTipDoing;
    /**
     * 提示文字的结束的时候的默认文字
     */
    private String mTextTipStop;
    /**
     * 控制外圈转动的线程
     */
    private Thread thread;
    /**
     * 控制外圈转动的线程的Runnable
     */
    private Runnable waitingControlRunnable;

    //各种标记
    /**
     * 是否可以旋转画布标记
     */
    private static boolean IsWaitingOk;

    /**
     * 线程与主线程交互的时候使用
     */
    private Handler mHandler;

    /**
     * 设置状态栏变色需要当前页面的activity
     *
     * @param context
     * @param attrs
     */
    private Activity mCurrActivity;


    public PointViewFull(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化从xml文件布局中读取的属性 颜色什么的
        mHandler = new Handler();
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.PointViewFull);

        mProgessMax = typedArray.getInteger(R.styleable.PointViewFull_score_max_full, 100);
        mProgessCurr = mProgessMax;

        mWaitingStrokeWidth = typedArray.getDimension(R.styleable.PointViewFull_waiting_strokewidth_full,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        mWaitingPointCount = typedArray.getInteger(R.styleable.PointViewFull_waiting_pointcount_full, 48);
        mWaitingPointColor = typedArray.getColor(R.styleable.PointViewFull_waiting_pointcolor_full, Color.WHITE);

        mTextScoreSize = typedArray.getDimension(R.styleable.PointViewFull_score_size_full,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics()));
        mTextScoreColor = typedArray.getColor(R.styleable.PointViewFull_score_fontcolor_full, Color.WHITE);
        mTextScore = mProgessCurr + "";

        mTextUnitSize = mTextScoreSize / 3f;
        mTextUnitColor = typedArray.getColor(R.styleable.PointViewFull_unit_fontcolor_full, Color.WHITE);

        mTextTip = mTextTipStart;
        mTextTipSize = mTextScoreSize / 3f;
        mTextTipColor = typedArray.getColor(R.styleable.PointViewFull_tip_fontcolor_full, Color.WHITE);

        mTitleFontSize = mTextTipSize * 1.5f;
        mTitleColor = typedArray.getColor(R.styleable.PointViewFull_title_font_color_full, Color.WHITE);

        if (typedArray.getString(R.styleable.PointViewFull_unit_text_full) != null) {
            mTextUnit = typedArray.getString(R.styleable.PointViewFull_unit_text_full);
        } else {
            mTextUnit = "分";
        }

        if (typedArray.getString(R.styleable.PointViewFull_tip_before_full) != null) {
            mTextTipStart = typedArray.getString(R.styleable.PointViewFull_tip_before_full);
        } else {
            mTextTipStart = "点击立即检测";
        }
        if (typedArray.getString(R.styleable.PointViewFull_tip_doing_full) != null) {
            mTextTipDoing = typedArray.getString(R.styleable.PointViewFull_tip_doing_full);
        } else {
            mTextTipDoing = "检测中...";
        }
        if (typedArray.getString(R.styleable.PointViewFull_tip_done_full) != null) {
            mTextTipStop = typedArray.getString(R.styleable.PointViewFull_tip_done_full);
        } else {
            mTextTipStop = "检测完成";
        }
        if (typedArray.getString(R.styleable.PointViewFull_title_full) != null) {
            mTitle = typedArray.getString(R.styleable.PointViewFull_title_full);
        } else {
            mTitle = "O-Life";
        }

        mTextTip = mTextTipStart;

        typedArray.recycle();

        mDesiredSize = dip2px(60);

        //背景色画笔 实心填充 设置渐变（在ondraw中） 抗锯齿
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        //等待圈画笔 空心  抗锯齿 圆润
        mWaitingPaint = new Paint();
        mWaitingPaint.setStyle(Paint.Style.STROKE);
        mWaitingPaint.setAntiAlias(true);
        mWaitingPaint.setStrokeCap(Paint.Cap.ROUND);
        mWaitingPaint.setColor(mWaitingPointColor);
        mWaitingPaint.setStrokeWidth(mWaitingStrokeWidth);


        //分数文字
        mTextScorePaint = new Paint();
        mTextScorePaint.setAntiAlias(true);
        mTextScorePaint.setStyle(Paint.Style.STROKE);
        mTextScorePaint.setStrokeCap(Paint.Cap.ROUND);
        mTextScorePaint.setColor(mTextScoreColor);
        mTextScorePaint.setTextSize(mTextScoreSize);
        mOvalTextScore = new Rect();

        //分数单位
        mTextUnitPaint = new Paint();
        mTextUnitPaint.setAntiAlias(true);
        mTextUnitPaint.setStyle(Paint.Style.STROKE);
        mTextUnitPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextUnitPaint.setColor(mTextUnitColor);
        mTextUnitPaint.setTextSize(mTextUnitSize);
        mOvalTextUnit = new Rect();

        //提示文字
        mTextTipPaint = new Paint();
        mTextTipPaint.setAntiAlias(true);
        mTextTipPaint.setStyle(Paint.Style.STROKE);
        mTextTipPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextTipPaint.setColor(mTextTipColor);
        mTextTipPaint.setTextSize(mTextTipSize);
        mOvalTextTip = new Rect();

        //其他初始化

        //标题文字
        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setStyle(Paint.Style.STROKE);
        mTitlePaint.setStrokeCap(Paint.Cap.ROUND);
        mTitlePaint.setColor(mTitleColor);
        mTitlePaint.setTextSize(mTitleFontSize);
        mOvalTitle = new Rect();


        //存储点的列表
        mPointList = new ArrayList<>();

        //可否旋转画布的标记
        PointViewFull.IsWaitingOk = false;

        //控制线程的核心
        waitingControlRunnable = new Runnable() {
            @Override
            public void run() {
                //设置可以旋转外圈
                PointViewFull.IsWaitingOk = true;
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //设置不必旋转外圈了
                        PointViewFull.IsWaitingOk = false;
                        //线程在睡眠的时候 使用 interrupt 会抛出异常 这个时候将其return结束 巧妙结束线程
                        break;
                    }
                    //刷新工作
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                }
            }
        };


    }

    /**
     * 旋转的角度
     */
    private int degress = 0;

    //1.只有在这个方法里面才会绘图
    //2.每一次调用invalidate(); 或者 postInvalicate（） 都会从新调用这个方法
    //3.绘制完的canvas会被回收掉 每一次都是新的canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制随分数改变的背景 绘制文本 绘制外围等待圈

        drawBg(canvas);

        if (PointViewFull.IsWaitingOk) {
            canvas.save();
            degress += 2;//4度以上会出现视觉错误 逆转
            canvas.rotate(degress, getWidth() / 2, getHeight() / 2 + mOvalTitle.height() / 1.2f);
            drawWaiting(canvas);
            canvas.restore();
        } else {
            drawWaiting(canvas);
        }

        drawMyText(canvas);

    }


    //绘制文字部分
    private void drawMyText(Canvas canvas) {

        //标题文字 标题的矩形大小不变 已经在initdata中计算过了
        float stratX4 = getWidth() / 2 - mOvalTitle.width() / 2 - dip2px(5);
        float stratY4 = mOvalTitle.height() * 1.5f;
        canvas.drawText(mTitle, stratX4, stratY4, mTitlePaint);

        if (mProgessCurr < 10) {
            mTextScore = "0" + mProgessCurr + "";
        } else {
            mTextScore = mProgessCurr + "";
        }
        //分数文字
        mTextScorePaint.getTextBounds(mTextScore, 0, mTextScore.length(), mOvalTextScore);
        float stratX = getWidth() / 2 - mOvalTextScore.width() / 2 - dip2px(10);
        float stratY = getHeight() / 2 + mOvalTextScore.height() / 2 - dip2px(10) + mOvalTitle.height() / 1.2f;
        canvas.drawText(mTextScore, stratX, stratY, mTextScorePaint);
        //辅助线
        //canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,mTextScorePaint);
        //canvas.drawLine(getWidth()/2,0,getWidth()/2,getHeight(),mTextScorePaint);

        //分数单位
        mTextUnitPaint.getTextBounds(mTextUnit, 0, mTextUnit.length(), mOvalTextUnit);
        float stratX2 = getWidth() / 2 + mOvalTextScore.width() / 2 + dip2px(3);
        float stratY2 = getHeight() / 2 + mOvalTextScore.height() / 2 - dip2px(10) + mOvalTitle.height() / 1.2f;
        canvas.drawText(mTextUnit, stratX2, stratY2, mTextUnitPaint);
        //提示文字
        mTextTipPaint.getTextBounds(mTextTip, 0, mTextTip.length(), mOvalTextTip);
        float stratX3 = getWidth() / 2 - mOvalTextTip.width() / 2;
        float stratY3 = getHeight() / 2 + mOvalTextTip.height() / 2 + mOvalTextScore.height() / 2 + dip2px(5) + mOvalTitle.height() / 1.2f;
        canvas.drawText(mTextTip, stratX3, stratY3, mTextTipPaint);
    }

    //绘制外边的等待圈
    private void drawWaiting(Canvas canvas) {
        for (int i = 0; i < mPointList.size(); i++) {
            float stratX = mPointList.get(i).get("x1");
            float stratY = mPointList.get(i).get("y1");
            canvas.drawPoint(stratX, stratY, mWaitingPaint);
        }

    }

    //绘制背景，随分数变化而变化
    private void drawBg(Canvas canvas) {
        if (mProgessCurr >= 80) {
            mBgPaint.setShader(shaderGreen);//默认的背景绘制效果 绿色
            setCurrStatusBarColor(Color.parseColor("#49aa4d"));
            if (mColorChangeLisenter!=null){
                mColorChangeLisenter.colorChanged(Color.parseColor("#49aa4d"));
            }
        }
        if (mProgessCurr < 80 && mProgessCurr >= 60) {
            mBgPaint.setShader(shaderOragen);
            setCurrStatusBarColor(Color.parseColor("#E89420"));
            if (mColorChangeLisenter!=null){
                mColorChangeLisenter.colorChanged(Color.parseColor("#E89420"));
            }
        }
        if (mProgessCurr < 60) {
            mBgPaint.setShader(shaderRed);
            setCurrStatusBarColor(Color.parseColor("#D45036"));
            if (mColorChangeLisenter!=null){
                mColorChangeLisenter.colorChanged(Color.parseColor("#D45036"));
            }
        }
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBgPaint);
    }

    //初始化数据 比如弧线的矩形的区域之类的
    //系统在创建这个类对象的时候还没有测量布局文件里面的控件的宽高 这个时候不能初始化类似半径之类的数据 在onSizeChange之后再初始化数据
    private void initData() {
        //初始化弧线的矩形区域 找到相对应的点等 渐变器
        //渐变背景
        if (shaderGreen == null) {
            shaderGreen = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(),
                    Color.parseColor("#49aa4d"), Color.parseColor("#4dbe57"),
                    Shader.TileMode.MIRROR);
        }
        if (shaderOragen == null) {
            shaderOragen = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(),
                    Color.parseColor("#E89420"), Color.parseColor("#ECBE57"),
                    Shader.TileMode.MIRROR);
        }
        if (shaderRed == null) {
            shaderRed = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(),
                    Color.parseColor("#D45036"), Color.parseColor("#EB614D"),
                    Shader.TileMode.MIRROR);
        }

        //标题文字 要最先计算 后面的需要用到标题的大小
        mTitlePaint.getTextBounds(mTitle, 0, mTitle.length(), mOvalTitle);

        //点
        mRadiusPoint = getHeight() / 2f / 1.8f;
        mOvalWaitting = new RectF();
        mOvalWaitting.left = (getWidth() / 2 - mRadiusPoint);
        mOvalWaitting.top = (getHeight() / 2 - mRadiusPoint) + mOvalTitle.height() / 1.2f;
        mOvalWaitting.right = (getWidth() / 2 + mRadiusPoint);
        mOvalWaitting.bottom = (getHeight() / 2 + mRadiusPoint) + mOvalTitle.height() / 1.2f;

        //除了标题意外的文字部分的绘制不能再iniData方法中执行 因为该方法只会执行一次 每次绘制都要计算位置 不然位置会出错


        //pm.getPosTan 能找出距离初始位置长度为itemLength*i 的点的坐标 并将其写入到pos数组中 null是正切值 一般不用

        Path path = new Path();
        path.addArc(mOvalWaitting, 0, 359.9f);
        PathMeasure pm = new PathMeasure(path, false);//false代表不关闭路径
        float itemLength = pm.getLength() / (mWaitingPointCount - 1);//每两个点之间的距离 总长度/（点数-1）
        for (int i = 0; i < mWaitingPointCount; i++) {
            float[] pos = new float[2];
            pm.getPosTan(itemLength * i, pos, null);
            Map<String, Float> map = new HashMap<>();
            map.put("x1", pos[0]);
            map.put("y1", pos[1]);
            mPointList.add(map);
        }


    }

    //在这里面初始化相关的数据 比如图案的矩形区域
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initData();
    }

    //dp像素转换工具
    private int dip2px(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    //传入当前控件的大小 而决定View的大小只需要两个值：宽 详细 测量值（widthMeasureSpec）和高 详细 测量值（heightMeasureSpec）。
    // 也可以把详细测量值理解为视图View想要的大小说明（想要的未必就是最终大小）。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //onMesure的最总目的 存储我们测量值里面的size 现在测量值里面有多种mode 我们根据自己的情况决定什么mode下传什么size给系统
        // 现在的任务就是 计算出准确 或者我们想要的 的measuredWidthSize和heightMeasureSize并传递进去
        //setMeasuredDimension();
        int measureWidth = resolveSize(widthMeasureSpec, mDesiredSize);
        int measureHeight = resolveMeasured(heightMeasureSpec, mDesiredSize);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 解析测量值 根据用户的布局 MeasureSpec封装了父布局传递给子布局的布局要求
     * 每个MeasureSpec代表了一组宽度和高度的要求
     * MeasureSpec由size和mode组成。
     *
     * @param measureSpec 实际的值
     * @param desiredSize 期望的值
     * @return 返回测量值的size
     */
    private int resolveMeasured(int measureSpec, int desiredSize) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED: //view 的高度为0 或者没有设置宽高
                result = desiredSize;
                break;
            case MeasureSpec.AT_MOST:  //wrap-content
                // (当设置为wrap_content时，模式为AT_MOST, 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸)
                result = Math.min(specSize, desiredSize);//wap_content 就是用默认值和传入值比较小的那一个
                break;
            case MeasureSpec.EXACTLY:  //match-content
                // (当设置width或height为match_parent时，模式为EXACTLY，因为子view会占据剩余容器的空间，所以它大小是确定的)
            default:
                result = specSize;
        }
        return result;
    }

    private void setCurrStatusBarColor(int color) {
        if (this.mCurrActivity != null) {


            try {
                //如果SDK的版本大于或等于 5.0 的时候才可以更换颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = mCurrActivity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(color);

                    //底部导航栏
                    //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始外圈旋转
     */
    public void waitingStart() {
        if (thread == null) {
            thread = new Thread(waitingControlRunnable);
            thread.start();
        }
    }

    /**
     * 停止外圈旋转
     */
    public void waitingStop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
            degress = 0;
        }
    }

    /**
     * 设置实时显示的分数
     *
     * @param score
     */
    public void setCurrScore(int score) {
        if (score >= 0) {
            this.mProgessCurr = score;
            invalidate();
        }
    }

    public int getCurrScore() {
        return mProgessCurr;
    }

    /**
     * 设置实时提示文字 错误时候可以提示 只能传6个汉字或者字母
     */

    public void setCurrText(String message) {
        if (message.length() > 6) {
            message = message.substring(0, 6);
        }
        mTextTip = message + "...";
        invalidate();
    }


    /**
     * 设置使用这个View的当前的activity 设置状态栏可变色4.4以上
     */
    public void setCurrActivity(Activity currActivity) {
        this.mCurrActivity = currActivity;
    }

    /**
     * 设置颜色改变监听器
     */
    public void setOnColorChangerListener(ColorChangeLisenter colorChangerListener){
        this.mColorChangeLisenter = colorChangerListener;
    }

}
