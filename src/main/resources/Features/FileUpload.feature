@all @Fileupload
Feature: File Upload

  Scenario: File upload
    Given the input is present in the excel sheet for FileUpload
    And the url and cookies are given in the properties for FileUpload
    And taking the excel rows as input for FileUpload
    Then Results are generated in excel for FileUpload
