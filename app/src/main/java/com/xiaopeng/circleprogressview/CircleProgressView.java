package com.xiaopeng.circleprogressview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Date: 2019/4/19
 * Created by LiuJian
 *
 * @author LiuJian
 */

public class CircleProgressView extends View {

    private float mProgress;
    private float mArcStart;
    private float mArcEnd;
    private double mRotateStep;
    private Bitmap mBitmap;
    private int mTotalTime;
    private Bitmap mImage;
    private Drawable mDrawable;
    private int mBoundWidth;
    private int mProgressWidth;
    private boolean mIsRotate;
    private int mProgressColor;
    private int mProgressBackColor;
    private float mRotateDegree;

    private float mRadius;
    private Paint mPaint;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgress = 360;
        mArcStart = 270;
        mArcEnd = 360;
        mRotateStep = 0.2;

        mBoundWidth = 5;
        mProgressWidth = 30;
        mIsRotate = false;
        mProgressColor = Color.GREEN;
        mProgressBackColor = Color.GRAY;
        mRotateDegree = 0;

    }


    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
    }

    public float getArcStart() {
        return mArcStart;
    }

    public void setArcStart(float mArcStart) {
        this.mArcStart = mArcStart;
    }

    public float getArcEnd() {
        return mArcEnd;
    }

    public void setArcEnd(float mArcEnd) {
        this.mArcEnd = mArcEnd;
    }

    public double getRotateStep() {
        return mRotateStep;
    }

    public void setRotateStep(double mRotateStep) {
        this.mRotateStep = mRotateStep;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(int mTotalTime) {
        this.mTotalTime = mTotalTime;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getBoundWidth() {
        return mBoundWidth;
    }

    public void setBoundWidth(int mBoundWidth) {
        this.mBoundWidth = mBoundWidth;
    }

    public int getProgressWidth() {
        return mProgressWidth;
    }

    public void setProgressWidth(int mProgressWidth) {
        this.mProgressWidth = mProgressWidth;
    }

    public boolean isRotate() {
        return mIsRotate;
    }

    public void setRotate(boolean mRotate) {
        mIsRotate = mRotate;
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(int mProgressColor) {
        this.mProgressColor = mProgressColor;
    }

    public int getProgressBackColor() {
        return mProgressBackColor;
    }

    public void setProgressBackColor(int mProgressBackColor) {
        this.mProgressBackColor = mProgressBackColor;
    }

    public float getRotateDegree() {
        return mRotateDegree;
    }

    public void setRotateDegree(float mRotateDegree) {
        this.mRotateDegree = mRotateDegree;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制内圈begin
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        mRadius = Math.min(width, height) / 2 - mBoundWidth - mProgressWidth;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBoundWidth);
        mPaint.setColor(Color.BLACK);

        int centerX = paddingLeft + getWidth() / 2;
        int centerY = paddingTop + getHeight() / 2;

        canvas.drawCircle(centerX, centerY, mRadius, mPaint);
        //绘制内圈end

        //绘制内部圆形图片 begin

        float totalRadius = mRadius + mBoundWidth + mProgressWidth / 2;
        if (mDrawable != null && mBitmap == null) {
            mImage = ((BitmapDrawable) mDrawable).getBitmap();
            mBitmap = Bitmap.createBitmap((int) (2 * totalRadius), (int) (2 * totalRadius), Bitmap.Config.ARGB_8888);
            Canvas bitMapCanvas = new Canvas(mBitmap);
            Paint bitMapPaint = new Paint();
            bitMapPaint.setAntiAlias(true);

            bitMapCanvas.drawCircle(totalRadius, totalRadius, mRadius, bitMapPaint);

            bitMapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            bitMapCanvas.drawBitmap(mImage, null, new RectF(0, 0, 2 * totalRadius, 2 * totalRadius), bitMapPaint);

        }
        //绘制内部圆形图片 end


        //旋转内部圆形图片 begin
        RectF rect = new RectF(centerX - totalRadius, centerY - totalRadius, centerX + totalRadius, centerY + totalRadius);
        canvas.save();
        if (mIsRotate) {
            canvas.rotate(mRotateDegree, centerX, centerY);
        }
        canvas.drawBitmap(mBitmap, null, rect, mPaint);

        canvas.restore();
        //旋转内部圆形图片 end

        // 绘制进度条 begin
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //
        RectF oval = new RectF(centerX - totalRadius, centerY - totalRadius, centerX + totalRadius, centerY + totalRadius);
        mPaint.setColor(mProgressBackColor);

        //绘制进度条背景
        canvas.drawArc(oval, mArcStart, mArcEnd, false, mPaint);

        //绘制进度条
        mPaint.setColor(mProgressColor);
        canvas.drawArc(oval, mArcStart, mProgress, false, mPaint);
        //绘制进度条 end

    }


    public void start(long time) {
        mBitmap = null;

        time *= 60000;
        final float step = (float) 360 / (time / 30);
        CountDownTimer mTimer = new CountDownTimer(time, 30) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgress -= step;
                mRotateDegree -= mRotateStep;
                invalidate();
            }

            @Override
            public void onFinish() {
                end(step);
                this.cancel();
            }

        };
        mTimer.start();
    }

    private void end(float step) {
        mProgress -= step;
        invalidate();
        mProgress = 0;
        invalidate();
    }

}
