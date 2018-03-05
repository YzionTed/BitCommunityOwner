package com.bit.communityOwner.util;

import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.utils.ACache;

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
