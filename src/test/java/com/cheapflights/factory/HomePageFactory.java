package com.cheapflights.factory;

import com.cheapflights.abstractpages.AbstractHomePage;
import com.cheapflights.desktop.EmptyHomePage;
import com.cheapflights.desktop.PrefilledHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageFactory {

    private WebDriver driver;

    private static String originFieldAttribute = "value";
    private static String originFieldValue = "";

    @FindBy(name = "origin")
    protected static WebElement origin;

    public HomePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static AbstractHomePage getCorrectPage(WebDriver driver) {
        if (origin.getAttribute(originFieldAttribute).equals(originFieldValue)) {
            return new EmptyHomePage(driver);
        } else {
            return new PrefilledHomePage(driver);
        }
    }

}
