package com.cheapflights.desktop;

import com.cheapflights.abstractpages.AbstractHomePage;
import com.cheapflights.tools.WebDriverTools;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EmptyHomePage extends AbstractHomePage {

    public EmptyHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@class='next-month']")
    private WebElement nextButton;

    @FindBy(xpath = "//th[@class='-monthname']/span")
    private WebElement monthName;

    @FindBy(css = "svg[class='icon -departureDate']")
    private WebElement departureField;

    @FindBy(css = "svg[class='icon -returnDate']")
    private WebElement arrivalField;

    @FindBy(xpath = "//span[@class='cfui -field -spinner -numAdults']//a[@class='spin-up']")
    private WebElement adultsPlus;

    @FindBy(css = "div[class='lookup']")
    private WebElement options;

    @FindBy(xpath = "//button[@class='-sunspear']")
    private WebElement submitButton;

    private final By departureDates = By.xpath("//div/table[1]//td");
    private final By returnDates = By.xpath("//div/table[1]//td");


    public EmptyHomePage chooseOrigin(String from) {
        origin.click();
        origin.sendKeys(from);
        WebDriverTools.waitForVisibilityFluently(driver, options, 2, 1);
        origin.sendKeys(Keys.ARROW_DOWN);
        origin.sendKeys(Keys.ENTER);
        return this;


    }

    public EmptyHomePage chooseDestination(String to) {
        destination.click();

        destination.sendKeys(to);
        WebDriverTools.waitForVisibilityFluently(driver, options, 2, 1);
        destination.sendKeys(Keys.ARROW_DOWN);
        destination.sendKeys(Keys.ENTER);
        return this;
    }

    public EmptyHomePage chooseDates(String period, String startDate, String endDate) {
        departureField.click();
        while (!(monthName.getText().contains(period.toUpperCase()))) {
            nextButton.click();

        }
        List<WebElement> departure = driver.findElements(departureDates);

        for (WebElement cell : departure) {
            if (cell.getText().equals(startDate)) {
                cell.click();
                break;
            }
        }

        arrivalField.click();

        List<WebElement> arrival = driver.findElements(returnDates);
        for (WebElement cell : arrival) {
            if (cell.getText().equals(endDate)) {
                cell.click();
                break;
            }
        }
        return this;

    }

    public EmptyHomePage increaseNumberOfAdults(int number) {
        for (int i = 1; i < number; i++) {
            adultsPlus.click();
        }
        return this;
    }

}
