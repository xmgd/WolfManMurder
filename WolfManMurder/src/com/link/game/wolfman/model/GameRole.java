
package com.link.game.wolfman.model;

import com.link.game.wolfman.activity.R;

import java.io.Serializable;

public enum GameRole implements Serializable {
    XIANZHI("先知", R.drawable.bg_xianzhi), QIUBITE("丘比特", R.drawable.bg_qiubite), NVWU("女巫",
            R.drawable.bg_nvwu), WOLF("狼人", R.drawable.bg_wolf), MAN("村民", R.drawable.bg_man), HUNTER(
            "猎人", R.drawable.bg_hunter), PROTECT("守卫", R.drawable.bg_protect);
    private static final long serialVersionUID = 1L;

    private String name;

    private int imageId = R.drawable.bg_card;

    private boolean isKill = false;

    private boolean isShow = false;

    GameRole(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public int getId() {
        return this.imageId;
    }

    public boolean isKill() {
        return isKill;
    }

    public void setKill(boolean isKill) {
        this.isKill = isKill;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public String toString() {
        return String.valueOf(this.name);
    }
}
