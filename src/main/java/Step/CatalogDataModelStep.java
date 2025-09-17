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

public class CatalogDataModelStep {

	@Given("the input is present in the excel sheet for CatalogDataModel")
	public void CatalogDataModel() {
		System.out.println("************ CatalogDataModel API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for CatalogDataModel")
	public void the_url_and_cookies_are_given_in_the_properties_for_CatalogDataModel() {

	}

	@Given("taking the excel rows as input for CatalogDataModel")
	public void taking_the_excel_rows_as_input_for_CatalogDataModel() {

	}

	@Then("Results are generated in excel for CatalogDataModel")
	public void results_are_generated_in_excel_for_CatalogDataModel() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("CatalogDataModel");
		System.out.println("************ CatalogDataModel API Testing - ENDS************");
	}

}
