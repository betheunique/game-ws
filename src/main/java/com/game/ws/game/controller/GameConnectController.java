package com.game.ws.game.controller;

import com.game.ws.game.beans.DefaultResponseBean;
import com.game.ws.game.beans.GameConnectResponse;
import com.game.ws.game.service.GameConnectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Path("/join")
@Component
public class GameConnectController {

    private static Logger logger = Logger.getLogger(GameConnectController.class);

    @Autowired
    private GameConnectService gameConnectService;

    @Path("/connect")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response gameConnect(@HeaderParam("sessionId") String sessionId){
        logger.info("SessionId coming as ::"+ sessionId);
//        GameConnectResponse gameConnectResponse = new GameConnectResponse();
//        return 1;
        return gameConnectService.connectResponse(sessionId);
    }
}
