package com.bit.communityOwner.util;

/**
 * Created by DELL60 on 2018/3/8.
 */

public class StringUtils {

    /**
     * 获取文件所在的bucket
     * @param str
     * @return
     */
    public static String getBucket(String str){
        try {
            if (str!=null){
                String[] sResult = str.split("_");
                if (sResult!=null&&sResult.length>=3){
                    return sResult[1];
                }
            }
        }catch (Exception e){

        }
        return "bit-app";
    }
}
