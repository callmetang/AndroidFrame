package com.frame.framelibrary.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tangdehao on 2016/1/6.
 */
public class FileUtils {


    public static final String FILE_NAME_HAITAO = "haitao";
    public static final String FILE_NAME_HAITAO_CHCHE = "/haitao/cache/emoji/";
    public static final String FILE_NAME_HAITAO_IMAGES = "haitao/images";
    public static final String FILE_NAME_HAITAO_VIDEOS = "haitao/videos";
    public static final String FILE_NAME_HAITAO_EMOJI = "haitao/emoji";

    public static String getImagePath(Context context) {
//        return getSDPath(context) + System.currentTimeMillis() + ".jpg";
        return getSDPath(context, FILE_NAME_HAITAO_IMAGES) + System.currentTimeMillis() + ".png";
    }

    public static String getVideoPath(Context context) {
//        return getSDPath(context) + System.currentTimeMillis() + ".mp4";
        return getSDPath(context, FILE_NAME_HAITAO_VIDEOS) + System.currentTimeMillis() + ".mp4";
    }
    public static String getImageCachePath(Context context) {
        return getSDCachePath(context) + System.currentTimeMillis() + ".png";
    }
    /**
     * 获取SDK路径
     *
     * @return
     */
    public static String getSDPath(Context context) {
        File file = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            File dir = Environment.getExternalStorageDirectory();//获取跟目录
            file = new File(dir, FILE_NAME_HAITAO);//文件目录
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            file = context.getCacheDir();
        }
        return file.getAbsolutePath() + "/";

    }

    /**
     * 获取SDK路径
     *
     * @param context
     * @param dirs    文件路径  FILE_NAME_HAITAO / FILE_NAME_HAITAO_IMAGES /FILE_NAME_HAITAO_VIDEOS
     * @return
     */
    public static String getSDPath(Context context, String dirs) {
        File file = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            File dir = Environment.getExternalStorageDirectory();//获取跟目录
            file = new File(dir, dirs);//文件目录
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            file = context.getCacheDir();
        }
        return file.getAbsolutePath() + "/";

    }
    /**
     * 获取SDK路径 SDCard/Android/data/你的应用包名/cache/目录
     * @param context
     * @return
     */
    public static String getSDCachePath(Context context) {
        File file = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            file = getExternalCacheDir(context);//获取跟目录
        } else {
            file = context.getCacheDir();
        }
        return file.getAbsolutePath() + "/";

    }
    public static String getMimeType(File file) {
        String type = null;
        URL u = null;
        try {
            u = new URL(file.getAbsolutePath());
            URLConnection uc = null;
            uc = u.openConnection();
            type = uc.getContentType();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return type;
    }


    /**
     * 该方法返回/data/data/youPackageName/files的File对象
     *
     * @param context
     * @return
     */
    public static File getFilesDir(Context context) {

        File file = new File(context.getFilesDir(), FILE_NAME_HAITAO);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * 该方法返回/data/data/youPackageName/cache的File对象。
     *
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {

        File file = new File(context.getCacheDir(), FILE_NAME_HAITAO);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * 获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * 目录对应 设置->应用->应用详情里面的”清除缓存“选项
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {

        File file = new File(context.getExternalCacheDir(), FILE_NAME_HAITAO);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getExternalCacheDir(Context context, String fileName) {

        File file = new File(context.getExternalCacheDir(), fileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * 目录对应 设置->应用->应用详情里面的”清除数据“选项
     *
     * @param context
     * @return
     */
    public static File getExternalFilesDir(Context context) {
        File file = context.getExternalFilesDir(FILE_NAME_HAITAO);
        return file;
    }


    public static Uri getUri(File file, Context context) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                String authority = context.getApplicationInfo().packageName + ".provider";
                return FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
            } else {
                return Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static Uri getUriForFile(Context context, File file) {
//        if (context == null || file == null) {
//            throw new NullPointerException();
//        }
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= 24) {
//            uri = FileProvider.getUriForFile(context.getApplicationContext(),  AppUtils.getPackageName()+".fileProvider", file);
//        } else {
//            uri = Uri.fromFile(file);
//        }
//        return uri;
//    }


    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }


}
