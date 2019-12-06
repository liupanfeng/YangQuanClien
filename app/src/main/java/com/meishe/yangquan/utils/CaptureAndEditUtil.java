package com.meishe.yangquan.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;



import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CaptureAndEditUtil {
    private static final String TAG = CaptureAndEditUtil.class.getSimpleName();
    public static final int TIME_BASE = 1000000;
    public static final int REQUEST_FAILED = -101;
    private static long lastClickTime2;

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                Log.e(TAG,"fail to close" ,e);
            }
        }
    }

    /**
     *   删除文件
     * @param path 需要删除的文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.delete();
    }

    /**
     *  是否是快速双击
     * @return 是否是快速双击
     */
    public static boolean isFastDoubleClick() {
        boolean flag = false;
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime2;
        if ( 0 < timeD && timeD < 800) {
            flag = true;
        }
        lastClickTime2 = time;
        return flag;
    }


    /**
     *   获取当前时间，转换成字符串 格式：yyyyMMddHHmmss" 年月日小时分钟秒
     * @return
     */
    public static String getCharacterAndNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     *  将bitmap 图片转换成字符串
     * @param bitmap  需要转换的bitmap图片
     * @return  返回转换后的字符串
     */
    public static String convertIconToString(Bitmap bitmap) {
        if(bitmap == null){
            return "";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     *  将dp值转换成px值
     * @param context  context
     * @param dpValue  dp值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        if(context == null){
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *  获取屏幕宽度
     * @param context  context
     * @return  屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if(context == null){
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     *  获取屏幕高度
     * @param context  context
     * @return  屏幕高度
     */

    public static int getScreenHeight(Context context) {
        if(context == null){
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     *   格式化时间 毫秒
     * @param time  需要格式化的时间
     * @return  格式化的字符串
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     *   将bitmap转换成指定宽高的bitmap
     * @param bitmap  需要转换的bitmap图片
     * @param width  新的图片宽度
     * @param height 新的图片高度
     * @return  转换成功后的bitmap
     */
    public static Bitmap getScaledBitmap(Bitmap bitmap, int width, int height) {
        Bitmap newBitmap = null;
        if (bitmap == null)
            return null;
        newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        if (newBitmap.getWidth() != width || newBitmap.getHeight() != height) {
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return newBitmap;
    }

    /**
     *    判断activity是否处于后台
     * @param context
     * @return  true 表示处于后台  false 表示处于前天
     */
    public static boolean isBackground(Context context) {
        if(context == null){
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     *   将us格式化字符串  格式：00:00:00
     * @param us  微妙值
     * @return  格式化的字符串
     */
    public static String formatTimeStrWithUs(long us) {
        int second = (int) Math.floor(us / 1000000.0 + 0.5D);
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String timeStr;
        if (us == 0) {
            timeStr = "00:00";
        }
        if (hh > 0) {
            timeStr = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            timeStr = String.format("%02d:%02d", mm, ss);
        }
        return timeStr;
    }

    /**
     *  将bitmap保存到SD卡
     */
    public static boolean saveBitmapToSD(Bitmap bt, File destFile) {
        if (destFile.exists()) {
            boolean isDelete = destFile.delete();
            if (!isDelete) {
                Log.d(TAG,"fail to delete file");
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destFile);
            bt.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            Log.d(TAG,"fail to find saveBitmapToSD file" ,e);
        } catch (IOException e) {
            Log.d(TAG,"fail to saveBitmapToSD" ,e);
        }finally {
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (IOException e){
            }
        }
        return true;
    }

    /**
     *   将bitmap 保存到本地文件中
     * @param bitmap  需要保存的bitmap
     * @param destFile  目标文件
     * @param quality  质量
     * @return  是否保存成功
     */
    public static boolean copyToFile(Bitmap bitmap, File destFile, int quality) {
        return copyToFile(bitmap, destFile, Bitmap.CompressFormat.PNG, quality);
    }

    public static boolean copyToFile(Bitmap bitmap, File destFile, Bitmap.CompressFormat format, int quality) {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        try {
            if (destFile.exists()) {
                boolean isDelete = destFile.delete();
                if (!isDelete) {
                    Log.d(TAG,"fail to delete file");
                }
            }
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                bitmap.compress(format, quality, out);
            } finally {
                out.flush();
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     *   判断文件是否存在
     * @param filePath  需要判断的文件名
     * @return  是否存在
     */
    public static boolean fileExist(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists())
            return true;
        return false;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = (float) height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = (float)width / 2;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    /**
     *   获取文件大小
     * @param file  需要读取的文件名
     * @return  返回文件大小
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        }
        return size;
    }

    /**
     *  获取滤镜特效中文名
     * @param name  滤镜特效名称与id
     * @return 特效中文名
     */
    public static String getFxName(String name) {
        if ("None".equals(name)) {
            return "原图";
        }
        if ("Sage".equals(name)) {
            return "明快";
        }
        if ("Maid".equals(name)) {
            return "少女时代";
        }
        if ("Mace".equals(name)) {
            return "锐利";
        }
        if ("Lace".equals(name)) {
            return "蕾丝";
        }
        if ("Mall".equals(name)) {
            return "时尚";
        }
        if ("Sap".equals(name)) {
            return "元气";
        }
        if ("Sara".equals(name)) {
            return "调皮";
        }
        if ("Pinky".equals(name)) {
            return "草莓薄荷";
        }
        if ("Sweet".equals(name)) {
            return "粉嫩";
        }
        if ("Fresh".equals(name)) {
            return "清爽";
        }
        return "原图";
    }





}
