package com.bit.fuxingwuye.activities.residential_quarters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bit.communityOwner.KeyString;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.AcitivityHousingBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * Created by 23 on 2018/2/26.
 */

public class Housing extends BaseActivity<HousingImpl> implements HousingContract.View {
    public static int HOUSING_TAG = 0 ;
    AcitivityHousingBinding binding;
    HousingPickerAdapter housingPickerAdapter;
    ACache aCache;
    String village = "";

    @Override
    public void toastMsg(String msg) {

    }


    @Override
    protected void setupVM() {

    }

    @Override
    protected void setupHandlers() {
        super.setupHandlers();
    }

    @Override
    protected void initEventAndData() {
        aCache = ACache.get(this);
        village = aCache.getAsString(HttpConstants.village);
        binding = DataBindingUtil.setContentView(this, R.layout.acitivity_housing);
        if(HOUSING_TAG == 1){
            binding.toolbar.actionBarTitle.setText("选择小区");
            binding.toolbar.btnBack.setVisibility(View.GONE);
            HOUSING_TAG = 0 ;
        }else {
            binding.toolbar.actionBarTitle.setText("切换小区");
            binding.toolbar.btnBack.setVisibility(View.VISIBLE);
        }
        binding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.updata.setVisibility(View.GONE);
        mPresenter.getHousing(aCache.getAsString(HttpConstants.USERID));
        binding.updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getHousing(aCache.getAsString(HttpConstants.USERID));
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
    @Override
    public void showError(){
        binding.updata.setVisibility(View.VISIBLE);
    }

    /**
     * 获取社区返回的数据
     * @param community
     */
    @Override
    public void showHousing(Community community) {
        if(community != null && community.getRecords().isEmpty()){
            binding.updata.setVisibility(View.VISIBLE);

        }else{
            binding.updata.setVisibility(View.GONE);
        }
        if (community != null && community.getRecords().size() > 0) {
            housingPickerAdapter = new HousingPickerAdapter(R.layout.housing_item, community.getRecords());
            binding.rvRoomList.setLayoutManager(new LinearLayoutManager(this));
            binding.rvRoomList.setAdapter(housingPickerAdapter);
            housingPickerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (housingPickerAdapter.getData().get(position).getName() != null) {
                        aCache.put(HttpConstants.village, housingPickerAdapter.getData().get(position).getName());
                        LogUtil.e(Tag.tag, housingPickerAdapter.getData().get(position).getId());
                        aCache.put(HttpConstants.COMMUNIYID, housingPickerAdapter.getData().get(position).getId());
                        EvenBusMessage message = new EvenBusMessage();
                        message.setEvent(HttpConstants.village);
                        message.setValuse(housingPickerAdapter.getData().get(position).getName());
                        EventBus.getDefault().post(message);
                        Intent intent = new Intent(Housing.this, MainTabActivity.class);
                        if(getIntent().getBundleExtra(KeyString.EXTRA_BUNDLE) != null){
                            intent.putExtra(KeyString.EXTRA_BUNDLE, getIntent().getBundleExtra(KeyString.EXTRA_BUNDLE));
                        }
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }

    }

    private class HousingPickerAdapter extends BaseQuickAdapter<Community.RecordsBean, BaseViewHolder> {

        public HousingPickerAdapter(int layoutResId, @Nullable List<Community.RecordsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Community.RecordsBean item) {
            helper.setText(R.id.communtiy_txt, item.getName());
            if ((village != null && !village.equals("")) && village.equals(item.getName())) {
                helper.setChecked(R.id.housingstaut, true);
            } else {
                helper.setChecked(R.id.housingstaut, false);
            }

        }
    }
}
