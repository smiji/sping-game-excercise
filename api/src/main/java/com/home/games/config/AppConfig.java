package com.home.games.config;

import com.home.games.game.RouletteWheel;
import com.home.games.resolver.*;
import com.home.games.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public RouletteWheel rouletteWheel() {
        return new RouletteWheel();
    }

    public Map<String, BetResolver> betResolver() {
        return Map.of(
                BetMode.SINGLE_PLAYER_SINGLE_POCKET.name(), new SpinBetSinglePlayerSinglePocketResolver(),
                BetMode.MULTI_PLAYER_SINGLE_POCKET.name(), new SpinBetMultiplayerSinglePocketResolver(),
                BetMode.SINGLE_PLAYER_MULTI_POCKET.name(), new SpinBetSinglePlayerMultiPocketResolver(),
                BetMode.MULTI_PLAYER_MULTI_POCKET.name(), new SpinBetMultiplayerMultiPocketResolver());
    }

    @Bean
    public RouletteService rouletteService(@Autowired RouletteWheel rouletteWheel) {
        return new RouletteService(rouletteWheel, betResolver());
    }

}
