package com.epam.selenium.pages.desktop;

import com.epam.selenium.pages.abstractpages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class PrefilledHomePage extends AbstractPage {

    public PrefilledHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "origin")
    private WebElement origin;

    @FindBy(name = "destination")
    private WebElement destination;

    @FindBy(xpath = "//div[contains(@id, 'origin-smartbox-dropdown')]")
    private WebElement originOptions;

    @FindBy(xpath = "//div[contains(@id, 'destination-smartbox-dropdown')]")
    private WebElement destinationOptions;

    @FindBy(xpath = "//div[@aria-label='Departure date input']")
    private WebElement departureDateField;

    @FindBy(xpath = "//div[@class='contentContainer']")
    private WebElement datePicker;

    @FindBy(xpath = "//div[@class='monthDisplay']")
    private WebElement monthName;

    @FindBy(css = "div[aria-label='Next month']")
    private WebElement nextButton;

    @FindBy(xpath = "(//button[@type=\"submit\"]//span[contains(text(), 'Find deals')])[1]")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class=\"cabinTravelerWrapper\"]//div[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath = "//a[@aria-label='Select number of travelers and cabin class']")
    private WebElement travellersNumber;

    @FindBy(xpath = "(//div[contains(@id, 'adults')]//button[@title=\"Increment\"])[2]")
    private WebElement adultsPlus;

    private By dates = By.xpath("(//div[@class='weeks'])[3]//div[@class='day']");

    public PrefilledHomePage chooseOrigin(String from) {
        origin.click();
        origin.clear();
        origin.sendKeys(from);
        waitForVisibilityFluently(originOptions, 2, 1);
        origin.sendKeys(Keys.ENTER);
        return this;
    }

    public PrefilledHomePage chooseDestination(String to) {
        destination.click();
        destination.sendKeys(to);
        waitForVisibilityFluently(destinationOptions, 2, 1);
        destination.sendKeys(Keys.ENTER);
        return this;
    }

    public PrefilledHomePage chooseDates(String period, String startDate, String endDate) {
        departureDateField.click();
        waitForVisibilityFluently(datePicker, 10, 1);
        By october = By.xpath("//div[contains(text(), '" + period + "')]");

        while (!(monthName.getText().contains(period))) {
            nextButton.click();
            if (isDisplayed(driver.findElements(october))) {
                break;
            }
        }
        By endDateLocator = By.xpath("(//div[@class='weeks'])[3]//div[contains(text(), '" + endDate + "')]");
        List<WebElement> duration = driver.findElements(dates);
        for (WebElement day : duration) {
            if (day.getText().equals(startDate)) {
                Actions chooser = new Actions(driver);
                chooser.click(day)
                        .sendKeys(Keys.TAB)
                        .moveToElement(driver.findElement(endDateLocator))
                        .click(driver.findElement(endDateLocator))
                        .build().perform();

                break;
            }
        }
        return this;

    }

    public PrefilledHomePage increaseNumberOfAdults(int number) {
        travellersNumber.click();
        for (int i = 1; i < number; i++) {
            adultsPlus.click();
        }
        closeButton.click();
        return this;
    }

    public void submitForm() {
        submitButton.click();
    }


}
