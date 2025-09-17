package Step;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Get {
    static String Jsonrequest = null;
    public static Logger log = Logger.getLogger(Get.class);
    public static String filePath = new File("").getAbsolutePath();

    public void Get(String url, String ofsuser, int i, String response, String excel_path, int count, String path, String Cookies) throws IOException {
    	
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
        try {
            if (url.contains("/download")) {
                downloadFile(url, Cookies, fileName,path,excel_path,i,count);
            } else {
                processNormalApiResponse(url, ofsuser, i, response, excel_path, count, path, Cookies,fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String url, String Cookies, Path fileName, String path, String excel_path,int i,int count) {
        try {
            ConnectionParam conn = new ConnectionParam();
            CloseableHttpClient httpClient = conn.GetConnectionParam();
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept", "*/*");
            request.setHeader("Cookie", Cookies);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                log.info("Download successful, saving file...");
        		ExcelUtils excelRow=new ExcelUtils(excel_path,"TestCases");
                Path downloadedFilePath = Path.of(filePath + "/Output/"+ path + "/" + excelRow.getCellData(i, 15));
                saveFile(response.getEntity().getContent(), downloadedFilePath);
                String Expectedresponse = new String(Files.readAllBytes(fileName), "UTF-8");
                compareFiles(downloadedFilePath, fileName,excel_path,i,count,url,Expectedresponse);
            } else {
                log.error("Failed to download file. Status code: " + statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveFile(InputStream inputStream, Path downloadedFilePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(downloadedFilePath.toFile())) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            
            // Read from inputStream and write to fileOutputStream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            log.info("File saved successfully: " + downloadedFilePath);

        } catch (IOException e) {
            log.error("Error while saving downloaded file: " + e.getMessage());
        }
    }
    
    private void compareFiles(Path downloadedFilePath, Path expectedFilePath, String excel_path,int i,int count,String url,String Expectedresponse) {
        try {
            byte[] downloadedFileContent = Files.readAllBytes(downloadedFilePath);
            byte[] expectedFileContent = Files.readAllBytes(expectedFilePath);

            if (downloadedFileContent.length == expectedFileContent.length) {
                boolean filesAreEqual = true;

                for (int j = 0; j < downloadedFileContent.length; j++) {
                    if (downloadedFileContent[j] != expectedFileContent[j]) {
                        filesAreEqual = false;
                        break;
                    }
                }
                String response = "Excel File";

                if (filesAreEqual) {
                	log.info("Status::Passed");
    				ExcelWrite pass=new ExcelWrite();
    				pass.ExcelWritePass(excel_path,"TestCases",i,response,count);
                } else {
                	log.info("Status::Failed");
    				ExcelWrite fail=new ExcelWrite();
    				fail.ExcelWriteFail(excel_path,"TestCases",i,response,Expectedresponse,url,count);
    			}
            }

        } catch (IOException e) {
            log.error("Error while comparing files: " + e.getMessage());
        }
    }

    private void processNormalApiResponse(String url, String ofsuser, int i, String response, String excel_path, int count, String path, String Cookies, Path fileName) {
		try {


			ExcelUtils excelRow1=new ExcelUtils(excel_path,"TestCases");

			if(url.contains("/dsa/edd"))
			{

				log.info("Inside EDD or Data extraction, to replace dsID");

				String Json = excelRow1.getCellData((i-1), 13);

				if(url.contains("#eddid#"))
				{
					String eddid = GetValueFromJson.GetValueJson(Json, "id");
					url = url.replace("#eddid#", eddid);
					log.info("Replaced #eddid# in request body with "+eddid);
				}
			}
			
			///////Aswini code /////////
			if(url.contains("/catalogviewer/v1/dfcs/catalog/summary/#id1#"))
			{
				Path summaryidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_SummaryWithoutKeyword.json");
				String resp = Files.readString(summaryidjsonpath);
				String summaryid = GetValueFromJson.getSummaryID(resp, "FSDF");
				url = url.replace("#id1#", summaryid);
			}
			if(url.contains("/catalogviewer/v1/dfcs/catalog/summary/#id2#"))
			{
				Path summaryidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_SummaryWithoutKeyword.json");
				String resp = Files.readString(summaryidjsonpath);
				String summaryid = GetValueFromJson.getSummaryID(resp, "PBSM");
				url = url.replace("#id2#", summaryid);
			}
			if(url.contains("glossaryMappingVersion=#version#"))
			{
				Path summaryidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_glossarymappingmaster.json");
				String resp = Files.readString(summaryidjsonpath);
				String summaryid = GetValueFromJson.getversionID(resp, "glossaryMappingVersion");
				url = url.replace("#version#", summaryid);
			}
			
			if(url.contains("primaryCatalogVersion=#version#"))
			{
				Path summaryidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_glossarymappingmaster.json");
				String resp = Files.readString(summaryidjsonpath);
				String summaryid = GetValueFromJson.getversionID(resp, "primaryCatalogVersion");
				url = url.replace("#version#", summaryid);
			}			
			
			if(url.contains("connectorVersion=#version#"))
			{
				Path summaryidjsonpath = Path.of(filePath+"/Output/"+path+"/ActualResponse_connectors_without_Filter.json");
				String resp = Files.readString(summaryidjsonpath);
				String summaryid = GetValueFromJson.getversionID(resp, "connectorVersion");
				url = url.replace("#version#", summaryid);
			}
			
			
			////////


			ConnectionParam conn=new ConnectionParam();
			CloseableHttpClient httpClient=conn.GetConnectionParam(); 


			log.info("Updated URL for the pipeline is:"+url);

			HttpGet request=new HttpGet(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Cookie", Cookies);
			request.setHeader("ofs_remote_user",ofsuser);
			request.setHeader("ofs_remote_username","Saikumar Dontula");

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

//			FileInputStream fin1=new FileInputStream("Parameter/Properties.properties");
//			Properties prop1 = new Properties();
//			prop1.load(fin1);
//			FileInputStream fin=new FileInputStream("Parameter/ExcelProperties.properties");
//			Properties prop=new Properties();
//			prop.load(fin);
//
//			ExcelUtils excelRow=new ExcelUtils(excel_path,"TestCases");
//			log.info("The Row is:"+i);
//			String InputJson1=prop.getProperty(path+"_InputJson");
//			String JsonFileName=excelRow.getCellData(i, 15);
//			String InputJson=InputJson1+JsonFileName;
//			log.info("The path for the input json is ::"+InputJson);
//			String MappingRequired=excelRow.getCellData(i, 16);
//			log.info("The Mapping is Required?:"+MappingRequired);
//
//			Path fileName= Path.of(InputJson);

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
				if(url.contains("/dsa/vars"))
				{
					log.info("Inside Custom Comparator GET Paramater Details");

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
							new Customization("body.paramData[0].paramId", (o1, o2) -> true),
							new Customization("body.paramData[0].paramMDate", (o1, o2) -> true)
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
				else if(url.contains("/dfcsapi/dfcs/appsetup/v1/domains"))
				{
					log.info("Inside Custom Comparator Domains");

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
							new Customization("tenantId", (o1, o2) -> true),
							new Customization("appVersion", (o1, o2) -> true)
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
				else if(url.contains("/ina/"))
				{
					log.info("Inside Custom Comparator");

					if(ActualResponseindented.contains("DGS0000"))
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
							new Customization("createdAtTime", (o1, o2) -> true),
							new Customization("updatedAtTime", (o1, o2) -> true),
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

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
							new Customization("executionDate", (o1, o2) -> true),
							new Customization("exceptionReportDetails", (o1, o2) -> true)
							));
					responseCheck = true;

				}
				else if(url.contains("/analyticsUrl"))
				{
					log.info("Inside analyticsUrl data View API");

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
							new Customization("msgbody", (o1, o2) -> true)
							));
					responseCheck = true;

				}
				else if(url.contains("/catalogviewer/v1/dfcs/applicationdataservice/subscribeadsstatus"))
				{
					log.info("Connector subscribeadsstatus");

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
							new Customization("body[*].createdAt", (o1, o2) -> true),
							new Customization("body[*].createdBy", (o1, o2) -> true),
							new Customization("body[*].updatedAt", (o1, o2) -> true),
							new Customization("body[*].updatedBy", (o1, o2) -> true),
							new Customization("body[*].catalogConnectorDeploymentStatusId", (o1, o2) -> true)							
							));
					responseCheck = true;

				} else if (url.contains("/catalogviewer/v1/dfcs/datamodel")) {
                    responseCheck = JsonCompSupporter.getStatusCompare(ActualResponseindented, ExpectedResponseindented);
                }
				else if (url.contains("/catalogviewer/v1/dfcs/glossarymapping/glossarymappingmaster")) {
					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
							new Customization("body[*].createdAt", (o1, o2) -> true),
				            new Customization("body[*].createdBy", (o1, o2) -> true),
				            new Customization("body[*].updatedAt", (o1, o2) -> true),
				            new Customization("body[*].updatedBy", (o1, o2) -> true)
							));
					responseCheck = true;
					
				}
				else if (url.contains("/catalogviewer/v1/dfcs/catalog/summary")) {
					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,			            
				            new Customization("ctlgPubDate", (o1, o2) -> true),
				            new Customization("ctlgVer", (o1, o2) -> true),
				            new Customization("ctlgFileName", (o1, o2) -> true),
				            new Customization("ctlgSrcVer", (o1, o2) -> true),
				            new Customization("id", (o1, o2) -> true)
							));
					responseCheck = true;
					
				}
				else if(url.contains("/glossarymapping/glossarymappingdetails") || url.contains("/glossarymapping/glossarymappingreview"))
				{
					log.info("glossary summary api");

					JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
							new Customization("body.createdAt", (o1, o2) -> true),
				            new Customization("body.createdBy", (o1, o2) -> true),
				            new Customization("body.updatedAt", (o1, o2) -> true),
				            new Customization("body.updatedBy", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterId", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterDetails", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterDetails[*].createdAt", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterDetails[*].createdBy", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterDetails[*].updatedAt", (o1, o2) -> true),
				            new Customization("body.glossaryMappingMasterDetails[*].updatedBy", (o1, o2) -> true),
							new Customization("body.glossaryMappingMasterDetails[*].glossaryMappingMasterDetailsId", (o1, o2) -> true),
							new Customization("body.glossaryMappingMasterDetails[*].glossaryMappingVersion", (o1, o2) -> true)
							
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


//			PrintWriter actualoutput = new PrintWriter(filePath+"/Output/"+path+"/ActualResponse_"+excelRow.getCellData(i, 15)+"");
//
//			actualoutput.print(ActualResponseindented);
//
//			actualoutput.close();
			ExcelUtils excelRow=new ExcelUtils(excel_path,"TestCases");

			
			StoreAPIResponse.StoreResponse(ActualResponseindented,path,excelRow.getCellData(i, 15),url);


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
