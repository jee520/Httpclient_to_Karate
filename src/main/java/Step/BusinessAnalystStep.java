package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BusinessAnalystStep {

	@Given("the input is present in the excel sheet for Business Analyst")
	public void the_input_is_present_in_the_excel_sheet_for_Business_Analyst() {
		System.out.println("************ Business Analyst API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Business Analyst")
	public void the_url_and_cookies_are_given_in_the_properties_for_Business_Analyst() {

	}

	@Given("taking the excel rows as input for Business Analyst")
	public void taking_the_excel_rows_as_input_for_Business_Analyst() {

	}

	@Then("Results are generated in excel for Business Analyst")
	public void results_are_generated_in_excel_for_Business_Analyst() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("BusinessAnalyst");
		System.out.println("************ Business Analyst API Testing - ENDS************");
	}

}
