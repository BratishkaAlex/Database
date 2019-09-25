package framework.base;

import framework.browser.Browser;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public abstract class BaseTest {
    @BeforeTest
    public void setUpBrowser() {
        Browser.maximize();
    }

    @AfterTest
    public void closeBrowser() {
        Browser.closeBrowser();
    }
}
