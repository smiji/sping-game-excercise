package com.home.games.resolver;

import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.util.Util;
import lombok.extern.slf4j.Slf4j;
import com.home.games.game.WheelResult;


@Slf4j
public class SpinBetMultiplayerSinglePocketResolver implements BetResolver {

    @Override
    public SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin) {
        log.info("Bet request made for Multi player single pocket mode by : {}", Util.listNames(spinDTO));
        return SpinResultDTO
                .builder()
                .betResults(spinDTO
                        .getPlayerBets()
                        .stream()
                        .map(playerBetDTO -> BetResolver.composeBetResultDTO(playerBetDTO, spin))
                        .toList())
                .pocket(spin.getPocketResult())
                .build();
    }

}

