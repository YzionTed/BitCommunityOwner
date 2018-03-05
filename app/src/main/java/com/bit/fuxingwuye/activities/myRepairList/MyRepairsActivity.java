package com.BIT.fuxingwuye.activities.myRepairList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.repairDetail.RepairDetailActivity;
import com.BIT.fuxingwuye.adapter.RepairAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityMyRepairsBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.BIT.fuxingwuye.constant.AppConstants.RES_REFRESH_REPAIR;

public class MyRepairsActivity extends BaseActivity<MyRepairsPresenterImpl> implements MyRepairsContract.View {

    private ActivityMyRepairsBinding mBinding;
    private int page = 1;
    private List<RepairBean> lists = new ArrayList<RepairBean>();
    private RepairAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_repairs);
        mBinding.toolbar.actionBarTitle.setText("我的报修");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupVM() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.xrecyclerview.setLayoutManager(linearLayoutManager);
        mBinding.xrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mBinding.xrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mBinding.xrecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);

        final CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));

        mBinding.xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                commonBean.setCurrentPage(page+"");
                mPresenter.getRepairs(commonBean,0);
            }

            @Override
            public void onLoadMore() {
                page++;
                commonBean.setCurrentPage(page+"");
                mPresenter.getRepairs(commonBean,1);
            }
        });

        mAdapter = new RepairAdapter(lists);
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
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRepairs(final List<RepairBean> datas,int type) {
        if (datas.size()==0){
            mBinding.llNoRepair.setVisibility(View.VISIBLE);
            mBinding.xrecyclerview.setVisibility(View.GONE);
        }else{
            mBinding.llNoRepair.setVisibility(View.GONE);
            mBinding.xrecyclerview.setVisibility(View.VISIBLE);
            if(type==0){
                lists.clear();
                for (RepairBean repairBean:datas){
                    lists.add(repairBean);
                }
                mAdapter.notifyDataSetChanged();
                mBinding.xrecyclerview.refreshComplete();
            }else if(type == 1){
                for (RepairBean repairBean:datas){
                    lists.add(repairBean);
                }
                mBinding.xrecyclerview.loadMoreComplete();
                mAdapter.notifyDataSetChanged();
            }else if(type == 4){
                mAdapter.notifyDataSetChanged();
                mBinding.xrecyclerview.refreshComplete();
            }else{
                mBinding.xrecyclerview.setNoMore(true);
            }
        }

        mAdapter.setOnItemClickListener(new RepairAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                startActivityForResult(new Intent(MyRepairsActivity.this, RepairDetailActivity.class)
                        .putExtra("id",position), AppConstants.REQ_REFRESH_REPAIR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RES_REFRESH_REPAIR:
                mBinding.xrecyclerview.refresh();
                break;
            default:
                break;
        }
    }
}
