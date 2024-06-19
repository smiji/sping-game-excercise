package roxor.games.games.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.home.games.controller.exception.ErrorResp;
import com.home.games.model.request.PlayerBetDTO;
import com.home.games.model.request.SpinDTO;
import com.home.games.util.Util;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    @Test
    void testGetInteger() {
        String value = null;
        Integer integer = Util.getInteger(value);
        assertNull(integer);
    }

    @Test
    void testFileRead() throws IOException {
        String fileName = "/player/SinglePlayer_forced_36.json";
        String fileData = Util.readJsonFromFile(fileName);
        DocumentContext jsonDoc = JsonPath.parse(fileData);
        String playerNameSam = "$.playerBets[0].playerName";
        assertEquals("Sam", jsonDoc.read(playerNameSam));
    }

    @Test
    void testDeserializeToSpinDTO() throws IOException {
        String fileName = "/player/SinglePlayer_forced_36.json";
        String fileData = Util.readJsonFromFile(fileName);
        SpinDTO spinDTO = Util.deserialize(SpinDTO.class, fileData);
        assertEquals("Sam", spinDTO.getPlayerBets().get(0).getPlayerName());
    }

    @Test
    void testListNames() throws IOException {
        String fileName = "/player/Multiplayer.json";
        String fileData = Util.readJsonFromFile(fileName);
        SpinDTO spinDTO = Util.deserialize(SpinDTO.class, fileData);
        assertEquals("Nick,Jules", Util.listNames(spinDTO));
    }

    @Test
    void testValidateAmount() {
        String value = "df";
        assertEquals(BigDecimal.ZERO, Util.validateAmount(value));
    }

    @Test
    void testErrorResp() throws JsonProcessingException {
        String sampleJsonData = "{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Invalid request content\",\"instance\":\"/v1/roulette/spin\"}\n";
        ErrorResp deserialize = Util.deserialize(ErrorResp.class, sampleJsonData);
        assertNotNull(deserialize);
    }

    @Test
    void testComparePockets() {
        String value = "15.0";
        int num = 15;
        assertTrue(Util.comparePockets(value, num));
    }

    @Test
    void testCalculateWinnAmount() {
        BigDecimal winAmount = Util.calculateWinAmount("9.5", 36);
        assertEquals(new BigDecimal("342.00"), winAmount);
    }

    @Test
    void testSetScale() {
        BigDecimal multiplyResult = new BigDecimal("9.5").multiply(new BigDecimal("3.2"));
        BigDecimal afterRounding = Util.setScale(multiplyResult, 2);
        assertEquals(new BigDecimal("30.40"), afterRounding);
    }

    @Test
    void testGetBetMode() {

        // multiplayer , multipocket
        PlayerBetDTO playerBetDTO1 = PlayerBetDTO.builder().pocket("1,3,4").betAmount("4,4,5").build();
        PlayerBetDTO playerBetDTO2 = PlayerBetDTO.builder().pocket("1,3,4").betAmount("4,4,5").build();
        List<PlayerBetDTO> playerBetDTOS = new ArrayList<>();
        playerBetDTOS.add(playerBetDTO1);
        playerBetDTOS.add(playerBetDTO2);
        SpinDTO mockSpinDTO = Mockito.mock(SpinDTO.class);
        Mockito.when(mockSpinDTO.getPlayerBets()).thenReturn(playerBetDTOS);
        String betMode = Util.getBetMode(mockSpinDTO);
        Assertions.assertEquals("MULTI_PLAYER_MULTI_POCKET", betMode);

        // multiplayer , single pocket
        playerBetDTO1 = PlayerBetDTO.builder().pocket("1").betAmount("4").build();
        playerBetDTO2 = PlayerBetDTO.builder().pocket("1").betAmount("5").build();
        List<PlayerBetDTO> playerBetDTOS1 = new ArrayList<>();
        playerBetDTOS1.add(playerBetDTO1);
        playerBetDTOS1.add(playerBetDTO2);
        Mockito.when(mockSpinDTO.getPlayerBets()).thenReturn(playerBetDTOS1);
        betMode = Util.getBetMode(mockSpinDTO);
        Assertions.assertEquals("MULTI_PLAYER_SINGLE_POCKET", betMode);

        // single player , single pocket
        playerBetDTO1 = PlayerBetDTO.builder().pocket("1").betAmount("4").build();
        List<PlayerBetDTO> playerBetDTOS2 = new ArrayList<>();
        playerBetDTOS2.add(playerBetDTO1);
        Mockito.when(mockSpinDTO.getPlayerBets()).thenReturn(playerBetDTOS2);
        betMode = Util.getBetMode(mockSpinDTO);
        Assertions.assertEquals("SINGLE_PLAYER_SINGLE_POCKET", betMode);

        // single player , multi pocket
        playerBetDTO1 = PlayerBetDTO.builder().pocket("1,5").betAmount("4,10").build();
        List<PlayerBetDTO> playerBetDTOS3 = new ArrayList<>();
        playerBetDTOS3.add(playerBetDTO1);
        Mockito.when(mockSpinDTO.getPlayerBets()).thenReturn(playerBetDTOS3);
        betMode = Util.getBetMode(mockSpinDTO);
        Assertions.assertEquals("SINGLE_PLAYER_MULTI_POCKET", betMode);
    }

    @Test
    void testWinAmount(){
        int winPocket=4;
        String pockets="3,5,4";
        String amounts="10,40,50";
        long multiplayer = 36;
        BigDecimal bigDecimalWinAmount = Util.calculateWinAmountForPocket(winPocket, pockets, amounts, multiplayer);
        assertEquals(0, bigDecimalWinAmount.compareTo(new BigDecimal(36 * 50)));
    }
}