package com.bit.fuxingwuye.activities.onlinePay;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayReqBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityOnlinePayBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

public class OnlinePayActivity extends BaseActivity<OnlinePayPresenter> implements OnlinePayContract.View {

    private ActivityOnlinePayBinding mBinding;
    private IWXAPI api = WXAPIFactory.createWXAPI(this, AppConstants.WECHAT_APPID);
    public static OnlinePayActivity instance;
    private CommonBean commonBean = new CommonBean();

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(OnlinePayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        mPresenter.aliQuery(commonBean);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OnlinePayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_online_pay);
    }

    @Override
    protected void setupVM() {
        mBinding.toolbar.actionBarTitle.setText("确认缴费");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setupHandlers() {
        mBinding.llWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rbWechat.setChecked(true);
                mBinding.rbAlipay.setChecked(false);
                mBinding.rbUnionpay.setChecked(false);
            }
        });
        mBinding.llAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rbWechat.setChecked(false);
                mBinding.rbAlipay.setChecked(true);
                mBinding.rbUnionpay.setChecked(false);
            }
        });
        mBinding.llUnionpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rbWechat.setChecked(false);
                mBinding.rbAlipay.setChecked(false);
                mBinding.rbUnionpay.setChecked(true);
            }
        });
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinding.rbWechat.isChecked()){
                    if(!api.isWXAppInstalled()){
                        toastMsg("请先安装微信");
                    }else if(api.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT){
                        toastMsg("您的微信版本不支持微信支付");
                    }else if(api.registerApp(AppConstants.WECHAT_APPID)){
                        commonBean.setId(getIntent().getStringExtra("id"));
                        PayReqBean payReqBean = new PayReqBean();
                        payReqBean.setId(getIntent().getStringExtra("id"));
                        payReqBean.setUserId(ACache.get(OnlinePayActivity.this).getAsString(HttpConstants.USERID));
                        mPresenter.wechat(payReqBean);
                    }
                }else if(mBinding.rbAlipay.isChecked()){
                    commonBean.setId(getIntent().getStringExtra("id"));
                    PayReqBean payReqBean = new PayReqBean();
                    payReqBean.setId(getIntent().getStringExtra("id"));
                    payReqBean.setUserId(ACache.get(OnlinePayActivity.this).getAsString(HttpConstants.USERID));
                    mPresenter.alipay(payReqBean);
                }else if(mBinding.rbUnionpay.isChecked()){
                    mPresenter.unionpay();
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wxpay(PayReqBean.cont req) {
        PayReq payReq = new PayReq();
        payReq.appId = req.getAppid();
        payReq.partnerId = req.getPartnerid();
        payReq.prepayId = req.getPrepayid();
        payReq.nonceStr = req.getNoncestr();
        payReq.timeStamp = req.getTimestamp();
        payReq.packageValue = "Sign=WXPay";
        payReq.sign = req.getSign();
        payReq.extData = "app data";
        api.sendReq(payReq);
        mBinding.btnCommit.setClickable(false);
    }

    @Override
    public void aliPay(PayReqBean.cont req) {
        final String orderInfo = req.getOrderStr();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OnlinePayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void wxPaySuccess() {

    }

    @Override
    public void aliPaySuccess() {

    }
}
