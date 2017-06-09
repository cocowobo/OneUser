package com.peipao8.vehiclelock.LYangCode.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.peipao8.vehiclelock.App;

/**
 * 2016/9/28. 总结整理的代码中设置所谓的沉浸式状态栏
 */
public class ImmersiveUtils {


	private static final int INVALID_VAL   = -1;  //默认-1
	private static final int COLOR_DEFAULT = Color.parseColor ( "#20000000" );

	//-----------------------------------沉浸式状态栏方法(一) 设置4.4和5.1都为半透明式状态栏,-----------------------------------
	/**设置4.4和5.1都为半透明式状态栏
	 * 用法.在titleview上配置
	 * android:fitsSystemWindows="true"
	 android:clipToPadding="true"
	 * 上面的属性,开启适配状态栏,让最上端view的padding增加状态栏高度,  (只有当程序布局延伸到状态栏才会触发生效),
	 * 调用此方法传入activity,先开启透明状态栏
	 * 5.1清除透明状态栏特性后,从activity中寻找窗体对象设置状态栏颜色为半透明,
	 * 4.4 找到父窗体在被覆盖的状态栏下的位置放一个制定颜色的view,同时将状态栏设置为半透明色以便看到下面的view
	 *
	 * 存在的bug.如果需要将导航栏和状态栏的透明状态栏都去掉,那么就会让下面的只去掉一个透明状态栏而恰好我们需要的上移
	 * 效果没有,这个 所以不推荐用*/
	public void createTranslucenceStateBar(Activity activity) {
		Window window = activity.getWindow ();
		//必须开始透明状态栏,才会让顶部view,嵌入状态栏下
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );  //设置透明状态栏 ,让顶部view上移
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
		// 大于5.1  ,透明是半透明的,但是部分定制rom厂商,在系统中去掉了半透明,变成了全透明,
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//大于5.1,不论是原生还是定制,都会是半透明,利用的是变色状态栏,
			//清除透明状态栏,但是上移还在,为什么要清除,不清除,变色状态栏设置无效
			//注意,如果开启了状态栏和导航栏的透明特性,但是清空没有两个都清空,则上移效果不消失
			window.clearFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
			activity.getWindow ().setStatusBarColor ( COLOR_DEFAULT );   //恢复厂商去掉的半透明,
			return;
		}
		// 4.4到5.1系统透明是渐变透明的,但是部分定制rom厂商,在系统中去掉了渐变透明,变成了全透明
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES
			.LOLLIPOP) {
			// 原生4.4上.是渐变透明的, 再放个半透明,那就是渐变加半透明
			// 定制4.4.是全透明的,放个半透明,那就是半透明,
			ViewGroup contentView   = (ViewGroup) activity.findViewById ( android.R.id.content );   //找到整个窗口
			View      statusBarView = new View ( activity );      //新建布局
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT, App.mStatusBarHeight );     //布局的宽高参数为设置为状态栏宽高
			statusBarView.setBackgroundColor ( COLOR_DEFAULT );       // 布局背景色
			//添加布局.到状态栏位置,因为整个窗口是个fragment,则压在了原来的布局上
			contentView.addView ( statusBarView, lp );
		}
	}
	//-----------------------------------沉浸式状态栏方法(二) 设置4.4和5.1都为半透明式状态栏,-----------------------------------
	/**设置4.4和5.1都为半透明式状态栏
	 * 相比一无需设置fitwindowsstatebar  ,只需要传入最顶部view,重新设置padding值,就和fitwindowstatebar一样效果
	 * 调用此方法传入activity,先开启透明状态栏
	 * 5.1清除透明状态栏特性后,从activity中寻找窗体对象设置状态栏颜色为半透明,
	 * 4.4 找到父窗体在被覆盖的状态栏下的位置放一个制定颜色的view,同时将状态栏设置为半透明色以便看到下面的view*/
	public  void createTranslucenceStateBarTwo(Activity activity,View view) {
		setImmerseLayout (activity,view);  //调用方法(三)
		Window window = activity.getWindow ();
		//必须开始透明状态栏,才会让顶部view,嵌入状态栏下
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );  //设置透明状态栏 ,让顶部view上移
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
		// 大于5.1  ,透明是半透明的,但是部分定制rom厂商,在系统中去掉了半透明,变成了全透明,
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//大于5.1,不论是原生还是定制,都会是半透明,利用的是变色状态栏,
			//清除透明状态栏,但是上移还在,为什么要清除,不清楚,变色状态栏设置无效
			window.clearFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
			activity.getWindow ().setStatusBarColor ( COLOR_DEFAULT );   //恢复厂商去掉的半透明,
			return;
		}
		// 4.4到5.1系统透明是渐变透明的,但是部分定制rom厂商,在系统中去掉了渐变透明,变成了全透明
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES
			.LOLLIPOP) {
			// 原生4.4上.是渐变透明的, 再放个半透明,那就是渐变加半透明
			// 定制4.4.是全透明的,放个半透明,那就是半透明,
			ViewGroup contentView = (ViewGroup) activity.findViewById ( android.R.id.content );   //找到整个窗口
			View statusBarView = new View ( activity );      //新建布局
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT,
				App.mStatusBarHeight );     //布局的宽高参数为设置为状态栏宽高
			statusBarView.setBackgroundColor ( COLOR_DEFAULT );       // 布局背景色
			//添加布局.到状态栏位置,因为整个窗口是个fragment,则压在了原来的布局上
			contentView.addView ( statusBarView, lp );
		}
	}
	//-----------------------------------沉浸式状态栏方法(三) 设置4.4和5.1都为全屏式透明式状态栏,不强调是否全透半透-------------------
	/**设置4.4和5.1都为   全屏式透明式状态栏
	 * 调用此方法传入activity,先开启透明状态栏
	 * 然后清除可能多写的fitsystemWindow属性.这里我们不需要适配状态栏高度
	 * 获得,我们的设置的布局的布局参数,其类型为(FrameLayout.LayoutParams),判断如果其view高度为状态栏高度,则
	 * 就是已经调用过某些方法,添加过设置高度为状态栏高度的自定义view,这里采用删除已添加的和主布局文件同级的view,
	 * 并再此获得childview,这下就是,我们的主布局文件view了.,获得布局参数,如果marginTop大于等于状态栏高度.
	 * 为避免又是因为某些方法.无端端多设置了maginTop.则用当前marginTop高度减去状态栏高度,一直
	 * 捡到top值小于状态栏高度,在设置进去.为什么这么搞.利用一个原则,整个主布局文件对象的marginTop 本就不应该有,
	 *
	 * 注意:::此方法是为了兼容其他的库或者代码方法在4.4设置了假view填充状态栏后,的修复方法..
	 */
	public  void createTranslucenceStateBarThree(Activity activity) {
		Window window = activity.getWindow();
		ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT); // 获得contentview
		//首先使 ChildView 不预留空间
		View mChildView = mContentView.getChildAt(0);  //要么是我们定义的布局文件,要么是我们在代码中又添加到头的view
		if (mChildView == null) {
			return;
		}
		ViewCompat.setFitsSystemWindows(mChildView, false);    //去掉取来的childview的fitsystemWindows设置
		//需要设置这个 flag 才能设置状态栏
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);     //开启透明状态栏特性
		// 获得布局参数,类型为FrameLayout.LayoutParams
		ViewGroup.LayoutParams layoutParams = mChildView.getLayoutParams ();
		//避免多次调用该方法时,多次移除了 View
		if ( layoutParams != null && layoutParams.height == App.mStatusBarHeight) {
			//移除假的 View.
			mContentView.removeView(mChildView);
			mChildView = mContentView.getChildAt(0);
		}
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
		//清除 ChildView 的 marginTop 属性
		while (lp != null && lp.topMargin >= App.mStatusBarHeight) {
			lp.topMargin -= App.mStatusBarHeight;    //我觉得应该直接改为0.哎呀.
		}
		mChildView.setLayoutParams(lp);
	}
	/**设置4.4和5.1都为   着色式透明式状态栏
	 * 调用此方法传入activity,先开启透明状态栏
	 * 然后清除可能多写的fitsystemWindow属性.这里我们不需要适配状态栏高度
	 * 获得,我们的设置的布局的布局参数,其类型为(FrameLayout.LayoutParams),判断如果其view高度为状态栏高度,则
	 * 就是已经调用过某些方法,添加过设置高度为状态栏高度的自定义view,这里采用删除已添加的和主布局文件同级的view,
	 * 并再此获得childview,这下就是,我们的主布局文件view了.,获得布局参数,如果marginTop大于等于状态栏高度.
	 * 为避免又是因为某些方法.无端端多设置了maginTop.则用当前marginTop高度减去状态栏高度,一直
	 * 捡到top值小于状态栏高度,在设置进去.为什么这么搞.利用一个原则,整个主布局文件对象的marginTop 本就不应该有,
	 * 注意:::此方法是为了兼容其他的库或者代码方法在4.4设置了假view填充状态栏后,的修复方法..
	 */
	public  void createTranslucenceStateBarFour(Activity activity,int statusColor) {
		Window window = activity.getWindow();
		ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   //开启透明状态栏
		View mChildView = mContentView.getChildAt(0);    //获得第一个子view
		if (mChildView != null) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
			//如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
			if (lp != null && lp.topMargin < App.mStatusBarHeight && lp.height != App.mStatusBarHeight) {
				//不为空,topMargin值,小于状态栏高度,就是未设置假view的top或者主布局文件的top.
				//   大于等于状态栏高度,就是设置了假view的top或者主布局文件的top,在判断这个子view的高度
				//   等于就是假view.不等于就是主布局文件
				//      综合起来就是 存在的 且未设置 marginTop的主布局文件
				ViewCompat.setFitsSystemWindows(mChildView, false);//主布局文件不预留系统高度空间
				lp.topMargin += App.mStatusBarHeight;      //设置MarginTop值.
				mChildView.setLayoutParams(lp);
				View fakeView = new View(activity);     //new  假view
				ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.mStatusBarHeight);
				fakeView.setBackgroundColor(statusColor);  //设置假view颜色
				//向 ContentView 中添加假 View
				mContentView.addView(fakeView, 0, vlp);    //添加假view.
				return;
			}
		}else{
			return; //未设置view,未加载布局文件,直接返回
		}
		//走到这一步的.就是不为空并且,是假view.的子view.
		if (  mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == App.mStatusBarHeight) {
			//假view 高度等于状态栏高度  ,设置背景色 ,预防此假view是其他方法设置的  ,后直接返回
			mChildView.setBackgroundColor(statusColor);       	//避免重复调用时多次添加 View
			return;
		}
	}

	/**设置4.4和5.1都为   全屏式透明式状态栏
	 * 调用此方法传入activity,先开启透明状态栏
	 *
	 * 注意:::找到了主布局对象的父view去了,是linerlayout
	 */
	public  void createTranslucenceStateBarFive(Activity activity) {
		Window window = activity.getWindow ();
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
		/**主布局文件framLayout*/
		ViewGroup mContentView   = (ViewGroup) activity.findViewById ( Window.ID_ANDROID_CONTENT );
		/**fragmlayout外层的系统级的linerlayout*/
		ViewGroup mContentParent = (ViewGroup) mContentView.getParent ();

		//最外层linerlayout ,取出第一个,要么是framlayout要么是我们手动添加到此级别的假view
		View statusBarView = mContentParent.getChildAt ( 0 );
		if (statusBarView != null && statusBarView.getLayoutParams () != null && statusBarView.getLayoutParams ().height == App.mStatusBarHeight) {
			//移除假的 View
			mContentParent.removeView ( statusBarView );
		}
		//ContentView 不预留空间
		if (mContentParent.getChildAt ( 0 ) != null) {  //移除假view后或者就没有假view后,设置framlayout不适配高度
			ViewCompat.setFitsSystemWindows ( mContentParent.getChildAt ( 0 ), false );
		}
		//ChildView 不预留空间
		View mChildView = mContentView.getChildAt ( 0 ); //这是我们的布局文件的根布局
		if (mChildView != null) {
			ViewCompat.setFitsSystemWindows ( mChildView, false );  //不适配高度
		}
	}
	/**设置4.4和5.1都为   着色式透明式状态栏
	 * 调用此方法传入activity,先开启透明状态栏
	 *
	 * 注意:::找到了主布局对象的父view去了,是linerlayout,此着色模式(会有一条黑线,无法解决):我没看到
	 */
	public  void createTranslucenceStateBarSix(Activity activity,int statusColor) {
		Window window = activity.getWindow ();
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
		/**主布局文件framLayout*/
		ViewGroup mContentView   = (ViewGroup) activity.findViewById ( Window.ID_ANDROID_CONTENT );
		/**fragmlayout外层的系统级的linerlayout*/
		ViewGroup mContentParent = (ViewGroup) mContentView.getParent ();

		//最外层linerlayout ,取出第一个,要么是framlayout要么是我们手动添加到此级别的假view
		View statusBarView = mContentParent.getChildAt ( 0 );
		if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == App.mStatusBarHeight) {
			//避免重复调用时多次添加 假View                ,设置背景色
			statusBarView.setBackgroundColor(statusColor);
			return;
		}
		//创建一个假的 View, 并添加到 ContentParent
		statusBarView = new View(activity);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			App.mStatusBarHeight);    //布局参数
		statusBarView.setBackgroundColor(statusColor);  //设置背景色
		ViewCompat.setFitsSystemWindows(statusBarView, false);
		mContentParent.addView(statusBarView, 0, lp);   //添加假view
	}
	//-----------------------------------沉浸式状态栏方法(四) 手动设置嵌入状态栏的view的padding高度,-----------------------------------
	/**传入view设置上padding值为状态栏高度,其余方位padding不变
	 * 内部设置了透明状态栏特性,不需要再theme中再设置,设置也可以,
	 * 无需设置下面两个属性
	 * android:fitsSystemWindows="true"
	 android:clipToPadding="true"
	 * 因为一般是对titleview进行此方法,来设置上padding,来避免沉浸式后title上移,
	 * 而上面两个配置就是为了避免上移的,所以不需要
	 * 注意:一般使用此方法,应该已经开启了透明状态栏,让顶部view嵌入状态栏
	 */
	public void setImmerseLayout(Activity activity ,View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window =activity. getWindow ();
			//设置状态栏和,导航栏为透明状态栏(api19有的)
			window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
			window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
			int paddingLeft = view.getPaddingLeft ();// 得到传入view当前时刻的PaddingLeft值
			int paddingRight = view.getPaddingRight ();// 得到传入view当前时刻的PaddingRight值
			int paddingBottom = view.getPaddingBottom();// 得到传入view当前时刻的paddingBottom值
			view.setPadding ( paddingLeft, App.mStatusBarHeight, paddingRight, paddingBottom );
		}
	}

	//-----------------------------------沉浸式状态栏方法(五) 设置 5.1都为半透明式状态栏,和透明状态栏,或者自定义颜色-----------------------------------
	/**设置 5.1都为半透明式状态栏的第二种方式
	 * 用法.在titleview上配置
	 * android:fitsSystemWindows="true"
	 android:clipToPadding="true"
	 * 上面的属性,开启适配状态栏,让最上端view的padding增加状态栏高度(只有当程序布局延伸到状态栏才会触发生效),
	 * 调用此方法传入activity,来获得整个窗体,并设置系统属性 --应用程序布局全屏  ,这样不用开启透明状态栏特性
	 * 5.1从activity中寻找窗体对象设置状态栏颜色,color 为-1 则是白色背景,是0.则是全透明,1是不设颜色,系统默认色,
	 *  正常色值不用管
	 */
	public static void createStateBarColor(Activity activity,int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			View decorView = activity.getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ; //应用程序布局全屏
			decorView.setSystemUiVisibility(option);
			if (color == 1) {
				return;
			}
			activity.getWindow().setStatusBarColor(color); //再设置状态栏透明色
			return;
		}
	}
	//-----------------------------------沉浸式状态栏方法(六) 设置4.4和 5.1都为半透明式状态栏,和透明状态栏,或者自定义颜色-----------------------------------
	/**设置 5.1都为半透明式状态栏的第二种方式
	 * 用法.在titleview上配置
	 * android:fitsSystemWindows="true"
	 android:clipToPadding="true"
	 * 上面的属性,开启适配状态栏,让最上端view的padding增加状态栏高度(只有当程序布局延伸到状态栏才会触发生效),
	 * 调用此方法传入activity,来获得整个窗体,并设置系统属性 --应用程序布局全屏  ,这样不用开启透明状态栏特性
	 * 5.1从activity中寻找窗体对象设置状态栏颜色,color 为-1 则是白色背景,是0.则是全透明,1是不设颜色,系统默认色,
	 *  正常色值不用管
	 */
	public static void createTranslucenceState(Activity activity,int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			View decorView = activity.getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ; //应用程序布局全屏
			decorView.setSystemUiVisibility(option);
			if (color == 1) {
				return;
			}
			activity.getWindow().setStatusBarColor(color); //再设置状态栏透明色
			return;
		}
	}

	// 开启透明状态栏的第一种方法,
	//注意,如果开启了状态栏和导航栏的透明特性,但是清空没有两个都清空,则上移效果不消失
	private void startTransparencyStatusOne(Activity activity ) {
		Window window = activity.getWindow ();
		//必须开始透明状态栏,才会让顶部view,嵌入状态栏下
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );  //设置透明状态栏 ,让顶部view上移
		window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
	}
	// 关闭透明状态栏的第一种方法,
	private void closeTransparencyStatusOne(Activity activity ) {
		Window window = activity.getWindow ();
		window.clearFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
		window.clearFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
		//注意,如果开启了状态栏和导航栏的透明特性,但是清空没有两个都清空,则上移效果不消失
	}
	/** 开启透明状态栏的第二种方法,*/
	private void startTransparencyStatusTwo(Activity activity ) {
		//-----------------------------------方法(二)-----------------------------------
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		winParams.flags |= bits;       //01
		win.setAttributes(winParams);  //02   注意01和02顺序可颠倒,可能是相邻两句话执行太快吧
		//++++++++++++++++++++++++++++++++++关闭(二)++++++++++++++++++++++++++++++++++++
		winParams.flags &= ~bits;
		win.setAttributes(winParams);
	}
	/** 关闭透明状态栏的第二种方法,*/
	private void closeTransparencyStatusTwo(Activity activity ) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		winParams.flags &= ~bits;
		win.setAttributes(winParams);
	}
	/** 开启/关闭适配系统状态栏的方法,
	 * 使用注意事项:只给第一个设置有效,多次设置,以第一个为准,设置给谁,谁的上padding增加*/
	private void setFitWindowStatus(Activity activity ,Boolean bl) {
		ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
		View mChildView = mContentView.getChildAt(0);
		if (mChildView != null) {
			//注意不是设置 ContentView 的FitsSystemWindows, 而是设置ContentView的第一个子View.预留出系统 View 的空间.
			ViewCompat.setFitsSystemWindows(mChildView, bl);
		}
	}


	/** 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
	 * @return 返回状态栏高度的像素值。
	 */
	public static int getStatusBarHeight(Context context) {
		//状态栏高度方法一.
		int result     = 0;
		int resourceId = context.getResources ().getIdentifier ( "status_bar_height", "dimen", "android" );
		if (resourceId > 0) {
			result = context.getResources ().getDimensionPixelSize ( resourceId );
		}
		//状态栏高度方法二
		int statusHeight = 0;
		try {
			Class<?> clazz  = Class.forName ( "com.android.internal.R$dimen" );
			Object   object = clazz.newInstance ();
			int height = Integer.parseInt ( clazz.getField ( "status_bar_height" ).get ( object ).toString () );
			statusHeight = context.getResources ().getDimensionPixelSize ( height );
		} catch (Exception e) {
			e.printStackTrace ();
		}

		if (result == 0 && statusHeight == 0) {//都为0,就返回0
			return 0;
		} else if (result == statusHeight) {//两个都不为0 ,并且相等,则都算出结果了.返回result
			return result;
		} else {//有一个不为0.,返回大的.
			return result > statusHeight ? result : statusHeight;
		}
	}
}
