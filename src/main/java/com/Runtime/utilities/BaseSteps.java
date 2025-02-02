package com.Runtime.utilities;

import com.Base.Utilities.GeneralUtilities;
import io.cucumber.java.Scenario;

public class BaseSteps {

	public Scenario scn;
	public PageObjectManager pages;
	public RuntimeEnvironment runtime;
	public GeneralUtilities generalUtilities;

	public void initialiseRuntime(String browser,
								   String Environment) {

		runtime = new RuntimeEnvironment(scn, browser, Environment);
		this.pages = runtime.getPage(runtime);
		this.generalUtilities = new GeneralUtilities(runtime);

	}

	public void initialiseRuntime() {

		runtime = new RuntimeEnvironment(scn);
		this.pages = runtime.getPage(runtime);
		this.generalUtilities = new GeneralUtilities(runtime);

	}

	public RuntimeEnvironment getRuntime() {
		return runtime;
	}
	public Scenario getScenario() {
		return scn;
	}

	public PageObjectManager getPages() {
		return pages;
	}


	public GeneralUtilities getGeneralReusableFunction()
	{
		return generalUtilities;
	}

}
