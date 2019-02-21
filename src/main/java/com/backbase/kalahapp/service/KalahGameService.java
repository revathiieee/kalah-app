package com.backbase.kalahapp.service;

import com.backbase.kalahapp.model.KalahGame;

/**
 * This interface created for Service Implementation
 *
 * @author revathik
 */
public interface KalahGameService {

    /**
     * To create a new game
     * @param pitNumber pit number
     * @return KalahGame object
     */
    KalahGame createGame(Integer pitNumber);

    /**
     * To play a game
     * @param gameId as Game Id
     * @param pitId as pit index
     * @return KalahGame object
     */
    KalahGame play(String gameId, Integer pitId);
}

