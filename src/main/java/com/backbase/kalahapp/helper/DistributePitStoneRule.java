package com.backbase.kalahapp.helper;

import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGamePit;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represent for distribute to the next pits
 * except for the opponent home.
 *
 * @author revathik
 */
@Slf4j
public class DistributePitStoneRule extends KalahGameRule {

    @Override
    public void apply(KalahGame game, KalahGamePit currentPit) {
        log.debug("check the rules for distributing stone to the next pit(s)");
        Integer stoneToDistribute = currentPit.getStoneCount();
        currentPit.setStoneCount(0);
        for (int i = 0; i < stoneToDistribute; i++) {
            currentPit = game.getBoard().getNextPit(currentPit);
            log.debug("next pit {}", currentPit);
            if (currentPit.isDistributable(game.getGameStatus())) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
            }else{
                i--;
            }
        }
        this.next.apply(game, currentPit);
    }
}
