package com.BIT.fuxingwuye.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.adapter.ProvincePickerAdapter;
import com.BIT.fuxingwuye.bean.CityBean;
import com.BIT.fuxingwuye.bean.DistrictBean;
import com.BIT.fuxingwuye.bean.ProvinceBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.databinding.ActivityProvincePickerBinding;
import com.BIT.fuxingwuye.interfaces.OnCommon2Listener;
import com.BIT.fuxingwuye.utils.ProXmlParserHandler;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * SmartCommunity-com.BIT.fuxingwuye.activities
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class ProvincePickerActivity extends AppCompatActivity {

    private ActivityProvincePickerBinding mBinding;
    private ProvincePickerAdapter adapter;
    private String mCurrentProviceName, mCurrentCityName, mCurrentDistrictName;
    private int mCurrentCityPos, mCurrentProvicePos;
    private List<ProvinceBean> provinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_province_picker);
        mBinding.toolbar.actionBarTitle.setText("选择户口所在地");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        provinces = initProvinceDatas();
        initRecycleView();
    }

    private void initRecycleView(){
        mBinding.rvAreaList.setHasFixedSize(true);
        mBinding.rvAreaList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProvincePickerAdapter(this);
        adapter.setData(provinces);
        adapter.setOnItemClickListner(new OnCommon2Listener() {
            @Override
            public void OnCallBack(Object object1, Object object2) {
                if (object2 instanceof ProvinceBean){  // 省
                    adapter.setChoosedId(-1);
                    adapter.setData(((ProvinceBean) object2).getCityList());
                    adapter.notifyDataSetChanged();
                    mCurrentProviceName = ((ProvinceBean) object2).getName();
                    mCurrentProvicePos = (int) object1;
                    mBinding.tvProvice.setText(mCurrentProviceName);
                    mBinding.tvProvice.setVisibility(View.VISIBLE);

                } else if (object2 instanceof CityBean){  // 市
                    adapter.setChoosedId(-1);
                    adapter.setData(((CityBean) object2).getDistrictList());
                    adapter.notifyDataSetChanged();
                    mCurrentCityName = ((CityBean) object2).getName();
                    mCurrentCityPos = (int) object1;
                    mBinding.tvCity.setText(mCurrentCityName);
                    mBinding.tvCity.setVisibility(View.VISIBLE);
                } else if (object2 instanceof DistrictBean){  // 区
                    mCurrentDistrictName = ((DistrictBean) object2).getName();

                    // // TODO: 2017/7/14  请在此setresult sb 是你想要的结果 然后finish
                    StringBuffer sb = new StringBuffer();
                    sb.append(mCurrentProviceName).append(mCurrentCityName).append(mCurrentDistrictName);
                    Intent it = new Intent();
                    it.putExtra("account",sb.toString());
                    setResult(AppConstants.RES_PICK_PROVINCE,it);
                    finish();
                }
            }
        });
        mBinding.rvAreaList.setAdapter(adapter);
    }

    /**
     * 解析省市区的XML数据
     */
    protected List<ProvinceBean> initProvinceDatas() {
        AssetManager asset = this.getAssets();
        List<ProvinceBean> provinceBeanList = null;
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            ProXmlParserHandler handler = new ProXmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceBeanList = handler.getDataList();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
        return provinceBeanList;
    }

    public void pickProvince(View view){
        mBinding.tvProvice.setVisibility(View.GONE);
        mBinding.tvCity.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(provinces);
        adapter.notifyDataSetChanged();
    }

    public void pickCity(View view) {
        mBinding.tvCity.setVisibility(View.GONE);
        adapter.setChoosedId(-1);
        adapter.setData(provinces.get(mCurrentProvicePos).getCityList());
        adapter.notifyDataSetChanged();
    }
}
