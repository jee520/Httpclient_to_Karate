package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataMarketPlaceStep {
	
	
	
	@Given("the input is present in the excel sheet for Data Marketplace")
	public void the_input_is_present_in_the_excel_sheet_for_data_marketplace() {
		System.out.println("************ Data Market Place API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Data Marketplace")
	public void the_url_and_cookies_are_given_in_the_properties_for_data_marketplace() {
	   
	}

	@Given("taking the excel rows as input for Data Marketplace")
	public void taking_the_excel_rows_as_input_for_data_marketplace() {
	    
	}

	@Then("Results are generated in excel for Data Marketplace")
	public void results_are_generated_in_excel_for_data_marketplace() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("DataMarketPlace");
		System.out.println("************ Data Market Place API Testing - ENDS************");
	}
	
	

}
