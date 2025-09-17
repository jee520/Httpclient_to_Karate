package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataControlsStep {

	
	// DQ Checks
		@Given("the input is present in the excel sheet for DataQuality checks")
		public void the_input_is_present_in_the_excel_sheet_for_data_quality_checks() {
			System.out.println("************ Data Quality Checks API Testing - STARTS************");
		}

		@Given("the url and cookies are given in the properties for DataQuality checks")
		public void the_url_and_cookies_are_given_in_the_properties_for_data_quality_checks() {
		    
		}

		@Given("taking the excel rows as input for DataQuality checks")
		public void taking_the_excel_rows_as_input_for_data_quality_checks() {
		  
		}

		@Then("Results are generated in excel for DataQuality checks")
		public void results_are_generated_in_excel_for_data_quality_checks() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
			ApiValidation.ApiValidation("DQChecks");
			System.out.println("************ Data Quality Checks API Testing - ENDS************");
		}
		
		
		//DQ Groups
		@Given("the input is present in the excel sheet for DataQuality Groups")
		public void the_input_is_present_in_the_excel_sheet_for_data_quality_groups() {
			System.out.println("************ Data Quality Groups API Testing - STARTS************");
		}

		@Given("the url and cookies are given in the properties for DataQuality Groups")
		public void the_url_and_cookies_are_given_in_the_properties_for_data_quality_groups() {
		   
		}

		@Given("taking the excel rows as input for DataQuality Groups")
		public void taking_the_excel_rows_as_input_for_data_quality_groups() {
		  
		}

		@Then("Results are generated in excel for DataQuality Groups")
		public void results_are_generated_in_excel_for_data_quality_groups() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
			ApiValidation.ApiValidation("DQGroups");
			System.out.println("************ Data Quality Groups API Testing - ENDS************");
		}
		
		//DQ EXCEPTION
		@Given("the input is present in the excel sheet for DQ Exception")
		public void the_input_is_present_in_the_excel_sheet_for_dq_exception() {
			System.out.println("************ Data Quality Exception API Testing - STARTS************");
		}

		@Given("the url and cookies are given in the properties for DQ Exception")
		public void the_url_and_cookies_are_given_in_the_properties_for_dq_exception() {
		   
		}

		@Given("taking the excel rows as input for DQ Exception")
		public void taking_the_excel_rows_as_input_for_dq_exception() {
		   
		}

		@Then("Results are generated in excel for DQ Exception")
		public void results_are_generated_in_excel_for_dq_exception() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
			ApiValidation.ApiValidation("DQException");
			System.out.println("************ Data Quality Exception API Testing - ENDS************");
		}

	
}
