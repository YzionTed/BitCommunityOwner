package com.bit.fuxingwuye.activities.via;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Toast;

import com.bit.communityOwner.model.PassCode;
import com.bit.communityOwner.model.PassFlag;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.viaRecord.ViaRecordActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.ViaBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityViaBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.AppInfo;
import com.bit.fuxingwuye.utils.CommonUtils;
import com.bit.fuxingwuye.utils.ScannerUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.DateTimePicker;

public class ViaActivity extends BaseActivity<ViaPresenterImpl> implements ViaContract.View, View.OnClickListener {

    private ActivityViaBinding mBinding;
    private ViaBean viaBean = new ViaBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_via);
        mBinding.toolbar.actionBarTitle.setText("放行条");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_viahistory);
        mBinding.toolbar.ivRightActionBar.setOnClickListener(this);
        mBinding.ivStarttime.setOnClickListener(this);
        mBinding.ivEndtime.setOnClickListener(this);
        mBinding.btnCommit.setOnClickListener(this);

        mBinding.etViaErcodeLayout.setVisibility(View.GONE);

        TokenBean tokenBean = (TokenBean) ACache.get(this).getAsObject(HttpConstants.TOKENBEAN);
        mBinding.etViaName.setText(tokenBean.getName());
    }

    @Override
    protected void setupVM() {
       /* UserBean userBean = (UserBean) ACache.get(this).getAsObject(HttpConstants.USER);
        mBinding.etViaName.setText(userBean.getUserName());
        viaBean.setUserId(userBean.getId());
        viaBean.setUserName(userBean.getUserName());*/
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1=new Date();
        mBinding.etViaStart.setText(format.format(d1));
        viaBean.setBeginTime(format.format(d1));
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
     Toast.makeText(this,""+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess(String url) {
        Intent it = new Intent(ViaActivity.this,ViaRecordActivity.class);
        it.putExtra("url",url);
        it.putExtra("start",viaBean.getBeginTime());
        it.putExtra("end",viaBean.getEndTime());
        it.putExtra("type", AppConstants.VIA_TYPE_ADD);
        startActivity(it);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_right_action_bar:
                startActivity(new Intent(this, ViaRecordActivity.class));
                break;
            case R.id.iv_starttime:
                DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
                Time t = new Time();
                t.setToNow();
                picker.setDateRangeStart(t.year, t.month+1, t.monthDay);
                picker.setDateRangeEnd(2099, 11, 11);
                picker.setTimeRangeStart(t.hour, t.minute);
                picker.setTimeRangeEnd(23, 59);
                picker.setTopLineColor(0x99FF0000);
//                picker.setLabelTextColor(0xFFFF0000);
                picker.setDividerColor(0xFFFF0000);
                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        mBinding.etViaStart.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        viaBean.setBeginTime(year + "-" + month + "-" + day + " " + hour + ":" + minute+":00");
                    }
                });
                picker.show();
                break;
            case R.id.iv_endtime:
                DateTimePicker picker1 = new DateTimePicker(this, DateTimePicker.HOUR_24);
                Time tt = new Time();
                tt.setToNow();
                picker1.setDateRangeStart(tt.year, tt.month+1, tt.monthDay);
                picker1.setDateRangeEnd(2099, 11, 11);
                picker1.setTimeRangeStart(tt.hour, tt.minute);
                picker1.setTimeRangeEnd(23, 59);
                picker1.setTopLineColor(0x99FF0000);
//                picker.setLabelTextColor(0xFFFF0000);
                picker1.setDividerColor(0xFFFF0000);
                picker1.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        mBinding.etViaEnd.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        viaBean.setEndTime(year + "-" + month + "-" + day + " " + hour + ":" + minute+":00");
                    }
                });
                picker1.show();
                break;
            case R.id.btn_commit:
                try{
                    if (TextUtils.isEmpty(mBinding.etViaEnd.getText().toString().trim())||
                            TextUtils.isEmpty(mBinding.etViaStart.getText().toString().trim())){
                        toastMsg("请填写有效时间!");
                        return;
                    }
                PassFlag flag = new PassFlag();
                String  communityId =  ACache.get(this).getAsString(HttpConstants.COMMUNIYID);
                flag.setCommunityId(communityId);
                final String startTime = mBinding.etViaStart.getText().toString();
                final String endTime =  mBinding.etViaEnd.getText().toString();

                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd HH:mm");
                Date dt1 = sdf.parse(startTime);
                Date dt2 = sdf.parse(endTime);
                long begin = dt1.getTime();
                long end = dt2.getTime();

                flag.setBeginAt(""+begin);
                flag.setEndAt(""+end);
                flag.setItems(mBinding.etPersonalContent.getText().toString().trim());

                if(begin == end){
                    toastMsg("开始时间和结束时间不能相同!");
                    return;
                }else if(end<begin){
                    toastMsg("结束时间不能小于开始时间!");
                    return;
                }

                if(TextUtils.isEmpty(mBinding.etPersonalContent.getText().toString())){
                    toastMsg("请填写放行理由!");
                    return;
                }

                if(!AppInfo.isNetworkAvailable(getBaseContext())){
                    toastMsg("请检查网络设置!");
                    return;
                }


                    Api.rpass(flag, new ResponseCallBack<PassCode>() {
                        @Override
                        public void onSuccess(PassCode data) {
                            super.onSuccess(data);
                           String  id =  data.getId();

                            String erCode = "http://bit.cn/bit/"+1+"/"+1000+"/"+"no/"+"001"+"/para/"+"id/"+id;
                            final Bitmap bitmap = ScannerUtils.createQRImage(erCode, 800,800, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

                            Log.e("url id","ercode url:"+erCode);

                            mBinding.viaLayout1.setVisibility(View.GONE);
                            mBinding.viaLayout2.setVisibility(View.GONE);
                            mBinding.btnCommit.setVisibility(View.GONE);
                            mBinding.etViaErcodeLayout.setVisibility(View.VISIBLE);

                            mBinding.ivErcodeImageview.setImageBitmap(bitmap);

                            mBinding.ivErcodeTime.setText("有效期:"+startTime+" ~ "+endTime);
                            mBinding.ivSaveErcode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    saveImage(getBaseContext(),bitmap);
                                }
                            });

                        }

                        @Override
                        public void onFailure(ServiceException e) {
                            super.onFailure(e);
                        }
                    });

                }catch (Exception e){

                }


                break;
        }
    }

    public void saveImage(final Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "放行条二维码");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "放行条二维码.jpg";
        File file = new File(appDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "二维码已保存到手机相册", Toast.LENGTH_LONG).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, "PayCode");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
    }



}
