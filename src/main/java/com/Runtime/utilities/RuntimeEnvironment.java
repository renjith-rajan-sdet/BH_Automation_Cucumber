package com.Runtime.utilities;

import com.Base.Utilities.*;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Scenario;

public class RuntimeEnvironment {

	public WebDriver driver;
	public Scenario scn;
	public PageObjectManager pages;
	public String browser;
	public String Environment;
	public Configurations configurations;


	public RuntimeEnvironment(Scenario scn, String browser, String Environment) {
		// The class is used to package all the needed details on the run time
		// environment

		this.driver = Browser.InitialiseWebDriver(browser);
		this.scn = scn;

		// Set the attributes
		this.browser = browser;
		this.Environment = Environment;
		this.configurations = new Configurations(driver, Environment);
	}

	// Environment and browser defaulted in this case (QA and Chrome)
	public RuntimeEnvironment(Scenario scn) {
		// The class is used to package all the needed details on the run time
		// environment

		this.driver = Browser.InitialiseWebDriver(Browser.getBrowser());
		this.scn = scn;

		// Set the attributes

		this.browser = Browser.getBrowser();
		this.Environment = Browser.getEnvironment();
		this.configurations = new Configurations(driver, Environment);
	}


	public PageObjectManager getPage(RuntimeEnvironment runtime) {
		pages = new PageObjectManager(runtime);
		return pages;
	}

}
