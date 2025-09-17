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

public class DataGovernanceStep {

	@Given("the input is present in the excel sheet for Data Governance")
	public void the_input_is_present_in_the_excel_sheet_for_Data_Governance() {
		System.out.println("************ Data Governance API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Data Governance")
	public void the_url_and_cookies_are_given_in_the_properties_for_Data_Governance() {

	}

	@Given("taking the excel rows as input for Data Governance")
	public void taking_the_excel_rows_as_input_for_Data_Governance() {

	}

	@Then("Results are generated in excel for Data Governance")
	public void results_are_generated_in_excel_for_Data_Governance() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("DataGovernance");
		System.out.println("************ Data Governance API Testing - ENDS************");
	}

}
