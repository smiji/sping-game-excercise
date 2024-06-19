package com.home.games.resolver;

import com.home.games.game.WheelResult;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.util.Util;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SpinBetMultiplayerMultiPocketResolver implements BetResolver {
    @Override
    public SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin) {
        log.info("Bet request made for Multi player single pocket mode by : {}", Util.listNames(spinDTO));
        return SpinResultDTO.builder()
                .betResults(
                        spinDTO.getPlayerBets()
                                .stream()
                                .map(playerBetDTO -> BetResolver.resolveResultForMultiPocket(playerBetDTO, spin))
                                .toList()).pocket(spin.getPocketResult()).build();

    }

}
