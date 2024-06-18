package roxor.games.roulette.resolver;

import roxor.games.roulette.game.WheelResult;
import roxor.games.roulette.model.request.PlayerBetDTO;
import roxor.games.roulette.model.request.SpinDTO;
import roxor.games.roulette.model.response.BetResultDTO;
import roxor.games.roulette.model.response.SpinResultDTO;
import roxor.games.roulette.util.Util;

import java.math.BigDecimal;


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

    SpinResultDTO resolve(SpinDTO spinDTO, WheelResult spin);

}
