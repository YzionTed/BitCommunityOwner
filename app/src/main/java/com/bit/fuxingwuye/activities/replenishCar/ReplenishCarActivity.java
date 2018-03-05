package com.BIT.fuxingwuye.activities.replenishCar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.commitSuccess.CommitSuccessActivity;
import com.BIT.fuxingwuye.activities.parkPicker.ParkPickerActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.bean.CarBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityReplenishCarBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.BitmapAndStringUtils;
import com.BIT.fuxingwuye.utils.CommonUtils;

public class ReplenishCarActivity extends BaseActivity<RCPresenterImpl> implements RCContract.View{

    private ActivityReplenishCarBinding mBinding;
    private ACache mCache;
    private static final int REQUEST_CAPTURE = 1;  //拍照

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_replenish_car);
        mCache = ACache.get(this);
        CarBean carBean = new CarBean();
        carBean.setUserId(mCache.getAsString(HttpConstants.USERID));
        mBinding.setBean(carBean);
    }

    @Override
    protected void setupVM() {
        if(getIntent().getIntExtra("type",0)==AppConstants.COME_FROM_REPLENISHDATA){//完善个人信息
            mBinding.toolbar.actionBarTitle.setText("完善个人信息");
            mBinding.btnNocar.setVisibility(View.VISIBLE);
            mBinding.ivTitle.setVisibility(View.VISIBLE);
            mBinding.llTitle.setVisibility(View.VISIBLE);
        }else if(getIntent().getIntExtra("type",0)==AppConstants.COME_FROM_CARMANAGER){ //新增车位
            mBinding.toolbar.actionBarTitle.setText("新增车位");
            mBinding.btnNocar.setVisibility(View.GONE);
            mBinding.ivTitle.setVisibility(View.GONE);
            mBinding.llTitle.setVisibility(View.GONE);
            mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setupHandlers() {
        mBinding.llTakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查权限(6.0以上做权限判断)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!BaseApplication.getInstance().isFileEnalbe()) {
                        BaseApplication.getInstance().checkWriteReadEnable(ReplenishCarActivity.this);
                    } else {
                        takeCamera();
                    }
                } else {
                    takeCamera();
                }
            }
        });
        mBinding.llAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBinding.llPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ReplenishCarActivity.this,ParkPickerActivity.class), AppConstants.REQ_PICK_PARK);
            }
        });
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBinding.getBean().getParkId())){
                    Toast.makeText(ReplenishCarActivity.this,"请填写车位号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(null!=mBinding.getBean().getCarNum()){
                    mBinding.getBean().setCarNum(CommonUtils.encryptData(mBinding.getBean().getCarNum()));
                }
                if(null!=mBinding.getBean().getDriveLicense()){
                    mBinding.getBean().setDriveLicense(CommonUtils.encryptData(mBinding.getBean().getDriveLicense()));
                }
                if(null!=mBinding.getBean().getUsePeople()){
                    mBinding.getBean().setUsePeople(CommonUtils.encryptData(mBinding.getBean().getUsePeople()));
                }
                mPresenter.commitData(mBinding.getBean());
            }
        });
        mBinding.btnNocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReplenishCarActivity.this,CommitSuccessActivity.class));
                finish();
            }
        });
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //拍照
    private void takeCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
            startActivityForResult(takePictureIntent, REQUEST_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==AppConstants.RES_PICK_PARK){//选择车位号完成
            mBinding.getBean().setParkId(data.getStringExtra("parkid"));
            mBinding.tvPark.setText(data.getStringExtra("park"));
        }else if(requestCode==REQUEST_CAPTURE&&resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mBinding.getBean().setDriveLicense(BitmapAndStringUtils.convertIconToString(imageBitmap));
            mBinding.photoHint.setVisibility(View.GONE);
            mBinding.ivPhoto.setVisibility(View.VISIBLE);
            mBinding.ivPhoto.setImageBitmap(imageBitmap);
        }
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
    public void commitSuccess() {
        if(getIntent().getIntExtra("type",0)==AppConstants.COME_FROM_REPLENISHDATA){//完善个人信息
            startActivity(new Intent(ReplenishCarActivity.this,CommitSuccessActivity.class));
            finish();
        }else if(getIntent().getIntExtra("type",0)==AppConstants.COME_FROM_CARMANAGER){ //新增车位
            Intent it = new Intent();
            setResult(AppConstants.RES_ADD_CAR,it);
            finish();
        }
    }
}
