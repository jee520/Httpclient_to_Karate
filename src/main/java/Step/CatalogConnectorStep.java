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

public class CatalogConnectorStep {

	@Given("the input is present in the excel sheet for CatalogConnector")
	public void the_input_is_present_in_the_excel_sheet_for_CatalogConnector() {
		System.out.println("************ CatalogConnector API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for CatalogConnector")
	public void the_url_and_cookies_are_given_in_the_properties_for_CatalogConnector() {

	}

	@Given("taking the excel rows as input for CatalogConnector")
	public void taking_the_excel_rows_as_input_for_CatalogConnector() {

	}

	@Then("Results are generated in excel for CatalogConnector")
	public void results_are_generated_in_excel_for_CatalogConnector() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("CatalogConnector");
		System.out.println("************ CatalogConnector API Testing - ENDS************");
	}

}
