package com.peipao8.vehiclelock.CustomerView.progressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.peipao8.vehiclelock.R;



/**
 * 横向进度条,特点和使用方法
 * <attr name="unreached_color" format="color" /> //未达到进度颜色
 <attr name="reached_color" format="color" />   //已进行进度颜色
 <attr name="reached_bar_height" format="dimension" /> //已完成进度高度
 <attr name="unreached_bar_height" format="dimension" />   //未完成进度高度
 <attr name="text_size" format="dimension" />          //字体大小
 <attr name="text_color" format="color" />             //字体颜色
 <attr name="text_offset" format="dimension" />        //文字和进度末尾的间隔
 <attr name="text_visibility" format="enum">           //是否显示
 */
public class HorizontalProgressBarWithNumber extends ProgressBar {
    
    private static final int DEFAULT_TEXT_SIZE                     = 10;             //默认字体大小
    private static final int DEFAULT_TEXT_COLOR                    = 0XFFFC00D1;     //默认字体颜色,紫红色
    private static final int DEFAULT_COLOR_UNREACHED_COLOR         = 0xFFD3D6DA;     //进度条未走完的灰色泛点蓝色灰色为主
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR   = 2;              //已走完进度的高度
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;              //未走完进度的高度
    private static final int DEFAULT_SIZE_TEXT_OFFSET              = 10;             //文字偏移量
    private static final int VISIBLE = 0;                                          //是否显示
    
