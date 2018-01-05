package com.epam.selenium.pages.abstractpages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public abstract class AbstractPage {

    protected WebDriver driver;
    protected Logger logger = Logger.getLogger(this.getClass().getName());


    public AbstractPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void waitForVisibilityFluently(WebElement element, int timeout, int poll) {
        new FluentWait(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(poll, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClickabilityFluently(WebElement element, int timeout, int poll) {
        new FluentWait(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(poll, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibilityExplicitly(WebElement element, int timeout) {
        new WebDriverWait(driver, timeout).until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForAttributeToBe(By by, String attribute, String value, int timeout) {
        new WebDriverWait(driver, timeout).until(ExpectedConditions.attributeToBe(by, attribute, value));
    }


    public boolean waitForJSandJQueryToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public boolean isDisplayed(List<WebElement> element) {
        boolean result;
        try {
            result = (element.size() > 0 && element.get(0).isDisplayed());

        } catch (org.openqa.selenium.NoSuchElementException e) {
            result = false;
        }
        return result;
    }

    public String getElementText(String xpath) {
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public void highlightElement(String xpath) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('style','border: solid 2px red')", this.getDriver().findElement(By.xpath(xpath)));

    }


}
