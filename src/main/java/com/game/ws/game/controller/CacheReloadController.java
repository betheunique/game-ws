package com.game.ws.game.controller;

import com.game.ws.game.access.SessionIDRepository;
import com.game.ws.game.cache.RedisProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Component
@Path("/clearCache")
public class CacheReloadController {

    private static Logger logger = Logger.getLogger(CacheReloadController.class);


    private SessionIDRepository sessionIDRepository;


    private RedisProperties redisProperties;

    /**
     * Re initialize sessionIdVsTotalValue Map.
     * @return
     */
    @Path("/reloadSessionID")
    @GET
    public int reloadSessionID() {
        logger.info("re initializing sessionId and corresponding totalValue");
        sessionIDRepository.initialize();
        return 1;
    }

    /**
     * Re-initializes redis properties.
     * @return
     */
    @Path("reloadRedisProperties")
    @GET
    public int reloadRedisProperties() {
        redisProperties.reloadProperties();
        return 1;
    }
}
