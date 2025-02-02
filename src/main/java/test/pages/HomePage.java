package test.pages;

import com.Base.Utilities.GeneralUtilities;
import com.Runtime.utilities.PageObjectManager;
import com.Runtime.utilities.RuntimeEnvironment;
import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    private PageObjectManager pages;
    private GeneralUtilities generalUtilities;
    Scenario scn;
    RuntimeEnvironment runtime;


    public HomePage(RuntimeEnvironment runtime) {
        this.driver = runtime.driver;
        this.scn = runtime.scn;
        this.pages = runtime.getPage(runtime);
        this.runtime = runtime;
        this.generalUtilities = new GeneralUtilities(runtime);
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH,   xpath = "//img[@alt='Top Navigation Logo']/parent::a/parent::div[@class='visible-lg-inline']/a")
    WebElement LogoImage;
    @FindBy(how = How.XPATH,   xpath = "//button[text()='Manage Cookies']/..//button[text()='Accept All']")
    WebElement Cookies_AcceptALlButton;

    @Step("Verify that the Bright Horizons home page is loaded successfully.")
    public void validateHomePage_Loaded()
    {
        generalUtilities.waitUntilPageLoads(driver,10);
        generalUtilities.waitUntilObjectLoads(driver,LogoImage,10,"Logo Image on the left right corner of the Bright Horizons Home page");
        Assert.assertTrue("Home page load failure....! Unable to validate the Logo Image on the left right corner of the Bright Horizons Home page"
                ,generalUtilities.verifyObjectIsDisplayed(LogoImage,driver));
        if(generalUtilities.verifyObjectIsDisplayed(Cookies_AcceptALlButton,driver))
        {
            generalUtilities.clickElement(driver,Cookies_AcceptALlButton,"Cookies -> Accept All button");
            generalUtilities.waitForSeconds(3);
        }
        generalUtilities.takeScreenSnap("BrightHorizons_HomePage",scn,driver);
        scn.log("Bright Horizons home page is loaded successfully.");
    }

    @FindBy(how = How.XPATH,   xpath = "//span[@class='icon-search bhc-icon-search-rounded']/parent::a[@role='button' and @aria-controls='subnav-search-desktop-top']")
    WebElement searchIcon;

    @Step("Click on the search icon on the top right corner of the page")
    public void click_OnTheSearchIcon()
    {
        generalUtilities.clickElement(driver,searchIcon,"Search icon on the top right corner of the home page");
        generalUtilities.waitUntilObjectLoads(driver,pages.get_SearchBrightHorisons_Screen().searchHeader,
                10,"Search Bright Horizons screen - including the search header, search box and search button");

    }

    public void Validate_TheSearchScreenIsLoaded()
    {
        Assert.assertTrue("Search page not loaded even after clicking on the search icon on the home screen...!",
                generalUtilities.verifyObjectIsDisplayed(pages.get_SearchBrightHorisons_Screen().searchHeader, driver));
        scn.log("Search Bright Horizons screen is loaded successfully.");
        generalUtilities.takeScreenSnap("BrightHorizons_SearchPage",scn,driver);
    }


    @FindBy(how = How.XPATH,   xpath = "//nav[@class='nav-shared txt-nav-hierarchy nav-top js-nav-shared js-nav-top']" +
            "//li[@class='nav-item displayed-desktop']/a[contains(text(),'Find a Center')]")
    WebElement FindACenter_Button;

    public void click_FindACenterButton()
    {
        generalUtilities.clickElement(driver,FindACenter_Button,"Find a Center button on the home page");
        generalUtilities.waitUntilObjectLoads(driver,pages.get_FindACenter_Page().FindACenter_Header_Text,
                10,"Find a center header on top of the 'find a center' page");

    }



}
