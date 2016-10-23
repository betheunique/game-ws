package com.game.ws.game.embedded;

import com.game.ws.game.constants.GameConstants;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.io.ClassPathResource;


import java.io.IOException;

/**
 * Created by abhishekrai on 22/10/2016.
 */
public class EmbeddedJetty {

    private static Logger logger = Logger.getLogger(EmbeddedJetty.class);

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(getPortFromArgs(args));
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        logger.info("No server port configured, falling back to {}" + GameConstants.GAME_EMBEDDED_JETTY_PORT);
        return GameConstants.GAME_EMBEDDED_JETTY_PORT;
    }

    private void startJetty(int port) throws Exception {
        logger.debug("Starting server at port {}"+ port);
        Server server = new Server(port);
        server.setHandler(getServletContextHandler());
        server.start();
        logger.info("Server started at port {}"+ port);
        server.join();
    }

    private static WebAppContext getServletContextHandler() throws IOException {
        WebAppContext contextHandler = new WebAppContext();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(GameConstants.GAME_EMBEDDED_JETTY_CONTEXT_PATH);
        contextHandler.setWar("src/main/webapp");
        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);

        contextHandler.addServlet(servletHolder, "/");

        //contextHandler.addServlet(org.springframework.web.servlet.DispatcherServlet.class, "/classes");
        contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        return contextHandler;
    }

}
