package com.cailei.qqstepview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {
    //外圆的颜色
    private int mOutColor;
    //外圆画笔
    private Paint mOutPaint;

    //内圆的颜色
    private int mInnerColor;
    //内圆画笔
    private Paint mInnerPaint;

    //环形圆的宽度  20代表的是20px
    private int mBorderWidth=20;


    private Paint mTextPaint;
    //文字大小
    private int mStepTextSize=16;


    private int mStepTextColor;




    //总共的   当前的
    private int mStepMax=100;
    private int currentStep=50;





   ObjectAnimator objectAnimator;


    private int viewHeight;
    private int viewWidth;
    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.QQStepView);
        mInnerColor=array.getColor(R.styleable.QQStepView_innerColor,Color.RED);
        mOutColor=array.getColor(R.styleable.QQStepView_outColor,Color.BLUE);
        mBorderWidth= (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize=array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor=array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);


        initPaints();
        array.recycle();
    }

    private void initPaints() {
        mOutPaint=new Paint();
        //设置抗锯齿
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOutColor);
        //设置边缘
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置空心
        mOutPaint.setStyle(Paint.Style.STROKE);


        mInnerPaint=new Paint();
        //设置抗锯齿
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        //设置边缘
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置空心
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint();
        //设置抗锯齿
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth=MeasureSpec.getSize(widthMeasureSpec);
        viewHeight=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(viewWidth>viewHeight?viewHeight:viewWidth,viewWidth>viewHeight?viewHeight:viewWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //startAngle 开始角度 x轴正向是0度 sweepAngle 顺时针扫过的角度
        RectF rectF=new RectF(0+mBorderWidth/2,0+mBorderWidth/2,getWidth()-mBorderWidth/2,getHeight());
        canvas.drawArc(rectF,135,270,false,mOutPaint);


        if(mStepMax==0){
            return;
        }
        //画内圆
        float sweepAngle=(float)currentStep/mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);



        //画文字
        String setpText=currentStep+"";
        Rect bound=new Rect();
        mTextPaint.getTextBounds(setpText,0,setpText.length(),bound);
        int dx=getWidth()/2-(bound.right-bound.left)/2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt=mTextPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getWidth()/2+dy;
        canvas.drawText(setpText,dx,baseLine,mTextPaint);

    }



    public void setMaxStep(int maxStep){
        this.mStepMax=maxStep;
    }


    public synchronized void setCurrentStep(int currentStep){
        this.currentStep=currentStep;
        //重新绘制
        invalidate();
    }




}
