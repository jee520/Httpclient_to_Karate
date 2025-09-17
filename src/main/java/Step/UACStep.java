package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UACStep {
	
	
	
	@Given("the input is present in the excel sheet for UAC")
	public void the_input_is_present_in_the_excel_sheet_for_uac() {
		System.out.println("*************** UAC API Automation - STARTS*****************");
	}

	@Given("the url and cookies are given in the properties for UAC")
	public void the_url_and_cookies_are_given_in_the_properties_for_uac() {
	  
	}

	@Given("taking the excel rows as input for UAC")
	public void taking_the_excel_rows_as_input_for_uac() {
	   
	}

	@Then("Results are generated in excel for UAC")
	public void results_are_generated_in_excel_for_uac() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("UAC");
		System.out.println("*************** UAC API Automation - ENDS*****************");
	}
	
	
	

}
