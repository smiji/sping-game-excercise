package roxor.games.roulette.service;

import roxor.games.roulette.game.RouletteWheel;
import roxor.games.roulette.model.request.SpinDTO;
import roxor.games.roulette.model.response.SpinResultDTO;
import roxor.games.roulette.resolver.BetResolver;
import roxor.games.roulette.util.Util;

import java.util.Map;


public class RouletteService {

    private final RouletteWheel rouletteWheel;

    private final Map<String, BetResolver> betResolver;

    public RouletteService(RouletteWheel rouletteWheel, Map<String, BetResolver> betResolver) {
        this.rouletteWheel = rouletteWheel;
        this.betResolver = betResolver;
    }

    public SpinResultDTO spin(SpinDTO spinDTO) {
        return betResolver
                .get(Util.getBetMode(spinDTO))
                .resolve(spinDTO, rouletteWheel.spin(Util.getInteger(spinDTO.getForcedPocket())));
    }

}
