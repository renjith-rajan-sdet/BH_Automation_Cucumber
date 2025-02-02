package com.Base.Utilities;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.Duration;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.Runtime.utilities.RuntimeEnvironment;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.junit.Assert;
import org.openqa.selenium.*;
import io.cucumber.java.Scenario;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralUtilities {
    RuntimeEnvironment runtime;
    Scenario scn;

    public GeneralUtilities(RuntimeEnvironment runtime) {
        this.runtime = runtime;
        this.scn = runtime.scn;
    }

    /******************************************************************
     * Description : To take a screenshot
     * Arguments : WebDriver instance
     * Return Value : byte[]
     ********************************************************************/

    private byte[] takeScreenSnap(WebDriver driver) {

        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);

    }


    /******************************************************************
     * Description : To take a screenshot after full scroll
     * Arguments : WebDriver instance
     * Return Value : byte[]
     ********************************************************************/

    private byte[] takeScreenSnapFullScroll(WebDriver driver) throws javax.imageio.IIOException {
        BufferedImage screenshot;
        screenshot = Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenshot, "png", outputStream);
        } catch (IOException e) {
            Browser.getLogger().info("IO exception while taking screenshot");
        } catch (WebDriverException e) {
            Browser.getLogger().info("WebDriver exception while taking screenshot");
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            Assert.fail("Exception while taking screenshot");
        }
             return outputStream.toByteArray();
    }

    /******************************************************************
     * Description : To take a screenshot
     * Arguments : String image description, Scenario instance, WebDriver instance
     * Return Value : NA
     ********************************************************************/

    public void takeScreenSnap(String imgDescription, Scenario scn, WebDriver driver) {
        try {
            scn.attach(takeScreenSnap(driver), "image/png",
                    imgDescription + "___" + GeneralUtilities.generateTimeStamp());
        } catch (JavascriptException e) {
           fail("Unable to take screenshot. Webpage not loaded...!");
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Unable to take screenshot. Webpage not loaded...!");
        }
    }

    /******************************************************************
     * Description : To take a screenshot
     * Arguments : String image description, Scenario instance, WebDriver instance
     * Return Value : NA
     ********************************************************************/

    public void takeScreenSnap_FullScroll(String imgDescription, Scenario scn, WebDriver driver) {
        try {
            scn.attach(takeScreenSnapFullScroll(driver), "image/png",
                    imgDescription + "___" + GeneralUtilities.generateTimeStamp());
        } catch (JavascriptException e) {
            fail("Unable to take screenshot. Webpage not loaded...!");
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Unable to take screenshot. Webpage not loaded...!");
        }
    }


    /******************************************************************
     * Description : To wait/halt the execution for the seconds given
     * Arguments : Seconds in Integer
     * Return Value : NA
     ********************************************************************/

    public void waitForSeconds(int secondsWait) {
        secondsWait = secondsWait * 1000;
        try {
            Thread.sleep(secondsWait);
        } catch (InterruptedException e) {
            Browser.getLogger().info("Interrupted Exception");
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
        }
    }

    /******************************************************************
     * Description : To wait/halt the execution until Page loaded completely
     * Arguments : WebDriver instance
     * Return Value : NA
     ********************************************************************/

    public void waitUntilPageLoads(WebDriver driver,int intSeconds) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String siteReady = (String) js.executeScript("return document.readyState;");
            int iterator = 0;
            while (!siteReady.equalsIgnoreCase("COMPLETE") && iterator < 50) {
                waitForSeconds(intSeconds);
                Browser.getLogger().info("WEB PAGE NOT READY - Wait.... ");
                siteReady = (String) js.executeScript("return document.readyState;");
                iterator++;
            }
        } catch (TimeoutException e) {
            fail("Page load failure.");
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }

    /******************************************************************************
     * Description : To verify the object in Web page
     * Arguments : WebElement, WebDriver instance
     * Return Value : boolean
     ********************************************************************************/

    public boolean verifyObjectIsDisplayed(WebElement wElement, WebDriver driver) {
        try {
            if (wElement.isDisplayed()) {
                waitForSeconds(3);
                Browser.getLogger().info("Element is displayed : " + wElement);
                return true;
            } else {
                    scrollToViewElement(driver, wElement,"");
                    Browser.getLogger().info("Element found but hidden...!   : " + wElement);
                    waitForSeconds(3);
                    try {
                        if (wElement.isDisplayed()) {
                            waitForSeconds(3);
                            return true;
                        }
                    } catch (NoSuchElementException e) {
                        Browser.getLogger().error("Element not found(No Such Element)");
                }
                return false;
            }
        }
        catch (WebDriverException e) {
            Browser.getLogger().info("Element not found(WebDriver Exception)");
            return false;
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return false;
        }
    }

    /******************************************************************************
     * Description : To click on element by Actions Class and WebElement
     * Arguments : WebElement, WebDriver instance, elementInfo
     * Return Value : NA
     ********************************************************************************/

    private void actionsClick(WebElement wElement, WebDriver driver, String ElementInfo)
           {
        try {
            waitForSeconds(5);
            Actions action = new Actions(driver);
            action.moveToElement(wElement).click().build().perform();
        } catch (NoSuchElementException e) {
            Browser.getLogger().info("Element to click not found..! :  " + ElementInfo);
                fail("Element to click not found..! :  " + ElementInfo);
        } catch (WebDriverException e) {
            fail("Unable to click the element(Webdriver exception) :  " + ElementInfo);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Unable to click the element(Generic exception) :  " + ElementInfo);

        }
    }
    /******************************************************************************
     * Description : To click on element by Java Script Executor and WebElement
     * Arguments : WebDriver instance,WebElement, elementInfo
     * Return Value : NA
     ********************************************************************************/

    public void clickElementJScript(WebDriver driver, WebElement myElement, String ElementInfo) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoView()", myElement);
            js.executeScript("arguments[0].click();", myElement);
            Browser.getLogger().info("Clicked using jScript : " + ElementInfo);
        } catch (NoSuchElementException e) {
                fail("Element to click doesn't exist : " + ElementInfo);

        } catch (ElementClickInterceptedException e) {
            Browser.getLogger().info("Element hidden. Unable to click " + ElementInfo);
            fail("Element to click - hidden");
        } catch (ElementNotInteractableException e) {
            Browser.getLogger().info("Element hidden. " + ElementInfo);
            fail("Element to click - hidden");
        } catch (InvalidElementStateException e) {
            Browser.getLogger().info("Element hidden. Unable to click " + ElementInfo);
            fail("Element to click - hidden");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + ElementInfo);
            WebElement tempElement = reinitialiseWebElement(myElement, driver);
            if (verifyObjectIsDisplayed(tempElement, driver)) {
                clickElementJScript(driver, tempElement, ElementInfo);
            }
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }

    /******************************************************************************
     * Description : To click on element by WebElement given
     * Arguments : WebDriver instance,WebElement, elementInfo
     * Return Value : NA
     ********************************************************************************/

    public void clickElement(WebDriver driver, WebElement wElement, String ElementInfo) {
        try {
            wElement.click();
            Browser.getLogger().info("Clicked the element : " + ElementInfo);

        } catch (NoSuchElementException e) {
                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
                fail("The element to click doesn't exist : " + ElementInfo);
            }
        catch (ElementClickInterceptedException e) {
            Browser.getLogger().info("Element hidden. Scrolling...!" + ElementInfo);
            scrollToViewElement(driver, wElement,ElementInfo);
            if (verifyObjectIsDisplayed(wElement, driver)) {
                waitForSeconds(5);
                try {
                    wElement.click();
                } catch (ElementClickInterceptedException e1) {

                    actionsClick(wElement, driver, ElementInfo);
                }
            }
            else {
                actionsClick(wElement, driver, ElementInfo);
            }
        } catch (ElementNotInteractableException e) {
            Browser.getLogger()
                    .info("Element not intractable. Unable to click, trying to Click using JS " + ElementInfo);
            clickElementJScript(driver, wElement, ElementInfo);
        } catch (InvalidElementStateException e) {
            Browser.getLogger().info("Element click failed...!" + ElementInfo);
            fail("Element click failed : " + ElementInfo);
        } catch (WebDriverException e) {
            Browser.getLogger().info("Element is not found. click failed..!" + " : " + ElementInfo);
            fail("Element click failed (WebDriverException): " + ElementInfo);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }

    /******************************************************************************
     * Description : To scroll into view for the WebElement given
     * Arguments : WebDriver instance, WebElement, elementInfo
     * Return Value : WebElement
     ********************************************************************************/

    public WebElement scrollToViewElement(WebDriver driver, WebElement wElement, String ElementInfo) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", wElement);
        Browser.getLogger().info("Scrolling to view the element : " + ElementInfo);
        return wElement;
    }

    /******************************************************************************
     * Description : To verify Webpage URL
     * Arguments : WebDriver instance, String text to Verify
     * Return Value : boolean
     ********************************************************************************/

    public boolean verifyWebPageURL(WebDriver driver, String textToVerify) {
        try {
            if (driver.getCurrentUrl().trim().contains(textToVerify.trim()) && !textToVerify.isEmpty()) {
                Browser.getLogger().info("WebPage URL comparison successful for : " + driver.getCurrentUrl());
                return true;
            } else {
                Browser.getLogger().info("Text comparison failed for the WebPage" + " Expected :" + textToVerify
                        + " Actual :" + driver.getCurrentUrl());
                return false;
            }
        } catch (NullPointerException e) {
            fail("Driver is null");
            return false;
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return false;
        }
    }

    /******************************************************************************
     * Description : To set text in the input WebElement given
     * Arguments : WebElement, String value, WebDriver instance, elementInfo
     * Return Value : NA
     ********************************************************************************/

    public void setText(WebElement wElement, String valueToSet, WebDriver driver, String ElementInfo) {
        try {
            wElement.sendKeys(valueToSet);
            Browser.getLogger().info("Entered Text : " + valueToSet + " to element: " + ElementInfo);
        } catch (NoSuchElementException e) {
            Browser.getLogger().error("The element to set text doesn't exist : " + ElementInfo);
            fail("The element to set text doesn't exist : " + ElementInfo);

        } catch (WebDriverException e) {
            Browser.getLogger().info("Web Element not displayed(WebDriverException) :" + ElementInfo);
            fail("The element to set text doesn't exist  : " + ElementInfo);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }
    /******************************************************************************
     * Description : To do any keyboard button press
     * Arguments : String xpath, Web Element el, String Key to Press, WebDriver instance, elementInfo
     * Return Value : NA
     ********************************************************************************/

    public void pressKey(WebElement el,String KeyPress, WebDriver driver, String ElementInfo) {
        try {
            switch (KeyPress) {
                case "ENTER":
                    el.sendKeys(Keys.ENTER);
                    Browser.getLogger().info("Pressed ENTER against the element : " + ElementInfo);
                    break;
                case "TAB":
                    el.sendKeys(Keys.TAB);
                    Browser.getLogger().info("Pressed TAB against the element : " + ElementInfo);
                    break;
                case "DOWN ARROW":
                    el.sendKeys(Keys.ARROW_DOWN);
                    Browser.getLogger().info("Pressed DOWN ARROW against the element : " + ElementInfo);
                    break;
                default:
                    Browser.getLogger().info("Key not defined");
                    fail("Key not defined" + KeyPress);
            }
        } catch (NoSuchElementException e) {
            Browser.getLogger().info("The element to press key doesn't exist : " + ElementInfo);
            fail("The element to press key doesn't exist : " + ElementInfo);
        } catch (WebDriverException e) {
            Browser.getLogger().info("Web Element not displayed (WebDriverException):" + ElementInfo);
            fail("The element to press key doesn't exist (WebDriverException): " + ElementInfo);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }
    /******************************************************************************
     * Description : To attach any text required in the report.
     * Arguments : String header, String logMessage, Scenario instance
     * Return Value : NA
     ********************************************************************************/

    public static void recordTextDataEnd(String Header, String logStringMsg, Scenario scn) {
        try {
            logStringMsg = Header + " :  " + logStringMsg;
            scn.attach(logStringMsg.getBytes(), "text/plain", Header);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
        }
    }
//
    /******************************************************************************
     * Description : To attach any text required in the report.
     * Arguments : String header, String logMessage, Scenario instance
     * Return Value : NA
     ********************************************************************************/

    public void recordTextData(String Header, String logStringMsg, Scenario scn) {
        try {
            logStringMsg = Header + " :  " + logStringMsg;
            scn.attach(logStringMsg.getBytes(), "text/plain", Header);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
    }


    /******************************************************************************
     * Description : To re-initialize web element
     * Arguments : WebElement, WebDriver Instance
     * Return Value : WebElement
     ********************************************************************************/

    public WebElement reinitialiseWebElement(WebElement webEl, WebDriver webDriver) {
        String elementInfo = webEl.toString();
        Browser.getLogger().info("Element reinitialise : " + elementInfo);
        try {
            elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
        } catch (ArrayIndexOutOfBoundsException e) {
            return reinitialiseWebElementWithoutRef(webEl, webDriver);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return reinitialiseWebElementWithoutRef(webEl, webDriver);
        }
        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
        Browser.getLogger().info(elementInfo);
        WebElement retWebEl = null;
        if (elementInfo.contains("-> link text:")) {
            retWebEl = webDriver.findElement(By.linkText(elementLocator));
        } else if (elementInfo.contains("-> name:")) {
            retWebEl = webDriver.findElement(By.name(elementLocator));
        } else if (elementInfo.contains("-> id:")) {
            retWebEl = webDriver.findElement(By.id(elementLocator));
        } else if (elementInfo.contains("-> xpath:")) {
            retWebEl = webDriver.findElement(By.xpath(elementLocator));
        } else if (elementInfo.contains("-> class name:")) {
            retWebEl = webDriver.findElement(By.className(elementLocator));
        } else if (elementInfo.contains("-> css selector:")) {
            retWebEl = webDriver.findElement(By.cssSelector(elementLocator));
        } else if (elementInfo.contains("-> partial link text:")) {
            retWebEl = webDriver.findElement(By.partialLinkText(elementLocator));
        } else if (elementInfo.contains("-> tag name:")) {
            retWebEl = webDriver.findElement(By.tagName(elementLocator));
        } else {
            Browser.getLogger().info("No valid locator found. Could not refresh the stale element : " + webEl);
            fail("No valid locator found. Could not refresh the stale element : " + webEl);
        }
        return retWebEl;
    }
//
    /******************************************************************************
//     * Description : To re-initialize web element without reference
//     * Arguments : WebElement, WebDriver Instance
//     * Return Value : WebElement
//     ********************************************************************************/
//
    private WebElement reinitialiseWebElementWithoutRef(WebElement webEl, WebDriver webDriver) {
        String elementInfo = webEl.toString();
        Browser.getLogger().info("Element reinitialise : " + elementInfo);
        elementInfo = elementInfo.substring(elementInfo.indexOf("By."));
        Browser.getLogger().info("elementInfo : " + elementInfo);
        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
        WebElement retWebEl = null;
        if (elementInfo.contains("link text:")) {
            retWebEl = webDriver.findElement(By.linkText(elementLocator));
        } else if (elementInfo.contains("name:")) {
            retWebEl = webDriver.findElement(By.name(elementLocator));
        } else if (elementInfo.contains("id:")) {
            retWebEl = webDriver.findElement(By.id(elementLocator));
        } else if (elementInfo.contains("xpath:")) {
            retWebEl = webDriver.findElement(By.xpath(elementLocator));
        } else if (elementInfo.contains("class name:")) {
            retWebEl = webDriver.findElement(By.className(elementLocator));
        } else if (elementInfo.contains("css selector:")) {
            retWebEl = webDriver.findElement(By.cssSelector(elementLocator));
        } else if (elementInfo.contains("partial link text:")) {
            retWebEl = webDriver.findElement(By.partialLinkText(elementLocator));
        } else if (elementInfo.contains("tag name:")) {
            retWebEl = webDriver.findElement(By.tagName(elementLocator));
        } else {
            Browser.getLogger().info("No valid locator found. Could not refresh the stale element : " + webEl);
            fail("No valid locator found. Could not refresh the stale element : " + webEl);
        }
        return retWebEl;
    }

    /******************************************************************************
     * Description : To generate time stamp
     * Arguments : NA
     * Return Value : String-12 digit number(format yy/mm/dd/hour:minutes:seconds)
     ********************************************************************************/

    public static String generateTimeStamp() {
        return (new SimpleDateFormat("yy/MM/dd/HH:mm:ss").format(new java.util.Date())); // 12 digits
    }

    /******************************************************************************
     * Description : To wait until the object loads
     * Arguments : WebDriver instance, WebElement, int wait, String elementInfo
     * Return Value : boolean
     ********************************************************************************/

    public boolean waitUntilObjectLoads(WebDriver driver, WebElement wElement, int waitTime, String elementInfo) {
        for (int i = 0; i < waitTime; i++) {
            if (waitUntilObjectLoadsSub(driver, wElement, waitTime, elementInfo)) {
                return true;
            }
            waitForSeconds(2);
        }
        return false;
    }

    /******************************************************************************
     * Description : To wait until the object loads
     * Arguments : WebDriver instance, WebElement , int wait, String elementInfo
     * Return Value : boolean
     ********************************************************************************/

    private boolean waitUntilObjectLoadsSub(WebDriver driver, WebElement wElement, int waitTime, String elementInfo) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        try {
            wait.until(ExpectedConditions.visibilityOf(wElement));
            if (wElement.isDisplayed()) {
                Browser.getLogger().info("Element found :" + elementInfo);
                return true;
            } else {
                Browser.getLogger().info("Element found but hidden. Scrolling....! :" + elementInfo);
                scrollToViewElement(driver, wElement,"");
            }
            return true;
        } catch (TimeoutException e) {
            Browser.getLogger().info("Element not found (Timeout):" + elementInfo);
            return false;
        } catch (NoSuchElementException e) {
            Browser.getLogger().info("Element not found (No Such Element):" + elementInfo);
            return false;
        } catch (StaleElementReferenceException d) {
            return false;
        } catch (WebDriverException f) {
            Browser.getLogger()
                    .info("Element not found :" + elementInfo);
            return false;
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return false;
        }
    }
}