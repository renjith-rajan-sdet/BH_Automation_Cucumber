package test.pages;

import com.Base.Utilities.GeneralUtilities;
import com.Base.Utilities.ProcessDataSheet;
import com.Runtime.utilities.PageObjectManager;
import com.Runtime.utilities.RuntimeEnvironment;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    private PageObjectManager pages;
    private ProcessDataSheet testData;
    private GeneralUtilities generalUtilities;
    Scenario scn;
    RuntimeEnvironment runtime;

    public void intitialiseObjects() {
        PageFactory.initElements(driver, this);
    }

    public HomePage(RuntimeEnvironment runtime) {
        this.testData = runtime.testData;
        this.driver = runtime.driver;
        this.scn = runtime.scn;
        this.pages = runtime.getPage(runtime);
        this.testData = runtime.testData;
        this.runtime = runtime;
        this.generalUtilities = new GeneralUtilities(runtime);
        intitialiseObjects();
    }

    @FindBy(xpath = "(//img[@src='https://m.media-amazon.com/images/I/815XJU1dEKL._AC_UL320_.jpg'])[1]")
    WebElement imgWallpaper;
    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    WebElement btnAddToCart;
    @FindBy(xpath = "//a[@id='nav-cart']")
    WebElement btnCart;
    @FindBy(xpath = "//img[@class='sc-product-image']")
    WebElement imgWolpinWallpaper;

    public void searchForWallPaper() {
//        generalReusableFunctions.setText("//input[@id=\"twotabsearchtextbox\"]", "WallPaper", driver);
//        generalReusableFunctions.clickElement("//input[@id=\"nav-search-submit-button\"]", driver, "Enter");
        System.out.println("step executed successfully");

    }


}
