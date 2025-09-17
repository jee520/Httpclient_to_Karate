@all @DQException
Feature: Data Quality Exception Report

  Scenario: DQ Exception
    Given the input is present in the excel sheet for DQ Exception
    And the url and cookies are given in the properties for DQ Exception
    And taking the excel rows as input for DQ Exception
    Then Results are generated in excel for DQ Exception
