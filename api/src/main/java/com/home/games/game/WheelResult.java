package com.home.games.game;

public class WheelResult {

    private final int pocketResult;

    private WheelResult(int pocketResult) {
        this.pocketResult = pocketResult;
    }

    public static WheelResult create(int pocketResult) {
        return new WheelResult(pocketResult);
    }

    public int getPocketResult() {
        return pocketResult;
    }
}
