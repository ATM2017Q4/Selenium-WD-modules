package com.cheapflights.desktop;

import com.cheapflights.tools.WebDriverTools;
import com.cheapflights.abstractpages.AbstractSearchPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Level;

public class FirstFlightSearchPage extends AbstractSearchPage {

    public FirstFlightSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "2")
    private WebElement twoStops;

    @FindBy(name = "1")
    private WebElement oneStop;

    @FindBy(xpath = "//div[@aria-label='Maximum flight duration']")
    private WebElement slider;

    @FindBy(xpath = "//div[contains(@id, 'legdur-content')]//div[contains(@class,'activeRange')]")
    private WebElement progress;

    @FindBy(xpath = "//div[@data-name='sort-section']")
    private WebElement sortSection;

    @FindBy(xpath = "//div[@data-name='sort-section']//div//div/span")
    private WebElement sortSectionValue;

    @FindBy(xpath = "//li[@data-title='Cheapest']")
    private WebElement cheapestFlights;

    @FindBy(css = "ul[class = 'dropdownList']")
    private WebElement sortingList;

    @FindBy(xpath = "//div[@class='Common-Results-ProgressBar Hidden']")
    private WebElement progressBar;

    private By loadComplete = By.xpath("//div[@class='resultsContainer']/div[contains(@id, 'cover')]");

    private static String cheapestFlightXpath = "(//div[@class='above-button']//a[@class='booking-link']/span[@class='price option-text'])[1]";

    public FirstFlightSearchPage chooseNonStopFlights() {
        try {
            WebDriverTools.waitForInvisibilityExplicitly(driver, progressBar, 100);
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.log(Level.SEVERE, "Driver was unable to locate the element during the specified amount of time", e);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.log(Level.SEVERE, "Driver was not able to find the element by the specified locator." + e);
        } finally {
            WebDriverTools.waitForJSandJQueryToLoad(driver);
            oneStop.click();
            WebDriverTools.waitForJSandJQueryToLoad(driver);
            twoStops.click();
            WebDriverTools.waitForJSandJQueryToLoad(driver);
        }
        return this;
    }

    public FirstFlightSearchPage modifyDuration(int divider, int multiplier) {
        //Here Javascript is used as a workaround as moveToElement doesn't scroll the element into view in ForeFox and MoveTargetOutOfBoundsException is thrown
        //https://github.com/mozilla/geckodriver/issues/776
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider);
        Dimension size = progress.getSize();
        int sliderWidth = size.getWidth();

        Actions builder = new Actions(driver);
        builder.moveToElement(slider).click()
                .dragAndDropBy
                        (slider, -((sliderWidth / divider) * multiplier), 0)
                .build()
                .perform();
        WebDriverTools.waitForJSandJQueryToLoad(driver);
        return this;
    }

    public FirstFlightSearchPage sortByCheapest() {
        if (sortSectionValue.getText().equals("Cheapest")) {
            sortSection.click();
            WebDriverTools.waitForVisibilityFluently(driver, sortingList, 5, 1);
            cheapestFlights.click();
            WebDriverTools.waitForAttributeToBe(driver, loadComplete, "class", "resultsListCover tl", 20);
        }
        return this;
    }

    public int getCheapestFlight() {
        String[] price;
        int sum;
        String cheapestFlight = driver.findElement(By.xpath(cheapestFlightXpath)).getText();
        price = cheapestFlight.split("\\$");
        sum = Integer.parseInt(price[1]);
        return sum;
    }

}
