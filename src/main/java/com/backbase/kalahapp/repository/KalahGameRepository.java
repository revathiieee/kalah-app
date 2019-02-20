package com.backbase.kalahapp.repository;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import com.backbase.kalahapp.model.KalahGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represent the storage of the kalah game in the map and get the map by game id
 *
 * @author revathik
 */
@Slf4j
@Component
public class KalahGameRepository {

    private static final Map<String, KalahGame> gameMap = new ConcurrentHashMap<>();

    /**
     * This method create a new Kalah Game and save that object in a Map.
     * @param initialPitStoneCount is the number of the stone of a pit.
     * @return KalahGame object.
     */
    public KalahGame createKalahGame(Integer initialPitStoneCount){
        String id = UUID.randomUUID().toString();
        KalahGame kalahGame = new KalahGame(initialPitStoneCount);
        kalahGame.setId(id);
        gameMap.put(id, kalahGame);
        return gameMap.get(id);
    }

    /**
     * This method will return the kalah game object by id.
     * @param id is the kalah game id.
     * @return KalahGame
     */
    public KalahGame findByKalahGameId(String id){
        KalahGame kalahGame = gameMap.get(id);
        if(kalahGame == null){
            throw new KalahRuntimeException("Kalah Game is not found for this id: "+id);
        }
        return kalahGame;
    }
}
