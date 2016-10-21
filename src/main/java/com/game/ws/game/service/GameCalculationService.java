package com.game.ws.game.service;

import com.game.ws.game.access.SessionIDAccess;
import com.game.ws.game.access.SessionIDRepository;
import com.game.ws.game.beans.GameCalculationResponse;
import com.game.ws.game.cache.RedisClient;
import com.game.ws.game.db.DBService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Service
public class GameCalculationService {

    private static Logger logger = Logger.getLogger(GameCalculationService.class);

    @Autowired
    DBService genericDBService;

    public GameCalculationResponse calculate( int firstValue, int secondValue, String sessionId) {
        SessionIDRepository sessionIDRepository = new SessionIDRepository();
        logger.info("firstValue , secondValue and sessionId coming as  ::"+"\t"+ firstValue +"\t"+ secondValue +"\t"+ sessionId);
        int totalValue = 0;

        /**
         * Initializing game state for sessionId
         */
        if (null != sessionId) {
            totalValue = Integer.valueOf(sessionIDRepository.gettotalValueVsSessionID(sessionId));
        }
        /**
         * If SessionId exists get current stored state (totalValue).
         */
        if (firstValue == 0 && secondValue == 0 ) {
            return prepareCalculationResponse(totalValue);
        } else {
             totalValue += firstValue + secondValue;
            // TODO set sessionIdVsTotalValue.
            //Volatile storage
            // sessionIDRepository.settotalValueVsSessionID(sessionId, String.valueOf(totalValue));

            SessionIDAccess sessionAccess = new SessionIDAccess();
            sessionAccess.setSessionId(sessionId);
            sessionAccess.setTotalValue(String.valueOf(totalValue));

            /**
             * Converted to hibernate
             */
            genericDBService.insertRecord(sessionAccess);

            return prepareCalculationResponse(totalValue);
        }
    }

    /**
     * Prepare calculationObject.
     * @param totalValue
     * @return
     */
    private GameCalculationResponse prepareCalculationResponse(int totalValue) {
        GameCalculationResponse gameCalculationResponse = new GameCalculationResponse();
        gameCalculationResponse.setTotalValue(totalValue);
        gameCalculationResponse.setErrorCode(1);
        gameCalculationResponse.setErrorMessage("calculation Successful");
        return gameCalculationResponse;
    }
}
