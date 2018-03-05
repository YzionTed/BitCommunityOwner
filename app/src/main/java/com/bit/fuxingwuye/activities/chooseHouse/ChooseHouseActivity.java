package com.BIT.fuxingwuye.activities.chooseHouse;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityChooseHouseBinding;
import com.BIT.fuxingwuye.utils.ACache;

import java.util.List;

public class ChooseHouseActivity extends BaseActivity<ChooseHousePresenterImpl> implements ChooseHouseContract.View {

    private ActivityChooseHouseBinding mBinding;
    String[] floorids;
    String[] floornames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_choose_house);
        mBinding.toolbar.actionBarTitle.setText("请选择住房");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupVM() {
        if(null!= ACache.get(this).getAsObject(HttpConstants.USER)){
            List<UserBean.FloorMap> maps = ((UserBean)ACache.get(this).getAsObject(HttpConstants.USER)).getFloorInfo();
            floornames = new String[maps.size()];
            floorids = new String[maps.size()];
            for (int i=0;i<maps.size();i++){
                floorids[i] = maps.get(i).getFloorId();
                floornames[i] = maps.get(i).getAddress();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, floornames);
            mBinding.houselist.setAdapter(adapter);
            mBinding.houselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent();
                    it.putExtra("housename",floornames[position]);
                    it.putExtra("houseid",floorids[position]);
                    setResult(AppConstants.RES_CHOOSE_HOUSE,it);
                    finish();
                }
            });
        }else{
            mPresenter.findone(new FindBean(ACache.get(this).getAsString("mobilePhone")));
        }
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
    public void showFloors(UserBean userBean) {
        List<UserBean.FloorMap> maps = ((UserBean)ACache.get(this).getAsObject(HttpConstants.USER)).getFloorInfo();
        floornames = new String[maps.size()];
        floorids = new String[maps.size()];
        for (int i=0;i<maps.size();i++){
            floorids[i] = maps.get(i).getFloorId();
            floornames[i] = maps.get(i).getAddress();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, floornames);
        mBinding.houselist.setAdapter(adapter);
        mBinding.houselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();
                it.putExtra("housename",floornames[position]);
                it.putExtra("houseid",floorids[position]);
                setResult(AppConstants.RES_CHOOSE_HOUSE,it);
                finish();
            }
        });
    }
}
