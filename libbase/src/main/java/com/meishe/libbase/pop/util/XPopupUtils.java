package com.meishe.libbase.pop.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.meishe.libbase.pop.core.AttachPopupView;
import com.meishe.libbase.pop.core.BasePopupView;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.libbase.pop.core.DrawerPopupView;
import com.meishe.libbase.pop.core.PositionPopupView;
import com.meishe.libbase.pop.enums.ImageType;
import com.meishe.libbase.pop.impl.FullScreenPopupView;
import com.meishe.libbase.pop.impl.PartShadowPopupView;
import com.meishe.libbase.pop.interfaces.XPopupImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Description:
 * X弹出工具类
 * X pops up the utility class
 */
public class XPopupUtils {
    /**
     * Gets window width.
     * 得到窗口的宽度
     * @param context the context 上下文
     * @return the window width  窗宽
     */
    public static int getWindowWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    /**
     * Gets window height.
     * 得到窗口的高度
     * @param context the context 上下文
     * @return the window height 窗口高度
     */
    public static int getWindowHeight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    /**
     * Dp 2 px int.
     * p2px int
     * @param context  the context 上下文
     * @param dipValue the dip value 下降值
     * @return the int
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * Gets status bar height.
     * 获取状态栏高度
     * @return the status bar height 状态栏的高度
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Return the navigation bar's height.
     * 返回导航栏的高度
     * @return the navigation bar's height 导航栏的高度
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Sets width height.
     * 设置宽度高度
     * @param target the target 目标
     * @param width  the width 宽
     * @param height the height 高
     */
    public static void setWidthHeight(View target, int width, int height) {
        if (width <= 0 && height <= 0) return;
        ViewGroup.LayoutParams params = target.getLayoutParams();
        if (width > 0) params.width = width;
        if (height > 0) params.height = height;
        target.setLayoutParams(params);
    }

    /**
     * Apply popup size.
     * 应用弹出大小
     * @param content   the content 上下文
     * @param maxWidth  the max width 最大宽
     * @param maxHeight the max height 最大高
     */
    public static void applyPopupSize(ViewGroup content, int maxWidth, int maxHeight) {
        applyPopupSize(content, maxWidth, maxHeight, null);
    }

