package com.bit.fuxingwuye.handlers;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.activities.payment.PaymentActivity;
import com.bit.fuxingwuye.activities.register.RegisterActivity;
import com.bit.fuxingwuye.activities.resetPwd.ResetPwdActivity;

/**
 * Created by Dell on 2017/7/5.
 */

public class MyHandlers {

    public void gotoRegister(View view){
        view.getContext().startActivity(new Intent(view.getContext(), RegisterActivity.class));
    }

    public void gotoPay(View view){
        view.getContext().startActivity(new Intent(view.getContext(), RegisterActivity.class));
    }

    public void resetPwd(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ResetPwdActivity.class));
    }

    public void gotoNoWhere(View view){
        Toast.makeText(view.getContext(),"该功能暂未开通",Toast.LENGTH_SHORT).show();
    }

    public void gotoPayment(View view){
        view.getContext().startActivity(new Intent(view.getContext(), PaymentActivity.class));
    }

}
