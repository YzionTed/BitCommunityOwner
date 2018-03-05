package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * KangTuUpperComputer-com.kangtu.uppercomputer.utils
 * 作者： YanwuTang
 * 时间： 2016/9/22.
 */
public class FileUtil {

    // 检查文件存在性
    public static boolean isFileExist(String fileName) {

        if (TextUtils.isEmpty(fileName))
            return false;

        String myFileName = fileName;
        File file = new File(myFileName);
        return file.exists();
    }

    // 新建文件
    public static String createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            System.out.println("新建文件操作出错");
            Log.e("FileUtil", "新建文件操作出错");
            e.printStackTrace();
            Log.e("FileUtil", "exception message:" + e.getMessage());
        }
        return null;
    }

    /**
     * 写数据
     * @param context
     * @param fileName
     * @param writestr
     */
    public static void writeFile(Context context, String fileName, String writestr){
        try {
            if (writestr == null || TextUtils.isEmpty(writestr)){
                return;
            }
            File file = new File(fileName);
            if (!file.exists()){
                Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
                return;
            }
            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "写入文件失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 写数据
     * @param context
     * @param fileName
     * @param writestr
     */
    public static void writeFile(Context context, String fileName, byte[] writestr){
        try {
            if (writestr == null){
                return;
            }
            File file = new File(fileName);
            if (!file.exists()){
                Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
                return;
            }
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(writestr);
            fout.close();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "写入文件失败！", Toast.LENGTH_SHORT).show();
        }
    }

    //写数据
    public static void writeFile(Context context, String fileName, List<String> writestr){
        if (writestr == null) return;
        for (int i = 0; i < writestr.size(); i++) {
            try {
                if (writestr.get(i) == null || TextUtils.isEmpty(writestr.get(i))) {
                    return;
                }
                File file = new File(fileName);
                if (!file.exists()) {
                    Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
                    return;
                }
                FileOutputStream fout = new FileOutputStream(file, true);
                byte[] bytes = (writestr.get(i) + "\n").getBytes();
                fout.write(bytes);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "写入文件失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //读数据
    public static String readFile(Context context, String fileName){
        String res = "";
        try{
            File file = new File(fileName);
            if (!file.exists()){
                Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
                return res;
            }

            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "UTF-8");
            fin.close();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "读取文件失败！", Toast.LENGTH_SHORT).show();
        }
        return res;
    }
    //读数据
    public static byte[] readBytesFile(Context context, String fileName){
        byte [] buffer = null;
        try{
            File file = new File(fileName);
            if (!file.exists()){
                Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
                return null;
            }

            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);
//            String res = HexUtil.bytesToStr(buffer);
//            LogUtil.d("readBytesFile", res);
            fin.close();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "读取文件失败！", Toast.LENGTH_SHORT).show();
        }
        return buffer;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files){
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for(File file:files){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("图片", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
