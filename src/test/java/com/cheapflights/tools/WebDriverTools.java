package com.cheapflights.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverTools {


    public static void waitForVisibilityFluently(WebDriver driver, WebElement element, int timeout, int poll) {
        new FluentWait(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(poll, TimeUnit.SECONDS)
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));

    }

    public static void waitForInvisibilityExplicitly(WebDriver driver, WebElement element, int timeout) {
        new WebDriverWait(driver, timeout).until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForAttributeToBe(WebDriver driver, By by, String attribute, String value, int timeout) {
        new WebDriverWait(driver, timeout).until(ExpectedConditions.attributeToBe(by, attribute, value));
    }


    public static boolean waitForJSandJQueryToLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
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

//    public void highlightElement(String xpath) {
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//        jsExecutor.executeScript("arguments[0].setAttribute('style','border: solid 2px red')", this.getDriver().findElement(By.xpath(xpath)));
//    }
}
