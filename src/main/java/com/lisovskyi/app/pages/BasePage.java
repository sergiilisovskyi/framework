package com.lisovskyi.app.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object base class. It provides the base structure and properties for a
 * page object to extend.
 */

public class BasePage {

	protected String URL;

	protected WebDriver driver;

	// Expected page title
	protected String pageTitle;

	public BasePage(WebDriver driver, String pageTitle) {
		this.driver = driver;
		this.pageTitle = pageTitle;
	}

	public boolean isPageLoaded() {
		return (driver.getTitle().contains(pageTitle));
	}

	public void open() {
		driver.get(URL);
	}

	public String getTitle() {
		return pageTitle;
	}

	public String getURL() {
		return URL;
	}

	protected void sendText(String xpath, String text) {
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public boolean isTextPresentOnPage(String text) {
		return driver.getPageSource().contains(text);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementPresent(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementPresentAndDisplayed(By by){
		try{
			return driver.findElement(by).isDisplayed();
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static <T extends BasePage> T getInstance(WebDriver webDriver, String url, Class<T> type) {
		webDriver.get(url);
	    return getInstance(webDriver, type);
	}	

	public static <T extends BasePage> T getInstance(WebDriver webDriver, Class<T> type) {
	    return PageFactory.initElements(webDriver, type);
	}

}
