
package com.link.game.wolfman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.link.game.wolfman.model.GameNum;
import com.link.game.wolfman.model.GameRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleOperateActivity extends Activity implements OnItemClickListener,
        OnItemLongClickListener {
    private List<GameRole> mCurrentList = new ArrayList<GameRole>();

    private RoleAdapter mRoleAdapter;

    @InjectView(R.id.roleGV)
    GridView mRoleGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_operate_activity);
        ButterKnife.inject(this);
        dealWithIntent();
        mRoleAdapter = new RoleAdapter();
        mRoleGV.setAdapter(mRoleAdapter);
        mRoleGV.setOnItemClickListener(this);
        mRoleGV.setOnItemLongClickListener(this);
    }

    @OnClick(R.id.restartBTN)
    public void onRestartGame() {
        Intent it = new Intent(this, RoleConfirmActivity.class);
        it.putExtra(GameNum.GAME_NUM, (Serializable) mCurrentList);
        it.putExtra(GameNum.GAME_RESTART, true);
        finish();
        startActivity(it);
    }

    @OnClick(R.id.overBTN)
    public void onOver() {
        finish();
    }

    private void dealWithIntent() {
        Intent it = getIntent();
        mCurrentList = (List<GameRole>) it.getSerializableExtra(GameNum.GAME_NUM);
    }

    class RoleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCurrentList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCurrentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(RoleOperateActivity.this).inflate(
                        R.layout.role_operate_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            convertView.setLayoutParams(new ListView.LayoutParams(-1, mRoleGV.getHeight() / 3));
            //            GameRole gr = (GameRole) getItem(position);
            holder.roleTV.setText("玩家 " + (position + 1));
            holder.roleIV.setBackgroundResource(R.drawable.bg_card);
            return convertView;
        }

        public class ViewHolder {
            @InjectView(R.id.roleIV)
            ImageView roleIV;

            @InjectView(R.id.playerTV)
            TextView roleTV;

            public ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        GameRole gr = (GameRole) mRoleAdapter.getItem(position);
        gr.setKill(gr.isKill() ? false : true);
        TextView killTV = (TextView) view.findViewById(R.id.killTV);
        killTV.setVisibility(gr.isKill() ? View.VISIBLE : View.INVISIBLE);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GameRole gr = (GameRole) mRoleAdapter.getItem(position);
        gr.setShow(gr.isShow() ? false : true);
        ImageView roleIV = (ImageView) view.findViewById(R.id.roleIV);
        roleIV.setBackgroundResource(gr.isShow() ? gr.getId() : R.drawable.bg_card);
    }
}
