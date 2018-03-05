package com.BIT.fuxingwuye.activities.houseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.adapter.RecordAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.EvenBusConstants;
import com.BIT.fuxingwuye.base.ProprietorBean;
import com.BIT.fuxingwuye.bean.EvenBusMessage;
import com.BIT.fuxingwuye.bean.RecordData;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.GsonUtil;
import com.BIT.fuxingwuye.utils.LogUtil;
import com.BIT.fuxingwuye.utils.Tag;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Iterator;

/**
 * Created by 23 on 2018/2/28.
 */

public class ApplicationRecordActivity extends BaseActivity<ApplicationRecordImpl> implements ApplicationRecordContract.View {
    ListView listview;
    RecordAdapter adapter;
    Intent intent;
    TextView title;
    ImageView back;
    String roomid;
    @Override
    public void toastMsg(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_record);
        listview = (ListView) findViewById(R.id.fm_xrecyclerview);
        title = (TextView) findViewById(R.id.action_bar_title);
        back = (ImageView) findViewById(R.id.btn_back);
        intent = getIntent();
        EventBus.getDefault().register(this);
        Bundle bundle = intent.getExtras();
        title.setText("申请记录");
        if (bundle.getString(HttpConstants.ROOMID) != null) {
            roomid=bundle.getString(HttpConstants.ROOMID);
            mPresenter.GetRecord(roomid);
        } else {
            Toast.makeText(ApplicationRecordActivity.this, "缺少必要的参数", Toast.LENGTH_LONG).show();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ApplicationRecordActivity.this, ApplicationDetailsActivity.class);
                intent.putExtra(HttpConstants.ID, adapter.getItem(position).getId());
                LogUtil.e(Tag.tag,"数据："+GsonUtil.toJson(adapter.getItem(position)));
                intent.putExtra(HttpConstants.USER,GsonUtil.toJson(adapter.getItem(position)));
                intent.putExtra(HttpConstants.auditstatus,adapter.getItem(position).getAuditStatus());
                startActivity(intent);

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Updata(EvenBusMessage messageEvent) {
        if(messageEvent.getEvent().equals(EvenBusConstants.ApplicationRecordActivity)){
            if (roomid != null&&!"".equals(roomid)) {
                mPresenter.GetRecord(roomid);
            } else {
                Toast.makeText(ApplicationRecordActivity.this, "缺少必要的参数", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void ShowRecord(ProprietorBean recordData) {
        LogUtil.e(Tag.tag, "请求成功：" + GsonUtil.toJson(recordData));
        Iterator<ProprietorBean.RecordsBean> it = recordData.getRecords().iterator();
        while (it.hasNext()) {
            ProprietorBean.RecordsBean userObj = it.next();
            if (userObj.getRelationship() == 1) {
                it.remove();
            }
        }
        if (recordData.getRecords().size() <= 0) {
            Toast.makeText(this, "暂无信息", Toast.LENGTH_LONG).show();
        }
        adapter = new RecordAdapter(this, recordData.getRecords());
        listview.setAdapter(adapter);

    }
}
