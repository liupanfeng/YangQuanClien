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
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;

import androidx.appcompat.widget.AppCompatImageView;

;

/**
 * A modified version for https://github.com/chrisbanes/PhotoView.
 * 修改版https://github.com/chrisbanes/PhotoView
 * 图片view类
 * Pictures of the view class
 */
@SuppressWarnings("unused")
public class PhotoView extends AppCompatImageView {

    /**
     * The Attacher.
     * 连接器
     */
    public PhotoViewAttacher attacher;
    private ScaleType pendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        attacher = new PhotoViewAttacher(this);
        /*
        * We always pose as a Matrix scale type, though we can change to another scale type via the attacher
        * 虽然我们可以通过攻击器更改为另一种规模类型，但我们总是以矩阵规模类型的姿态
        * */
        super.setScaleType(ScaleType.MATRIX);
        /*
        * apply the previously applied scale type
        * 应用先前应用的缩放类型
        * */
        if (pendingScaleType != null) {
            setScaleType(pendingScaleType);
            pendingScaleType = null;
        }
    }

    /**
     * Get the current {@link PhotoViewAttacher} for this view. Be wary of holding on to references
     * to this attacher, as it has a reference to this view, which, if a reference is held in the
     * wrong place, can cause memory leaks.
     * 获取此视图的当前{@link PhotoViewAttacher}。对参考资料保持警惕
     * 因为它有一个对这个视图的引用，如果一个引用在
     * 错误的位置，可能导致内存泄漏
     * @return the attacher. 连接器
     */
    public PhotoViewAttacher getAttacher() {
        return attacher;
    }

    @Override
    public ScaleType getScaleType() {
        return attacher.getScaleType();
    }

    @Override
    public Matrix getImageMatrix() {
        return attacher.getImageMatrix();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        attacher.setOnLongClickListener(l);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        attacher.setOnClickListener(l);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (attacher == null) {
            pendingScaleType = scaleType;
        } else {
            attacher.setScaleType(scaleType);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        /*
        * setImageBitmap calls through to this method
        * setImageBitmap调用此方法
        * */
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            attacher.update();
        }
        return changed;
    }

    public void setRotationTo(float rotationDegree) {
        attacher.setRotationTo(rotationDegree);
    }

    public void setRotationBy(float rotationDegree) {
        attacher.setRotationBy(rotationDegree);
    }

    /**
     * Is zoomable boolean.
     * 是否可缩放
     * @return the boolean
     */
    public boolean isZoomable() {
        return attacher.isZoomable();
    }

    public void setZoomable(boolean zoomable) {
        attacher.setZoomable(zoomable);
    }

    public RectF getDisplayRect() {
        return attacher.getDisplayRect();
    }

    /**
     * Gets display matrix.
     * 显示矩阵
     * @param matrix the matrix
     */
    public void getDisplayMatrix(Matrix matrix) {
        attacher.getDisplayMatrix(matrix);
    }

    @SuppressWarnings("UnusedReturnValue") public boolean setDisplayMatrix(Matrix finalRectangle) {
        return attacher.setDisplayMatrix(finalRectangle);
    }

    public void getSuppMatrix(Matrix matrix) {
        attacher.getSuppMatrix(matrix);
    }

    /**
     * Sets supp matrix.
     *增刊矩阵
     * @param matrix the matrix
     * @return the supp matrix
     */
    public boolean setSuppMatrix(Matrix matrix) {
        return attacher.setDisplayMatrix(matrix);
    }

    /**
     * Gets minimum scale.
     * 得到最小缩放
     * @return the minimum scale
     */
    public float getMinimumScale() {
        return attacher.getMinimumScale();
    }

    /**
     * Gets medium scale.
     * 得到中等缩放
     * @return the medium scale
     */
    public float getMediumScale() {
        return attacher.getMediumScale();
    }

    /**
     * Gets maximum scale.
     *得到最大的缩放
     * @return the maximum scale
     */
    public float getMaximumScale() {
        return attacher.getMaximumScale();
    }

    /**
     * Gets scale.
     * 获得缩放
     * @return the scale
     */
    public float getScale() {
        return attacher.getScale();
    }

    /**
     * Sets allow parent intercept on edge.
     * 设置允许父节点在边缘拦截
     * @param allow the allow 允许
     */
    public void setAllowParentInterceptOnEdge(boolean allow) {
        attacher.setAllowParentInterceptOnEdge(allow);
    }

    /**
     * Sets minimum scale.
     * 设定了最低缩放
     * @param minimumScale the minimum scale 最小缩放
     */
    public void setMinimumScale(float minimumScale) {
        attacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        attacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        attacher.setMaximumScale(maximumScale);
    }

    /**
     * Sets scale levels.
     * 设置缩放水平
     * @param minimumScale the minimum scale 最小
     * @param mediumScale  the medium scale 中等
     * @param maximumScale the maximum scale 最大
     */
    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        attacher.setScaleLevels(minimumScale, mediumScale, maximumScale);
    }

    /**
     * Sets on matrix change listener.
     * 矩阵的变化监听
     * @param listener the listener
     */
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        attacher.setOnMatrixChangeListener(listener);
    }

    /**
     * Sets on photo tap listener.
     * 照片点击监听
     * @param listener the listener
     */
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        attacher.setOnPhotoTapListener(listener);
    }

    /**
     * Sets on outside photo tap listener.
     * 外部照片点击监听
     * @param listener the listener
     */
    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener listener) {
        attacher.setOnOutsidePhotoTapListener(listener);
    }

    /**
     * Sets on view tap listener.
     * 视图点击监听
     * @param listener the listener
     */
    public void setOnViewTapListener(OnViewTapListener listener) {
        attacher.setOnViewTapListener(listener);
    }

    /**
     * Sets on view drag listener.
     * 视图拖动监听
     * @param listener the listener
     */
    public void setOnViewDragListener(OnViewDragListener listener) {
        attacher.setOnViewDragListener(listener);
    }

    public void setScale(float scale) {
        attacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        attacher.setScale(scale, animate);
    }

    /**
     * Sets scale.
     * 设置缩放
     * @param scale   the scale 缩放
     * @param focalX  the focal x x
     * @param focalY  the focal y y
     * @param animate the animate 动画
     */
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        attacher.setScale(scale, focalX, focalY, animate);
    }

    /**
     * Sets zoom transition duration.
     * 变焦转场时间
     * @param milliseconds the milliseconds  毫秒
     */
    public void setZoomTransitionDuration(int milliseconds) {
        attacher.setZoomTransitionDuration(milliseconds);
    }

    /**
     * Sets on double tap listener.
     * 双击监听
     * @param onDoubleTapListener the on double tap listener 双击监听
     */
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        attacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    /**
     * Sets on scale change listener.
     * 缩放改变监听
     * @param onScaleChangedListener the on scale changed listener 缩放改变监听
     */
    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        attacher.setOnScaleChangeListener(onScaleChangedListener);
    }

    /**
     * Sets on single fling listener.
     * 单扔监听
     * @param onSingleFlingListener the on single fling listener
     */
    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        attacher.setOnSingleFlingListener(onSingleFlingListener);
    }
}
