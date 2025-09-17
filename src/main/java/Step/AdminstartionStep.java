package Step;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AdminstartionStep {
	
	
	@Given("the input is present in the excel sheet for FileUpload")
	public void the_input_is_present_in_the_excel_sheet_for_file_upload() {
		System.out.println("************ FileUpload API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for FileUpload")
	public void the_url_and_cookies_are_given_in_the_properties_for_file_upload() {
	   
	}

	@Given("taking the excel rows as input for FileUpload")
	public void taking_the_excel_rows_as_input_for_file_upload() {
	   
	}

	@Then("Results are generated in excel for FileUpload")
	public void results_are_generated_in_excel_for_file_upload() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("FileUpload");
		System.out.println("************ FileUpload API Testing - ENDS************");
	}
	
	
	//FISCAL PERIOD
	
	@Given("the input is present in the excel sheet for Fiscal Period")
	public void the_input_is_present_in_the_excel_sheet_for_fiscal_period() {
		System.out.println("************ Fiscal Period API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Fiscal Period")
	public void the_url_and_cookies_are_given_in_the_properties_for_fiscal_period() {
	   
	}

	@Given("taking the excel rows as input for Fiscal Period")
	public void taking_the_excel_rows_as_input_for_fiscal_period() {
	  
	}

	@Then("Results are generated in excel for Fiscal Period")
	public void results_are_generated_in_excel_for_fiscal_period() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("FiscalPeriod");
		System.out.println("************ Fiscal Period API Testing - ENDS************");
	}
	
	
	
	//Legal Entity
	@Given("the input is present in the excel sheet for Legal Entity")
	public void the_input_is_present_in_the_excel_sheet_for_legal_entity() {
		System.out.println("************ Legal Entity API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Legal Entity")
	public void the_url_and_cookies_are_given_in_the_properties_for_legal_entity() {
	  
	}

	@Given("taking the excel rows as input for Legal Entity")
	public void taking_the_excel_rows_as_input_for_legal_entity() {
	   
	}

	@Then("Results are generated in excel for Legal Entity")
	public void results_are_generated_in_excel_for_legal_entity() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("LegalEntity");
		System.out.println("************ Legal Entity API Testing - ENDS************");
	}
	
	
	//Parameter
	@Given("the input is present in the excel sheet for Parameter")
	public void the_input_is_present_in_the_excel_sheet_for_parameter() {
		System.out.println("************ Parameter API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Parameter")
	public void the_url_and_cookies_are_given_in_the_properties_for_parameter() {
	   
	}

	@Given("taking the excel rows as input for Parameter")
	public void taking_the_excel_rows_as_input_for_parameter() {
	    
	}

	@Then("Results are generated in excel for Parameter")
	public void results_are_generated_in_excel_for_parameter() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("Parameter");
		System.out.println("************ Parameter API Testing - ENDS************");
	}
	
	
	//RUN EXE PARAMETERS
	@Given("the input is present in the excel sheet for REP")
	public void the_input_is_present_in_the_excel_sheet_for_rep() {
		System.out.println("************ Run Execution Parameter API Testing - STARTS ************");
	}

	@Given("the url and cookies are given in the properties for REP")
	public void the_url_and_cookies_are_given_in_the_properties_for_rep() {
	   
	}

	@Given("taking the excel rows as input for REP")
	public void taking_the_excel_rows_as_input_for_rep() {
	    
	}

	@Then("Results are generated in excel for REP")
	public void results_are_generated_in_excel_for_rep() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("RunExecParam");
		System.out.println("************ Run Execution Parameter API Testing - ENDS ************");
	}
	
	
	//REPORT CONFIGURATION
	@Given("the input is present in the excel sheet for Report Configuration")
	public void the_input_is_present_in_the_excel_sheet_for_report_configuration() {
		System.out.println("************ Report Configuration API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Report Configuration")
	public void the_url_and_cookies_are_given_in_the_properties_for_report_configuration() {
	   
	}

	@Given("taking the excel rows as input for Report Configuration")
	public void taking_the_excel_rows_as_input_for_report_configuration() {
	   
	}

	@Then("Results are generated in excel for Report Configuration")
	public void results_are_generated_in_excel_for_report_configuration() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("ReportConfiguration");
		System.out.println("************ Report Configuration API Testing - ENDS************");
	}
	




}
