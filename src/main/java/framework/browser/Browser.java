package framework.browser;

import org.openqa.selenium.WebDriver;

import static framework.utils.LoggerUtil.LOGGER;

public class Browser {

    private static WebDriver driver = null;

    private Browser() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = BrowserFactory.getDriver();
        }
        return driver;
    }

    public static void maximize() {
        getDriver().manage().window().maximize();
        LOGGER.warn("Maximize window");
    }

    public static void enterUrl(String url) {
        driver.get(url);
    }

    public static void closeBrowser() {
        LOGGER.warn("Close browser");
        getDriver().quit();
        driver = null;
    }
}
