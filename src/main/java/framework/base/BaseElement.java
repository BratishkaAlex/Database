package framework.base;

import framework.browser.Browser;
import framework.utils.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static framework.utils.LoggerUtil.LOGGER;

public abstract class BaseElement {
    private String name;
    private By loc;
    private WebElement webElement;

    public BaseElement(By loc, String name) {
        LOGGER.info(String.format("Creating instance of %s", name));
        this.loc = loc;
        this.name = name;
        this.webElement = Browser.getDriver().findElement(loc);
    }

    public static List<WebElement> getListElements(By loc) {
        return Browser.getDriver().findElements(loc);
    }

    public static int countOfSuchElements(By loc) {
        return Browser.getDriver().findElements(loc).size();
    }

    public boolean isDisplayed() {
        LOGGER.info(String.format("Checking displaying of %s", name));
        return webElement.isDisplayed();
    }

    public String getText() {
        LOGGER.info(String.format("Getting text of %s", name));
        return webElement.getText();
    }

    public void click() {
        LOGGER.info(String.format("Click on %s", name));
        getWebElement().click();
    }

    public void waitAndClick() {
        LOGGER.info(String.format("Wait for clickable and then click on %s", name));
        Waiter.waitForClickAble(loc);
        getWebElement().click();
    }

    public String getAttribute(String attributeName) {
        return webElement.getAttribute(attributeName);
    }

    public List<WebElement> findElementsFromCurrent(By loc) {
        return webElement.findElements(loc);
    }

    public WebElement findElementFromCurrent(By loc) {
        return webElement.findElement(loc);
    }

    protected WebElement getWebElement() {
        return this.webElement;
    }

    public boolean isEnabled() {
        return webElement.isEnabled();
    }
}
