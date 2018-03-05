package com.BIT.fuxingwuye.activities.communityDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.adapter.PhotoAdapter;
import com.BIT.fuxingwuye.adapter.ReplyAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.CommunityBean;
import com.BIT.fuxingwuye.bean.ImagePathBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.ReplyBean;
import com.BIT.fuxingwuye.bean.ZanBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityCommunityDetailBinding;
import com.BIT.fuxingwuye.http.HttpProvider;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityDetailActivity extends BaseActivity<CommunityDetailPresenterImpl>
        implements CommunityDetailContract.View, View.OnClickListener {

    private ActivityCommunityDetailBinding mBinding;
    private ReplyAdapter replyAdapter;
    private List<CommunityBean.InfoReply> replies;
    private CommunityBean mCommunityBean;
    private String replyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_community_detail);
    }

    @Override
    protected void setupVM() {
        mBinding.toolbar.actionBarTitle.setText("动态详情");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        commonBean.setId(getIntent().getStringExtra("id"));
        mPresenter.getCommunity(commonBean);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.tvDelete.setOnClickListener(this);
        mBinding.btnSend.setOnClickListener(this);
        mBinding.llContent.setOnClickListener(this);
        mBinding.ivZan.setOnClickListener(this);
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
    public void showDetail(CommunityBean communityBean) {
        mCommunityBean = communityBean;
        mBinding.tvName.setText(communityBean.getUserName());
        mBinding.tvContent.setText(communityBean.getContent());
        mBinding.tvNames.setText(communityBean.getNames());
        Date date = new Date(communityBean.getPasteTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd HH:mm");
        mBinding.tvTime.setText(sdf.format(date));
        mBinding.tvZanNum.setText(communityBean.getZanNumber());
        mBinding.tvReplyNum.setText(communityBean.getReplyNumber());
        if (communityBean.getUserId().equals(ACache.get(this).getAsString(HttpConstants.USERID))){
            mBinding.tvDelete.setVisibility(View.VISIBLE);
            mBinding.ivZanstate.setImageResource(R.mipmap.icon_zangrey);
        }else {
            mBinding.tvDelete.setVisibility(View.INVISIBLE);
            mBinding.ivZanstate.setImageResource(R.mipmap.icon_zanred);
        }

        if (null!=communityBean.getImgList()){
            mBinding.llImages.setVisibility(View.VISIBLE);
            List<ImagePathBean> images = communityBean.getImgList();
            StringBuffer sb = new StringBuffer().append(HttpProvider.getHttpIpAdds());
            if (images.size()==1){
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),mBinding.ivContent1);
            }else if (images.size()==2){
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),mBinding.ivContent1);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(1).getImgUrl(),mBinding.ivContent2);
                mBinding.ivContent2.setVisibility(View.VISIBLE);
            }else {
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),mBinding.ivContent1);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(1).getImgUrl(),mBinding.ivContent2);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(2).getImgUrl(),mBinding.ivContent3);
                mBinding.ivContent2.setVisibility(View.VISIBLE);
                mBinding.ivContent3.setVisibility(View.VISIBLE);
            }
        }else {
            mBinding.llImages.setVisibility(View.GONE);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.xrecyclerview.setLayoutManager(linearLayoutManager);


        replies = communityBean.getReplies();
        replyAdapter = new ReplyAdapter(replies);
        mBinding.xrecyclerview.setAdapter(replyAdapter);
        replyAdapter.setOnItemClickListener(new ReplyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                replyId = replies.get(position).getId();
                mBinding.tvReplyName.setText("回复  "+replies.get(position).getUserName());
            }
        });
    }

    @Override
    public void deleteSuccess() {
        Intent it = new Intent();
        setResult(AppConstants.RES_REFRESH_INFO,it);
        finish();
    }

    @Override
    public void refresh() {
        CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        commonBean.setId(getIntent().getStringExtra("id"));
        mPresenter.getCommunity(commonBean);
        mBinding.etReply.setText("");
    }

    @Override
    public void refreshZan(String s) {
        mBinding.tvZanNum.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                Intent it = new Intent();
                setResult(AppConstants.RES_REFRESH_INFO,it);
                finish();
                break;
            case R.id.tv_delete:
                CommonBean commonBean = new CommonBean();
                commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
                commonBean.setId(getIntent().getStringExtra("id"));
                mPresenter.delete(commonBean);
                break;
            case R.id.ll_content:
                mBinding.tvReplyName.setText("");
                replyId = "";
                break;
            case R.id.iv_zan:
                ZanBean zanBean = new ZanBean();
                zanBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
                zanBean.setPasteId(mCommunityBean.getId());
                zanBean.setId(mCommunityBean.getZanId());
                mPresenter.zan(zanBean);
                break;
            case R.id.btn_send:
                if (TextUtils.isEmpty(mBinding.etReply.getText().toString().trim())){
                    Toast.makeText(CommunityDetailActivity.this,"请填写评论内容",Toast.LENGTH_SHORT).show();
                }else {
                    ReplyBean replyBean = new ReplyBean();
                    replyBean.setContent(mBinding.etReply.getText().toString().trim());
                    replyBean.setPasteId(mCommunityBean.getId());
                    replyBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
                    replyBean.setReplyId(replyId);
                    mPresenter.saveReply(replyBean);
                }
                break;
            default:
                break;
        }
    }
}
