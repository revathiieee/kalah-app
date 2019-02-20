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


    public static final Integer PLAYER1_INDEX = 1;
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
