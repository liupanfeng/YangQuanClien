package com.meishe.yangquan.utils;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.meishe.yangquan.bean.FodderInfo;
import com.meishe.yangquan.pop.ShowBigPictureView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.content.Context.CLIPBOARD_SERVICE;

public class Util {
    private final static String TAG = "Util";
    private final static long TEN_THOUSAND = 10 * 1000;//一万
    private static final int AUTH_TYPE_COMMON_USER = 0;
    private static final int AUTH_TYPE_YELLOW_V = 1;
    private static final int AUTH_TYPE_BLUE_V = 2;
    private static final int AUTH_TYPE_SYSTEM_USER = 3;
    private static int prevNonce = 0;//记录一下前一个随机数值
    public static final int HANDLE_WHAT_SHARE = 1000;
    public static final int HANDLE_WHAT_GLASS = 2000;
    public static final int HANDLE_WHAT_GLASS_SHOW = 3000;
    public static final String SCENE_PATH = "personal/homepage";

    //剪切板名称
    private static final String CLIP_LABEL_NAME = "yang_quan";

    private static long lastClickTime2;


    public static boolean checkNull(String content) {
        if (TextUtils.isEmpty(content)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是快速双击
     *
     * @return 是否是快速双击
     */
    public static boolean isFastDoubleClick() {
        boolean flag = false;
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime2;
        if (0 < timeD && timeD < 800) {
            flag = true;
        }
        lastClickTime2 = time;
        return flag;
    }

    public static String getPercentValue( float similarity){
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);//最多两位百分小数，如25.23%
        return fmt.format(similarity);
    }


    /**
     * Return the activity by context.
     * 返回activity的上下文
     * @param context The context. 上下文
     * @return the activity by context. activity的上下文
     */
    public static Activity getActivityByContext(Context context) {
        Activity activity = getActivityByContextInner(context);
        if (!isActivityAlive(activity)) {
            return null;
        }
        return activity;
    }

    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }



