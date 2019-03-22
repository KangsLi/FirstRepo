package com.example.mooddetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyTempView extends View {

    private String temp;
    private Paint currentTmp;
    private Paint mPaint;
    private Paint textPaint;
    private Paint paintCircle;
    private Paint paintLine;
    private Bitmap bitmaplv;
    private Bitmap bitmaplan;
    private Bitmap bitmapred;
    private Context context;
    private Paint mPaintOther;
    private Paint left;

    public MyTempView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentTmp = new Paint();
        currentTmp.setAntiAlias(true);
        currentTmp.setTextSize(20);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#bce3fe"));
        // mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(18);
        paintCircle = new Paint();
        // paintCircle.setColor(Color.parseColor("#61BEE7"));
        paintCircle.setAntiAlias(true);
        paintCircle.setTextSize(45);
        left=new Paint();
        left.setAntiAlias(true);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        paintLine = new Paint();
        paintLine.setStrokeWidth(2.5f);
        paintLine.setColor(Color.BLUE);
        bitmaplv = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_s);
        bitmaplan = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_lan_small);
        bitmapred = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_red_small);
        Paint rightPaint = new Paint();
        rightPaint.setAntiAlias(true);
        mPaintOther = new Paint();
        mPaintOther.setColor(Color.parseColor("#030102"));
        mPaintOther.setAntiAlias(true);
        mPaintOther.setStrokeWidth(1);

    }


    public void setTemp(String temp,Context context) {
        this.temp = temp;
        this.context = context;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,380 + 120);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressWarnings("deprecation")
        // 屏幕宽度的一半
        int width = getWidth() / 2;
        Log.d("getWidth",String.valueOf(width));
        Log.d("getHeight",String.valueOf(getHeight()));
        // int screenHeight = getHeight();
        // int m = (int) (MyUtils.px2dp(context, screenHeight) * 1.1 + 30);
        float tem1 = Float.parseFloat(temp);

        int y = getHeight()/2;
        textPaint.setColor(Color.parseColor("#f9847b"));



        textPaint.setColor(Color.parseColor("#479aed"));


        // 温度计 矩形
        canvas.drawRect(0, 0, width*2 , getHeight(), mPaint);
        currentTmp.setColor(Color.parseColor("#3DB475"));
        paintCircle.setColor(Color.parseColor("#3DB475"));
        left.setColor(Color.parseColor("#d9f3ff"));
        // 当前温度表示 矩形
        canvas.drawRect(width -getHeight()/8+10, 0, width +getHeight()/8-10,getHeight()/4, left);
        canvas.drawRect(width -getHeight()/8+10, getHeight()/4, width +getHeight()/8-10,getHeight()*13/16, paintCircle);
        // 圆形
        canvas.drawCircle(width, getHeight()*7/8, getHeight()/8, paintCircle);
        // 右\侧三角形刻度
        canvas.drawBitmap(bitmaplv, width +getHeight()/8-10,getHeight()/4-bitmaplv.getHeight()/2 , mPaint);
        // 当前温度字体
        canvas.drawText(temp + "°C", width +getHeight()/8-10+ bitmaplv.getWidth() ,
                getHeight()/4, paintCircle);
        /*canvas.drawText("当前温度", width +getHeight()/8-10+ bitmaplv.getWidth(),
                getHeight()/4+20, currentTmp);*/

    }
}

