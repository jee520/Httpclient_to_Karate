package Step;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.ContentType;

public class Post {
    static String[] JsonParameters = null;
    static String Request = null;
    static String Id1 = null;
    static String Jsonrequest = null;
    static String Id = null;
    static JSONParser parserForRequest;
    static JSONObject json;
    static JSONObject js;

    public static Logger log = Logger.getLogger(Get.class);
    public static String filePath = new File("").getAbsolutePath();

    public void Post(String url, String ofsuser, int i, String response, String requestBody, String excel_path, int count, String path, String Cookies) {
    	CloseableHttpResponse response1 = null;
        String ActualResponse = ""; 
    	
    	try {
            ExcelUtils excelRow1 = new ExcelUtils(excel_path, "TestCases");

            FileInputStream fin1 = new FileInputStream("Parameter/Properties.properties");
            Properties prop1 = new Properties();
            prop1.load(fin1);
            FileInputStream fin = new FileInputStream("Parameter/ExcelProperties.properties");
            Properties prop = new Properties();
            prop.load(fin);

            String reqInputJson1 = prop.getProperty(path + "_InputJson");
            String reqJsonFileName = excelRow1.getCellData(i, 6);
            String reqInputJson = reqInputJson1 + reqJsonFileName;
            String fileexcel_path1 = prop.getProperty(path + "_InputJson");
            String fileexcel_pathFileName = excelRow1.getCellData(i, 39);
            String fileexcel_path = fileexcel_path1 + fileexcel_pathFileName;
            log.info("The path for the input request body json is ::" + reqInputJson);

            Path reqfileName = Path.of(reqInputJson);
            // Now calling Files.readString() method to read the file
            requestBody = Files.readString(reqfileName);

            if (url.contains("/dsa/edd")) {
                log.info("Inside EDD or Data extraction, to replace dsID");

                Path dsidreqfileName = Path.of(filePath + "/Output/" + path + "/ActualResponse_eds_response.json");
                // Now calling Files.readString() method to read the file
                String dsidrequestBody = Files.readString(dsidreqfileName);

                if (requestBody.contains("#dsid#")) {
                    String dsid = GetValueFromJson.GetValueJson(dsidrequestBody, "dsId");
                    requestBody = requestBody.replace("#dsid#", dsid);
                    log.info("Replaced #dsid# in request body with " + dsid);
                }
            }
            
            if (requestBody.contains("#version#")) {
                log.info("Detected #version# placeholder in request body");

                String versionSourceFile = null;
                String versionJsonContent = null;
                String versionKey = null;

                if (url.contains("glossarymapping")) {
                    versionSourceFile = filePath + "/Output/" + path + "/ActualResponse_glossarymappingmaster.json";
                    versionKey = "glossaryMappingVersion";
                } else if (url.contains("connector")) {
                    versionSourceFile = filePath + "/Output/" + path + "/ActualResponse_connectors_without_Filter.json";
                    versionKey = "connectorVersion";
                } else if (url.contains("/catalog/publish")) {
                	log.info("The requestbody read");
                	System.out.println("catalogSummaryVersion");
                    versionSourceFile = filePath + "/Output/" + path + "/ActualResponse_SummaryWithID1.json";
                    versionKey = "ctlgVer";
                    log.info("The requestbody read :" + versionKey);
                }

                try {
                    if (versionSourceFile != null) {
                        versionJsonContent = Files.readString(Path.of(versionSourceFile));
                        String latestVersion = GetValueFromJson.getversionID(versionJsonContent, versionKey);

                        if (latestVersion != null && !latestVersion.isEmpty()) {
                            requestBody = requestBody.replace("#version#", latestVersion);
                            log.info("Replaced #version# with " + latestVersion);
                        } else {
                            log.warn("Could not extract version from file: " + versionSourceFile);
                        }
                    } else {
                        log.warn("No version mapping found for URL: " + url);
                    }
                } catch (Exception e) {
                    log.error("Error while replacing #version#: " + e.getMessage());
                    e.printStackTrace();
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
            if(requestBody.contains("#publishTenantID#")) {
            	String publishTenant = prop1.getProperty("publishTenant");
            	requestBody = requestBody.replace("#publishTenantID#", publishTenant.toUpperCase());            	            	
            }
            if(requestBody.contains("#clientTenantID#")) {
            	String clientTenant = prop1.getProperty("clientTenant");
            	requestBody = requestBody.replace("#clientTenantID#", clientTenant.toUpperCase());            	            	
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

            log.info("The Final Request Body is : " + requestBody);

            ConnectionParam conn = new ConnectionParam();
            CloseableHttpClient httpClient = conn.GetConnectionParam();
            JSONParser parser = new JSONParser();
            JSONObject postRequest = (JSONObject) parser.parse(requestBody);
            log.info("Request is:" + postRequest);
            
            if (url.contains("/dsa/edd/##eddid##")) {
                Path eddidjsonpath = Path.of(filePath + "/Output/" + path + "/ActualResponse_" + excelRow1.getCellData((i - 1), 15) + "");
                String eddsuccessmsg = Files.readString(eddidjsonpath);

                String eddid = GetValueFromJson.GetValueJson(eddsuccessmsg, "id");
                url = url.replace("##eddid##", eddid);
            }
            if (url.contains("/dsa/connector/##cid##")) {
                Path connidjsonpath = Path.of(filePath + "/Output/" + path + "/ActualResponse_" + excelRow1.getCellData((i - 1), 15) + "");
                String connectorsuccessmsg = Files.readString(connidjsonpath);

                String cid = GetValueFromJson.GetValueJson(connectorsuccessmsg, "id");
                url = url.replace("##cid##", cid);
            }
            if (url.contains("/dsa/connector/##ccid##")) {
                Path connidjsonpath = Path.of(filePath + "/Output/" + path + "/ActualResponse_" + excelRow1.getCellData((i - 2), 15) + "");
                String connectorsuccessmsg = Files.readString(connidjsonpath);

                String cid = GetValueFromJson.GetValueJson(connectorsuccessmsg, "id");
                url = url.replace("##ccid##", cid);
            }

            log.info("Updated URL for the pipeline is:" + url);

            HttpPost request = new HttpPost(url);
            request.setHeader("Accept", "*/*");
            request.setHeader("ofs_remote_user", ofsuser);
            request.setHeader("ofs_remote_username", "Saikumar Dontula");
            request.setHeader("Cookie", Cookies);

            if (url.endsWith("/upload")) {
                String filePathForUpload = fileexcel_path;
                File fileToUpload = new File(filePathForUpload);
                log.error("File found at path: " + filePathForUpload);

                if (!fileToUpload.exists()) {
                    log.error("File not found at path: " + filePathForUpload);
                    return;
                }

                String jsonStringBody = requestBody;
                StringBody jsonBody = new StringBody(jsonStringBody, ContentType.APPLICATION_JSON);

                FileBody fileBody = new FileBody(fileToUpload);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                builder.addPart("file", fileBody);
                builder.addPart("data", jsonBody);
                builder.setContentType(org.apache.http.entity.ContentType.MULTIPART_FORM_DATA);

                request.setEntity(builder.build());

                response1 = httpClient.execute(request);
                int statusCode = response1.getStatusLine().getStatusCode();
                log.info("Response status code: " + statusCode);
                ActualResponse = EntityUtils.toString(response1.getEntity(), "UTF-8");
                log.info("File upload response: " + ActualResponse);

                processResponse(excel_path, i, response1, ActualResponse);

            } else {
            	
                request.setHeader("Content-Type", "application/json");


                StringEntity params = new StringEntity(postRequest.toJSONString());
                params.setContentType("application/json");

                request.setEntity(params);
                log.info("Params:" + params);

                response1 = httpClient.execute(request);
                ActualResponse = EntityUtils.toString(response1.getEntity(), "UTF-8");
                log.info("Response2" + ActualResponse);

                processResponse(excel_path, i, response1, ActualResponse);

            }

            ObjectMapper mapper = new ObjectMapper();
            String ActualResponseindented = "";

            try {
                Object json = mapper.readValue(ActualResponse, Object.class);
                ActualResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (Exception rsnull) {
                log.error("Inside catch as actual response is null: ");
                log.error(rsnull);
                ActualResponseindented = ActualResponse;
            }

            ExcelUtils excelRow = new ExcelUtils(excel_path, "TestCases");
            log.info("The Row is:" + i);
            String InputJson1 = prop.getProperty(path + "_InputJson");
            String JsonFileName = excelRow.getCellData(i, 15);
            String InputJson = InputJson1 + JsonFileName;
            log.info("The path for the input json is ::" + InputJson);
            String MappingRequired = excelRow.getCellData(i, 16);
            log.info("The Mapping is Required?:" + MappingRequired);

            Path fileName = Path.of(InputJson);

            // Now calling Files.readString() method to read the file
            String Jsonresponse = Files.readString(fileName);

            log.info("The input json response body is ::" + Jsonresponse);  //--commented

            ObjectMapper mapper1 = new ObjectMapper();
            Object json1 = mapper1.readValue(Jsonresponse, Object.class);

            String ExpectedResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);

            boolean responseCheck;
            try {
                if (url.contains("/dsa/edd")) {
                    log.info("Inside Custom Comparator EDD or Extraction");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("body.id", (o1, o2) -> true)));
                    responseCheck = true;

                }
                if (url.contains("/dsa/vars")) {
                    log.info("Inside Custom Comparator Create Parameter");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("body.id", (o1, o2) -> true)));
                    responseCheck = true;

                } else if (url.contains("/dsa/connector/")) {
                    log.info("Inside Custom Comparator Connectors");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("body.id", (o1, o2) -> true)));
                    responseCheck = true;
                } else if (url.contains("/uac/")) {
                    log.info("Inside Custom Comparator UAC");

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
                } else if (url.contains("getFileUpldHist")) {
                    log.info("Inside Custom Comparator File Upload hist");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("body", (o1, o2) -> true)));
                    responseCheck = true;
                } else if (url.contains("getObjStoreParUrl")) {
                    log.info("Inside Custom Comparator File Upload object PAR url");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("body.success", (o1, o2) -> true)
                    ));
                    responseCheck = true;
                } else if (url.contains("/ina/v1/tasks")) {
                    log.info("Inside /ina/v1/tasks data View API");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.STRICT,
                            new Customization("msgBody", (o1, o2) -> true)
                    ));
                    responseCheck = true;

                } else if (url.contains("/catalogviewer/v1/dfcs/businessterms/get")) {
                    responseCheck = JsonCompSupporter.getStatusCompare(ActualResponseindented, ExpectedResponseindented);
                } else if (url.contains("/catalogviewer/v1/dfcs/glossaries/get")) {
                    responseCheck = JsonCompSupporter.getStatusCompare(ActualResponseindented, ExpectedResponseindented);
                } else if (url.contains("/catalogviewer/v1/dfcs/datamodel/")) {
                    responseCheck = JsonCompSupporter.getStatusCompare(ActualResponseindented, ExpectedResponseindented);
                } else if (url.contains("/catalogviewer/v1/dfcs/connector/get")) {
                    log.info("Connector summary api");

                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, new CustomComparator(JSONCompareMode.LENIENT,
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
				            new Customization("ctlgSrcVer", (o1, o2) -> true)
							));
					responseCheck = true;
					
				}else {
                    JSONAssert.assertEquals(ActualResponseindented, ExpectedResponseindented, JSONCompareMode.LENIENT);
                    responseCheck = true;
                }
            } catch (AssertionError e) {
                log.info("inside AssertionError catch");
                log.error(e.getMessage());
                responseCheck = false;
            } catch (JSONException e1) {
                log.info("inside JSONException catch");
                log.error(e1.getMessage());
                responseCheck = false;
            }

            log.info("Response check::" + responseCheck);
            log.info("Response code::" + response1.getStatusLine().getStatusCode());

            PrintWriter actualoutput = new PrintWriter(filePath + "/Output/" + path + "/ActualResponse_" + excelRow.getCellData(i, 15) + "");
            actualoutput.print(ActualResponseindented);
            actualoutput.close();

            if (response1.getStatusLine().getStatusCode() == 200 && responseCheck) {
                log.info("Status::Passed");
                ExcelWrite pass = new ExcelWrite();
                pass.ExcelWritePass(excel_path, "TestCases", i, ActualResponseindented, count);

            } else if (response1.getStatusLine().getStatusCode() == 500 && responseCheck) {
                log.info("Status::Passed");
                ExcelWrite pass = new ExcelWrite();
                pass.ExcelWritePass(excel_path, "TestCases", i, Integer.toString(response1.getStatusLine().getStatusCode()), count);

            } else if (response1.getStatusLine().getStatusCode() == 400 && responseCheck) {
                log.info("Status::Passed");
                ExcelWrite pass = new ExcelWrite();
                pass.ExcelWritePass(excel_path, "TestCases", i, Integer.toString(response1.getStatusLine().getStatusCode()), count);

            } else {
                log.info("Status::Failed");
                ExcelWrite fail = new ExcelWrite();
                fail.ExcelWriteFail(excel_path, "TestCases", i, ActualResponseindented, ExpectedResponseindented, url, count);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processResponse(String excel_path, int i, CloseableHttpResponse response1, String ActualResponse) {
        try {
            // You can reuse the existing response processing logic
            ObjectMapper mapper = new ObjectMapper();
            String ActualResponseindented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(ActualResponse, Object.class));

            // Example for checking status code
            if (response1.getStatusLine().getStatusCode() == 200) {
                log.info("Request successful!");
                ExcelWrite pass = new ExcelWrite();
                pass.ExcelWritePass(excel_path, "TestCases", i, ActualResponseindented, 0);  // Adjust count value as needed
            } else {
                log.error("Request failed with status code: " + response1.getStatusLine().getStatusCode());
                ExcelWrite fail = new ExcelWrite();
                fail.ExcelWriteFail(excel_path, "TestCases", i, ActualResponseindented, "Expected Response", "url", 0);  // Adjust accordingly
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
