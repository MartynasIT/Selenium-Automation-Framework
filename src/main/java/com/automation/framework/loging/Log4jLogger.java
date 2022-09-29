package com.automation.framework.loging;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLogger implements ConsoleLogger {
    @Getter @Setter
    private Logger logger = LogManager.getLogger();

    public Log4jLogger() {
        setLogger(LogManager.getLogger());
    }

    @Override
    public void log(String message) {
        getLogger().info(message);
    }
}
