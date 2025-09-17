package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataCatalogStep {
	
	
	//BT
	@Given("the input is present in the excel sheet for BusinessTerms")
	public void the_input_is_present_in_the_excel_sheet_for_BusinessTerms() {
		System.out.println("************ Business Terms API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for BusinessTerms")
	public void the_url_and_cookies_are_given_in_the_properties_for_BusinessTerms() {
	 
	}

	@Given("taking the excel rows as input for BusinessTerms")
	public void taking_the_excel_rows_as_input_for_BusinessTerms() {
	
	}

	@Then("Results are generated in excel for BusinessTerms")
	public void results_are_generated_in_excel_for_BusinessTerms() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		//ApiValidation api=new ApiValidation();
		ApiValidation.ApiValidation("BusinessTerms");
		System.out.println("************ Business Terms API Testing - ENDS************");
	}
	
	
	//ENTITY
	@Given("the input is present in the excel sheet for Entities")
	public void the_input_is_present_in_the_excel_sheet_for_Entities() {
	    
	}

	@Given("the url and cookies are given in the properties for Entities")
	public void the_url_and_cookies_are_given_in_the_properties_for_Entities() {
	 
	}

	@Given("taking the excel rows as input for Entities")
	public void taking_the_excel_rows_as_input_for_Entities() {
	
	}

	@Then("Results are generated in excel for Entities")
	public void results_are_generated_in_excel_for_Entities() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		//ApiValidation api=new ApiValidation();
		ApiValidation.ApiValidation("Entity");
	}
	
	//EG
	@Given("the input is present in the excel sheet for External Glossary")
	public void the_input_is_present_in_the_excel_sheet_for_external_glossary() {
		System.out.println("*************** External Glossary API Automation Execution - STARTS*****************");
	}

	@Given("the url and cookies are given in the properties for External Glossary")
	public void the_url_and_cookies_are_given_in_the_properties_for_external_glossary() {

	}

	@Given("taking the excel rows as input for External Glossary")
	public void taking_the_excel_rows_as_input_for_external_glossary() {

	}

	@Then("Results are generated in excel for External Glossary")
	public void results_are_generated_in_excel_for_external_glossary() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("ExternalGlossary");

		System.out.println("*************** External Glossary API Automation Execution - ENDS*****************");
	}
	
	//BIRD
	@Given("the input is present in the excel sheet for BIRD")
	public void the_input_is_present_in_the_excel_sheet_for_bird() {
		System.out.println("*************** BIRD Mapper API Automation - STARTS*****************");
	}

	@Given("the url and cookies are given in the properties for BIRD")
	public void the_url_and_cookies_are_given_in_the_properties_for_bird() {
	   
	}

	@Given("taking the excel rows as input for BIRD")
	public void taking_the_excel_rows_as_input_for_bird() {
	    
	}

	@Then("Results are generated in excel for BIRD")
	public void results_are_generated_in_excel_for_bird() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("BIRDMapper");
		
		System.out.println("*************** BIRD Mapper API Automation - ENDS*****************");
	}
	
	//DATA SCENARIOS
	@Given("the input is present in the excel sheet for Data Secanrios")
	public void the_input_is_present_in_the_excel_sheet_for_data_secanrios() {
		System.out.println("************ Data Scenarios API Testing - STARTS************");
		
		
	}

	@Given("the url and cookies are given in the properties for Data Secanrios")
	public void the_url_and_cookies_are_given_in_the_properties_for_data_secanrios() {
	  
		
	}

	@Given("taking the excel rows as input for Data Secanrios")
	public void taking_the_excel_rows_as_input_for_data_secanrios() {
	   
		
	}

	@Then("Results are generated in excel for Data Secanrios")
	public void results_are_generated_in_excel_for_data_secanrios() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("DataScenarios");
		System.out.println("************ Data Scenarios API Testing - ENDS************");
	}
	
	
	
	
	
	
	

}