    /** 画笔 */
    protected Paint mPaint     = new Paint ();
    /** 默认字体颜色,紫红色 */
    protected int   mTextColor = DEFAULT_TEXT_COLOR;
    /** 已走完进度颜色,紫红色 */
    protected int mReachedBarColor            = DEFAULT_TEXT_COLOR;
    /** 进度条未走完的灰色泛点蓝色灰色为主 */
    protected int mUnReachedBarColor          = DEFAULT_COLOR_UNREACHED_COLOR;
    /** 默认字体大小 */
    protected int   mTextSize  = sp2px ( DEFAULT_TEXT_SIZE );
    /** 已走完进度的高度 */
    protected int mReachedProgressBarHeight = dp2px ( DEFAULT_HEIGHT_REACHED_PROGRESS_BAR );
    /** 未走完进度的高度 */
    protected int mUnReachedProgressBarHeight = dp2px ( DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR );
    /** 文字偏移量 */
    protected int mTextOffset = dp2px ( DEFAULT_SIZE_TEXT_OFFSET );
    /** 本类进度条的真实宽度(减去padding得到的) */
    protected int mRealWidth;
    /** 是否显示文字 */
    protected boolean mIfDrawText = true;
    
    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs) {
        this ( context, attrs, 0 );
    }
    
    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs, int defStyle) {
        super ( context, attrs, defStyle );
        obtainStyledAttributes ( attrs );
        mPaint.setTextSize ( mTextSize );    //设置画笔大小
        mPaint.setColor ( mTextColor );      //设置画笔颜色
    }
    
    /** 获得xml配置的自定义属性 */
    private void obtainStyledAttributes(AttributeSet attrs) {
        // 获得自定义属性打包的集合数据
        final TypedArray attributes = getContext ().obtainStyledAttributes ( attrs, R.styleable
            .HorizontalProgressBarWithNumber );
        //取出字体颜色.代替默认的紫红色;
        mTextColor = attributes.getColor ( R.styleable.HorizontalProgressBarWithNumber_text_color,DEFAULT_TEXT_COLOR );
        //取出完成进度颜色.代替默认的紫红色(这里是如果未配置完成进度颜色但是配置了字体颜色则进度颜色就取字体颜色);
        mReachedBarColor = attributes.getColor ( R.styleable.HorizontalProgressBarWithNumber_reached_color, mTextColor );
        //取出未完成进度颜色.代替默认的灰色带点蓝;
        mUnReachedBarColor = attributes.getColor ( R.styleable .HorizontalProgressBarWithNumber_unreached_color, DEFAULT_COLOR_UNREACHED_COLOR );
        //取出字体大小.代替默认的10sp;
        mTextSize = (int) attributes.getDimension ( R.styleable.HorizontalProgressBarWithNumber_text_size,mTextSize );
        //默认2dp;
        mReachedProgressBarHeight = (int) attributes.getDimension ( R.styleable .HorizontalProgressBarWithNumber_reached_bar_height, mReachedProgressBarHeight );
        //默认2dp;
        mUnReachedProgressBarHeight = (int) attributes.getDimension ( R.styleable .HorizontalProgressBarWithNumber_unreached_bar_height, mUnReachedProgressBarHeight );
        //默认10dp;
        mTextOffset = (int) attributes.getDimension ( R.styleable .HorizontalProgressBarWithNumber_text_offset, mTextOffset );
        //是否显示进度字体
        int textVisible = attributes.getInt ( R.styleable.HorizontalProgressBarWithNumber_text_visibility,VISIBLE );
        if (textVisible != VISIBLE) {
            mIfDrawText = false;
        }
        attributes.recycle ();
    }
    
    /** 测量大小 */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = MeasureSpec.getSize ( widthMeasureSpec );        //得到测量的宽度
        int height = measureHeight ( heightMeasureSpec );             //测量进度条view高度
        setMeasuredDimension ( width, height );                       //设置进度条的最终高度
        
        mRealWidth = getMeasuredWidth () - getPaddingRight () - getPaddingLeft (); //进度条的真正进度内容显示区域的真正宽度
    }
      /**
       * MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
       * MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为 WRAP_CONTENT 时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
       * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
       */
    private int measureHeight(int measureSpec) {
        int result   = 0;
        int specMode = MeasureSpec.getMode ( measureSpec );
        int specSize = MeasureSpec.getSize ( measureSpec );
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            float textHeight = ( mPaint.descent () - mPaint.ascent () );     //取出文字由基准线的最大上高度减去基准线到下面的最大下高度
            //上padding加下padding加自定义进度条高度的大值,
            result = (int) ( getPaddingTop () + getPaddingBottom () + Math.max ( Math.max ( mReachedProgressBarHeight, mUnReachedProgressBarHeight ), Math.abs ( textHeight ) ) );
            if (specMode == MeasureSpec.AT_MOST) {
                //如果是 WRAP_CONTENT ,则父亲给的是他所能给的最大值,但是实际上我们说不定很小,所以取小的
                result = Math.min ( result, specSize );
            }
        }
        return result;
    }
    
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save ();
        canvas.translate ( getPaddingLeft (), getHeight () / 2 );  //将画布的坐标原点向x方向移动paddingleft,y原点放在高度的中间
        
        boolean noNeedBg     = false;                              //是否不需要绘制未完成,默认是false,那就是需要
        float   radio        = getProgress () * 1.0f / getMax ();  //当前进度除以进度范围上线
        float   progressPosX = (int) ( mRealWidth * radio );       //得到进度条宽度的按照进度的长度
        String  text         = getProgress () + "%";               //进度值
        // mPaint.getTextBounds(text, 0, text.length(), mTextBound);
        
        float textWidth  = mPaint.measureText ( text );             //得到文字的宽度
        float textHeight = ( mPaint.descent () + mPaint.ascent () ) / 2;    //文字上高度加下高度除以2
        
        if (progressPosX + textWidth > mRealWidth) {                //如果已完成进度加上字体宽度大于进度条长度,也就是超出了
            progressPosX = mRealWidth - textWidth;                  //超出则进度条已完成长度不在增加,留出显示文字的空间
            noNeedBg = true;                                        //不需要绘制未完成进度
        }
        
        // draw reached bar
        float endX = progressPosX - mTextOffset / 2;                //上面得到的进度条已完成长度减去文字偏移量除以2得到结束值
        if (endX > 0) {                                             //如果进度条已完成长度超出偏移量除以2
            mPaint.setColor ( mReachedBarColor );                   //画笔颜色和高度设置
            mPaint.setStrokeWidth ( mReachedProgressBarHeight );    //这个高度是由原点上下对半高度绘制的
            canvas.drawLine ( 0, 0, endX, 0, mPaint );              //绘制出来的已完成进度船度比起真实已完成长度缩减了偏移量除以2
        }
        // draw progress bar
        // measure text bound
        if (mIfDrawText) {                                           //显示文字就是绘制文字
            mPaint.setColor ( mTextColor );
            canvas.drawText ( text, progressPosX, -textHeight, mPaint );  //文字显示在真实进度之后
        }
        
        // draw unreached bar
        if (!noNeedBg) {                                             //需要绘制未完成,刚开始是进来的
            float start = progressPosX + mTextOffset / 2 + textWidth;
            mPaint.setColor ( mUnReachedBarColor );
            mPaint.setStrokeWidth ( mUnReachedProgressBarHeight );
            canvas.drawLine ( start, 0, mRealWidth, 0, mPaint );
        }
        
        canvas.restore ();
    }
    
    /** dp 2 px * @param dpVal */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension ( TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources ().getDisplayMetrics () );
    }
    
    /** sp 2 px * @param spVal */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension ( TypedValue.COMPLEX_UNIT_SP, spVal, getResources ().getDisplayMetrics () );
    }
}
