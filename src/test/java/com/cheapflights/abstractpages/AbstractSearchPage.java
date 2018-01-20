package com.cheapflights.abstractpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public abstract class AbstractSearchPage {

    protected static WebDriver driver;

    protected Logger logger = Logger.getLogger(this.getClass().getName());

    public AbstractSearchPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public abstract AbstractSearchPage chooseNonStopFlights();

    public abstract AbstractSearchPage modifyDuration(int divider, int multiplier);

    public AbstractSearchPage sortByCheapest() {
        return this;
    }

    public abstract int getCheapestFlight();

    public void closeFilters() {

    }


}
