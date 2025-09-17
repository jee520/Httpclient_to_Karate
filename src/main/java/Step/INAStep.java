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

public class INAStep {

	@Given("the input is present in the excel sheet for issues and actions")
	public void the_input_is_present_in_the_excel_sheet_for_issues_and_actions() {
		System.out.println("************ INA API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for issues and actions")
	public void the_url_and_cookies_are_given_in_the_properties_for_issues_and_actions() {

	}

	@Given("taking the excel rows as input for issues and actions")
	public void taking_the_excel_rows_as_input_for_issues_and_actions() {

	}

	@Then("Results are generated in excel for issues and actions")
	public void results_are_generated_in_excel_for_issues_and_actions() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("INA");
		System.out.println("************ INA API Testing - ENDS************");
	}

}
