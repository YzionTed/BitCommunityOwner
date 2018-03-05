package com.bit.fuxingwuye.activities.houseManager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.adapter.ProprietorManagerAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.EvenBusConstants;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.bit.fuxingwuye.views.MySwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 23 on 2018/2/28.
 */

public class ProprietorManagementActivity extends BaseActivity<ProprietorManagementImpl> implements ProprietorManagementContract.View{
    ProprietorManagerAdapter adapter;
    String roomid="";
    String roomladdress="";
    Intent intent;
    MySwipeMenuListView listView;
    TextView room,title,record;
    SwipeMenuCreator creator;
    ImageView btback;
    @Override
    public void toastMsg(String msg) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_proprietor_management);
        EventBus.getDefault().register(this);
        listView= (MySwipeMenuListView) findViewById(R.id.listView);
        room= (TextView) findViewById(R.id.room_txt);
        title= (TextView) findViewById(R.id.action_bar_title);
        record= (TextView) findViewById(R.id.record);
        btback= (ImageView) findViewById(R.id.btn_back);

        intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            roomid=bundle.getString(HttpConstants.ROOMID);
            roomladdress=bundle.getString("roomladdress");

        }
        if(roomid!=null&&!"".equals(roomid)){
            mPresenter.GetProprietorData(roomid);
        }else{
            Toast.makeText(this,"请重新选择社区",Toast.LENGTH_LONG).show();
        }
        room.setText(roomladdress);
        title.setText("住户管理");
        record.setVisibility(View.VISIBLE);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProprietorManagementActivity.this,ApplicationRecordActivity.class);
                intent.putExtra(HttpConstants.ROOMID,roomid);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Updata(EvenBusMessage messageEvent) {
        if(messageEvent.getEvent().equals(EvenBusConstants.ApplicationRecordActivity)){
            if (roomid != null&&!"".equals(roomid)) {
                mPresenter.GetProprietorData(roomid);
            } else {
                Toast.makeText(ProprietorManagementActivity.this, "缺少必要的参数", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void initEventAndData() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showProprietorData(ProprietorBean bean) {
        adapter=new ProprietorManagerAdapter(this,bean.getRecords());
        listView.setAdapter(adapter);
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                }
            }
            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(0);
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(18);
                menu.addMenuItem(item1);

            }
            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                item1.setWidth(dp2px(70));
                item1.setTitle("解绑");
                item1.setTitleSize(18);
                item1.setTitleColor(Color.WHITE);
                menu.addMenuItem(item1);

            }
        };
        listView.setMenuCreator(creator);
        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if(adapter.getItem(position).getId()!=null){
                            mPresenter.Relieve(adapter.getItem(position).getId());
                        }else{
                            Toast.makeText(ProprietorManagementActivity.this,"缺少重要字段，返回重试",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:

                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void showRelieveSuccess() {
        if(roomid!=null&&!"".equals(roomid)){
            mPresenter.GetProprietorData(roomid);
        }else{
            Toast.makeText(this,"请重新选择社区",Toast.LENGTH_LONG).show();
        }


    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
