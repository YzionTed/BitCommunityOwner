package com.BIT.fuxingwuye.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.onlinePay.OnlinePayActivity;
import com.BIT.fuxingwuye.activities.payList.PayListActivity;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Dell on 2017/9/1.
 * Created time:2017/9/1 16:48
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, View.OnClickListener {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    private RelativeLayout toolbar;
    private TextView action_bar_title;
    private ImageView btn_back;
    private Button btn_repay,btn_pay_success;
    private ImageView iv_pay_result;
    private TextView tv_pay_result,tv_pay_resason;
    private LinearLayout ll_pay_fail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        initView();
        if(null!=OnlinePayActivity.instance){
            OnlinePayActivity.instance.finish();
        }

        api = WXAPIFactory.createWXAPI(this, AppConstants.WECHAT_APPID);
        api.handleIntent(getIntent(), this);


    }

    private void initView() {
        toolbar = (RelativeLayout)findViewById(R.id.toolbar);
        action_bar_title = (TextView)toolbar.findViewById(R.id.action_bar_title);
        action_bar_title.setText("缴费结果");
        btn_back = (ImageView)toolbar.findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);

        btn_repay = (Button)findViewById(R.id.btn_repay);
        btn_pay_success = (Button)findViewById(R.id.btn_pay_success);
        iv_pay_result = (ImageView)findViewById(R.id.iv_pay_result);
        tv_pay_result = (TextView)findViewById(R.id.tv_pay_result);
        tv_pay_resason = (TextView)findViewById(R.id.tv_pay_resason);
        ll_pay_fail = (LinearLayout)findViewById(R.id.ll_pay_fail);
        btn_repay.setOnClickListener(this);
        btn_pay_success.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "PayFinish,errCode = " + resp.errCode);

//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示");
//            builder.setMessage("微信支付结果："+resp.errCode);
//            builder.show();
//        }
        if(resp.errCode == 0){     //支付成功
            iv_pay_result.setImageResource(R.mipmap.u350);
            tv_pay_result.setText("缴费成功");
            tv_pay_resason.setText("成功缴纳物业管理费");
        }else if(resp.errCode == -1){    //支付失败
            iv_pay_result.setImageResource(R.mipmap.u318);
            tv_pay_result.setText("缴费失败");
            tv_pay_result.setTextColor(Color.RED);
            ll_pay_fail.setVisibility(View.VISIBLE);
            btn_pay_success.setVisibility(View.GONE);
        }else if(resp.errCode == -2){    //支付取消
            iv_pay_result.setImageResource(R.mipmap.u318);
            tv_pay_result.setText("缴费取消");
            tv_pay_result.setTextColor(Color.RED);
            ll_pay_fail.setVisibility(View.VISIBLE);
            btn_pay_success.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_repay:
                startActivity(new Intent(this, OnlinePayActivity.class));
                finish();
                break;
            case R.id.btn_pay_success:
                finish();
                break;
        }
    }
}
