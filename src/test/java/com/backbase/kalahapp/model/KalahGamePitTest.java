package com.backbase.kalahapp.model;

import org.junit.Assert;
import org.junit.Test;

public class KalahGamePitTest {

    @Test
    public void testDistributable() {
        KalahGamePit kalahGamePit1 = new KalahGamePit(1,6,1);
        KalahGamePit kalahGamePit7 = new KalahGamePit(7,6,1);
        Assert.assertEquals(true, kalahGamePit1.isDistributable(KalahGameStatus.PLAYER1_TURN));
        Assert.assertEquals(false, kalahGamePit7.isDistributable(KalahGameStatus.PLAYER2_TURN));
    }

    @Test
    public void testPlayer1Pit(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        Assert.assertEquals(true, kalahGamePit1.isPlayerPit(KalahGameStatus.PLAYER1_TURN));
        Assert.assertEquals(false, kalahGamePit1.isPlayerPit(KalahGameStatus.PLAYER2_TURN));
    }

    @Test
    public void testPlayer2Pit(){
        KalahGamePit kalahGamePit7 = new KalahGamePit(8, 6, 2);
        Assert.assertEquals(false, kalahGamePit7.isPlayerPit(KalahGameStatus.PLAYER1_TURN));
        Assert.assertEquals(true, kalahGamePit7.isPlayerPit(KalahGameStatus.PLAYER2_TURN));
    }

    @Test
    public void testHome(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        KalahGamePit kalahGamePit7 = new KalahGamePit(7, 6, 1);
        KalahGamePit kalahGamePit13 = new KalahGamePit(13, 6, 1);
        KalahGamePit kalahGamePit14 = new KalahGamePit(14, 6, 1);
        Assert.assertEquals(false, kalahGamePit1.isHome());
        Assert.assertEquals(true, kalahGamePit7.isHome());
        Assert.assertEquals(false, kalahGamePit13.isHome());
        Assert.assertEquals(true, kalahGamePit14.isHome());
    }

    @Test
    public void testNextPitIndex(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        KalahGamePit kalahGamePit7 = new KalahGamePit(7, 6, 1);
        KalahGamePit kalahGamePit14 = new KalahGamePit(14, 6, 1);
        Assert.assertEquals(Integer.valueOf(2), kalahGamePit1.nextPitIndex());
        Assert.assertEquals(Integer.valueOf(8), kalahGamePit7.nextPitIndex());
        Assert.assertEquals(Integer.valueOf(1), kalahGamePit14.nextPitIndex());
    }

    @Test
    public void testPlayer1House(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        KalahGamePit kalahGamePit7 = new KalahGamePit(7, 6, 1);
        KalahGamePit kalahGamePit8 = new KalahGamePit(8, 6, 2);
        KalahGamePit kalahGamePit14 = new KalahGamePit(14, 6, 2);
        Assert.assertEquals(false, kalahGamePit1.isPlayer1Home());
        Assert.assertEquals(true, kalahGamePit7.isPlayer1Home());
        Assert.assertEquals(false, kalahGamePit8.isPlayer1Home());
        Assert.assertEquals(false, kalahGamePit14.isPlayer1Home());
    }

    @Test
    public void testPlayer2House(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        KalahGamePit kalahGamePit7 = new KalahGamePit(7, 6, 1);
        KalahGamePit kalahGamePit8 = new KalahGamePit(8, 6, 2);
        KalahGamePit kalahGamePit14 = new KalahGamePit(14, 6, 2);
        Assert.assertEquals(false, kalahGamePit1.isPlayer2Home());
        Assert.assertEquals(false, kalahGamePit7.isPlayer2Home());
        Assert.assertEquals(false, kalahGamePit8.isPlayer2Home());
        Assert.assertEquals(true, kalahGamePit14.isPlayer2Home());
    }

    @Test
    public void testGetOpponentPitIndex(){
        KalahGamePit kalahGamePit1 = new KalahGamePit(1, 6, 1);
        KalahGamePit kalahGamePit8 = new KalahGamePit(8, 6, 1);
        Assert.assertEquals(Integer.valueOf(13), kalahGamePit1.getOpponentPitIndex());
        Assert.assertEquals(Integer.valueOf(6), kalahGamePit8.getOpponentPitIndex());
    }

}
