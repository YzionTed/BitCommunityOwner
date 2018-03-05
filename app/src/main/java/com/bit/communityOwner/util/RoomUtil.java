package com.BIT.communityOwner.util;

import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.utils.ACache;

/**
 * Created by zhangjiajie on 18/3/1.
 */

public class RoomUtil {

    public static boolean hasRoom(String communityId) {
        ACache aCache = ACache.get(BaseApplication.getInstance());
        String a = aCache.getAsString(communityId);
        if ("1".equals(a)) {
            return true;
        }
        return false;
    }

}
