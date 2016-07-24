import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import com.lisovskyi.app.pages.BasePage;


public class TestBase{

	
	 @AfterMethod
	    public static void clearCookies() {
	    	getWebDriver().manage().deleteAllCookies();
	    }     

		@AfterSuite(alwaysRun = true)
		public static void quitWebDriver() {
			for (WebDriver webDriver : webDriverPool) {
				webDriver.quit();
			}
		}   
	    
	    public static WebDriver getWebDriver() {
	        return WebDriver.get();
	    }   
	
}
