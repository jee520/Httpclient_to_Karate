@all @Entity
Feature: Entity

  Scenario: Entity Details
    Given the input is present in the excel sheet for Entities
    And the url and cookies are given in the properties for Entities
    And taking the excel rows as input for Entities
    Then Results are generated in excel for Entities
