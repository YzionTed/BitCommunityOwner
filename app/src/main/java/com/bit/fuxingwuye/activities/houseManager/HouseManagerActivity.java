package com.bit.fuxingwuye.activities.houseManager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.replenishHouse.ReplenishHouseActivity;
import com.bit.fuxingwuye.activities.householdManager.HouseholdManagerActivity;
import com.bit.fuxingwuye.adapter.HouseAdapter;
import com.bit.fuxingwuye.adapter.HouseManagerAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.EvenBusConstants;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.bean.FloorBean;
import com.bit.fuxingwuye.bean.RoomList;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityHouseManagerBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.LiteOrmUtil;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bit.fuxingwuye.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *住房管理主页面
 *
 */
public class HouseManagerActivity extends BaseActivity<HMPresenterImpl> implements HMContract.View {

    private ActivityHouseManagerBinding mBinding;
    private HouseAdapter houseAdapter;
    private ACache mCache;
    private UserBean userBean;
    private CommonBean commonBean;
    HouseManagerAdapter adapter;
    SwipeMenuListView listView;
    Map<String,String> map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(EvenBusMessage messageEvent) {
        if(messageEvent.getEvent().equals(EvenBusConstants.HouseManagerAcitvity)){
            if(mCache.getAsString(HttpConstants.COMMUNIYID)==null||mCache.getAsString(HttpConstants.USERID)==null){
                ToastUtil.showSingletonText(HouseManagerActivity.this,"缺少必要参数",300);
            }else{
                map.put("communityId",""+mCache.getAsString(HttpConstants.COMMUNIYID));
                map.put("userId",""+mCache.getAsString(HttpConstants.USERID));
                mPresenter.getFloors(map);
            }

        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_house_manager);
        mBinding.toolbar.actionBarTitle.setText("住房管理");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);

    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.addNewhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HouseManagerActivity.this,ReplenishDataActivity.class);
                it.putExtra("type",AppConstants.COME_FROM_HOUSEMANAGER);
               // it.putExtra("username",userBean.getUserName());
//                it.putExtra("phone",userBean.getMobilePhone());
                startActivityForResult(it, AppConstants.REQ_ADD_HOUSE);
            }
        });
    }

    /**
     * 侧滑交互
     */

    @Override
    protected void setupVM() {
       // mAppList = getPackageManager().getInstalledApplications(0);
        mCache = ACache.get(this);
        if(mCache.getAsObject("user")!=null){
            userBean = (UserBean) mCache.getAsObject("user");
        }
        /*commonBean = new CommonBean();
        commonBean.setUserId(mCache.getAsString(HttpConstants.USERID));*/

        if(mCache.getAsString(HttpConstants.COMMUNIYID)==null){
            Toast.makeText(HouseManagerActivity.this,"缺少重要属性，请重新选择小区",Toast.LENGTH_LONG).show();
        }else if(mCache.getAsString(HttpConstants.USERID)==null){
            Toast.makeText(HouseManagerActivity.this,"缺少重要属性，请重新登录重试",Toast.LENGTH_LONG).show();
        }else{
            map.put("communityId",""+mCache.getAsString(HttpConstants.COMMUNIYID));
            map.put("userId",""+mCache.getAsString(HttpConstants.USERID));
            mPresenter.getFloors(map);
        }

        listView = (SwipeMenuListView) findViewById(R.id.listView);
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                   /* case 2:
                        createMenu3(menu);
                        break;*/
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(0);
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(18);
                menu.addMenuItem(item1);


            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                item1.setWidth(dp2px(70));
                item1.setTitle("关闭");
                item1.setTitleSize(18);
                item1.setTitleColor(Color.WHITE);
                menu.addMenuItem(item1);

            }
        };
        listView.setMenuCreator(creator);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position)!=null){
                    if(adapter.getItem(position).getAuditStatus()==1&&adapter.getItem(position).getRelationship()==1){
                        Intent intent=new Intent(HouseManagerActivity.this,ProprietorManagementActivity.class);
                        intent.putExtra(HttpConstants.ROOMID,adapter.getItem(position).getRoomId());
                        intent.putExtra("roomladdress",adapter.getItem(position).getRoomLocation());
                        startActivity(intent);
                    }else if(adapter.getItem(position).getAuditStatus()==0){
                        Toast.makeText(HouseManagerActivity.this,"房屋认证正在审核",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:

                        if(adapter.getItem(position).getId()==null){
                            Toast.makeText(HouseManagerActivity.this,"缺少重要属性，请重新登录重试",Toast.LENGTH_LONG).show();
                        }else{
                            mPresenter.deleteFloor(adapter.getItem(position).getId());
                        }

                        break;
                    case 1:

                        break;
                }
                return false;
            }
        });
        // set SwipeListener
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
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

   /* @Override
    public void showFloors(final List<FloorBean> floorBeen) {
        if(null!=floorBeen){
          *//*  mBinding.llNohouse.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mBinding.recyclerView.setLayoutManager(linearLayoutManager);
            mBinding.recyclerView.setNestedScrollingEnabled(false);
            mBinding.recyclerView.setFocusable(false);
            houseAdapter = new HouseAdapter(floorBeen);
            mBinding.recyclerView.setAdapter(houseAdapter);
            houseAdapter.setOnItemClickListener(new HouseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent it = new Intent(HouseManagerActivity.this,HouseholdManagerActivity.class);
                    it.putExtra("plotname",floorBeen.get(position).getPlotName());
                    it.putExtra("houseid",floorBeen.get(position).getId());
                    startActivity(it);
                }
            });*//*
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case AppConstants.RES_ADD_HOUSE:
                if(mCache.getAsString(HttpConstants.COMMUNIYID)!=null&&mCache.getAsString(HttpConstants.USERID)!=null){
                    map.put("communityId",""+mCache.getAsString(HttpConstants.COMMUNIYID));
                    map.put("userId",""+mCache.getAsString(HttpConstants.USERID));
                    mPresenter.getFloors(map);
                }else{
                    ToastUtil.showSingletonText(HouseManagerActivity.this,"缺少必要参数",3000);
                }


                break;
        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    /**
     * 加载列表
     * @param floorBeen
     */

    @Override
    public void showFloors(List<RoomList> floorBeen) {
       if(floorBeen.size()<=0){
           mBinding.llNohouse.setVisibility(View.VISIBLE);
           listView.setVisibility(View.GONE);
       }else{
           mBinding.llNohouse.setVisibility(View.GONE);
           listView.setVisibility(View.VISIBLE);
           adapter = new HouseManagerAdapter(this,floorBeen);
           listView.setAdapter(adapter);
       }
    }

    /**
     * 侧滑关闭操作
     */
    @Override
    public void deleteSuccess() {
        if(mCache.getAsString(HttpConstants.COMMUNIYID)!=null&&mCache.getAsString(HttpConstants.USERID)!=null){
            map.put("communityId",""+mCache.getAsString(HttpConstants.COMMUNIYID));
            map.put("userId",""+mCache.getAsString(HttpConstants.USERID));
            mPresenter.getFloors(map);
        }else{
            ToastUtil.showSingletonText(HouseManagerActivity.this,"缺少必要参数",3000);
        }
        LogUtil.e(Tag.tag,"删除成功");
    }

    /**
     * 网络异常
     */
    @Override
    public void onError() {
        mBinding.onError.setVisibility(View.VISIBLE);
        TextView str= (TextView) mBinding.onError.findViewById(R.id.stauttv);
        str.setText("网络飞走了");
    }
}