    private static Activity getActivityByContextInner(Context context) {
        if (context == null) {
            return null;
        }
        List<Context> list = new ArrayList<>();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            Activity activity = getActivityFromDecorContext(context);
            if (activity != null) {
                return activity;
            }
            list.add(context);
            context = ((ContextWrapper) context).getBaseContext();
            if (context == null) {
                return null;
            }
            if (list.contains(context)) {
                // loop context
                return null;
            }
        }
        return null;
    }



    private static Activity getActivityFromDecorContext(Context context) {
        if (context == null) {
            return null;
        }
        if (context.getClass().getName().equals("com.android.internal.policy.DecorContext")) {
            try {
                Field mActivityContextField = context.getClass().getDeclaredField("mActivityContext");
                mActivityContextField.setAccessible(true);
                //noinspection ConstantConditions,unchecked
                return ((WeakReference<Activity>) mActivityContextField.get(context)).get();
            } catch (Exception ignore) {
            }
        }
        return null;
    }




    public static String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context,String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }


    public static void showBigPicture(Context context,String filePath){
        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        ShowBigPictureView showBigPictureView = ShowBigPictureView.create(context, filePath);
        if (showBigPictureView != null) {
            showBigPictureView.show();
        }
    }


    public static void setViewClickEffect(final View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.animate()
                    .translationZ(15f).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.animate()
                                    .translationZ(1.0f).setDuration(500);
                        }
                    }).start();
        }
    }

    public static String formatPhoneNumber(String phoneNumber){
        if(phoneNumber.length()!=11){
            return phoneNumber;
        }
        phoneNumber=phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7,11);
        return phoneNumber;
    }


    /**
     * 处理上传服务器内容中包含图片不能识别的问题
     *
     * @param content
     * @return
     */
    public static String encodeString(String content) {
        String str_count = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
        return str_count;
    }

    /**
     * 解码数据
     *
     * @param content
     * @return
     */
    public static String decodeString(String content) {
        byte[] bytes = Base64.decode(content, Base64.DEFAULT);
        return new String(bytes);
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return true;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return false;
            }
        }

        return true;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断Activity是否Destroy
     */
    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^[1][0-9]{10}$";
        //  String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8])|(16[0-9])|(19[8]))\\d{8}$";
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,
        // [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 兼容 Android N，Intent中不能使用 file:///*
     *
     * @param context
     * @param uri
     * @return
     */
    public static Uri getIntentUri(Context context, Uri uri) {
        //support android N+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getContentUri(context, uri);
        } else {
            return uri;
        }
    }

    public static Uri getContentUri(Context context, Uri fileUri) {
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".imagePicker.provider", new File(fileUri.getPath()));
    }

    /**
     * content uri to path
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    public static String getAgeByBirth(String birth) {
        Date birthDay = null;
        if (birth == null) {
            return "";
        }
        SimpleDateFormat format = null;
        if (birth.contains("/")) {
            format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        }
        try {
            birthDay = format.parse(birth);
        } catch (ParseException e) {
            Log.e(TAG, "fail to getAgeByBirth parse", e);
            return "";
        }
        Calendar cal = Calendar.getInstance();


        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    // do nothing
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        } else {
            // monthNow<monthBirth
            // donothing
        }
        return age + "";
    }

    public static String stringToMD5(String sourceStr) {
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(sourceStr.getBytes("utf-8"));
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < md.length; i++) {
                int tmp = md[i];
                if (tmp < 0)
                    tmp += 256;
                if (tmp < 16)
                    builder.append('0');
                builder.append(Integer.toHexString(tmp));
            }
            //return buf.toString().substring(8, 24);// 16位加密
            return builder.toString();

        } catch (Exception e) {
            Log.e(TAG, "fail to stringToMD5", e);
            return null;
        }
    }

    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getCurSysTimeStr() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getRandomStr() {
        SecureRandom secureRandom = new SecureRandom();
        int i = secureRandom.nextInt(Integer.MAX_VALUE);
        if (prevNonce == i) {
            i = secureRandom.nextInt(Integer.MAX_VALUE);
        }
        prevNonce = i;
        return String.valueOf(i);
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            Log.e(TAG, "fail to stringtoBitmap", e);
        }
        return bitmap;
    }


    /**
     * 这个是完整的格式
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static String bitmaptoString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String getDeviceId() {
        return "35" + "-" +//we make this look like a valid IMEI
                Build.BOARD.length() % 10 + "-" +
                Build.BRAND.length() % 10 + "-" +
                Build.CPU_ABI.length() % 10 + "-" +
                Build.DEVICE.length() % 10 + "-" +
                Build.DISPLAY.length() % 10 + "-" +
                Build.HOST.length() % 10 + "-" +
                Build.ID.length() % 10 + "-" +
                Build.MANUFACTURER.length() % 10 + "-" +
                Build.MODEL.length() % 10 + "-" +
                Build.PRODUCT.length() % 10 + "-" +
                Build.TAGS.length() % 10 + "-" +
                Build.TYPE.length() % 10 + "-" +
                Build.USER.length() % 10; //13 digits
    }

    public static String timeslashData(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        return sdr.format(new Date(time));
    }


    public static boolean checkSDCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean useLoopForCheckItemIsExist(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    public static String formatNumber(String strNumber) {
        long numValue = 0;
        try {
            numValue = Long.parseLong(strNumber);
        } catch (NumberFormatException e) {

        }
        if (numValue < 0) {
            numValue = 0;
        }
        float floatResult;
        String strResult;
        if ((numValue / 1000000000) > 0) {
            floatResult = (float) numValue / 1000000000;
            strResult = String.format("%.1f", floatResult) + "亿";
        } else if ((numValue / 10000) > 0) {
            floatResult = (float) numValue / 10000;
            strResult = String.format("%.1f", floatResult) + "W";
        } else {
            strResult = String.valueOf(numValue);
        }
        return strResult;
    }


    public static String getMethodName(int stackIndex) {
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        if (s.length > stackIndex && stackIndex >= 0) {
            return s[stackIndex].getMethodName();
        } else {
            return null;
        }
    }

    public static String converToKm(float distance) {
        float d = distance / 1000;
        if (d >= 1) {
            return String.format("%.1f", d) + "千米";
        } else {
            return String.format("%.1f", distance) + "米";
        }
    }


    public static ArrayList<String> getPermissionsList() {
        ArrayList<String> permissionsList = new ArrayList<>();
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.CAMERA);
        permissionsList.add(Manifest.permission.RECORD_AUDIO);
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionsList;
    }

    public static String getDownloadApkFile(String apkName) {
        return PathUtils.getAPKFilePath() + "/" + apkName;
    }

    //高亮显示字符串里包含的关键字列表的字符
    public static void setSearchKeyWordHighlight(TextView textView, List<String> keyWordList, String strContent) {
        if (textView == null) {
            return;
        }
        if (keyWordList == null || keyWordList.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(strContent)) {
            return;
        }
        SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append(strContent);
        for (String keyWord : keyWordList) {
            if (keyWord == null) {
                continue;
            }
            Pattern p = Pattern.compile(keyWord);//正则表达式，匹配搜索关键字
            Matcher matcher = p.matcher(strContent);
            if ((matcher != null) && matcher.find()) {
                int startIndex = matcher.start();
                int endIndex = matcher.end();
                //设置部分文字颜色
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#ffffe400"));
                style.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        textView.setText(style);
    }

    //格式化
    public static String formatLongData(long totalNum) {
        if (totalNum < 0) {
            totalNum = 0;
        }
        StringBuilder strPlayNum = new StringBuilder();
        if (totalNum < TEN_THOUSAND) {//一万次
            strPlayNum.append(totalNum);
        } else {
            float tmpNum = (float) totalNum / TEN_THOUSAND;
            String calPlayNum = String.format("%.1f", tmpNum);//小数点后保留一位
            strPlayNum.append(calPlayNum);
            strPlayNum.append('w');
        }
        return strPlayNum.toString();
    }


    public static String formatUTC(long time, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strPattern, Locale.CHINA);
        return simpleDateFormat.format(time);
    }


    //将要存为图片的view传进来 生成bitmap对象
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    //使用IO流将bitmap对象存到本地指定文件夹
    public static void saveMyBitmap(final String bitName, final Bitmap bitmap, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filePath = Environment.getExternalStorageDirectory().getPath();
                FileOutputStream outputStream = null;
                File file = new File(filePath + "/DCIM/Camera/" + bitName + ".png");
                try {
                    if (file.createNewFile()) {
                        outputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        Message msg = Message.obtain();
                        msg.obj = file.getPath();
                        msg.what = HANDLE_WHAT_SHARE;
                        handler.sendMessage(msg);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "fail to saveMyBitmap", e);
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "fail to saveMyBitmap", e);
                    }
                }
            }
        }).start();
    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes("utf-8"));
//            String result = "";
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
//                result += temp;
                result.append(temp);
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            Log.e(TAG, "fail to md5", e);
        }
        return "";
    }


    public static String encodeChineseOfUrl(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append('%').append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }


    /**
     * 跳转到微信
     */
    public static void openWechat(Context context) {
        if (context == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToastCenterNoBg(context, "您还没有安装微信!");
        }
    }


    /**
     * 打开微博客户端
     */
    public static void openWeibo(Context context) {
        if (context == null) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("sinaweibo://splash"));
            context.startActivity(intent);
