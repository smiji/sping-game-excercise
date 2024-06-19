package com.home.games.service;

import com.home.games.game.RouletteWheel;
import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.resolver.BetResolver;
import com.home.games.util.Util;

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
