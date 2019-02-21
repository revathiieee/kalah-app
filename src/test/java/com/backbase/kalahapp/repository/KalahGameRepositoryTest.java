package com.backbase.kalahapp.repository;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import com.backbase.kalahapp.model.KalahGame;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KalahGameRepositoryTest {

    @InjectMocks
    private KalahGameRepository gameRepository;

    @Test
    public void testCreateGame(){
        KalahGame game1 = gameRepository.createKalahGame(6);
        Assert.assertEquals(game1, game1);
    }

    @Test
    public void testFindByKalahGameId(){
        KalahGame game1 = gameRepository.createKalahGame(6);
        KalahGame game = gameRepository.findByKalahGameId(game1.getId());
        Assert.assertNotNull(game);
        Assert.assertEquals(game1, game1);
    }

    @Test(expected = KalahRuntimeException.class)
    public void testFindByKalahGameIdException(){
        KalahGame game1 = gameRepository.createKalahGame(6);
        game1.setId("7");
        KalahGame game = gameRepository.findByKalahGameId(game1.getId());
        Assert.assertNotNull(game);
        Assert.assertEquals(game1, game1);
    }
}
