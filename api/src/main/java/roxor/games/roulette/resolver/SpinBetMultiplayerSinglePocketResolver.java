package roxor.games.roulette.resolver;

import lombok.extern.slf4j.Slf4j;
import roxor.games.roulette.game.WheelResult;
import roxor.games.roulette.model.request.SpinDTO;
import roxor.games.roulette.model.response.SpinResultDTO;
import roxor.games.roulette.util.Util;

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

