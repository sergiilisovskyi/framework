package com.lisovskyi.app.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.lisovskyi.app.config.DriverFactory;

public class ScreenshotListener extends TestListenerAdapter {

	DriverFactory driverFactory = null;
	
	private boolean createFile(File screenshot) {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());
			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				try {
					fileCreated = screenshot.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileCreated;
	}

	private void writeScreenshotToFile(WebDriver driver, File screenshot) {
		try {
			FileOutputStream fos = new FileOutputStream(screenshot);
			fos.write(((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.BYTES));
			fos.close();
		} catch (IOException e) {
			System.err.println("Unable to write "
					+ screenshot.getAbsolutePath());
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult failingTest) {
		
		try {
			WebDriver driver = driverFactory.getDriver();
			String screenshotDirectory = System.getProperty(
					"screenshotDirectory", "target/screenshots");
			String screenshotAbsolutePath = screenshotDirectory
					+ File.separator + System.currentTimeMillis() + "_"
					+ failingTest.getName();
			File screenshot = new File(screenshotAbsolutePath);
			if (createFile(screenshot)) {
				try {
					writeScreenshotToFile(driver, screenshot);
				} catch (ClassCastException e) {
					writeScreenshotToFile(new Augmenter().augment(driver),
							screenshot);
				}

				System.out.println("Written screeshot to "
						+ screenshotAbsolutePath);
			} else {
				System.err
						.println("Unable to create " + screenshotAbsolutePath);
			}

		} catch (Exception e) {
			System.err.println("Unable to capture screeshot...");
			e.printStackTrace();

		}
	}

}
