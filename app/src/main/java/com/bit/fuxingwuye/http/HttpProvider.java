package com.BIT.fuxingwuye.http;

import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.LogUtil;

/**
 * SmartCommunity-com.BIT.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/1.
 */

public class HttpProvider {

    /**
     * get ip adds
     * @return
     */
    public static String getHttpIpAdds() {
        if (LogUtil.D) {
            return HttpConstants.HTTP + HttpConstants.HTTP_IP_ADDS_TEST + HttpConstants.PORT;
        } else {
            return HttpConstants.HTTP + HttpConstants.HTTP_IP_ADDS + HttpConstants.TPORT;
        }
    }
}
