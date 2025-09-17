package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SARStep {
	
	
	@Given("the input is present in the excel sheet for SAR")
	public void the_input_is_present_in_the_excel_sheet_for_sar() {
		System.out.println("************ SAR API TestCase Execution - STARTS************");
	}

	@Given("the url and cookies are given in the properties for SAR")
	public void the_url_and_cookies_are_given_in_the_properties_for_sar() {
	   
	}

	@Given("taking the excel rows as input for SAR")
	public void taking_the_excel_rows_as_input_for_sar() {
	   
	}

	@Then("Results are generated in excel for SAR")
	public void results_are_generated_in_excel_for_sar() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("SAR");
		System.out.println("************ SAR API TestCase Execution - ENDS************");
	}
	

}
