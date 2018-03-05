package com.bit.fuxingwuye.http;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.bit.fuxingwuye.views.CustomProgressDialog;


/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 15:13
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog pd;

    private Context context;
    private boolean cancelable;
    private boolean show;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable,boolean show) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = false;
        this.show = show;
    }

    private void initProgressDialog(){
        if(show){
            if (pd == null) {
                pd = CustomProgressDialog.createLoadingDialog(context,"正在加载中");

                pd.setCancelable(cancelable);

                if (cancelable) {
                    pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            mProgressCancelListener.onCancelProgress();
                        }
                    });
                }

                if (!pd.isShowing()) {
                    pd.show();
                }
            }
        }

    }

    private void dismissProgressDialog(){
        if(show){
            if (pd != null) {
                pd.dismiss();
                pd = null;
            }
        }

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
