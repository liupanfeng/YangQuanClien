/*
 Copyright 2011, 2012 Chris Banes.
 <p>
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 <p>
 http://www.apache.org/licenses/LICENSE-2.0
 <p>
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.meishe.libbase.pop.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.OverScroller;


/**
 * The component of which does the work allowing for zooming, scaling, panning, etc.
 * It is made public in case you need to subclass something other than AppCompatImageView and still
 * gain the functionality that {@link PhotoView} offers
 * 它的组件可以进行缩放、缩放、平移等操作。
 * 它是公共的，以防你需要子类，而不是AppCompatImageView
 * 获得{@link PhotoView}提供的功能
 */
public class PhotoViewAttacher implements View.OnTouchListener,
        View.OnLayoutChangeListener {

    private static float DEFAULT_MAX_SCALE = 4.0f;
    private static float DEFAULT_MID_SCALE = 2.5f;
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private static int DEFAULT_ZOOM_DURATION = 200;

    private static final int HORIZONTAL_EDGE_NONE = -1;
    private static final int HORIZONTAL_EDGE_LEFT = 0;
    private static final int HORIZONTAL_EDGE_RIGHT = 1;
    private static final int HORIZONTAL_EDGE_BOTH = 2;
    private static final int VERTICAL_EDGE_NONE = -1;
    private static final int VERTICAL_EDGE_TOP = 0;
    private static final int VERTICAL_EDGE_BOTTOM = 1;
    private static final int VERTICAL_EDGE_BOTH = 2;
    private static int SINGLE_TOUCH = 1;

    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private int mZoomDuration = DEFAULT_ZOOM_DURATION;
    private float mMinScale = DEFAULT_MIN_SCALE;
    private float mMidScale = DEFAULT_MID_SCALE;
    private float mMaxScale = DEFAULT_MAX_SCALE;

    private boolean mAllowParentInterceptOnEdge = true;
    private boolean mBlockParentIntercept = false;

    private ImageView mImageView;

    /*
    * Gesture Detectors
    * 手势探测器
    * */
    private GestureDetector mGestureDetector;
    private CustomGestureDetector mScaleDragDetector;

    /*
    * These are set so we don't keep allocating them on the heap
    * 这些是设置好的，所以我们不会在堆上分配它们
    * */
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];

    /*
    * Listeners
    * 监听
    * */
    private OnMatrixChangedListener mMatrixChangeListener;
    private OnPhotoTapListener mPhotoTapListener;
    private OnOutsidePhotoTapListener mOutsidePhotoTapListener;
    private OnViewTapListener mViewTapListener;
    private View.OnClickListener mOnClickListener;
    private OnLongClickListener mLongClickListener;
    private OnScaleChangedListener mScaleChangeListener;
    private OnSingleFlingListener mSingleFlingListener;
    private OnViewDragListener mOnViewDragListener;

    private FlingRunnable mCurrentFlingRunnable;
    private int mHorizontalScrollEdge = HORIZONTAL_EDGE_BOTH;
    private int mVerticalScrollEdge = VERTICAL_EDGE_BOTH;
    private float mBaseRotation;
    /**
     * The Is top end.
     * 顶端
     */
    public boolean isTopEnd,
    /**
     * The Is bottom end.
     * 底端
     */
    isBottomEnd, /**
     * The Is left end.
     * 左端
     */
    isLeftEnd, /**
     * The Is right end.
     * 右端
     */
    isRightEnd = false;
    /**
     * The Is vertical.
     * 垂直的
     */
    public boolean isVertical,
    /**
     * The Is horizontal.
     * 水平
     */
    isHorizontal;
    private boolean mZoomEnabled = true;
    /*
    * 是否是长图
    * Is it a long graph
    * */
    private boolean isLongImage = false;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private OnGestureListener onGestureListener = new OnGestureListener() {
        @Override
        public void onDrag(float dx, float dy) {
            if (mScaleDragDetector.isScaling()) {
                /*
                * Do not drag if we are already scaling
                * 如果我们已经缩放了，就不要拖拽
                * */
                return;
            }
            if (mOnViewDragListener != null) {
                mOnViewDragListener.onDrag(dx, dy);
            }
            mSuppMatrix.postTranslate(dx, dy);
            checkAndDisplayMatrix();
            isTopEnd = (mVerticalScrollEdge == VERTICAL_EDGE_TOP) && getScale() != 1f;
            isBottomEnd = (mVerticalScrollEdge == VERTICAL_EDGE_BOTTOM) && getScale() != 1f;
            isLeftEnd = (mHorizontalScrollEdge == HORIZONTAL_EDGE_LEFT) && getScale() != 1f;
            isRightEnd = (mHorizontalScrollEdge == HORIZONTAL_EDGE_RIGHT) && getScale() != 1f;

            ViewParent parent = mImageView.getParent();
            if (parent == null) return;
            if (mAllowParentInterceptOnEdge && !mScaleDragDetector.isScaling() && !mBlockParentIntercept) {
                if ((mHorizontalScrollEdge == HORIZONTAL_EDGE_BOTH && !isLongImage)
                        || (mHorizontalScrollEdge == HORIZONTAL_EDGE_LEFT && dx >= 0f && isHorizontal)
                        || (mHorizontalScrollEdge == HORIZONTAL_EDGE_RIGHT && dx <= -0f && isHorizontal)
//                        || (mVerticalScrollEdge == VERTICAL_EDGE_TOP && dy >= 1f)
//                        || (mVerticalScrollEdge == VERTICAL_EDGE_BOTTOM && dy <= -1f)
                ) {
                    parent.requestDisallowInterceptTouchEvent(false);
                } else if ((mVerticalScrollEdge == VERTICAL_EDGE_BOTH && isVertical)
                        || (isTopEnd && dy > 0 && isVertical)
                        || (isBottomEnd && dy < 0 && isVertical)) {
                    parent.requestDisallowInterceptTouchEvent(false);
                } else if (isLongImage) {
                    /*
                    * 长图特殊上下滑动
                    * Long graph slides up and down specially
                    * */
                    if ((mVerticalScrollEdge == VERTICAL_EDGE_TOP && dy > 0 && isVertical)
                      || (mVerticalScrollEdge == VERTICAL_EDGE_BOTTOM && dy < 0 && isVertical)) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                }

            } else {
                if (mHorizontalScrollEdge == HORIZONTAL_EDGE_BOTH && isLongImage && isHorizontal) {
                    /*
                    * 长图左右滑动
                    * Slide the long diagram left and right
                    * */
                    parent.requestDisallowInterceptTouchEvent(false);
                }else{
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
        }

        @Override
        public void onFling(float startX, float startY, float velocityX, float velocityY) {
            mCurrentFlingRunnable = new FlingRunnable(mImageView.getContext());
            mCurrentFlingRunnable.fling(getImageViewWidth(mImageView),
                    getImageViewHeight(mImageView), (int) velocityX, (int) velocityY);
            mImageView.post(mCurrentFlingRunnable);
        }

        @Override
        public void onScale(float scaleFactor, float focusX, float focusY) {
            if (getScale() < mMaxScale || scaleFactor < 1f) {
                if (mScaleChangeListener != null) {
                    mScaleChangeListener.onScaleChange(scaleFactor, focusX, focusY);
                }
                mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
                checkAndDisplayMatrix();
            }
        }
    };

    public PhotoViewAttacher(ImageView imageView) {
        mImageView = imageView;
        imageView.setOnTouchListener(this);
        imageView.addOnLayoutChangeListener(this);
        if (imageView.isInEditMode()) {
            return;
        }
        mBaseRotation = 0.0f;
        /*
        * Create Gesture Detectors...
        * 创建动作探测器…
        * */
        mScaleDragDetector = new CustomGestureDetector(imageView.getContext(), onGestureListener);
        mGestureDetector = new GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            /*
            * forward long click listener
            * 前向长点击监听器
            * */
            @Override
            public void onLongPress(MotionEvent e) {
                if (mLongClickListener != null) {
                    mLongClickListener.onLongClick(mImageView);
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                if (mSingleFlingListener != null) {
                    if (getScale() > DEFAULT_MIN_SCALE) {
                        return false;
                    }
                    if (e1.getPointerCount() > SINGLE_TOUCH
                            || e2.getPointerCount() > SINGLE_TOUCH) {
                        return false;
                    }
                    return mSingleFlingListener.onFling(e1, e2, velocityX, velocityY);
                }
                return false;
            }
        });
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(mImageView);
                }
                final RectF displayRect = getDisplayRect();
                final float x = e.getX(), y = e.getY();
                if (mViewTapListener != null) {
                    mViewTapListener.onViewTap(mImageView, x, y);
                }
                if (displayRect != null) {
                    /*
                    * Check to see if the user tapped on the photo
                    * 检查用户是否点击了照片
                    * */
                    if (displayRect.contains(x, y)) {
                        float xResult = (x - displayRect.left)
                                / displayRect.width();
                        float yResult = (y - displayRect.top)
                                / displayRect.height();
                        if (mPhotoTapListener != null) {
                            mPhotoTapListener.onPhotoTap(mImageView, xResult, yResult);
                        }
                        return true;
                    } else {
                        if (mOutsidePhotoTapListener != null) {
                            mOutsidePhotoTapListener.onOutsidePhotoTap(mImageView);
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent ev) {
                try {
                    float scale = getScale();
                    float x = ev.getX();
                    float y = ev.getY();
                    if (scale < getMediumScale()) {
                        setScale(getMediumScale(), x, y, true);
                    } else if (scale >= getMediumScale() && scale < getMaximumScale()) {
                        setScale(getMaximumScale(), x, y, true);
                    } else {
                        setScale(getMinimumScale(), x, y, true);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    /*
                    * Can sometimes happen when getX() and getY() is called
                    * getX()和getY()调用时有时会发生吗
                    * */
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                /*
                * Wait for the confirmed onDoubleTap() instead
                * 等待确认的onDoubleTap()
                * */
                return true;
            }
        });
    }

    /**
     * Sets on double tap listener.
     * 设置双tap监听器
     * @param newOnDoubleTapListener the new on double tap listener 新的双击监听器
     */
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
    }

    /**
     * Sets on scale change listener.
     *
     * @param onScaleChangeListener the on scale change listener
     */
    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangeListener) {
        this.mScaleChangeListener = onScaleChangeListener;
    }

    /**
     * Sets on single fling listener.
     * 设置单拨监听
     * @param onSingleFlingListener the on single fling listener 单拨监听
     */
    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        this.mSingleFlingListener = onSingleFlingListener;
    }

    /**
     * Is zoom enabled boolean.
     * 变焦启用
     * @return the boolean
     */
    @Deprecated
    public boolean isZoomEnabled() {
        return mZoomEnabled;
    }

    /**
     * Gets display rect.
     * 显示矩形
     * @return the display rect 显示矩形
     */
    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    /**
     * Sets display matrix.
     * 显示矩阵
     * @param finalMatrix the final matrix 最后一个矩阵
     * @return the display matrix 显示矩阵
     */
    public boolean setDisplayMatrix(Matrix finalMatrix) {
        if (finalMatrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        if (mImageView.getDrawable() == null) {
            return false;
        }
        mSuppMatrix.set(finalMatrix);
        checkAndDisplayMatrix();
        return true;
    }

    /**
     * Sets base rotation.
     * 基础旋转
     * @param degrees the degrees 角度
     */
    public void setBaseRotation(final float degrees) {
        mBaseRotation = degrees % 360;
        update();
        setRotationBy(mBaseRotation);
        checkAndDisplayMatrix();
    }

    /**
     * Sets rotation to.
     * 设置旋转
     * @param degrees the degrees 角度
     */
    public void setRotationTo(float degrees) {
        mSuppMatrix.setRotate(degrees % 360);
        checkAndDisplayMatrix();
    }

    /**
     * Sets rotation by.
     * 设置旋转
     * @param degrees the degrees  角度
     */
    public void setRotationBy(float degrees) {
        mSuppMatrix.postRotate(degrees % 360);
        checkAndDisplayMatrix();
    }

    /**
     * Gets minimum scale.
     *  获得最小缩放
     * @return the minimum scale 最小缩放
     */
    public float getMinimumScale() {
        return mMinScale;
    }

    /**
     * Gets medium scale.
     *   获得中等缩放
     * @return the medium scale 中等缩放
     */
    public float getMediumScale() {
        return mMidScale;
    }

    /**
     * Gets maximum scale.
     *  获得最大缩放
     * @return the maximum scale 最大缩放
     */
    public float getMaximumScale() {
        return mMaxScale;
    }

    /**
     * Gets scale.
     * 获得缩放
     * @return the scale 缩放
     */
    public float getScale() {
        return (float) Math.sqrt((float) Math.pow(getValue(mSuppMatrix, Matrix.MSCALE_X), 2) + (float) Math.pow
                (getValue(mSuppMatrix, Matrix.MSKEW_Y), 2));
    }

    /**
     * Gets scale type.
     *  获得缩放类型
     * @return the scale type 缩放类型
     */
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int
            oldRight, int oldBottom) {
        /*
        * Update our base matrix, as the bounds have changed
        * 当边界改变时，更新基矩阵
        * */
        if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
            updateBaseMatrix(mImageView.getDrawable());
        }
    }

    /**
     * The X.
     * x
     */
    float x, /**
     * The Y.
     * y
     */
    y;

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        boolean handled = false;
        if (mZoomEnabled && Util.hasDrawable((ImageView) v)) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = ev.getX();
                    y = ev.getY();
                    ViewParent parent = v.getParent();
                    /*
                    * First, disable the Parent from intercepting the touch event,If we're flinging, and the user presses down, cancel fling
                    * 首先，禁用父进程拦截触摸事件，如果我们抛出，用户按下，取消抛出
                    * */
                    cancelFling();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    isTopEnd = false;
                    /*
                    * If the user has zoomed less than min scale, zoom back to min scale
                    * 如果用户缩放的范围小于最小尺度，则缩放回最小尺度
                    * */
                    if (getScale() < mMinScale) {
                        RectF rect = getDisplayRect();
                        if (rect != null) {
                            v.post(new AnimatedZoomRunnable(getScale(), mMinScale,
                                    rect.centerX(), rect.centerY()));
                            handled = true;
                        }
                    } else if (getScale() > mMaxScale) {
                        RectF rect = getDisplayRect();
                        if (rect != null) {
                            v.post(new AnimatedZoomRunnable(getScale(), mMaxScale,
                                    rect.centerX(), rect.centerY()));
                            handled = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = Math.abs(ev.getX() - x);
                    float dy = Math.abs(ev.getY() - y);
                    if(isLongImage){
                        isVertical = dy > dx;
                        isHorizontal = dx > dy * 2;
                    }else {
                        isVertical = (getScale() != 1.0 && dy > dx);
                        isHorizontal = (getScale() != 1.0 && dx > dy * 2);
                    }
                    break;
            }
            /*
            * Try the Scale/Drag detector
            * 尝试缩放/拖动检测器
            * */
            if (mScaleDragDetector != null) {
                boolean wasScaling = mScaleDragDetector.isScaling();
                boolean wasDragging = mScaleDragDetector.isDragging();
                handled = mScaleDragDetector.onTouchEvent(ev);
                boolean didntScale = !wasScaling && !mScaleDragDetector.isScaling();
                boolean didntDrag = !wasDragging && !mScaleDragDetector.isDragging();
                mBlockParentIntercept = didntScale && didntDrag;
            }
            /*
            * Check to see if the user double tapped
            * 检查用户是否双击
            * */
            if (mGestureDetector != null && mGestureDetector.onTouchEvent(ev)) {
                handled = true;
            }

        }
        return handled;
    }

    /**
     * Sets allow parent intercept on edge.
     * 设置允许父节点在边缘拦截
     * @param allow the allow 允许
     */
    public void setAllowParentInterceptOnEdge(boolean allow) {
        mAllowParentInterceptOnEdge = allow;
    }

    public void setMinimumScale(float minimumScale) {
        Util.checkZoomLevels(minimumScale, mMidScale, mMaxScale);
        mMinScale = minimumScale;
    }

    public void setMediumScale(float mediumScale) {
        Util.checkZoomLevels(mMinScale, mediumScale, mMaxScale);
        mMidScale = mediumScale;
    }

    public void setMaximumScale(float maximumScale) {
        Util.checkZoomLevels(mMinScale, mMidScale, maximumScale);
        mMaxScale = maximumScale;
    }

    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        Util.checkZoomLevels(minimumScale, mediumScale, maximumScale);
        mMinScale = minimumScale;
        mMidScale = mediumScale;
        mMaxScale = maximumScale;
    }

    /**
     * Sets on long click listener.
     * 设置长按点击监听
     * @param listener the listener
     */
    public void setOnLongClickListener(OnLongClickListener listener) {
        mLongClickListener = listener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    /**
     * Sets on matrix change listener.
     * 设置矩阵更改监听器
     * @param listener the listener
     */
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        mMatrixChangeListener = listener;
    }

    /**
     * Sets on photo tap listener.
     * 设置在照片点击监听
     * @param listener the listener
     */
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        mPhotoTapListener = listener;
    }

    /**
     * Sets on outside photo tap listener.
     * 设置在外面的照片点击监听
     * @param mOutsidePhotoTapListener the m outside photo tap listener 外部的m照片点击侦听器
     */
    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener mOutsidePhotoTapListener) {
        this.mOutsidePhotoTapListener = mOutsidePhotoTapListener;
    }

    /**
     * Sets on view tap listener.
     * 设置在视图tap监听器
     * @param listener the listener
     */
    public void setOnViewTapListener(OnViewTapListener listener) {
        mViewTapListener = listener;
    }

    /**
     * Sets on view drag listener.
     * 设置在视图拖动监听器
     * @param listener the listener
     */
    public void setOnViewDragListener(OnViewDragListener listener) {
        mOnViewDragListener = listener;
    }

    /**
     * Sets scale.
     * 设置缩放
     * @param scale the scale 缩放
     */
    public void setScale(float scale) {
        setScale(scale, false);
    }

    public void setScale(float scale, boolean animate) {
        setScale(scale, (mImageView.getRight()) / 2, (mImageView.getBottom()) / 2, animate);
    }

    /**
     * Sets scale.
     * 设置缩放
     * @param scale   the scale 缩放
     * @param focalX  the focal x  焦x
     * @param focalY  the focal y  焦y
     * @param animate the animate
     */
    public void setScale(float scale, float focalX, float focalY,
                         boolean animate) {
        /*
        * Check to see if the scale is within bounds
        * 检查一下刻度是否在范围内
        * */
//        if (scale < mMinScale || scale > mMaxScale) {
//            throw new IllegalArgumentException("Scale must be within the range of minScale and maxScale");
//        }
        if (animate) {
            mImageView.post(new AnimatedZoomRunnable(getScale(), scale,
                    focalX, focalY));
        } else {
            mSuppMatrix.setScale(scale, scale, focalX, focalY);
            checkAndDisplayMatrix();
        }
    }

    /**
     * Set the zoom interpolator
     * 设置缩放插值器
     * @param interpolator the zoom interpolator 放大插值器
     */
    public void setZoomInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setScaleType(ScaleType scaleType) {
        if (Util.isSupportedScaleType(scaleType) && scaleType != mScaleType) {
            mScaleType = scaleType;
            update();
        }
    }

    public boolean isZoomable() {
        return mZoomEnabled;
    }

    public void setZoomable(boolean zoomable) {
        mZoomEnabled = zoomable;
        update();
    }

    /**
     * Update.
     *  更新
     */
    public void update() {
        if (mZoomEnabled) {
            /*
            *  Update the base matrix using the current drawable
            * 使用当前drawable更新基本矩阵
            * */
            updateBaseMatrix(mImageView.getDrawable());
        } else {
            /*
            * Reset the Matrix...
            * 重置矩阵……
            * */
            resetMatrix();
        }
    }

    /**
     * Get the display matrix
     * 获取显示矩阵
     * @param matrix target matrix to copy to 复制到的目标矩阵
     */
    public void getDisplayMatrix(Matrix matrix) {
        matrix.set(getDrawMatrix());
    }

    /**
     * Get the current support matrix
     * 获取当前支持矩阵
     * @param matrix the matrix 矩阵
     */
    public void getSuppMatrix(Matrix matrix) {
        matrix.set(mSuppMatrix);
    }

    private Matrix getDrawMatrix() {
        mDrawMatrix.set(mBaseMatrix);
        mDrawMatrix.postConcat(mSuppMatrix);
        return mDrawMatrix;
    }

    /**
     * Gets image matrix.
     * 图像矩阵
     * @return the image matrix 图像矩阵
     */
    public Matrix getImageMatrix() {
        return mDrawMatrix;
    }

    /**
     * Sets zoom transition duration.
     * 变焦转场时长
     * @param milliseconds the milliseconds 毫秒
     */
    public void setZoomTransitionDuration(int milliseconds) {
        this.mZoomDuration = milliseconds;
    }

    /**
     * Helper method that 'unpacks' a Matrix and returns the required value
     * 帮助器方法，“解包”一个矩阵并返回所需的值
     * @param matrix     Matrix to unpack 矩阵解包
     * @param whichValue Which value from Matrix.M* to return 矩阵的哪个值。M *返回
     * @return returned value 返回值
     */
    public float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(mMatrixValues);
        return mMatrixValues[whichValue];
    }

    /**
     * Resets the Matrix back to FIT_CENTER, and then displays its contents
     * 将矩阵重置为FIT_CENTER，然后显示其内容
     */
    private void resetMatrix() {
        mSuppMatrix.reset();
        setRotationBy(mBaseRotation);
        setImageViewMatrix(getDrawMatrix());
        checkMatrixBounds();
    }

    private void setImageViewMatrix(Matrix matrix) {
        mImageView.setImageMatrix(matrix);
        /*
        * Call MatrixChangedListener if needed
        * 如果需要，调用MatrixChangedListener
        * */
        if (mMatrixChangeListener != null) {
            RectF displayRect = getDisplayRect(matrix);
            if (displayRect != null) {
                mMatrixChangeListener.onMatrixChanged(displayRect);
            }
        }
    }

    /**
     * Helper method that simply checks the Matrix, and then displays the result
     * 帮助方法，它只是检查矩阵，然后显示结果
     */
    private void checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            setImageViewMatrix(getDrawMatrix());
        }
    }

    /**
     * Helper method that maps the supplied Matrix to the current Drawable
     * 帮助方法，该方法将提供的矩阵映射到当前可绘制的
     * @param matrix - Matrix to map Drawable against 映射可绘制的矩阵
     * @return RectF - Displayed Rectangle 显示的矩形
     */
    private RectF getDisplayRect(Matrix matrix) {
        Drawable d = mImageView.getDrawable();
        if (d != null) {
            mDisplayRect.set(0, 0, d.getIntrinsicWidth(),
                    d.getIntrinsicHeight());
            matrix.mapRect(mDisplayRect);
            return mDisplayRect;
        }
        return null;
    }

    /**
     * Calculate Matrix for FIT_CENTER
     * 计算矩阵FIT_CENTER
     * @param drawable - Drawable being displayed
     */
    private void updateBaseMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        mBaseMatrix.reset();
        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;
        if (mScaleType == ScaleType.CENTER) {
            mBaseMatrix.postTranslate((viewWidth - drawableWidth) / 2F,
                    (viewHeight - drawableHeight) / 2F);

        } else if (mScaleType == ScaleType.CENTER_CROP) {
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else if (mScaleType == ScaleType.CENTER_INSIDE) {
            float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else {
            RectF mTempSrc = new RectF(0, 0, drawableWidth, drawableHeight);
            RectF mTempDst = new RectF(0, 0, viewWidth, viewHeight);
            if ((int) mBaseRotation % 180 != 0) {
                mTempSrc = new RectF(0, 0, drawableHeight, drawableWidth);
            }
            switch (mScaleType) {
                case FIT_CENTER:
                    /*
                    * for long image, 图片高>view高，比例也大于view的高/宽，则认为是长图
                    * For long image, if the image is high > and the view is high, and the proportion is also greater than the height/width of the view, it is considered as a long image
                    * */
                    if (drawableHeight > viewHeight && drawableHeight * 1f / drawableWidth > viewHeight * 1f / viewWidth) {
//                        mBaseMatrix.postScale(widthScale, widthScale);
//                        setScale(widthScale);
                        /*
                        * 长图特殊处理，宽度撑满屏幕，并且顶部对齐
                        * Long images are special, the width fills the screen, and the top is aligned
                        * */
                        isLongImage = true;
                        mBaseMatrix.setRectToRect(mTempSrc, new RectF(0, 0, viewWidth, drawableHeight * widthScale), ScaleToFit.START);
                    } else {
                        mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.CENTER);
                    }
                    break;
                case FIT_START:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.START);
                    break;
                case FIT_END:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.END);
                    break;
                case FIT_XY:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.FILL);
                    break;
                default:
                    break;
            }
        }
        resetMatrix();
    }

    private boolean checkMatrixBounds() {
        final RectF rect = getDisplayRect(getDrawMatrix());
        if (rect == null) {
            return false;
        }
        final float height = rect.height(), width = rect.width();
        float deltaX = 0, deltaY = 0;
        final int viewHeight = getImageViewHeight(mImageView);
        if (height <= viewHeight && rect.top >= 0) {
            switch (mScaleType) {
                case FIT_START:
                    deltaY = -rect.top;
                    break;
                case FIT_END:
                    deltaY = viewHeight - height - rect.top;
                    break;
                default:
                    deltaY = (viewHeight - height) / 2 - rect.top;
                    break;
            }
            mVerticalScrollEdge = VERTICAL_EDGE_BOTH;
        } else if (rect.top >= 0) {
            mVerticalScrollEdge = VERTICAL_EDGE_TOP;
            deltaY = -rect.top;
        } else if (rect.bottom <= viewHeight) {
            mVerticalScrollEdge = VERTICAL_EDGE_BOTTOM;
            deltaY = viewHeight - rect.bottom;
        } else {
            mVerticalScrollEdge = VERTICAL_EDGE_NONE;
        }
        final int viewWidth = getImageViewWidth(mImageView);
//        Log.e("tag", "rect: " + rect.toShortString() + " viewWidth: " + viewWidth + " viewHeight: " + viewHeight
//                + " recLeft: " + rect.left + "  recRight: " + rect.right + " mHorizontalScrollEdge: " + mHorizontalScrollEdge);
        if (width <= viewWidth && rect.left >= 0) {
            switch (mScaleType) {
                case FIT_START:
                    deltaX = -rect.left;
                    break;
                case FIT_END:
                    deltaX = viewWidth - width - rect.left;
                    break;
                default:
                    deltaX = (viewWidth - width) / 2 - rect.left;
                    break;
            }
            mHorizontalScrollEdge = HORIZONTAL_EDGE_BOTH;
        } else if (rect.left >= 0) {
            mHorizontalScrollEdge = HORIZONTAL_EDGE_LEFT;
            deltaX = -rect.left;
        } else if (rect.right <= viewWidth) {
            deltaX = viewWidth - rect.right;
            mHorizontalScrollEdge = HORIZONTAL_EDGE_RIGHT;
        } else {
            mHorizontalScrollEdge = HORIZONTAL_EDGE_NONE;
        }
        /*
        * Finally actually translate the matrix
        * 最后转换矩阵
        * */
        mSuppMatrix.postTranslate(deltaX, deltaY);
        return true;
    }

    private int getImageViewWidth(ImageView imageView) {
        return imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

    private void cancelFling() {
        if (mCurrentFlingRunnable != null) {
            mCurrentFlingRunnable.cancelFling();
            mCurrentFlingRunnable = null;
        }
    }

    private class AnimatedZoomRunnable implements Runnable {

        private final float mFocalX, mFocalY;
        private final long mStartTime;
        private final float mZoomStart, mZoomEnd;

        public AnimatedZoomRunnable(final float currentZoom, final float targetZoom,
                                    final float focalX, final float focalY) {
            mFocalX = focalX;
            mFocalY = focalY;
            mStartTime = System.currentTimeMillis();
            mZoomStart = currentZoom;
            mZoomEnd = targetZoom;
        }

        @Override
        public void run() {
            float t = interpolate();
            float scale = mZoomStart + t * (mZoomEnd - mZoomStart);
            float deltaScale = scale / getScale();
            onGestureListener.onScale(deltaScale, mFocalX, mFocalY);
            /*
            * We haven't hit our target scale yet, so post ourselves again
            * 我们还没有达到我们的目标规模，所以再次张贴我们自己
            * */
            if (t < 1f) {
                Compat.postOnAnimation(mImageView, this);
            }
        }

        private float interpolate() {
            float t = 1f * (System.currentTimeMillis() - mStartTime) / mZoomDuration;
            t = Math.min(1f, t);
            t = mInterpolator.getInterpolation(t);
            return t;
        }
    }

    private class FlingRunnable implements Runnable {

        private final OverScroller mScroller;
        private int mCurrentX, mCurrentY;

        public FlingRunnable(Context context) {
            mScroller = new OverScroller(context);
        }

        /**
         * Cancel fling.
         * 取消fling
         */
        public void cancelFling() {
            mScroller.forceFinished(true);
        }

        /**
         * Fling.
         * 扔
         * @param viewWidth  the view width 宽
         * @param viewHeight the view height 高
         * @param velocityX  the velocity x  速度x
         * @param velocityY  the velocity y  速度y
         */
        public void fling(int viewWidth, int viewHeight, int velocityX,
                          int velocityY) {
            final RectF rect = getDisplayRect();
            if (rect == null) {
                return;
            }
            final int startX = Math.round(-rect.left);
            final int minX, maxX, minY, maxY;
            if (viewWidth < rect.width()) {
                minX = 0;
                maxX = Math.round(rect.width() - viewWidth);
            } else {
                minX = maxX = startX;
            }
            final int startY = Math.round(-rect.top);
            if (viewHeight < rect.height()) {
                minY = 0;
                maxY = Math.round(rect.height() - viewHeight);
            } else {
                minY = maxY = startY;
            }
            mCurrentX = startX;
            mCurrentY = startY;
            /*
            * If we actually can move, fling the scroller
            * 如果我们真的能移动，扔滚动条
            * */
            if (startX != maxX || startY != maxY) {
                mScroller.fling(startX, startY, velocityX, velocityY, minX,
                        maxX, minY, maxY, 0, 0);
            }
        }

        @Override
        public void run() {
            if (mScroller.isFinished()) {
                /*
                * remaining post that should not be handled
                * 其余不应该处理的职位
                * */
                return;
            }
            if (mScroller.computeScrollOffset()) {
                final int newX = mScroller.getCurrX();
                final int newY = mScroller.getCurrY();
                mSuppMatrix.postTranslate(mCurrentX - newX, mCurrentY - newY);
                checkAndDisplayMatrix();
                mCurrentX = newX;
                mCurrentY = newY;
                /*
                * Post On animation
                * 动画
                * */
                Compat.postOnAnimation(mImageView, this);
            }
        }
    }
}
