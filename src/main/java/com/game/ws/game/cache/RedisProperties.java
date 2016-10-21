package com.game.ws.game.cache;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Component
public class RedisProperties {

    private static Logger logger = Logger.getLogger(RedisProperties.class);
    private String REDIS_PROPERTIES_FILE = "redisCache.properties";

    private Properties properties = new Properties();

    @PostConstruct
    private void initializeRedisProperties(){
        try{
            properties.load(this.getClass().getClassLoader().getResourceAsStream(REDIS_PROPERTIES_FILE));
        }catch(IOException ioe){
            logger.error("Error while loading redis cache properties: " + ioe.getMessage());
        }
    }

    public void reloadProperties(){
        logger.debug("Re-initializing redis properties..");
        initializeRedisProperties();
    }

    public String getPropertiesTextValue(String key){
        return (String)properties.get(key);
    }
}
