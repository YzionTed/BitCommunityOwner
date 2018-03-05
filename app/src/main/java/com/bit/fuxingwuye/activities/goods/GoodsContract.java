package com.bit.fuxingwuye.activities.goods;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.GoodsBean;

/**
 * Created by Dell on 2017/11/1.
 * Created time:2017/11/1 16:24
 */

public class GoodsContract {

    public interface View extends BaseView{
        void showGoods();
    }

    public interface Presenter extends BasePresenter<View>{
        void getGoods(GoodsBean goodsBean);
    }
}
