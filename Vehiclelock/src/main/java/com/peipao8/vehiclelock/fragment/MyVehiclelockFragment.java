package com.peipao8.vehiclelock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peipao8.vehiclelock.R;

import java.util.ArrayList;

public class MyVehiclelockFragment extends BaseFragment /*implements OnClickListener */{

	private static final int RESULT_OK = -1;
	private View view;
	private LinearLayout ovalLayout;
	private LinearLayout ll_indent;
	private LinearLayout ll_subscribe_server;
	/** 请求首页Munu数据 */
	int requestShoppCar = 1000;
	int homeMenuCode = 15;

	/** 假数据 */
	// private ArrayList<HomeMenuBean> list = new ArrayList<HomeMenuBean>();
	// private String[] names = new String[] { "设备安装", "设备保养", "卡纸维修", "定影器错误",
	// "印品浅/脏", "印品全黑", "印品全白", "印品灰底", "印品缺色",
	// "印品重影/花色", "不识别墨粉盒", "漏墨", "不能连电脑打印", "电源指示灯不亮", "报废墨满错误", "未知错误" };
	// private int[] draws = new int[] { R.drawable.anzhuang,
	// R.drawable.baoyang, R.drawable.kazhi,
	// R.drawable.dingyingcuowu, R.drawable.huidi, R.drawable.quanhei,
	// R.drawable.quanbai, R.drawable.huidi,
	// R.drawable.quese, R.drawable.huase, R.drawable.wufashibie,
	// R.drawable.loumo, R.drawable.diannao,
	// R.drawable.zhishi, R.drawable.baofei, R.drawable.weizhi };
	private TextView search;
	private TextView saoZXing;
	private LinearLayout shopping;
	private LinearLayout productCecord;// 产品档案
	private LinearLayout gpsAdd;
	private TextView tvCallPhone;
	private TextView gps_address;
	private ArrayList<String> productSelectList;
	private String userID;
	int searchResqest = 510; // 搜索页面的打开
	private TextView tv_shoppcar;
	/**
	 * 定位
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate( R.layout.fragment_my_vehiclelock, container, false);
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}
//
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//		super.onViewCreated(view, savedInstanceState);
//		initListData();
//		initView();
//		initListener();
//		setHeight();
//		getShoppCarData();
//	}
//
//
//	private void initListData() {
//		getHomeMenuData();
//
//		productSelectList = new ArrayList<String>();
//		productSelectList.add("产品");
//		productSelectList.add("激光打印机");
//		productSelectList.add("激光一体机");
//		productSelectList.add("数码复合机");
//		productSelectList.add("喷墨一体机");
//	}
//
//	/** * 访问首页数据 */
//	private void getHomeMenuData() {
//		Map<String, String> map = new HashMap<String, String>();
//		if (!TextUtils.isEmpty(userID)) {
//			map.put("userId", userID + "");
//		} else {
//			map.put("userId", "");
//		}
//
//		Params params = new Params(HomeFragment.this, XDConstantValue.ROOT_HOST + XDConstantValue.API_GET_HOME_DATA,
//				map, false, true, XDConstantValue.POST_METHOD, homeMenuCode);
//		NetWorkTask netWorkTask = new NetWorkTask();
//		netWorkTask.executeFragmentProxy(params);
//	}
//
//	private void initView() {
//		search = (TextView) view.findViewById(R.id.home_tv_search);// 搜索按钮
//		tvCallPhone = (TextView) view.findViewById(R.id.tv_call_phone);
//		saoZXing = (TextView) view.findViewById(R.id.home_tv_sao_zxing);// 扫一扫
//		ovalLayout = (LinearLayout) view.findViewById(R.id.ovalLayout);// 获取圆点容器
//		ll_indent = (LinearLayout) view.findViewById(R.id.ll_personal_indent);
//		ll_subscribe_server = (LinearLayout) view.findViewById(R.id.ll_personal_subscribe_server);
//		shopping = (LinearLayout) view.findViewById(R.id.home_ll_shopping);// 购物车
//		productCecord = (LinearLayout) view.findViewById(R.id.home_ll_product_record);// 产品档案
//		gpsAdd = (LinearLayout) view.findViewById(R.id.home_gps);// 定位
//		gps_address = (TextView) view.findViewById(R.id.gps_address_tv);
//		tv_shoppcar = (TextView) view.findViewById(R.id.tv_shoppcar);
//	}
//
//	/** 访问购物车网络 */
//	private void getShoppCarData() {
//		if (TextUtils.isEmpty(userID)) {
//			return;
//		}
//
//	}
//
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		super.onHiddenChanged(hidden);
//		if (TextUtils.isEmpty(userID)) {
//		}
//		if(!hidden){
//			getShoppCarData();
//		}
//	}
//
//	/**
//	 * 初始化监听
//	 */
//	private void initListener() {
//		search.setOnClickListener(this);
//		saoZXing.setOnClickListener(this);
//		shopping.setOnClickListener(this);
//		productCecord.setOnClickListener(this);
//		gpsAdd.setOnClickListener(this);
//		tvCallPhone.setOnClickListener(this);
//		ll_indent.setOnClickListener(this);
//		ll_subscribe_server.setOnClickListener(this);
//		view.findViewById(R.id.lease_four_ll).setOnClickListener(this);// 激光打印机
//		view.findViewById(R.id.lease_three_ll).setOnClickListener(this);//// 激光一体机
//		view.findViewById(R.id.lease_two_ll).setOnClickListener(this);// 数码复合机
//		view.findViewById(R.id.lease_one_ll).setOnClickListener(this);// 喷墨一体机
//	}
//
//	/** mainActivity调用了 */
//	public void doclick(View v) {
//
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//
//		// 点击个人订单
//		case R.id.ll_personal_indent:
//			if (!isLogin()) {
//				startActivity(new Intent(activity, LoginActivity.class));
//			} else {
//				activity.startActivity(new Intent(activity, PersonalIndentActivity.class));
//			}
//			break;
//
//		case R.id.tv_call_phone:
//			// 传入服务， parse（）解析号码
//			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvCallPhone.getText()));
//			// 通知activtity处理传入的call服务
//			getActivity().startActivity(intent);
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	/**
//	 * 校验是否登录
//	 */
//	private boolean isLogin() {
//		return XDCacheJsonManager.getValue(context, XDConstantValue.USERINFO, XDConstantValue.USER_ID_KEY, 0) != null
//				&& XDCacheJsonManager.getValue(context, XDConstantValue.USERINFO, XDConstantValue.USER_ID_KEY, 0)
//						.length() > 0;
//	}
//
//	private void setHeight() {
//
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		int sysVersion = Integer.parseInt(VERSION.SDK);
//		if (sysVersion >= 19) {
//			// view加载完成时回调
//			saoZXing.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//				@Override
//				public void onGlobalLayout() {
//					initHeightBarHead();
//					saoZXing.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//				}
//			});
//		}
//	}
//
//	public void initHeightBarHead() {
//		LinearLayout lltitle = (LinearLayout) view.findViewById(R.id.ll_title);
//		lltitle.setPadding(0, getStateBarHeight() + UiUtils.dp2px(14), 0, UiUtils.dp2px(14));
//	}
//
//	// 获取状态栏高度.
//	public int getStateBarHeight() {
//
//		// 状态栏高度方法一.
//		int result = 0;
//		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//		if (resourceId > 0) {
//			result = getResources().getDimensionPixelSize(resourceId);
//		}
//
//		// 状态栏高度方法二
//		int statusHeight = 0;
//		try {
//			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//			Object object = clazz.newInstance();
//			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
//			statusHeight = context.getResources().getDimensionPixelSize(height);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (result == 0 && statusHeight == 0) {// 都为0,就返回0
//			return 0;
//		} else if (result == statusHeight) {// 两个都不为0 ,并且相等,则都算出结果了.返回result
//			return result;
//		} else {// 有一个不为0.,返回大的.
//			return result > statusHeight ? result : statusHeight;
//		}
//	}
//
//	/** 访问主页服务列表网络 */
//	private void initMenuAdapter(ArrayList<HomeMenuBean> serverTypeLst) {
//
//	/**
//	 * 当主页的listview或者grivdview被点击条目时调用,跳转到其他fragment.并传递对象给维保页面
//	 */
//	private void setClickItem(int position, int index) {
//		getActivity().getSupportFragmentManager().beginTransaction().hide( MainActivity.fragments[0])
//				.hide(MainActivity.fragments[2]).hide(MainActivity.fragments[3]).hide(MainActivity.fragments[4])
//				.hide(MainActivity.fragments[1]).show(MainActivity.fragments[index]).commit();// 全部fragment隐藏,然后显示指定的fragment
//		MainActivity mainActivity = (MainActivity) getActivity();// 得到依附的fragment
//		TextView[] mainTabs = mainActivity.mainTabs;// 获得底部点击按钮
//
//		for (int i = 0; i < mainTabs.length; i++) {
//			if (i == index) {
//				mainTabs[i].setSelected(true);// 指定底部按钮被选中
//				mainActivity.currentTabIndex = index; // 更新当前显示的fragment的标记
//			} else {
//				mainTabs[i].setSelected(false);
//			}
//
//		}
//		if (index == 1) {// 当要跳转的是维保服务.就执行设置维保服务的数据
//			((WeiBaoFuWuFragment) (MainActivity.fragments[1])).xiala_server_tv
//					.setText(serverTypeLst.get(position).getName());
//		} else if (index == 2) {// 当要跳转的是设备租赁.就执行设置设备租赁的产品的选择信息
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).tvProduct.setText(productSelectList.get(position));
//			// 同时设置跳转后访问网络时候的产品选择参数
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).typeSelect = position + "";
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).pageNo = 1;
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).colorSelect = "";
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).areaSizeSelect = "";
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).joinTypeSelect = "";
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).speedSelect = "";
//			// 访问网络.
//			((SheBeiZuLinFragment) (MainActivity.fragments[2])).getListDate();
//		}
//
//	}
//
//	private void getBannerData(ArrayList<HomeLunBoBean> changePictureLst) {
//		final ArrayList<BannerBean> imgList = new ArrayList<BannerBean>();
//		for (HomeLunBoBean homeLunBoBean : changePictureLst) {
//			imgList.add(new BannerBean(homeLunBoBean.getName(), homeLunBoBean.getImg(), homeLunBoBean.getContent()));
//		}
//		// 第二和第三参数 2选1 ,参数2为 图片网络路径数组 ,参数3为图片id的数组,本地测试用 ,2个参数都有优先采用 参数2
//		gallery.xdstart(getActivity(), imgList, null, 3000, ovalLayout, R.drawable.xuanzhong, R.drawable.dian);
//		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {
//
//			@Override
//			public void onItemClick(int curIndex) {
//				XDLogUtil.i(TAG, "当前点击页面id = " + curIndex + "当前页面对应的链接 ---" + imgList.get(curIndex).getId());
//			}
//		});
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		// 处理扫描结果（在界面上显示）
//		if (resultCode == RESULT_OK) {
//			Bundle bundle = data.getExtras();
//			String scanResult = bundle.getString("result");
//			if (!TextUtils.isEmpty(scanResult)) {
//				UiUtils.toast(scanResult);
//				if (scanResult.contains("http://") || scanResult.contains("https://")) {
//					Intent intent = new Intent();
//					intent.setAction("android.intent.action.VIEW");
//					Uri content_url = Uri.parse(scanResult);
//					intent.setData(content_url);
//					startActivity(intent);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		getShoppCarData();
//	}
//
//	@Override
//	public void onGetResult(Object result, int iError, int requestCode) {
//		super.onGetResult(result, iError, requestCode);
//		if (requestCode == homeMenuCode) {
//			if (result != null) {
//				XDLogUtil.i(TAG, "homeMenuCode------------------->" + result);
//				try {
//					String res = (String) result;
//					Gson gson = new Gson();
//					HomeFragmentResponse fromjson = gson.fromJson(res, HomeFragmentResponse.class);
//					if (fromjson.isSuccess()) {
//						ArrayList<HomeLunBoBean> changePictureLst = fromjson.getData().getChangePictureLst();
//						serverTypeLst = fromjson.getData().getServerTypeLst();
//						// new 适配器 然后刷新.
//						initMenuAdapter(serverTypeLst);
//						// 初始化 轮播条
//						getBannerData(changePictureLst);
//					} else {
//						Toast.makeText(context, fromjson.getMsg(), Toast.LENGTH_SHORT).show();
//					}
//				} catch (Exception e) {
//					System.err.println(TAG + "崩溃日志:" + e);
//					XDLogUtil.i(TAG, "主页 内容获取后解析失败");
//				}
//
//			}
//
//		} else if (requestCode == requestShoppCar) {
//			if (result != null) {
//				XDLogUtil.i(TAG, "----------购物车--------->" + result);
//				String indentJson = (String) result;
//				ShoppingItemBeanSun fromjson = GsonTools.getObj(indentJson, ShoppingItemBeanSun.class);
//				if ("200".equals(fromjson.getCode())) {
//					int totalNumber = 0;
//					ArrayList<shopBean> list = fromjson.getData().getList();
//					for (shopBean shopbean : list) {
//						ArrayList<indentBean> shopstorage = shopbean.getShopstorage();
//						for (indentBean indentBean : shopstorage) {
//							totalNumber += 1;
//						}
//					}
//					if (totalNumber > 10) {
//						tv_shoppcar.setText("购物车(" + totalNumber + ") ");
//					} else {
//						tv_shoppcar.setText("购物车(" + totalNumber + ")");
//					}
//				} else {
//					Toast.makeText(context, fromjson.getMsg(), Toast.LENGTH_SHORT).show();
//				}
//
//			}
//		}
//	}
//	@Override
//	public void onDestroyView() {
//		// TODO Auto-generated method stub
//		super.onDestroyView();
//		// 退出时销毁定位
//		if(null!=mLocationClient) mLocationClient.stop();
//
//	}
}
