package test.pages;

import com.Base.Utilities.Browser;
import com.Base.Utilities.GeneralUtilities;
import com.Runtime.utilities.PageObjectManager;
import com.Runtime.utilities.RuntimeEnvironment;
import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.NoSuchElementException;

public class FindACenter_Page {
    private WebDriver driver;
    private PageObjectManager pages;
    private GeneralUtilities generalUtilities;
    Scenario scn;
    RuntimeEnvironment runtime;


    public FindACenter_Page(RuntimeEnvironment runtime) {
        this.driver = runtime.driver;
        this.scn = runtime.scn;
        this.pages = runtime.getPage(runtime);
        this.runtime = runtime;
        this.generalUtilities = new GeneralUtilities(runtime);
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH,   xpath = "//h4[contains(text(),'Find a Center')]")
    WebElement FindACenter_Header_Text;

    @Step("Verify that the find a center page is loaded successfully.")
    public void validateFindACenterPage_Loaded()
    {
        generalUtilities.waitUntilPageLoads(driver,10);
        generalUtilities.waitUntilObjectLoads(driver,FindACenter_Header_Text,10,"Find a center header on top of the 'find a center' page");
        Assert.assertTrue("Find a Center -  page load failure....! Unable to validate the header text (Find a center) on the top left of the screen"
                ,generalUtilities.verifyObjectIsDisplayed(FindACenter_Header_Text,driver));
        generalUtilities.takeScreenSnap("FindACenter_Page_Loaded",scn,driver);
        scn.log("Find a center page is loaded successfully.");
    }

    @FindBy(how = How.XPATH,   xpath = "//input[@id='addressInput']")
    WebElement AddressInput_TextBox;


    @FindBy(how = How.XPATH,   xpath = "//div/span[@class='resultsNumber']")
    WebElement NoOfCenters_Fetched;

    String searchResultsFetched_Xpath = "//div[@class='description-wrapper']";

    @Step("Search for {0} in the find a center page press ENTER key and validate that the search results are loaded..!")
    public void SearchForACenter(String searchText)
    {
        generalUtilities.setText(AddressInput_TextBox,searchText,driver,"search box in the find a center page");
        generalUtilities.waitForSeconds(5);
        generalUtilities.pressKey(AddressInput_TextBox,"ENTER",driver,"search box in the find a center page");
        generalUtilities.waitUntilPageLoads(driver,10);
        generalUtilities.waitUntilObjectLoads(driver,NoOfCenters_Fetched,10,"search results of the 'find a center' page");

        if(generalUtilities.verifyObjectIsDisplayed(NoOfCenters_Fetched,driver))
        {
            Browser.getLogger().info("Search results loaded successfully..!");
            generalUtilities.takeScreenSnap("FindACenter_SearchResults",scn,driver);
        }
        else {
            Browser.getLogger().error("Find my center : search results are not loaded..! Validation failed..!");
            generalUtilities.takeScreenSnap_FullScroll("FindACenter_SearchResults_FAIL",scn,driver);
            Assert.assertTrue("Find my center : search results are not loaded..! Validation failed..!",
                    generalUtilities.verifyObjectIsDisplayed(NoOfCenters_Fetched,driver));
        }
        Assert.assertTrue("Find my center : search results are not loaded..! Validation failed..!",
                generalUtilities.verifyObjectIsDisplayed(NoOfCenters_Fetched,driver));
    }

    @Step("validate that the number of centers fetched is matching with the number mentioned on top")
    public void validate_TheNoOfSearchResultsFetched() {
        Assert.assertTrue("Find my center : search results are not loaded..! Validation failed..!",
                generalUtilities.verifyObjectIsDisplayed(NoOfCenters_Fetched, driver));
        int NoOfCetersFetched_fromScreenMessage = 0;
        try {
            NoOfCetersFetched_fromScreenMessage = Integer.parseInt(NoOfCenters_Fetched.getText().trim());
        } catch (NumberFormatException e) {
            Assert.fail("Find my center page : No of search results not loaded or not a number...!");
        }

        int actualNoOfResultsFetched = 0;
        try {
            actualNoOfResultsFetched = driver.findElements(By.xpath(searchResultsFetched_Xpath)).size();
        } catch (NoSuchElementException e) {
            Assert.fail("Search results snippets are not displayed..!");
        }

        if (NoOfCetersFetched_fromScreenMessage == actualNoOfResultsFetched) {
            Browser.getLogger().info("No of search results displayed matches the number displayed on screen..!");
            scn.log("No of search results displayed matches the number displayed on screen..!");
            generalUtilities.takeScreenSnap_FullScroll("FindACenter_SearchResults",scn,driver);
        }
        else {
            Browser.getLogger().error("No of search results displayed NOT matching the number displayed on screen..!  Expected : " +
                    actualNoOfResultsFetched + " Actual : " + NoOfCetersFetched_fromScreenMessage);
            generalUtilities.takeScreenSnap_FullScroll("FindACenter_SearchResults_FAIL",scn,driver);
            Assert.fail("No of search results displayed NOT matching the number displayed on screen..!  Expected : " +
                    actualNoOfResultsFetched + " Actual : " + NoOfCetersFetched_fromScreenMessage);

        }
    }

    @FindBy(how = How.XPATH,   xpath = "//div[@class='mapTooltip']")
    WebElement CenterPoppup_RightSide_tooltip;
    public void click_on_the_firstSearchResult()
    {
        WebElement firstElement = null;
        try {
            firstElement = driver.findElements(By.xpath(searchResultsFetched_Xpath)).get(0);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Assert.fail("Search results snippets are not displayed..!");
        }

        generalUtilities.clickElement(driver,firstElement,"First center in the search result");
    }

