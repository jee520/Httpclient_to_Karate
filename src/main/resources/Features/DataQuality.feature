@all @DataQuality
Feature: DataQuality Check API

  Scenario: DataQuality Check API
    Given the input is present in the excel sheet for DataQuality
    And the url and cookies are given in the properties for DataQuality
    And taking the excel rows as input for DataQuality
    Then Results are generated in excel for DataQuality
