package com.automation.framework.loging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLogger implements ConsoleLogger {
    private static final Logger logger = LogManager.getLogger();

    public static void log(String message) {
        logger.info(message);
    }
}
