package com.BIT.fuxingwuye.base;

import android.app.Activity;
import android.os.Bundle;

import com.BIT.fuxingwuye.dagger.component.ActivityComponent;
import com.BIT.fuxingwuye.dagger.component.DaggerActivityComponent;
import com.BIT.fuxingwuye.dagger.module.ActivityModule;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * SmartCommunity-com.BIT.fuxingwuye.base
 * 作者： YanwuTang
 * 时间： 2017/6/30.
 */

public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    private Bundle state;

    @Inject
    protected T mPresenter;
    protected Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = savedInstanceState;
        try {
            initInject();
            if (mPresenter != null)
                mPresenter.attachView(this);
            BaseApplication.getInstance().addActivity(this);
            initEventAndData();
            providers();
            setupVM();
            setupHandlers();
            justAfterSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        BaseApplication.getInstance().removeActivity(this);
    }

    protected abstract void initEventAndData();

    protected abstract void initInject();

    /**
     * 添加依赖
     */
    protected void providers() {
    }

    /**
     * dataBind here
     */
    protected void setupBinding() {
    }

    /**
     * 设置 source (初始化数据)
     */
    protected void setupVM() {
    }

    /**
     * 设置layout 的事件 可以理解为view的交互
     */
    protected void setupHandlers() {
    }

    /**
     * 这里关联 handler 和 bind
     */
    protected void justAfterSetup() {
    }

    protected Bundle getSavedInstanceState() {
        return state;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(BaseApplication.getComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
