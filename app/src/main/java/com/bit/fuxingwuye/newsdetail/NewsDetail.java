package com.BIT.fuxingwuye.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.communityOwner.model.OssToken;
import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.communityOwner.util.LogUtil;
import com.BIT.communityOwner.util.UploadUtils;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.NoticeListBean;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class NewsDetail extends AppCompatActivity {

   Gson gson=new Gson();
   Intent intent;
   TextView newtitle,time,info,action_bar_title,editorName;
    ImageView img,btn_back;
    private OSS oss;
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
                    OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                            .getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken());
                    oss = new OSSClient(NewsDetail.this, data.getEndPoint(), credentialProvider);
                    try {
                        String url = oss.presignConstrainedObjectURL("bit-app", bean.getThumbnailUrl(),30 * 60);
                        ImageLoaderUtil.setImage(url, img,R.mipmap.image_default,R.mipmap.image_default);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
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
