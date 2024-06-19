package roxor.games.games.game;

import com.home.games.game.WheelResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WheelResultTest {


    @Test
    void getPocketResultReturnsCorrectPocketNumber() {
        int pocketNumber = 1;
        assertEquals(pocketNumber, WheelResult.create(pocketNumber).getPocketResult());
    }
}