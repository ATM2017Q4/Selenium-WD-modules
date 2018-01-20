package com.cheapflights.tests;

import com.cheapflights.abstractpages.AbstractHomePage;
import com.cheapflights.factory.HomePageFactory;
import com.cheapflights.factory.SearchPageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CheapFlightsTest {

    private WebDriver driver;
    private AbstractHomePage homePage;


    String hubUrl = "http://localhost:4444/wd/hub";


    @BeforeClass
    @Parameters({"browser"})
    public void launchBrowser() {
        // System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();

        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "MAC");
        try {
            driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Parameters({"url"})
    @BeforeClass(dependsOnMethods = "launchBrowser", description = "Add implicit wait and maximize window")
    public void openUrl(String url) {
        driver.get(url);

    }

    @Parameters({"origin", "destination", "period",
            "startDate", "endDate", "numberOfAdults",
            "sliderDivider", "sliderMultiplier"})
    @Test(description = "Fill in form and get the cheapest flight")
    public void chooseTheCheapestFlight(String origin, String destination, String period,
                                        String startDate, String endDate, int numberOfAdults,
                                        int sliderDivider, int sliderMultiplier) {
        HomePageFactory pageFactory = new HomePageFactory(driver);
        homePage = pageFactory.getCorrectPage(driver);
        homePage.chooseOrigin(origin)
                .chooseDestination(destination)
                .chooseDates(period, startDate, endDate)
                .increaseNumberOfAdults(numberOfAdults)
                .submitForm()
                .chooseNonStopFlights()
                .modifyDuration(sliderDivider, sliderMultiplier)
                .sortByCheapest()
                .closeFilters();
        Assert.assertTrue(SearchPageFactory.getCorrectPage(driver).getCheapestFlight() < 550);

    }

    @AfterClass(description = "Close browser")
    public void tearDown() {
        driver.quit();
    }


}
