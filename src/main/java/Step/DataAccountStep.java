package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataAccountStep {
	
	
	
	@Given("the input is present in the excel sheet for Data Account")
	public void the_input_is_present_in_the_excel_sheet_for_data_account() {
		System.out.println("************ Data Account API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Data Account")
	public void the_url_and_cookies_are_given_in_the_properties_for_data_account() {
	   
	}

	@Given("taking the excel rows as input for Data Account")
	public void taking_the_excel_rows_as_input_for_data_account() {
	   
	}

	@Then("Results are generated in excel for Data Account")
	public void results_are_generated_in_excel_for_data_account() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("DataAccount");
		System.out.println("************ Data Account API Testing - ENDS************");
	}

	
	
	

}
