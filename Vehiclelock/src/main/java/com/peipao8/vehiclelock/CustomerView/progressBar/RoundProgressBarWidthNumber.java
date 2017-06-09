package com.peipao8.vehiclelock.CustomerView.progressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.peipao8.vehiclelock.R;

/**
 * 在横着进度的基础上绘制.
 * <attr name="radius" format="dimension" />      //半径
 */
public class RoundProgressBarWidthNumber extends HorizontalProgressBarWithNumber {
    /** 默认半径30dp */
    private int mRadius = dp2px ( 30 );
    private int mMaxPaintWidth;
    
    public RoundProgressBarWidthNumber(Context context) {
        this ( context, null );
    }
    
    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs) {
        super ( context, attrs );
        //已完成进度高度是未完成进度高度2.5倍.
        mReachedProgressBarHeight = (int) ( mUnReachedProgressBarHeight * 2.5f );
        TypedArray ta = context.obtainStyledAttributes ( attrs, R.styleable.RoundProgressBarWidthNumber );
        //得到半径
        mRadius = (int) ta.getDimension ( R.styleable.RoundProgressBarWidthNumber_radius, mRadius );
        ta.recycle ();
        
        mPaint.setStyle ( Style.STROKE );
        mPaint.setAntiAlias ( true );
        mPaint.setDither ( true );
        mPaint.setStrokeCap ( Cap.ROUND );
    }
    
    /**
     * 这里默认在布局中padding值要么不设置，要么全部设置
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        //重新测量,取最大值,肯定是已完成进度更大,以为构造中已完成变为未完成3倍
        mMaxPaintWidth = Math.max ( mReachedProgressBarHeight, mUnReachedProgressBarHeight ); //
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft () + getPaddingRight (); //直径加进度高度加padding
        int width     = resolveSize ( expect, widthMeasureSpec );                           //自动解析大小,根据模式
        int height    = resolveSize ( expect, heightMeasureSpec );
        int realWidth = Math.min ( width, height );
        
        mRadius = ( realWidth - getPaddingLeft () - getPaddingRight () - mMaxPaintWidth ) / 2;   //内部空白区域半径
        
        setMeasuredDimension ( realWidth, realWidth );
    }
    
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        
        String text       = getProgress () + "%";
        float  textWidth  = mPaint.measureText ( text );
        float  textHeight = ( mPaint.descent () + mPaint.ascent () ) / 2;
        
        canvas.save ();
        //整个view的正方形缩小一圈,形成留出左padding加上线宽,上边留出上padding加线宽
        canvas.translate ( getPaddingLeft () + mMaxPaintWidth / 2, getPaddingTop () + mMaxPaintWidth / 2 );
        mPaint.setStyle ( Style.STROKE );
        // draw unreaded bar
        mPaint.setColor ( mUnReachedBarColor );
        mPaint.setStrokeWidth ( mUnReachedProgressBarHeight );
        canvas.drawCircle ( mRadius, mRadius, mRadius, mPaint );
        // draw reached bar
        mPaint.setColor ( mReachedBarColor );
        mPaint.setStrokeWidth ( mReachedProgressBarHeight );
        float sweepAngle = getProgress () * 1.0f / getMax () * 360;
        canvas.drawArc ( new RectF ( 0, 0, mRadius * 2, mRadius * 2 ), 0, sweepAngle, false, mPaint );
        // draw text
        mPaint.setStyle ( Style.FILL );
        canvas.drawText ( text, mRadius - textWidth / 2, mRadius - textHeight, mPaint );
        canvas.restore ();
    }
}
