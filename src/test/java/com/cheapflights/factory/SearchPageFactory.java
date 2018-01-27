package com.cheapflights.factory;

import com.cheapflights.abstractpages.AbstractSearchPage;
import com.cheapflights.desktop.FirstFlightSearchPage;
import com.cheapflights.desktop.SecondFlightSearchPage;
import org.openqa.selenium.WebDriver;


public class SearchPageFactory {

    static String searchPageUrl = "https://global.cheapflights.com/flight-search/MOW-TYO";


    public static AbstractSearchPage getCorrectPage(WebDriver driver) {
        if (driver.getCurrentUrl().contains(searchPageUrl)) {
            return new FirstFlightSearchPage(driver);
        } else {
            return new SecondFlightSearchPage(driver);
        }
    }
}
