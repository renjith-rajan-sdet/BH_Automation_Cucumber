@BrightHorizonSanitychecks
Feature: Sample cross browser tests

  @CrossBorswerTests
    @severity=High
  Scenario Outline: TC001_BrightHorizons_WebSite_CenterSearch : Browser : "<Browser>"
    Given Web Application launched - Application - "BrightHorizons" : with Browser : "<Browser>", Environment : "PROD"
    And click on 'search/loupe' icon on top right of the screen
    Then validate that the popup text box for search is loaded on the screen
    And enter the text "Employee Education in 2018: Strategies to Watch" to the search box and click on the search button
    Then validate that the first search result is the exact match of the searched text in the above step

    Examples:
      | Browser |
      | Chrome  |
      | Edge    |
      | Firefox |


  @CrossBorswerTests
  @severity=High
  Scenario Outline: TC002_BrightHorizons_WebSite_CenterSearch: Browser : "<Browser>"
    Given Web Application launched - Application - "BrightHorizons" : with Browser : "<Browser>", Environment : "PROD"
    Then click on 'Find a center' button on top of the screen
    And validate that the url of the webpage opened contains "/child-care-locator"
    And in the location search box - type 'New York' and press ENTER key
    When the search results are loaded, validate that the number of centers found is the same as the number of centers listed as search result
    Then click on the first center on the list
    And validate that a poppup is opened showing the center details - on the right side
    And verify the details displayed in the poppup to be the same as the the one displayed on the left side

    Examples:
      | Browser |
      | Chrome  |
      | Edge    |
      | Firefox |




