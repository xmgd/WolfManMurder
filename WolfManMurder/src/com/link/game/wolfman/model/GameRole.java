
package com.link.game.wolfman.model;

public enum GameRole {
    XIANZHI("先知"), QIUBITE("丘比特"), NVWU("女巫"), WOLF("狼人"), MAN("水民"), HUNTER("猎人"), PROTECT("守卫");
    private String name;

    GameRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(this.name);
    }
}
