package com.bit.fuxingwuye.activities.faultType;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.databinding.ActivityFaultTypeBinding;

public class FaultTypeActivity extends Activity implements View.OnClickListener {

    private ActivityFaultTypeBinding mBinding;
    private String faulttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_fault_type);
        mBinding.toolbar.actionBarTitle.setText("故障类型");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent().getStringExtra("type").equals(AppConstants.FAULT_PERSONAL)){
            mBinding.gridPersonal.setVisibility(View.VISIBLE);
            mBinding.gridPublic.setVisibility(View.GONE);
            mBinding.typeTip.setText("请选择住户故障类型");
            if (getIntent().getStringExtra("faulttype").equals("水电燃气")){
                mBinding.ivWater.setImageResource(R.mipmap.icon_water);
                mBinding.tvWater.setTextColor(getResources().getColor(R.color.blue1));
            }else if (getIntent().getStringExtra("faulttype").equals("房屋结构")){
                mBinding.ivHouse.setImageResource(R.mipmap.icon_housefault);
                mBinding.tvHouse.setTextColor(getResources().getColor(R.color.blue1));
            }else if (getIntent().getStringExtra("faulttype").equals("安防消防")){
                mBinding.ivFire.setImageResource(R.mipmap.icon_fire);
                mBinding.tvFire.setTextColor(getResources().getColor(R.color.blue1));
            }else if (getIntent().getStringExtra("faulttype").equals("其他")){
                mBinding.ivPersonalOther.setImageResource(R.mipmap.icon_other_personal);
                mBinding.tvPersonalOther.setTextColor(getResources().getColor(R.color.blue1));
            }
        }else if(getIntent().getStringExtra("type").equals(AppConstants.FAULT_PUBLIC)){
            mBinding.gridPersonal.setVisibility(View.GONE);
            mBinding.gridPublic.setVisibility(View.VISIBLE);
            mBinding.typeTip.setText("请选择公共物业故障类型");
            if (getIntent().getStringExtra("faulttype").equals("电梯")){
                mBinding.ivElevator.setImageResource(R.mipmap.icon_elefault);
                mBinding.tvElevator.setTextColor(getResources().getColor(R.color.blue1));
            }else if (getIntent().getStringExtra("faulttype").equals("门禁")){
                mBinding.ivGate.setImageResource(R.mipmap.icon_gatefault);
                mBinding.tvGate.setTextColor(getResources().getColor(R.color.blue1));
            }else if (getIntent().getStringExtra("faulttype").equals("其他")){
                mBinding.ivPublicOther.setImageResource(R.mipmap.icon_other_public);
                mBinding.tvPublicOther.setTextColor(getResources().getColor(R.color.blue1));
            }
        }

        mBinding.gridWater.setOnClickListener(this);
        mBinding.gridFire.setOnClickListener(this);
        mBinding.gridHouse.setOnClickListener(this);
        mBinding.gridOther.setOnClickListener(this);
        mBinding.gridGate.setOnClickListener(this);
        mBinding.gridElevator.setOnClickListener(this);
        mBinding.publicGridOther.setOnClickListener(this);
        mBinding.btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grid_water:
                faulttype = AppConstants.REPAIR_TYPE_WATER;
                initType();
                mBinding.ivWater.setImageResource(R.mipmap.icon_water);
                mBinding.tvWater.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.grid_fire:
                faulttype = AppConstants.REPAIR_TYPE_SAFE;
                initType();
                mBinding.ivFire.setImageResource(R.mipmap.icon_fire);
                mBinding.tvFire.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.grid_house:
                faulttype = AppConstants.REPAIR_TYPE_HOUSE;
                initType();
                mBinding.ivHouse.setImageResource(R.mipmap.icon_housefault);
                mBinding.tvHouse.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.grid_other:
                faulttype = AppConstants.REPAIR_TYPE_OTHER;
                initType();
                mBinding.ivPersonalOther.setImageResource(R.mipmap.icon_other_personal);
                mBinding.tvPersonalOther.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.grid_gate:
                faulttype = AppConstants.REPAIR_TYPE_GATE;
                initType();
                mBinding.ivGate.setImageResource(R.mipmap.icon_gatefault);
                mBinding.tvGate.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.grid_elevator:
                faulttype = AppConstants.REPAIR_TYPE_ELEVATOR;
                initType();
                mBinding.ivElevator.setImageResource(R.mipmap.icon_elefault);
                mBinding.tvElevator.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.public_grid_other:
                faulttype = AppConstants.REPAIR_TYPE_OTHER;
                initType();
                mBinding.ivPublicOther.setImageResource(R.mipmap.icon_other_public);
                mBinding.tvPublicOther.setTextColor(getResources().getColor(R.color.blue1));
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(faulttype)){
                    Toast.makeText(FaultTypeActivity.this,"请选择故障类型",Toast.LENGTH_SHORT).show();
                }else{
                    Intent it = new Intent();
                    it.putExtra("faulttype",faulttype);
                    if (getIntent().getStringExtra("type").equals(AppConstants.FAULT_PERSONAL)){
                        setResult(AppConstants.RES_PERSONAL_REPAIR,it);
                    }else if(getIntent().getStringExtra("type").equals(AppConstants.FAULT_PUBLIC)){
                        setResult(AppConstants.RES_PUBLIC_REPAIR,it);
                    }
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void initType() {
        if (getIntent().getStringExtra("type").equals(AppConstants.FAULT_PERSONAL)){
            mBinding.ivWater.setImageResource(R.mipmap.icon_water_unchoose);
            mBinding.ivFire.setImageResource(R.mipmap.icon_fire_unchoose);
            mBinding.ivHouse.setImageResource(R.mipmap.icon_housefault_unchoose);
            mBinding.ivPersonalOther.setImageResource(R.mipmap.icon_personal_unchoose);
            mBinding.tvWater.setTextColor(getResources().getColor(R.color.grary1));
            mBinding.tvFire.setTextColor(getResources().getColor(R.color.grary1));
            mBinding.tvHouse.setTextColor(getResources().getColor(R.color.grary1));
            mBinding.tvPersonalOther.setTextColor(getResources().getColor(R.color.grary1));
        }else if(getIntent().getStringExtra("type").equals(AppConstants.FAULT_PUBLIC)){
            mBinding.ivGate.setImageResource(R.mipmap.icon_gatefault_unchoose);
            mBinding.ivElevator.setImageResource(R.mipmap.icon_elefault_unchoose);
            mBinding.ivPublicOther.setImageResource(R.mipmap.icon_public_unchoose);
            mBinding.tvGate.setTextColor(getResources().getColor(R.color.grary1));
            mBinding.tvElevator.setTextColor(getResources().getColor(R.color.grary1));
            mBinding.tvPublicOther.setTextColor(getResources().getColor(R.color.grary1));
        }
    }
}