    public void validate_TheMapPopup_IsDisplayed_OnTheRight()
    {
        generalUtilities.waitUntilPageLoads(driver,5);
        Assert.assertTrue("Center details are not displayed on the right side after clicking on the left side result snippet",
                generalUtilities.verifyObjectIsDisplayed(CenterPoppup_RightSide_tooltip,driver));
        generalUtilities.takeScreenSnap("FindACenter_SearchResults_tooltip_RightSide",scn,driver);
    }

    @FindBy(how = How.XPATH,   xpath = "//div[@class='mapTooltip']/span[@class='mapTooltip__headline']")
    WebElement CenterPoppup_RightSide_tooltip_Headline;
    @FindBy(how = How.XPATH,   xpath = "//div[@class='mapTooltip']//div[@class='mapTooltip__address']")
    WebElement CenterPoppup_RightSide_tooltip_Address;
    @FindBy(how = How.XPATH,   xpath = "//div[@class='mapTooltip']//div[@class='mapTooltip__contact-info']//a")
    WebElement CenterPoppup_RightSide_tooltip_PhoneNo;
    @FindBy(how = How.XPATH,   xpath = "//div[@class='mapTooltip']//span[@class='mapTooltip__hours']")
    WebElement CenterPoppup_RightSide_tooltip_Hours;

    String searchResultsFetched_CenterName_Xpath = "//div[@class='description-wrapper']//h3[@class='centerResult__name']";
    String searchResultsFetched_CenterAddress_Xpath = "//div[@class='description-wrapper']//span[@class='centerResult__address']";
    String searchResultsFetched_CenterHours_Xpath = "//span[@class='centerResult__hours']";
    String searchResultsFetched_CenterPhone_Xpath = "//a[@class='track_contact_click centerResult__phone']";

    @Step("validate that the details on the first center displayed on the left is in sync with the details on the right.")
    public void validate_TheCenterdetails_WithMapPoppup() {
        String CenterName_Left = null;
        String CenterAddress_Left = null;
        String CenterPhone_Left = null;
        String CenterHours_Left = null;
        try {
            CenterName_Left = driver.findElements(By.xpath(searchResultsFetched_CenterName_Xpath)).getFirst().getText().trim();
            CenterAddress_Left = driver.findElements(By.xpath(searchResultsFetched_CenterAddress_Xpath)).getFirst().getText().trim();
            CenterHours_Left = driver.findElements(By.xpath(searchResultsFetched_CenterHours_Xpath)).getFirst().getText().trim();
            CenterPhone_Left = driver.findElements(By.xpath(searchResultsFetched_CenterPhone_Xpath)).getFirst().getText().trim();
        } catch (NoSuchElementException e) {
            Assert.fail("Search results snippets are not displayed..!");
        }
        Assert.assertTrue("Center details are not displayed on the right side after clicking on the left side result snippet",
                generalUtilities.verifyObjectIsDisplayed(CenterPoppup_RightSide_tooltip,driver));
        generalUtilities.takeScreenSnap_FullScroll("FindACenter_SearchResults_tooltip_RightSide",scn,driver);
        Browser.getLogger().info("Captured the center details from the left snippet for validation...!");
        scn.log("Captured the center details from the left snippet for validation...!");
        Assert.assertTrue("Center details validation failed for the map popup for the field 'Center Name'. " +
                        "Expected name : " + CenterName_Left + " Actual : " + CenterPoppup_RightSide_tooltip_Headline.getText().trim(),
                CenterName_Left.equalsIgnoreCase(CenterPoppup_RightSide_tooltip_Headline.getText().trim()));

        Assert.assertTrue("Center details validation failed for the map popup for the field 'Center Address'. " +
                        "Expected : " + CenterAddress_Left + " Actual : " + CenterPoppup_RightSide_tooltip_Address.getText().trim(),
                CenterAddress_Left.equalsIgnoreCase(CenterPoppup_RightSide_tooltip_Address.getText().trim().replace("\n"," ")));
        Assert.assertTrue("Center details validation failed for the map popup for the field 'Center Phone No'. " +
                        "Expected name : " + CenterPhone_Left + " Actual : " + CenterPoppup_RightSide_tooltip_PhoneNo.getText().trim().replace("\n"," "),
                CenterPhone_Left.trim().equalsIgnoreCase(CenterPoppup_RightSide_tooltip_PhoneNo.getText().trim().replace("\n"," ")));
        Assert.assertTrue("Center details validation failed for the map popup for the field 'Center Operational Hours'. " +
                        "Expected : " + CenterHours_Left + " Actual : " + CenterPoppup_RightSide_tooltip_Hours.getText().trim().replace("\n"," "),
                CenterHours_Left.equalsIgnoreCase(CenterPoppup_RightSide_tooltip_Hours.getText().trim().replace("\n"," ")));
        Browser.getLogger().info("Validated that the center info displayed on the right side tooltip is in sync with the center info displayed on the left side snippet..!");
        scn.log("Validated that the center info displayed on the right side tooltip is in sync with the center info displayed on the left side snippet..!");
        scn.log("Details validated : " + CenterName_Left + " : "
        + CenterAddress_Left + " : " + CenterPhone_Left + " : " + CenterHours_Left);
    }

}
