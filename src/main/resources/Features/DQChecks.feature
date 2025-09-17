@all @DQChecks
Feature: DataQuality Check API

  Scenario: DataQuality Check API
    Given the input is present in the excel sheet for DataQuality checks
    And the url and cookies are given in the properties for DataQuality checks
    And taking the excel rows as input for DataQuality checks
    Then Results are generated in excel for DataQuality checks
