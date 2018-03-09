package com.bit.fuxingwuye.activities.fragment.mainFragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.communityOwner.util.RoomUtil;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.activities.addFaultRepair.FaultRepairActivity;
import com.bit.fuxingwuye.activities.brake.BrakeActivity;
import com.bit.fuxingwuye.activities.callEle.CallEleActivity;
import com.bit.fuxingwuye.activities.callPolice.CallPoliceActivity;
import com.bit.fuxingwuye.activities.community.CommunityActivity;
import com.bit.fuxingwuye.activities.payList.PayListActivity;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;
import com.bit.fuxingwuye.activities.shops.ShopsActivity;
import com.bit.fuxingwuye.activities.via.ViaActivity;
import com.bit.fuxingwuye.activities.videoDevices.VideoDevicesActivity;
import com.bit.fuxingwuye.adapter.ServicesAdapter;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseFragment;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.bean.MenuItem;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.bean.NoticeReqBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.request.DataPagesBean;
import com.bit.fuxingwuye.bean.request.NoticeBean;
import com.bit.fuxingwuye.chat.ChatActivity;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.newsdetail.NewsDetail;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.AppInfo;
import com.bit.fuxingwuye.utils.GsonUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.bit.fuxingwuye.views.BottomMenuFragment;
import com.bit.fuxingwuye.views.MenuItemOnClickListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends BaseFragment<FMainPresenter> implements FMainContract.View, View.OnClickListener,
        EasyPermissions.PermissionCallbacks {
    TextView chosehousing;
    private XRecyclerView fm_xrecyclerview;
    private ACache mCache;
    private Boolean status;
    private ServicesAdapter mAdapter;
    private List<NoticeListBean> lists = new ArrayList<NoticeListBean>();
    private int page = 1;
    private CommonBean commonBean;
    String[] phone = {Manifest.permission.CALL_PHONE};
    String[] media = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    String[] readwrite = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int RC_READ_WRITE = 120;
    private static final int RC_PHONE = 122;
    private static final int RC_MEDIA = 123;
    private static final int RC_LOCATION = 124;
    String village = "";
    LemonHelloInfo info3;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fragment_main;
    }

    LinearLayout grid_pay = null;
    LinearLayout grid_communition = null;
    LinearLayout grid_community = null;
    LinearLayout grid_repair = null;
    LinearLayout grid_shop = null;
    LinearLayout grid_police = null;
    LinearLayout grid_video = null;
    LinearLayout grid_brake = null;
    LinearLayout grid_pass = null;

    String topName;
    NoticeListBean notice;
    NoticeReqBean mNoticeReq = new NoticeReqBean();
    List<NoticeBean> Alldata = new ArrayList<>();
    private int size = 2;
    private int mTotalPage;

    /**
     *
     */
    @Override
    protected void initEventAndData() {
        mCache = ACache.get(getActivity());

        lists.clear();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        village = mCache.getAsString(HttpConstants.village);
        status = "3".equals(mCache.getAsString(HttpConstants.STATUS));
        // mPresenter.findOne(new FindBean(mCache.getAsString(HttpConstants.MOBILE)));
        fm_xrecyclerview = (XRecyclerView) mView.findViewById(R.id.fm_xrecyclerview);
        chosehousing = (TextView) mView.findViewById(R.id.choseHousing);
        fm_xrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        fm_xrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        fm_xrecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        if ("".equals(village) || null == village) {
            chosehousing.setText("切换小区");
        } else {
            chosehousing.setText(village);
        }
        mAdapter = new ServicesAdapter();
        fm_xrecyclerview.setAdapter(mAdapter);
        topName = chosehousing.getText().toString();


        String communityId = ACache.get(getActivity()).getAsString(HttpConstants.COMMUNIYID);
//
//        if ("5a8cfa62518089ae7afccc0c".equals(communityId)) {
//            View header = LayoutInflater.from(getActivity()).inflate(R.layout.f_header, (ViewGroup) mView.findViewById(android.R.id.content),
// false);
//            grid_pay = (LinearLayout) header.findViewById(R.id.grid_pay);
//            grid_communition = (LinearLayout) header.findViewById(R.id.grid_communition);
//            grid_community = (LinearLayout) header.findViewById(R.id.grid_community);
//            grid_repair = (LinearLayout) header.findViewById(R.id.grid_repair);
//            grid_shop = (LinearLayout) header.findViewById(R.id.grid_shop);
//            grid_police = (LinearLayout) header.findViewById(R.id.grid_police);
//            grid_video = (LinearLayout) header.findViewById(R.id.grid_video);
//            grid_brake = (LinearLayout) header.findViewById(R.id.grid_brake);
//            grid_pass = (LinearLayout) header.findViewById(R.id.grid_pass);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            fm_xrecyclerview.setLayoutManager(linearLayoutManager);
//            fm_xrecyclerview.addHeaderView(header);
//
//            grid_pay.setOnClickListener(this);
//            grid_communition.setOnClickListener(this);
//            grid_community.setOnClickListener(this);
//            grid_repair.setOnClickListener(this);
//            grid_shop.setOnClickListener(this);
//            grid_police.setOnClickListener(this);
//            grid_video.setOnClickListener(this);
//            grid_brake.setOnClickListener(this);
//            grid_pass.setOnClickListener(this);
//
////            showFunctionModule();
//
//        } else

        if ("5a82adf3b06c97e0cd6c0f3d".equals(communityId) || "5a8cfc54518089ae7afccc0d".equals(communityId) || "5a8cfa62518089ae7afccc0c".equals
                (communityId)) {
            View header = LayoutInflater.from(getActivity()).inflate(R.layout.f_header_hx, (ViewGroup) mView.findViewById(android.R.id.content),
                    false);
            grid_pay = (LinearLayout) header.findViewById(R.id.grid_pay);
            grid_repair = (LinearLayout) header.findViewById(R.id.grid_repair);
            grid_communition = (LinearLayout) header.findViewById(R.id.grid_communition);
            grid_police = (LinearLayout) header.findViewById(R.id.grid_police);
            grid_video = (LinearLayout) header.findViewById(R.id.grid_video);
            grid_pass = (LinearLayout) header.findViewById(R.id.grid_pass);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            fm_xrecyclerview.setLayoutManager(linearLayoutManager);
            fm_xrecyclerview.addHeaderView(header);
            grid_pay.setOnClickListener(this);
            grid_communition.setOnClickListener(this);
            grid_repair.setOnClickListener(this);
            grid_police.setOnClickListener(this);
            grid_video.setOnClickListener(this);
            grid_pass.setOnClickListener(this);
        }
//        else if ("5a8cfc54518089ae7afccc0d".equals(communityId)) {
//            View header = LayoutInflater.from(getActivity()).inflate(R.layout.f_header_qt, (ViewGroup) mView.findViewById(android.R.id.content),
//                    false);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            fm_xrecyclerview.setLayoutManager(linearLayoutManager);
//            fm_xrecyclerview.addHeaderView(header);
//        }

        chosehousing.setOnClickListener(this);
        commonBean = new CommonBean();
        commonBean.setUserId(mCache.getAsString(HttpConstants.USERID));
        fm_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Api.getNoticeList(mNoticeReq, page, size, new ResponseCallBack<DataPagesBean<NoticeBean>>() {
                    @Override
                    public void onSuccess(DataPagesBean<NoticeBean> data) {
                        super.onSuccess(data);
                        Alldata.clear();
                        Alldata.addAll(data.getRecords());
                        fm_xrecyclerview.refreshComplete();
                        showNotices(Alldata);


                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        fm_xrecyclerview.refreshComplete();
                        super.onFailure(e);
                    }
                });
            }

            @Override
            public void onLoadMore() {
                if (page <= mTotalPage) {
                    page = page + 1;
                    Api.getNoticeList(mNoticeReq, page, size, new ResponseCallBack<DataPagesBean<NoticeBean>>() {
                        @Override
                        public void onSuccess(DataPagesBean<NoticeBean> data) {
                            super.onSuccess(data);
                            List<NoticeBean> datas = data.getRecords();
                            Log.e(Tag.tag, "加载前：" + GsonUtil.toJson(Alldata));
                            if (datas != null && !datas.isEmpty()) {
                                Alldata.addAll(datas);
                                showNotices(Alldata);
                            }
                            Log.e(Tag.tag, "加载后：" + GsonUtil.toJson(Alldata));
                            fm_xrecyclerview.loadMoreComplete();

                        }

                        @Override
                        public void onFailure(ServiceException e) {
                            fm_xrecyclerview.loadMoreComplete();
                            super.onFailure(e);
                        }
                    });
                } else {
                    fm_xrecyclerview.loadMoreComplete();
                }
            }
        });
        mNoticeReq.setCommunityId(ACache.get(getContext()).getAsString(HttpConstants.COMMUNIYID));
        mNoticeReq.setNoticeType(1);
        Api.getNoticeList(mNoticeReq, page, size, new ResponseCallBack<DataPagesBean<NoticeBean>>() {
            @Override
            public void onSuccess(DataPagesBean<NoticeBean> data) {
                super.onSuccess(data);
                List<NoticeBean> datas = data.getRecords();
                Log.e("datas", "----datas size:" + datas.size());
                mTotalPage = data.getTotalPage();
                if (datas != null && !datas.isEmpty()) {
                    Alldata.addAll(datas);
                    showNotices(Alldata);
                }


            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                Log.e("datas", "----datas eeee:" + e);
            }
        });

        fm_xrecyclerview.setPullRefreshEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param
     * @param请求公告成功时返回的数据
     */

    public void showNotices(List<NoticeBean> datas) {
        Log.e(Tag.tag, GsonUtil.toJson(datas));
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
        fm_xrecyclerview.loadMoreComplete();
        mAdapter.setOnItemClickListener(new ServicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String id, int position) {
                Intent intent = new Intent(getActivity(), NewsDetail.class);
//                intent.putExtra("newsdetail",GsonUtil.toJson(mAdapter.getData(position)));
                intent.putExtra("id", mAdapter.getData(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void findOne(UserBean userBean) {
        mCache.put(HttpConstants.USER, userBean);
        mCache.put(HttpConstants.USERID, userBean.getId());
    }

    /**
     * @param activity 判断是否有选择小区和权限
     */
    private void HavaPermission(Class<?> activity) {
        if (mCache.getAsString(HttpConstants.COMMUNIYID) == null || "".equals(mCache.getAsString(HttpConstants.COMMUNIYID))) {
            Toast.makeText(getActivity(), "请选择小区", Toast.LENGTH_LONG).show();
        } else if (!RoomUtil.hasRoom(mCache.getAsString(HttpConstants.COMMUNIYID))) {
            showDigiog();
        } else {
            Intent intent = new Intent(getActivity(), activity);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grid_pay:
                HavaPermission(PayListActivity.class);
                break;
            case R.id.choseHousing:
                startActivity(new Intent(getActivity(), Housing.class));
                break;
            case R.id.grid_community:
                HavaPermission(CommunityActivity.class);
                // startActivity(new Intent(getActivity(), CommunityActivity.class));
                break;
            case R.id.grid_communition:
                if (BaseApplication.getInstance().callPhoneEnalbe()) {
                    final BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();

                    List<MenuItem> menuItemList = new ArrayList<MenuItem>();
                    MenuItem menuItem1 = new MenuItem();
                    menuItem1.setText("在线客服");
                    menuItem1.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem1) {
                        @Override
                        public void onClickMenuItem(View v, MenuItem menuItem) {
                            if (!EasyPermissions.hasPermissions(getActivity(), media)) {
                                EasyPermissions.requestPermissions(getActivity(), "需要获取拍照和录音权限", RC_MEDIA, media);
                            } else {
                                if (EMClient.getInstance().isConnected()) {
                                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "fxwy"));
                                } else {
                                    EMClient.getInstance().login(mCache.getAsString(HttpConstants.MOBILE), "123456", new EMCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,
                                                    "fxwy"));
                                        }

                                        @Override
                                        public void onError(int i, String s) {
                                            Toast.makeText(getActivity(), "登陆聊天系统失败，请电话联系物业", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {
                                            Toast.makeText(getActivity(), "正在登陆聊天系统...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                    MenuItem menuItem2 = new MenuItem();
                    menuItem2.setText("物业电话0472-710 5404");
                    menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
                        @Override
                        public void onClickMenuItem(View v, MenuItem menuItem) {
                            if (!EasyPermissions.hasPermissions(getActivity(), phone)) {
                                EasyPermissions.requestPermissions(getActivity(), "需要获取拨打电话权限", RC_PHONE, phone);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "04727105404"));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

//                    menuItemList.add(menuItem1);
                    menuItemList.add(menuItem2);

                    bottomMenuFragment.setMenuItems(menuItemList, "联系物业");

                    bottomMenuFragment.show(getActivity().getFragmentManager(), "BottomMenuFragment");

                } else {
                    BaseApplication.getInstance().checkCallPhoneEnable(getActivity());
                }
                break;
            case R.id.grid_repair:
                if (!EasyPermissions.hasPermissions(getActivity(), readwrite)) {
                    EasyPermissions.requestPermissions(getActivity(), "需要获取文件读写权限", RC_READ_WRITE, readwrite);
                } else {

                    getActivity().startActivity(new Intent(getActivity(), FaultRepairActivity.class).putExtra("update", false));
                }
                break;
            case R.id.grid_police:
                if (AppInfo.isNetworkAvailable(getContext())) {
                    HavaPermission(CallPoliceActivity.class);
                } else {
                    toastMsg("请检查网络设置!");
                }
                // getActivity().startActivity(new Intent(getActivity(), CallPoliceActivity.class));
                break;
            case R.id.grid_shop:
                HavaPermission(ShopsActivity.class);
                // getActivity().startActivity(new Intent(getActivity(), ShopsActivity.class));
                break;
            case R.id.grid_video:
                HavaPermission(VideoDevicesActivity.class);
                // getActivity().startActivity(new Intent(getActivity(), VideoDevicesActivity.class));
                break;
            case R.id.grid_brake:
                HavaPermission(BrakeActivity.class);
                //getActivity().startActivity(new Intent(getActivity(), BrakeActivity.class));
                break;
            case R.id.grid_pass:
                HavaPermission(ViaActivity.class);
                //getActivity().startActivity(new Intent(getActivity(), ViaActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case RC_PHONE:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "04727105404"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case RC_MEDIA:
                if (EMClient.getInstance().isConnected()) {
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "fxwy"));
                } else {
                    EMClient.getInstance().login(mCache.getAsString(HttpConstants.MOBILE), "66666", new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "fxwy"));
                        }

                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(getActivity(), "登陆聊天系统失败，请电话联系物业", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            Toast.makeText(getActivity(), "正在登陆聊天系统...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case RC_LOCATION:
                getActivity().startActivity(new Intent(getActivity(), CallEleActivity.class));
                break;
            case RC_READ_WRITE:
                getActivity().startActivity(new Intent(getActivity(), FaultRepairActivity.class).putExtra("update", false));
                break;
            default:
                break;
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * @param messageEvent 小区改变时进入这里进行更新公告
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setChosehousing(EvenBusMessage messageEvent) {
        if (messageEvent.getEvent().equals(HttpConstants.village)) {
            chosehousing.setText(messageEvent.getValuse());
            if (mCache.getAsString(HttpConstants.COMMUNIYID) != null) {
                page = 1;
                Api.getNoticeList(mNoticeReq, page, size, new ResponseCallBack<DataPagesBean<NoticeBean>>() {
                    @Override
                    public void onSuccess(DataPagesBean<NoticeBean> data) {
                        super.onSuccess(data);
                        List<NoticeBean> datas = data.getRecords();
                        Alldata.clear();
                        Alldata.addAll(datas);
                        showNotices(Alldata);


                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                    }
                });
            }
            getActivity().finish();
            startActivity(new Intent(getContext(), MainTabActivity.class));
        }
    }

    private void showDigiog() {
        info3 = new LemonHelloInfo()
                .setTitle("权限不足")
                .setTitleFontSize(18)
                .setContentFontSize(16)
                .setContent("该功能暂不开放，需要完成房产认证后方可体验！")
                .addAction(new LemonHelloAction("暂不认证", Color.parseColor("#999999"), new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                        helloView.hide();

                    }
                }))
                .addAction(new LemonHelloAction("立即认证", Color.RED, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                        Intent intent = new Intent(getActivity(), ReplenishDataActivity.class);
                        startActivity(intent);
                    }
                }));
        info3.show(getActivity());
    }
}
