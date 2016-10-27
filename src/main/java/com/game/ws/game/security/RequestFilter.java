package com.game.ws.game.security;

import com.game.ws.game.access.SessionIDRepository;
import com.game.ws.game.constants.GameConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest servletRequest;

    @Autowired
    private SessionIDRepository sessionIDRepository;

    private static Logger logger = Logger
            .getLogger(RequestFilter.class);

    /**
     * It will intercept incoming request from different consumer.
     * verify sessionId.
     * logs hostName, ip details.
     * According to sessionId pass the request to controllers.
     * @param requestContext
     * @throws IOException
     */
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        logger.info("game-ws being accessed from : IP :" + servletRequest.getRemoteAddr()
                + ",\n HostName is :" + servletRequest.getRemoteHost()
                + ", \n User-Agent : " + servletRequest.getHeader("user-agent")
                + ", \n Host : " + servletRequest.getHeader("host"));

        String path = requestContext.getUriInfo().getPath();

        String sessionid = null;
        Cookie[] gameCookie = servletRequest.getCookies();
        if (gameCookie != null) {
            for (int i = 0; i < gameCookie.length; i++) {
                if (gameCookie[i].getName().equals("sessionid")) {
                    sessionid = gameCookie[i].getValue();
                    break;
                }
            }
        }
        try {
            if (GameConstants.GAME_CONNECT_PATH.equals(path)) {
                if (sessionid == null) {
                    sessionid = generateSessionId();
                }
                logger.debug("adding sessionId to header: " + sessionid);
                requestContext.getHeaders().add("sessionId", sessionid);
            }
            else {
                List<String> gameSessionIdList = sessionIDRepository.getSessionID();

                if(gameSessionIdList.contains(sessionid)) {
                    logger.debug("adding sessionId to header: " + sessionid);
                    logger.info("Total score for the sessionId" );
                    requestContext.getHeaders().add("sessionId", sessionid);
                }
                else {
                    sessionExpiredRequestContext(requestContext);
                }
            }

        } catch (Exception e) {
            logger.error("Exception in the filter: " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("filtration was successfully executed");
    }

    private static String generateSessionId() throws UnsupportedEncodingException {
        String uid = new java.rmi.server.UID().toString(); // guaranteed unique
        return URLEncoder.encode(uid,"UTF-8"); // encode any special chars
    }

    /**
     * It will send session expired status.
     * If session is expired user cannot access other url. Need to start the game again,
     * it will generate another sessionId.
     * @param requestContext
     */
    private void sessionExpiredRequestContext(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Your Session Expired").build());
    }
}
