package com.bit.fuxingwuye.http;

import com.bit.fuxingwuye.constant.HttpConstants;

/**
 * SmartCommunity-com.bit.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/1.
 */

public class HttpProvider {

    /**
     * get ip adds
     * @return
     */
    public static String getHttpIpAdds() {
        if (HttpConstants.isFormalEnvironment) {
            return HttpConstants.Base_Url_Formal;
        } else {
            return HttpConstants.Base_Url_Test;
        }
    }
}
