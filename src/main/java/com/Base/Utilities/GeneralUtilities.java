package com.Base.Utilities;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.Runtime.utilities.RuntimeEnvironment;
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.json.JSONObject;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.junit.Assert;
import org.openqa.selenium.*;
//import com.assertthat.selenium_shutterbug.core.Capture;
//import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.cucumber.java.Scenario;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
//    /******************************************************************
//     * Description : To take full window snippet
//     * Arguments : description, scenario
//     * Return Value : NA
//     * Challenge: During parallel execution, the snippet may not be correct as it captures the current window snippet.
//     *            Will not work if the user interacts with the system during run time
//     *            Will not work for headless mode
//     *            Will not work in case browser is minimized
//     ********************************************************************/
//    public void takeScreenshot(String imgDescription, Scenario scn) throws AWTException, IOException {
//        try {
//            Robot robot = new Robot();
//            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(screenCapture, "png", baos);
//            scn.attach(baos.toByteArray(), "image/png", imgDescription + "___" + GeneralUtilities.generateTimeStamp());
//        } catch (AWTException | HeadlessException | IOException e) {
//            Assert.fail("Unable to take screenshot. Webpage not loaded...!");
//        }
//    }
    /******************************************************************
     * Description : To generate a random number
     * Arguments : NA
     * Return Value : String
     *******************************************************************/

    private String generateRandomNumber() {
        try {
            int min = 12;
            int max = 100000;
            int b = (int) (Math.random() * (max - min + 1) + min);
            return Integer.toString(b);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return "Error";
        }
    }

//    /******************************************************************
//     * Description : To generate a screenshot file from byte array
//     * Arguments : byte Array
//     * Return Value : String
//     ********************************************************************/
//    private String createScreenShotFromBiteArray(byte[] byteArrayData) {
//        String fileName = Browser.almAttachments + "ALM_" + generateRandomNumberFromTimeStamp() + generateRandomNumber()
//                + ".PNG";
//        try {
//            FileUtils.writeByteArrayToFile(new File(fileName), byteArrayData);
//        } catch (IOException e) {
//            runtime.almUpdater.updateTestStepResult("Unable to save the file..", TestStepResult.Failed);
//            fail("Unable to save the file..");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            runtime.almUpdater.updateTestStepResult("Generic Exception", TestStepResult.Failed);
//            fail("Generic Exception");
//        }
//        return fileName;
//    }
//
//    /******************************************************************
//     * Description : To create a text file from byte array
//     * Arguments : byte Array
//     * Return Value : String
//     ********************************************************************/
//
//    private String createTextFileFromBiteArray(byte[] byteArrayData) {
//        String fileName = Browser.almAttachments + "ALM_" + generateRandomNumberFromTimeStamp() + generateRandomNumber()
//                + ".txt";
//        try {
//            FileUtils.writeByteArrayToFile(new File(fileName), byteArrayData);
//        } catch (IOException e) {
//            runtime.almUpdater.updateTestStepResult("Unable to save the file..", TestStepResult.Failed);
//            fail("Unable to save the file..");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            runtime.almUpdater.updateTestStepResult("Generic Exception", TestStepResult.Failed);
//            fail("Generic Exception");
//        }
//        return fileName;
//    }

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

//    /******************************************************************
//     * Description : To take a screenshot after full scroll (Overloaded component)
//     * Arguments : String image description, Scenario instance, WebDriver instance
//     * Return Value : NA
//     ********************************************************************/
//
//    public void takeScreenSnapFullScroll(String imgDescription, Scenario scn, WebDriver driver) {
//        try {
//            scn.attach(takeScreenSnapFullScroll(driver), "image/png",
//                    imgDescription + "___" + GeneralUtilities.generateTimeStamp());
//        } catch (IIOException e) {
//            waitForSeconds(5);
//            takeScreenSnapFullScroll(imgDescription, scn, driver);
//            return;
//        } catch (JavascriptException e) {
//            fail("Unable to take screenshot. Webpage not loaded...!");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//        waitForSeconds(2);
//        scrollToTopOfPage(driver);
//        waitForSeconds(4);
//    }

