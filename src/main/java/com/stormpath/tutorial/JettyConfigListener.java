package com.stormpath.tutorial;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JettyConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String jettyServletPrefix = "org.eclipse.jetty.servlet.Default.";
        sce.getServletContext().setInitParameter(jettyServletPrefix + "dirAllowed", "false");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
