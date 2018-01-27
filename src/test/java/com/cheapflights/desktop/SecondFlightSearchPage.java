package com.cheapflights.desktop;

import com.cheapflights.abstractpages.AbstractSearchPage;
import com.cheapflights.tools.WebDriverTools;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Level;

public class SecondFlightSearchPage extends AbstractSearchPage {
    public SecondFlightSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//label[contains(text(), 'Cheapest')]")
    private WebElement cheapestFlight;

    @FindBy(css = "a[data-index='3']")
    private WebElement stopsFilter;

    @FindBy(css = "label[for='filters-stops-multi']")
    private WebElement multiStops;

    @FindBy(css = "label[for='filters-stops-one']")
    private WebElement oneStop;

    @FindBy(css = "a[data-index='2']")
    private WebElement durationFilter;

    @FindBy(css = "div[id='duration-slider-outbound'] div[class='handle']")
    private WebElement slider;

    @FindBy(css = "div[id='duration-slider-outbound'] div[class='slider-bar']")
    private WebElement progress;

    @FindBy(css = "span[class='handle-top-items']>i[class='icon-arrow-down']")
    private WebElement closeButton;

    @FindBy(id = "update-indicator")
    private WebElement updateIndicator;

    private String cheapestFlightXpath = "//div[@class='quicklink cheapest clearfix selected-filter']//span[@class='value']";

    public SecondFlightSearchPage chooseNonStopFlights() {
        try {
            WebDriverTools.waitForVisibilityFluently(driver, cheapestFlight, 300, 10);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.log(Level.SEVERE, "Driver was unable to locate the element: either the page didn't load properly or the element doesn't exist");
            WebDriverTools.waitForVisibilityFluently(driver, cheapestFlight, 150, 10);
        } finally {
            stopsFilter.click();
            WebDriverTools.waitForJSandJQueryToLoad(driver);
            oneStop.click();
            multiStops.click();
        }
        return this;
    }

    public SecondFlightSearchPage modifyDuration(int divider, int multiplier) {
        durationFilter.click();
        Dimension size = progress.getSize();
        int sliderWidth = size.getWidth();
        Actions builder = new Actions(driver);
        builder
                .dragAndDropBy
                        (slider, -((sliderWidth / divider) * multiplier), 0)
                .build()
                .perform();
        return this;
    }

    public void closeFilters() {
        closeButton.click();
        WebDriverTools.waitForInvisibilityExplicitly(driver, updateIndicator, 10);
    }


    public int getCheapestFlight() {
        String cheapestFlight = driver.findElement(By.xpath(cheapestFlightXpath)).getText();
        int sum = Integer.parseInt(cheapestFlight);
        return sum;
    }

}
