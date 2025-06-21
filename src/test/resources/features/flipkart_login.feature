Feature: Flipkart Login and Product Search

Background:
    Given user is on Flipkart homepage 
  @smoke 
  Scenario: User logs in and searches for a product
    And user searches for "iPhone"
    Then product search results should be displayed with "iPhone"
    
  @smoke 
  Scenario: User logs in and searches for a product
    And user searches for "iPhone"
   
  @smoke   
  Scenario: User searches for a product
    And user searches for "Samsung Galaxy"
    Then product search results should be displayed with "Samsung Galaxy"
    
  @smoke   
  Scenario Outline: User searches for a product
    When user searches for "<product>"
    Then product search results should be displayed with "<product>"
      
  Examples:   
  |product    |
  |iphone     |
  |macbook pro|