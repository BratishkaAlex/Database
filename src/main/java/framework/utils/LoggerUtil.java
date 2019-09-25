package framework.utils;

public class LoggerUtil {

    public static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger("TestLogger");

    public static void step(String message, int counter) {
        LOGGER.info(String.format("<<<<<<<<Step %d>>>>>>>>>>>>.\n%s\n", counter, message));
    }
}
