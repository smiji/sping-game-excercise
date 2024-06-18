package roxor.games.roulette.resolver;

import lombok.extern.slf4j.Slf4j;
import roxor.games.roulette.game.WheelResult;
import roxor.games.roulette.model.request.SpinDTO;
import roxor.games.roulette.model.response.SpinResultDTO;

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
