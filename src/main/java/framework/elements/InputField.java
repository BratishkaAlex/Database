package framework.elements;

import framework.base.BaseElement;
import org.openqa.selenium.By;

public class InputField extends BaseElement {

    public InputField(By loc, String name) {
        super(loc, name);
    }

    public void sendKeys(String text) {
        super.getWebElement().sendKeys(text);
    }
}
