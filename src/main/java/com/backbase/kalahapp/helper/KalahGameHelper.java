package com.backbase.kalahapp.helper;

import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGamePit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This class represent the kalah game rule
 *
 * @author revathik
 */
@Slf4j
@Component
public class KalahGameHelper {

    private final KalahGameRule gameRule;
    public KalahGameHelper() {
        this.gameRule = new StartPitRule();
        gameRule.setNext(new DistributePitStoneRule())
                .setNext(new EndPitRule())
                .setNext(new GameOverRule());
    }

    public void play(KalahGame game, KalahGamePit pit) {
        this.gameRule.apply(game, pit);
    }
}
