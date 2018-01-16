package com.cheapflights.factory;

import com.cheapflights.abstractpages.AbstractSearchPage;
import com.cheapflights.desktop.SecondFlightSearchPage;
import com.cheapflights.desktop.FirstFlightSearchPage;
import org.openqa.selenium.WebDriver;


public class SearchPageFactory {

    static String searchPageUrl = "https://global.cheapflights.com/flight-search/MOW-TYO";


    public static AbstractSearchPage getCorrectPage(WebDriver driver) {

        AbstractSearchPage page;
        if (driver.getCurrentUrl().contains(searchPageUrl)) {
            page = new FirstFlightSearchPage(driver);
        } else {
            page = new SecondFlightSearchPage(driver);
        }
        return page;

    }
}
