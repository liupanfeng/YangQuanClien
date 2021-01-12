package com.meishe.libbase.pop.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.animator.EmptyAnimator;
import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.ScaleAlphaAnimator;
import com.meishe.libbase.pop.animator.ScrollScaleAnimator;
import com.meishe.libbase.pop.animator.ShadowBgAnimator;
import com.meishe.libbase.pop.animator.TranslateAlphaAnimator;
import com.meishe.libbase.pop.animator.TranslateAnimator;
import com.meishe.libbase.pop.enums.PopupStatus;
import com.meishe.libbase.pop.impl.FullScreenPopupView;
import com.meishe.libbase.pop.util.KeyboardUtils;
import com.meishe.libbase.pop.util.XPopupUtils;
import com.meishe.libbase.pop.util.navbar.NavigationBarObserver;
import com.meishe.libbase.pop.util.navbar.OnNavigationBarListener;

import java.util.ArrayList;
import java.util.Stack;

import static com.meishe.libbase.pop.enums.PopupAnimation.NoAnimation;


/**
 * Description: 弹窗基类
 * Popup window base class
 */
public abstract class BasePopupView extends FrameLayout implements OnNavigationBarListener {
    private static Stack<BasePopupView> stack = new Stack<>(); //静态存储所有弹窗对象
    public PopupInfo popupInfo;
    protected PopupAnimator popupContentAnimator;
    protected ShadowBgAnimator shadowBgAnimator;
    private int touchSlop;
    public PopupStatus popupStatus = PopupStatus.Dismiss;
    private boolean isCreated = false;

    public BasePopupView(@NonNull Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        shadowBgAnimator = new ShadowBgAnimator(this);
        /*
        *  添加Popup窗体内容View
        * Add Popup form content View
        * */
        View contentView = LayoutInflater.from(context).inflate(getPopupLayoutId(), this, false);
        /*
        * 事先隐藏，等测量完毕恢复，避免View影子跳动现象。
        * Hide it in advance and restore it after the measurement is completed to avoid the jumping of the View shadow
        * */
        contentView.setAlpha(0);
        addView(contentView);
    }

