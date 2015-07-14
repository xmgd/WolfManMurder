
package com.link.game.wolfman.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class MyAnim extends Animation {
    private final float centerX, centerY;

    private final float translateX, translateY, translateZ;

    private final float fromDegree, toDegree;

    private final boolean isReverse, isFillAfter;

    private final long durationTime;

    private final Interpolator interpolator;

    private final int rotate;

    private int direction;//direction为-1的时候为顺时针

    Camera camera = new Camera();

    /**
     * @param centerX
     * @param centerY
     * @param translateX X轴上应该平移的距离
     * @param translateY Y轴上应该平移的距离
     * @param translateZ Z轴上应该平移的距离
     * @param fromDegree 翻转开始时的角度
     * @param toDegree 翻转结束时的角度
     * @param isReverse 是否反向翻转（顺时针为正向）
     * @param isFillAfter 动画结束后是否停留在结束时的位置
     * @param rotate 围绕着哪个轴进行翻转（1,X轴；2,Y轴；3,Z轴）
     * @param durationTime 动画持续的时间
     * @param interpolator 动画效果
     */
    public MyAnim(float centerX, float centerY, float translateX, float translateY,
            float translateZ, float fromDegree, float toDegree, boolean isReverse,
            boolean isFillAfter, int rotate, long durationTime, Interpolator interpolator) {
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.translateX = translateX;
        this.translateY = translateY;
        this.translateZ = translateZ;
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
        this.isReverse = isReverse;
        this.isFillAfter = isFillAfter;
        this.rotate = rotate;
        this.durationTime = durationTime;
        this.interpolator = interpolator;
    }

    @Override
    /**
     * 获取当前view的宽度和高度，并且计算其宽高的中心位置
     * 设置动画的间隔
     */
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        setDuration(durationTime);
        setFillAfter(isFillAfter); //停留在移动后的位置
        setInterpolator(interpolator); //运行速度
    }

    @Override
    /**
     * interpolatedTime 代表了动画的时间进行比，不管动画实际的持续时间如何，当动画播放时，该参数总是自动从0变化到1.
     * t 该参数代表了不见动画在不同时刻对图形或组件的变形程度。
     */
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();

        camera.save();
        if (isReverse) {
            camera.translate(translateX * interpolatedTime, 0 - (translateY * interpolatedTime),
                    translateZ * interpolatedTime);
            direction = 1;
        } else {
            camera.translate(translateX * interpolatedTime, 0 - (translateY * interpolatedTime),
                    translateZ * (1.0f - interpolatedTime));
            direction = -1;
        }
        switch (rotate) {
            case 1:
                camera.rotateX(fromDegree + (toDegree - fromDegree) * interpolatedTime * direction); //中心是绕X轴旋转
                break;
            case 2:
                camera.rotateY(fromDegree + (toDegree - fromDegree) * interpolatedTime * direction); //中心是绕Y轴旋转
                break;
            case 3:
                camera.rotateZ(fromDegree + (toDegree - fromDegree) * interpolatedTime * direction); //中心是绕Z轴旋转
                break;
        }
        camera.getMatrix(matrix);//把我们的摄像头加在变换矩阵上
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
