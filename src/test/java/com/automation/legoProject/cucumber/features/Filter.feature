Feature: User can filter products

  Scenario: Filter products by keychains with price and age parameters
    Given I am at "lego.com" main page
    When I navigate to Lego Merchandise page and click on Keychains
    And I select "Key Chain" in the Product type filter
    And I select "6+" in the Age filter
    And I select "$0 - $25" in the price filter
    Then I see keychains that were correctly filtered: Item is "Key Chain" max price "25.00"