    public BasePopupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 执行初始化
     * Perform initialization
     */
    protected void init() {
        if (popupStatus == PopupStatus.Showing) return;
        popupStatus = PopupStatus.Showing;
        NavigationBarObserver.getInstance().register(getContext());
        NavigationBarObserver.getInstance().addOnNavigationBarListener(this);

        /*
        * 1. 初始化Popup
        * Initialize the Popup
        * */
        if (!isCreated) {
            initPopupContent();
        }
        /*
        * apply size dynamic
        * 应用规模动态
        * */
        //if (!(this instanceof FullScreenPopupView) && !(this instanceof ImageViewerPopupView)) {
        if (!(this instanceof FullScreenPopupView)) {
            XPopupUtils.setWidthHeight(getTargetSizeView(),
                    (getMaxWidth() != 0 && getPopupWidth() > getMaxWidth()) ? getMaxWidth() : getPopupWidth(),
                    (getMaxHeight() != 0 && getPopupHeight() > getMaxHeight()) ? getMaxHeight() : getPopupHeight()
            );
        }
        if (!isCreated) {
            isCreated = true;
            onCreate();
            if (popupInfo.xPopupCallback != null) popupInfo.xPopupCallback.onCreated();
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                * 如果有导航栏，则不能覆盖导航栏，判断各种屏幕方向
                * If there is a navigation bar, you cannot overwrite the navigation bar and determine the various screen directions
                * */
                applySize(false);
                getPopupContentView().setAlpha(1f);

                /*
                * 2. 收集动画执行器
                * Collect the animation executor
                * */
                collectAnimator();

                if (popupInfo.xPopupCallback != null) popupInfo.xPopupCallback.beforeShow();

                /*
                * 3. 执行动画
                * Perform the animation
                * */
                doShowAnimation();

                doAfterShow();

                /*
                * 目前全屏弹窗快速弹出输入法有问题，暂时用这个方案
                *  At present, there is a problem with the full-screen popup fast input method, so we will use this scheme temporarily
                * */
                if (!(BasePopupView.this instanceof FullScreenPopupView))
                    focusAndProcessBackPress();
            }
        }, 50);

    }

    private boolean hasMoveUp = false;
    private void collectAnimator(){
        if(popupContentAnimator==null){
            /*
            * 优先使用自定义的动画器
            * Use custom animators first
            * */
            if (popupInfo.customAnimator != null) {
                popupContentAnimator = popupInfo.customAnimator;
                popupContentAnimator.targetView = getPopupContentView();
            } else {
                /*
                * 根据PopupInfo的popupAnimation字段来生成对应的动画执行器，如果popupAnimation字段为null，则返回null
                * The corresponding animation executor is generated based on the PopupInfo popupAnimation field, and null is returned if the popupAnimation field is null
                * */
                popupContentAnimator = genAnimatorByPopupType();
                if (popupContentAnimator == null) {
                    popupContentAnimator = getPopupAnimator();
                }
            }

            /*
            * 3. 初始化动画执行器
            * Initializes the animation executor
            * */
            shadowBgAnimator.initAnimator();
            if (popupContentAnimator != null) {
                popupContentAnimator.initAnimator();
            }
        }
    }

    @Override
    public void onNavigationBarChange(boolean show) {
        if(!show){
            applyFull();
        }else {
            applySize(true);
        }
    }

    /**
     * Apply full.
     * 应用
     */
    protected void applyFull(){
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.topMargin = 0;
        params.leftMargin = 0;
        params.bottomMargin = 0;
        params.rightMargin = 0;
        setLayoutParams(params);
    }

    /**
     * Apply size.
     * 应用规模
     * @param isShowNavBar the is show nav bar
     *                     显示导航条
     */
    protected void applySize(boolean isShowNavBar){
        LayoutParams params = (LayoutParams) getLayoutParams();
        int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        boolean isNavBarShown = isShowNavBar || XPopupUtils.isNavBarVisible(getContext());
        if (rotation == 0) {
            params.leftMargin = 0;
            params.rightMargin = 0;
            params.bottomMargin = isNavBarShown ? XPopupUtils.getNavBarHeight() : 0;
        } else if (rotation == 1) {
            params.bottomMargin = 0;
            params.rightMargin = isNavBarShown ? XPopupUtils.getNavBarHeight() : 0;
            params.leftMargin = 0;
        } else if (rotation == 3) {
            params.bottomMargin = 0;
            params.leftMargin = 0;
            params.rightMargin = isNavBarShown ? XPopupUtils.getNavBarHeight() : 0;
        }
        setLayoutParams(params);
    }

    /**
     * Show base popup view.
     * 显示基本弹出视图
     * @return the base popup view 基本弹出视图
     */
    public BasePopupView show() {
        if (getParent() != null) return this;
        final Activity activity = (Activity) getContext();
        popupInfo.decorView = (ViewGroup) activity.getWindow().getDecorView();
        KeyboardUtils.registerSoftInputChangedListener(activity, this, new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                if (height == 0) {
                    /*
                    * 说明对话框隐藏
                    * Description dialog box hidden
                    * */
                    XPopupUtils.moveDown(BasePopupView.this);
                    hasMoveUp = false;
                } else {
                    /*
                    * when show keyboard, move up
                    * 当显示键盘时，向上移动
                    * */
                    XPopupUtils.moveUpToKeyboard(height, BasePopupView.this);
                    hasMoveUp = true;
                }
            }
        });
        /*
        * 1. add PopupView to its decorView after measured.
        * 在测量后添加PopupView到它的decorView。
        * */
        popupInfo.decorView.post(new Runnable() {
            @Override
            public void run() {
                if (getParent() != null) {
                    ((ViewGroup) getParent()).removeView(BasePopupView.this);
                }
                popupInfo.decorView.addView(BasePopupView.this, new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));

                /*
                * 2. do init，game start.
                * 做初始化,游戏开始
                * */
                init();
            }
        });
        return this;
    }

    /**
     * Do after show.
     * 做了表演
     */
    protected void doAfterShow() {
        removeCallbacks(doAfterShowTask);
        postDelayed(doAfterShowTask, getAnimationDuration());
    }

    private Runnable doAfterShowTask = new Runnable() {
        @Override
        public void run() {
            popupStatus = PopupStatus.Show;
            onShow();
            if (BasePopupView.this instanceof FullScreenPopupView) focusAndProcessBackPress();
            if (popupInfo != null && popupInfo.xPopupCallback != null)
                popupInfo.xPopupCallback.onShow();
            if (XPopupUtils.getDecorViewInvisibleHeight((Activity) getContext()) > 0 && !hasMoveUp) {
                XPopupUtils.moveUpToKeyboard(XPopupUtils.getDecorViewInvisibleHeight((Activity) getContext()), BasePopupView.this);
            }
        }
    };

    private ShowSoftInputTask showSoftInputTask;

    /**
     * Focus and process back press.
     * 焦点和过程返回按下
     */
    public void focusAndProcessBackPress() {
        if (popupInfo.isRequestFocus) {
            setFocusableInTouchMode(true);
            requestFocus();
            if (!stack.contains(this)) stack.push(this);
        }
        /*
        * 此处焦点可能被内容的EditText抢走，也需要给EditText也设置返回按下监听
        * The focus here may be stolen by the EditText of the content, and you also need to set the return to EditText to press listen
        * */
        setOnKeyListener(new BackPressListener());
        if(!popupInfo.autoFocusEditText) showSoftInput(this);

        //let all EditText can process back pressed.
        ArrayList<EditText> list = new ArrayList<>();
        XPopupUtils.findAllEditText(list, (ViewGroup) getPopupContentView());
        for (int i = 0; i < list.size(); i++) {
            final EditText et = list.get(i);
            et.setOnKeyListener(new BackPressListener());
            if (i == 0 && popupInfo.autoFocusEditText) {
                et.setFocusable(true);
                et.setFocusableInTouchMode(true);
                et.requestFocus();
                showSoftInput(et);
            }
        }
    }

    /**
     * Show soft input.
     * 显示软输入
     * @param focusView the focus view 焦点视图
     */
    protected void showSoftInput(View focusView){
        if (popupInfo.autoOpenSoftInput) {
            if (showSoftInputTask == null) {
                showSoftInputTask = new ShowSoftInputTask(focusView);
            } else {
                removeCallbacks(showSoftInputTask);
            }
            postDelayed(showSoftInputTask, 10);
        }
    }

    /**
     * Dismiss or hide soft input.
     * 关闭或隐藏软输入
     */
    protected void dismissOrHideSoftInput() {
        if (KeyboardUtils.sDecorViewInvisibleHeightPre == 0)
            dismiss();
        else
            KeyboardUtils.hideSoftInput(BasePopupView.this);
    }

    /**
     * The type Show soft input task.
     * 显示软输入任务类
     */
    class ShowSoftInputTask implements Runnable {
        View focusView;
        boolean isDone = false;

        public ShowSoftInputTask(View focusView) {
            this.focusView = focusView;
        }

        @Override
        public void run() {
            if (focusView != null && !isDone) {
                isDone = true;
                KeyboardUtils.showSoftInput(focusView);
            }
        }
    }

    /**
     * The type Back press listener.
     * 返回按下的监听
     */
    class BackPressListener implements OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                if (popupInfo.isDismissOnBackPressed &&
                        (popupInfo.xPopupCallback == null || !popupInfo.xPopupCallback.onBackPressed()))
                    dismissOrHideSoftInput();
                return true;
            }
            return false;
        }
    }

    /**
     * 根据PopupInfo的popupAnimation字段来生成对应的内置的动画执行器
     * Generate the corresponding built-in animation executor based on the PopupInfo popupAnimation field
     * @return the popup animator 弹出动画
     */
    protected PopupAnimator genAnimatorByPopupType() {
        if (popupInfo == null || popupInfo.popupAnimation == null) return null;
        switch (popupInfo.popupAnimation) {
            case ScaleAlphaFromCenter:
            case ScaleAlphaFromLeftTop:
            case ScaleAlphaFromRightTop:
            case ScaleAlphaFromLeftBottom:
            case ScaleAlphaFromRightBottom:
                return new ScaleAlphaAnimator(getPopupContentView(), popupInfo.popupAnimation);

            case TranslateAlphaFromLeft:
            case TranslateAlphaFromTop:
            case TranslateAlphaFromRight:
            case TranslateAlphaFromBottom:
                return new TranslateAlphaAnimator(getPopupContentView(), popupInfo.popupAnimation);

            case TranslateFromLeft:
            case TranslateFromTop:
            case TranslateFromRight:
            case TranslateFromBottom:
                return new TranslateAnimator(getPopupContentView(), popupInfo.popupAnimation);

            case ScrollAlphaFromLeft:
            case ScrollAlphaFromLeftTop:
            case ScrollAlphaFromTop:
            case ScrollAlphaFromRightTop:
            case ScrollAlphaFromRight:
            case ScrollAlphaFromRightBottom:
            case ScrollAlphaFromBottom:
            case ScrollAlphaFromLeftBottom:
                return new ScrollScaleAnimator(getPopupContentView(), popupInfo.popupAnimation);

            case NoAnimation:
                return new EmptyAnimator();
        }
        return null;
    }

    protected abstract int getPopupLayoutId();

    /**
     * 如果你自己继承BasePopupView来做，这个不用实现
     * If you inherit BasePopupView to do it yourself, you don't have to implement this
     * @return impl layout id 布局id
     */
    protected int getImplLayoutId() {
        return -1;
    }

    /**
     * 获取PopupAnimator，用于每种类型的PopupView自定义自己的动画器
     * Get the PopupAnimator for each type of PopupView to customize your own animator
     * @return popup animator 弹出动画
     */
    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    /**
     * 请使用onCreate，主要给弹窗内部用，不要去重写。
     * Please use onCreate, mainly for the inside of the popover, do not overwrite
     */
    protected void initPopupContent() {
    }

    protected void onCreate() {
    }

    /**
     * 执行显示动画：动画由2部分组成，一个是背景渐变动画，一个是Content的动画；
     * 背景动画由父类实现，Content由子类实现
     * Perform display animation: The animation consists of 2 parts, one is background gradient animation, one is Content animation;
     * Background animation is implemented by the parent class and Content by the child class
     */
    protected void doShowAnimation() {
        if (popupInfo.hasShadowBg) {
            shadowBgAnimator.isZeroDuration = (popupInfo.popupAnimation == NoAnimation);
            shadowBgAnimator.animateShow();
        }
        if (popupContentAnimator != null)
            popupContentAnimator.animateShow();
    }

    /**
     * 执行消失动画：动画由2部分组成，一个是背景渐变动画，一个是Content的动画；
     * 背景动画由父类实现，Content由子类实现
     * Perform disappear animation: The animation consists of two parts, one is background gradient animation, one is Content animation;
     *  Background animation is implemented by the parent class and Content by the child class
     */
    protected void doDismissAnimation() {
        if (popupInfo.hasShadowBg) {
            shadowBgAnimator.animateDismiss();
        }
        if (popupContentAnimator != null)
            popupContentAnimator.animateDismiss();
    }

    /**
     * 获取内容View，本质上PopupView显示的内容都在这个View内部。
     * 而且我们对PopupView执行的动画，也是对它执行的动画
     * Get the content View, and essentially whatever PopupView is displaying is inside this View.
     * And the animation we're doing on PopupView is the same animation we're doing on PopupView
     * @return popup content view
     */
    public View getPopupContentView() {
        return getChildAt(0);
    }

    public View getPopupImplView() {
        return ((ViewGroup) getPopupContentView()).getChildAt(0);
    }

    public int getAnimationDuration() {
        return popupInfo.popupAnimation == NoAnimation ? 10 : XPopup.getAnimationDuration();
    }

    /**
     * 弹窗的最大宽度，一般用来限制布局宽度为wrap或者match时的最大宽度
     * The maximum width of a popover, usually used to limit the width of a layout to the maximum width for wrap or match
     * @return max width 最大宽度
     */
    protected int getMaxWidth() {
        return 0;
    }

    /**
     * 弹窗的最大高度，一般用来限制布局高度为wrap或者match时的最大宽度
     * The maximum height of a popover, usually used to limit the layout height to the maximum width for wrap or match
     * @return max height 最大高度
     */
    protected int getMaxHeight() {
        return popupInfo.maxHeight;
    }

    /**
     * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
     * The width of the pop-up window, which is used to dynamically set the width of the current pop-up window, is limited by getMaxWidth()
     * @return popup width 弹出宽度
     */
    protected int getPopupWidth() {
        return 0;
    }

    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     * The height of the pop-up window, used to dynamically set the height of the current pop-up window, is limited by getMaxHeight()
     * @return popup height 弹出高度
     */
    protected int getPopupHeight() {
        return 0;
    }

    /**
     * Gets target size view.
     * 获取目标大小视图
     * @return the target size view 目标尺寸视图
     */
    protected View getTargetSizeView() {
        return getPopupContentView();
    }

    /**
     * 消失
     * disappear
     */
    public void dismiss() {
        if (popupStatus == PopupStatus.Dismissing) return;
        popupStatus = PopupStatus.Dismissing;
        if (popupInfo.autoOpenSoftInput) KeyboardUtils.hideSoftInput(this);
        clearFocus();
        doDismissAnimation();
        doAfterDismiss();
    }

    /**
     * Delay dismiss.
     * 延迟消失
     * @param delay the delay 延迟
     */
    public void delayDismiss(long delay) {
        if (delay < 0) delay = 0;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, delay);
    }

    /**
     * Delay dismiss with.
     * 延迟解散
     * @param delay    the delay 延迟
     * @param runnable the runnable 可运行
     */
    public void delayDismissWith(long delay, Runnable runnable) {
        this.dismissWithRunnable = runnable;
        delayDismiss(delay);
    }

    /**
     * Do after dismiss.
     * 消失后
     */
    protected void doAfterDismiss() {
        if (popupInfo.autoOpenSoftInput) KeyboardUtils.hideSoftInput(this);
        removeCallbacks(doAfterDismissTask);
        postDelayed(doAfterDismissTask, getAnimationDuration());
    }

    private Runnable doAfterDismissTask = new Runnable() {
        @Override
        public void run() {
            onDismiss();
            if (popupInfo != null && popupInfo.xPopupCallback != null) {
                popupInfo.xPopupCallback.onDismiss();
            }
            if (dismissWithRunnable != null) {
                dismissWithRunnable.run();
                /*
                * no cache, avoid some bad edge effect.
                * 没有缓存，避免了一些不好的边缘效果
                * */
                dismissWithRunnable = null;
            }
            popupStatus = PopupStatus.Dismiss;
            NavigationBarObserver.getInstance().removeOnNavigationBarListener(BasePopupView.this);

            if (!stack.isEmpty()) stack.pop();
            if (popupInfo != null && popupInfo.isRequestFocus) {
                if (!stack.isEmpty()) {
                    stack.get(stack.size() - 1).focusAndProcessBackPress();
                } else {
                    /*
                    * 让根布局拿焦点，避免布局内RecyclerView类似布局获取焦点导致布局滚动
                    * RecyclerView - Create a root layout that RecyclerView the focus so that the layout does not cause static rolling
                    * */
                    View needFocusView = ((Activity) getContext()).findViewById(android.R.id.content);
                    needFocusView.setFocusable(true);
                    needFocusView.setFocusableInTouchMode(true);
                }
            }

            /*
            * 移除弹窗，GameOver
            * Remove the popover, GameOver
            * */
            if (popupInfo.decorView != null) {
                popupInfo.decorView.removeView(BasePopupView.this);
                KeyboardUtils.removeLayoutChangeListener(popupInfo.decorView, BasePopupView.this);
            }
        }
    };

    Runnable dismissWithRunnable;

    public void dismissWith(Runnable runnable) {
        this.dismissWithRunnable = runnable;
        dismiss();
    }

    public boolean isShow() {
        return popupStatus != PopupStatus.Dismiss;
    }

    public boolean isDismiss() {
        return popupStatus == PopupStatus.Dismiss;
    }

    public void toggle() {
        if (isShow()) {
            dismiss();
        } else {
            show();
        }
    }

    /**
     * 消失动画执行完毕后执行
     * The disappear animation is executed after execution
     */
    protected void onDismiss() {
    }

    /**
     * 显示动画执行完毕后执行
     * Execute after the animation is finished
     */
    protected void onShow() {
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stack.clear();
        removeCallbacks(doAfterShowTask);
        removeCallbacks(doAfterDismissTask);
        KeyboardUtils.removeLayoutChangeListener(popupInfo.decorView, BasePopupView.this);
        if (showSoftInputTask != null) removeCallbacks(showSoftInputTask);
        popupStatus = PopupStatus.Dismiss;
        showSoftInputTask = null;
        hasMoveUp = false;
    }

    private float x, y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
        * 如果自己接触到了点击，并且不在PopupContentView范围内点击，则进行判断是否是点击事件,如果是，则dismiss
        * If you touch a click and do not click within the PopupContentView, determine if it is a click event, and if it is, dismiss
        * */
        Rect rect = new Rect();
        getPopupContentView().getGlobalVisibleRect(rect);
        if (!XPopupUtils.isInRect(event.getX(), event.getY(), rect)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float dx = event.getX() - x;
                    float dy = event.getY() - y;
                    float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    if (distance < touchSlop && popupInfo.isDismissOnTouchOutside) {
                        dismiss();
                    }
                    x = 0;
                    y = 0;
                    break;
            }
        }
        return true;
    }


}
