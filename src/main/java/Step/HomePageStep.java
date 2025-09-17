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

public class HomePageStep {




	@Given("the input is present in the excel sheet for Homepage")
	public void the_input_is_present_in_the_excel_sheet_for_homepage() {
		System.out.println("************ HomePage API Testing - STARTS************");
	}

	@Given("the url and cookies are given in the properties for Homepage")
	public void the_url_and_cookies_are_given_in_the_properties_for_homepage() {

	}

	@Given("taking the excel rows as input for Homepage")
	public void taking_the_excel_rows_as_input_for_homepage() {

	}

	@Then("Results are generated in excel for Homepage")
	public void results_are_generated_in_excel_for_homepage() throws KeyManagementException, EmptyFileException, ClientProtocolException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		ApiValidation.ApiValidation("HomePage");
		System.out.println("************ HomePage API Testing - ENDS************");
	}







//	public static void main(String[] args) {
//
//
//		String X="niveditha";
//		String Y="lavekdahnita";
//		
//		int S=3;
//		int R=5;
//
//		X = X.toLowerCase();  
//		Y = Y.toLowerCase();  
//
//		int M = X.length();
//		int N = Y.length();
//
//		char ch[]=Y.toCharArray();  
//		String rev="";  
//		for(int i=ch.length-1;i>=0;i--){  
//			rev+=ch[i];  
//		}  
//
//		System.out.println("Reverese String is: "+rev);
//
//		int s=0;int s1=2;
//
//		int count=0;
//
//		int countrev =0;
//
//		for (int i = 0; i < 1; i++) {
//
//			for (int j = 0; j < N; j++)
//			{
//				if(Y.contains(X.substring(s, s1)))
//				{
//					s=s+2;
//					s1=s1+2;
//					count++;
//				}
//				else if(Y.contains(String.valueOf(X.substring(s, s1).charAt(0))))
//				{
//					s=X.indexOf(String.valueOf(X.substring(s, s1).charAt(0)))+1;
//					s1=s+2;
//
//					if(s1>M)
//					{
//						break;
//					}
//					count++;
//				}
//			}
//
//
//		}
//
//
//		s=0;s1=2;
//		for (int i = 0; i < 1; i++) 
//		{
//			for (int j = 0; j < N; j++)
//			{
//				if(rev.contains(X.substring(7,9)))
//				{
//					countrev++;
//					break;
//				}
//			}
//		}
//
//
//		System.out.println("Number of sub strings selected from Y is : "+count);
//
//		System.out.println("Number of sub strings selected from reversed Y is :  "+countrev);
//		
//		if(count>0 || countrev>0)
//		{
//			int Fcator = (count*S)+(countrev*R);
//
//			System.out.println("String Fcator = "+Fcator);
//		}
//
//		else
//		{
//			System.out.println("Impossible");
//		}
//		
//		
//		
//		
//		
//		
//		
//	}























}