    /**
     * Apply popup size.
     * 应用弹出大小
     * @param content        the content 上下文
     * @param maxWidth       the max width 最大宽
     * @param maxHeight      the max height 最大高
     * @param afterApplySize the after apply size 后应用规模
     */
    public static void applyPopupSize(final ViewGroup content, final int maxWidth, final int maxHeight, final Runnable afterApplySize) {
        content.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = content.getLayoutParams();
                View implView = content.getChildAt(0);
                ViewGroup.LayoutParams implParams = implView.getLayoutParams();
                /*
                * 假设默认Content宽是match，高是wrap
                * Suppose the default Content width is match and the height is wrap
                * */
                int w = content.getMeasuredWidth();
                /*
                * response impl view wrap_content params.
                * 响应impl view wrap_content参数。
                * */
                if (implParams.width == FrameLayout.LayoutParams.WRAP_CONTENT) {
//                    w = Math.min(w, implView.getMeasuredWidth());
                }
                if (maxWidth != 0) {
                    params.width = Math.min(w, maxWidth);
                }

                int h = content.getMeasuredHeight();
                /*
                * response impl view match_parent params.
                * 响应impl视图匹配_parent参数
                * */
                if (implParams.height == FrameLayout.LayoutParams.MATCH_PARENT) {
                    h = ((ViewGroup) content.getParent()).getMeasuredHeight();
                    params.height = h;
                }
                if (maxHeight != 0) {
                    /*
                    * 如果content的高为match，则maxHeight限制impl
                    * If the height of Content is match, then maxHeight limits IMPl
                    * */
                    if (params.height == FrameLayout.LayoutParams.MATCH_PARENT ||
                            params.height == (getWindowHeight(content.getContext()) + getStatusBarHeight())) {
                        implParams.height = Math.min(implView.getMeasuredHeight(), maxHeight);
                        implView.setLayoutParams(implParams);
                    } else {
                        params.height = Math.min(h, maxHeight);
                    }
                }
                content.setLayoutParams(params);

                if (afterApplySize != null) {
                    afterApplySize.run();
                }
            }
        });
    }

    /**
     * Sets cursor drawable color.
     * 设置光标可绘制的颜色
     * @param et    the et
     * @param color the color 颜色
     */
    public static void setCursorDrawableColor(EditText et, int color) {
        /*
        * 暂时没有找到有效的方法来动态设置cursor的颜色
        * There is no effective way to dynamically set the color of the CURSOR
        * */
    }

    /**
     * Create bitmap drawable bitmap drawable.
     * 创建可绘制的位图
     * @param resources the resources 资源
     * @param width     the width 宽
     * @param color     the color 颜色
     * @return the bitmap drawable  位图绘制
     */
    public static BitmapDrawable createBitmapDrawable(Resources resources, int width, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, 20, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0, 0, bitmap.getWidth(), 4, paint);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 4, bitmap.getWidth(), 20, paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
        bitmapDrawable.setGravity(Gravity.BOTTOM);
        return bitmapDrawable;
    }

    /**
     * Create selector state list drawable.
     * 创建可绘制的选择器状态列表
     * @param defaultDrawable the default drawable 默认可拉的
     * @param focusDrawable   the focus drawable 可拉的焦点
     * @return the state list drawable 可提取的状态列表
     */
    public static StateListDrawable createSelector(Drawable defaultDrawable, Drawable focusDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusDrawable);
        stateListDrawable.addState(new int[]{}, defaultDrawable);
        return stateListDrawable;
    }

    /**
     * Is in rect boolean.
     * 是矩形
     * @param x    the x x
     * @param y    the y y
     * @param rect the rect 矩形
     * @return the boolean
     */
    public static boolean isInRect(float x, float y, Rect rect) {
        return x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom;
    }

    /**
     * Return whether soft input is visible.
     * 返回软输入是否可见
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return getDecorViewInvisibleHeight(activity) > 0;
    }

    private static int sDecorViewDelta = 0;

    /**
     * Gets decor view invisible height.
     * 获取装饰视图看不见的高度
     * @param activity the activity
     * @return the decor view invisible height 装饰看不见的高度
     */
    public static int getDecorViewInvisibleHeight(final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    /**
     * Move up to keyboard.
     * 移到键盘上
     * @param keyboardHeight the keyboard height 键盘高度
     * @param pv             the pv
     */
    public static void moveUpToKeyboard(int keyboardHeight, BasePopupView pv) {
        if (!pv.popupInfo.isMoveUpToKeyboard) return;
        if (pv instanceof PositionPopupView) return;
        /*
        * 判断是否盖住输入框
        * Determine if the input field is covered
        * */
        ArrayList<EditText> allEts = new ArrayList<>();
        findAllEditText(allEts, pv);
        EditText focusEt = null;
        for (EditText et : allEts) {
            if (et.isFocused()) {
                focusEt = et;
                break;
            }
        }

        int dy = 0;
        int popupHeight = pv.getPopupContentView().getHeight();
        int popupWidth = pv.getPopupContentView().getWidth();
        if (pv.getPopupImplView() != null) {
            popupHeight = Math.min(popupHeight, pv.getPopupImplView().getMeasuredHeight());
            popupWidth = Math.min(popupWidth, pv.getPopupImplView().getMeasuredWidth());
        }
        int windowHeight = getWindowHeight(pv.getContext());
        int focusEtTop = 0;
        int focusBottom = 0;
        if (focusEt != null) {
            int[] locations = new int[2];
            focusEt.getLocationInWindow(locations);
            focusEtTop = locations[1];
            focusBottom = focusEtTop + focusEt.getMeasuredHeight();
        }

        /*
        * 暂时忽略PartShadow弹窗和AttachPopupView
        * Ignore the PartShadow popup window and AttachPopupView for now
        * */
        if (!(pv instanceof PartShadowPopupView) && pv instanceof AttachPopupView) return;
        /*
        * 执行上移
        * Perform up
        * */
        if (pv instanceof FullScreenPopupView ||
                (popupWidth == XPopupUtils.getWindowWidth(pv.getContext()) &&
                        popupHeight == (XPopupUtils.getWindowHeight(pv.getContext()) + XPopupUtils.getStatusBarHeight()))
        ) {
            /*
            * 如果是全屏弹窗，特殊处理，只要输入框没被盖住，就不移动。
            * If it is a full screen popover, special treatment, as long as the input field is not covered, do not move
            * */
            if (focusBottom + keyboardHeight < windowHeight) {
                return;
            }
        }
        if (pv instanceof FullScreenPopupView) {
            int overflowHeight = (focusBottom + keyboardHeight) - windowHeight;
            if (focusEt != null && overflowHeight > 0) {
                dy = overflowHeight;
            }
        } else if (pv instanceof CenterPopupView) {
            int targetY = keyboardHeight - (windowHeight - popupHeight + getStatusBarHeight()) / 2; //上移到下边贴着输入法的高度

            if (focusEt != null && focusEtTop - targetY < 0) {
                targetY += focusEtTop - targetY - getStatusBarHeight();//限制不能被状态栏遮住
            }
            dy = Math.max(0, targetY);
        } else if (pv instanceof BottomPopupView) {
            dy = keyboardHeight;
            if (focusEt != null && focusEtTop - dy < 0) {
                dy += focusEtTop - dy - getStatusBarHeight();//限制不能被状态栏遮住
            }
        } else if (isBottomPartShadow(pv) || pv instanceof DrawerPopupView) {
            int overflowHeight = (focusBottom + keyboardHeight) - windowHeight;
            if (focusEt != null && overflowHeight > 0) {
                dy = overflowHeight;
            }
        }else if(isTopPartShadow(pv)){
            int overflowHeight = (focusBottom + keyboardHeight) - windowHeight;
            if (focusEt != null && overflowHeight > 0) {
                dy = overflowHeight;
            }
            if(dy!=0){
                pv.getPopupImplView().animate().translationY(-dy)
                        .setDuration(200)
                        .setInterpolator(new OvershootInterpolator(0))
                        .start();
            }
            return;
        }
        /*
        * dy=0说明没有触发移动，有些弹窗有translationY，不能影响它们
        * Dy =0 means no movement is triggered, some popovers have translationY in them, they cannot be affected
        * */
        if (dy == 0 && pv.getPopupContentView().getTranslationY() != 0) return;
        pv.getPopupContentView().animate().translationY(-dy)
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator(0))
                .start();
    }

    private static boolean isBottomPartShadow(BasePopupView pv) {
        return pv instanceof PartShadowPopupView && ((PartShadowPopupView) pv).isShowUp;
    }

    private static boolean isTopPartShadow(BasePopupView pv) {
        return pv instanceof PartShadowPopupView && !((PartShadowPopupView) pv).isShowUp;
    }

    /**
     * Move down.
     * 向下移动
     * @param pv the pv
     */
    public static void moveDown(BasePopupView pv) {
        /*
        * 暂时忽略PartShadow弹窗和AttachPopupView
        * Ignore the PartShadow popup window and AttachPopupView for now
        * */
        if (pv instanceof PositionPopupView) return;
        if (!(pv instanceof PartShadowPopupView) && pv instanceof AttachPopupView) return;
        if (pv instanceof PartShadowPopupView && !isBottomPartShadow(pv)) {
            pv.getPopupImplView().animate().translationY(0)
                    .setInterpolator(new OvershootInterpolator(0))
                    .setDuration(200).start();
        }else {
            pv.getPopupContentView().animate().translationY(0)
                    .setInterpolator(new OvershootInterpolator(0))
                    .setDuration(200).start();

        }
    }


    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     * 返回导航栏是否可见。
     * 在onWindowFocusChanged中调用它会得到正确的结果
     * @param context the context
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(Context context) {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = context
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)
                        && child.getVisibility() == View.VISIBLE) {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible) {
            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }

    /**
     * Find all edit text.
     * 查找所有编辑文本
     * @param list  the list 集合
     * @param group the group 组
     */
    public static void findAllEditText(ArrayList<EditText> list, ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View v = group.getChildAt(i);
            if (v instanceof EditText && v.getVisibility() == View.VISIBLE) {
                list.add((EditText) v);
            } else if (v instanceof ViewGroup) {
                findAllEditText(list, (ViewGroup) v);
            }
        }
    }

    private static Context mContext;

    /**
     * Save bmp to album.
     * 保存bmp到相册
     * @param context     the context 上下文
     * @param imageLoader the image loader  加载图片
     * @param uri         the uri 地址
     */
    public static void saveBmpToAlbum(final Context context, final XPopupImageLoader imageLoader, final Object uri) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        mContext = context;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                File source = imageLoader.getImageFile(mContext, uri);
                if (source == null) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "图片不存在！", Toast.LENGTH_SHORT).show();
                            mContext = null;
                        }
                    });
                    return;
                }
                /*
                * 1. create path
                * 建立路径
                * */
                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_PICTURES;
                File dirFile = new File(dirPath);
                if (!dirFile.exists()) dirFile.mkdirs();
                try {
                    ImageType type = ImageHeaderParser.getImageType(new FileInputStream(source));
                    String ext = getFileExt(type);
                    final File target = new File(dirPath, System.currentTimeMillis() + "." + ext);
                    if (target.exists()) target.delete();
                    target.createNewFile();
                    /*
                    * 2. save
                    * 保存
                    * */
                    writeFileFromIS(target, new FileInputStream(source));
                    /*
                    * 3. notify
                    * 通知
                    * */
                    MediaScannerConnection.scanFile(mContext, new String[]{target.getAbsolutePath()},
                            new String[]{"image/" + ext}, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(final String path, Uri uri) {
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "已保存到相册！", Toast.LENGTH_SHORT).show();
                                            mContext = null;
                                        }
                                    });
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "没有保存权限，保存功能无法使用！", Toast.LENGTH_SHORT).show();
                            mContext = null;
                        }
                    });
                }
            }
        });
    }

    private static String getFileExt(ImageType type) {
        switch (type) {
            case GIF:
                return "gif";
            case PNG:
            case PNG_A:
                return "png";
            case WEBP:
            case WEBP_A:
                return "webp";
            case JPEG:
                return "jpeg";
        }
        return "jpeg";
    }

    private static boolean writeFileFromIS(final File file, final InputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
