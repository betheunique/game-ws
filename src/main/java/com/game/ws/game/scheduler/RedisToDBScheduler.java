package com.game.ws.game.scheduler;

import com.game.ws.game.access.SessionIDAccess;
import com.game.ws.game.cache.RedisClient;
import com.game.ws.game.db.DBService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by abhishekrai on 27/10/2016.
 * Running the scheduler every 2 hours.
 * Data to be inserted into db from redis every 2 hours.
 */
@Component
public class RedisToDBScheduler {
    private static Logger logger = Logger.getLogger(RedisToDBScheduler.class);
    private static final long TIME_TO_RUN_SCHEDULER_IN_MILLISEC = 60 * 60 * 2 * 1000;
    public ScheduledExecutorService scheduleExecutor = Executors.newSingleThreadScheduledExecutor();

    private Map<String, String> sessionIDVsTotalValue = new HashMap<String, String>();

    @Autowired
    private DBService genericDBService;

    @PostConstruct
    public void initialize(){
        startExecutor();
    }

    public void startExecutor(){
        scheduleExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                logger.debug("RedisToDBScheduler...");
                insertSessionInDB();
            }
        }, millisecsToInitialRun(), TIME_TO_RUN_SCHEDULER_IN_MILLISEC, TimeUnit.MILLISECONDS);

    }

    /*
     * returns the next even hour
     * For e.g if time is 1 pm, the next even hour is 2 pm
     */
    private long millisecsToInitialRun(){
        long initialDelay = 0;
        try{
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR);
            int minuteDiff = 60 - cal.get(Calendar.MINUTE);
            if(hour % 2 == 0){
                minuteDiff = minuteDiff + 60;
            }
            initialDelay = minuteDiff * 60 * 1000;
        }catch(Exception e){
            logger.error("Exception inside millisecsToInitialRun() : " + e.getMessage());
        }
        logger.debug("InitialDelay in millisecs : " + initialDelay);
        return initialDelay;
    }

    private void insertSessionInDB(){
        try{
            RedisClient redisClient = new RedisClient();

            sessionIDVsTotalValue = redisClient.getAllSessionIDAndValuePair();
            //TODO Batch insert into DB.

        }catch(Exception e){
            logger.error("Exception in Push Notification Scheduler, Inside getTokenFromDB() : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
