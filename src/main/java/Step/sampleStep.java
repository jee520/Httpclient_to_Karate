package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class sampleStep {
	@Given("the input is present in the excel sheet b")
	public void the_input_is_present_in_the_excel_sheet_b() {
	
	}

	@Given("the url and cookies are given in the properties v")
	public void the_url_and_cookies_are_given_in_the_properties_v() {
	    
	}

	@Given("taking the excel rows as input v")
	public void taking_the_excel_rows_as_input_v() {
	    
	}

	@Then("Results are generated in excel v")
	public void results_are_generated_in_excel_v() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation api=new ApiValidation();
		api.ApiValidation("sample");
	}

}
