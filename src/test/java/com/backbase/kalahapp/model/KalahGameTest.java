package com.backbase.kalahapp.model;

import org.junit.Assert;
import org.junit.Test;

public class KalahGameTest {

    @Test
    public void shouldCreateGame(){
        KalahGame game = new KalahGame(KalahGameBoard.INITIAL_STONE_ON_PIT);
        Assert.assertEquals(KalahGamePlayer.PLAYER1_INDEX, game.getPlayer1().getPlayerIndex());
        Assert.assertEquals(KalahGamePlayer.PLAYER2_INDEX, game.getPlayer2().getPlayerIndex());
        Assert.assertNotNull(game.getBoard());
        Assert.assertNull(game.getWinner());
        Assert.assertNull(game.getId());
        Assert.assertEquals(14, game.getBoard().getPits().size());
        Assert.assertEquals(KalahGameStatus.INIT, game.getGameStatus());
    }
}
