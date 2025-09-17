package Step;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Delete {

	public static Logger log = Logger.getLogger(Get.class);
	public static String filePath = new File("").getAbsolutePath();

	

	public void Delete(String url,String ofsuser,int i,String response,String requestBody,String excel_path,int count,String path, String Cookies)

	{  
		try {
			ConnectionParam conn=new ConnectionParam();
			CloseableHttpClient httpClient=conn.GetConnectionParam();
			//JSONParser parser = new JSONParser();
			//JSONObject postRequest = (JSONObject) parser.parse(requestBody);
			
			ExcelUtils excelRow1=new ExcelUtils(excel_path,"TestCases");
			
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
			if(url.contains("/dsa/connector/##decconnid##"))
			{
				Path decidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_"+excelRow1.getCellData((i-1), 15)+"");
				String decsuccessmsg = Files.readString(decidjsonpath);
				
				String decid = GetValueFromJson.GetValueJson(decsuccessmsg, "id");
				url = url.replace("##decconnid##", decid);
			}
			
			System.out.println("Updated URL for the pipeline is:"+url);

			HttpDelete request=new HttpDelete(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			request.setHeader("ofs_remote_user", ofsuser);
			request.setHeader("ofs_remote_username","Saikumar Dontula");
			request.setHeader("Cookie", Cookies);
			//StringEntity params = new StringEntity(postRequest.toJSONString());
			//((HttpResponse) request).setEntity(params);

			CloseableHttpResponse response1= httpClient.execute(request);
			HttpEntity entity = response1.getEntity();
			//System.out.println("Response Body::"+EntityUtils.toString(response1.getEntity(), "UTF-8"));
			System.out.println("Response1:"+response);
			String ActualResponse=EntityUtils.toString(response1.getEntity(), "UTF-8");
			System.out.println("Response2"+ActualResponse);
			
			
			
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
