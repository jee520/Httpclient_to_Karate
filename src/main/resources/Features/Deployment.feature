@all @Deploy
Feature: Deployment

  Scenario: Deployment API
    Given the input is present in the excel sheet for Deployment
    And the url and cookies are given in the properties for Deployment
    And taking the excel rows as input for Deployment
    Then Results are generated in excel for Deployment
