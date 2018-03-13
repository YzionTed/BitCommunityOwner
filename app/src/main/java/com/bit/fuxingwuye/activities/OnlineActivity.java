package com.bit.fuxingwuye.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.Online;
import com.bit.fuxingwuye.bean.OnlineData;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;


public class OnlineActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView title;
    private ListView lvOnline;
    private List<OnlineData> onlineDataList;

    String[] media = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private static final int RC_MEDIA = 123;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_online);
        initView();
        initListener();
    }


    /**
     * 初始化view
     */
    private void initView() {
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.title);
        lvOnline = findViewById(R.id.lv_online);

        requestOnlineData();


    }

    /**
     * 设置监听器
     */
    private void initListener() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvOnline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!EasyPermissions.hasPermissions(OnlineActivity.this, media)) {
                    EasyPermissions.requestPermissions(OnlineActivity.this, "需要获取拍照和录音权限", RC_MEDIA, media);
                } else {
                    if (NimUIKit.getAccount() != null) {
                        NimUIKit.startP2PSession(OnlineActivity.this, onlineDataList.get(i).getId());
                    }
                }
            }
        });
    }


    /**
     * 请求在线咨询列表
     */
    public void requestOnlineData() {
        String url = HttpConstants.Base_Url_Test + "/v1/user/property/" + "5a82adf3b06c97e0cd6c0f3d" + "/user-list";
        Log.e("onlineactivity", url);
        OkHttpUtils
                .get()
                .url(url)
                .addParams("postCode", "SUPPORTSTAFF")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("onlineactivity", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("onlineactivity", response);

                        Online online = new Gson().fromJson(response, Online.class);

                        if (online != null && online.isSuccess() && online.getData() != null){
                            onlineDataList = online.getData();
                            if (onlineDataList != null && onlineDataList.size() > 0){
                                lvOnline.setAdapter(new CommonAdapter<OnlineData>(OnlineActivity.this, R.layout.item_online, onlineDataList) {
                                    @Override
                                    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, OnlineData item, int position) {
                                        if (position == onlineDataList.size() - 1){
                                            viewHolder.getView(R.id.divider).setVisibility(View.GONE);
                                        }
                                        viewHolder.setText(R.id.tv_name, item.getCommunityName() + item.getPropertyName());


                                    }
                                });
                            }
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
