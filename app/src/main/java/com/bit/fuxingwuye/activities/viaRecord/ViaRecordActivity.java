package com.bit.fuxingwuye.activities.viaRecord;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.adapter.ViaAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.PropertyBean;
import com.bit.fuxingwuye.bean.request.PassCodeBean;
import com.bit.fuxingwuye.bean.request.DataPagesBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityViaRecordBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.ScannerUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViaRecordActivity extends BaseActivity<ViaRecordPresenterImpl> implements ViaRecordContract.View {

    private ActivityViaRecordBinding mBinding;
    private PropertyBean commonBean = new PropertyBean();
    private List<PassCodeBean> lists = new ArrayList<>();
    private int page = 1;
    private int size = 10;
    private int mTotalPage = 0;
    private ViaAdapter mAdapter;
    private int type = 0;//0 列表，1 二维码,2 新增放行条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_via_record);
        mBinding.toolbar.actionBarTitle.setText("放行条记录");
        type = getIntent().getIntExtra("type",0);
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        commonBean.setCommunityId(ACache.get(this).getAsString(HttpConstants.COMMUNIYID));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.xrecyclerview.setLayoutManager(linearLayoutManager);
        mBinding.xrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mBinding.xrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mBinding.xrecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mAdapter = new ViaAdapter(lists);
        mBinding.xrecyclerview.setAdapter(mAdapter);


        Api.getPassCodeList(commonBean,page,size, new ResponseCallBack<DataPagesBean<PassCodeBean>>() {
            @Override
            public void onSuccess(DataPagesBean data) {
                super.onSuccess(data);
                if(data.getRecords().isEmpty()){
                    mBinding.llNovia.setVisibility(View.VISIBLE);
                    mBinding.xrecyclerview.setVisibility(View.GONE);
                }else{
                    mBinding.xrecyclerview.setVisibility(View.VISIBLE);
                    mBinding.llNovia.setVisibility(View.GONE);
                    mTotalPage = data.getTotalPage();
                    lists.clear();
                    List<PassCodeBean> datas = (List<PassCodeBean>) data.getRecords();
                    for (PassCodeBean viaBean:  datas){
                        lists.add(viaBean);
                    }
                    mAdapter.notifyDataSetChanged();
                    mBinding.xrecyclerview.refreshComplete();
                }

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if(lists.isEmpty()){
                    mBinding.llNovia.setVisibility(View.VISIBLE);
                    mBinding.xrecyclerview.setVisibility(View.GONE);
                }else{
                    mBinding.xrecyclerview.setVisibility(View.VISIBLE);
                    mBinding.llNovia.setVisibility(View.GONE);
                }
            }
        });


        mBinding.xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Api.getPassCodeList(commonBean,page,size, new ResponseCallBack<DataPagesBean<PassCodeBean>>() {
                    @Override
                    public void onSuccess(DataPagesBean data) {
                        super.onSuccess(data);
                        lists.clear();
                        List<PassCodeBean> datas = (List<PassCodeBean>) data.getRecords();
                        for (PassCodeBean viaBean:  datas){
                            lists.add(viaBean);
                        }
                        mAdapter.notifyDataSetChanged();
                        mBinding.xrecyclerview.refreshComplete();

                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        super.onFailure(e);
                    }
                });
            }

            @Override
            public void onLoadMore() {
                if(page <= mTotalPage){
                    page = page+1;
                    Api.getPassCodeList(commonBean,page,size, new ResponseCallBack<DataPagesBean<PassCodeBean>>() {
                        @Override
                        public void onSuccess(DataPagesBean data) {
                            super.onSuccess(data);
                            List<PassCodeBean> datas = (List<PassCodeBean>) data.getRecords();
                            for (PassCodeBean viaBean: datas){
                                lists.add(viaBean);
                            }
                            mAdapter.notifyDataSetChanged();
                            mBinding.xrecyclerview.refreshComplete();

                        }

                        @Override
                        public void onFailure(ServiceException e) {
                            super.onFailure(e);
                        }
                    });
                }else{
                    toastMsg("已经到底了!");
                }
            }
        });

/*        if (type==AppConstants.VIA_TYPE_LIST||type==AppConstants.VIA_TYPE_QR){
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
        }*/

        mAdapter.setOnItemClickListener(new ViaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showQR(lists.get(position));
            }
        });
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void setupVM() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
     Toast.makeText(getBaseContext(),""+msg,Toast.LENGTH_SHORT).show();
        mBinding.xrecyclerview.loadMoreComplete();
    }

    @Override
    public void showList(List<PassCodeBean> viaBeanList, int type) {
        if(type==0){
            lists.clear();
            for (PassCodeBean viaBean:viaBeanList){
                lists.add(viaBean);
            }
            mAdapter.notifyDataSetChanged();
            mBinding.xrecyclerview.refreshComplete();
        }else if(type == 1){
            for (PassCodeBean viaBean:viaBeanList){
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


    }

    private void showQR(PassCodeBean viaBean) {
        String  id =  viaBean.getId();
        String erCode = "http://bit.cn/bit/"+1+"/"+1000+"/"+"no/"+"001"+"/para/"+"id/"+id;
        final Bitmap bitmap = ScannerUtils.createQRImage(erCode,800,800, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        mBinding.xrecyclerview.setVisibility(View.GONE);
        mBinding.llShow.setVisibility(View.VISIBLE);
        mBinding.btnCommit.setVisibility(View.VISIBLE);
        mBinding.ivCode.setImageBitmap(bitmap);
        if (type==AppConstants.VIA_TYPE_LIST||type==AppConstants.VIA_TYPE_QR){
            Date date1 = new Date(viaBean.getBeginAt());
            Date date2 = new Date(viaBean.getEndAt());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mBinding.tvTime.setText("有效期："+sdf.format(date1)+" - "+sdf.format(date2));
            type = AppConstants.VIA_TYPE_QR;
        }else if(type==AppConstants.VIA_TYPE_ADD){
            Date date1 = new Date(viaBean.getBeginAt());
            Date date2 = new Date(viaBean.getEndAt());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mBinding.tvTime.setText("有效期："+sdf.format(date1)+" - "+sdf.format(date2));
            type = AppConstants.VIA_TYPE_ADD;
        }

        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(getBaseContext(),bitmap);
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

    public void saveImage(final Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "放行条二维码");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "放行条二维码.jpg";
        File file = new File(appDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "二维码已保存到手机相册", Toast.LENGTH_LONG).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, "PayCode");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
    }

}
