/*
 Copyright 2011, 2012 Chris Banes.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.meishe.libbase.pop.photoview;

/**
 * The interface On gesture listener.
 * 动作监听接口
 */
interface OnGestureListener {

    /**
     * On drag.
     * 拖动
     * @param dx the dx dx
     * @param dy the dy dy
     */
    void onDrag(float dx, float dy);

    /**
     * On fling.
     * 扔
     * @param startX    the start x 起点x
     * @param startY    the start y 起点y
     * @param velocityX the velocity x 速度x
     * @param velocityY the velocity y 速度y
     */
    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    /**
     * On scale.
     * 缩放
     * @param scaleFactor the scale factor 缩放因素
     * @param focusX      the focus x  聚焦x
     * @param focusY      the focus y  聚焦y
     */
    void onScale(float scaleFactor, float focusX, float focusY);

}