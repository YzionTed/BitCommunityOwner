package com.bit.fuxingwuye.activities.viaRecord;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.adapter.ViaAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.ViaBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityViaRecordBinding;
import com.bit.fuxingwuye.http.HttpProvider;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.ScannerUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViaRecordActivity extends BaseActivity<ViaRecordPresenterImpl> implements ViaRecordContract.View {

    private ActivityViaRecordBinding mBinding;
    private CommonBean commonBean = new CommonBean();
    private List<ViaBean> lists = new ArrayList<>();
    private int page = 1;
    private ViaAdapter mAdapter;
    private int type = 0;//0 列表，1 二维码,2 新增放行条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_via_record);
        mBinding.toolbar.actionBarTitle.setText("放行条记录");
        type = getIntent().getIntExtra("type",0);
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type== AppConstants.VIA_TYPE_LIST||type==AppConstants.VIA_TYPE_ADD){
                    finish();
                }else if(type==AppConstants.VIA_TYPE_QR){
                    mBinding.llShow.setVisibility(View.GONE);
                    mBinding.xrecyclerview.setVisibility(View.VISIBLE);
                    mBinding.xrecyclerview.refresh();
                    type = AppConstants.VIA_TYPE_LIST;
                }
            }
        });
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
                mPresenter.getVias(commonBean,0);
            }

            @Override
            public void onLoadMore() {
                page++;
                commonBean.setCurrentPage(page+"");
                mPresenter.getVias(commonBean,1);
            }
        });



        if (type==AppConstants.VIA_TYPE_LIST||type==AppConstants.VIA_TYPE_QR){
            mAdapter = new ViaAdapter(lists);
            mBinding.xrecyclerview.setAdapter(mAdapter);
            mBinding.xrecyclerview.refresh();
        }else if (type==AppConstants.VIA_TYPE_ADD){
            mBinding.llNovia.setVisibility(View.GONE);
            ViaBean viaBean = new ViaBean();
            viaBean.setBeginTime(getIntent().getStringExtra("start"));
            viaBean.setEndTime(getIntent().getStringExtra("end"));
            viaBean.setUrl(getIntent().getStringExtra("url"));
            viaBean.setViaStatus(1);
            showQR(viaBean);
        }

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {

    }

    @Override
    public void showList(List<ViaBean> viaBeanList, int type) {
        if(type==0){
            lists.clear();
            for (ViaBean viaBean:viaBeanList){
                lists.add(viaBean);
            }
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        }else if(type == 1){
            for (ViaBean viaBean:viaBeanList){
                lists.add(viaBean);
            }
            mBinding.xrecyclerview.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        }else if(type == 4){
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
            mBinding.xrecyclerview.setNoMore(true);
        }else{
            mBinding.xrecyclerview.refreshComplete();
            mBinding.xrecyclerview.setNoMore(true);
        }

        if (lists.size()==0){
            mBinding.llNovia.setVisibility(View.VISIBLE);
            mBinding.xrecyclerview.setVisibility(View.GONE);
        }else{
            mBinding.llNovia.setVisibility(View.GONE);
            mBinding.xrecyclerview.setVisibility(View.VISIBLE);
        }

        mAdapter.setOnItemClickListener(new ViaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showQR(lists.get(position));
            }
        });
    }

    private void showQR(ViaBean viaBean) {

        String url = HttpProvider.getHttpIpAdds()+ viaBean.getUrl();
        final Bitmap bitmap = ScannerUtils.createQRImage(url,800,800, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        mBinding.xrecyclerview.setVisibility(View.GONE);
        mBinding.llShow.setVisibility(View.VISIBLE);
        if (viaBean.getViaStatus()== -1){
            mBinding.btnCommit.setVisibility(View.GONE);
        }else if(viaBean.getViaStatus()== 1){
            mBinding.btnCommit.setVisibility(View.VISIBLE);
        }
        mBinding.ivCode.setImageBitmap(bitmap);
        if (type==AppConstants.VIA_TYPE_LIST||type==AppConstants.VIA_TYPE_QR){
            Date date1 = new Date(Long.parseLong(viaBean.getBeginTime()));
            Date date2 = new Date(Long.parseLong(viaBean.getEndTime()));
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd HH:mm");
            mBinding.tvTime.setText("有效期："+sdf.format(date1)+"-"+sdf.format(date2));
            type = AppConstants.VIA_TYPE_QR;
        }else if(type==AppConstants.VIA_TYPE_ADD){
            mBinding.tvTime.setText("有效期："+viaBean.getBeginTime()+"-"+viaBean.getEndTime());
            type = AppConstants.VIA_TYPE_ADD;
        }

        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannerUtils.saveImageToGallery(ViaRecordActivity.this,bitmap);
                Toast.makeText(ViaRecordActivity.this,"二维码已保存到相册",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (type==AppConstants.VIA_TYPE_LIST){
                finish();
            }else if(type==AppConstants.VIA_TYPE_QR){
                mBinding.llShow.setVisibility(View.GONE);
                mBinding.xrecyclerview.setVisibility(View.VISIBLE);
                mBinding.xrecyclerview.refresh();
                type = 0;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
