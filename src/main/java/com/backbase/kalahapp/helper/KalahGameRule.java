package com.backbase.kalahapp.helper;

import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGamePit;

/**
 * This class is an abstract class to define the rule based on game and pit
 *
 * @author revathik
 */
public abstract class KalahGameRule {
    protected KalahGameRule next;
    public abstract void apply(KalahGame game, KalahGamePit currentPit);

    public KalahGameRule setNext(KalahGameRule next) {
        this.next = next;
        return next;
    }
}
