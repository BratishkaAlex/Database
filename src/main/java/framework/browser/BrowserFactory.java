package framework.browser;

import framework.utils.PropertyManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.util.HashMap;

import static framework.utils.LoggerUtil.LOGGER;

class BrowserFactory {
    static WebDriver getDriver() {
        WebDriver driver;
        String browser = PropertyManager.getConfigProperty("browser");
        switch (browser) {
            case "chrome":
                LOGGER.info("Creating instance of ChromeDriver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getOptions(browser));
                break;
            case "firefox":
                LOGGER.info("Creating instance of FirefoxDriver");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getOptions(browser));
                break;
            default:
                throw new IllegalArgumentException("Wrong browser name!");
        }
        return driver;
    }

    private static MutableCapabilities getOptions(String browser) {
        switch (browser) {
            case "chrome":
                return getPropForChrome();
            case "firefox":
                return getPropsForFirefox();
            default:
                throw new IllegalArgumentException("Wrong browser name!");
        }
    }

    private static ChromeOptions getPropForChrome() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", new File(PropertyManager.getConfigProperty("path")).getAbsolutePath());
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("intl.accept_languages", PropertyManager.getConfigProperty("language"));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }

    private static FirefoxOptions getPropsForFirefox() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("intl.accept_languages", PropertyManager.getConfigProperty("language"));
        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-debian-package, application/octet-stream");
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.download.dir", new File(PropertyManager.getConfigProperty("path")).getAbsolutePath());
        return firefoxOptions;
    }
}
