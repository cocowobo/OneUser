/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peipao8.vehiclelock.LYangCode.nohttp;

import android.support.v4.app.FragmentActivity;

import com.peipao8.vehiclelock.CustomerView.Dialog.BaiduLoadingDialog;
import com.peipao8.vehiclelock.LYangCode.CustomerView.dialog.WaitDialog;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.R;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    
    private FragmentActivity mActivity;
    /**
     * Dialog.
     */
    private WaitDialog       mWaitDialog;
    /**
     * Request.
     */
    private Request<?>       mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T>  callback;
    private BaiduLoadingDialog baiduLoadingDialog;
    
    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(FragmentActivity activity, Request<?> request, HttpListener<T> httpCallback, boolean
        canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        this.callback = httpCallback;
        if (activity != null && isLoading) {
            baiduLoadingDialog = new BaiduLoadingDialog ();
            baiduLoadingDialog.setCancelable ( canCancel );
        }
    }
    
    /**
     * 用于不会显示进度条，不可以取消的那种
     *
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(Request<?> request, HttpListener<T> httpCallback) {
        this.mRequest = request;
        this.callback = httpCallback;
    }
    
    /**
     * 用于不会显示进度条，不可以取消的那种
     *
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(FragmentActivity activity, Request<?> request, HttpListener<T> httpCallback) {
        this.mActivity = activity;
        this.mRequest = request;
        this.callback = httpCallback;
    }
    
    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (mWaitDialog != null && !mActivity.isFinishing () && !mWaitDialog.isShowing ())
            mWaitDialog.show ();
    }
    
    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (mWaitDialog != null && mWaitDialog.isShowing ())
            mWaitDialog.dismiss ();
    }
    
    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (baiduLoadingDialog != null && baiduLoadingDialog.isVisible ()) {
            baiduLoadingDialog.dismiss ();
        }
        int responseCode = response.getHeaders ().getResponseCode ();
        if (responseCode != 200) {
            // 200 OK      //客户端请求成功
            // 400 Bad Request  //客户端请求有语法错误，不能被服务器所理解
            // 401 Unauthorized //请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用
            // 403 Forbidden  //服务器收到请求，但是拒绝提供服务
            // 404 Not Found  //请求资源不存在，eg：输入了错误的URL
            // 405 表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。
            // 500 Internal Server Error //服务器发生不可预期的错误
            // 503 Server Unavailable  //服务器当前不能处理客户端的请求，一段时间后可能恢复正常
            UiUtils.toastDebug ( mActivity, what + "数据请求失败 " + responseCode );
        } else if (callback != null) {
            callback.onSucceed ( what, response );
        }
    }
    
    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        if (baiduLoadingDialog != null && baiduLoadingDialog.isVisible ()) {
            baiduLoadingDialog.dismiss ();
        }
        Exception exception = response.getException ();
        if (exception instanceof NetworkError) {// 网络不好
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_please_check_network );
        } else if (exception instanceof TimeoutError) {// 请求超时
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_timeout );
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_not_found_server );
        } else if (exception instanceof URLError) {// URL是错的
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_url_error );
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_not_found_cache );
        } else if (exception instanceof ProtocolException) {
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_system_unsupport_method );
        } else if (exception instanceof StorageSpaceNotEnoughError) {
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_storage_spce_not_enough );
        } else if (exception instanceof StorageReadWriteError) {
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_storage_read_writer_error );
        } else if (exception instanceof ServerError) {
            // 这个异常只会在下载的时候出现,表示服务器已经执行完部分对资源的GET请求
            UiUtils.showSnackbar ( mActivity, what + "", R.string.error_server_error );
        }
        Logger.e ( "错误：" + exception.getMessage () );
        if (callback != null)
            callback.onFailed ( what, response );
    }
}
