package com.BIT.fuxingwuye.activities.parkPicker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.adapter.RoomPickerAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.PlotInfoBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.databinding.ActivityParkPickerBinding;
import com.BIT.fuxingwuye.interfaces.OnCommon2Listener;

import java.util.List;

public class ParkPickerActivity extends BaseActivity<ParkPickerPresenterImpl> implements ParkPickerContract.View {

    private ActivityParkPickerBinding mBinding;
    private RoomPickerAdapter adapter;

    private String mCurrentPlotName, mCurrentBuildName, mCurrentRoomName,mParkName;
    private int mCurrentPlotPos, mCurrentBuildPos,mCurrentRoomPos;
    private int step = 0;
    private List<PlotInfoBean> plotInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_park_picker);
    }

    @Override
    protected void setupVM() {
        mBinding.toolbar.actionBarTitle.setText("选择车位");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CommonBean commonBean = new CommonBean();
        commonBean.setId("");
        mPresenter.getParks(commonBean);
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
    public void showParks(List<PlotInfoBean> plotInfoBeanList) {
        plotInfos = plotInfoBeanList;
        initRecycleView();
    }

    private void initRecycleView(){
        mBinding.rvRoomList.setHasFixedSize(true);
        mBinding.rvRoomList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomPickerAdapter(this);
        adapter.setData(plotInfos);
        adapter.setOnItemClickListner(new OnCommon2Listener() {
            @Override
            public void OnCallBack(Object object1, Object object2) {
                // // TODO: 2017/7/14
                if (step == 0){
                    adapter.setChoosedId(-1);
                    adapter.setData(((PlotInfoBean) object2).getList());
                    adapter.notifyDataSetChanged();
                    step++;
                    mCurrentPlotName = ((PlotInfoBean) object2).getValue();
                    mCurrentPlotPos = (int) object1;
                    mBinding.tvPlot.setText(mCurrentPlotName);
                    mBinding.tvPlot.setVisibility(View.VISIBLE);
                } else if (step == 1){
                    adapter.setChoosedId(-1);
                    if(null!=((PlotInfoBean) object2).getList()){
                        adapter.setData(((PlotInfoBean) object2).getList());
                        adapter.notifyDataSetChanged();
                        step++;
                        mCurrentBuildName = ((PlotInfoBean) object2).getValue();
                        mCurrentBuildPos = (int) object1;
                        mBinding.tvBuild.setText(mCurrentBuildName);
                        mBinding.tvBuild.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(ParkPickerActivity.this,"没有下级数据了",Toast.LENGTH_SHORT).show();
                        mCurrentBuildName = ((PlotInfoBean) object2).getValue();
                        StringBuffer sb = new StringBuffer();
                        sb.append(mCurrentPlotName).append(mCurrentBuildName);
                        Intent it = new Intent();
                        it.putExtra("park",sb.toString());
                        it.putExtra("parkid",((PlotInfoBean) object2).getKey());
                        setResult(AppConstants.RES_PICK_PARK,it);
                        finish();
                    }

                } else if (step == 2){
                    //TOdo setresult and finish
                    adapter.setChoosedId(-1);
                    if(null!=((PlotInfoBean) object2).getList()){
                        adapter.setData(((PlotInfoBean) object2).getList());
                        adapter.notifyDataSetChanged();
                        step++;
                        mCurrentRoomName = ((PlotInfoBean) object2).getValue();
                        mCurrentRoomPos = (int) object1;
                        mBinding.tvPark.setText(mCurrentRoomName);
                        mBinding.tvPark.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(ParkPickerActivity.this,"没有下级数据了",Toast.LENGTH_SHORT).show();
                        mCurrentRoomName = ((PlotInfoBean) object2).getValue();
                        StringBuffer sb = new StringBuffer();
                        sb.append(mCurrentPlotName).append(mCurrentBuildName).append(mCurrentRoomName);
                        Intent it = new Intent();
                        it.putExtra("park",sb.toString());
                        it.putExtra("parkid",((PlotInfoBean) object2).getKey());
                        setResult(AppConstants.RES_PICK_PARK,it);
                        finish();
                    }
                }else if(step==3){
                    mParkName = ((PlotInfoBean) object2).getValue();
                    StringBuffer sb = new StringBuffer();
                    sb.append(mCurrentPlotName).append(mCurrentBuildName).append(mCurrentRoomName).append(mParkName);
                    Intent it = new Intent();
                    it.putExtra("park",sb.toString());
                    it.putExtra("parkid",((PlotInfoBean) object2).getKey());
                    setResult(AppConstants.RES_PICK_PARK,it);
                    finish();
                }
            }
        });
        mBinding.rvRoomList.setAdapter(adapter);
    }

    public void carpickPlot(View view){
        mBinding.tvPlot.setVisibility(View.GONE);
        mBinding.tvBuild.setVisibility(View.GONE);
        mBinding.tvPark.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(plotInfos);
        adapter.notifyDataSetChanged();
        step=0;
    }

    public void carpickBuild(View view){
        mBinding.tvBuild.setVisibility(View.GONE);
        mBinding.tvPark.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(plotInfos.get(mCurrentPlotPos).getList());
        adapter.notifyDataSetChanged();
        step=1;
    }

    public void carpickPark(View view){
        mBinding.tvPark.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(plotInfos.get(mCurrentPlotPos).getList().get(mCurrentBuildPos).getList());
        adapter.notifyDataSetChanged();
        step=2;
    }
}
