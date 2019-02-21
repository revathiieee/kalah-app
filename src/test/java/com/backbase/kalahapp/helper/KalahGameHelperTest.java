package com.backbase.kalahapp.helper;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGameBoard;
import com.backbase.kalahapp.model.KalahGamePit;
import com.backbase.kalahapp.model.KalahGameStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KalahGameHelperTest {

    @InjectMocks
    private KalahGameHelper gameHelper;

    @Test
    public void testStartWithOwnPit(){
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2) );
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
        Assert.assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test(expected = KalahRuntimeException.class)
    public void testNotStartWithEmptyPit(){
        KalahGame game = new KalahGame(6);
        KalahGamePit pit = game.getBoard().getPits().get(2);
        pit.setStoneCount(0);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
    }

    @Test(expected = KalahRuntimeException.class)
    public void testNotStartWithOpponentPit(){
        KalahGame game = new KalahGame(6);
        game.setGameStatus(KalahGameStatus.PLAYER2_TURN);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
    }

    @Test
    public void testDistributeStoneFromPlayer2PitToPlayer1Pit() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(12));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(12));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(13));
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(14));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(1));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
        Assert.assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testDistributeStoneFromPlayer1PitToPlayer2Pit(){
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(4));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(10));
        Assert.assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testDistribute13Stone(){
        KalahGame game = new KalahGame(6);
        game.getBoard().getPits().get(4).setStoneCount(13);
        game.getBoard().getPits().get(10).setStoneCount(10);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(4));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        Assert.assertEquals(Integer.valueOf(13), game.getBoard().getStoneCountByPitIndex(7));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(10));
        Assert.assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        Assert.assertEquals(Integer.valueOf(13), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testIncreaseHomeStoneOnOwnEmptyPit() {
        KalahGame game = new KalahGame(6);
        KalahGamePit pit1 = game.getBoard().getPitByPitIndex(1);
        pit1.setStoneCount(2);

        KalahGamePit pit2 = game.getBoard().getPitByPitIndex(3);
        pit2.setStoneCount(0);

        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));

        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(3) );
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(11));
        Assert.assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        Assert.assertEquals(Integer.valueOf(7), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testChangeGameToPlayerTurn1() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));
        Assert.assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void testChangeGameToPlayerTurn2() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
        Assert.assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    public void testChangeGameToPlayerTurn2Again() {
        KalahGame game = new KalahGame(6);
        KalahGamePit pit = game.getBoard().getPits().get(8);
        pit.setStoneCount(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(8));
        Assert.assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    public void testGameOverRule() {
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(6).setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));
        Assert.assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void testPlayer1Win() {
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        KalahGamePit lastPit = game.getBoard().getPits().get(6);
        lastPit.setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));
        Assert.assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void testPlayer2Win(){
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(13).setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(13));
        Assert.assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer2());
    }

    @Test
    public void testDraw(){
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(6).setStoneCount(1);
        game.getBoard().getPits().get(14).setStoneCount(1);

        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));

        Assert.assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        Assert.assertEquals(game.getWinner(), null);
    }
}
