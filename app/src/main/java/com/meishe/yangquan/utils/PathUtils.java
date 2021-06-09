package com.meishe.yangquan.utils;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.meishe.yangquan.App;
import com.meishe.yangquan.bean.User;

import java.io.File;

public class PathUtils {
    private static final String TAG = "PathUtils";

    public static final String topicTitleJson = "topicTitle.json";
    public static final String searchHistoryJson = "searchHistory.json";
    public static final String searchMusicHistoryJson = "searchMusicHistory.json";

    public static final String ExternalCacheDir = App.getInstance().getApplicationContext().getExternalCacheDir()+ File.separator;
    //private static final String ExternalFilesDirectory = App.getInstance().getApplicationContext().getExternalFilesDir(null)+ File.separator;
    private static final String ExternalDirectory = Environment.getExternalStorageDirectory()+ File.separator;
    private static final String rootDir = "yangquan" +  File.separator;
    private static final String ExtralDataPath =  rootDir + "cover" + File.separator;

    private static final String APK_DIRECTORY = rootDir + "yangquan_apk";
    private static final String Compile_ForUpload = rootDir + "compile" + File.separator;
    private static final String LOCAL_TOPIC_DIR = "localTopic" + File.separator;
    //相册视频路径
    private static final String ALBUM_VIDEO_DIR = Environment.DIRECTORY_DCIM + File.separator + "Camera"+ File.separator + rootDir;

    private static final String SHARE_VIDEO_DOWNLOAD_DIR = "videoShare" + File.separator;
    private static final String AD_MEDIA_DOWNLOAD_DIR = "mediaAD" + File.separator;
    private static final String SERI_DIR = "seri" + File.separator;

    public static String getPersonalCoverImageDirectory() {
        return getFolderDirectory(ExternalCacheDir,ExtralDataPath);
    }

    public static String getTempCoverImageName(Context context) {
//        CommonData commonData = CommonData.getInstance();
//        UserInfo userInfo = commonData.getUserInfo();
        User user=null;
        if (user == null) {
            return null;
        }
        long userId = user.getUserId();
        return "temp" + userId + ".png";

    }


    public static String getCompileDirectory() {
        return getFolderDirectory(ExternalCacheDir,Compile_ForUpload);
    }

    public static String getAPKFilePath() {
        return getFolderDirectory(ExternalCacheDir, APK_DIRECTORY);
    }

    public static String getLocalTopicDirectory(){
        return getFolderDirectory(ExternalCacheDir, LOCAL_TOPIC_DIR);
    }

    //获取相册视频目录
    public static String getAblumVideoDirectory(){
        return getFolderDirectory(ExternalDirectory, ALBUM_VIDEO_DIR);
    }

    //获取分享下载的视频目录
    public static String getShareVideoDirectory(){
        return getFolderDirectory(ExternalCacheDir, SHARE_VIDEO_DOWNLOAD_DIR);
    }
    //获取广告下载的视频目录
    public static String getADMediaDirectory(){
        return getFolderDirectory(ExternalCacheDir, AD_MEDIA_DOWNLOAD_DIR);
    }

    //获取文件夹生成目录
    public static String getFolderDirectory(String rootParentDir,String dstDirToCreate){
        File dstFileDir = new File(rootParentDir, dstDirToCreate);
        if (!dstFileDir.exists() && !dstFileDir.mkdirs()) {
            Log.e(TAG, "Failed to make image directory = " + dstFileDir.getAbsolutePath());
            return null;
        }
        return dstFileDir.getAbsolutePath();
    }


    //判断广告文件是否已经下载
    public static boolean hasADDownload (String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        String adMediaDirectory = PathUtils.getADMediaDirectory();
        if (TextUtils.isEmpty(adMediaDirectory)) {
            return false;
        }
        try{
            File adDownloadDir = new File(adMediaDirectory);
            String[] fileLists = adDownloadDir.list();
            if (fileLists == null || fileLists.length <= 0) {
                return false;
            }
            for (String fileList : fileLists) {
                if (fileList.equals(fileName)) {
                    return true;
                }
            }
        }catch (NullPointerException e){
            return false;
        }
        return false;
    }

    //删除文件夹
    public static void deleteFolder(String folderDirectory){
        if(TextUtils.isEmpty(folderDirectory)){
            return;
        }
        File deleteFolderDir = new File(folderDirectory);
        deleteFolder(deleteFolderDir);
    }

    //删除文件夹
    public static void deleteFolder(File fileDir){
        File fileList[] = fileDir.listFiles();
        if (fileList != null){
            int fileCount = fileList.length;
            for (int idx = 0;idx < fileCount;idx++){
                File file = fileList[idx];
                if (file == null){
                    continue;
                }
                if(file.isFile()){
                    if (file.exists()){
                        boolean isDelete = file.delete();
                        if (!isDelete) {
                            Log.d(TAG,"fail to delete file");
                        }
                    }
                }else if (file.isDirectory()){
                    //TODO
                    deleteFolder(file);
                }
            }
        }
        boolean isDelete = fileDir.delete();
        if (!isDelete) {
            Log.d(TAG,"fail to delete file");
        }
    }
    //删除文件
    public static void deleteFile(String filePath){
        if(TextUtils.isEmpty(filePath)){
            return;
        }
        File deleteFile = new File(filePath);
        if(deleteFile.exists()){
            boolean isDelete = deleteFile.delete();
            if (!isDelete) {
                Log.d(TAG,"fail to delete file");
            }
        }
    }

    /**
     * 获取序列化对象的文件路劲
     * @return
     */
    public static String getSeriDirectory(){
        return getFolderDirectory(ExternalCacheDir, SERI_DIR);
    }
}
