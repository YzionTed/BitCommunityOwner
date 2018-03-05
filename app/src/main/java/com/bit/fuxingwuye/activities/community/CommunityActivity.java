package com.BIT.fuxingwuye.activities.community;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.addReply.AddReplyActivity;
import com.BIT.fuxingwuye.activities.communityDetail.CommunityDetailActivity;
import com.BIT.fuxingwuye.adapter.CommunityAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.bean.ZanBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityCommunityBinding;
import com.BIT.fuxingwuye.http.HttpProvider;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityActivity extends BaseActivity<CommunityPresenterImpl> implements CommunityContract.View,
        View.OnClickListener, CommunityAdapter.ReplyCallBack, CommunityAdapter.ZanCallBack {

    private ActivityCommunityBinding mBinding;
    private CommunityAdapter mAdapter;
    private int page = 1;
    private int nextpage = 1;
    private List<InformationBean.Info> lists = new ArrayList<InformationBean.Info>();
    private ACache mCache;
    private CommonBean commonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_community);
        mBinding.toolbar.actionBarTitle.setText("社区动态");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_add);
    }

    @Override
    protected void setupVM() {
        mCache = ACache.get(this);
        commonBean = new CommonBean();
        commonBean.setUserId(mCache.getAsString(HttpConstants.USERID));
        if (null == mCache.getAsString(HttpConstants.DROPOUTTIME)) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mCache.put(HttpConstants.DROPOUTTIME, sdf.format(date));
            commonBean.setDropOutTime(mCache.getAsString(HttpConstants.DROPOUTTIME));
        } else {
            commonBean.setDropOutTime(mCache.getAsString(HttpConstants.DROPOUTTIME));
        }

        mPresenter.getReplies(commonBean);

        UserBean userBean = (UserBean) mCache.getAsObject(HttpConstants.USER);
        mBinding.tvName.setText(userBean.getUserName());
        if (null != userBean.getHeadImg() && !TextUtils.isEmpty(userBean.getHeadImg())) {
            ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds() + "/" + userBean.getHeadImg(), mBinding.ivHead);
        }

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
                commonBean.setCurrentPage(page + "");
                mPresenter.getEvents(commonBean, 0);
            }

            @Override
            public void onLoadMore() {
                if (nextpage > page) {
                    page = nextpage;
                    commonBean.setCurrentPage(page + "");
                    mPresenter.getEvents(commonBean, 1);
                } else {
                    mBinding.xrecyclerview.loadMoreComplete();
                }

            }
        });
        mAdapter = new CommunityAdapter(this, lists, this, this);
        mBinding.xrecyclerview.setAdapter(mAdapter);
        mBinding.xrecyclerview.refresh();
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.toolbar.ivRightActionBar.setOnClickListener(this);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEvents(InformationBean datas, int type) {

        if (datas.isLastPage()) {
            mBinding.xrecyclerview.setNoMore(true);
        } else {
            mBinding.xrecyclerview.setNoMore(false);
        }
        nextpage = datas.getNextPage();

        if (type == 0) {
            lists.clear();
            for (InformationBean.Info info : datas.getList()) {
                lists.add(info);
            }
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();

        } else if (type == 1) {
            for (InformationBean.Info info : datas.getList()) {
                lists.add(info);
            }
            mBinding.xrecyclerview.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        } else if (type == 4) {
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        } else {
            mBinding.xrecyclerview.setNoMore(true);
        }
        mAdapter.setOnItemClickListener(new CommunityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent it = new Intent(CommunityActivity.this, CommunityDetailActivity.class);
                it.putExtra("id", lists.get(position).getId());
                startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
            }
        });
    }

    @Override
    public void refresh(int pos, InformationBean.Info info) {
        lists.get(pos).setZanNumber(info.getZanNumber());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReply(List<InformationBean.Info> infos) {
        if (infos.size() == 0) {
            mBinding.tvNewReply.setText("没有新消息");
        } else {
            mBinding.tvNewReply.setText("有" + infos.size() + "条新消息");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_right_action_bar:
                startActivityForResult(new Intent(CommunityActivity.this, AddReplyActivity.class), AppConstants.REQ_REFRESH_INFO);
                break;
        }
    }

    @Override
    public void onReplyclick(View view, String position) {
        Intent it = new Intent(CommunityActivity.this, CommunityDetailActivity.class);
        it.putExtra("id", position);
        startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
    }

    @Override
    public void onZanClick(View view, String position) {
        ZanBean zanBean = new ZanBean();
        zanBean.setUserId(mCache.getAsString(HttpConstants.USERID));
        zanBean.setPasteId(lists.get(Integer.parseInt(position)).getId());
        zanBean.setId(lists.get(Integer.parseInt(position)).getZanId());
        mPresenter.zan(zanBean, Integer.parseInt(position));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case AppConstants.RES_REFRESH_INFO:
                mBinding.xrecyclerview.refresh();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mCache.put(HttpConstants.DROPOUTTIME, sdf.format(date));
    }
}
