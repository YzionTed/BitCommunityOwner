package com.bit.fuxingwuye.activities.fragment.mineFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.aboutBit.AboutBitActivity;
import com.bit.fuxingwuye.activities.carManager.CarManagerActivity;
import com.bit.fuxingwuye.activities.editPersonal.EditPersonalActivity;
import com.bit.fuxingwuye.activities.feedback.FeedbackActivity;
import com.bit.fuxingwuye.activities.houseManager.HouseManagerActivity;
import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.activities.myRepairList.MyRepairsActivity;
import com.bit.fuxingwuye.activities.payRecord.PayRecordActivity;
import com.bit.fuxingwuye.activities.resetPwd.ResetPwdActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;
import com.bit.fuxingwuye.activities.setting.SettingActivity;
import com.bit.fuxingwuye.base.BaseFragment;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.bean.OutLogin;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.GlideUtil;
import com.bit.fuxingwuye.utils.ImageLoaderUtil;
import com.bit.fuxingwuye.utils.OssManager;
import com.bit.fuxingwuye.views.XCRoundImageView;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentMine extends BaseFragment<FMinePresenter> implements FMineContract.View, View.OnClickListener {

    private RelativeLayout rl_mine_house, rl_mine_park, rl_mine_pay, rl_mine_repair, rl_mine_pass,
            rl_mine_feedback, rl_mine_about, rl_mine, rl_mine_password;
    private XCRoundImageView iv_mine_avatar;
    private TextView tv_mine_name;
    private ImageView iv_setting, iv_review;
    private ACache mCache;
    private Boolean status;
    private TokenBean mUserBean;
    myBroadcastReceiver receiver;
    Button outlogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        receiver = new myBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.data.refreshdata");
        intentFilter.addAction("com.data.refreshUser");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fragment_mine;
    }

    @Override
    protected void initEventAndData() {
        mCache = ACache.get(getActivity());
        status = "3".equals(mCache.getAsString(HttpConstants.STATUS));
        rl_mine_house = (RelativeLayout) mView.findViewById(R.id.rl_mine_house);
        rl_mine_park = (RelativeLayout) mView.findViewById(R.id.rl_mine_park);
        rl_mine_pay = (RelativeLayout) mView.findViewById(R.id.rl_mine_pay);
        rl_mine_repair = (RelativeLayout) mView.findViewById(R.id.rl_mine_repair);
        rl_mine_pass = (RelativeLayout) mView.findViewById(R.id.rl_mine_pass);
        rl_mine_feedback = (RelativeLayout) mView.findViewById(R.id.rl_mine_feedback);
        rl_mine_about = (RelativeLayout) mView.findViewById(R.id.rl_mine_about);
        rl_mine = (RelativeLayout) mView.findViewById(R.id.rl_mine);
        rl_mine_password = (RelativeLayout) mView.findViewById(R.id.rl_mine_password);
        tv_mine_name = (TextView) mView.findViewById(R.id.tv_mine_name);
        iv_setting = (ImageView) mView.findViewById(R.id.iv_setting);
        iv_review = (ImageView) mView.findViewById(R.id.iv_review);

        outlogin = (Button) mView.findViewById(R.id.outlogin);
        iv_mine_avatar = (XCRoundImageView) mView.findViewById(R.id.iv_mine_avatar);
        rl_mine_house.setOnClickListener(this);
        rl_mine_park.setOnClickListener(this);
        rl_mine_pay.setOnClickListener(this);
        rl_mine_repair.setOnClickListener(this);
        rl_mine_pass.setOnClickListener(this);
        rl_mine_feedback.setOnClickListener(this);
        rl_mine_about.setOnClickListener(this);
        rl_mine.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        outlogin.setOnClickListener(this);
        rl_mine_password.setOnClickListener(this);
        islogin();
//        if (null != mCache.getAsObject(HttpConstants.USER)) {
//            mUserBean = (UserBean) mCache.getAsObject(HttpConstants.USER);
//            if (TextUtils.isEmpty(mUserBean.getOwner())) {
//                tv_mine_name.setText("请设置个人信息");
//            } else {
//                tv_mine_name.setText(mUserBean.getUserName());
//            }
//            if (null != mUserBean.getHeadImg() && !TextUtils.isEmpty(mUserBean.getHeadImg())) {
//                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds() + "/" + mUserBean.getHeadImg(), iv_mine_avatar);
//            }
//            switch (mUserBean.getIdentityStatus()) {
//                case 1:
//                    iv_review.setImageResource(R.mipmap.icon_pending_review);
//                    break;
//                case 2:
//                    iv_review.setImageResource(R.mipmap.icon_pending_review);
//                    break;
//                case 3:
//                    iv_review.setImageResource(R.mipmap.icon_been_reviewed);
//                    break;
//            }
//        } else {
//            // mPresenter.findPersonal(new FindBean(mCache.getAsString("mobilePhone")));
//        }
        if (null != mCache.getAsObject(HttpConstants.TOKENBEAN)) {
            TokenBean tokenBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
            if (TextUtils.isEmpty(tokenBean.getName())) {
                tv_mine_name.setText("游客");
            } else {
                tv_mine_name.setText(tokenBean.getName());
            }

            String url = OssManager.getInstance().getUrl(tokenBean.getHeadImg());
            GlideUtil.loadImage(getContext(), url, iv_mine_avatar);
//            ImageLoaderUtil.setImageWithCache(tokenBean.getHeadImg(), iv_mine_avatar);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Login(EvenBusMessage messageEvent) {
        if (messageEvent.getEvent().equals(HttpConstants.loginstaut)) {
            islogin();
        }
    }

    private void islogin() {
        if ("".equals(mCache.getAsString(HttpConstants.TOKEN)) || mCache.getAsString(HttpConstants.TOKEN) == null) {
            outlogin.setVisibility(View.VISIBLE);

        } else {
            outlogin.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_house:
                if (mCache.getAsString(HttpConstants.village) == null || "".equals(mCache.getAsString(HttpConstants.village))) {
                    LemonHelloInfo info3 = new LemonHelloInfo()
                            .setTitle("切换小区")
                            .setTitleFontSize(18)
                            .setContentFontSize(16)
                            .setContent("尊敬的用户，当前尚未选择小区，部分功能无法实现。")
                            .addAction(new LemonHelloAction("不前往", Color.parseColor("#999999"), new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();

                                }
                            }))
                            .addAction(new LemonHelloAction("前往", Color.RED, new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                    Intent intent = new Intent(getActivity(), Housing.class);
                                    startActivity(intent);
                                }
                            }));
                    info3.show(getActivity());
                } else {
                    getActivity().startActivity(new Intent(getActivity(), HouseManagerActivity.class));
                }

                break;
            case R.id.rl_mine_park:
                getActivity().startActivity(new Intent(getActivity(), CarManagerActivity.class));
                break;
            case R.id.outlogin:
                LemonHelloInfo info3 = new LemonHelloInfo()
                        .setTitle("退出")
                        .setTitleFontSize(18)
                        .setContentFontSize(16)
                        .setContent("确定要退出登录状态吗？")
                        .addAction(new LemonHelloAction("不退出", Color.parseColor("#999999"), new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();

                            }
                        }))
                        .addAction(new LemonHelloAction("退出", Color.RED, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();

                                ACache.get(getActivity()).clear();

                                getActivity().finish();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                                // mPresenter.outlogin();
                            }
                        }));
                info3.show(getActivity());
                break;

            case R.id.rl_mine_pay:
                getActivity().startActivity(new Intent(getActivity(), PayRecordActivity.class));
                break;
            case R.id.rl_mine_repair:
                getActivity().startActivity(new Intent(getActivity(), MyRepairsActivity.class));
                break;
            case R.id.rl_mine_pass:
                break;
            case R.id.rl_mine_feedback:
