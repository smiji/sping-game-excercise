package roxor.games.roulette.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WheelResultTest {


    @Test
    void getPocketResultReturnsCorrectPocketNumber() {
        int pocketNumber = 1;
        assertEquals(pocketNumber, WheelResult.create(pocketNumber).getPocketResult());
    }
}