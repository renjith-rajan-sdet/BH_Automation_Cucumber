package test.pages;

import com.Base.Utilities.Browser;
import com.Base.Utilities.GeneralUtilities;
import com.Runtime.utilities.PageObjectManager;
import com.Runtime.utilities.RuntimeEnvironment;
import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.fail;

public class SearchBrightHorisons_Screen {
    private WebDriver driver;
    private PageObjectManager pages;
    private GeneralUtilities generalUtilities;
    Scenario scn;
    RuntimeEnvironment runtime;
    String searchtext;


    public SearchBrightHorisons_Screen(RuntimeEnvironment runtime) {
        this.driver = runtime.driver;
        this.scn = runtime.scn;
        this.pages = runtime.getPage(runtime);
        this.runtime = runtime;
        this.generalUtilities = new GeneralUtilities(runtime);
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH,   xpath = "//nav[@id='subnav-search-desktop-top']//input[@id='search-field']")
    WebElement searchBox;
    @FindBy(how = How.XPATH,   xpath = "//nav[@id='subnav-search-desktop-top']//button[text()='Search']")
    WebElement searchButton;
    @FindBy(how = How.XPATH,   xpath = "//nav[@id='subnav-search-desktop-top']//p[text()='Search Bright Horizons']")
    WebElement searchHeader;

    String searchResultsResourceHeader = "//div[@class='results container']//h3";
    @FindBy(how = How.XPATH,   xpath = "//div[@class='results container']//h3")
    WebElement searchResultsResourceHeader_WElement;

    @Step("search the text '{0}' in the search box and fetch results")
    public void Enter_SearchText_PressEnter(String searchText)
    {
        generalUtilities.setText(searchBox,searchText,driver,"Search box under 'Search Bright Horizons' screen");
        generalUtilities.clickElement(driver,searchButton,"Search button under 'Search Bright Horizons' screen");
        this.searchtext = searchText;
        generalUtilities.waitUntilPageLoads(driver,10);
        generalUtilities.waitUntilObjectLoads(driver, searchResultsResourceHeader_WElement,10,"Search results");
        if(generalUtilities.verifyObjectIsDisplayed(searchResultsResourceHeader_WElement,driver))
        {
            Browser.getLogger().info("Search results loaded successfully...!");
            scn.log("Search results loaded successfully...!");
            generalUtilities.takeScreenSnap("BrightHorizons_SearchResults",scn,driver);
        }
        else {
            scn.log("Search results not loaded for the search text : "+searchtext+"");
            Browser.getLogger().error("Search results not loaded for the search text : "+searchtext+"");
            fail("Search results not loaded for the search text : "+searchtext+"");

        }

    }

    @Step("validate that the first search result is an exact match of the searched text")
    public void validate_first_SearchResult_as_ExactMatch()
    {
        String firstSearchResult = driver.findElements(By.xpath(searchResultsResourceHeader)).get(0).getText();
        if(firstSearchResult.equals(searchtext))
        {
            Browser.getLogger().info("Validation is successfull. First search result is an exact match of the search criteria provided : "+searchtext+"");
            scn.log("Validation is successfull. First search result is an exact match of the search criteria provided : "+searchtext+"");
            generalUtilities.takeScreenSnap_FullScroll("BrightHorizons_SearchResults",scn,driver);


        }
        else {
            Browser.getLogger().error("First search result is not an exact match of the search criteria provided : "
                    +searchtext+" Actual text : "+firstSearchResult+"");
            scn.log("First search result is not an exact match of the search criteria provided : "
                    +searchtext+" Actual text : "+firstSearchResult+"");
            generalUtilities.takeScreenSnap_FullScroll("BrightHorizons_SearchResults_FAIL",scn,driver);
            fail("First search result is not an exact match of the search criteria provided : "
                    +searchtext+" Actual text : "+firstSearchResult+"");

        }
    }

}
