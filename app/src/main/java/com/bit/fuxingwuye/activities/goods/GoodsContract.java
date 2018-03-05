package com.BIT.fuxingwuye.activities.goods;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.GoodsBean;

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
