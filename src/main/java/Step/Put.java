package Step;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Put {


	public static Logger log = Logger.getLogger(Put.class);
	public static String filePath = new File("").getAbsolutePath();


	public void Put(String url,String ofsuser, int i,String response,String requestBody,String excel_path,int count,String path, String Cookies)

	{  
		try {


			ExcelUtils excelRow1=new ExcelUtils(excel_path,"TestCases");

			FileInputStream fin1=new FileInputStream("Parameter/Properties.properties");
			Properties prop1 = new Properties();
			prop1.load(fin1);
			FileInputStream fin=new FileInputStream("Parameter/ExcelProperties.properties");
			Properties prop=new Properties();
			prop.load(fin);

			String reqInputJson1=prop.getProperty(path+"_InputJson");
			String reqJsonFileName=excelRow1.getCellData(i, 6);
			String reqInputJson=reqInputJson1+reqJsonFileName;
			log.info("The path for the input request body json is ::"+reqInputJson);

			Path reqfileName= Path.of(reqInputJson);

			// Now calling Files.readString() method to
			// read the file
			requestBody = Files.readString(reqfileName);


			if(url.contains("/dsa/edd"))
			{

				log.info("Inside EDD or Data extraction, to replace dsID");

				Path dsidreqfileName= Path.of(filePath+"/Output/"+path+"/ActualResponse_eds_response.json");

				// Now calling Files.readString() method to
				// read the file
				String dsidrequestBody = Files.readString(dsidreqfileName);

				if(requestBody.contains("#dsid#") && requestBody.contains("#deId#"))
				{
					String dsid = GetValueFromJson.GetValueJson(dsidrequestBody, "dsId");
					requestBody = requestBody.replace("#dsid#", dsid);
					log.info("Replaced #dsid# in request body with "+dsid);
				}
				log.info(" Request Body after replacing dsid is : "+requestBody);

				if(requestBody.contains("#deId#"))
				{
					Path deidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-1), 15)+"");
					String eddsuccessmsg = Files.readString(deidjsonpath);

					String eddid = GetValueFromJson.GetValueJson(eddsuccessmsg, "id");
					requestBody = requestBody.replace("#deId#", eddid);
					log.info("Replaced #deId# in request body with "+eddid);
				}
			}
			if(requestBody.contains("#clientTenant#")) {
            	String clientTenant = prop1.getProperty("clientTenant");
            	requestBody = requestBody.replace("#clientTenant#", clientTenant);            	            	
            }
            if(requestBody.contains("#publishTenant#")) {
            	String publishTenant = prop1.getProperty("publishTenant");
            	requestBody = requestBody.replace("#publishTenant#", publishTenant);            	            	
            }
            if(requestBody.contains("#clientSecreID#")) {
            	String clientSecreID = prop1.getProperty("clientSecreID");
            	requestBody = requestBody.replace("#clientSecreID#", clientSecreID);            	            	
            }
            if(requestBody.contains("#OfsUser#")) {
            	String OfsUser = prop1.getProperty("OfsUser");
            	requestBody = requestBody.replace("#OfsUser#", OfsUser);            	            	
            }
            if(requestBody.contains("#password#")) {
            	String password = prop1.getProperty("password");
            	requestBody = requestBody.replace("#password#", password);            	            	
            }

			log.info("The Final Request Body is : "+requestBody);


			ConnectionParam conn=new ConnectionParam();
			CloseableHttpClient httpClient=conn.GetConnectionParam(); 
			JSONParser parser = new JSONParser();
			JSONObject postRequest = (JSONObject) parser.parse(requestBody);
			System.out.println("Request is:"+postRequest);

			if(url.contains("/dsa/edd/##eddid##"))
			{
				Path eddidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-1), 15)+"");
				String eddsuccessmsg = Files.readString(eddidjsonpath);

				String eddid = GetValueFromJson.GetValueJson(eddsuccessmsg, "id");
				url = url.replace("##eddid##", eddid);
			}
			if(url.contains("/dsa/connector/##cid##"))
			{
				Path connidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-1), 15)+"");
				String connectorsuccessmsg = Files.readString(connidjsonpath);

				String cid = GetValueFromJson.GetValueJson(connectorsuccessmsg, "id");
				url = url.replace("##cid##", cid);
			}
			if(url.contains("/dsa/edd/##deceddid##"))
			{
				Path decidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-2), 15)+"");
				String decsuccessmsg = Files.readString(decidjsonpath);

				String decid = GetValueFromJson.GetValueJson(decsuccessmsg, "id");
				url = url.replace("##deceddid##", decid);
			}
			if(url.contains("/dsa/connector/##decconnid##"))
			{
				Path decidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-2), 15)+"");
				String decsuccessmsg = Files.readString(decidjsonpath);

				String decid = GetValueFromJson.GetValueJson(decsuccessmsg, "id");
				url = url.replace("##decconnid##", decid);
			}

			//		url=url.replaceAll("<tenant_id>", Tanent);
			log.info("Updated URL for the pipeline is:"+url);

			HttpPut request = new HttpPut(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-Type", "application/json");
			request.setHeader("ofs_remote_user", ofsuser);
			request.setHeader("ofs_remote_username","Saikumar Dontula");
			request.setHeader("Cookie", Cookies);
			//request.setHeader("ofs_tenant_id", Tanent);
			//		request.setHeader("ofs_service_id", Service);
			//		request.setHeader("ofs_workspace_id", Workspace);
			//		request.setHeader("locale", Locale);
			//		request.setHeader("ofs_remote_user", OfsUser);

			StringEntity params = new StringEntity(postRequest.toJSONString());
			params.setContentType("application/json");


			request.setEntity(params);
			log.info("Params:"+params);
			CloseableHttpResponse response1= httpClient.execute(request);


			log.info("Response1:"+response);
			String ActualResponse=EntityUtils.toString(response1.getEntity(), "UTF-8");
			log.info("Response2"+ActualResponse);




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





			boolean responseCheck;
			try
			{
				JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, JSONCompareMode.LENIENT);
				responseCheck = true;
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

			log.info("Response check::"+responseCheck);

			log.info("Response code::"+response1.getStatusLine().getStatusCode());

			PrintWriter actualoutput = new PrintWriter(filePath+"/Output/"+path+"/ActualResponse_"+excelRow.getCellData(i, 15)+"");

			actualoutput.print(ActualResponseindented);

			actualoutput.close();


			if (response1.getStatusLine().getStatusCode() == 200 && responseCheck)
			{
				log.info("Status::Passed");
				ExcelWrite pass=new ExcelWrite();
				pass.ExcelWritePass(excel_path,"TestCases",i,ActualResponseindented,count);


			}
			else if (response1.getStatusLine().getStatusCode() == 500 && responseCheck)
			{
				log.info("Status::Passed");
				ExcelWrite pass=new ExcelWrite();
				pass.ExcelWritePass(excel_path,"TestCases",i,Integer.toString(response1.getStatusLine().getStatusCode()),count);

			}
			else if (response1.getStatusLine().getStatusCode() == 400 && responseCheck)
			{
				log.info("Status::Passed");
				ExcelWrite pass=new ExcelWrite();
				pass.ExcelWritePass(excel_path,"TestCases",i,Integer.toString(response1.getStatusLine().getStatusCode()),count);

			}
			else
			{
				log.info("Status::Failed");
				ExcelWrite fail=new ExcelWrite();
				fail.ExcelWriteFail(excel_path,"TestCases",i,ActualResponseindented,ExpectedResponseindented,url,count);

			}

		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
