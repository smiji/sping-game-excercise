package com.home.games.game;

import java.security.SecureRandom;

public class RouletteWheel {

    private final SecureRandom secureRandom = new SecureRandom();

    public WheelResult spin(Integer forcedPocketNumber) {

        int pocketResult = secureRandom.nextInt(37);

        if (forcedPocketNumber != null) {
            pocketResult = forcedPocketNumber;
        }

        return WheelResult.create(pocketResult);
    }

}
