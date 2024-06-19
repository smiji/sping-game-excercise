package com.home.games.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.games.config.BetMode;
import com.home.games.model.request.PlayerBetDTO;
import com.home.games.model.request.SpinDTO;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Util {

    private static final String BET_MODE_SINGLE_PLAYER_SINGLE_POCKET = BetMode.SINGLE_PLAYER_SINGLE_POCKET.name();
    private static final String BET_MODE_MULTI_PLAYER_SINGLE_POCKET = BetMode.MULTI_PLAYER_SINGLE_POCKET.name();

    private static final String BET_MODE_SINGLE_PLAYER_MULTI_POCKET = BetMode.SINGLE_PLAYER_MULTI_POCKET.name();
    private static final String BET_MODE_MULTI_PLAYER_MULTI_POCKET = BetMode.MULTI_PLAYER_MULTI_POCKET.name();


    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Util() {
    }

    public static Integer getInteger(String value) {
        return Objects.isNull(value) || value.isEmpty() ? null : Integer.parseInt(value.trim());
    }

    public static <T> T deserialize(Class<T> t, String sampleJsonData) throws JsonProcessingException {
        return objectMapper.readValue(sampleJsonData, t);
    }

    public static String readJsonFromFile(String fileLocation) throws IOException {
        File jsonFile = new ClassPathResource(fileLocation).getFile();
        return new String(Files.readAllBytes(jsonFile.toPath()));
    }

    public static String getBetMode(SpinDTO spinDTO) {
        // If the pocket is csv,
        // and the
        boolean isMultiPocketBet = isMultiPocketBet(spinDTO.getPlayerBets());
        if (spinDTO.getPlayerBets().size() == 1) {
            if (isMultiPocketBet) {
                return BET_MODE_SINGLE_PLAYER_MULTI_POCKET;
            }
            return BET_MODE_SINGLE_PLAYER_SINGLE_POCKET;
        } else {
            if (isMultiPocketBet) {
                return BET_MODE_MULTI_PLAYER_MULTI_POCKET;
            }
            return BET_MODE_MULTI_PLAYER_SINGLE_POCKET;
        }
    }

    public static String listNames(SpinDTO spinDTO) {
        return spinDTO.getPlayerBets().stream().map(PlayerBetDTO::getPlayerName).collect(Collectors.joining(","));
    }

    public static BigDecimal validateAmount(String value) {
        return NumberUtils.isParsable(value.trim()) ? new BigDecimal(value.trim()) : BigDecimal.ZERO;
    }

    public static boolean comparePockets(String pocket, int pocketResult) {
        return Double.valueOf(pocket.trim()).intValue() == pocketResult;
    }

    public static BigDecimal calculateWinAmount(String amount, long mutliplyer) {
        return setScale(validateAmount(amount).multiply(BigDecimal.valueOf(mutliplyer)), 2);
    }

    public static BigDecimal setScale(BigDecimal input, int scale) {
        return input.setScale(scale, RoundingMode.HALF_EVEN);
    }

    public static boolean isMultiPocketBet(List<PlayerBetDTO> lstPlayers) {
        return lstPlayers.stream().anyMatch(playerBetDTO -> playerBetDTO.getPocket().contains(","));
    }

    public static BigDecimal calculateWinAmountForPocket(int winPocket, String pockets, String amounts, long mutliplyer) {
        String[] pocketItems = pockets.split(",");
        String[] amountsForEachItem = amounts.split(",");
        int winPocketIndex = -1;
        for (int i = 0; i < pocketItems.length; i++) {
            int pocket = Double.valueOf(pocketItems[i].trim()).intValue();
            if (pocket == winPocket) {
                winPocketIndex = i;
                break;
            }
        }
        return setScale(validateAmount(amountsForEachItem[winPocketIndex]).multiply(BigDecimal.valueOf(mutliplyer)), 2);
    }

}
