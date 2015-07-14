
package com.link.game.wolfman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.link.game.wolfman.anim.MyAnim;
import com.link.game.wolfman.model.GameNum;
import com.link.game.wolfman.model.GameRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoleConfirmActivity extends Activity {
    @InjectView(R.id.roleTV)
    TextView mRoleTV;

    @InjectView(R.id.rolebgTV)
    TextView mRoleBgTV;

    @InjectView(R.id.playTV)
    TextView mPlayTV;

    @InjectView(R.id.nextStepTV)
    TextView mNextStepTV;

    GameNum mGameNum;

    private boolean mHasWatched = false;

    private int mCurrentPlayer = 0;

    private List<GameRole> mPlayerList;

    private final List<GameRole> mCurrentList = new ArrayList<GameRole>();

    private GameRole mCurrentRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_confirm_activity);
        ButterKnife.inject(this);
        dealWithIntent();
        updateInfo();
    }

    private void dealWithIntent() {
        mGameNum = new GameNum();
        Intent it = getIntent();
        mPlayerList = new ArrayList<GameRole>();
        if (it.getBooleanExtra(GameNum.GAME_RESTART, false)) {
            mPlayerList = (List<GameRole>) it.getSerializableExtra(GameNum.GAME_NUM);
            mGameNum.setTotalNum(mPlayerList.size());
            return;
        }
        mGameNum = (GameNum) it.getSerializableExtra(GameNum.GAME_NUM);
        mPlayerList.add(GameRole.XIANZHI);
        mPlayerList.add(GameRole.QIUBITE);
        mPlayerList.add(GameRole.NVWU);
        for (int i = 0; i < mGameNum.getWolfNum(); i++) {
            mPlayerList.add(GameRole.WOLF);
        }
        for (int i = 0; i < mGameNum.getManNum(); i++) {
            mPlayerList.add(GameRole.MAN);
        }
        if (mGameNum.getHunterNum() > 0) {
            mPlayerList.add(GameRole.HUNTER);
        }
        if (mGameNum.getProtectNum() > 0) {
            mPlayerList.add(GameRole.PROTECT);
        }
    }

    /**
     * 此为利用一个矩阵旋转
     */
    @OnClick(R.id.watchBTN)
    public void watch() {
        if (mHasWatched) {
            return;
        }
        mRoleTV.setVisibility(View.VISIBLE);
        //        Animation anim = AnimationUtils.loadAnimation(this, R.drawable.card_rotate);
        //        RotateAnimation ra = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
        //                Animation.ABSOLUTE, 0);
        Rotate3d r3 = new Rotate3d();
        r3.setCenter(mRoleBgTV.getMeasuredWidth() / 2, mRoleBgTV.getMeasuredHeight() / 2);
        r3.setDuration(1500);
        r3.setInterpolator(this, android.R.anim.decelerate_interpolator);
        r3.setFillAfter(true);
        mRoleBgTV.startAnimation(r3);
        imageHandler.sendEmptyMessageDelayed(101, 400);

    }

    private static final long DURATION_TIME = 3000;

    private static final boolean IS_FILL_AFTER = false;

    //    @OnClick(R.id.watchBTN)
    public void watch1() {
        MyAnim myAnim = new MyAnim(mRoleBgTV.getMeasuredWidth() / 2,
                mRoleBgTV.getMeasuredHeight() / 2, 0, 0, 0, 0, 90, false, IS_FILL_AFTER, 2,
                DURATION_TIME, new AccelerateDecelerateInterpolator());
        //创建伸缩动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3 / 2, 1.0f, 3 / 2,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(DURATION_TIME);
        scaleAnimation.setFillAfter(IS_FILL_AFTER);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,
                mRoleBgTV.getMeasuredWidth() / 2, 0, mRoleBgTV.getMeasuredHeight() / 2);
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
                mRoleBgTV.post(new SwapViews());
            }
        });
        mRoleBgTV.startAnimation(animSet);
    }

    private final class SwapViews implements Runnable {

        @Override
        public void run() {
            imageHandler.sendEmptyMessage(101);
            MyAnim myAnim = new MyAnim(mRoleBgTV.getMeasuredWidth() / 2,
                    mRoleBgTV.getMeasuredHeight() / 2, 0, 0, 0, 90, 0, true, IS_FILL_AFTER, 2,
                    DURATION_TIME, new LinearInterpolator());
            //创建伸缩动画
            ScaleAnimation scaleAnimation = new ScaleAnimation(3 / 2, 3, 3 / 2, 3,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(DURATION_TIME);
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    mRoleBgTV.getMeasuredWidth() / 2, mRoleBgTV.getMeasuredWidth(),
                    mRoleBgTV.getMeasuredHeight() / 2, mRoleBgTV.getMeasuredHeight());
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
            mRoleBgTV.startAnimation(animSet);
        }
    }

    private final Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 101) {
                mHasWatched = true;
                switch (mCurrentRole) {
                    case HUNTER:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_hunter);
                        break;
                    case NVWU:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_nvwu);
                        break;
                    case MAN:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_man);
                        break;
                    case PROTECT:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_protect);
                        break;
                    case QIUBITE:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_qiubite);
                        break;
                    case WOLF:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_wolf);
                        break;
                    case XIANZHI:
                        mRoleBgTV.setBackgroundResource(R.drawable.bg_xianzhi);
                        break;
                    default:
                        break;
                }
            }
        };
    };

    class Rotate3d extends Animation {
        private float mCenterX = 0;

        private float mCenterY = 0;

        public void setCenter(float centerX, float centerY) {
            mCenterX = centerX;
            mCenterY = centerY;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            Camera camera = new Camera();
            camera.save();
            camera.rotateY(180 * interpolatedTime);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-mCenterX, -mCenterY);
            matrix.postTranslate(mCenterX, mCenterY);
        }
    }

    @OnClick(R.id.nextBTN)
    public void next(Button nextBtn) {
        if (imageHandler.hasMessages(101)) {
            return;
        }
        if (!mHasWatched) {
            Toast.makeText(this, "请查看你的身份！", Toast.LENGTH_SHORT).show();
            return;
        }
        updateInfo();
        nextBtn.setText(mCurrentPlayer == mGameNum.getTotalNum() ? "传给主持人" : "传给下一个玩家");
        mNextStepTV.setText(mCurrentPlayer == mGameNum.getTotalNum() ? "第二步：传给主持人" : "第二步：传给下一个玩家");
    }

    private void updateInfo() {
        mHasWatched = false;
        mCurrentPlayer += 1;
        mRoleTV.setVisibility(View.INVISIBLE);
        mRoleBgTV.setBackgroundResource(R.drawable.bg_card);
        if (mCurrentPlayer > mGameNum.getTotalNum()) {
            startGame();
            return;
        }
        mPlayTV.setText("玩家 " + mCurrentPlayer);
        Random ran = new Random();
        mCurrentRole = mPlayerList.get(ran.nextInt(mPlayerList.size()));
        mRoleTV.setText(mCurrentRole.toString());
        mPlayerList.remove(mCurrentRole);
        mCurrentList.add(mCurrentRole);
    }

    private void startGame() {
        Toast.makeText(this, "游戏开始", Toast.LENGTH_LONG).show();
        Intent it = new Intent(this, RoleOperateActivity.class);
        it.putExtra(GameNum.GAME_NUM, (Serializable) mCurrentList);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
