package com.BIT.fuxingwuye.activities.repairDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.serviceComment.ServiceCommentActivity;
import com.BIT.fuxingwuye.adapter.RepairImageAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.EvaluationBean;
import com.BIT.fuxingwuye.bean.ImagePathBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.databinding.ActivityRepairDetailBinding;
import com.BIT.fuxingwuye.views.FullyGridLayoutManager;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.List;

public class RepairDetailActivity extends BaseActivity<RepairDetailPresenterImpl> implements RepairDetailContract.View {

    private ActivityRepairDetailBinding mBinding;
    private CommonBean commonBean;
    private RepairBean mRepairBean;
    private RepairImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_repair_detail);
        mBinding.toolbar.actionBarTitle.setText("故障报修详情");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.setActionHandler(mPresenter);
    }

    @Override
    protected void setupVM() {
        commonBean = new CommonBean();
        commonBean.setId(getIntent().getStringExtra("id"));
        mPresenter.getRepair(commonBean);

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
    public void showRepair(RepairBean repairBean) {
        mRepairBean = repairBean;
        mBinding.setBean(mRepairBean);
        if(mRepairBean.getRepairStatus().equals("待受理")){
            mBinding.btnCancel.setVisibility(View.VISIBLE);
        }else if(mRepairBean.getRepairStatus().equals("已受理")){
            mBinding.llRepairman.setVisibility(View.VISIBLE);
        }else if(mRepairBean.getRepairStatus().equals("已完成")){
            mBinding.btnDelete.setVisibility(View.VISIBLE);
            if(mRepairBean.getEvaluationStatus().equals("1")){
                mBinding.btnComment.setVisibility(View.VISIBLE);
            }
            EvaluationBean evaluationBean = new EvaluationBean();
            evaluationBean.setRepairNo(mRepairBean.getRepairNo());
            mPresenter.getComment(evaluationBean);
        }else if(mRepairBean.getRepairStatus().equals("已取消")){
            mBinding.btnDelete.setVisibility(View.VISIBLE);
        }else if(mRepairBean.getRepairStatus().equals("驳回")){
            mBinding.btnDelete.setVisibility(View.VISIBLE);
            mBinding.llRefuseReason.setVisibility(View.VISIBLE);
        }
        if(mRepairBean.getImgUrls().size()>0){
            FullyGridLayoutManager manager = new FullyGridLayoutManager(RepairDetailActivity.this, 3);
            manager.setSmoothScrollbarEnabled(false);
            mBinding.gridImage.setLayoutManager(manager);
            mBinding.gridImage.setVisibility(View.VISIBLE);
            List<String> list = new ArrayList<>();
            for (int i=0;i<mRepairBean.getImgUrls().size();i++){
                list.add(mRepairBean.getImgUrls().get(i).getImgUrl());
            }
            mAdapter = new RepairImageAdapter(this,list);
            mBinding.gridImage.setAdapter(mAdapter);
        }

    }

    @Override
    public void deleteSuccess() {
        Toast.makeText(this,"该条记录已删除",Toast.LENGTH_SHORT).show();
        Intent it = new Intent();
        setResult(AppConstants.RES_REFRESH_REPAIR, it);
        finish();
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this,"该记录已取消",Toast.LENGTH_SHORT).show();
        Intent it = new Intent();
        setResult(AppConstants.RES_REFRESH_REPAIR, it);
        finish();
    }

    @Override
    public void delete() {
        LemonHello.getWarningHello("提示","是否要删除该条报修记录")
                .addAction(new LemonHelloAction("删除", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                        mPresenter.deleteRepair(commonBean);
                    }
                }))
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }))
                .show(RepairDetailActivity.this);
    }

    @Override
    public void update() {
        mRepairBean.setRepairStatus("4");
        mPresenter.updateRepair(mRepairBean);
    }

    @Override
    public void comment() {
        startActivity(new Intent(this, ServiceCommentActivity.class).putExtra("id",mRepairBean.getRepairNo()));
    }

    @Override
    public void showComment(EvaluationBean evaluationBean) {
        if(null!=evaluationBean){
            mBinding.llComment.setVisibility(View.VISIBLE);
            mBinding.etServiceComment.setText(evaluationBean.getContent());
            mBinding.ratingbar.setClickable(false);
            mBinding.ratingbar.setStar(Integer.parseInt(evaluationBean.getGrade()),false);
        }
    }

    @Override
    public void showImage(List<ImagePathBean> path) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case AppConstants.RES_UPDATE_REPAIR:
                mPresenter.getRepair(commonBean);
                break;
        }
    }
}
