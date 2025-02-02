package com.Base.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

public class Configurations {

	WebDriver driver;
	Properties testProperties = null;
	Properties GlobalTestProperties = null;
	String propertyFile;
	String Environment;
	
	public static final String ConfigPath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "Configuration" + File.separator;
	
	public Configurations(WebDriver driver, String Environment) {
		this.driver = driver;
		this.Environment = Environment;
	}
	public Configurations(WebDriver driver) {
		this.driver = driver;
		this.Environment = Browser.getEnvironment();
	}

	public Properties loadProperties()
	{
		if(testProperties!= null)
		{
			return testProperties;
		}
		else
		{
			testProperties = new Properties();
		}
		propertyFile = ConfigPath + Environment + ".properties";
		
		try {
			FileInputStream testPropertiesFile = new FileInputStream(new File(propertyFile));		
			testProperties.load(new InputStreamReader(testPropertiesFile, StandardCharsets.UTF_8));
			testPropertiesFile.close();
		} catch (FileNotFoundException | NullPointerException e) {
			throw new RuntimeException("Check if file exists " + propertyFile);
		} catch (IOException e) {
			throw new RuntimeException("Error reading property file " + propertyFile);
		}

		return testProperties;
	}
	
	public Properties loadGlobalData()
	{
		
		propertyFile = ConfigPath + "GlobalData.properties";

		if(GlobalTestProperties!= null)
		{
			return GlobalTestProperties;
		}
		else
		{
			GlobalTestProperties = new Properties();
		}
		try {
			FileInputStream testPropertiesFile = new FileInputStream(propertyFile);		
			GlobalTestProperties.load(new InputStreamReader(testPropertiesFile, Charset.forName("UTF-8")));
			testPropertiesFile.close();
		} catch (FileNotFoundException | NullPointerException e) {
			throw new RuntimeException("Check if file exists " + propertyFile);
		} catch (IOException e) {
			throw new RuntimeException("Error reading property file " + propertyFile);
		}

		return GlobalTestProperties;
	}
	
	public String globalGetData(String KeyText)
	{
		return loadGlobalData().getProperty(KeyText);
	}
	
	public String envGetData(String KeyText)
	{
		Browser.getLogger().info("Retrieving value for : " + KeyText
		+ " for the environment : " + Environment);
		try {
			return loadProperties().getProperty(KeyText);
		} catch (NullPointerException e) {
			Assertions.fail("Could not retrieve field :" + KeyText + ". The field not found.!");
		}
		return null;
	}
}
