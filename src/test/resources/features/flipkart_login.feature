Feature: Flipkart Login and Product Search

  Scenario: User logs in and searches for a product
    Given user is on Flipkart homepage
    And user searches for "iPhone"
    Then product search results should be displayed