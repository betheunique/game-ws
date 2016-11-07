package com.game.ws.game.controller;

import com.game.ws.game.beans.GameCalculationResponse;
import com.game.ws.game.service.GameCalculationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Path("/game")
@Component
public class GameCalculationController {

    private  static  Logger logger = Logger.getLogger(GameCalculationController.class);

    @Autowired
    private GameCalculationService gameCalculationService;

    @Path("/calculation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GameCalculationResponse gameCalculation(@QueryParam("A") int firstValue,
                                                   @QueryParam("B") int secondValue,
                                                   @HeaderParam("sessionId") String sessionId) {
        logger.info("SessionId coming as "+ sessionId);
        return gameCalculationService.calculate(firstValue, secondValue, sessionId);


    }

}
