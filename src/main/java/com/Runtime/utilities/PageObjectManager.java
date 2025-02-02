package com.Runtime.utilities;


import test.pages.*;

public class PageObjectManager {

	private final RuntimeEnvironment runtime;
	public PageObjectManager(RuntimeEnvironment runtime) {
		this.runtime = runtime;
	}
	HomePage homePage;

	public HomePage getHomePage()
	{
		if(homePage==null) {homePage = new HomePage(runtime);return homePage;}return homePage;
	}
	SearchBrightHorisons_Screen searchBrightHorisons_Screen;
	public SearchBrightHorisons_Screen get_SearchBrightHorisons_Screen()
	{
		if(searchBrightHorisons_Screen==null) {searchBrightHorisons_Screen = new SearchBrightHorisons_Screen(runtime);
			return searchBrightHorisons_Screen;}return searchBrightHorisons_Screen;
	}

	FindACenter_Page findACenter_Page;
	public FindACenter_Page get_FindACenter_Page()
	{
		if(findACenter_Page==null) {findACenter_Page = new FindACenter_Page(runtime);return findACenter_Page;}return findACenter_Page;
	}



}
