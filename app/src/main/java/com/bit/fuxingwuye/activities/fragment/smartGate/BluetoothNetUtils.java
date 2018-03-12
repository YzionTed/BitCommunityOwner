package com.bit.fuxingwuye.activities.fragment.smartGate;

/**
 * Created by Dell on 2018/3/8.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.activities.fragment.elevatorFrag.StoreElevatorListBeans;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.DoorMiLiRequestBean;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.bean.ElevatorListRequestion;
import com.bit.fuxingwuye.bean.StoreDoorMILiBeanList;
import com.bit.fuxingwuye.constant.PreferenceConst;
import com.bit.fuxingwuye.utils.PreferenceUtils;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * 米粒蓝牙保存网络数据
 */
public class BluetoothNetUtils {


    private static final String TAG = "BluetoothNetUtils";


    /**
     * type==1 代表的是需要谈提示 type==2不需要提示
     *
     * @param doorMacArr
     * @param type
     * @param onBlutoothDoorCallBackListener
     */
    public void getMiLiNetDate(final String[] doorMacArr, final int type, final OnBlutoothDoorCallBackListener onBlutoothDoorCallBackListener) {

        if (!isNetworkAvailable(BaseApplication.getInstance())) {
            if (type == 1) {
                ToastUtil.showShort("连接异常，请检查网络");
            }
            return;
        }
        DoorMiLiRequestBean doorMiLiRequestBean = new DoorMiLiRequestBean();
        Community.RecordsBean recordsBean = BaseApplication.getVillageInfo();
        if (recordsBean != null) {
            doorMiLiRequestBean.setCommunityId(recordsBean.getId());
        } else {
            ToastUtil.showShort("您还未选择小区");
            return;
        }
//        if (doorMacArr != null) {
//            doorMiLiRequestBean.setDoorMacArr(doorMacArr);
//        }
        Api.getDoorDate(doorMiLiRequestBean, new ResponseCallBack<List<DoorMILiBean>>() {
            @Override
            public void onSuccess(List<DoorMILiBean> doorMILiBeans) {
                super.onSuccess(doorMILiBeans);
                Log.e(TAG, "doorMILiBeans==" + doorMILiBeans);
                if (doorMILiBeans != null) {

                    if (doorMILiBeans.size() > 0) {
                        StoreDoorMILiBeanList storeDoorMILiBeanList = new StoreDoorMILiBeanList();
                        storeDoorMILiBeanList.setDoorMILiBeans(doorMILiBeans);
                        storeDoorMILiBeanList.setStoreTime(new Date().getTime());
                        if (doorMacArr == null) {
                            PreferenceUtils.setPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() + PreferenceConst.MILIDOORMAC, new Gson().toJson(storeDoorMILiBeanList));
                        }
                        if (onBlutoothDoorCallBackListener != null) {
                            onBlutoothDoorCallBackListener.OnCallBack(1, storeDoorMILiBeanList);
                        }
                    } else {
                        if (type == 1) {
                            if (onBlutoothDoorCallBackListener != null) {
                                onBlutoothDoorCallBackListener.OnCallBack(2, null);
                            }
                            ToastUtil.showShort("您还没有可以开锁的设备");
                        }
                    }
                } else {
                    if (type == 1) {
                        if (onBlutoothDoorCallBackListener != null) {
                            onBlutoothDoorCallBackListener.OnCallBack(2, null);
                        }
                        ToastUtil.showShort("您还没有可以开锁的设备");
                    }
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if (onBlutoothDoorCallBackListener != null) {
                    if (type == 1) {
                        ToastUtil.showShort(e.getMsg());
                    }
                    onBlutoothDoorCallBackListener.OnCallBack(2, null);
                }
            }
        });
    }


