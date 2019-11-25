package com.meishe.yangquan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedPreferencesUtil {
    
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();
    private static SharedPreferencesUtil intance;
    private  SharedPreferences sp;
    private SharedPreferencesUtil(Context context) {
        if(context != null){
            this.sp = context.getSharedPreferences("yangquan", Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferencesUtil getInstance(Context context){
        if (intance==null){
            synchronized (SharedPreferencesUtil.class){
                if (intance==null){
                    intance=new SharedPreferencesUtil(context);
                }
            }
        }
        return intance;
    }



    public void putString(String key,
                          String value) {
        if(sp == null){
            return;
        }
        if (!sp.contains(key) || !sp.getString(key, "None").equals(value)) {
            SharedPreferences.Editor e = sp.edit();
            e.putString(key, value);
            e.apply();
        }
    }

    public void putBoolean(String key,
                           boolean value) {
        if(sp == null){
            return;
        }
        if (!sp.contains(key) || sp.getBoolean(key, false) != value) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean(key, value);
            e.apply();
        }
    }

    public void putInt(String key, int value) {
        if(sp == null){
            return;
        }
        if (!sp.contains(key) || sp.getInt(key, Integer.MIN_VALUE) != value) {
            SharedPreferences.Editor e = sp.edit();
            e.putInt(key, value);
            e.apply();
        }
    }

    public void putLong(String key,
                        long value) {
        if(sp == null){
            return;
        }
        if (!sp.contains(key) || sp.getLong(key, Long.MIN_VALUE) != value) {
            SharedPreferences.Editor e = sp.edit();
            e.putLong(key, value);
            e.apply();
        }
    }

    public void putFloat(String key,
                         float value) {
        if(sp == null){
            return;
        }
        if (!sp.contains(key) || sp.getFloat(key, Float.MIN_VALUE) != value) {
            SharedPreferences.Editor e = sp.edit();
            e.putFloat(key, value);
            e.apply();
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        if(sp == null){
            return false;
        }
        return sp.getBoolean(key, defValue);
    }

    public String getString(String key) {
        if(sp == null){
            return "";
        }
        return sp.getString(key, "");
    }

    //摄像头
    public int getInt(String key) {
        if(sp == null){
            return 1;
        }
        return sp.getInt(key, 1);
    }

    public long getLong(String key) {
        if(sp == null){
            return 1;
        }
        return sp.getLong(key, 0);
    }
    public void removeKey(String... key) {
        if(sp == null){
            return;
        }
        for (String s : key) {
            SharedPreferences.Editor e = sp.edit();
            e.remove(s);
            e.apply();
        }

    }

    public boolean containKeys(String... key) {
        if(sp == null){
            return false;
        }
        for (String s : key) {
            if (sp.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean setObjectToShare(Context context, Object object, String key) {
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(context);
        if (object == null) {
            SharedPreferences.Editor editor = share.edit().remove(key);
            return editor.commit();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            Log.e(TAG,"fail to setObjectToShare writeObject" ,e);
            return false;
        }
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        try {
            baos.close();
            oos.close();
        } catch (IOException e) {
            Log.e(TAG,"fail to setObjectToShare close" ,e);
        }
        SharedPreferences.Editor editor = share.edit();
        // 将编码后的字符串写到base64.xml文件中
        String objectStr = String.valueOf(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        editor.putString(key,objectStr );
        return editor.commit();
    }

    public static Object getObjectFromShare(Context context, String key) {
        SharedPreferences sharePre = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            String wordBase64 = sharePre.getString(key, "");
            // 将base64格式字符串还原成byte数组
            if (TextUtils.isEmpty(wordBase64)) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes("utf-8"),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            // 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            Log.e(TAG,"fail to getObjectFromShare" ,e);
        }
        return null;
    }
}
