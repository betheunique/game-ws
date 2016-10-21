package com.game.ws.application.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

/**
 * Created by abhishekrai on 19/10/2016.
 */
@Aspect
@Component
public class LoggingAspect {

    private Logger logger = Logger.getLogger("reportsLogger");
    @Pointcut("within(com.game.ws.game.controllers..*)")
    public void pointCut() {}

    StopWatch stopWatch;
    @Before(value="pointCut()")
    public void beforeMethod(JoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("Inside "+ joinPoint.getTarget().getClass().getSimpleName() + " class");
    }

    @After(value="pointCut()")
    public void afterMethod(JoinPoint joinPoint) throws Throwable {
        if(stopWatch != null && stopWatch.isRunning())
            stopWatch.stop();
        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        logMessage.append(")");
        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");
        logger.info(logMessage.toString());
    }
}
