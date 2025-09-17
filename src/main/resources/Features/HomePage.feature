@all @HomePage
Feature: Get Count from HomePage

  Scenario: Get the number of BTs and entities
    Given the input is present in the excel sheet for Homepage
    And the url and cookies are given in the properties for Homepage
    And taking the excel rows as input for Homepage
    Then Results are generated in excel for Homepage
