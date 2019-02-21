package com.backbase.kalahapp.model;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import org.junit.Assert;
import org.junit.Test;

public class KalahGameBoardTest {

    @Test
    public void testCreateBoard(){
        KalahGameBoard board = createBoard();
        Assert.assertNotNull(board.getPits());
        Assert.assertEquals(14, board.getPits().size());
    }

    @Test
    public void testGetStoneCountByPitIndex(){
        KalahGameBoard board = createBoard();
        Integer pit1Stone = board.getStoneCountByPitIndex(1);
        Integer home1Stone = board.getStoneCountByPitIndex(7);
        Integer pit8Stone = board.getStoneCountByPitIndex(8);
        Integer home2Stone = board.getStoneCountByPitIndex(14);
        Assert.assertEquals(Integer.valueOf(6), pit1Stone);
        Assert.assertEquals(Integer.valueOf(0), home1Stone);
        Assert.assertEquals(Integer.valueOf(6), pit8Stone);
        Assert.assertEquals(Integer.valueOf(0), home2Stone);
    }

    @Test
    public void testGetPlayerHome(){
        KalahGameBoard board = createBoard();
        KalahGamePit home1 = board.getPlayerHome(KalahGamePlayer.PLAYER1_INDEX);
        KalahGamePit home2 = board.getPlayerHome(KalahGamePlayer.PLAYER2_INDEX);
        Assert.assertEquals(Integer.valueOf(7), home1.getPitIndex());
        Assert.assertEquals(Integer.valueOf(14), home2.getPitIndex());
    }

    @Test(expected = KalahRuntimeException.class)
    public void testErrorPlayerHome(){
        KalahGameBoard board = createBoard();
        KalahGamePit home1 = board.getPlayerHome(new Integer(3));
    }

    @Test
    public void testGetPitByPitIndex(){
        KalahGameBoard board = createBoard();
        KalahGamePit pit = board.getPitByPitIndex(2);
        Assert.assertEquals(Integer.valueOf(2), pit.getPitIndex());
        Assert.assertEquals(Integer.valueOf(1), pit.getPlayerIndex());
    }

    @Test
    public void testGetNextPit() {
        KalahGameBoard board = createBoard();
        KalahGamePit pit1 = board.getPitByPitIndex(1);
        KalahGamePit pit2 = board.getNextPit(pit1);
        KalahGamePit pit14 = board.getPitByPitIndex(14);
        KalahGamePit pit1Again = board.getNextPit(pit14);
        Assert.assertEquals(Integer.valueOf(2), pit2.getPitIndex());
        Assert.assertEquals(pit1, pit1Again);
    }

    @Test
    public void testGetOppositePit() {
        KalahGameBoard board = createBoard();
        KalahGamePit pit1 = board.getPitByPitIndex(1);
        KalahGamePit oppositePit = board.getOppositePit(pit1);
        KalahGamePit pit1Again = board.getOppositePit(oppositePit);
        Assert.assertEquals(Integer.valueOf(13), oppositePit.getPitIndex());
        Assert.assertEquals(pit1, pit1Again);
    }

    @Test
    public void testGetPlayer1PitStoneCount(){
        KalahGameBoard board = createBoard();
        Integer player1PitStoneCount = board.getPlayer1PitStoneCount();
        Assert.assertEquals(Integer.valueOf(36), player1PitStoneCount);
    }

    @Test
    public void testGetPlayer2PitStoneCount(){
        KalahGameBoard board = createBoard();
        board.getPits().get(8).setStoneCount(0);
        Integer player2PitStoneCount = board.getPlayer2PitStoneCount();
        Assert.assertEquals(Integer.valueOf(30), player2PitStoneCount);
    }

    private static KalahGameBoard createBoard(){
        KalahGamePlayer player1 = new KalahGamePlayer(KalahGamePlayer.PLAYER1_INDEX, "player1");
        KalahGamePlayer player2 = new KalahGamePlayer(KalahGamePlayer.PLAYER2_INDEX, "player2");
        return new KalahGameBoard(KalahGameBoard.INITIAL_STONE_ON_PIT, player1, player2);
    }
}
