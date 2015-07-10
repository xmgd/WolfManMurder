
package com.link.game.wolfman;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

import com.link.game.wolfman.activity.R;

public class MainActivity extends Activity {

    @OnClick(R.id.startBTN)
    public void startBTN() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }
}
