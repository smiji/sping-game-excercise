package com.home.games.resolver;


import com.home.games.game.WheelResult;
import com.home.games.model.request.PlayerBetDTO;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.BetResultDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.util.Util;

import java.math.BigDecimal;
import java.util.Arrays;

public interface BetResolver {

    static BetResultDTO composeBetResultDTO(PlayerBetDTO playerBetDTO, WheelResult spin) {
        if (isBetSuccessful(playerBetDTO, spin)) {
            return BetResultDTO.builder()
                    .winAmount(Util.calculateWinAmount(playerBetDTO.getBetAmount(),36).toPlainString())
                    .outcome("win")
                    .playerBet(playerBetDTO).build();
        } else {
            return BetResultDTO.builder()
                    .winAmount(BigDecimal.ZERO.toPlainString())
                    .outcome("loose")
                    .playerBet(playerBetDTO).build();
        }
    }

    static boolean isBetSuccessful(PlayerBetDTO playerBetDTO, WheelResult spin) {
        return Util.comparePockets(playerBetDTO.getPocket(), spin.getPocketResult());
    }

    static BetResultDTO resolveResultForMultiPocket(PlayerBetDTO playerBetDTO, WheelResult spin) {
        if (isBetSuccessfulForMultiPocket(playerBetDTO, spin)) {
            return BetResultDTO.builder()
                    .winAmount(Util.calculateWinAmountForPocket(spin.getPocketResult(),playerBetDTO.getPocket(),playerBetDTO.getBetAmount(),36).toPlainString())
                    .outcome("win")
                    .playerBet(playerBetDTO).build();
        } else {
            return BetResultDTO.builder()
                    .winAmount(BigDecimal.ZERO.toPlainString())
                    .outcome("loose")
                    .playerBet(playerBetDTO).build();
        }
    }

    static boolean isBetSuccessfulForMultiPocket(PlayerBetDTO playerBetDTO, WheelResult spin) {
        return Arrays.stream(playerBetDTO.getPocket().split(","))
                .anyMatch(pocket -> Double.valueOf(pocket.trim()).intValue() == spin.getPocketResult());
    }

    SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin);

}
