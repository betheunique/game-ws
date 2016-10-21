package com.game.ws.game.service;

import com.game.ws.game.beans.DefaultResponseBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Service
public class GameConnectService {

    private static Logger logger = Logger.getLogger(GameConnectService.class);


    public Response connectResponse(String sessionId) {
        DefaultResponseBean defaultResponseBean = new DefaultResponseBean();
        defaultResponseBean.setErrorCode(1);
        defaultResponseBean.setErrorMessage("Welcome to game");
        return Response.ok().entity(defaultResponseBean).cookie(new NewCookie("sessionId", sessionId)).build();
    }
}
