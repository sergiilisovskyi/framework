package com.lisovskyi.app.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public enum DriverType implements DriverSetup {

	FIREFOX {
		public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new MarionetteDriver(capabilities);
		}
	},
	CHROME {
		public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches",
					Arrays.asList("--no-default-browser-check"));
			HashMap<String, String> chromePreferences = new HashMap<String, String>();
			chromePreferences.put("profile.password_manager_enabled", "false");
			capabilities.setCapability("chrome.prefs", chromePreferences);
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new ChromeDriver(capabilities);
		}
	},
	IE {
		public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
					true);
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability("requireWindowFocus", true);
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new InternetExplorerDriver(capabilities);
		}
	},
	SAFARI {
		public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("safari.cleanSession", true);
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new SafariDriver(capabilities);
		}
	},
	OPERA {
		public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new OperaDriver(capabilities);
		}

	};

	protected DesiredCapabilities addProxySettings(
			DesiredCapabilities capabilities, Proxy proxySettings) {
		if (null != proxySettings) {
			capabilities.setCapability(CapabilityType.PROXY, proxySettings);
		}

		return capabilities;
	}

	protected List<String> applyPhantomJSProxySettings(
			List<String> cliArguments, Proxy proxySettings) {
		if (null == proxySettings) {
			cliArguments.add("--proxy-type=none");
		} else {
			cliArguments.add("--proxy-type=http");
			cliArguments.add("--proxy=" + proxySettings.getHttpProxy());
		}
		return cliArguments;
	}
}
