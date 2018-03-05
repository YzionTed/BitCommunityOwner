package com.bit.fuxingwuye.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.communityOwner.util.LogUtil;
import com.bit.communityOwner.util.UploadUtils;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.utils.GlideUtil;
import com.bit.fuxingwuye.utils.ImageLoaderUtil;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.bit.fuxingwuye.utils.OssManager;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class NewsDetail extends AppCompatActivity {

   Gson gson=new Gson();
   Intent intent;
   TextView newtitle,time,info,action_bar_title,editorName;
    ImageView img,btn_back;
    NoticeListBean.RecordsBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        newtitle= (TextView) findViewById(R.id.newtitle);
        time= (TextView) findViewById(R.id.time);
        info= (TextView) findViewById(R.id.info);
        editorName= (TextView) findViewById(R.id.editorName);
        action_bar_title= (TextView) findViewById(R.id.action_bar_title);
        img= (ImageView) findViewById(R.id.img);
        btn_back= (ImageView) findViewById(R.id.btn_back);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            bean=gson.fromJson(bundle.getString("newsdetail"),NoticeListBean.RecordsBean.class);
            newtitle.setText(bean.getTitle());
            time.setText(getFormatTime(bean.getCreateAt()));
            info.setText(bean.getBody());
            action_bar_title.setText(bean.getTitle());
            editorName.setText(bean.getEditorName());

        }
        Api.ossToken(new ResponseCallBack<OssToken>() {
            @Override
            public void onSuccess(final OssToken data) {
                if(data!=null){
                    OssManager.getInstance().init(NewsDetail.this, data);
                    String url = OssManager.getInstance().getUrl(bean.getThumbnailUrl());
                    GlideUtil.loadImage(NewsDetail.this, url, img);
                }

            }

            @Override
            public void onFailure(ServiceException e) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  String getFormatTime(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(time);

        String formatTime = format.format(date);

        return formatTime;

    }

}