    /**
     * 获取电梯的蓝牙数据
     * <p>
     * type==1 代表的是需要谈提示 type==2不需要提示
     */
    public void getBluetoothElevatorDate(final int type, final OnBlutoothElevatorCallBackListener onBlutoothDoorCallBackListener) {
        ElevatorListRequestion elevatorListRequestion = new ElevatorListRequestion();
        elevatorListRequestion.setUserId(BaseApplication.getUserLoginInfo().getId());
        Community.RecordsBean recordsBean = BaseApplication.getVillageInfo();
        if (recordsBean != null) {
            elevatorListRequestion.setCommunityId(recordsBean.getId());
        } else {
            ToastUtil.showShort("您还未选择小区");
            return;
        }

        Api.lanyaElevatorLists(elevatorListRequestion, new ResponseCallBack<List<ElevatorListBean>>() {
            @Override
            public void onSuccess(List<ElevatorListBean> elevatorListBeans) {
                super.onSuccess(elevatorListBeans);

                if (elevatorListBeans != null) {
                    if (elevatorListBeans.size() > 0) {
                        StoreElevatorListBeans storeElevatorListBeans = new StoreElevatorListBeans();
                        storeElevatorListBeans.setElevatorListBeans(elevatorListBeans);
                        storeElevatorListBeans.setStoreTime(new Date().getTime());

                        PreferenceUtils.setPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() + PreferenceConst.BLUETOOTHELEVATOR, new Gson().toJson(storeElevatorListBeans));
                        if (onBlutoothDoorCallBackListener != null) {
                            onBlutoothDoorCallBackListener.OnCallBack(1, storeElevatorListBeans);
                        }
                    } else {
                        if (type == 1) {
                            if (onBlutoothDoorCallBackListener != null) {
                                onBlutoothDoorCallBackListener.OnCallBack(2, null);
                            }
                            ToastUtil.showShort("没有找到您可以开的电梯");
                        }
                    }
                }
                if (type == 1) {
                    if (onBlutoothDoorCallBackListener != null) {
                        onBlutoothDoorCallBackListener.OnCallBack(2, null);
                    }
                    ToastUtil.showShort("没有找到您可以开的电梯");
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if (onBlutoothDoorCallBackListener != null) {
                    ToastUtil.showShort(e.getMsg());
                    onBlutoothDoorCallBackListener.OnCallBack(2, null);
                }
            }
        });
    }


    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }


    /**
     * 获取米粒门蓝牙的数据
     */
    public StoreDoorMILiBeanList getBletoothDoorDate() {
        String prefString = PreferenceUtils.getPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() + PreferenceConst.MILIDOORMAC, "");
        StoreDoorMILiBeanList storeDoorMILiBeanList = null;
        try {
            if (prefString != null && prefString.trim().length() > 0) {
                storeDoorMILiBeanList = new Gson().fromJson(prefString, StoreDoorMILiBeanList.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "StoreElevatorListBeans的Gson转换对象出问题");
        }
        return storeDoorMILiBeanList;
    }

    /**
     * 获取电梯蓝牙的数据
     */
    public StoreElevatorListBeans getBletoothElevateDate() {
        String prefString = PreferenceUtils.getPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() + PreferenceConst.BLUETOOTHELEVATOR, "");
        StoreElevatorListBeans storeElevatorListBeans = null;
        try {
            if (prefString != null && prefString.trim().length() > 0) {
                storeElevatorListBeans = new Gson().fromJson(prefString, StoreElevatorListBeans.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "StoreElevatorListBeans的Gson转换对象出问题");
        }
        return storeElevatorListBeans;
    }


    public interface OnBlutoothDoorCallBackListener {
        //state 1:表示成功 2：表示失败
        void OnCallBack(int state, StoreDoorMILiBeanList storeDoorMILiBeanList);
    }

    public interface OnBlutoothElevatorCallBackListener {
        //state 1:表示成功 2：表示失败
        void OnCallBack(int state, StoreElevatorListBeans storeDoorMILiBeanList);
    }


}