//                if (TextUtils.isEmpty(mUserBean.getOwner())) {
//                    Toast.makeText(getActivity(), "请先设置个人信息", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!status) {
//                    Toast.makeText(getActivity(), "审核未通过，该功能未开放", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.rl_mine_about:
                getActivity().startActivity(new Intent(getActivity(), AboutBitActivity.class));
                break;
            case R.id.rl_mine:
//                if (TextUtils.isEmpty(mUserBean.getOwner())) {
//                    startActivity(new Intent(getActivity(), ReplenishDataActivity.class));
//                } else {
                startActivity(new Intent(getActivity(), EditPersonalActivity.class));
//                }
                break;
            case R.id.iv_setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

            case R.id.rl_mine_password:
                startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void findPersonal(UserBean userBean) {
//        mUserBean = userBean;
        mCache.put(HttpConstants.USER, userBean);
        mCache.put(HttpConstants.USERID, userBean.getId());
        if (TextUtils.isEmpty(userBean.getOwner())) {
            tv_mine_name.setText("请设置个人信息");
        } else {
            tv_mine_name.setText(userBean.getUserName());
        }
        switch (userBean.getIdentityStatus()) {
            case 1:
                iv_review.setImageResource(R.mipmap.icon_pending_review);
                break;
            case 2:
                iv_review.setImageResource(R.mipmap.icon_pending_review);
                break;
            case 3:
                iv_review.setImageResource(R.mipmap.icon_been_reviewed);
                break;
        }
    }

    @Override
    public void outloginSucess(OutLogin str) {
        mCache.remove(HttpConstants.TOKEN);
        // islogin();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    class myBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.data.refreshUser")) {
                if (null != mCache.getAsObject(HttpConstants.TOKENBEAN)) {
                    mUserBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
                    if (TextUtils.isEmpty(mUserBean.getName())) {
                        tv_mine_name.setText("游客");
                    } else {
                        tv_mine_name.setText(mUserBean.getName());
                    }
                    if (!TextUtils.isEmpty(mUserBean.getHeadImg())) {
                        ImageLoaderUtil.setImageWithCache(mUserBean.getHeadImg(), iv_mine_avatar);
                    }
//                    switch (mUserBean.getIdentityStatus()) {
//                        case 1:
//                            iv_review.setImageResource(R.mipmap.icon_pending_review);
//                            break;
//                        case 2:
//                            iv_review.setImageResource(R.mipmap.icon_pending_review);
//                            break;
//                        case 3:
//                            iv_review.setImageResource(R.mipmap.icon_been_reviewed);
//                            break;
//                    }
                } else {
                    //mPresenter.findPersonal(new FindBean(mCache.getAsString("mobilePhone")));
                }
            } else if (intent.getAction().equals("com.data.refreshdata")) {
//                tv_mine_name.setText(intent.getStringExtra("username"));
//                mUserBean.setOwner("1");
//                mUserBean.setUserName(intent.getStringExtra("username"));
            }
        }
    }
}
