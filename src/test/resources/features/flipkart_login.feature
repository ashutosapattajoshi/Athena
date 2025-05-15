Feature: Flipkart Login and Product Search

  Scenario: User logs in and searches for a product
    Given user is on Flipkart homepage
    When user closes the login popup
    And user clicks on Login and enters "9999999999"
    And user requests OTP
    And user searches for "Samsung Galaxy"
    Then product search results should be displayed