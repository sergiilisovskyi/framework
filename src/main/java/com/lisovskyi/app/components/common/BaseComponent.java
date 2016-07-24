package com.lisovskyi.app.components.common;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.lisovskyi.app.WebDriver.WebDriverInstance;

public class BaseComponent {

	private static Logger logger = LogManager.getLogger(BaseComponent.class);
	
	private static final int TIMEOUT_SECONDS = 60;
	
	private static final int POOLING_PERIOD = 1;
	
	protected WebElement baseWebElement;

	/**
	 * Constructor of BaseComponent class
	 * @param baseWEBElement
	 */
	public BaseComponent(WebElement baseWEBElement) {
		this.baseElement = baseWEBElement;
	}
	
	
	
	private Wait<WebDriver> getFluentWait(){
		Wait<WebDriver> wait = new FluentWait<WebDriver>(WebDriverInstance.getDriver()).
				withTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).pollingEvery(POOLING_PERIOD, TimeUnit.SECONDS).
				ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class);
		return wait;
	}
	
	public WebElement waitForElement(){
		return getFluentWait().until(ExpectedConditions.visibilityOf(baseElement));
	}
	
	
	
	
	
	public WebElement getDescendant(final By descendantLocator){
		 WebElement descendantFound = null;
		 baseElement = getFluentWait().until(ExpectedConditions.visibilityOf(baseElement));
		 try{
			 String parentLocator = getLocatorForWebElement();
			 logger.info("Looking in parent: " + parentLocator + " for descendant: " + descendantLocator);
			 descendantFound = baseElement.findElement(descendantLocator);
		 } catch (NoSuchElementException e){
			 logger.error("Exception when searching for descendant: " + descendantLocator, e);
	            return descendantFound;
		 }
		 return descendantFound;
	}
	
	   /**
     * Extracts the locator used to identify the webElement in the DOM.
     * @return
     */
    protected String getLocatorForWebElement(WebElement element) {
        if (element == null) {
            return "Element is null";
        }
        return element.toString().substring(element.toString().indexOf("->"), element.toString().length());
    }
    
    /**
     * Extracts the locator used to identify the webElement in the DOM.
     * @return
     */
    protected String getLocatorForWebElement() {
        if (baseWebElement == null) {
            return "Element is null";
        }
        String locator;
        try {
            locator = baseWebElement.toString().substring(baseWebElement.toString().indexOf("->"),
                baseWebElement.toString().length());
        } catch (Exception e) {
            logger.catching(e);
            locator = "Element might no longer be present in the page";
        }
        return locator;
    }
	
	
	
}
