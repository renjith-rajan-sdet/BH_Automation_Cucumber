package StepDefinitions;

import com.Runtime.utilities.BaseSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BrightHorizons_Sanity {

    private final BaseSteps baseSteps;

    public BrightHorizons_Sanity(BaseSteps baseSteps) {
        this.baseSteps = baseSteps;
    }

    @Then("validate that the popup text box for search is loaded on the screen")
    public void validateThatThePopupTextBoxForSearchIsLoadedOnTheScreen() {
        baseSteps.getPages().getHomePage().Validate_TheSearchScreenIsLoaded();
    }


    @And("click on - search - icon on top right of the screen")
    public void clickOnSdddearchLoupeIconOnTopRightOfTheScreen() {
        baseSteps.getPages().getHomePage().validateHomePage_Loaded();
        baseSteps.getPages().getHomePage().click_OnTheSearchIcon();
    }

    @And("enter the text {string} to the search box and click on the search button")
    public void enterTheTextToTheSearchBoxAndClickOnTheSearchButton(String searchText) {
    baseSteps.getPages().get_SearchBrightHorisons_Screen().Enter_SearchText_PressEnter(searchText);
    }

    @Then("validate that the first search result is the exact match of the searched text in the above step")
    public void validateThatTheFirstSearchResultIsTheExactMatchOfTheSearchedTextInTheAboveStep() {
        baseSteps.getPages().get_SearchBrightHorisons_Screen().validate_first_SearchResult_as_ExactMatch();
    }

    @Then("click on -Find a center- button on top of the screen")
    public void clickOnFindACenterButtonOnTopOfTheScreen() {
        baseSteps.getPages().getHomePage().validateHomePage_Loaded();
        baseSteps.getPages().getHomePage().click_FindACenterButton();
        baseSteps.getPages().get_FindACenter_Page().validateFindACenterPage_Loaded();

    }

    @And("validate that the url of the webpage opened contains {string}")
    public void validateThatTheUrlOfTheWebpageOpenedContains(String URLSubStringToValidate) {

        Assert.assertTrue("The URL does not contain the expected string : " + URLSubStringToValidate
                + "  instead the url displayed is : " + baseSteps.runtime.driver.getCurrentUrl(),
                baseSteps.generalUtilities.verifyWebPageURL(baseSteps.runtime.driver, URLSubStringToValidate));
        baseSteps.generalUtilities.recordTextData("URL Substring validation"
        ,"Validation successfull. Validated the string : ' " + URLSubStringToValidate + "' in the url : '" + baseSteps.runtime.driver.getCurrentUrl()
        , baseSteps.scn);
    }

    @And("in the location search box - type {string} and press ENTER key")
    public void inTheLocationSearchBoxTypeNewYorkAndPressENTERKey(String locationToSearch) {
        baseSteps.getPages().get_FindACenter_Page().SearchForACenter(locationToSearch);
    }

    @When("the search results are loaded, validate that the number of centers found is the same as the number of centers listed as search result")
    public void theSearchResultsAreLoadedValidateThatTheNumberOfCentersFoundIsTheSameAsTheNumberOfCentersListedAsSearchResult() {
        baseSteps.getPages().get_FindACenter_Page().validate_TheNoOfSearchResultsFetched();
    }

    @Then("click on the first center on the list")
    public void clickOnTheFirstCenterOnTheList() {
        baseSteps.getPages().get_FindACenter_Page().click_on_the_firstSearchResult();
    }


    @And("validate that a popup is opened showing the center details - on the right side")
    public void validateThatAPopupIsOpenedShowingTheCenterDetailsOnTheRightSide() {
        baseSteps.getPages().get_FindACenter_Page().validate_TheMapPopup_IsDisplayed_OnTheRight();
    }

    @And("verify the details displayed in the popup to be the same as the the one displayed on the left side")
    public void verifyTheDetailsDisplayedInThePopupToBeTheSameAsTheTheOneDisplayedOnTheLeftSide() {
        baseSteps.getPages().get_FindACenter_Page().validate_TheCenterdetails_WithMapPoppup();
    }
}
