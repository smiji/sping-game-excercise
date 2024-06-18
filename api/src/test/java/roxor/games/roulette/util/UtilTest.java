package roxor.games.roulette.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import roxor.games.roulette.controller.exception.ErrorResp;
import roxor.games.roulette.model.request.SpinDTO;

import java.io.IOException;
import java.math.BigDecimal;

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

}