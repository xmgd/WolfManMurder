
package com.link.game.wolfman.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.link.game.wolfman.activity.R;
import com.link.game.wolfman.anim.MyAnim;

public class MainActivity extends Activity implements OnClickListener {
    private static final long DURATION_TIME = 3000;

    private static final boolean IS_FILL_AFTER = false;

    private FrameLayout container;

    private ImageView ivMain;

    private ImageView ivMain1;

    //标记控件的宽高
    private float width;

    private float height;

    private float translateX;

    private float translateY;

    //放大倍数
    private float num;

    //获取标题栏和状态栏的高度
    private int contentTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        addListener();
    }

    private void setupView() {
        container = (FrameLayout) findViewById(R.id.container);
        ivMain = (ImageView) findViewById(R.id.iv_main);
        ivMain1 = (ImageView) findViewById(R.id.iv_main1);
        //获取屏幕的宽度和高度
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        Log.i("info", "获取的屏幕的宽度是：" + width);
        height = display.getHeight();
        Log.i("info", "获取的屏幕的高度是：" + height);
    }

    private void addListener() {
        ivMain.setOnClickListener(this);
        ivMain1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main:
                /*
                 * 计算图片应该缩放的倍数，考虑到横竖屏和标题栏状态栏
                 * 应该是用宽和去掉标题栏、状态栏的高进行比较，找到较小值，并且除以图片的宽度或者高度就能够知道图片应该缩放的倍数
                 */
                num = 3;
                Log.i("info", "应该扩大的倍数是：" + num);
                //获取能显示动画的布局的中点坐标
                float screenX = width / 2;
                float screenY = height / 2 + contentTop / 2;
                Log.i("info", "能显示内容的布局的中点横坐标为：" + screenX + ",纵坐标为：" + screenY);
                //获取图片的宽高的一半
                float centerY = container.getHeight() / 2;
                float centerX = container.getWidth() / 2;
                //float centerY = container.getHeight();
                Log.i("info", "图片的宽度的一半：" + centerX + ",图片的高度的一半：" + centerY);
                //获取图片的中心点坐标
                int[] location = new int[2];
                container.getLocationOnScreen(location);
                float x = location[0] + centerX;
                float y = location[1] + centerY;
                Log.i("info", "图片的横坐标是：" + location[0] + ",图片的纵坐标是：" + location[1]);
                Log.i("info", "图片的中点横坐标是" + x + "纵坐标是" + y);
                //获取到图片要偏移的坐标
                translateX = screenX - x;
                Log.i("info", "要偏移的横坐标是" + translateX);
                translateY = screenY - y;
                Log.i("info", "要偏移的纵坐标是" + translateY);
                //为ivMain设置自定义动画
                MyAnim myAnim = new MyAnim(centerX, centerY, 0, 0, 0, 0, 90, false, IS_FILL_AFTER,
                        2, DURATION_TIME, new AccelerateDecelerateInterpolator());
                //创建伸缩动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, num / 2, 1.0f, num / 2,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(DURATION_TIME);
                scaleAnimation.setFillAfter(IS_FILL_AFTER);
                TranslateAnimation translateAnimation = new TranslateAnimation(0, translateX / 2,
                        0, translateY / 2);
                translateAnimation.setDuration(DURATION_TIME);
                translateAnimation.setFillAfter(IS_FILL_AFTER);
                //创建动画集
                AnimationSet animSet = new AnimationSet(true);
                animSet.addAnimation(scaleAnimation);
                animSet.addAnimation(translateAnimation);
                animSet.addAnimation(myAnim);
                animSet.setFillAfter(IS_FILL_AFTER);
                /**
                 * 当第一幅图片旋转完成之后，让第一幅图片消失，并且显示第二幅图片
                 */
                animSet.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        container.post(new SwapViews());
                        Log.i("info", "animation的Listener方法被调用了");
                    }
                });
                container.startAnimation(animSet);
                break;
        }
    }

    private final class SwapViews implements Runnable {

        @Override
        public void run() {
            if (ivMain.getVisibility() == View.VISIBLE) {
                Log.i("info", "设置ImageView的显示");
                ivMain.setVisibility(View.GONE);
                ivMain1.setVisibility(View.VISIBLE);
                //获取能显示动画的布局的中点坐标
                float screenX = width / 2;
                float screenY = height / 2 + contentTop / 2;
                Log.i("info", "能显示内容的布局的中点横坐标为：" + screenX + ",纵坐标为：" + screenY);
                //获取图片的宽高
                //float centerX = container.getWidth()/2;
                float centerY = container.getHeight() / 2;
                float centerX = container.getWidth() / 2;
                //float centerY = container.getHeight();
                Log.i("info", "图片的宽度：" + centerX + ",图片的高度：" + centerY);
                //获取图片的中心点坐标
                int[] location = new int[2];
                container.getLocationOnScreen(location);
                float x = location[0] + centerX;
                float y = location[1] + centerY;
                Log.i("info", "图片的中点横坐标是" + x + "纵坐标是" + y);
                //获取到图片要偏移的坐标
                translateX = screenX - x;
                Log.i("info", "要偏移的横坐标是" + translateX);
                translateY = screenY - y;
                Log.i("info", "要偏移的纵坐标是" + translateY);

                MyAnim myAnim = new MyAnim(centerX, centerY, 0, 0, 0, 90, 0, true, IS_FILL_AFTER,
                        2, DURATION_TIME, new LinearInterpolator());
                //创建伸缩动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(num / 2, num, num / 2, num,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(DURATION_TIME);
                TranslateAnimation translateAnimation = new TranslateAnimation(translateX / 2,
                        translateX, translateY / 2, translateY);
                translateAnimation.setDuration(DURATION_TIME);
                translateAnimation.setFillAfter(IS_FILL_AFTER);
                //创建动画集
                AnimationSet animSet = new AnimationSet(true);
                animSet.addAnimation(scaleAnimation);
                animSet.addAnimation(translateAnimation);
                animSet.addAnimation(myAnim);
                animSet.setFillAfter(IS_FILL_AFTER);

                animSet.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                container.startAnimation(animSet);
            }
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        View v = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        contentTop = v.getTop();
        Log.i("info", "状态栏和标题栏的高度为：" + contentTop);
    }

}
