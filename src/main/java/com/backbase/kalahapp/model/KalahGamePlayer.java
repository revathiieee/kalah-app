package com.backbase.kalahapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * This class represents the player of the kalah game
 *
 * @author revathik
 */
@Data
@AllArgsConstructor
public class KalahGamePlayer {

    /**
     * Hold player1 index as 1
     */
    public static final Integer PLAYER1_INDEX = 1;
    /**
     * Hold player2 index as 2
     */
    public static final Integer PLAYER2_INDEX = 2;

    /**
     * Holds the index value of the player
     */
    @NotNull
    private Integer playerIndex;

    /**
     * Holds the name of the player
     */
    @NotNull
    private String playerName;
}
