package com.backbase.kalahapp.resource;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.service.KalahGameService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class KalahGameResourceTest {

    private static final Integer INITIAL_STONE_ON_PIT = 6;
    private static final Integer INITIAL_STONE_ON_HOUSE = 0;
    private static final Integer PLAYER1_INDEX = 1;
    private static final Integer PLAYER2_INDEX = 2;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private KalahGameService kalahGameService;

    @InjectMocks
    private KalahGameResource resource = new KalahGameResource();


    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DirtiesContext
    public void testCreateGame() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/games");
        MvcResult result = mockMvc.perform(initGameRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())

                //check game state
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("INIT"))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

                //check pit index
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.pitIndex").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.8.pitIndex").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.pitIndex").value(14))

                //check player index
                .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(KalahGameResourceTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(KalahGameResourceTest.PLAYER2_INDEX))

                //check pit owner
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.playerIndex").value(KalahGameResourceTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.13.playerIndex").value(KalahGameResourceTest.PLAYER2_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.playerIndex").value(KalahGameResourceTest.PLAYER2_INDEX))

                //check  initial pit stone count
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(KalahGameResourceTest.INITIAL_STONE_ON_PIT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.12.stoneCount").value(KalahGameResourceTest.INITIAL_STONE_ON_PIT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.stoneCount").value(KalahGameResourceTest.INITIAL_STONE_ON_HOUSE))
                .andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(201, status);
    }

    @Test
    public void testCreateGameError() {
        Mockito.when(
                kalahGameService.createGame(Mockito.any())).thenThrow(new KalahRuntimeException("Oh, Error"));
        try {
            resource.createBoard(new Integer(5));
        } catch (ResponseStatusException rse) {
            Assert.assertTrue(Objects.requireNonNull(rse.getMessage()).contains("500"));
        }
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testPlayPitIndexError() {
        try {
            resource.play("", new Integer(0));
        } catch (ResponseStatusException rse) {
            Assert.assertTrue(Objects.requireNonNull(rse.getMessage()).contains("500"));
        }
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testPlayPlayerIndexError() {
        try {
            resource.play("", new Integer(7));
        } catch (ResponseStatusException rse) {
            Assert.assertTrue(Objects.requireNonNull(rse.getMessage()).contains("500"));
        }
        Mockito.validateMockitoUsage();
    }


    @Test
    @DirtiesContext
    public void testPlayGame() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/games");
        String responseString = mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        KalahGame game = objectMapper.readValue(responseString, KalahGame.class);

        MockHttpServletRequestBuilder playGame = MockMvcRequestBuilders.put("/games/"+game.getId()+"/pits/"+ 1);
        MvcResult result = mockMvc.perform(playGame)
                .andExpect(MockMvcResultMatchers.status().isOk())

                //check game id
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

                //check player index
                .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(KalahGameResourceTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(KalahGameResourceTest.PLAYER2_INDEX))

                //starting pit should be zero
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.stoneCount").value(0))

                //pit should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.2.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.3.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.4.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.stoneCount").value(7))

                //player 1 house should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.7.stoneCount").value(1))

                //check game state as end with player 1 house
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("PLAYER1_TURN"))
                .andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }

}
