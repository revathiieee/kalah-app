package com.backbase.kalahapp.service;

import com.backbase.kalahapp.model.KalahGame;

/**
 * This interface created for Service Implementation
 *
 * @author revathik
 */
public interface KalahGameService {

    KalahGame createGame(Integer pitNumber);

    KalahGame play(String gameId, Integer pitId);
}

