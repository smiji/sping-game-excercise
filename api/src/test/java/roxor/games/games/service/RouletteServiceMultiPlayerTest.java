package roxor.games.games.service;

import com.home.games.config.BetMode;
import com.home.games.game.RouletteWheel;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.BetResultDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.resolver.BetResolver;
import com.home.games.resolver.SpinBetMultiplayerSinglePocketResolver;
import com.home.games.service.RouletteService;
import com.home.games.util.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RouletteServiceMultiPlayerTest {

    private static final String FILE_MULTI_PLAYER_FORCED_WIN = "/player/Multiplayer_forced.json";
    private static final String FILE_MULTI_PLAYER = "/player/Multiplayer.json";

    RouletteService rouletteService;
    RouletteWheel rouletteWheel;
    Map<String, BetResolver> betResolver;

    @BeforeAll
    public void setUp() {
        rouletteWheel = new RouletteWheel();
        betResolver = Map.of(BetMode.MULTI_PLAYER_SINGLE_POCKET.name(), new SpinBetMultiplayerSinglePocketResolver());
        rouletteService = new RouletteService(rouletteWheel, betResolver);
    }

    @Test
    void testSpinResult_multi_player() throws IOException {
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class, Util.readJsonFromFile(FILE_MULTI_PLAYER)));
        String namesOfParticipants = spinResult.getBetResults().stream().map(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName()).collect(Collectors.joining(","));
        assertEquals(2, spinResult.getBetResults().size());
        assertEquals("Nick,Jules", namesOfParticipants, "Participant's name missing");
        assertTrue(spinResult.getPocket() >= 0 && spinResult.getPocket() < 37);
    }

    @Test
    void testSpinResult_multi_player_forced_win() throws IOException {
        SpinResultDTO spinResult = rouletteService.spin(Util.deserialize(SpinDTO.class, Util.readJsonFromFile(FILE_MULTI_PLAYER_FORCED_WIN)));
        String namesOfParticipants = spinResult.getBetResults().stream().map(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName()).collect(Collectors.joining(","));

        //basic test , number of items in the response
        assertEquals(2, spinResult.getBetResults().size());
        assertEquals("Nick,Jules", namesOfParticipants, "Participant's name missing");
        assertTrue(spinResult.getPocket() >= 0 && spinResult.getPocket() < 37);

        //As per json Nick loose -
        String nickOutcome = spinResult.getBetResults()
                .stream()
                .filter(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName().equalsIgnoreCase("Nick"))
                .map(BetResultDTO::getOutcome).findFirst().orElse("NA");

        //As per json Jules win -
        String julesOutcome = spinResult.getBetResults()
                .stream()
                .filter(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName().equalsIgnoreCase("Jules"))
                .map(BetResultDTO::getOutcome).findFirst().orElse("NA");

        // test for outcome
        assertEquals("loose", nickOutcome, "Nick expected to loose");
        assertEquals("win", julesOutcome, "Jules expected to win");

        // test for win amount for nick , expected - 0 , since he lost
        BigDecimal nickWinAmount = spinResult.getBetResults()
                .stream()
                .filter(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName().equalsIgnoreCase("Nick"))
                .map(betResultDTO -> new BigDecimal(betResultDTO.getWinAmount())).findFirst().orElse(BigDecimal.valueOf(-1L));

        // test for win amount for jules , expected - 36 * 2 , since he won
        BigDecimal julesWinAmount = spinResult.getBetResults()
                .stream()
                .filter(betResultDTO -> betResultDTO.getPlayerBet().getPlayerName().equalsIgnoreCase("Jules"))
                .map(betResultDTO -> new BigDecimal(betResultDTO.getWinAmount())).findFirst().orElse(BigDecimal.valueOf(-1L));

        //test for win amount
        assertEquals(0, nickWinAmount.compareTo(BigDecimal.ZERO), "Nick expected to have 0 win amount");
        assertEquals(0, julesWinAmount.compareTo(BigDecimal.valueOf(72L)), "Jules expected to have 72 win amount");
    }

}
