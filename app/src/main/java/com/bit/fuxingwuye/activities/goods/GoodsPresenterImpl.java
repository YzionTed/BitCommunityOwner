package com.bit.fuxingwuye.activities.goods;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.GoodsBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/11/1.
 * Created time:2017/11/1 16:24
 */

public class GoodsPresenterImpl extends BaseRxPresenter<GoodsContract.View> implements GoodsContract.Presenter {

    private Context context;

    @Inject
    public GoodsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getGoods(GoodsBean goodsBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getGoods(goodsBean)
                .map(new HttpResultFunc<List<GoodsBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<GoodsBean>>>() {
            @Override
            public void next(BaseEntity<List<GoodsBean>> o) {

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
