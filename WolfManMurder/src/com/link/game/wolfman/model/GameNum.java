
package com.link.game.wolfman.model;

import java.io.Serializable;

public class GameNum implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String GAME_NUM = "game_num";

    public static final String GAME_RESTART = "game_restart";

    private int totalNum;

    private int wolfNum;

    private int manNum;

    private int hunterNum;

    private int protectNum;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getWolfNum() {
        return wolfNum;
    }

    public void setWolfNum(int wolfNum) {
        this.wolfNum = wolfNum;
    }

    public int getManNum() {
        return manNum;
    }

    public void setManNum(int manNum) {
        this.manNum = manNum;
    }

    public int getHunterNum() {
        return hunterNum;
    }

    public void setHunterNum(int hunterNum) {
        this.hunterNum = hunterNum;
    }

    public int getProtectNum() {
        return protectNum;
    }

    public void setProtectNum(int protectNum) {
        this.protectNum = protectNum;
    }
}
