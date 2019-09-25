package framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static framework.utils.LoggerUtil.LOGGER;

public class PropertyManager {

    private static String pathToPropertiesFile = "src/main/resources/config.properties";
    private static String pathToMailPropertiesFile = "src/main/resources/mail.properties";
    private static String pathToEnDictionary = "src/main/resources/en.properties";
    private static String pathToRuDictionary = "src/main/resources/ru.properties";
    private static String pathToDatabasePropertiesFile = "src/main/resources/database.properties";

    public static String getConfigProperty(String property) {
        LOGGER.warn("Reading property '" + property + "' from .properties file, can be FileNotFoundException or IOException");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(pathToPropertiesFile)));
        } catch (FileNotFoundException e) {
            LOGGER.error("Config.property wasn't found", e);
        } catch (IOException e) {
            LOGGER.error("Error in reading .property file", e);
        }
        return properties.getProperty(property);
    }

    public static Properties getPropertiesForMail() {
        LOGGER.warn("Reading properties for mail from .properties file, can be FileNotFoundException or IOException");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(pathToMailPropertiesFile)));
        } catch (FileNotFoundException e) {
            LOGGER.error("mail.property wasn't found", e);
        } catch (IOException e) {
            LOGGER.error("Error in reading mail.property file", e);
        }
        return properties;
    }

    public static String getPropertyForMail(String property) {
        LOGGER.warn("Reading property '" + property + "' from .properties file, can be FileNotFoundException or IOException");
        return getPropertiesForMail().getProperty(property);
    }

    public static String getDatabaseProperty(String property) {
        LOGGER.warn("Reading property '" + property + "' from .properties file, can be FileNotFoundException or IOException");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(pathToDatabasePropertiesFile)));
        } catch (FileNotFoundException e) {
            LOGGER.error("Config.property wasn't found", e);
        } catch (IOException e) {
            LOGGER.error("Error in reading .property file", e);
        }
        return properties.getProperty(property);
    }

    public static String getWordFromDictionary(String word) {
        LOGGER.warn("Reading word '" + word + "' from .properties dictionary, can be FileNotFoundException or IOException");
        Properties properties = new Properties();
        try {
            switch (PropertyManager.getConfigProperty("language")) {
                case "ru":
                    properties.load(new FileReader(new File(pathToRuDictionary)));
                    break;
                case "en":
                    properties.load(new FileReader(new File(pathToEnDictionary)));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown language");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Dictionary wasn't found", e);
        } catch (IOException e) {
            LOGGER.error("Error in reading dictionary file", e);
        }
        return properties.getProperty(word);
    }

}
