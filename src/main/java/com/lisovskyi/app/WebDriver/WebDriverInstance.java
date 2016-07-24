package com.lisovskyi.app.WebDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class WebDriverInstance {

	private static Logger logger = LogManager.getLogger(WebDriverInstance.class);
	
	private static WebDriver driver = null;
	
	/**
	 * Call this method to get driver instance.
	 * @return {@link WebDriver} instance.
	 */
	public static WebDriver getDriver(){
		if(driver == null){
			logger.error("Webdriver hasn't been set. Make sure you use the setter right after the driver is initialized");
			throw new NullPointerException("Webdriver instance is null, make sure you "
	                + "use the setter right after the driver instance is created.");
		}
		return driver;
	}
	
	public static void setDriver(WebDriver driver){
		WebDriverInstance.driver = driver;
		logger.info("Webdriver instance has been set.");
	}
	
	
	
	
}