//    /******************************************************************
//     * Description : To perform mouse over to any web element
//     * Arguments : WebElement, WebDriver instance
//     * Return Value : NA
//     ********************************************************************/
//
//    public void mouseOver(WebElement element, WebDriver driver) {
//        WebElement wE = verifyObjectExistence(element, driver);
//        try {
//            Actions action = new Actions(driver);
//            action.moveToElement(wE).perform();
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }

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
     * Description : To click the 1st element found in a page with matching xpath
     * Arguments : xpath in String, WebDriver instance, element info in String
     * Return Value : NA
     ********************************************************************************/

    public void clickFirstIntractableElement(String elementXpath, WebDriver driver, String ElementInfo) {
        List<WebElement> elements = driver.findElements(By.xpath(elementXpath));
        Browser.getLogger().info("No Of elements matching xpath :" + elements.size());
        for (WebElement element : elements) {
            try {
                if (element.isDisplayed()) {
                    element.click();
                    Browser.getLogger().info("Clicked on  : " + element);
                    return;
                }
            } catch (ElementNotInteractableException | StaleElementReferenceException e) {
                Browser.getLogger().info("Hidden element : " + element + " : Checking next..");
            } catch (Exception e) {
                Browser.getLogger().info("Generic Exception");
            }
        }
        fail("Unable to click element : " + ElementInfo);
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

//    /******************************************************************************
//     * Description : To verify the object in Web page and to fix xpath using AI
//     * Arguments : WebElement, WebDriver instance, String elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyObjectIsDisplayed(WebElement wElement, WebDriver driver, String ElementInfo) {
//        int noOfAttempts = 0;
//        try {
//            if (wElement.isDisplayed()) {
//                Browser.getLogger().info("Web Element is present & displayed on screen : " + ElementInfo);
//                String OuterHTML = wElement.getAttribute("outerHTML");
//            } else {
//                while (noOfAttempts < 3) {
//                    Browser.getLogger().info("Web Element is present but not displayed on screen : " + ElementInfo);
//                    scrollToViewWebElement(driver, ElementInfo);
//                    try {
//                        if (wElement.isDisplayed()) {
//                            Browser.getLogger().info("Web Element is present & displayed on screen : " + ElementInfo);
//                            return;
//                        }
//                    } catch (NoSuchElementException e) {
//                                            if (aiElement == null) {
//
//                            fail("Element Not found : " + ElementInfo);
//                        }
//                        isRecursiveCall = true;
//                        verifyObjectIsDisplayed(aiElement, driver, ElementInfo);
//                    }
//                    noOfAttempts++;
//                    waitForSeconds(4);
//                }
//                Browser.getLogger().fatal("Web Element not displayed : " + ElementInfo);
//                fail("Element Not found : " + ElementInfo);
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().fatal("Web Element not displayed : " + ElementInfo);
//                fail("Element Not found : " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            verifyObjectIsDisplayed(aiElement, driver, ElementInfo);
//        } catch (NullPointerException e) {
//            Browser.getLogger().fatal("Web Element not displayed(Null pointer exception) : " + ElementInfo);
//
//            fail("Element Not found : " + ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().fatal("Web Element not displayed(WebDriverException) : " + ElementInfo);
//            fail("Element Not found : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify the object is not displayed in Web page
//     * Arguments : WebElement, WebDriver instance, String elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyObjectIsNotDisplayed(WebElement wElement, WebDriver driver, String ElementInfo) {
//        int noOfAttempts = 0;
//        try {
//            if (wElement.isDisplayed()) {
//                Browser.getLogger().info("Web Element is present & displayed on screen : " + ElementInfo);
//                fail("WebElement is present : " + ElementInfo);
//            } else {
//                while (noOfAttempts < 3) {
//                    Browser.getLogger().info("Web Element is present but not displayed on screen : " + ElementInfo);
//                    scrollToViewWebElement(driver, wElement, ElementInfo);
//                    try {
//                        if (wElement.isDisplayed()) {
//                            fail("WebElement is present : " + ElementInfo);
//                        }
//                    } catch (NoSuchElementException e) {
//                        Browser.getLogger().info("Web Element is NOT present : " + ElementInfo);
//                        return;
//                    }
//                    noOfAttempts++;
//                }
//                Browser.getLogger().info("Web Element is NOT present : " + ElementInfo);
//            }
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Web Element is NOT present : " + ElementInfo);
//            return;
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed(WebDriverException) : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//    /******************************************************************************
//     * Description : To verify the object is enabled in Web page
//     * Arguments : WebElement, WebDriver instance, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyObjectIsEnabled(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            if (wElement.isEnabled()) {
//                Browser.getLogger().info("Web Element is present & displayed on screen : " + ElementInfo);
//                String OuterHTML = wElement.getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyObjectIsEnabled(aiElement, driver, ElementInfo);
//        } catch (NullPointerException e) {
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To scroll into Web Element view
//     * Arguments : WebDriver instance, WebElement , elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void scrollToViewWebElement(WebDriver driver, WebElement wElement, String elementInfo) {
//        try {
//            WebElement wE = verifyObjectExistence(wElement, driver);
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].scrollIntoView();", wE);
//            Browser.getLogger().info("Scrolled to web element : " + elementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Element not found : " + elementInfo);
//            fail("Element not found : " + elementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To scroll down by desired pixel
//     * Arguments : WebDriver instance, Pixel in Integer
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void scrollWindowByPixel(WebDriver driver, int pixel) {
//        try {
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("window.scrollBy(0," + pixel + ");");
//            Browser.getLogger().info("Scrolled by pixel : " + pixel);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify web page title
//     * Arguments : WebDriver instance, String text
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyWebPageTitle(WebDriver driver, String textToVerify) {
//        try {
//            if (driver.getTitle().contains(textToVerify)) {
//                Browser.getLogger().info("The Page is loaded with title: " + driver.getTitle());
//                return true;
//            } else {
//                Browser.getLogger().info(
//                        "WebPage Title Mismatch: " + " Expected :" + textToVerify + " Actual :" + driver.getTitle());
//                return false;
//            }
//        } catch (NullPointerException e) {
//            fail("Cannot get page title (Null Pointer Exception)");
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To reset implicit wait time
//     * Arguments : WebDriver instance, time in Seconds
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void resetImplicitWaitTime(WebDriver driver, int i) {
//        try {
//            driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify hyperlink in a web page
//     * Arguments : WebElement , WebDriver instance , text to verify , elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyHyperLink(WebElement wElement, WebDriver driver, String textToVerify, String ElementInfo) {
//        int noOfAttempts = 0;
//        try {
//            WebElement wE = verifyObjectExistence(wElement, driver);
//            if (wE.getAttribute("href").contains(textToVerify)) {
//                Browser.getLogger().info("Hyper link is successfully verified" + wE);
//                return true;
//            } else {
//                resetImplicitWaitTime(driver, 5);
//                while (noOfAttempts < 3) {
//                    Browser.getLogger().info("Web Element is present but not displayed on screen : " + wE);
//                    scrollToViewWebElement(driver, wE, ElementInfo);
//                    try {
//                        if (wE.getAttribute("href").contains(textToVerify)) {
//                            resetImplicitWaitTime(driver, noOfAttempts);
//                            return true;
//                        }
//                    } catch (NoSuchElementException e) {
//                        Browser.getLogger().info("Web Element not displayed : " + wE);
//                        return false;
//                    }
//                    noOfAttempts++;
//                }
//                resetImplicitWaitTime(driver, noOfAttempts);
//                Browser.getLogger().info("Hyper link is different" + wE);
//                return false;
//            }
//        } catch (StaleElementReferenceException e) {
//            Browser.getLogger().info("Element is stale(May have been closed / hidden/ not loaded)" + " : ");
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//
//    }
//
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
//
//    /******************************************************************************
//     * Description : To click on element by Actions Class and WebElement
//     * Arguments : WebElement, WebDriver instance, elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementByActions(String elXpath,WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            String absoluteXpath=getAbsoluteXPath(wElement,driver);
//            waitUntilObjectIsClickable(driver, wElement);
//            actionsClick(wElement, driver, ElementInfo);
//            Browser.getLogger().info("Clicked the element(Actions) : " + ElementInfo);
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(elXpath, OuterHTML, absoluteXpath,isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//            String aiXpath= aiInventoryUpdate.xpathAIFix(driver, elXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Element to click not found");
//                fail("Element to click not found..! :  " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            WebElement aiWebele = driver.findElement(By.xpath(aiXpath));
//            clickElementByActions(aiXpath,aiWebele, driver, ElementInfo);
//        } catch (ElementClickInterceptedException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (InvalidElementStateException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + wElement);
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            if (verifyObjectIsDisplayed(tempElement, driver)) {
//                clickElementJScript(driver, tempElement, ElementInfo);
//            }
//        } catch (InvocationTargetException e) {
//            Browser.getLogger().fatal("Element to click unavailable (InvocationTargetException) : " + ElementInfo);
//            fail("Element to click unavailable (InvocationTargetException) : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//
//
//    /******************************************************************************
//     * Description : To Verify text in WebPage by WebElement
//     * Arguments : WebDriver instance,WebElement, textToVerify
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyWebPageTextDisplayed(WebDriver driver, WebElement wElement, String textToVerify,
//                                              String ElementInfo) {
//        try {
//            if (wElement.getText().trim().equalsIgnoreCase(textToVerify.trim())) {
//                Browser.getLogger().info("Text comparison successful for : " + ElementInfo);
//                String OuterHTML = wElement.getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info("Text comparison failed for : " + ElementInfo + " Expected : " + textToVerify
//                        + " Actual : " + wElement.getText());
//                fail("Text comparison failed for : " + ElementInfo + " Expected : " + textToVerify + " Actual : "
//                        + wElement.getText());
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not available/displayed : " + ElementInfo);
//                fail("Web Element not available/displayed : " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            return verifyWebPageTextDisplayed(driver, aiElement, textToVerify, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger().info("Element hidden. " + ElementInfo);
//            return false;
//        } catch (InvalidElementStateException e) {
//            Browser.getLogger().info("Element hidden " + ElementInfo);
//            fail("Element hidden");
//            return false;
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            return verifyWebPageTextDisplayed(driver, tempElement, textToVerify, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
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
//    /******************************************************************************
//     * Description : To click on element by Actions Class and WebElement
//     * Arguments : WebElement, WebDriver instance, elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementByActions(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            actionsClick(wElement, driver, ElementInfo);
//            Browser.getLogger().info("Clicked the element(Actions) : " + ElementInfo);
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                fail("Element to click not found..! :  " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            clickElementByActions(aiElement, driver, ElementInfo);
//        } catch (ElementClickInterceptedException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (InvalidElementStateException e) {
//            Browser.getLogger()
//                    .info("Element hidden. Unable to click, trying to Click using JavaScript Executor " + wElement);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + wElement);
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            if (verifyObjectIsDisplayed(tempElement, driver)) {
//                clickElementJScript(driver, tempElement, ElementInfo);
//            }
//        } catch (InvocationTargetException e) {
//            Browser.getLogger().fatal("Element to click unavailable (InvocationTargetException) : " + ElementInfo);
//            fail("Element to click unavailable (InvocationTargetException) : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }



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
     * Description : To scroll to top of page
     * Arguments : WebDriver instance
     * Return Value : NA
     ********************************************************************************/

    public void scrollToTopOfPage(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
            waitForSeconds(3);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
        }
    }
//
//    /******************************************************************************
//     * Description : To scroll to bottom of page
//     * Arguments : WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void scrollTillEndOfPage(WebDriver driver) {
//        try {
//            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
//            waitForSeconds(3);
//            Browser.getLogger().info("Page scrolled till the end..!");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To scroll into view of element for which xpath given
//     * Arguments : WebDriver instance,String xpath, elementInfo
//     * Return Value : String
//     ********************************************************************************/
//
//    public String scrollToViewElement(WebDriver driver, String wElementXpath, String ElementInfo) {
//        WebElement wElement;
//        try {
//            wElement = driver.findElement(By.xpath(wElementXpath));
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Element not displayed : " + ElementInfo);
//                fail("The element doesn't exist : " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            return scrollToViewElement(driver, aiXpath, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", wElement);
//        String OuterHTML = wElement.getAttribute("outerHTML");
//        aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(wElement,driver),isRecursiveCall);
//        isRecursiveCall = false;
//        return wElementXpath;
//    }
//
//    /******************************************************************************
//     * Description : To click element if available
//     * Arguments : WebDriver instance, WebElement, elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementIfPresent(WebDriver driver, WebElement wElement, String ElementInfo) {
//        try {
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            String absoluteXpath=getAbsoluteXPath(wElement,driver);
//            wElement.click();
//            Browser.getLogger().info("Clicked the element : " + ElementInfo);
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                    absoluteXpath,isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not present-- continuing without click : " + ElementInfo);
//                return;
//            }
//            isRecursiveCall = true;
//            clickElementIfPresent(driver, aiElement, ElementInfo);
//        } catch (ElementClickInterceptedException e) {
//            Browser.getLogger()
//                    .info("Element click intercepted. Unable to click, trying to Click using Actions Class " + ElementInfo);
//            clickElementByActions(wElement, driver, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger()
//                    .info("Element not intractable. Unable to click, trying to Click using Actions Class " + ElementInfo);
//            clickElementByActions(wElement, driver, ElementInfo);
//        } catch (InvalidElementStateException e) {
//            Browser.getLogger()
//                    .info("Element state invalid. Unable to click, trying to Click using Actions Class " + ElementInfo);
//            clickElementByActions(wElement, driver, ElementInfo);
//        } catch (StaleElementReferenceException e) {
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + ElementInfo);
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            clickElementIfPresent(driver, tempElement, ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger()
//                    .info("Web Element not present (WebDriverException)-- continuing without click : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
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
//
//    /******************************************************************************
//     * Description : To select a list value with Visible Text provided
//     * Arguments : WebElement, String value, WebDriver instance, elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void selectListByVisibleText(WebElement wElement, String valueToSelect, WebDriver driver,
//                                        String ElementInfo) {
//        try {
//            Select selectElement = new Select(wElement);
//            clickElement(driver, wElement, ElementInfo);
//            waitForSeconds(5);
//            selectElement.selectByVisibleText(valueToSelect);
//            Browser.getLogger().info("Selected : " + valueToSelect + " from : " + ElementInfo);
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Select not displayed : " + ElementInfo);
//            fail("The select object doesn't exist : " + ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e) {
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            selectListByVisibleText(tempElement, valueToSelect, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To Verify Object is Enabled by Xpath given
//     * Arguments : String xpath, WebDriver instance
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyObjectIsEnabled(String xpathString, WebDriver driver) {
//        try {
//            if (driver.findElement(By.xpath(xpathString)).isEnabled()) {
//                Browser.getLogger().info("Web Element is Enabled : " + xpathString);
//                String OuterHTML = driver.findElement(By.xpath(xpathString)).getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(xpathString, OuterHTML, getAbsoluteXPath(driver.findElement(By.xpath(xpathString)),driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info("Web Element is disabled : " + xpathString);
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, xpathString, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + xpathString);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyObjectIsEnabled(aiXpath, driver);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To switch to the ChildWindow
//     * Arguments : WebDriver instance,String URL
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean switchToChildWindow(WebDriver driver, String urlContains) // Modified this as getWindowHandles do not
//    // return browser instances in correct
//    // order
//    {
//        int i = 1;
//        boolean switchedWindow = false;
//        do {
//            String ChildWindowHandle;
//            for (String iterator : driver.getWindowHandles()) {
//                ChildWindowHandle = iterator;
//                driver.switchTo().window(ChildWindowHandle);
//                waitUntilPageLoads(driver,5);
//                if (driver.getCurrentUrl().contains(urlContains)) {
//                    driver.manage().window().maximize();
//                    i = 30;
//                    Browser.getLogger().info("Switched the window successfully..");
//                    switchedWindow = true;
//                    break;
//                }
//                waitForSeconds(2);
//            }
//            waitForSeconds(2);
//            i++;
//        } while (i < 30);
//        if (!switchedWindow) {
//            Browser.getLogger().info("Unable to switch window");
//            return false;
//        }
//        return true;
//    }
//
//    /******************************************************************************
//     * Description : To delete the cookie given
//     * Arguments : WebDriver instance, String cookie name
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean deleteCookie(WebDriver driver, String cookieName) {
//        try {
//            String cookieValue = driver.manage().getCookieNamed(cookieName).toString();
//            if (!driver.manage().getCookieNamed(cookieName).equals("")) {
//                driver.manage().deleteCookieNamed(cookieName);
//                Browser.getLogger().info("Deleted the cookie successfully....! CookieName: " + cookieName
//                        + "  cookie value :  " + cookieValue);
//                recordTextData("Cookie Deleted -> ", cookieValue, scn);
//                return true;
//            }
//            Browser.getLogger().fatal("Failed to delete the cookie...! cookie not found..! Cookie Name : " + cookieName);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To set the cookie given
//     * Arguments : WebDriver instance,String cookieName, String cookieValue
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean setCookie(WebDriver driver, String cookieName, String cookieValue) {
//        try {
//            Browser.getLogger().info("Domain :" + driver.getCurrentUrl());
//            String emailIdValidationRegEx = "(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
//            String domain = driver.getCurrentUrl().trim();
//            Pattern pattern = Pattern.compile(emailIdValidationRegEx);
//            Matcher matcher = pattern.matcher(domain);
//            if (matcher.find()) {
//                domain = matcher.group(2);
//                Browser.getLogger().info("Domain to be used :" + domain);
//            } else {
//                Browser.getLogger().info("Domain not found in the URL : " + driver.getCurrentUrl().trim());
//                fail("Domain not found in the URL : " + driver.getCurrentUrl().trim());
//                return false;
//            }
//            recordTextData("DomainName -> ", domain, scn);
//            Cookie cookie = new Cookie(cookieName, cookieValue, domain, "/", null);
//            driver.manage().addCookie(cookie);
//            waitForSeconds(5);
//            if (driver.manage().getCookieNamed(cookieName).toString().contains(cookieValue)) {
//                Browser.getLogger()
//                        .info("Cookie set successfully...! Cookie Name : " + cookieName + "  cookie value: " + cookieValue);
//                recordTextData("CookieSet", "Cookie set successfully...! Cookie Name : " + cookieName
//                        + System.lineSeparator() + "  cookie value: " + driver.manage().getCookieNamed(cookieName), scn);
//                return true;
//            }
//            Browser.getLogger().fatal("Unable to set cookie...! Cookie Name : " + cookieName + "  cookie value: "
//                    + cookieValue + "  Actual cookie value : " + driver.manage().getCookieNamed(cookieName));
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To print the Cookie
//     * Arguments : WebDriver instance,String cookieName
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean printCookie(WebDriver driver, String cookieName) {
//        try {
//            if (!driver.manage().getCookieNamed(cookieName).equals("")) {
//                Browser.getLogger().info("Cookie available...! Cookie Name : " + cookieName + "  cookie value: "
//                        + driver.manage().getCookieNamed(cookieName));
//                recordTextData("CookieAvailable",
//                        "Cookie Name : " + cookieName + "  cookie value: " + driver.manage().getCookieNamed(cookieName),
//                        scn);
//                return true;
//            }
//            Browser.getLogger().fatal("Cookie not available...! Cookie Name : " + cookieName);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To read all the Cookies
//     * Arguments : WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void readAllCookies(WebDriver driver) {
//        try {
//            Set<Cookie> cookies = driver.manage().getCookies();
//            Browser.getLogger().info("Number of cookies" + cookies.size());
//            String allCookies = "";
//            for (Cookie cookie : cookies) {
//                allCookies = allCookies + cookie.getName() + " : " + cookie.getValue() + System.lineSeparator();
//            }
//            recordTextData("Cookies -> ", allCookies, scn);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
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
//
//    /******************************************************************************
//     * Description : To find JavaScript errors in the page loaded
//     * Arguments :WebDriver instance
//     * Return Value : boolean
//     ********************************************************************************/
//
//    @Step("Find java script errors in the page loaded")
//    public boolean findJavascriptErrors(WebDriver driver) {
//        boolean jscriptErrorNotPresent = true;
//        try {
//            LogEntries logentries = driver.manage().logs().get(LogType.BROWSER);
//            int i = 1;
//            for (LogEntry entry : logentries) {
//                enterFailLogs(entry, i);
//                i++;
//                if (entry.getLevel().toString().trim().equalsIgnoreCase("WARNING")) {
//                    return true;
//                } else {
//                    jscriptErrorNotPresent = false;
//                }
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//        return jscriptErrorNotPresent;
//    }
//
//    /******************************************************************************
//     * Description : To return fail logs message
//     * Arguments : LogEntry instance, integer "i"
//     * Return Value : String
//     ********************************************************************************/
//    @Attachment(value = "Error : {1}")
//    private String enterFailLogs(LogEntry entry, int i) {
//        String logStringMsg = "Issue :" + i + ":  " + entry.getLevel() + "   :   " + entry.getMessage();
//        Browser.getLogger().info(logStringMsg);
//        return logStringMsg;
//    }
//
//    /******************************************************************************
//     * Description : To delete temp files from TEMP folder.
//     * Arguments : NA
//     * Return Value : NA
//     ********************************************************************************/
//
//    public synchronized void deleteTempFiles() {
//        try {
//            String tempLocation = System.getProperty("user.home") + "\\AppData\\Local\\Temp\\";
//            File dir = new File(tempLocation);
//            deleteDir(dir);
//            Browser.getLogger().info("TEMP files deleted...!");
//            waitForSeconds(4);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : Called function inside deleteTempFiles, to delete directories and subdirectories
//     * Arguments : File instance
//     * Return Value : boolean
//     ********************************************************************************/
//
//    private boolean deleteDir(File dir) {
//        File tempFile;
//        final int TimeLimit = 30 * 60 * 1000;
//        long tenAgo = System.currentTimeMillis() - TimeLimit;
//        try {
//            if (dir.isDirectory()) {
//                String[] children = dir.list();
//                for (int i = 0; i < children.length; i++) {
//                    tempFile = new File(dir, children[i]);
//                    if (tempFile.lastModified() < tenAgo)
//                        if (tempFile.delete()) {
//                        } else {
//                            deleteDirAllSub(tempFile);
//                        }
//                }
//            }
//            dir.delete();
//        } catch (NullPointerException e) {
//            fail("Cannot delete file (Null Pointer Exception)");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//        return true;
//    }
//
//    /******************************************************************************
//     * Description : Called function inside deleteTempFiles , to delete sub files
//     * Arguments : File instance
//     * Return Value : boolean
//     ********************************************************************************/
//    private static boolean deleteDirAllSub(File dir) {
//        try {
//            String[] children;
//            if (dir.isDirectory()) {
//                children = dir.list();
//                for (int i = 0; i < children.length; i++) {
//                    boolean success = deleteDirAllSub(new File(dir, children[i]));
//                    if (!success) {
//                        Browser.getLogger().info("Deletion failed: " + children[i]);
//                        return false;
//                    }
//                }
//            }
//            dir.delete();
//            return true;
//        } catch (NullPointerException e) {
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
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

//    /******************************************************************************
//     * Description : To Click on the given element and select all (CTRL +A)
//     * Arguments : WebDriver instance, WebElement, ElementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void selectAllElements(WebDriver driver, WebElement wElement, String ElementInfo) {
//        try {
//            clickElement(wElement, driver, ElementInfo);
//            waitForSeconds(10);
//            Actions action = new Actions(driver);
//            action.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0061')).perform();
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To Copy a file from one folder to another.
//     * Arguments : String from, String to
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void copyFile(String from, String to) {
//        File fromFile = new File(from);
//        File toFile = new File(to + fromFile.getName());
//        if (toFile.exists() || !toFile.exists()) {
//            try {
//                FileUtils.copyFile(fromFile, toFile);
//            } catch (IOException e) {
//                Browser.getLogger().error("Someone is using the File :" + toFile.getName());
//                fail("Some other process is using the File : " + toFile.getName());
//            } catch (Exception e) {
//                Browser.getLogger().info("Generic Exception");
//                fail("Generic Exception");
//            }
//        }
//    }
//
//    /******************************************************************************
//     * Description : To Validate prefix of sub text
//     * Arguments : String mainText, String prefix
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean validatePrefixSubText(String mainText, String prefix) {
//        try {
//            return mainText.startsWith(prefix);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To wait until the Object is clickable
//     * Arguments : WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void waitUntilObjectIsClickable(WebDriver driver, WebElement wElement) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(wElement));
//        } catch (TimeoutException e) {
//            Browser.getLogger().info("Element not clickable:" + wElement.toString());
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Element not clickable:" + wElement.toString());
//            fail("Element not clickable:" + wElement);
//        } catch (StaleElementReferenceException e) {
//            waitForSeconds(5);
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + wElement.toString());
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            waitUntilObjectIsClickable(driver, tempElement);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To click Element given
//     * Arguments : WebElement, WebDriver instance, elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElement(WebElement wElement, WebDriver driver, String ElementInfo) {
//        clickElement(driver, wElement, ElementInfo);
//    }
//
//    /******************************************************************************
//     * Description : To get the xPath
//     * Arguments : WebElement
//     * Return Value : String
//     ********************************************************************************/
//
//    public String generateXpathFromWebElement(WebElement webEl) {
//        String elementInfo = webEl.toString();
//        Browser.getLogger().info("Xpath String (RAW) : " + elementInfo);
//        String elementLocator;
//        try {
//            elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
//            elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
//            elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            return generateXpathFromWebElementWithoutRef(webEl);
//        } catch (StringIndexOutOfBoundsException e) {
//            return generateXpathFromWebElementWithoutRef(webEl);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//        Browser.getLogger().info("Xpath String (returning) : " + elementLocator);
//        return elementLocator;
//    }
//
//    /******************************************************************************
//     * Description : To generate xpath from web element without reference
//     * Arguments : WebElement
//     * Return Value : String
//     ********************************************************************************/
//
//    public String generateXpathFromWebElementWithoutRef(WebElement webEl) {
//        try {
//            String elementInfo = webEl.toString();
//            elementInfo = elementInfo.substring(elementInfo.indexOf("By."));
//            String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
//            elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
//            return elementLocator;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//    }
//
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
     * Description : To validate text in any matching WebElements
     * Arguments : WebDriver Instance, String xpath
     * Return Value : WebElement
     ********************************************************************************/

    public WebElement validateTextInAnyMatchingWebElements(WebDriver driver, String wElementXpath,
                                                           String textToCompare) {
        WebElement elementTemp = null; // dummy element
        try {
            List<WebElement> AllElements = driver.findElements(By.xpath(wElementXpath));
            String AvailableTexts = "";
            for (WebElement we : AllElements) {
                if (we.getText().trim().contains(textToCompare)) {
                    Browser.getLogger().info("Text found : " + textToCompare + " under the xpath : " + wElementXpath);
                    return we;
                } else {
                    Browser.getLogger()
                            .info("Text comparison failed. checking next....! current text :  " + we.getText().trim());
                    AvailableTexts = AvailableTexts + we.getText().trim() + "   |  ";
                }
            }
            Browser.getLogger().fatal("Text validation failed. Text : " + textToCompare
                    + " Available texts on screen : " + AvailableTexts);
            fail("Text validation failed. Text : " + textToCompare + " Available texts on screen : " + AvailableTexts);
        } catch (NoSuchElementException e) {
            Browser.getLogger().fatal("No Elements matching xpath : " + wElementXpath);
            fail("No Elements matching xpath : " + wElementXpath);
            return elementTemp;
        } catch (StaleElementReferenceException e) {
            return validateTextInAnyMatchingWebElements(driver, wElementXpath, textToCompare);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return elementTemp;
        }
        return elementTemp;
    }
//
//    /******************************************************************************
//     * Description : To check whether matching text found or not
//     * Arguments : WebDriver Instance, String xpath, String text, scenario
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean isMatchingTextFound(WebDriver driver, String wElementXpath, String textToCompare, Scenario scn) {
//        try {
//            List<WebElement> AllElements = driver.findElements(By.xpath(wElementXpath));
//            String AvailableTexts = "";
//            for (WebElement we : AllElements) {
//                if (we.getText().trim().contains(textToCompare)) {
//                    Browser.getLogger().info("Text found : " + textToCompare + " under the xpath : " + wElementXpath);
//                    recordTextData("Text_Comparison_Successful", "Successfully found : " + textToCompare, scn);
//                    return true;
//                } else {
//                    Browser.getLogger()
//                            .info("Text comparison failed. checking next....! current text :  " + we.getText().trim());
//                    AvailableTexts = AvailableTexts + we.getText().trim() + "   |  ";
//                }
//            }
//            Browser.getLogger().fatal("Text validation failed. Text : " + textToCompare
//                    + " Available texts on screen : " + AvailableTexts);
//            recordTextData("Text_Comparison_Failed", "Available : " + AvailableTexts + "  Not found : " + textToCompare,
//                    scn);
//            return false;
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().fatal("No Elements matching xpath : " + wElementXpath);
//            fail("No Elements matching xpath : " + wElementXpath);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To scroll into WebElement view
//     * Arguments : WebDriver Instance, String xpath
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void scrollToViewWebElement(WebDriver driver, String wElementXpath) {
//        WebElement wElement = null;
//        try {
//            wElement = driver.findElement(By.xpath(wElementXpath));
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(wElement,driver),isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Element to scroll not found..! :  ");
//
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                fail("Element to scroll not found..! :  ");
//            }
//            isRecursiveCall = true;
//            scrollToViewWebElement(driver, aiXpath);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView()", wElement);
//
//    }
//
//    /******************************************************************************
//     * Description : To return 1st displayed element matching the xpath
//     * Arguments : String xpath,WebDriver instance
//     * Return Value : WebElement
//     ********************************************************************************/
//
//    public WebElement returnFirstDisplayedElementMatchingXpath(String elementXpath, WebDriver driver) {
//        try {
//            int size = driver.findElements(By.xpath(elementXpath)).size();
//            List<WebElement> matchingElements = driver.findElements(By.xpath(elementXpath));
//            int i = 0;
//            while (i < size) {
//                if (matchingElements.get(i).isDisplayed()) {
//                    return matchingElements.get(i);
//                }
//                i++;
//            }
//            return null;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return null;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify Object is Displayed
//     * Arguments : String xpath, WebDriver instance
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyObjectIsDisplayed(String xpathString, WebDriver driver) {
//        try {
//            if (driver.findElement(By.xpath(xpathString)).isDisplayed()) {
//                Browser.getLogger().info("WebElement is present : " + xpathString);
//                String OuterHTML = driver.findElement(By.xpath(xpathString)).getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(xpathString, OuterHTML, getAbsoluteXPath(driver.findElement(By.xpath(xpathString)),driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info("Web Element is present but not displayed on screen : " + xpathString);
//                int noOfAttempts = 0;
//                resetImplicitWaitTime(driver, 4);
//                while (noOfAttempts < 3) {
//                    scrollToViewElement(driver, xpathString, xpathString);
//                    isRecursiveCall = true;
//                    try {
//                        Browser.getLogger().info("Re-attempting to find the element: " + xpathString);
//                        if (driver.findElement(By.xpath(xpathString)).isDisplayed()) {
//                            isRecursiveCall = false;
//                            return true;
//                        }
//                    } catch (NoSuchElementException e) {
//                        isRecursiveCall = false;
//                        return false;
//                    }
//                    waitForSeconds(3);
//                    noOfAttempts++;
//                }
//                resetImplicitWaitTime(driver);
//                isRecursiveCall = false;
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, xpathString, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element is unavailable : " + xpathString);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyObjectIsDisplayed(aiXpath, driver);
//        } catch (StaleElementReferenceException f) {
//            Browser.getLogger().info("Stale Element exception - reloading the element reference : " + xpathString);
//            waitForSeconds(5);
//            return verifyObjectIsDisplayed(xpathString, driver);
//        } catch (WebDriverException f) {
//            Browser.getLogger().info("Web Element is unavailable : " + xpathString);
//            waitForSeconds(15);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify Object is Displayed
//     * Arguments : String xpath, WebDriver instance, String failure message
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyObjectIsDisplayed(String xpathString, WebDriver driver, String FailureMessage) {
//        try {
//            if (driver.findElement(By.xpath(xpathString)).isDisplayed()) {
//                scrollToViewWebElement(driver, xpathString);
//                Browser.getLogger().info("WebElement is present : " + xpathString);
//                String OuterHTML = driver.findElement(By.xpath(xpathString)).getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(xpathString, OuterHTML, getAbsoluteXPath(driver.findElement(By.xpath(xpathString)),driver),isRecursiveCall);
//                isRecursiveCall = false;
//            } else {
//                Browser.getLogger().info("Web Element is present but not displayed on screen : " + xpathString);
//                int noOfAttempts = 0;
//                resetImplicitWaitTime(driver, 4);
//                while (noOfAttempts < 6) {
//                    scrollToViewWebElement(driver, xpathString);
//                    try {
//                        Browser.getLogger().info("Re-attempting to find the element: " + xpathString);
//                        if (driver.findElement(By.xpath(xpathString)).isDisplayed()) {
//                            resetImplicitWaitTime(driver);
//                        }
//                    } catch (NoSuchElementException e) {
//                        // Do Nothing. proceed to next iteration.
//                    }
//                    waitForSeconds(8);
//                    noOfAttempts++;
//                }
//                resetImplicitWaitTime(driver);
//                fail(FailureMessage);
//            }
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, xpathString, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element is unavailable : " + xpathString);
//                fail(FailureMessage);
//            }
//            isRecursiveCall = true;
//            verifyObjectIsDisplayed(aiXpath, driver, FailureMessage);
//        } catch (StaleElementReferenceException f) {
//            Browser.getLogger().info("Stale Element exception - reloading the element reference : " + xpathString);
//            waitForSeconds(5);
//            verifyObjectIsDisplayed(xpathString, driver);
//        } catch (WebDriverException f) {
//            Browser.getLogger().info("Web Element is unavailable : " + xpathString);
//            fail(FailureMessage);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify Object is Displayed
//     * Arguments : WebElement, WebDriver instance, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyObjectIsDisabled(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            if (!wElement.isEnabled()) {
//                Browser.getLogger().info("Web Element is disabled : " + ElementInfo);
//                String OuterHTML = wElement.getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info("Web Element enabled (Expectation : disabled) : " + ElementInfo);
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not available/displayed : " + ElementInfo);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyObjectIsDisabled(aiElement, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify text box value
//     * Arguments : WebElement, String value, WebDriver instance
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyTextBoxValue(WebElement wElement, String Value, WebDriver driver) {
//        try {
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            if (wElement.getAttribute("value").equalsIgnoreCase(Value)) {
//                Browser.getLogger().info("Text box validated successfully...!");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not displayed");
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyTextBoxValue(aiElement, Value, driver);
//
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed (WebDriverException)");
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify text box value
//     * Arguments : WebElement, String value, WebDriver instance, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyTextBoxValue(WebElement wElement, String Value, WebDriver driver, String ElementInfo) {
//        try {
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            if (wElement.getAttribute("value").equalsIgnoreCase(Value)) {
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                fail("Text box value not matching. Expected : " + Value + "  Actual : "
//                        + wElement.getAttribute("value"));
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//                fail("Web Element not displayed : " + ElementInfo);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyTextBoxValue(aiElement, Value, driver, ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            fail("Web Element not displayed : " + ElementInfo);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify text box value
//     * Arguments : String xpath, String value, WebDriver instance ,String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyTextBoxValue(String wElementXpath, String Value, WebDriver driver, String ElementInfo) {
//        try {
//            WebElement wElement = driver.findElement(By.xpath(wElementXpath));
//            if (wElement.getAttribute("value").equalsIgnoreCase(Value)) {
//                String OuterHTML = wElement.getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info(
//                        "Validation failed. Expected : " + Value + "  Actual : " + wElement.getAttribute("value"));
//                fail("Validation failed. Expected : " + Value + "  Actual : " + wElement.getAttribute("value"));
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//                fail("Web Element not displayed : " + ElementInfo);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyTextBoxValue(aiXpath, Value, driver, ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            fail("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To fetch text box value
//     * Arguments : WebElement, WebDriver instance, String elementInfo
//     * Return Value : String
//     ********************************************************************************/
//
//    public String fetchTextBoxValue(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            String val = wElement.getAttribute("value");
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                    getAbsoluteXPath(wElement,driver),isRecursiveCall);
//            isRecursiveCall = false;
//            return val;
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//                fail("Web Element not displayed : " + ElementInfo);
//                return "NOT FOUND";
//            }
//            isRecursiveCall = true;
//            return fetchTextBoxValue(aiElement, driver, ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            fail("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            return "NOT FOUND";
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//    }
//
//    /******************************************************************************
//     * Description : To fetch text box value
//     * Arguments : String xpath, WebDriver instance, String elementInfo
//     * Return Value : String
//     ********************************************************************************/
//
//    public String fetchTextBoxValue(String wElementXpath, WebDriver driver, String ElementInfo) {
//        try {
//            String val = driver.findElement(By.xpath(wElementXpath)).getAttribute("value");
//            String OuterHTML = driver.findElement(By.xpath(wElementXpath)).getAttribute("outerHTML");
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(driver.findElement(By.xpath(wElementXpath)),driver),isRecursiveCall);
//            isRecursiveCall = false;
//            return val;
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//                fail("Web Element not displayed : " + ElementInfo);
//                return "NOT FOUND";
//            }
//            isRecursiveCall = true;
//            return fetchTextBoxValue(aiXpath, driver, ElementInfo);
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            fail("Web Element not displayed (WebDriverException) : " + ElementInfo);
//            return "NOT FOUND";
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//
//    }
//
//    /******************************************************************************
//     * Description : To set Text
//     * Arguments : String xpath, String value, WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void setText(String wElementXpath, String valueToSet, WebDriver driver) {
//        WebElement wElement = null;
//        waitUntilPageLoads(driver,2);
//
//        try {
//            wElement = driver.findElement(By.xpath(wElementXpath));
//            wElement.sendKeys(valueToSet);
//            Browser.getLogger().info("Entered Text : " + valueToSet + " to element: " + wElement);
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, wElement.getAttribute("outerHTML"),
//                    getAbsoluteXPath(wElement,driver),isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + wElement);
//                fail("The element to set text doesn't exist : " + wElement);
//            }
//            isRecursiveCall = true;
//            setText(aiXpath, valueToSet, driver);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To wait until the object is intractable
//     * Arguments : WebDriver instance, WebElement, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void waitUntilObjectIsIntractable(WebDriver driver, WebElement wElement, String ElementInfo) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(wElement));
//        } catch (TimeoutException e1) {
//            Browser.getLogger().info("Timed out waiting for the element: " + wElement.toString());
//        }
//        try {
//            if (wElement.isDisplayed()) {
//                return;
//            } else {
//                Browser.getLogger().info("Page not loaded in stipulated time. Test failed");
//                fail("Page not loaded in stipulated time. Test failed");
//            }
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Element not found in stipulated time :" + e);
//            fail("Element not found in stipulated time :" + e);
//        } catch (StaleElementReferenceException e) {
//            waitForSeconds(5);
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + ElementInfo);
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            waitUntilObjectIsIntractable(driver, tempElement, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To clear text
//     * Arguments : WebElement, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clearText(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            waitUntilPageLoads(driver,5);
//            int noOfAttempts = 1;
//            try {
//                while (!wElement.getAttribute("value").equalsIgnoreCase("") && noOfAttempts < 5) // for text box
//                {
//                    wElement.clear();
//                    aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement),
//                            wElement.getAttribute("outerHTML"), getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                    isRecursiveCall = false;
//                    waitForSeconds(4);
//                    Browser.getLogger().info("Clearing.. Attempt : " + noOfAttempts);
//                    Browser.getLogger().info("Current value : " + wElement.getAttribute("value").trim());
//                    noOfAttempts++;
//                }
//            } catch (NullPointerException e) { // when it is a text area, this part works
//                while (!wElement.getText().equalsIgnoreCase("") && noOfAttempts < 5) {
//                    wElement.clear();
//                    waitForSeconds(4);
//                    noOfAttempts++;
//                }
//            }
//            Browser.getLogger().info("cleared the value from " + wElement);
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not displayed : " + wElement);
//                fail("The element to clear value does not exist : " + wElement.toString());
//            }
//            isRecursiveCall = true;
//            clearText(aiElement, driver, ElementInfo);
//        } catch (InvalidElementStateException e1) {
//            waitUntilObjectIsIntractable(driver, wElement, ElementInfo);
//            int noOfAttempts = 0;
//            while (noOfAttempts < 30) {
//                if (verifyObjectIsEnabled(wElement, driver, ElementInfo)) {
//                    Browser.getLogger().info("TextBox/TextArea is enabled " + wElement);
//                    break;
//                }
//                noOfAttempts++;
//                waitForSeconds(2);
//                try {
//                    wElement.clear();
//                    aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement),
//                            wElement.getAttribute("outerHTML"), getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                    isRecursiveCall = false;
//                    Browser.getLogger().info("cleared the value from " + wElement);
//                } catch (NoSuchElementException e) {
//                    WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//                    if (aiElement == null) {
//                        Browser.getLogger().info("Web Element not displayed : " + wElement);
//                        fail("The element to clear value does not exist :  " + wElement);
//                    }
//                    isRecursiveCall = true;
//                    clearText(aiElement, driver, ElementInfo);
//                } catch (InvalidElementStateException e2) {
//                    Browser.getLogger().info("Web Element not loaded / is read-only : " + wElement);
//                    fail("Web Element not loaded / is read-only : " + wElement);
//                }
//            }
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            clearText(tempElement, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To clear text
//     * Arguments : String xpath, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clearText(String wElementXpath, WebDriver driver, String ElementInfo) {
//        WebElement wElement = null;
//        try {
//            wElement = driver.findElement(By.xpath(wElementXpath));
//            waitUntilPageLoads(driver,5);
//            int noOfAttempts = 1;
//            try {
//                while (!wElement.getAttribute("value").equalsIgnoreCase("") && noOfAttempts < 5) // for text box
//                {
//                    wElement.clear();
//                    aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, wElement.getAttribute("outerHTML"),
//                            getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                    isRecursiveCall = false;
//                    waitForSeconds(2);
//                    Browser.getLogger().info("Clearing.. Attempt : " + noOfAttempts);
//                    Browser.getLogger().info("Current value : " + wElement.getAttribute("value").trim());
//                    noOfAttempts++;
//                }
//            } catch (NullPointerException e) { // when it is a text area, this part works
//                while (!wElement.getText().equalsIgnoreCase("") && noOfAttempts < 5) {
//                    wElement.clear();
//                    waitForSeconds(2);
//                    noOfAttempts++;
//                }
//            }
//            Browser.getLogger().info("cleared the value from " + wElement);
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + wElement);
//                fail("The element to clear value does not exist : " + wElement);
//            }
//            isRecursiveCall = true;
//            clearText(aiXpath, driver, ElementInfo);
//        } catch (InvalidElementStateException e1) {
//            waitUntilObjectIsIntractable(driver, wElement, ElementInfo);
//            int noOfAttempts = 0;
//            while (noOfAttempts < 30) {
//                if (verifyObjectIsEnabled(wElement, driver, ElementInfo)) {
//                    Browser.getLogger().info("TextBox/TextArea is enabled " + wElement);
//                    break;
//                }
//                noOfAttempts++;
//                waitForSeconds(2);
//                try {
//                    wElement.clear();
//                    Browser.getLogger().info("cleared the value from " + wElement);
//                    String OuterHTML = wElement.getAttribute("outerHTML");
//                    aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                    isRecursiveCall = false;
//                } catch (NoSuchElementException e) {
//                    String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//                    if (aiXpath == null) {
//                        Browser.getLogger().info("Web Element not displayed : " + wElement);
//                        fail("The element to clear value does not exist : " + wElement);
//                    }
//                    isRecursiveCall = true;
//                    clearText(aiXpath, driver, ElementInfo);
//                } catch (InvalidElementStateException e2) {
//                    Browser.getLogger().info("Web Element not loaded / is read-only : " + wElement);
//                    fail("Web Element not loaded / is read-only : " + wElement);
//                }
//            }
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            WebElement tempElement = reinitialiseWebElement(wElement, driver);
//            clearText(tempElement, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To click element
//     * Arguments : String xpath, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElement(String wElementString, WebDriver driver, String ElementInfo) {
//        WebElement wElement = null;
//        try {
//            wElement = driver.findElement(By.xpath(wElementString));
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            String absoluteXpath=getAbsoluteXPath(wElement,driver);
//            wElement.click();
//            Browser.getLogger().info("Clicked the element : " + wElementString);
//            waitUntilPageLoads(driver,5);
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementString, OuterHTML, absoluteXpath,isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementString, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not displayed : " + wElementString);
//                fail("The element to click doesn't exist : " + wElementString);
//            }
//            isRecursiveCall = true;
//            clickElement(aiXpath, driver, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger().info("Element hidden. " + wElementString);
//            clickElementJScript(driver, wElement, ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            clickElement(wElementString, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To click element if present
//     * Arguments : WebElement, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementIfPresent(WebElement wElement, WebDriver driver, String ElementInfo) {
//        WebElement wE = null;
//        try {
//            waitUntilPageLoads(driver,5);
//            wE = verifyObjectExistenceNoAssertion(wElement, driver);
//            if (wE != null) {
//                waitForSeconds(5);
//            } else {
//                Browser.getLogger()
//                        .info("Web Element not displayed : " + ElementInfo + "   ..Continuing without click..!");
//                return;
//            }
//            wE.click();
//            Browser.getLogger().info("Clicked the element: " + ElementInfo);
//            waitForSeconds(4);
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wE, this);
//            if (aiElement == null) {
//                Browser.getLogger()
//                        .info("Web Element not displayed : " + ElementInfo + "   ..Continuing without click..!");
//                return;
//            }
//            isRecursiveCall = true;
//            clickElementIfPresent(aiElement, driver, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger().info("Element hidden. Unable to click " + ElementInfo);
//            clickElement(wE, driver, ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            try {
//                WebElement tempElement = reinitialiseWebElement(wE, driver);
//                clickElementIfPresent(tempElement, driver, ElementInfo);
//            } catch (NoSuchWindowException e11) {
//                Browser.getLogger().info("Window disappeared on clicking...!");
//            }
//        } catch (NoSuchWindowException e2) {
//            Browser.getLogger().info("Window disappeared on clicking...!");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To click element if present
//     * Arguments : String xpath, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementIfPresent(String wElementXpath, WebDriver driver, String ElementInfo) {
//        WebElement wElement;
//        try {
//            wElement = driver.findElement(By.xpath(wElementXpath));
//            waitUntilPageLoads(driver,5);
//            if (waitUntilObjectLoads(driver, wElement, 1, ElementInfo)) {
//                waitForSeconds(5);
//            } else {
//                Browser.getLogger()
//                        .info("Web Element not displayed : " + ElementInfo + "   ..Continuing without click..!");
//                return;
//            }
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            String absoluteXpath=getAbsoluteXPath(wElement,driver);
//            wElement.click();
//            Browser.getLogger().info("Clicked the element: " + ElementInfo);
//            waitForSeconds(4);
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, absoluteXpath,isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger()
//                        .info("Web Element not displayed : " + ElementInfo + "   ..Continuing without click..!");
//                return;
//            }
//            isRecursiveCall = true;
//            clickElementIfPresent(aiXpath, driver, ElementInfo);
//        } catch (ElementNotInteractableException e) {
//            Browser.getLogger().info("Element hidden. Unable to click " + ElementInfo);
//            clickElement(wElementXpath, driver, ElementInfo);
//        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
//            try {
//                clickElementIfPresent(wElementXpath, driver, ElementInfo);
//            } catch (NoSuchWindowException e11) {
//                Browser.getLogger().info("Window disappeared on clicking...!");
//            }
//        } catch (NoSuchWindowException e2) {
//            Browser.getLogger().info("Window disappeared on clicking...!");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//
//    }
//
//    /******************************************************************************
//     * Description : To click element by actions
//     * Arguments : String xpath, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//
//    /******************************************************************************
//     * Description : To click element Until element is not visible
//     * Arguments : WebElement, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void clickElementUntilElementIsNotVisible(WebElement wElement, WebDriver driver, String ElementInfo) {
//        try {
//            int attempts = 0;
//            resetImplicitWaitTime(driver, 4);
//            while (wElement.isDisplayed() && attempts < 10) {
//                clickElement(wElement, driver, ElementInfo);
//                waitForSeconds(10);
//                attempts++;
//            }
//            waitForSeconds(10);
//            resetImplicitWaitTime(driver);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To select list by value
//     * Arguments : WebElement, String value, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void selectListByValue(WebElement wElement, String valueToSelect, String ElementInfo) {
//        try {
//            Select selectElement = new Select(wElement);
//            selectElement.selectByValue(valueToSelect);
//            Browser.getLogger().info("Selected : " + valueToSelect + "  from : " + ElementInfo);
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Select not displayed : " + ElementInfo);
//            fail("The select object doesn't exist : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To select list by index
//     * Arguments : WebElement, int index, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void selectListByIndex(WebElement wElement, int indexToSelect, String ElementInfo) {
//        try {
//            Select selectElement = new Select(wElement);
//            selectElement.selectByIndex(indexToSelect);
//            Browser.getLogger().info("Selected : " + indexToSelect + "  from : " + selectElement);
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Select not displayed : " + ElementInfo);
//            fail("The select object doesn't exist : " + ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify Element value is matching with text
//     * Arguments :WebElement, String value, String attribute, String msg, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyElementValueIsMatching(WebElement wElement, String Value, String attribute, String message,
//                                             WebDriver driver, String ElementInfo) {
//        try {
//            String txtToCompare = wElement.getAttribute(attribute);
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            if (txtToCompare.equalsIgnoreCase(Value)) {
//                Browser.getLogger().info(message + " " + txtToCompare + " is matching with " + Value);
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                        getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return;
//            } else {
//                Browser.getLogger().info(message + " " + txtToCompare + " is not matching with " + Value);
//                fail(message + " " + txtToCompare + " is not matching with " + Value);
//            }
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Web Element not displayed : " + ElementInfo);
//                fail("Element not present : " + ElementInfo);
//            }
//            isRecursiveCall = true;
//            verifyElementValueIsMatching(aiElement, Value, attribute, message, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify string inside WebElement
//     * Arguments : WebElement, String value, String msg, WebDriver instance, String element Info
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyStringInsideWebElement(WebElement wElement, String Value, String message, WebDriver driver,
//                                             String ElementInfo) {
//        try {
//            WebElement el = scrollToViewElement(driver, wElement, Value);
//            String txtToCompare = el.getText();
//            if (txtToCompare.toUpperCase().contains(Value.trim().toUpperCase())) {
//                Browser.getLogger().info(message + " " + txtToCompare + " is matching with " + Value);
//                return;
//            } else {
//                Browser.getLogger().info(message + ":  " + txtToCompare + " is not matching with : " + Value
//                        + " .Validation failed for : '" + ElementInfo + "'");
//                fail(message + ":  " + txtToCompare + " is not matching with : " + Value + " .Validation failed for : '"
//                        + ElementInfo + "'");
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//
//
//    /******************************************************************************
//     * Description : To verify page URL
//     * Arguments : String text, WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifyPageUrl(String Validate_Text, WebDriver driver) {
//        try {
//            String txtToCompare = driver.getCurrentUrl();
//            if (txtToCompare.toUpperCase().contains(Validate_Text.trim().toUpperCase())) {
//                Browser.getLogger().info("URL validation successful : " + txtToCompare);
//                return;
//            } else {
//                Browser.getLogger()
//                        .info("URL validation failed : Actual :" + txtToCompare + " Expected : " + Validate_Text);
//                fail("URL validation failed : Actual :" + txtToCompare + " Expected : " + Validate_Text);
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To compare date
//     * Arguments : String date1 , String date 2,String date format 1, String date format 2
//     * Return Value : boolean
//     ********************************************************************************/
//
//    @Step("Compare the dates : {0} and {1}")
//    public boolean dateCompare(String Date1, String Date2, String DateFormat, String DateFormat2) {
//        SimpleDateFormat sdFormat;
//        SimpleDateFormat sdFormat2;
//        switch (DateFormat) {
//            case "yyyy-MM-dd":
//                sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//                break;
//            case "dd-MM-yyyy":
//                sdFormat = new SimpleDateFormat("dd-MM-yyyy");
//                break;
//            case "MM-dd-yyyy":
//                sdFormat = new SimpleDateFormat("MM-dd-yyyy");
//                break;
//            case "MM-dd-yy":
//                sdFormat = new SimpleDateFormat("MM-dd-yy");
//                break;
//            case "dd-MM-yy":
//                sdFormat = new SimpleDateFormat("dd-MM-yy");
//                break;
//            case "yyyy/MM/dd":
//                sdFormat = new SimpleDateFormat("yyyy/MM/dd");
//                break;
//            case "dd/MM/yyyy":
//                sdFormat = new SimpleDateFormat("dd/MM/yyyy");
//                break;
//            case "MM/dd/yyyy":
//                sdFormat = new SimpleDateFormat("MM/dd/yyyy");
//                break;
//            case "MM/dd/yy":
//                sdFormat = new SimpleDateFormat("MM/dd/yy");
//                break;
//            case "dd/MM/yy":
//                sdFormat = new SimpleDateFormat("dd/MM/yy");
//                break;
//            default:
//                Browser.getLogger().info("Date format undefined");
//                return false;
//        }
//        switch (DateFormat2) {
//            case "yyyy-MM-dd":
//                sdFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//                break;
//            case "dd-MM-yyyy":
//                sdFormat2 = new SimpleDateFormat("dd-MM-yyyy");
//                break;
//            case "MM-dd-yyyy":
//                sdFormat2 = new SimpleDateFormat("MM-dd-yyyy");
//                break;
//            case "MM-dd-yy":
//                sdFormat2 = new SimpleDateFormat("MM-dd-yy");
//                break;
//            case "dd-MM-yy":
//                sdFormat2 = new SimpleDateFormat("dd-MM-yy");
//                break;
//            case "yyyy/MM/dd":
//                sdFormat2 = new SimpleDateFormat("yyyy/MM/dd");
//                break;
//            case "dd/MM/yyyy":
//                sdFormat2 = new SimpleDateFormat("dd/MM/yyyy");
//                break;
//            case "MM/dd/yyyy":
//                sdFormat2 = new SimpleDateFormat("MM/dd/yyyy");
//                break;
//            case "MM/dd/yy":
//                sdFormat2 = new SimpleDateFormat("MM/dd/yy");
//                break;
//            case "dd/MM/yy":
//                sdFormat2 = new SimpleDateFormat("dd/MM/yy");
//                break;
//            default:
//                Browser.getLogger().info("Date format undefined");
//                return false;
//        }
//        try {
//            Date d1 = sdFormat.parse(Date1);
//            Date d2 = sdFormat2.parse(Date2);
//            if (d1.compareTo(d2) == 0) {
//                Browser.getLogger().info("Dates matching : " + d1 + " and " + d2);
//                return true;
//            } else {
//                Browser.getLogger().info("Dates not matching : " + d1 + " and " + d2);
//                fail("Dates not matching : " + d1 + " and " + d2);
//                return false;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Browser.getLogger().info("Date parse error");
//            fail("Date parse error...");
//            return false;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify web page text is displayed or not
//     * Arguments : String xpath, String text, WebDriver instance, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean verifyWebPageTextDisplayed(String wElementXpath, String textToVerify, WebDriver driver,
//                                              String ElementInfo) {
//        try {
//            WebElement wElement = driver.findElement(By.xpath(wElementXpath));
//            if (wElement.getText().trim().equalsIgnoreCase(textToVerify.trim())) {
//                Browser.getLogger().info("Text comparison successful for : " + ElementInfo);
//                String OuterHTML = wElement.getAttribute("outerHTML");
//                aiInventoryUpdate.updateAILogOnSuccessfulAction(wElementXpath, OuterHTML, getAbsoluteXPath(wElement,driver),isRecursiveCall);
//                isRecursiveCall = false;
//                return true;
//            } else {
//                Browser.getLogger().info("Text comparison failed for : " + ElementInfo + " Expected :" + textToVerify
//                        + " Actual : (" + wElement.getText().trim() + ")");
//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            String aiXpath = aiInventoryUpdate.xpathAIFix(driver, wElementXpath, this);
//            if (aiXpath == null) {
//                Browser.getLogger().info("Web Element not available/displayed : " + ElementInfo);
//                fail("Web Element not available/displayed : " + ElementInfo);
//                return false;
//            }
//            isRecursiveCall = true;
//            return verifyWebPageTextDisplayed(aiXpath, textToVerify, driver, ElementInfo);
//        } catch (StaleElementReferenceException g) {
//            return verifyWebPageTextDisplayed(wElementXpath, textToVerify, driver, ElementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return false;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To add date
//     * Arguments : String date1, String DayOrMonthOrYear, int number, String date format
//     * Return Value : String
//     ********************************************************************************/
//
//    public String addDate(String Date1, String DayOrMonthOrYear, int NumberTOAdd, String DateFormat) {
//        SimpleDateFormat sdFormat;
//        switch (DateFormat) {
//            case "yyyy-MM-dd":
//                sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//                break;
//            case "dd-MM-yyyy":
//                sdFormat = new SimpleDateFormat("dd-MM-yyyy");
//                break;
//            case "MM-dd-yyyy":
//                sdFormat = new SimpleDateFormat("MM-dd-yyyy");
//                break;
//            case "MM-dd-yy":
//                sdFormat = new SimpleDateFormat("MM-dd-yy");
//                break;
//            case "dd-MM-yy":
//                sdFormat = new SimpleDateFormat("dd-MM-yy");
//                break;
//            case "yyyy/MM/dd":
//                sdFormat = new SimpleDateFormat("yyyy/MM/dd");
//                break;
//            case "dd/MM/yyyy":
//                sdFormat = new SimpleDateFormat("dd/MM/yyyy");
//                break;
//            case "MM/dd/yyyy":
//                sdFormat = new SimpleDateFormat("MM/dd/yyyy");
//                break;
//            case "MM/dd/yy":
//                sdFormat = new SimpleDateFormat("MM/dd/yy");
//                break;
//            case "dd/MM/yy":
//                sdFormat = new SimpleDateFormat("dd/MM/yy");
//                break;
//            default:
//                Browser.getLogger().info("Date format undefined");
//                fail("UnSupported Date Format");
//                return "UnSupported Date Format";
//        }
//        try {
//            Date d1 = sdFormat.parse(Date1);
//            Calendar c = Calendar.getInstance();
//            c.setTime(d1);
//            switch (DayOrMonthOrYear.toUpperCase()) {
//                case "DAY":
//                    c.add(Calendar.DAY_OF_MONTH, NumberTOAdd);
//                    Browser.getLogger().info("Date addition successful. Added : " + Date1 + " with : " + NumberTOAdd + " "
//                            + DayOrMonthOrYear);
//                    return sdFormat.format(c.getTime());
//                case "YEAR":
//                    c.add(Calendar.YEAR, NumberTOAdd);
//                    Browser.getLogger().info("Date addition successful. Added : " + Date1 + " with : " + NumberTOAdd + " "
//                            + DayOrMonthOrYear);
//                    return sdFormat.format(c.getTime());
//                case "MONTH":
//                    c.add(Calendar.MONTH, NumberTOAdd);
//                    Browser.getLogger().info("Date addition successful. Added : " + Date1 + " with : " + NumberTOAdd + " "
//                            + DayOrMonthOrYear);
//                    return sdFormat.format(c.getTime());
//                default:
//                    Browser.getLogger()
//                            .info("Issue with input : " + DayOrMonthOrYear + ". Date can only be added by Day/Month/Year");
//                    fail("Issue with input : " + DayOrMonthOrYear + ". Date can only be added by Day/Month/Year");
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Browser.getLogger().info("Date parse error");
//            fail("Date parse error...");
//            return "Parse Exception";
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//        return "Parse Exception";
//    }
//
//    /******************************************************************************
//     * Description : To retrieve today's date from system
//     * Arguments : String date format , date time formatter
//     * Return Value : String
//     ********************************************************************************/
//
//    public String fetchCurrentSystemDate(String DateFormat) {
//        DateTimeFormatter dateTimeFormat;
//        switch (DateFormat) {
//            case "yyyy-MM-dd":
//                dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                break;
//            case "dd-MM-yyyy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                break;
//            case "MM-dd-yyyy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//                break;
//            case "MM-dd-yy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yy");
//                break;
//            case "dd-MM-yy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yy");
//                break;
//            case "yyyy/MM/dd":
//                dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//                break;
//            case "dd/MM/yyyy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                break;
//            case "MM/dd/yyyy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//                break;
//            case "MM/dd/yy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
//                break;
//            case "dd/MM/yy":
//                dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yy");
//                break;
//            default:
//                Browser.getLogger().info("Date format undefined");
//                fail("UnSupported Date Format");
//                return "UnSupported Date Format";
//        }
//        try {
//            LocalDateTime now = LocalDateTime.now();
//            return dateTimeFormat.format(now);
//        } catch (DateTimeException e) {
//            fail("Unable to fetch system date");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//        return "Error";
//    }
//
//    /******************************************************************************
//     * Description : To upload file
//     * Arguments : WebElement , String filePath , WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    @Step("Upload the file : {1} to the link : {0}")
//    public void uploadFile(WebElement uploadLink, String filePath, WebDriver driver) {
//        WebElement wE = verifyObjectExistence(uploadLink, driver);
//        try {
//            wE.sendKeys(Browser.uploadPath + filePath);
//            Browser.getLogger().info("File Upload successful : " + filePath);
//        } catch (Exception e1) {
//            Browser.getLogger().fatal("Upload failed : " + filePath);
//            fail("Upload file - failed");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To create a temporary copy of file
//     * Arguments : String masterFileName
//     * Return Value : String
//     ********************************************************************************/
//
//    public String createCopyOfFile(String MasterFileName) {
//        File srcFile = new File(Browser.uploadPath + MasterFileName);
//        String tempFileName = MasterFileName.split("\\.")[0] + "_Temp." + MasterFileName.split("\\.")[1];
//        File destFile = new File(Browser.uploadPath + tempFileName);
//        try {
//            copyFileUsingStream(srcFile, destFile);
//            Browser.getLogger().info("Created TEMP copy of the file : " + MasterFileName);
//        } catch (IOException e1) {
//            Browser.getLogger().info("Error copying the file : " + MasterFileName);
//            fail("Error while creating copy of file template");
//        } catch (NullPointerException e) {
//            Browser.getLogger().info("Cannot copy file (Null Pointer Exception)");
//            fail("Cannot copy file (Null Pointer Exception)");
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//
//        }
//        return tempFileName;
//    }
//
//
//    /******************************************************************************
//     * Description : To replace text in a text file(csv or text file)
//     * Arguments :String tempFileName, searchString, replaceString
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void replaceText(String tempFileName, String SearchString, String replaceString) {
//        String destinationFileName = Browser.uploadPath + tempFileName;
//        try {
//            FileReader fr = new FileReader(destinationFileName);
//            String s;
//            String totalStr = "";
//            try (BufferedReader br = new BufferedReader(fr)) {
//
//                while ((s = br.readLine()) != null) {
//                    totalStr += s + System.getProperty("line.separator");
//                }
//                totalStr = totalStr.replaceAll(SearchString, replaceString);
//                if (totalStr.length() == 0) {
//                    Browser.getLogger().info("File is empty : " + destinationFileName);
//                    fail("File is empty : " + destinationFileName);
//                } else {
//                    FileWriter fw = new FileWriter(destinationFileName);
//                    fw.write(totalStr);
//                    fw.close();
//                    Browser.getLogger().info("Replaced text successfully");
//                }
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Error while replacing text in the file template : " + destinationFileName);
//            fail("Error while replacing text in the file template : " + destinationFileName);
//        }
//    }
//
//    /******************************************************************************
//     * Description : To attach text file to result
//     * Arguments : String tempFileName
//     * Return Value : String
//     ********************************************************************************/
//
//    @Attachment(value = "{0}")
//    public String attachTextFileToResult(String tempFileName) {
//        String destinationFileName = Browser.uploadPath + tempFileName;
//        try {
//            FileReader fr = new FileReader(destinationFileName);
//            String s;
//            String totalStr = "";
//            try (BufferedReader br = new BufferedReader(fr)) {
//
//                while ((s = br.readLine()) != null) {
//                    totalStr += s + System.getProperty("line.separator");
//                }
//            }
//            Browser.getLogger().info("Attaching the text file to result : " + tempFileName);
//            return totalStr;
//        } catch (Exception e) {
//            Browser.getLogger().info("Error while attaching the text file : " + tempFileName);
//            fail("Error while attaching the text file : " + tempFileName);
//            return "error";
//        }
//    }
//
//    /******************************************************************************
//     * Description : To be used to delete the temporary file
//     * Arguments : String file
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void deleteFile(String FileNameToDelete) {
//        try {
//            Boolean fileDeleted=Files.deleteIfExists(Paths.get(FileNameToDelete));
//            Browser.getLogger().info("File: "+FileNameToDelete+" deletion : " + fileDeleted);
//        } catch (NoSuchFileException e) {
//            Browser.getLogger().info("No Such File : " + FileNameToDelete);
//            fail("No Such File : " + FileNameToDelete);
//        } catch (IOException e) {
//            Browser.getLogger().info("No permission to delete the file : " + FileNameToDelete);
//            fail("No permission to delete the file : " + FileNameToDelete);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To copy file using Stream
//     * Arguments : src file,destination file
//     * Return Value : NA
//     ********************************************************************************/
//
//    private void copyFileUsingStream(File source, File dest) throws IOException {
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            is = new FileInputStream(source);
//            os = new FileOutputStream(dest);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = is.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//        } finally {
//            is.close();
//            os.close();
//        }
//    }
//
//    /******************************************************************************
//     * Description : To verify that the file is present in download path
//     * Arguments : String fileName
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void downloadFile(String downloadedFileName) {
//        waitForSeconds(30); // wait until the file is downloaded
//        try {
//            File file = new File(Browser.downloadPath + downloadedFileName);
//            if (file.exists()) {
//                Browser.getLogger().info("File Downloaded successfully" + Browser.downloadPath + downloadedFileName);
//            } else {
//                Browser.getLogger().info("File not downloaded" + Browser.downloadPath + downloadedFileName);
//                fail("File not downloaded" + Browser.downloadPath + downloadedFileName);
//            }
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To generate random number from time stamp
//     * Arguments : NA
//     * Return Value : String-12 digit number(format yyMMddHHmmss)
//     ********************************************************************************/
//
//    public String generateRandomNumberFromTimeStamp() {
//        waitForSeconds(1);
//        return (new SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date())); // 12 digits
//    }
//
    /******************************************************************************
     * Description : To generate time stamp
     * Arguments : NA
     * Return Value : String-12 digit number(format yy/mm/dd/hour:minutes:seconds)
     ********************************************************************************/

    public static String generateTimeStamp() {
        return (new SimpleDateFormat("yy/MM/dd/HH:mm:ss").format(new java.util.Date())); // 12 digits
    }
//
//    /******************************************************************************
//     * Description : To verify default selection in a list
//     * Arguments : WebElement, String text, String elementInfo
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void verifySelectDefaultSelection(WebElement wElement, String textToVerify, String elementInfo) {
//        try {
//            Select sElement = new Select(wElement);
//            if (sElement.getFirstSelectedOption().getText().trim().equalsIgnoreCase(textToVerify)) {
//                Browser.getLogger()
//                        .info("Select validation success : " + sElement.getFirstSelectedOption().getText().trim());
//                return;
//            } else {
//                Browser.getLogger().info("Select comparison failed for : " + wElement + " Expected value :"
//                        + textToVerify + " Actual :" + sElement.getFirstSelectedOption().getText());
//                fail("Select comparison failed for : " + wElement + " Expected value :" + textToVerify + " Actual :"
//                        + sElement.getFirstSelectedOption().getText());
//            }
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("The Select not available/displayed : " + elementInfo);
//            fail("The Select not available/displayed : " + elementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To fetch default selection in a list
//     * Arguments : WebElement, String elementInfo
//     * Return Value : String
//     ********************************************************************************/
//
//    public String fetchSelectDefaultSelection(WebElement wElement, String elementInfo) {
//        try {
//            Select sElement = new Select(wElement);
//            return sElement.getFirstSelectedOption().getText();
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("The Select not available/displayed : " + wElement);
//            fail("The Select not available/displayed : " + wElement);
//            return "Not Found";
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//    }
//
//    /******************************************************************************
//     * Description : To reset implicit wait time
//     * Arguments : WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void resetImplicitWaitTime(WebDriver driver) {
//        driver.manage().timeouts().implicitlyWait(Constants.default_Timeout, TimeUnit.SECONDS);
//        Browser.getLogger().info("Implicit wait reset back to : " + Constants.default_Timeout);
//    }
//
    /******************************************************************************
     * Description : To click using JavaScriptExecutor
     * Arguments : WebDriver instance, String xpath
     * Return Value : NA
     ********************************************************************************/

    public void clickElementJScript(WebDriver driver, String myElementXpath) {
        WebElement myElement = null;
        try {
            myElement = driver.findElement(By.xpath(myElementXpath));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView()", myElement);
            js.executeScript("arguments[0].click();", myElement);
            Browser.getLogger().info("Clicked using jScript : " + myElement);
        } catch (NoSuchElementException e) {
                fail("Element to click doesn't exist : " + myElement);
        } catch (org.openqa.selenium.StaleElementReferenceException e1) {
            waitForSeconds(3);
            clickElementJScript(driver, myElementXpath);
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            fail("Generic Exception");
        }
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

    /******************************************************************************
     * Description : To wait until the object loads
     * Arguments : WebDriver instance, String xpath, int wait
     * Return Value : NA
     ********************************************************************************/

    public void waitUntilObjectLoads(WebDriver driver, String wElementXpath, int waitTime) {
        int i = 0;
        while (i < waitTime) {
            if (waitUntilObjectLoadsSub(driver, wElementXpath, waitTime)) {
                Browser.getLogger().info("Element found/loaded : " + wElementXpath);
                return;
            }
            i++;
        }
        Browser.getLogger().info("Element not found/loaded : " + wElementXpath);
    }

    /******************************************************************************
     * Description : To wait until the object loads
     * Arguments : WebDriver instance, String xpath, int wait
     * Return Value : boolean
     ********************************************************************************/

    private boolean waitUntilObjectLoadsSub(WebDriver driver, String wElementXpath, int waitTime) {
        WebElement wElement = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        try {
            wElement = driver.findElement(By.xpath(wElementXpath));
            wait.until(ExpectedConditions.visibilityOf(wElement));
            return true;
        } catch (TimeoutException e) {
            Browser.getLogger().info("Element not found:" + wElementXpath);
            waitForSeconds(Constants.default_Timeout);
            return false;
        } catch (NoSuchElementException e) {
            Browser.getLogger().info("Element not found:" + wElementXpath);
            waitForSeconds(Constants.default_Timeout);
            return false;
        } catch (StaleElementReferenceException d) {
            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + wElementXpath);
            waitUntilObjectLoadsSub(driver, wElementXpath, waitTime);
            return false;
        } catch (WebDriverException f) {
            f.printStackTrace();
            Browser.getLogger()
                    .info("Element not found (WebDriver Exception - Appium):" + wElementXpath + "... Wait...!");
            waitForSeconds(Constants.default_Timeout);
            return false;
        } catch (Exception e) {
            Browser.getLogger().info("Generic Exception");
            return false;
        }
    }
//
//    /******************************************************************************
//     * Description : To wait until frame is available and switch
//     * Arguments : WebDriver instance, String xpath, int wait
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void waitUntilFrameIsAvailableAndSwitch(WebDriver driver, WebElement wElement, int waitTime,
//                                                   String elementInfo) {
//        WebElement wE = null;
//        try {
//            waitUntilObjectLoads(driver, wElement, waitTime, elementInfo);
//            waitForSeconds(5);
//            wE = verifyObjectExistence(wElement, driver);
//            driver.switchTo().frame(wE);
//            Browser.getLogger().info("Switched frame successfully...");
//            waitForSeconds(5);
//        } catch (NoSuchFrameException e) {
//            fail("Unable to switch to the frame : " + elementInfo);
//        } catch (TimeoutException e) {
//            fail("Unable to switch to the frame : " + elementInfo);
//        } catch (StaleElementReferenceException e) {
//            waitForSeconds(5);
//            Browser.getLogger().info("Element is stale..! Reinitialising..!" + " : " + wE.toString());
//            driver.switchTo().defaultContent();
//            WebElement tempElement = reinitialiseWebElement(wE, driver);
//            waitUntilFrameIsAvailableAndSwitch(driver, tempElement, waitTime, elementInfo);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            fail("Generic Exception");
//        }
//    }
//
//    /******************************************************************************
//     * Description : To get Cookie value for the Cookie name given
//     * Arguments :WebDriver instance, String Cookie name
//     * Return Value : String
//     ********************************************************************************/
//
//	public String getCookieValue(WebDriver driver, String cookieName) {
//		try {
//			return driver.manage().getCookieNamed(cookieName).getValue();
//		} catch (NullPointerException e) {
//			Browser.getLogger().info("No Cookie available with name :(Null pointer exception) : " + cookieName);
//			return "No Cookie available";
//		} catch (Exception e) {
//			Browser.getLogger().info("Generic Exception");
//			return "Error";
//		}
//	}
//
//    /******************************************************************************
//     * Description : To wait until an object disappears
//     * Arguments : WebElement,String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean waitUntilObjectDisappears(WebElement wElement, String ElementInfo) {
//        int noOfAttempts = 0;
//        try {
//            while (wElement.isDisplayed() && noOfAttempts < 5) {
//                Browser.getLogger().info("Waiting for element to disappear : " + ElementInfo + "   wait time : "
//                        + Constants.default_Timeout);
//                waitForSeconds(Constants.default_Timeout);
//                noOfAttempts++;
//
//            }
//            Browser.getLogger().info("Element not disappeared : " + ElementInfo);
//            return false;
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("Element disappeared : " + ElementInfo);
//            return true;
//        } catch (WebDriverException e) {
//            Browser.getLogger().info("Element disappeared : " + ElementInfo);
//            return true;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return true;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To wait until an object disappears
//     * Arguments : WebDriver instance, WebElement, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean waitUntilObjectDisappears(WebDriver driver, String wElementXpath, String ElementInfo) {
//        try {
//            int i = 0;
//            while (driver.findElement(By.xpath(wElementXpath)).isDisplayed() && i < 2) {
//                Browser.getLogger().info("Waiting for the element to disappear : " + ElementInfo + "  wait time : "
//                        + Constants.default_Timeout);
//                i++;
//                waitForSeconds(Constants.default_Timeout);
//            }
//            if (i >= 2) {
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return true;
//        }
//    }
//
//    /******************************************************************************
//     * Description : To find element and switch frame
//     * Arguments : WebDriver instance, String element, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean findElementAndSwitchFrame(WebDriver driver, String ElementToCheck, String elementInfo) {
//        waitUntilPageLoads(driver,5);
//        driver.switchTo().defaultContent();
//        if (verifyObjectIsDisplayed(ElementToCheck, driver)) {
//            Browser.getLogger()
//                    .info("Element found in the default content. Not required to switch frame..   : " + ElementToCheck);
//            return true;
//        }
//        if (!findSubFrameAndSwitch(driver, ElementToCheck, elementInfo)) {
//            Browser.getLogger().info("Element not found..Unable to switch window   :" + ElementToCheck);
//            fail("Element not found..Unable to switch window    :" + ElementToCheck);
//            return false;
//        }
//        Browser.getLogger().info("Switched to iframe");
//        return true;
//    }
//
//    /******************************************************************************
//     * Description : To find element and switch frame
//     * Arguments : WebDriver instance, String element
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean findElementAndSwitchFrame(WebDriver driver, String ElementToCheck) {
//        waitUntilPageLoads(driver,5);
//        driver.switchTo().defaultContent();
//        if (verifyObjectIsDisplayed(ElementToCheck, driver)) {
//            Browser.getLogger()
//                    .info("Element found in the default content. Not required to switch frame..   : " + ElementToCheck);
//            return true;
//        }
//        if (!findSubFrameAndSwitch(driver, ElementToCheck, "WebElement")) {
//            Browser.getLogger().info("Element not found..Unable to switch window :" + ElementToCheck);
//            return false;
//        }
//        Browser.getLogger().info("Switched to iframe");
//        return true;
//    }
//
//    /******************************************************************************
//     * Description : To switch to default content
//     * Arguments : WebDriver instance
//     * Return Value : NA
//     ********************************************************************************/
//
//    public void switchToDefaultContent(WebDriver driver) {
//        driver.switchTo().defaultContent();
//        Browser.getLogger().info("Switched to default content in the web page");
//    }
//
//    /******************************************************************************
//     * Description : To find sub frame and switch
//     * Arguments : WebDriver instance, String element, String elementInfo
//     * Return Value : boolean
//     ********************************************************************************/
//
//    public boolean findSubFrameAndSwitch(WebDriver driver, String ElementToCheck, String elementInfo) {
//        WebElement tempElement = null;
//        String tempId;
//        String tempName;
//        int iterator;
//        boolean staleElementFlag;
//        List<WebElement> myFrames;
//        boolean switchedFrame = false;
//        myFrames = driver.findElements(By.tagName("iframe"));
//        Browser.getLogger().info("No Of frames : " + myFrames.size());
//        iterator = 0;
//        while (iterator < myFrames.size()) {
//            staleElementFlag = false;
//            while (staleElementFlag == false) {
//                try {
//                    tempId = myFrames.get(iterator).getAttribute("id");
//                    tempName = myFrames.get(iterator).getAttribute("name");
//                    if (tempId.equalsIgnoreCase("") || tempName.equalsIgnoreCase("")) {
//                        tempElement = myFrames.get(iterator);
//                    } else {
//                        tempElement = driver
//                                .findElement(By.xpath("//iframe[@id='" + tempId + "' and @name='" + tempName + "']"));
//                        Browser.getLogger().info(
//                                "Manipulated xpath : " + "//iframe[@id='" + tempId + "' and @name='" + tempName + "']");
//                    }
//                    staleElementFlag = true;
//                } catch (StaleElementReferenceException e) {
//                    Browser.getLogger().info("Stale element exception");
//                    if (switchedFrame == true) {
//                        return true;
//                    }
//                    myFrames = driver.findElements(By.tagName("iframe"));
//                } catch (IndexOutOfBoundsException g) {
//                    iterator = myFrames.size();
//                    continue;
//                }
//            }
//            waitUntilFrameIsAvailableAndSwitch(driver, tempElement, 20, elementInfo);
//            resetImplicitWaitTime(driver, 0);
//            if (verifyObjectIsDisplayed(ElementToCheck, driver)) {
//                Browser.getLogger().info("Element found and Switched the frame successfully..");
//                switchedFrame = true;
//                resetImplicitWaitTime(driver);
//                return true;
//            } else {
//                switchedFrame = findSubFrameAndSwitch(driver, ElementToCheck, elementInfo);
//            }
//            iterator++;
//        }
//        if (switchedFrame) {
//            return true;
//        }
//        driver.switchTo().parentFrame();
//        return false;
//    }
//
//    /******************************************************************************
//     * Description : To retrieve text from matching xpath
//     * Arguments : String xpath, WebDriver instance
//     * Return Value : String
//     ********************************************************************************/
//
//    public String retrieveAllTextFromMatchingXpath(String ElementXpath, WebDriver driver) {
//        int noOfElements;
//        try {
//            noOfElements = driver.findElements(By.xpath(ElementXpath)).size();
//        } catch (NoSuchElementException e) {
//            Browser.getLogger().info("No elements found matching the xpath : " + ElementXpath);
//            return "NOT FOUND";
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return "Error";
//        }
//        int i = 1;
//        String textToReturn = "";
//        while (i <= noOfElements) {
//            textToReturn = textToReturn + driver.findElement(By.xpath(ElementXpath + "[" + i + "]")).getText().trim();
//            i++;
//        }
//        return textToReturn.trim();
//    }
//
//    /******************************************************************************
//     * Description : To verify the object in Web page and to fix xpath using AI
//     * Arguments : WebElement, WebDriver instance
//     * Return Value : WebElement
//     ********************************************************************************/
//
//    public WebElement verifyObjectExistence(WebElement wElement, WebDriver driver) {
//        try {
//            waitForSeconds(3);
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                    getAbsoluteXPath(wElement,driver),isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Element not found");
//                fail("Element not found");
//            }
//            isRecursiveCall = true;
//            return verifyObjectExistence(aiElement, driver);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            Browser.getLogger().info("Element not found");
//            fail("Element not found");
//        }
//        return (wElement);
//    }
//
//    /******************************************************************************
//     * Description : To verify the object in Web page and to fix xpath using AI
//     * Arguments : WebElement, WebDriver instance
//     * Return Value : element
//     ********************************************************************************/
//
//    public WebElement verifyObjectExistenceNoAssertion(WebElement wElement, WebDriver driver) {
//        try {
//            waitForSeconds(3);
//            String OuterHTML = wElement.getAttribute("outerHTML");
//            aiInventoryUpdate.updateAILogOnSuccessfulAction(generateXpathFromWebElement(wElement), OuterHTML,
//                    getAbsoluteXPath(wElement,driver),isRecursiveCall);
//            isRecursiveCall = false;
//        } catch (NoSuchElementException e) {
//            WebElement aiElement = aiInventoryUpdate.xpathAIFix(driver, wElement, this);
//            if (aiElement == null) {
//                Browser.getLogger().info("Element not found");
//                return null;
//            }
//            isRecursiveCall = true;
//            return verifyObjectExistenceNoAssertion(aiElement, driver);
//        } catch (Exception e) {
//            Browser.getLogger().info("Generic Exception");
//            return null;
//        }
//        return (wElement);
//    }
//
//    /******************************************************************
//     * Description : To convert a Json object to Map
//     * Arguments : JSONObject
//     * Return Value : HashMap<String, String>
//     * Author: Nikhil David
//     ********************************************************************/
//
//    public HashMap<String, String> convertJsonToMap(JSONObject Obj) {
//        HashMap<String, String> JSONConvertedToMap = new HashMap<String, String>();
//        Set<String> keys = Obj.keySet();
//        Iterator<String> itr = keys.iterator();
//        try {
//            assertTrue(itr.hasNext());
//        } catch (AssertionError e) {
//            Browser.getLogger().info("Json file is empty");
//            fail();
//        }
//        while (itr.hasNext()) {
//            String currentKey = itr.next();
//            JSONConvertedToMap.put(currentKey, (String) Obj.get(currentKey));
//        }
//        return JSONConvertedToMap;
//    }
//
//    /******************************************************************
//     * Description : To create Json object from Json file
//     * Arguments : String FilePath, String FileName
//     * Return Value : JSONObject
//     * Author: Nikhil David
//     ********************************************************************/
//
//    public JSONObject readFileAndCreateJsonObject(String FilePath, String FileName) throws IOException {
//        JSONObject JObj = null;
//        String FileContent = null;
//        String extension = FilenameUtils.getExtension(FileName);
//        File dir = new File(FilePath);
//        try {
//            assertTrue(dir.isDirectory());
//        } catch (AssertionError e) {
//            Browser.getLogger().info("Input directory does not exist");
//            fail();        }
//        try {
//            assertTrue(extension.equalsIgnoreCase("json"));
//            FileContent = new String(Files.readAllBytes(Paths.get(FilePath + FileName)));
//        } catch (AssertionError e) {
//            e.printStackTrace();
//            Browser.getLogger().info("File'" + FileName + "' does not have JSON extension");
//            fail();
//        } catch (java.nio.file.NoSuchFileException e) {
//            e.printStackTrace();
//            Browser.getLogger().info("File '" + FileName + "'is not found in path '" + FilePath + "'");
//            fail();
//        }
//        try {
//            JObj = new JSONObject(FileContent);
//        } catch (org.json.JSONException e) {
//            Browser.getLogger().info(e.getStackTrace());
//            fail();
//        }
//        return JObj;
//    }
//    /******************************************************************
//     * Description : To get the absolute xpath of an element
//     * Arguments : Webelement, driver
//     * Return Value : String
//     * Author: Darsana
//     ********************************************************************/
//    public String getAbsoluteXPath(WebElement ele, WebDriver driver)
//    {
//        return (String) ((JavascriptExecutor) driver).executeScript(
//                "function absoluteXPath(element) {"+
//                        "var comp, comps = [];"+
//                        "var parent = null;"+
//                        "var xpath = '';"+
//                        "var getPos = function(element) {"+
//                        "var position = 1, curNode;"+
//                        "if (element.nodeType == Node.ATTRIBUTE_NODE) {"+
//                        "return null;"+
//                        "}"+
//                        "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"+
//                        "if (curNode.nodeName == element.nodeName) {"+
//                        "++position;"+
//                        "}"+
//                        "}"+
//                        "return position;"+
//                        "};"+
//                        "if (element instanceof Document) {"+
//                        "return '/';"+
//                        "}"+
//                        "for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"+
//                        "comp = comps[comps.length] = {};"+
//                        "switch (element.nodeType) {"+
//                        "case Node.TEXT_NODE:"+
//                        "comp.name = 'text()';"+
//                        "break;"+
//                        "case Node.ATTRIBUTE_NODE:"+
//                        "comp.name = '@' + element.nodeName;"+
//                        "break;"+
//                        "case Node.PROCESSING_INSTRUCTION_NODE:"+
//                        "comp.name = 'processing-instruction()';"+
//                        "break;"+
//                        "case Node.COMMENT_NODE:"+
//                        "comp.name = 'comment()';"+
//                        "break;"+
//                        "case Node.ELEMENT_NODE:"+
//                        "comp.name = element.nodeName;"+
//                        "break;"+
//                        "}"+
//                        "comp.position = getPos(element);"+
//                        "}"+
//                        "for (var i = comps.length - 1; i >= 0; i--) {"+
//                        "comp = comps[i];"+
//                        "xpath += '/' + comp.name.toLowerCase();"+
//                        "if (comp.position !== null) {"+
//                        "xpath += '[' + comp.position + ']';"+
//                        "}"+
//                        "}"+
//                        "return xpath;"+
//                        "} return absoluteXPath(arguments[0]);", ele);
//    }
//    /******************************************************************
//     * Description : To generate test script from plain english steps using chatGPT
//     * Arguments : String openai token/Key, String manual validation steps
//     * Return Value : NA
//     * Author: Rahul Nair
//     ********************************************************************/
//    public void generateChatGPTScript(String apiKey,String steps,String codeType,String numResponses,String temperature) {
//        String prompt = "Generate a "+codeType+" code to automate the steps: " + steps + "using Selenium java 3.141.59 in Chrome browser";
//        Response response;
//        String scriptText=null;
//        try{
//            response = RestAssured.given()
//                .baseUri("https://api.openai.com/v1/")
//                .basePath("/engines/text-davinci-003/completions")
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + apiKey)
//                .body("{\"prompt\": \"" + prompt + "\", \"max_tokens\": 1024, \"n\": " + numResponses + ", \"temperature\": " + temperature + "}")
//                .post();
//            if (response.getStatusCode() == 200) {
//                JsonPath jsonPath = response.jsonPath();
//                List<Map<String, Object>> choices = jsonPath.getList("choices");
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < choices.size(); i++) {
//                    sb.append(choices.get(i).get("text"));
//                }
//                scriptText = sb.toString();
//                System.out.println("Test Script generated from chat GPT is : " + scriptText);
//            } else{
//                fail("Failed to generate response from chatGPT: " + response.getBody().asString());
//            }
//        }catch(Exception e) {
//            fail("Failed to generate test scripts from steps using chatGPT");
//        }
//        writeToFile(Browser.Output+"chatGPTTestScript.java",scriptText);
//    }
//    /******************************************************************
//     * Description : To write text to any file
//     * Arguments : String filename, String text
//     * Return Value : NA
//     * Author: Darsana
//     ********************************************************************/
//    public void writeToFile(String filename, String textToWrite)
//    {
//        try {
//            FileWriter file = new FileWriter(filename);
//            file.write(textToWrite);
//            Browser.getLogger().info("Written text to the file : " + filename);
//            file.close();
//        } catch (IOException e) {
//            fail("Unable to write to the file : " + filename);
//        }
//    }
//    /******************************************************************************
//     * Description : To convert xpath to css
//     * Arguments : String xpath
//     * Return Value : String css
//     * Author: Karthick
//     * No/Limited Support: Xpath with special patterns like svg tag, xpath with text, xpath with index
//     ******************************************************************************/
//    public String xpathToCssselector(String xpath) {
//        StringBuilder cssSelector = new StringBuilder();
//        String[] parts;
//        if ((xpath.contains("@src")) || (xpath.contains("@href"))){
//            parts = xpath.split("^//");}
//        else{
//            parts = xpath.split("/");
//        }
//        for (String part : parts) {
//            if (!part.isEmpty()) {
//                String[] attributes = part.split("\\[");
//                String tagName = attributes[0].replace("@", "");
//                cssSelector.append(tagName);
//                for (int i = 1; i < attributes.length; i++) {
//                    String attribute = attributes[i].replace("]", "");
//                    String[] keyValue = attribute.split("=");
//                    String keyValue1 = attribute.split("=",2)[1];
//                    String attributeName = keyValue[0].replace("@", "");
//                    String attributeValue = keyValue1.replace("'", "'");
//                    cssSelector.append("[").append(attributeName).append("=").append(attributeValue.trim()).append("]");
//                }
//            }
//        }
//        return cssSelector.toString();
//    }
//    /******************************************************************************
//     * Description : To read text from pdf
//     * Arguments : String pdf url
//     * Return Value : String pdf text
//     * Author: Rishana MS
//     ******************************************************************************/
//    public String getPDFText(String url) {
//        URL pdfURL;
//        try {
//            pdfURL = new URL(url);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        InputStream ip;
//        try {
//            ip = (pdfURL.openStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        BufferedInputStream bf = new BufferedInputStream(ip);
//        PDDocument pdDocument;
//        try {
//            pdDocument = PDDocument.load(bf);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        int pageCount = pdDocument.getNumberOfPages();
//        System.out.println("pdf page count = "+pageCount);
//        try{
//            PDFTextStripper pdfStripper=new PDFTextStripper();
//            String pdfText=pdfStripper.getText(pdDocument);
//            pdDocument.close();
//            return pdfText;
//        }catch(IOException e){
//            fail("Reading PDF text failed");
//            return null;
//        }
//    }
}