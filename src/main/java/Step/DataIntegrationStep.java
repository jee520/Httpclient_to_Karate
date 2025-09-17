package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataIntegrationStep {

	
	@Given("the input is present in the excel sheet for EDD")
	public void the_input_is_present_in_the_excel_sheet_for_edd() {
		System.out.println("************ EDD API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for EDD")
	public void the_url_and_cookies_are_given_in_the_properties_for_edd() {
	   
	}

	@Given("taking the excel rows as input for EDD")
	public void taking_the_excel_rows_as_input_for_edd() {
	   
	}

	@Then("Results are generated in excel for EDD")
	public void results_are_generated_in_excel_for_edd() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("EDD");
		System.out.println("************ EDD API Testing - ENDS************");
	}
	
	
	//CONNECTORS
	@Given("the input is present in the excel sheet for Connectors")
	public void the_input_is_present_in_the_excel_sheet_for_connectors() {
		System.out.println("*************** Connectors API Automation - STARTS*****************");
	}

	@Given("the url and cookies are given in the properties for Connectors")
	public void the_url_and_cookies_are_given_in_the_properties_for_connectors() {
	  
	}

	@Given("taking the excel rows as input for Connectors")
	public void taking_the_excel_rows_as_input_for_connectors() {
	   
	}

	@Then("Results are generated in excel for Connectors")
	public void results_are_generated_in_excel_for_connectors() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("Connectors");
		System.out.println("*************** Connectors API Automation - ENDS*****************");
	}
	
	
	//DATA EXTRACTION
	@Given("the input is present in the excel sheet for Data Extraction")
	public void the_input_is_present_in_the_excel_sheet_for_data_extraction() {
		System.out.println("************ Data Extraction API Testing - STARTS ************");
	}

	@Given("the url and cookies are given in the properties for Data Extraction")
	public void the_url_and_cookies_are_given_in_the_properties_for_data_extraction() {
	   
	}

	@Given("taking the excel rows as input for Data Extraction")
	public void taking_the_excel_rows_as_input_for_data_extraction() {
	   
	}

	@Then("Results are generated in excel for Data Extraction")
	public void results_are_generated_in_excel_for_data_extraction() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("DataExtraction");
		System.out.println("************ Data Extraction API Testing - ENDS************");
	}
	
	
}