//          forwardVideo(String.valueOf(item.getSuggestVideoItem().getVideoId()), String.valueOf(type));
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToastCenterNoBg(context, "您还没有安装微博!");
        }
    }


    public static void serilizeData(Object obj, String path) {
        String filePath = path + File.separator + obj.getClass().getSimpleName() + ".txt";
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(
                            new File(filePath)
                    )
            );
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "fail to serilizeData", e);
        }

    }

    public static Object reverseSerilizeData(String path) {
        Object obj = null;
        String filePath = path + ".txt";
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(
                            new File(filePath)
                    )
            );
            obj = objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, "fail to reverseSerilizeData", e);
        }
        return obj;
    }

    public static int getScreenWidth(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            manager.getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            manager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            manager.getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            manager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    public static void refreshPictureToPhoto(Context context, String fileName) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.parse("file://" + fileName));
        context.sendBroadcast(intent);
    }

    /**
     * 拷贝文本到剪贴板
     */
    public static void copyClipData(Context context, String clipContent) {
        if (context == null) {
            return;
        }
        //获取剪贴板管理器
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            // 创建普通字符型ClipData
            ClipData clipData = ClipData.newPlainText(CLIP_LABEL_NAME, clipContent);
            ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
            // 将ClipData内容放到系统剪贴板里。
            clipboardManager.setPrimaryClip(clipData);

        }
    }


    /**
     * float 保留两位小数
     * @param value
     * @return
     */
    public static String float2StringTwoPosition(float value){
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(value);
        return format;
    }

    /**
     * 获取剪贴板内容
     */
    public static String getClipData(Context context) {
        if (context == null) {
            return "";
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
            if (clipDescription != null) {
                CharSequence clipLabelName = clipDescription.getLabel();
                if (!TextUtils.isEmpty(clipLabelName) && CLIP_LABEL_NAME.equals(clipLabelName)) {
                    //如果剪切板名称不为空，且与CLIP_LABEL_NAME相同,则认为是从当前App复制的内容，页面不作跳转
                    return "";
                }
            }
            String clipContent = "";
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null) {
                ClipData.Item item = clipData.getItemAt(0);
                if (item != null) {
                    CharSequence charSequence = item.getText();
                    if (!TextUtils.isEmpty(charSequence)) {
                        clipContent = charSequence.toString();
                    }
                }
            }
            String clipContentResult;
            //获取剪切版中的口令
            if (!TextUtils.isEmpty(clipContent)
                    && clipContent.contains("<$")
                    && clipContent.contains("$>")) {
                clipContentResult = clipContent.substring(clipContent.indexOf("<$") + 2, clipContent.indexOf("$>"));
            } else {
                clipContentResult = clipContent;
            }
            return clipContentResult;
        }
        return "";
    }

    /**
     * 清空剪贴板内容
     */
    public static void clearClipboard(Context context) {
        if (context == null) {
            return;
        }
        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setText(null);
            } catch (Exception e) {
                Log.d(TAG, "fail to clear clipboard:" + e.getMessage());
            }
        }
    }


    /**
     * 获取所有权限列表(相机权限，麦克风权限，存储权限) 动态申请的权限内容
     *
     * @return
     */
    public static List<String> getAllPermissionsList() {
//        ArrayList<String> newList = new ArrayList<>();
//        newList.add(Manifest.permission.CAMERA);
//        newList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        newList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ArrayList<String> permissionsList = new ArrayList<>();
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.CAMERA);
//        permissionsList.add(Manifest.permission.RECORD_AUDIO);
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionsList;
    }


}
