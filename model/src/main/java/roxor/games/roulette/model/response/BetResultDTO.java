package roxor.games.roulette.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import roxor.games.roulette.model.request.PlayerBetDTO;


@Builder
@Getter
@ToString
@EqualsAndHashCode
public class BetResultDTO {
    @NotEmpty
    private final PlayerBetDTO playerBet;
    @NotEmpty
    private final String outcome;
    @NotEmpty
    private final String winAmount;

    @JsonCreator
    public BetResultDTO(
            @JsonProperty("playerBet") PlayerBetDTO playerBet,
            @JsonProperty("outcome") String outcome,
            @JsonProperty("winAmount") String winAmount
    ) {
        this.playerBet = playerBet;
        this.outcome = outcome;
        this.winAmount = winAmount;
    }
}
