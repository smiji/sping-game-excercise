package com.home.games.resolver;

import com.home.games.game.WheelResult;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SpinBetSinglePlayerMultiPocketResolver implements BetResolver {

    @Override
    public SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin) {
        return SpinResultDTO
                .builder()
                .betResults(
                        spinDTO
                                .getPlayerBets()
                                .stream()
                                .map(playerBetDTO -> BetResolver.resolveResultForMultiPocket(playerBetDTO, spin))
                                .toList())
                .pocket(spin.getPocketResult()).build();

    }
}
