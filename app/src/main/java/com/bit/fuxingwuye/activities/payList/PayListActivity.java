package com.bit.fuxingwuye.activities.payList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bit.communityOwner.BaseActivity;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.chooseHouse.ChooseHouseActivity;
import com.bit.fuxingwuye.activities.onlinePay.OnlinePayActivity;
import com.bit.fuxingwuye.activities.payRecord.PayRecordActivity;
import com.bit.fuxingwuye.adapter.PayAdapter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityPayListBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PayListActivity extends BaseActivity implements PayListContract.View {

    private ActivityPayListBinding mBinding;
    private PayAdapter mAdapter;
    private List<PayListBean> lists = new ArrayList<>();
    private CommonBean commonBean = new CommonBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEventAndData();
    }

    //    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pay_list);
        mBinding.toolbar.actionBarTitle.setText("物业缴费");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
//        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_pay_record);
    }

    //    @Override
    protected void setupVM() {
//            List<UserBean.FloorMap> maps = ((UserBean)ACache.get(this).getAsObject(HttpConstants.USER)).getFloorInfo();
//            mBinding.tvHousename.setText(maps.get(0).getAddress());

        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
//        mPresenter.showPayList(commonBean);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.xrecyclerview.setLayoutManager(linearLayoutManager);

        mBinding.xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                mPresenter.showPayList(commonBean);
            }

            @Override
            public void onLoadMore() {
                mBinding.xrecyclerview.loadMoreComplete();
            }
        });
        mBinding.xrecyclerview.setNoMore(true);
        mAdapter = new PayAdapter(lists);
        mBinding.xrecyclerview.setAdapter(mAdapter);
        mBinding.xrecyclerview.refresh();

    }

    //    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rlHousename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(PayListActivity.this, ChooseHouseActivity.class)
                        , AppConstants.REQ_CHOOSE_HOUSE);
            }
        });
        mBinding.toolbar.ivRightActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayListActivity.this, PayRecordActivity.class));
            }
        });
    }

//    @Override
//    protected void initInject() {
//        getActivityComponent().inject(this);
//    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.RES_CHOOSE_HOUSE) {
            mBinding.tvHousename.setText(data.getStringExtra("housename"));
            commonBean.setFloorId(data.getStringExtra("houseid"));
            mBinding.xrecyclerview.refresh();
        }
    }

    @Override
    public void showPayList(List<PayListBean> payListBeanList) {
        lists.clear();
        for (PayListBean payListBean : payListBeanList) {
            lists.add(payListBean);
        }
        mAdapter.notifyDataSetChanged();
        mBinding.xrecyclerview.refreshComplete();

        if (lists.size() > 0) {
            mBinding.llNopay.setVisibility(View.GONE);
            mBinding.xrecyclerview.setVisibility(View.VISIBLE);
        } else {
            mBinding.llNopay.setVisibility(View.VISIBLE);
            mBinding.xrecyclerview.setVisibility(View.GONE);
        }
        mAdapter.setOnItemClickListener(new PayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(PayListActivity.this, OnlinePayActivity.class)
                        .putExtra("id", lists.get(position).getId()));
//                startActivity(new Intent(PayListActivity.this, PayContentActivity.class)
//                        .putExtra("id",lists.get(position).getExpensesNo())
//                .putExtra("type",1));
            }
        });
    }
}
