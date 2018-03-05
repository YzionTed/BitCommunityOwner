package com.bit.fuxingwuye.activities.payRecord;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.onlinePay.OnlinePayActivity;
import com.bit.fuxingwuye.activities.payContent.PayContentActivity;
import com.bit.fuxingwuye.activities.payList.PayListActivity;
import com.bit.fuxingwuye.adapter.PayAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityPayRecordBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PayRecordActivity extends BaseActivity<PayRecordPresenterImpl> implements PayRecordContract.View {

    private ActivityPayRecordBinding mBinding;
    private PayAdapter mAdapter;
    private List<PayListBean> lists = new ArrayList<>();
    private int page = 1;
    private CommonBean commonBean = new CommonBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_pay_record);
        mBinding.toolbar.actionBarTitle.setText("缴费记录");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupVM() {
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.xrecyclerview.setLayoutManager(linearLayoutManager);
        mBinding.xrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mBinding.xrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mBinding.xrecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);

        mBinding.xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                commonBean.setCurrentPage(page+"");
                mPresenter.showPayList(commonBean,0);
            }

            @Override
            public void onLoadMore() {
                page++;
                commonBean.setCurrentPage(page+"");
                mPresenter.showPayList(commonBean,1);
            }
        });

        mAdapter = new PayAdapter(lists);
        mBinding.xrecyclerview.setAdapter(mAdapter);
        mBinding.xrecyclerview.refresh();
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {

    }

    @Override
    public void showPayList(List<PayListBean> payListBeanList, int type) {
        if(type==0){
            lists.clear();
            for (PayListBean payListBean:payListBeanList){
                lists.add(payListBean);
            }
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        }else if(type == 1){
            for (PayListBean payListBean:payListBeanList){
                lists.add(payListBean);
            }
            mBinding.xrecyclerview.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        }else if(type == 4){
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
            mBinding.xrecyclerview.setNoMore(true);
        }else{
            mBinding.xrecyclerview.setNoMore(true);
        }

        if (lists.size()>0){
            mBinding.llNopay.setVisibility(View.GONE);
            mBinding.xrecyclerview.setVisibility(View.VISIBLE);
        }else {
            mBinding.llNopay.setVisibility(View.VISIBLE);
            mBinding.xrecyclerview.setVisibility(View.GONE);
        }
        mAdapter.setOnItemClickListener(new PayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(PayRecordActivity.this, PayContentActivity.class)
                        .putExtra("id",lists.get(position).getExpensesNo())
                .putExtra("type",2));
            }
        });
    }
}
