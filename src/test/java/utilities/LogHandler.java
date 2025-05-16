package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {

    private static final Logger logger = LogManager.getLogger();

    // Log a simple message (String)
    public static void info(String message) {
        logger.info(message);
    }

   // Log an exception along with a message
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }


    
}

