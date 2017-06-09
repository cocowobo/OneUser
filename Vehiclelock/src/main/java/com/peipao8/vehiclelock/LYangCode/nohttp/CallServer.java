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


import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.peipao8.vehiclelock.LYangCode.utils.data.SharePreManager;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/** NoHttp的添加请求到请求队列 的调度类 */
public class CallServer {
    
    private static CallServer callServer;
    
    /** 得到调度类. */
    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer ();
        return callServer;
    }
    
    /** 请求队列. */
    private RequestQueue requestQueue;
    
    private CallServer() { requestQueue = NoHttp.newRequestQueue (); }
    
    /** 下载队列. */
    private static DownloadQueue downloadQueue;
    
    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue ( 2 );
        return downloadQueue;
    }
    
    
    /**
     * 添加一个请求到请求队列.可以控制显示进度条和是否可以返回取消请求   context用来实例化dialog.  what 用来标志请求, 当多个请求使用同一个{@link HttpListener}时,
     * 在回调方法中会返回这个what.   request 请求对象.  callback  结果回调对象. canCancel 是否允许用户取消请求.
     * isLoading 是否显示dialog.
     */
    public <T> void add(FragmentActivity activity, int what, Request<T> request, HttpListener<T> callback, boolean
        canCancel, boolean isLoading) {
        if (idSetCachMode ( what, request )) {
            request.setCacheMode ( CacheMode.NONE_CACHE_REQUEST_NETWORK );
        }
        requestQueue.add ( what, request, new HttpResponseListener<T> ( activity, request, callback, canCancel,
            isLoading ) );
    }
    
    /**
     * 添加一个请求到请求队列.  和上面相比较，去掉了context的传递，和是否显示进度条，默认不显示，一般都是后台静静的请求
     * 所以为了保证最新都是不使用缓存的
     */
    public <T> void add(int what, Request<T> request, HttpListener<T> callback) {
        requestQueue.add ( what, request, new HttpResponseListener<T> ( request, callback ) );
    }
    
    /**
     * 添加一个请求到请求队列.  和上面相比较，去掉了context的传递，和是否显示进度条，默认不显示，一般都是后台静静的请求
     * 所以为了保证最新都是不使用缓存的
     */
    public <T> void add(Activity activity,int what, Request<T> request, HttpListener<T> callback) {
        requestQueue.add ( what, request, new HttpResponseListener<T> ( request, callback ) );
    }
    
    /**
     * 添加一个请求到请求队列.  和上面相比较，去掉了context的传递，和是否显示进度条，默认不显示，一般都是后台静静的请求
     * 所以为了保证最新都是不使用缓存的，但是有特殊情况，还是加上是否使用缓存的请求
     */
    public <T> void add(int what, Request<T> request, HttpListener<T> callback, Boolean isUseCach) {
        if (isUseCach && idSetCachMode ( what, request )) {
            request.setCacheMode ( CacheMode.NONE_CACHE_REQUEST_NETWORK );
        }
        requestQueue.add ( what, request, new HttpResponseListener<T> ( request, callback ) );
    }
    
    /** 请求时间是否小于2分钟 */
    private <T> boolean idSetCachMode(int what, Request<T> request) {
        int  length      = request.url ().length ();
        Long requestTime = SharePreManager.getLongValue ( "requestTime", "code" + what + length );
        long time        = System.currentTimeMillis ();
        if (time - requestTime < 2 * 60 * 1000) { return true; }
        SharePreManager.saveValue ( "requestTime", "code" + what + length, System.currentTimeMillis () );
        return false;
    }
    
    /** 取消这个sign标记的所有请求. */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign ( sign );
    }
    
    /** 取消队列中所有请求. */
    public void cancelAll() {
        requestQueue.cancelAll ();
    }
    
    /** 退出app时停止所有请求. */
    public void stopAll() {
        requestQueue.stop ();
    }
}
