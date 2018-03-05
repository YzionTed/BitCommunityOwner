package com.bit.fuxingwuye.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 需要简单全部导出：
 * 用Gson.
 * <p/>
 * 用GsonBuilder:
 * 有时候我们不需要把实体的所有属性都导出,只想把一部分属性导出为Json.
 * 有时候我们的实体类会随着版本的升级而修改.
 * 有时候我们想对输出的json默认排好格式.
 *
 * @author YanwuTang
 */
public class GsonUtil {

    /**
     * 无论有没有@expose，都转换
     * 列表对象就用： new TypeToken<List<Student>>() {}.getType()
     *
     * @param responseStr
     * @return
     */
    public static Object toObject(String responseStr, Type type) {
        // 从JSON串转成JAVA对象
        Gson gson = new Gson();
        return gson.fromJson(responseStr, type);
    }

    /**
     * 无论有没有@expose，都转换
     * 根据对象转换成JSON字符串
     *
     * @param obj 对象
     * @return
     */
    public static String toJson(Object obj) {

        //将JAVA对象转成JSON串
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 只有@expose才转换
     *
     * @param responseStr
     * @param clz
     * @return
     */
    public static Object getBeanFromJsonString(String responseStr, Class clz) {
        // 从JSON串转成JAVA对象
        Gson gson = initialGson();
        return gson.fromJson(responseStr, clz);
    }

    /**
     * 只有@expose才转换
     * 根据对象转换成JSON字符串
     *
     * @param obj 对象
     * @return
     */
    public static String formatBeanToJson(Object obj) {

        //将JAVA对象转成JSON串
        Gson gson = initialGson();
        return gson.toJson(obj);
    }

    private static Gson initialGson() {
        // 转换器
        GsonBuilder builder = new GsonBuilder();

        // 不转换没有 @Expose 注解的字段
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        builder = null;
        return gson;
    }


}
