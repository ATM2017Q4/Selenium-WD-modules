package com.epam.selenium.pages.tests;

import com.epam.selenium.pages.desktop.EmptyHomePage;
import com.epam.selenium.pages.desktop.FirstFlightSearchPage;
import com.epam.selenium.pages.desktop.PrefilledHomePage;
import com.epam.selenium.pages.desktop.SecondFligthtSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CheapFlightsTest {

    private WebDriver driver;

    private By logoXpath = By.xpath("//div[contains(@class, 'logo')]//a[@href='/']");
    private By originFieldName = By.name("origin");

    private String originFieldAttribute = "value";
    private String originFieldValue = "";

    @BeforeClass
    @Parameters({"browser"})
    public void launchBrowser() {
       // System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        try {
            driver = new RemoteWebDriver(new URL("http://192.168.56.1:5566/wd/hub"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Parameters({"url"})
    @BeforeClass(dependsOnMethods = "launchBrowser", description = "Add implicit wait and maximize window")
    public void openUrl(String url) {
        driver.get(url);

    }

    @Parameters({"origin", "destination", "period",
            "startDate", "endDate", "numberOfAdults"})
    @Test(description = "Fill in form on the empty Home AbstractPage")
    public void fillInForm(String origin, String destination, String period,
                           String startDate, String endDate, int numberOfAdults) {
        WebElement originField = driver.findElement(originFieldName);
        if (originField.getAttribute(originFieldAttribute).equals(originFieldValue)) {
            EmptyHomePage hp1 = new EmptyHomePage(driver);
            hp1.chooseOrigin(origin)
                    .chooseDestination(destination)
                    .chooseStartDate(period, startDate)
                    .chooseEndDate(endDate)
                    .increaseNumberOfAdults(numberOfAdults)
                    .submitForm();
        } else {
            PrefilledHomePage hp2 = new PrefilledHomePage(driver);
            hp2.chooseOrigin(origin)
                    .chooseDestination(destination)
                    .chooseDates(period, startDate, endDate)
                    .increaseNumberOfAdults(numberOfAdults)
                    .submitForm();
        }

    }

    @Parameters({"searchPageUrl", "dollarSign", "sumPattern", "currencySymbolXpath",
            "sumXpath", "cheapestFlightXpath", "sliderDivider", "sliderMultiplier"})
    @Test(description = "Filter results", dependsOnMethods = "fillInForm")
    public void filterResults(String searchPageUrl, String dollarSign, String sumPattern, String currencySymbolXpath,
                              String sumXpath, String cheapestFlightXpath, int sliderDivider, int sliderMultiplier) {
        FirstFlightSearchPage sp1 = new FirstFlightSearchPage(driver);
        SecondFligthtSearchPage sp2 = new SecondFligthtSearchPage(driver);
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(logoXpath)));

        if (driver.getCurrentUrl().contains(searchPageUrl)) {
            sp1.chooseNonstopFlights()
                    .modifyDuration(sliderDivider, sliderMultiplier)
                    .sortByCheapest();
            Assert.assertTrue(sp1.getElementText(cheapestFlightXpath).matches(sumPattern));
        } else {
            sp2.chooseNonStopFligths()
                    .modifyDuration(sliderDivider, sliderMultiplier)
                    .closeFilters();
            Assert.assertTrue(sp2.getElementText(currencySymbolXpath).equals(dollarSign));
            Assert.assertTrue(sp2.getElementText(sumXpath).matches(sumPattern));
        }
    }

    @AfterClass(description = "Close browser")
    public void tearDown() {
        driver.quit();
    }


}
