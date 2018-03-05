package com.bit.fuxingwuye.http;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.bit.fuxingwuye.views.IOSLoadingDialog;



/**
 * Created by 23 on 2018/3/2.
 */

public class DialogUltis {
    private static IOSLoadingDialog promptDialog;
    public static void showDialog(FragmentManager context,String msg){
        if(promptDialog==null){
            promptDialog = new IOSLoadingDialog().setOnTouchOutside(true);
        }
        promptDialog.show(context,msg);
    }
    public static void closeDialog(){
        if(promptDialog!=null){
            promptDialog.dismiss();
        }
    }
}
