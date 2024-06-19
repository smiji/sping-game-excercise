package com.home.games.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @NotNull
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
