package com.bit.fuxingwuye.activities.fragment.elevatorFrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bit.communityOwner.BaseActivity;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.bean.ElevatorListRequestion;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.CommonAdapter;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.utils.ViewHolder;

import java.util.List;


public class ChangeElevatorActivity extends BaseActivity implements View.OnClickListener {


    TextView actionBarTitle;
    ImageView btnBack;

    ListView lvElevator;

    private String[] blueAddressIds;
    private CommonAdapter commonAdapter;
    private ElevatorListBean doorJinBoBean;
    private ACache mCache;

    public String Tag = "ChangeElevatorActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_elevator);
        initView();
        initViewData();
    }

    private void initView() {
        mCache = ACache.get(this);
        actionBarTitle = (TextView) findViewById(R.id.action_bar_title);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        lvElevator = (ListView) findViewById(R.id.lv_elevator);
        btnBack.setOnClickListener(this);
    }



    public void initViewData() {
        actionBarTitle.setText("切换电梯");
        doorJinBoBean = (ElevatorListBean) getIntent().getSerializableExtra("doorJinBoBean");

        blueAddressIds = getIntent().getStringArrayExtra("ids");
        initListView();
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }


    private void initListView() {
        commonAdapter = new CommonAdapter<ElevatorListBean>(this, R.layout.item_access_child) {
            @Override
            public void convert(ViewHolder holder, final ElevatorListBean elevatorListBean, int position, View convertView) {

                if (doorJinBoBean != null) {
                    if (ChangeElevatorActivity.this.doorJinBoBean.getMacAddress() != null) {
                        if (ChangeElevatorActivity.this.doorJinBoBean.getMacAddress().equals(elevatorListBean.getMacAddress())) {
                            ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                        }
                    }
                }

                holder.setText(R.id.tv_item, elevatorListBean.getElevatorNum() + elevatorListBean.getName());
                holder.setOnClickListener(R.id.tv_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("elevatorListBean", elevatorListBean);
                        setResult(10, intent);
                        finish();
                    }
                });
            }
        };
        lvElevator.setAdapter(commonAdapter);
    }

    private void getData() {

        ElevatorListRequestion elevatorListRequestion = new ElevatorListRequestion();
        elevatorListRequestion.setCommunityId("5a82adf3b06c97e0cd6c0f3d");
        elevatorListRequestion.setUserId(mCache.getAsString(HttpConstants.USERID));

        Api.lanyaElevatorLists(elevatorListRequestion, new ResponseCallBack<List<ElevatorListBean>>() {
            @Override
            public void onSuccess(List<ElevatorListBean> elevatorListBeans) {
                super.onSuccess(elevatorListBeans);
                if (elevatorListBeans != null) {
                    if (elevatorListBeans.size() > 0) {
                        ElevatorListBean elevatorListBean = new ElevatorListBean();
                        elevatorListBean.setName("一键开梯");
                        elevatorListBean.setElevatorNum("");
                        elevatorListBean.setFirst(true);
                        elevatorListBeans.add(0, elevatorListBean);
                        commonAdapter.setDatas(elevatorListBeans);
                    }
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                ToastUtil.showShort(e.getMsg());

            }
        });

    }


}



