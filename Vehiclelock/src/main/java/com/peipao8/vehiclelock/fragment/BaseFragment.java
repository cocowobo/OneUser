package com.peipao8.vehiclelock.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 基类Fargment.只是初始化了上下文和依附的activity,同时内部封装了一些弹窗dialog.
 * 1.提示进度的dialog
 * 2.询问dialog
 * 3.警告弹窗
 */
public class BaseFragment extends Fragment {
    public Activity mActivity;
    public Context  mContext;
    private final String TAG = "=====BaseFragment" ;
    
    @Override
    public void onCreate( Bundle savedInstanceState) {
        mActivity =this.getActivity();
        mContext =this.getActivity().getApplicationContext();
        super.onCreate(savedInstanceState);
    }

    private AlertDialog queryDialog = null;
    private AlertDialog alertDialog = null;
    private ProgressDialog progressDialog = null;

    protected void showProgressDialog(Context context, String title, String message, boolean cancelable) {
        dismissProgressDialog();
        title = title == null ? "" : title;
        message = message == null ? "" : message;
        progressDialog = ProgressDialog.show(context, title, message);
        progressDialog.setCancelable(cancelable);
    }

    protected void updateProgressDialog(String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        }
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void showQueryDialog(Context context, String title, String message, boolean cancelable, final IQueryDialogResult dialogResult) {
        if (!(context instanceof Activity)) {
            return;
        }

        if (queryDialog != null && !queryDialog.isShowing()) {
            queryDialog.show();
            return;
        }

        Builder builder = new Builder(context);

        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }

        builder.setCancelable(cancelable);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (dialogResult != null) {
                    dialogResult.onOkClick();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (dialogResult != null) {
                    dialogResult.onCancelClick();
                }
            }
        });
        queryDialog = builder.create();
        queryDialog.show();
    }

    protected void showAlertDialog(Context context, String message, boolean cancelable, final IAlertDialogResult dialogResult) {
        if (!(context instanceof Activity)) {
            return;
        }

        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
            return;
        }

        Builder builder = new Builder(context);
        builder.setTitle("提示");

        if (message != null) {
            builder.setMessage(message);
        }

        builder.setCancelable(cancelable);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (dialogResult != null) {
                    dialogResult.onOkClick();
                }
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    protected void dismissQueryDialog() {
        if (queryDialog != null && queryDialog.isShowing()) {
            queryDialog.dismiss();
        }
    }

    protected void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public interface IQueryDialogResult {
        void onOkClick();

        void onCancelClick();
    }

    public interface IAlertDialogResult {
        void onOkClick();
    }
}
