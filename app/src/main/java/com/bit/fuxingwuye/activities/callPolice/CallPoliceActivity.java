package com.bit.fuxingwuye.activities.callPolice;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.adapter.CallPoliceAdapter;
import com.bit.fuxingwuye.adapter.GateAdapter;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.bean.CallPoliceBean;
import com.bit.fuxingwuye.bean.GetFeedbackBean;
import com.bit.fuxingwuye.bean.GetUserRoomListBean;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.UserRoomBean;
import com.bit.fuxingwuye.bean.request.AddCardReqBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.databinding.ActivityCallPoliceBinding;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;
import com.bit.fuxingwuye.utils.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CallPoliceActivity extends Activity{

    private ActivityCallPoliceBinding mBinding;
    List<GetUserRoomListBean> mUserRoomList = new ArrayList<GetUserRoomListBean>();
    CallPoliceBean callPoliceBean;
    UserBean userBean;
    private CallPoliceAdapter mAdapter;
    protected CompositeSubscription mCompositeSubscription;

    private Handler handler = new Handler();
    private Runnable runnable;
    private int[] mFrameRess;
    private int mLastFrameNo;
    private int mDuration = 100;
    private long mBreakDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_call_police);
        callPoliceBean = new CallPoliceBean();
        userBean = (UserBean)ACache.get(this).getAsObject(HttpConstants.USER);
//        userBean = new UserBean();
//
//        if(userBean != null){
//            maps = userBean.getFloorInfo();
//            callPoliceBean.setUserId("123456");
//        }
//        callPoliceBean.setType(2);//固定物业

        final int[] pFrameRess = {R.drawable.clock01,R.drawable.clock02,R.drawable.clock03,R.drawable.clock04,
                R.drawable.clock05,R.drawable.clock06,R.drawable.clock07,R.drawable.clock08,R.drawable.clock09,
                R.drawable.clock10,R.drawable.clock01,R.drawable.clock02,R.drawable.clock03,R.drawable.clock04,
                R.drawable.clock05,R.drawable.clock06,R.drawable.clock07,R.drawable.clock08,R.drawable.clock09,
                R.drawable.clock10,R.drawable.clock01,R.drawable.clock02,R.drawable.clock03,R.drawable.clock04,
                R.drawable.clock05,R.drawable.clock06,R.drawable.clock07,R.drawable.clock08,R.drawable.clock09,
                R.drawable.clock10,R.drawable.clock11};
        mFrameRess = pFrameRess;
        mLastFrameNo = pFrameRess.length-1;

        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable!=null)
                handler.removeCallbacks(runnable);
                finish();
            }
        });
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable!=null)
                    handler.removeCallbacks(runnable);
                finish();
            }
        });

      final String  communityId =  ACache.get(this).getAsString(HttpConstants.COMMUNIYID);
      final String  userId =  ACache.get(this).getAsString(HttpConstants.USERID);

        if(!TextUtils.isEmpty(communityId)){
            Api.getUserRoomsList(communityId, new ResponseCallBack<List<GetUserRoomListBean>>() {
                @Override
                public void onSuccess(List<GetUserRoomListBean> data) {
                    super.onSuccess(data);
                    mUserRoomList.clear();
                    mUserRoomList.addAll(data);
                    mAdapter = new CallPoliceAdapter(mUserRoomList);
                    mBinding.rvHouse.setLayoutManager(new GridLayoutManager(CallPoliceActivity.this, 2));
                    mBinding.rvHouse.setAdapter(mAdapter);
//                    mAdapter.setOnItemClickListener(new CallPoliceAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            if (!mUserRoomList.isEmpty()){
//                                callPoliceBean.setRoomid(mUserRoomList.get(position).getRoomId());
//                                mBinding.rvCallAnimation.setVisibility(View.VISIBLE);
//                                mBinding.tvTitle.setVisibility(View.GONE);
//                                mBinding.rvHouse.setVisibility(View.GONE);
//                                startAnimation();
//                                String rooms = "[{\"roomId\": \""+mUserRoomList.get(position).getRoomId()+"\", \"expireTime\":9000}]";
//                                AddCardReqBean bean = new AddCardReqBean();
//                                bean.setCommunityId(communityId);
//                                bean.setUserId(userId);
//                                bean.setKeyType("8");
//                                bean.setProcessTime(""+3600*10);
//                                bean.setRooms(rooms);
//                                Api.addUserCard(bean, new ResponseCallBack<String>() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        super.onSuccess(data);
//                                        Log.e("data","-1111---tag data:"+data);
//                                    }
//
//                                    @Override
//                                    public void onFailure(ServiceException e) {
//                                        super.onFailure(e);
//                                        Log.e("data","-2222---tag data:"+e);
//                                    }
//                                });
//
//                            }
//
//                        }
//                    });

                }

                @Override
                public void onFailure(ServiceException e) {
                    super.onFailure(e);
                }
            });
        }



    }

    private void startAnimation() {
        mBinding.ivCallAnimation.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    private void playConstant(final int i) {
        runnable = new Runnable() {
            @Override
            public void run() {
                mBinding.ivCallAnimation.setBackgroundResource(mFrameRess[i]);

                if (i != mLastFrameNo){
                    if (i==10){
                        mBinding.tvCount.setText("2");
                    }else if (i==20){
                        mBinding.tvCount.setText("1");
                    }
                    playConstant(i + 1);
                }else{
                    mBinding.tvCount.setVisibility(View.GONE);
                    callPolice();
                }
            }
        };
        handler.postDelayed(runnable, i == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);
    }

    private void callPolice() {

//        Observable observable = RetrofitManager.getInstace()
//                .create(NetworkApi.class).callPolice(callPoliceBean)
//                .map(new HttpResultFunc<String>());
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
//            @Override
//            public void next(BaseEntity<String> o) {
//                Toast.makeText(CallPoliceActivity.this,"已报警",Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        },this,false);
//        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(rxSubscription);

        Api.callPolice(callPoliceBean.getRoomid(), new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                toastMsg("发送报警成功！");
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });
    }


    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

}
