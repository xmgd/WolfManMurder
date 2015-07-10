
package com.link.game.wolfman.activity;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.link.game.wolfman.model.GameNum;
import com.link.game.wolfman.model.GameRole;

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

    GameNum mGameNum;

    private boolean mHasWatched = false;

    private int mCurrentPlayer = 0;

    private List<GameRole> mPlayerList;

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
        mGameNum = (GameNum) it.getSerializableExtra(GameNum.GAME_NUM);
        mPlayerList = new ArrayList<GameRole>();
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

    @OnClick(R.id.watchBTN)
    public void watch() {
        mHasWatched = true;
        mRoleTV.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.nextBTN)
    public void next() {
        if (!mHasWatched) {
            Toast.makeText(this, "请查看你的身份！", Toast.LENGTH_SHORT).show();
            return;
        }
        updateInfo();
    }

    private void updateInfo() {
        mHasWatched = false;
        mCurrentPlayer += 1;
        mRoleTV.setVisibility(View.INVISIBLE);
        mRoleBgTV.setBackgroundResource(color.black);
        if (mCurrentPlayer > mGameNum.getTotalNum()) {
            startGame();
            return;
        }
        mPlayTV.setText("玩家 " + mCurrentPlayer);
        Random ran = new Random();
        mCurrentRole = mPlayerList.get(ran.nextInt(mPlayerList.size()));
        mRoleTV.setText(mCurrentRole.toString());
        mPlayerList.remove(mCurrentRole);
    }

    private void startGame() {
        Toast.makeText(this, "游戏开始", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
