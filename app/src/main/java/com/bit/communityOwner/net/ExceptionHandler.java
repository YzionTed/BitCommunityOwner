package com.bit.communityOwner.net;

import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.utils.ACache;

/**
 * Created by zhangjiajie on 18/3/5.
 */

public class ExceptionHandler {
    public static final int CODE_TOKEN_INVALID = 9050001;

    public static void handle(ServiceException serviceException) {
        if (serviceException.getCode() == CODE_TOKEN_INVALID) {
            BaseApplication.finishAllActivity();
            ACache.get(BaseApplication.getInstance()).clear();
            BaseApplication.getInstance().startActivity(LoginActivity.createIntent(BaseApplication.getInstance()));
        }

    }

}
