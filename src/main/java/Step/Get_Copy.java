package Step;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Get_Copy {
	static String Jsonrequest=null;

	public static Logger log = Logger.getLogger(Get_Copy.class);
	public static String filePath = new File("").getAbsolutePath();

	public void Get(String url,String ofsuser, int i,String response,String excel_path,int count, String path,String Cookies)

	{  
		try {


			ExcelUtils excelRow1=new ExcelUtils(excel_path,"TestCases");

			if(url.contains("/dsa/edd"))
			{

				System.out.println("Inside EDD or Data extraction, to replace dsID");

				String Json = excelRow1.getCellData((i-1), 13);

				if(url.contains("#eddid#"))
				{
					String eddid = GetValueFromJson.GetValueJson(Json, "id");
					url = url.replace("#eddid#", eddid);
					System.out.println("Replaced #eddid# in request body with "+eddid);
				}
			}


			ConnectionParam conn=new ConnectionParam();
			CloseableHttpClient httpClient=conn.GetConnectionParam(); 


			log.info("Updated URL for the pipeline is:"+url);

			HttpGet request=new HttpGet(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Cookie", Cookies);
			request.setHeader("ofs_remote_user",ofsuser);

			CloseableHttpResponse response1= httpClient.execute(request);
			HttpEntity entity = response1.getEntity();

			log.info("HttpEntity: "+entity);


			String ActualResponse=EntityUtils.toString(response1.getEntity(), "UTF-8");
			log.info("The Actual response is ::"+ActualResponse);  //--commented

			ObjectMapper mapper = new ObjectMapper();
			String ActualResponseindented="";

			try
			{

				Object json = mapper.readValue(ActualResponse, Object.class);

				ActualResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

			}
			catch(Exception rsnull)
			{
				log.error("Inside catch as actual response is null: ");
				log.error(rsnull);
				ActualResponseindented=ActualResponse;
			}



			long lenght = ActualResponseindented.length();

			log.info("The actual Response lenght is: "+lenght);

			log.info("The Actual response is ::"+ActualResponseindented);

			if(lenght>=32767)
			{

				FileInputStream fin1=new FileInputStream("Parameter/Properties.properties");
				Properties prop1 = new Properties();
				prop1.load(fin1);
				FileInputStream fin=new FileInputStream("Parameter/ExcelProperties.properties");
				Properties prop=new Properties();
				prop.load(fin);

				ExcelUtils excelRow=new ExcelUtils(excel_path,"TestCases");
				log.info("The Row is:"+i);
				String InputJson1=prop.getProperty(path+"_InputJson");
				String JsonFileName=excelRow.getCellData(i, 15);
				String InputJson=InputJson1+JsonFileName;
				log.info("The path for the input json is ::"+InputJson);
				String MappingRequired=excelRow.getCellData(i, 16);
				log.info("The Mapping is Required?:"+MappingRequired);

				Path fileName= Path.of(InputJson);

				// Now calling Files.readString() method to
				// read the file
				String Jsonresponse = Files.readString(fileName);

				log.info("The input json response body is ::"+Jsonresponse );  //--commented



				ObjectMapper mapper1 = new ObjectMapper();
				Object json1 = mapper1.readValue(Jsonresponse, Object.class);

				String ExpectedResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);

				//boolean responseCheck=ActualResponse.equals(Jsonresponse);
				boolean responseCheck;
				try
				{

					if(url.contains("ENT"))
					{
						log.info("Inside Custom Comparator for Entity");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
								new Customization("createdDate", (o1, o2) -> true),
								new Customization("modifiedDate", (o1, o2) -> true)
								));
						responseCheck = true;

					}
					else
					{
						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, JSONCompareMode.LENIENT);
						responseCheck = true;
					}
				}
				catch(AssertionError e)
				{
					log.info("inside AssertionError catch");
					log.error(e.getMessage());
					responseCheck=false;
				}
				catch(JSONException e1)
				{
					log.info("inside JSONException catch");
					log.error(e1.getMessage());
					responseCheck=false;
				}
				//boolean responseCheck=ActualResponseindented.equals(ExpectedResponseindented);

				log.info("Response check::"+responseCheck);

				log.info("Response code::"+response1.getStatusLine().getStatusCode());

				PrintWriter output = new PrintWriter(InputJson1+"/Responses/ActualResponse_"+excelRow.getCellData(i, 2)+".txt");

				output.print(ActualResponseindented);

				output.close();

				PrintWriter output1 = new PrintWriter(InputJson1+"/Responses/ExpectedResponse_"+excelRow.getCellData(i, 2)+".txt");

				output1.print(ExpectedResponseindented);

				output1.close();


				PrintWriter actualoutput = new PrintWriter(filePath+"/Output/"+path+"/ActualResponse_"+excelRow.getCellData(i, 2)+".txt");

				actualoutput.print(ActualResponseindented);

				actualoutput.close();

				PrintWriter expectedoutput = new PrintWriter(filePath+"/Output/"+path+"/ExpectedResponse_"+excelRow.getCellData(i, 2)+".txt");

				expectedoutput.print(ExpectedResponseindented);

				expectedoutput.close();



				if (response1.getStatusLine().getStatusCode() == 200 && responseCheck)
				{
					log.info("Status::Passed");
					ExcelWrite pass=new ExcelWrite();
					pass.ExcelWritePass(excel_path,"TestCases",i,ActualResponseindented,count);


				}
				else
				{
					log.info("Status::Failed");
					ExcelWrite fail=new ExcelWrite();
					fail.ExcelWriteFail(excel_path,"TestCases",i,ActualResponseindented,ExpectedResponseindented,url,count);

				}


			}

			else
			{

				FileInputStream fin1=new FileInputStream("Parameter/Properties.properties");
				Properties prop1 = new Properties();
				prop1.load(fin1);
				FileInputStream fin=new FileInputStream("Parameter/ExcelProperties.properties");
				Properties prop=new Properties();
				prop.load(fin);

				ExcelUtils excelRow=new ExcelUtils(excel_path,"TestCases");
				log.info("The Row is:"+i);
				String Jsonresponse=excelRow.getCellData(i, 12);
				String MappingRequired=excelRow.getCellData(i, 16);
				log.info("The Mapping is Required?:"+MappingRequired);

				log.info("The input json response body is ::"+Jsonresponse );  //--commented



				ObjectMapper mapper1 = new ObjectMapper();
				Object json1 = mapper1.readValue(Jsonresponse, Object.class);

				String ExpectedResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);

				//boolean responseCheck=ActualResponse.equals(Jsonresponse);
				boolean responseCheck;
				try
				{
					if(url.contains("/dsa/eds"))
					{
						log.info("Inside Custom Comparator");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
								new Customization("body.DSData[0].dsId", (o1, o2) -> true),
								new Customization("body.DSData[0].dsMBy", (o1, o2) -> true),
								new Customization("body.DSData[0].dsMDate", (o1, o2) -> true),
								new Customization("body.DSData[0].dsUsed", (o1, o2) -> true)
								));
						responseCheck = true;

					}
					else if(url.contains("/getCount"))
					{
						log.info("Inside Custom Comparator Getcount");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
								new Customization("count", (o1, o2) -> true)
								));
						responseCheck = true;
					}
					else if(url.contains("ENT"))
					{
						log.info("Inside Custom Comparator for Entity");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
								new Customization("createdDate", (o1, o2) -> true),
								new Customization("modifiedDate", (o1, o2) -> true)
								));
						responseCheck = true;

					}
					else if(url.contains("/dsa/edd"))
					{
						log.info("Inside Custom Comparator");

						if(ActualResponseindented.contains("DIH0000"))
						{
							responseCheck = true;
						}
						else
						{
							responseCheck = false;
						}
					}
					else if(url.contains("/uac/"))
					{
						log.info("Inside Custom Comparator UAC");
						
						ActualResponseindented = ActualResponseindented.replace("[", "");
						ActualResponseindented = ActualResponseindented.replace("]", "");
						ExpectedResponseindented = ExpectedResponseindented.replace("[", "");
						ExpectedResponseindented = ExpectedResponseindented.replace("]", "");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
								new Customization("createdAt", (o1, o2) -> true),
								new Customization("createdBy", (o1, o2) -> true),
								new Customization("moduleMasterId", (o1, o2) -> true),
								new Customization("updatedAt", (o1, o2) -> true),
								new Customization("userId", (o1, o2) -> true),
								new Customization("userName", (o1, o2) -> true),
								new Customization("userCommentId", (o1, o2) -> true)
								));
						responseCheck = true;
					}
					else if(url.contains("/catalogviewer/v1/dfcs/dqexception/summary"))
					{
						log.info("Inside Custom Comparator for DQ Exception");

						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
								new Customization("executionDate", (o1, o2) -> true),
								new Customization("exceptionReportDetails", (o1, o2) -> true)
								));
						responseCheck = true;

					}
					else
					{
						JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, JSONCompareMode.LENIENT);
						responseCheck = true;
					}
				}
				catch(AssertionError e)
				{
					log.info("inside AssertionError catch");
					log.error(e.getMessage());
					responseCheck=false;
				}
				catch(JSONException e1)
				{
					log.info("inside JSONException catch");
					log.error(e1.getMessage());
					responseCheck=false;
				}
				//boolean responseCheck=ActualResponseindented.equals(ExpectedResponseindented);

				log.info("Response check::"+responseCheck);

				log.info("Response code::"+response1.getStatusLine().getStatusCode());


				if (response1.getStatusLine().getStatusCode() == 200 && responseCheck)
				{
					log.info("Status::Passed");
					ExcelWrite pass=new ExcelWrite();
					pass.ExcelWritePass(excel_path,"TestCases",i,ActualResponseindented,count);


				}
				else
				{
					log.info("Status::Failed");
					ExcelWrite fail=new ExcelWrite();
					fail.ExcelWriteFail(excel_path,"TestCases",i,ActualResponseindented,ExpectedResponseindented,url,count);

				}







			}




		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}



	//	public static void compareJSons() throws JsonProcessingException {
	//		JSONObject obj1=null;
	//		JSONObject obj2=null;
	//		try {
	//			obj1= (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sadontul\\workspace\\DFCS_Automation\\DFCS_APItests\\CatalogService\\Unit\\BusinessTerms\\GETBTDetails\\InputJson\\Responses\\testorder1.txt"));
	//			obj2= (JSONObject) new JSONParser().parse(new FileReader("C:\\Users\\sadontul\\workspace\\DFCS_Automation\\DFCS_APItests\\CatalogService\\Unit\\BusinessTerms\\GETBTDetails\\InputJson\\Responses\\testorder.txt"));
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		} catch (ParseException e) {
	//			e.printStackTrace();
	//		}
	//
	//		ObjectMapper mapper= new ObjectMapper();
	//
	//		Assert.assertEquals(mapper.readTree(obj1.toJSONString()),mapper.readTree(obj2.toJSONString()));
	//
	//		System.out.println("Passed");
	//	}


	/*
	 * public static void main(String[] args) throws JSONException, IOException {
	 * //compareJSons(); //Path fileName= Path.of(
	 * "CatalogService/Unit/BusinessTerms/GETBTDetails/InputJson/Responses/ActualResponse_100000.txt"
	 * ); Path fileName= Path.of(
	 * "CatalogService/Unit/BusinessTerms/GETBTDetails/InputJson/Responses/testorder.txt"
	 * ); String input = Files.readString(fileName);
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); Object json =
	 * mapper.readValue(input, Object.class);
	 * 
	 * String indented =
	 * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
	 * 
	 * 
	 * //Path fileName1= Path.of(
	 * "CatalogService/Unit/BusinessTerms/GETBTDetails/InputJson/BTSummary_scn1.txt"
	 * ); Path fileName1= Path.of(
	 * "CatalogService/Unit/BusinessTerms/GETBTDetails/InputJson/Responses/testorder1.txt"
	 * ); String input1 = Files.readString(fileName1);
	 * 
	 * ObjectMapper mapper1 = new ObjectMapper(); Object json1 =
	 * mapper1.readValue(input1, Object.class);
	 * 
	 * String indented1 =
	 * mapper1.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
	 * 
	 * 
	 * String actual = indented1; String expected = indented;
	 * 
	 * JSONAssert.assertEquals(actual, expected, JSONCompareMode.LENIENT);
	 * //JSONAssert.assertEquals(actual, expected, JSONCompareMode.STRICT);
	 * System.out.println("Passed");
	 * 
	 * 
	 * //log.info(indented);
	 * 
	 * }
	 */


}
