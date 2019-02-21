package com.backbase.kalahapp.service.impl;

import com.backbase.kalahapp.helper.KalahGameHelper;
import com.backbase.kalahapp.model.*;
import com.backbase.kalahapp.repository.KalahGameRepository;
import com.backbase.kalahapp.service.KalahGameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KalahGameServiceImplTest {

    @MockBean
    private KalahGameRepository gameRepository;

    @Autowired
    private KalahGameService kalahGameService;

    @Test
    public void testCreateGame(){
        KalahGamePlayer player1 = new KalahGamePlayer(KalahGamePlayer.PLAYER1_INDEX, "Player 1");
        KalahGamePlayer player2 = new KalahGamePlayer(KalahGamePlayer.PLAYER2_INDEX, "Player 2");
        KalahGameBoard board = new KalahGameBoard();
        board.setPits(initPit());
        KalahGame game = new KalahGame(KalahGameBoard.INITIAL_STONE_ON_PIT);
        game.setId(UUID.randomUUID().toString());
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        Mockito.when(gameRepository.createKalahGame(Mockito.any())).thenReturn(game);
        KalahGame mockGame = kalahGameService.createGame(6);

        Assert.assertEquals(game, mockGame);
    }

    @Test
    public void testPlayGame(){

        KalahGamePlayer player1 = new KalahGamePlayer(KalahGamePlayer.PLAYER1_INDEX, "Player 1");
        KalahGamePlayer player2 = new KalahGamePlayer(KalahGamePlayer.PLAYER2_INDEX, "Player 2");

        KalahGameBoard board = new KalahGameBoard();
        board.setPits(initPit());

        String id = UUID.randomUUID().toString();
        KalahGame game = new KalahGame(KalahGameBoard.INITIAL_STONE_ON_PIT);
        game.setGameStatus(KalahGameStatus.INIT);
        game.setId(id);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        Mockito.when(gameRepository.findByKalahGameId(id)).thenReturn(game);
        game.setGameStatus(KalahGameStatus.PLAYER1_TURN);

        Mockito.when(gameRepository.createKalahGame(Mockito.any())).thenReturn(game);
        KalahGame mockGame =  kalahGameService.play(game.getId(), game.getBoard().getPits().get(1).getPitIndex());

        Assert.assertEquals(KalahGameStatus.PLAYER1_TURN, mockGame.getGameStatus());
    }


    private Map<Integer, KalahGamePit> initPit(){
        Map<Integer, KalahGamePit> pits = new HashMap<>();
        for(int i = KalahGameBoard.PIT_START_INDEX; i < KalahGameBoard.PLAYER1_HOME; i++){
            KalahGamePit pit = new KalahGamePit(i, KalahGameBoard.INITIAL_STONE_ON_PIT, KalahGamePlayer.PLAYER1_INDEX);
            pits.put(i, pit);
        }
        KalahGamePit home1 = new KalahGamePit(KalahGameBoard.PLAYER1_HOME, KalahGameBoard.INITIAL_STONE_ON_HOME, KalahGamePlayer.PLAYER1_INDEX);
        pits.put(KalahGameBoard.PLAYER1_HOME, home1);

        for(int i= KalahGameBoard.PLAYER1_HOME + 1; i < KalahGameBoard.PLAYER2_HOME; i++){
            KalahGamePit pit = new KalahGamePit(i, KalahGameBoard.INITIAL_STONE_ON_PIT, KalahGamePlayer.PLAYER2_INDEX);
            pits.put(i, pit);
        }
        KalahGamePit home2 = new KalahGamePit(KalahGameBoard.PLAYER2_HOME, KalahGameBoard.INITIAL_STONE_ON_HOME, KalahGamePlayer.PLAYER2_INDEX);
        pits.put(KalahGameBoard.PLAYER2_HOME, home2);
        return pits;
    }


}
