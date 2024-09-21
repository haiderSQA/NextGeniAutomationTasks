Feature: Amazon Product Search and Validation

  Scenario: Search for toys, add to cart, and validate prices
    Given I open the Chrome browser with the specified profile
    When I navigate to "https://www.amazon.com"
    And I search for "toys"
    And I add the first toy to the cart and store its price
    And I add the second toy to the cart and store its price
    Then I validate the prices of the toys in the cart