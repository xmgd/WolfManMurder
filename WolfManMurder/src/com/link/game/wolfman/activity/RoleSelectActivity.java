
package com.link.game.wolfman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.link.game.wolfman.model.GameNum;

public class RoleSelectActivity extends Activity {
    private final static int MAX_PLAYER = 14;

    private final static int MIN_PLAYER = 8;

    @InjectView(R.id.numTV)
    TextView mNumTV;

    @InjectView(R.id.numWolfTV)
    TextView mNumWolfTV;

    @InjectView(R.id.numManTV)
    TextView mNumManTV;

    @InjectView(R.id.hunterTV)
    TextView mHunterTV;

    @InjectView(R.id.protectTV)
    TextView mProtectTV;

    @InjectView(R.id.reduceTV)
    TextView mReduceTV;

    @InjectView(R.id.addTV)
    TextView mAddTV;

    @InjectView(R.id.reduceWolfTV)
    TextView mReduceWolfTV;

    @InjectView(R.id.addWolfTV)
    TextView mAddWolfTV;

    @InjectView(R.id.reduceManTV)
    TextView mReduceManTV;

    @InjectView(R.id.addManTV)
    TextView mAddManTV;

    @InjectView(R.id.hunterCB)
    CheckBox mHunterCB;

    @InjectView(R.id.protectCB)
    CheckBox mProtectCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_select_activity);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.startBTN)
    public void startGame() {
        int player = Integer.parseInt(mNumTV.getText().toString());
        int wolf = Integer.parseInt(mNumWolfTV.getText().toString());
        int man = Integer.parseInt(mNumManTV.getText().toString());
        int other = 3;
        if (mHunterCB.isChecked()) {
            other += 1;
        }
        if (mProtectCB.isChecked()) {
            other += 1;
        }
        if (player != wolf + man + other) {
            Toast.makeText(this, "人数设置不正确！", Toast.LENGTH_LONG).show();
            return;
        }
        GameNum gn = new GameNum();
        gn.setTotalNum(player);
        gn.setManNum(man);
        gn.setWolfNum(wolf);
        gn.setHunterNum(mHunterCB.isChecked() ? 1 : 0);
        gn.setProtectNum(mProtectCB.isChecked() ? 1 : 0);
        startActivity(new Intent(this, RoleConfirmActivity.class).putExtra(GameNum.GAME_NUM, gn));
    }

    @OnClick(R.id.reduceTV)
    public void reduce() {
        int num = Integer.parseInt(mNumTV.getText().toString());
        if (num <= MIN_PLAYER) {
            return;
        }
        num -= 1;
        mNumTV.setText(num + "");
        autoChangeNum(num);
    }

    @OnClick(R.id.addTV)
    public void add() {
        int num = Integer.parseInt(mNumTV.getText().toString());
        if (num >= MAX_PLAYER) {
            return;
        }
        num += 1;
        mNumTV.setText(num + "");
        autoChangeNum(num);
    }

    @OnClick(R.id.reduceWolfTV)
    public void reduceWolf() {
        int num = Integer.parseInt(mNumWolfTV.getText().toString());
        int hunter = mHunterCB.isChecked() ? 1 : 0;
        int protect = mProtectCB.isChecked() ? 1 : 0;
        if (num <= 1) {
            return;
        }
        num -= 1;
        mNumWolfTV.setText(num + "");
        mNumManTV.setText(Integer.parseInt(mNumTV.getText().toString()) - 3 - num - hunter
                - protect + "");
    }

    @OnClick(R.id.addWolfTV)
    public void addWolf() {
        int numTotal = Integer.parseInt(mNumTV.getText().toString());
        int num = Integer.parseInt(mNumWolfTV.getText().toString());
        int hunter = mHunterCB.isChecked() ? 1 : 0;
        int protect = mProtectCB.isChecked() ? 1 : 0;
        if (num >= numTotal - hunter - protect - 4) {
            return;
        }
        num += 1;
        mNumWolfTV.setText(num + "");
        mNumManTV.setText(numTotal - 3 - num - hunter - protect + "");
    }

    @OnClick(R.id.reduceManTV)
    public void reduceMan() {
        int num = Integer.parseInt(mNumManTV.getText().toString());
        int hunter = mHunterCB.isChecked() ? 1 : 0;
        int protect = mProtectCB.isChecked() ? 1 : 0;
        if (num <= 1) {
            return;
        }
        if (num >= num) {

        }
        num -= 1;
        mNumManTV.setText(num + "");
        mNumWolfTV.setText(Integer.parseInt(mNumTV.getText().toString()) - 3 - num - hunter
                - protect + "");
    }

    @OnClick(R.id.addManTV)
    public void addMan() {
        int numTotal = Integer.parseInt(mNumTV.getText().toString());
        int num = Integer.parseInt(mNumManTV.getText().toString());
        int hunter = mHunterCB.isChecked() ? 1 : 0;
        int protect = mProtectCB.isChecked() ? 1 : 0;
        if (num >= numTotal - hunter - protect - 4) {
            return;
        }
        num += 1;
        mNumManTV.setText(num + "");
        mNumWolfTV.setText(numTotal - 3 - num - hunter - protect + "");
    }

    @OnCheckedChanged(R.id.hunterCB)
    public void hunterCheck() {
        mHunterTV.setVisibility(mHunterCB.isChecked() ? View.VISIBLE : View.GONE);
    }

    @OnCheckedChanged(R.id.protectCB)
    public void protectCheck() {
        mProtectTV.setVisibility(mProtectCB.isChecked() ? View.VISIBLE : View.GONE);
    }

    private void autoChangeNum(int num) {
        switch (num) {
            case 8:
                mNumWolfTV.setText("2");
                mNumManTV.setText("3");
                mHunterCB.setChecked(false);
                mProtectCB.setChecked(false);
                break;
            case 9:
                mNumWolfTV.setText("2");
                mNumManTV.setText("3");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(false);
                break;
            case 10:
                mNumWolfTV.setText("2");
                mNumManTV.setText("3");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(true);
                break;
            case 11:
                mNumWolfTV.setText("3");
                mNumManTV.setText("3");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(true);
                break;
            case 12:
                mNumWolfTV.setText("3");
                mNumManTV.setText("4");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(true);
                break;
            case 13:
                mNumWolfTV.setText("3");
                mNumManTV.setText("5");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(true);
                break;
            case 14:
                mNumWolfTV.setText("3");
                mNumManTV.setText("6");
                mHunterCB.setChecked(true);
                mProtectCB.setChecked(true);
                break;
            default:
                break;
        }
    }
}
