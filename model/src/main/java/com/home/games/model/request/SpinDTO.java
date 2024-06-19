package com.home.games.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class SpinDTO {

    @Size(max = 0)
    private final String forcedPocket;

    @Size(min = 1)
    @Valid
    private final List<PlayerBetDTO> playerBets;

    @JsonCreator
    public SpinDTO(@JsonProperty("forcedPocket") String forcedPocket,@JsonProperty("playerBets") List<PlayerBetDTO> playerBets) {
        this.forcedPocket = forcedPocket;
        this.playerBets = playerBets;
    }
}
