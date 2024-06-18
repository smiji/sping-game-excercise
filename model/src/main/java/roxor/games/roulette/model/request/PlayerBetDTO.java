package roxor.games.roulette.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
@EqualsAndHashCode
public class PlayerBetDTO {

    @NotBlank
    @NotNull
    private final String playerName;
    @NotBlank
    @NotNull
    private final String betType;

    @Max(value = 36)
    @Min(value = 0)
    private final String pocket;
    @NotBlank
    @NotNull
    private final String betAmount;

    @JsonCreator
    public PlayerBetDTO(
            @JsonProperty("playerName") String playerName,
            @JsonProperty("betType") String betType,
            @JsonProperty("pocket") String pocket,
            @JsonProperty("betAmount") String betAmount
    ) {

        this.playerName = playerName;
        this.betType = betType;
        this.pocket = pocket;
        this.betAmount = betAmount;
    }
}
