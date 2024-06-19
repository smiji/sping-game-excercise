package com.home.games.resolver;

import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import lombok.extern.slf4j.Slf4j;
import com.home.games.game.WheelResult;

import java.util.List;

@Slf4j
public class SpinBetSinglePlayerSinglePocketResolver implements BetResolver {
    @Override
    public SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin) {
        log.info("Bet request made for single player single pocket mode by : {}", spinDTO.getPlayerBets().get(0).getPlayerName());
        return SpinResultDTO
                .builder()
                .betResults(List.of(BetResolver.composeBetResultDTO(spinDTO.getPlayerBets().get(0), spin)))
                .pocket(spin.getPocketResult())
                .build();
    }
}
