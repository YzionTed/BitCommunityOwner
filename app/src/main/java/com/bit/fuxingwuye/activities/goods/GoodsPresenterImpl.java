package com.BIT.fuxingwuye.activities.goods;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.GoodsBean;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

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
