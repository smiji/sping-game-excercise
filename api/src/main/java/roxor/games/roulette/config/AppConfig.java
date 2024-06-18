package roxor.games.roulette.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roxor.games.roulette.game.RouletteWheel;
import roxor.games.roulette.resolver.BetResolver;
import roxor.games.roulette.resolver.SpinBetMultiplayerSinglePocketResolver;
import roxor.games.roulette.resolver.SpinBetSinglePlayerSinglePocketResolver;
import roxor.games.roulette.service.RouletteService;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public RouletteWheel rouletteWheel() {
        return new RouletteWheel();
    }

    public Map<String, BetResolver> betResolver() {
        return Map.of(BetMode.SINGLE_PLAYER_SINGLE_POCKET.name(), new SpinBetSinglePlayerSinglePocketResolver(), BetMode.MULTI_PLAYER_SINGLE_POCKET.name(), new SpinBetMultiplayerSinglePocketResolver());
    }

    @Bean
    public RouletteService rouletteService(@Autowired RouletteWheel rouletteWheel) {
        return new RouletteService(rouletteWheel, betResolver());
    }

}
