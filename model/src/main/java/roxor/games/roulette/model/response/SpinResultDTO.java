package roxor.games.roulette.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class SpinResultDTO {
    @NotEmpty
    private final Integer pocket;
    @NotEmpty
    private final List<BetResultDTO> betResults;

    @JsonCreator
    public SpinResultDTO(
            @JsonProperty("pocket") Integer pocket,
            @JsonProperty("betResults") List<BetResultDTO> betResults
    ) {
        this.pocket = pocket;
        this.betResults = betResults;
    }
}
