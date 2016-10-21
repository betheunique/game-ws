package com.game.ws.game.access;

import com.game.ws.game.cache.RedisClient;
import com.game.ws.game.db.DBService;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.IntegerPatternConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishekrai on 20/10/2016.
 */
public class SessionIDRepository {

    private static Logger logger = Logger.getLogger(SessionIDRepository.class);

    @Autowired
    private DBService genericDBService;
    /**
     * Commenting redis as persistance datasource.
     */
//    private RedisClient redisClient;

    private Map<String, SessionIDAccess> sessionIDVsTotalValue1 = new HashMap<String, SessionIDAccess>();

//Redis Map
    //TODO Can be revamp after full session service implementation.
    private Map<String, String> sessionIDVsTotalValue = new HashMap<String, String>();
    @PostConstruct
    public void initialize() {
        loadSessionID();
    }

    /**
     * It will initialize sessionId and its totalValue either from redis or MySQL.
     * These are initial values
     */
    private void loadSessionID() {

        List<SessionIDAccess> sessionIDAccesses = genericDBService.getAllRecords(SessionIDAccess.class);
        for(SessionIDAccess sessionID : sessionIDAccesses) {
            sessionIDVsTotalValue1.put(sessionID.getSessionId(), sessionID);
        }
//        sessionIDVsTotalValue = redisClient.getAllSessionIDAndValuePair();
        logger.info("Initializing all sessionId and their state");
    }

    /**
     * set totalValue against sessionId.
     */
    public void settotalValueVsSessionID (String sessionId, String totalValue) {
        sessionIDVsTotalValue.put(sessionId, totalValue);
    }

    /**
     * Get titalValue against sessionId.
     */
    public String gettotalValueVsSessionID (String sessionId) {
        return sessionIDVsTotalValue.get(sessionId);
    }

    /**
     * Get List of sessionId.
     */
    public List<String> getSessionID () {

        logger.info("Total SessionId"+ ((List<String>) sessionIDVsTotalValue.keySet()).size());
        logger.info("SesssionId's ::\t"+((List<String>) sessionIDVsTotalValue.keySet()) );
        return (List<String>) sessionIDVsTotalValue.keySet();
    }
}
