package com.bit.fuxingwuye.activities.roomPicker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;

import com.bit.fuxingwuye.base.BaseActivity;

import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.Building;
import com.bit.fuxingwuye.bean.Community;

import com.bit.fuxingwuye.bean.Room;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityRoomPickerBinding;

import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.GsonUtil;
import com.bit.fuxingwuye.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * SmartCommunity-com.bit.fuxingwuye.activities
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class RoomPickerActivity extends BaseActivity<RoomPickerPresenterImpl> implements RoomPickerContract.View {

    private ActivityRoomPickerBinding mBinding;
    ACache aCache;


    private int step = 0;
    private int limit = 3;

    RoomPickerAdapter roomPickerAdapter;
    BuildingPickerAdapter buildingPickerAdapter;
    RoomsPickerAdapter roomsPickerAdapter;
    Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_picker);
    }

    @Override
    protected void setupVM() {
        aCache = ACache.get(this);
        mBinding.toolbar.actionBarTitle.setText("选择房间");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        limit = getIntent().getIntExtra("limit", 3);
        mPresenter.getcommunity(aCache.getAsString(HttpConstants.USERID));
        mBinding.tvPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Community community= gson.fromJson(aCache.getAsString("community"),Community.class);
                setcommunity(community);
                mBinding.tvBuild.setVisibility(View.GONE);
            }
        });
        mBinding.tvBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("backinfo","数据：ssssssssssssssss"+aCache.getAsString("building"));
                Building building= gson.fromJson(aCache.getAsString("building"),Building.class);
                setbuilding(building);
                mBinding.tvBuild.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取网络社区成功返回的数据
     * @param community
     */
    @Override
    public void showcommunity(Community community) {
        LogUtil.e("backinfo", "请求成功：返回数据：" + GsonUtil.toJson(community));
        aCache.put("community", GsonUtil.toJson(community));
        setcommunity(community);

    }
    /**
     * 设置rvRoomList的社区数据
     * @param
     */
    private void setcommunity(Community community){
        roomPickerAdapter = new RoomPickerAdapter(R.layout.communtiy_item, community.getRecords());

        if(aCache.getAsString(HttpConstants.village)!=null&&!"".equals(aCache.getAsString(HttpConstants.village))){
            Iterator<Community.RecordsBean> it = community.getRecords().iterator();
            while (it.hasNext()) {
                Community.RecordsBean bean = it.next();
                if(aCache.getAsString(HttpConstants.village)!=null&&!aCache.getAsString(HttpConstants.village).equals(bean.getName())){
                    it.remove();
                }

            }
        }
        mBinding.rvRoomList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRoomList.setAdapter(roomPickerAdapter);
        roomPickerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mBinding.tvPlot.setText(roomPickerAdapter.getData().get(position).getName());
                mBinding.tvPlot.setVisibility(View.VISIBLE);
                if(roomPickerAdapter.getData().get(position).getId()!=null){
                    mPresenter.getbuilding(roomPickerAdapter.getData().get(position).getId());
                }else{
                    Toast.makeText(RoomPickerActivity.this,"获取数据出错",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /**
     * 获取楼房成功返回的数据
     * @param building
     */
    @Override
    public void showbuilding(Building building) {
        LogUtil.e("back",GsonUtil.toJson(building));
        aCache.put("building", GsonUtil.toJson(building));
        setbuilding(building);

    }

    /**
     * 设置rvRoomList数据
     * @param building
     */
   private void setbuilding(Building building){
        LogUtil.e("backinfo","解析"+GsonUtil.formatBeanToJson(building));
       if (building!=null&&building.getRecords().size() > 0) {
           buildingPickerAdapter = new BuildingPickerAdapter(R.layout.communtiy_item, building.getRecords());
           mBinding.rvRoomList.setLayoutManager(new LinearLayoutManager(this));
           mBinding.rvRoomList.setAdapter(buildingPickerAdapter);
           buildingPickerAdapter.notifyDataSetChanged();
           buildingPickerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                   mBinding.tvBuild.setText(buildingPickerAdapter.getData().get(position).getName());
                   mBinding.tvBuild.setVisibility(View.VISIBLE);
                   if(buildingPickerAdapter.getData().get(position).getId()!=null){
                       aCache.put(HttpConstants.BUILDINGID,buildingPickerAdapter.getData().get(position).getId());
                       mPresenter.getroom(buildingPickerAdapter.getData().get(position).getId());
                   }else{
                       Toast.makeText(RoomPickerActivity.this,"获取数据出错",Toast.LENGTH_LONG).show();
                   }

               }
           });
       } else {
           Toast.makeText(RoomPickerActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
       }
   }

    /**
     * 获取房间成功返回的数据
     * @param room
     */
    @Override
    public void showroom(Room room) {
       LogUtil.e("backinfo","数据："+ GsonUtil.toJson(room));
        aCache.put("room", GsonUtil.toJson(room));
        if (room.getRecords().size() > 0) {

            roomsPickerAdapter = new RoomsPickerAdapter(R.layout.communtiy_item, room.getRecords());

            mBinding.rvRoomList.setLayoutManager(new LinearLayoutManager(this));
            mBinding.rvRoomList.setAdapter(roomsPickerAdapter);
            roomsPickerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(roomsPickerAdapter.getData().get(position).getId()!=null){
                        aCache.put(HttpConstants.ROOMID,roomsPickerAdapter.getData().get(position).getId());
                    }
                    Intent it = new Intent();
                    it.putExtra("room",mBinding.tvPlot.getText().toString().trim()+mBinding.tvBuild.getText().toString().trim()+roomsPickerAdapter.getData().get(position).getName());
                    setResult(300,it);
                    finish();

                }
            });
        } else {
            Toast.makeText(RoomPickerActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
        }

    }


    public void pickPlot(View view) {
     /*   mBinding.tvPlot.setVisibility(View.GONE);
        mBinding.tvBuild.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(plotInfos);
        adapter.notifyDataSetChanged();
        step=0;*/
    }

    public void pickBuild(View view) {
        /*mBinding.tvBuild.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(plotInfos.get(mCurrentPlotPos).getList());
        adapter.notifyDataSetChanged();
        step=1;*/
    }

    private class RoomPickerAdapter extends BaseQuickAdapter<Community.RecordsBean, BaseViewHolder> {

        public RoomPickerAdapter(int layoutResId, @Nullable List<Community.RecordsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Community.RecordsBean item) {
            helper.setText(R.id.communtiy_txt, item.getName());

        }
    }

    private class BuildingPickerAdapter extends BaseQuickAdapter<Building.RecordsBean, BaseViewHolder> {

        public BuildingPickerAdapter(int layoutResId, @Nullable List<Building.RecordsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Building.RecordsBean item) {
            helper.setText(R.id.communtiy_txt, item.getName());

        }
    }

    private class RoomsPickerAdapter extends BaseQuickAdapter<Room.RecordsBean, BaseViewHolder> {

        public RoomsPickerAdapter(int layoutResId, @Nullable List<Room.RecordsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Room.RecordsBean item) {
            helper.setText(R.id.communtiy_txt, item.getName());

        }
    }
}
