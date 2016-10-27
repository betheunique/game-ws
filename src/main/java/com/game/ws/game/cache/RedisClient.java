package com.game.ws.game.cache;

import com.game.ws.game.access.SessionIDAccess;
import com.game.ws.game.constants.RedisConstants;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Service
public class RedisClient {

    private static Logger logger = Logger.getLogger(RedisClient.class);

    @Autowired
    private RedisProperties redisProperties;
    private String dataStructure;
    private Jedis jedis = null;

    @PostConstruct
    private void initializeRedisClient(){
        jedis = new Jedis(redisProperties.getPropertiesTextValue(RedisConstants.REDIS_SERVER));
        dataStructure = redisProperties.getPropertiesTextValue(RedisConstants.REDIS_DATA_STRUCTURE);
    }
    /**
     * This method will check if the redis is up and running fine.
     * If not then it will catch the exception thrown.
     * @return
     */
    public boolean checkIfRedisIsUp(){
        logger.debug("Checking if Redis is running..");
        try{
            jedis.connect();
            if("PONG".equalsIgnoreCase(jedis.ping())){
                return true;
            }
        }catch(JedisConnectionException ex){
            logger.error("An error occured while connecting to Redis..." + ex.getMessage());
        }catch(NullPointerException npe){
            logger.error("Jedis instance is null: " + npe.getMessage());
        }catch(Exception e){
            logger.error("Something went wrong while connecting to Redis: " + e.getMessage());
        }
        return false;
    }
    /**
     * This method is called to disconnect Redis when required.
     */
    public void disconnectRedis(){
        logger.debug("Disconnecting Redis client...");
        jedis.disconnect();
    }

    /**
     * This method is responsible for inserting data in the Redis cache(Hash data structure.)
     * Upon successful insertion it returns 1 or 0 (0 in case if the entry was already present
     * against the key formed.)
     * Before doing any action on the redis server it will first check the status of the redis in the
     * properties file and on result of ping command from the server, if both looks okay then it goes
     * ahead and does the insertion otherwise it return -1.
     * @return
     */
    @Async
    public long insertTotalValueVsSessionId (String key, String totalValue) {
        if(RedisConstants.REDIS_IS_ON.equals(redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY)) && checkIfRedisIsUp()){

            int seconds = (int)20*60*60;
//            SessionIDAccess sessionIDAccess = new SessionIDAccess();
            logger.debug("The expiry time is being set as: " + seconds);
            long returnValue = jedis.hsetnx(key, dataStructure, totalValue);

            jedis.expire(key, seconds);
            return returnValue;
        } else {
            logger.debug("Either redis is off in properties file or Redis is down..please check the redis server or propeorties file(0 mean stop, 1 mean running).." + redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY));
            return -1;
        }
    }

    /**
     * This method will retrieve the data from the cache stored against the provide key.
     * It will first check the status of the redis in the properties file and then on the
     * server, if both comes fine then it goes ahead and fetch the value against the key
     * otherwise it returns NULL.
     * @param key
     * @return
     */
    public String getTotalValueVsSessionId (String key) {

        if(RedisConstants.REDIS_IS_ON.equals(redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY)) && checkIfRedisIsUp()) {
            String totalValue = null;
            try {
                totalValue = jedis.hget(key, dataStructure);
            } catch (JedisDataException ex) {
                logger.error(" Error while retrieving data from cache--" + ex.getMessage());
            }
            if (totalValue == null) {
                logger.debug("No results were found in hte cache corresponding to the key :" + key);
                return null;
            } else {
                return totalValue;
            }
        } else {
            logger.info("Either redis is off in properties file or Redis is down..please check the redis server or propeorties file(0 mean stop, 1 mean running).." + redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY));
            return null;
        }
    }
    /**
     * Update the key in the cache. The below method will return 0 in case
     * the key was present and updated, if not then it will returns 1 which indicates new insertion.
     */
    public int updateSessionIdInRedis (String key, String totalvalue) {
        logger.debug("updating results in the cache..");
        if(RedisConstants.REDIS_IS_ON.equals(redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY)) && checkIfRedisIsUp()){

            long result = jedis.hset(key, dataStructure, totalvalue);
            //set expiry time
            if(result == 0 || result == 1){
                logger.debug("Update completed..." + result);
                return 0;
            }else{
                return -1;
            }

        } else{
            logger.debug("Redis is not running..please check server or properties file..");
            return -1;
        }
    }


    /**
     * Get all the key and value pair form cache. The below method will return a hashMap.
     * return all the keys with corresponding value mapping, a HashMap.
     * If redis is not running it will return null.
     */
    public Map<String, String> getAllSessionIDAndValuePair() {
        logger.debug("getting results ke/value pair from redis ..");
        if(RedisConstants.REDIS_IS_ON.equals(redisProperties.getPropertiesTextValue(RedisConstants.REDIS_ON_KEY)) && checkIfRedisIsUp()){
            Map<String, String> hgetAll = jedis.hgetAll(dataStructure);
            logger.info("All values in redis cache ::"+ hgetAll);
            return hgetAll;
        } else{
            logger.debug("Redis is not running..please check server or properties file..");
            return null;
        }

    }
}
