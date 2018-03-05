package com.bit.fuxingwuye.activities.shops;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.goods.GoodsActivity;
import com.bit.fuxingwuye.adapter.ShopAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.MerchantBean;
import com.bit.fuxingwuye.bean.RepairBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityShopsBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopsActivity extends BaseActivity<ShopsPresenterImpl> implements ShopsContract.View {

    private ActivityShopsBinding mBinding;
    private int page = 1;
    private List<MerchantBean> lists = new ArrayList<MerchantBean>();
    private ShopAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_shops);
        mBinding.toolbar.actionBarTitle.setText("周边商户");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupVM() {
//        List<String> urls = new ArrayList<>();
//        urls.add("http://img.zcool.cn/sucaiori/733B8D1B-4140-8B0C-EF86-CC6C6793D072.jpg@700w_0e_1l.jpg");
//        urls.add("http://img.zcool.cn/sucaiori/BB0E9E48-9B9B-AC2B-EEB5-198391F8CC1A.jpg@700w_0e_1l.jpg");
//        urls.add("http://img.zcool.cn/sucaiori/8BD5E2A8-52E7-18D5-D77B-E50DFFD5EEA4.jpg@700w_0e_1l.jpg");
//        mBinding.bannerView.setData(urls);
        CommonBean commonBean = new CommonBean();
        commonBean.setPosition("2");
        mPresenter.getSlide(commonBean);

        final MerchantBean merchantBean = new MerchantBean();
        merchantBean.setCurrentPage(String.valueOf(page));

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
                merchantBean.setCurrentPage(page+"");
                mPresenter.getShops(merchantBean,0);
            }

            @Override
            public void onLoadMore() {
                page++;
                merchantBean.setCurrentPage(page+"");
                mPresenter.getShops(merchantBean,1);
            }
        });
        mAdapter = new ShopAdapter(lists);
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
    protected void onResume() {
        super.onResume();
        mBinding.bannerView.startBannerScrollTask(2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinding.bannerView.stopBannerTask();
    }

    @Override
    public void showShops(final List<MerchantBean> datas, int type) {
        if(type==0){
            lists.clear();
            for (MerchantBean merchantBean:datas){
                lists.add(merchantBean);
            }
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        }else if(type == 1){
            for (MerchantBean merchantBean:datas){
                lists.add(merchantBean);
            }
            mBinding.xrecyclerview.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        }else if(type == 4){
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        }else{
            mBinding.xrecyclerview.setNoMore(true);
        }
        mAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent it = new Intent(ShopsActivity.this, GoodsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("merchant",lists.get(position));
                it.putExtra("mid",position);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    @Override
    public void showSlide(List<String> slides) {

    }
}
