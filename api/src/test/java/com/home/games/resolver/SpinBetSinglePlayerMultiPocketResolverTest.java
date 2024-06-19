package com.home.games.resolver;

import com.home.games.config.BetMode;
import com.home.games.game.RouletteWheel;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.service.RouletteService;
import com.home.games.util.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class SpinBetSinglePlayerMultiPocketResolverTest {

    private static final String FILE_SINGLE_PLAYER_FORCED = "/player/SinglePlayerMultipocket_forced.json";
    private static final String FILE_SINGLE_PLAYER_MULTI_POCKET_FORCED_WIN = "/player/SinglePlayerMultipocket_forced.json";
    private static final String FILE_SINGLE_PLAYER_MULTI_POCKET = "/player/SinglePlayerMultipocket.json";
    RouletteService rouletteService;
    RouletteWheel rouletteWheel;
    Map<String, BetResolver> betResolver;

    @BeforeAll
    public void setUp() {
        rouletteWheel = new RouletteWheel();
        betResolver = Map.of(BetMode.SINGLE_PLAYER_MULTI_POCKET.name(), new SpinBetSinglePlayerMultiPocketResolver());
        rouletteService = new RouletteService(rouletteWheel, betResolver);
    }

    @Test
    void testSpinResult_single_player_forced() throws IOException {
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class,Util.readJsonFromFile(FILE_SINGLE_PLAYER_FORCED)));
        assertEquals(1, spinResult.getBetResults().size());
        spinResult.getBetResults().forEach(betResultDTO -> assertEquals("Sam", betResultDTO.getPlayerBet().getPlayerName()));
        assertEquals(36, spinResult.getPocket());
    }

    @Test
    void testSpinResult_single_player_forced_win() throws IOException{
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class,Util.readJsonFromFile(FILE_SINGLE_PLAYER_MULTI_POCKET_FORCED_WIN)));
        assertEquals(1, spinResult.getBetResults().size());
        spinResult.getBetResults().forEach(betResultDTO -> {
            assertEquals("Sam", betResultDTO.getPlayerBet().getPlayerName(), "Player name missing");
            assertEquals("win", betResultDTO.getOutcome(), "expecting outcome - win");
            assertEquals(0, new BigDecimal(36 * 5).compareTo(new BigDecimal(betResultDTO.getWinAmount())), "Win amount not matching");
        });
        assertEquals(36, spinResult.getPocket());
        assertEquals(36, spinResult.getPocket());
    }

    @Test
    void testSpinResult_single_player() throws IOException {
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class,Util.readJsonFromFile(FILE_SINGLE_PLAYER_MULTI_POCKET)));
        assertEquals(1, spinResult.getBetResults().size());
        spinResult.getBetResults().forEach(betResultDTO -> {
            assertEquals("Sam", betResultDTO.getPlayerBet().getPlayerName());
        });
        assertTrue(0 <= spinResult.getPocket() && spinResult.getPocket() < 37);
    }


}