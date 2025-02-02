package StepDefinitions;

import static org.junit.jupiter.api.Assertions.fail;
import java.awt.AWTException;
import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import com.Base.Utilities.Browser;
import com.Base.Utilities.GeneralUtilities;
import com.Runtime.utilities.BaseSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Hooks extends BaseSteps{
	private final BaseSteps baseSteps;
	public Hooks(BaseSteps baseSteps) {
		this.baseSteps = baseSteps;
	}
	
	@Given("Web Application launched - Application - {string} : with Browser : {string}, Environment : {string}")
	public void webApplicationLaunch(String ApplicationName, String browser,
			String Environment) {

		baseSteps.initialiseRuntime(browser, Environment);
		baseSteps.runtime.driver.get(baseSteps.runtime.configurations.envGetData(ApplicationName + "_URL"));
		baseSteps.generalUtilities.waitUntilPageLoads(baseSteps.runtime.driver,5);
	}

	@Given("Web Application launched - Application - {string}")
	public void webApplicationLaunch(String ApplicationName) {

		baseSteps.initialiseRuntime();
		baseSteps.runtime.driver.get(baseSteps.runtime.configurations.envGetData(ApplicationName + "_URL"));
	}
	@Before()
	public void setup(Scenario sh) {
		baseSteps.scn = sh;
		String timeStamp = GeneralUtilities.generateTimeStamp();
		GeneralUtilities.recordTextDataEnd("Test Execution Began", timeStamp,baseSteps.scn);
	}
	@After()
	public void closeBrowser() throws IOException, AWTException {

		JavascriptExecutor jse = null;
		try {
			jse = (JavascriptExecutor)baseSteps.runtime.driver;
		} catch (NullPointerException e2) {
			Browser.getLogger().info("runtime/driver is null");
		}
		
		if(baseSteps.runtime == null)
		{
			Browser.getLogger().info("runtime is null");
			return;
		}
		if (baseSteps.scn.isFailed()) {
			baseSteps.generalUtilities.takeScreenSnap("Failed", baseSteps.scn, baseSteps.runtime.driver);
			String timeStamp = GeneralUtilities.generateTimeStamp();
			GeneralUtilities.recordTextDataEnd("Test Execution Ended", timeStamp,baseSteps.scn);
		}
		else if(baseSteps.scn.getStatus().toString().equals("PASSED"))
		{
			//baseSteps.mobileReusableFunctions.takeScreenSnap("Passed", baseSteps.scn,  baseSteps.runtime.driver);//for mobile app testing
			baseSteps.generalUtilities.takeScreenSnap("Passed", baseSteps.scn,  baseSteps.runtime.driver);
			String timeStamp = GeneralUtilities.generateTimeStamp();
			GeneralUtilities.recordTextDataEnd("Test Execution Ended", timeStamp,baseSteps.scn);
		}
		else
		{
			baseSteps.generalUtilities.takeScreenSnap("Broken", baseSteps.scn, baseSteps.runtime.driver);
			String timeStamp = GeneralUtilities.generateTimeStamp();
			GeneralUtilities.recordTextDataEnd("Test Execution Ended", timeStamp,baseSteps.scn);
		}

		try {
			//baseSteps.runtime.driver.close();
			baseSteps.runtime.driver.quit();
		} catch (NullPointerException e) {
			System.out.println("driver is null.....!");
		}

	}
	
	@Then("close the browser")
	public void resetDriver()
	{
		try {
			closeBrowser();
		} catch (IOException | AWTException e) {
			e.printStackTrace();
			fail("Could not close the browser..!");
		}
		baseSteps.generalUtilities.waitForSeconds(10);
	}



	private RemoteWebDriver remoteWebDriver;


}