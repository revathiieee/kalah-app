package com.backbase.kalahapp.resource;

import com.backbase.kalahapp.exception.KalahRuntimeException;
import com.backbase.kalahapp.model.KalahGame;
import com.backbase.kalahapp.model.KalahGameBoard;
import com.backbase.kalahapp.service.KalahGameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest endpoint of this kalah game
 *
 * @author revathik
 */
@RestController
@RequestMapping("/games")
@Slf4j
public class KalahGameResource {

    /**
     * Instantiates kalahgameservice
     */
    @Autowired
    private KalahGameService kalahGameService;

    /**
     * Endpoint to create a new Game
     * @param numberOfStone no of stones default is 6
     * @return Response body of KalahGame
     */
    @PostMapping
    @ApiOperation(value = "Submit a new  kalah game.", notes = "Add New Kalah Game")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a new game"),
            @ApiResponse(code = 400, message = "An error has occurred when attempting to parse request"),
            @ApiResponse(code = 500, message = "A technical error has occurred when attempting to create the game") })
    public ResponseEntity<KalahGame> createBoard(@RequestParam(name = "stone", defaultValue = "6", required = false) Integer numberOfStone){
        log.debug("initializing kalah game");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(kalahGameService.createGame(numberOfStone));
        } catch (KalahRuntimeException e) {
            log.error("A technical error occurred while create a new game: {}", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A technical error has occurred when attempting to create a game"); //NOPMD
        }
    }

    /**
     * Endpoint to play the kalah game
     * @param gameId as Game Id
     * @param pitIndex as index of pit
     * @return KalahGame Object
     */
    @PutMapping("/{gameId}/pits/{pitIndex}")
    @ApiOperation(value = "Make a move to play the game", notes = "Make a move to play the game")
    public ResponseEntity<KalahGame> play(@ApiParam(value = "GameID for the corresponding kalah game", required = true) @PathVariable String gameId, @ApiParam(value = "PitId for the corresponding pits", required = true) @PathVariable Integer pitIndex){
        log.debug("From game {}, player {} is moving stone from pit {}",gameId, pitIndex);
        try {
            if (pitIndex > KalahGameBoard.PIT_END_INDEX || pitIndex < KalahGameBoard.PIT_START_INDEX) {
                throw new KalahRuntimeException("Incorrect pit index");
            }
            if (pitIndex.equals(KalahGameBoard.PLAYER1_HOME) || pitIndex.equals(KalahGameBoard.PLAYER2_HOME)) {
                throw new KalahRuntimeException("Home stone is not allow to distribute");
            }
            return ResponseEntity.status(HttpStatus.OK).body(kalahGameService.play(gameId, pitIndex));
        } catch (KalahRuntimeException e) {
            log.error("A technical error occurred while moving a game: {}", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A technical error has occurred when attempting to play a game"); //NOPMD
        }
    }
}
