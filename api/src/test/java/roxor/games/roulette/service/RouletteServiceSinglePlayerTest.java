package roxor.games.roulette.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import roxor.games.roulette.config.BetMode;
import roxor.games.roulette.game.RouletteWheel;
import roxor.games.roulette.model.request.SpinDTO;
import roxor.games.roulette.model.response.SpinResultDTO;
import roxor.games.roulette.resolver.BetResolver;
import roxor.games.roulette.resolver.SpinBetSinglePlayerSinglePocketResolver;
import roxor.games.roulette.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RouletteServiceSinglePlayerTest {

    private static final String FILE_SINGLE_PLAYER_FORCED = "/player/SinglePlayer_forced_36.json";
    private static final String FILE_SINGLE_PLAYER_FORCED_WIN = "/player/SinglePlayer_forced_36_win.json";
    private static final String FILE_SINGLE_PLAYER = "/player/SinglePlayer.json";
    RouletteService rouletteService;
    RouletteWheel rouletteWheel;
    Map<String, BetResolver> betResolver;

    @BeforeAll
    public void setUp() {
        rouletteWheel = new RouletteWheel();
        betResolver = Map.of(BetMode.SINGLE_PLAYER_SINGLE_POCKET.name(), new SpinBetSinglePlayerSinglePocketResolver());
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
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class,Util.readJsonFromFile(FILE_SINGLE_PLAYER_FORCED_WIN)));
        assertEquals(1, spinResult.getBetResults().size());
        spinResult.getBetResults().forEach(betResultDTO -> {
            assertEquals("Sam", betResultDTO.getPlayerBet().getPlayerName(), "Player name missing");
            assertEquals("win", betResultDTO.getOutcome(), "expecting outcome - win");
            assertEquals(0, new BigDecimal(36 * 50).compareTo(new BigDecimal(betResultDTO.getWinAmount())), "Win amount not matching");
        });
        assertEquals(36, spinResult.getPocket());
        assertEquals(36, spinResult.getPocket());
    }

    @Test
    void testSpinResult_single_player() throws IOException {
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class,Util.readJsonFromFile(FILE_SINGLE_PLAYER)));
        assertEquals(1, spinResult.getBetResults().size());
        spinResult.getBetResults().forEach(betResultDTO -> {
            assertEquals("Sam", betResultDTO.getPlayerBet().getPlayerName());
        });
        assertTrue(0 <= spinResult.getPocket() && spinResult.getPocket() < 37);
    }


}