package com.Base.Utilities;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Browser {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger();

	/** The Constant targetBrowser. */
	public static String targetBrowser = System.getProperty("browser");


	private static String envNameFromSystemProperty = System.getProperty("env");
	private static String headlessMode = System.getProperty("headless");

	/**
	 * return the logger
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	public static String getEnvironment() {
		if ((envNameFromSystemProperty == null) || (envNameFromSystemProperty.isEmpty())) {
			Browser.getLogger().info("Environment not specified...! Defaulting to QA");
			envNameFromSystemProperty = "QA";
		}
		return envNameFromSystemProperty;
	}

	public static String getBrowser() {
		if ((targetBrowser == null) || (targetBrowser.isEmpty())) {
			Browser.getLogger().info("Browser not specified...! Defaulting to 'Chrome'");
			targetBrowser = "Chrome";
		}
		return targetBrowser;
	}

	public static String getHeadlessMode() {
		if ((headlessMode == null) || (headlessMode.isEmpty())) {
			Browser.getLogger().info("Headless not specified...! Defaulting to 'False'");
			headlessMode = "false";
		}
		return headlessMode;
	}

	public static WebDriver InitialiseWebDriver(String browser) {
		WebDriver driver = null;

		switch(browser.trim().toUpperCase()) {
			case "CHROME":
				Browser.getLogger().info("Creating chrome driver..!");
				driver = newChromeDriver();
				break;
			case "EDGE":
				Browser.getLogger().info("Creating Edge driver..!");
				driver = newEdgeDriver();
				break;
			case "FIREFOX":
				Browser.getLogger().info("Creating Firefox driver..!");
				driver = newFirefoxDriver();
				break;
			default:
				fail("Browser not defined : " + browser);
				break;
		}

		if (driver == null) {
			Browser.getLogger().info("Driver is null..!");
			fail("Driver is null..! Browser : " + browser);
		}
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.page_Load_Timeout));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.default_Timeout));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Constants.script_Load_Timeout));
		return driver;

	}

	private static WebDriver newFirefoxDriver() {
		FirefoxOptions options = new FirefoxOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);

		options.addArguments("-private");
		options.addArguments("--start-maximized");
		options.addArguments("--deny-permission-prompts");
		if (getHeadlessMode().equalsIgnoreCase("True")) {
			getLogger().info("Firefox running in headless mode....!");
			options.addArguments("--headless");
		}
		return new FirefoxDriver(options);
	}

	private static WebDriver newEdgeDriver() {
		EdgeOptions options = new EdgeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);

		if (getHeadlessMode().equalsIgnoreCase("True")) {
			getLogger().info("Edge running in headless mode....!");
			options.addArguments("--headless");
		}
		options.addArguments("--no-sandbox");
		options.addArguments("--incognito");
		options.addArguments("disable-extensions");
		options.addArguments("start-maximized");
		options.addArguments("disable-cache");
		options.addArguments("disable-application-cache");
		options.addArguments("disable-offline-load-stale-cache");
		options.addArguments("disk-cache-size=0");
		options.addArguments("disable-infobars");
		options.addArguments("disable-browser-side-navigation");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--ignore-ssl-errors=yes"); // Added to handle insecure content
		options.addArguments("--ignore-certificate-errors");// Added to handle insecure content
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-backgrounding-occluded-windows");// To handle multiple windows
		options.addArguments("--deny-permission-prompts");

		return new EdgeDriver(options);
	}

	private static WebDriver newChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("profile.default_content_settings.cookies", 2);

		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--no-sandbox");
		options.addArguments("--incognito");
		options.addArguments("disable-extensions");
		options.addArguments("start-maximized");
		options.addArguments("disable-cache");
		options.addArguments("disable-application-cache");
		options.addArguments("disable-offline-load-stale-cache");
		options.addArguments("disk-cache-size=0");
		options.addArguments("disable-infobars");
		options.addArguments("disable-browser-side-navigation");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--ignore-ssl-errors=yes"); // Added to handle insecure content
		options.addArguments("--ignore-certificate-errors");// Added to handle insecure content
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-backgrounding-occluded-windows");// To handle multiple windows
		options.addArguments("--deny-permission-prompts");


		if (getHeadlessMode().equalsIgnoreCase("true")) {
			options.addArguments("--headless");
			getLogger().info("Chrome is running on headless mode..!");
		}
		return new ChromeDriver(options);
	}
}
