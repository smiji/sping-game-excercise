package roxor.games.games.game;

import com.home.games.game.RouletteWheel;
import com.home.games.game.WheelResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouletteWheelTest {

    private RouletteWheel rouletteWheel = new RouletteWheel();



    @Test
    void canForcePocketNumber() {
        int forcedPocketNumber = 0;
        WheelResult result = rouletteWheel.spin(forcedPocketNumber);
        assertEquals(forcedPocketNumber, result.getPocketResult());
    }

    @Test
    void pocketResultFallsWithinExpectedBounds() {
        int spins = 1000;
        for (int i = 0; i < spins; i++) {
            int actualPocketNumber = rouletteWheel.spin(null).getPocketResult();
            assertTrue(0 <= actualPocketNumber && actualPocketNumber <= 36,
                    "Expected actualPocketNumber to be within the range [0,36] but was: " + actualPocketNumber);
        }
    }



}