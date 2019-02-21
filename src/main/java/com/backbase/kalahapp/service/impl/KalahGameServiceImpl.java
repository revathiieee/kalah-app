package com.backbase.kalahapp.service.impl;

import com.backbase.kalahapp.helper.KalahGameHelper;
import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGamePit;
import com.backbase.kalahapp.repository.KalahGameRepository;
import com.backbase.kalahapp.service.KalahGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Implementation of KalahGameService Interface
 *
 * @author revathik
 */
@Slf4j
@Service
public class KalahGameServiceImpl implements KalahGameService {

    @Autowired
    private KalahGameRepository gameRepository;

    @Autowired
    private KalahGameHelper gameHelper;

    /**
     * Method to start the new kalah game
     * @param pitNumber initial number of stone
     * @return KalahGame object
     */
    @Override
    public KalahGame createGame(Integer pitNumber) {
        return gameRepository.createKalahGame(pitNumber);
    }

    /**
     * Method is responsible for every new move of the stones from a pit.
     * @param gameId Kalah Game Id
     * @param pitId Index of the pit
     * @return KalahGame object
     */
    @Override
    public KalahGame play(String gameId, Integer pitId) {
        log.debug("gameId {}, pitIndex {}",gameId, pitId);
        KalahGame game = gameRepository.findByKalahGameId(gameId);
        KalahGamePit pit = game.getBoard().getPitByPitIndex(pitId);
        gameHelper.play(game, pit);
        return game;
    }
}
