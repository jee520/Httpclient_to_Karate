package Step;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Properties;
import org.apache.http.client.ClientProtocolException;
import org.apache.poi.EmptyFileException;

public class ApiValidation {


	public static void ApiValidation(String Path) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, EmptyFileException, ParseException, InterruptedException {

		int count=0;
		ParamRead propPath=new ParamRead();
		//String pathread=propPath.ParamRead(1);
		String pathread=Path;
		PathReader pathRead=new PathReader();
		String[] pathreadArray=pathread.split(",");
		System.out.println("PathRead:"+pathread);
		try {
			for(int intArray=0;intArray<pathreadArray.length;intArray++)
			{
				String excel_pathread=pathRead.GetPath(pathreadArray[intArray]);
				String [] excel_path=excel_pathread.split(",");
				for(int excelArray=0;excelArray<excel_path.length;excelArray++)
				{
					System.out.println("Path is:"+excel_path[excelArray]);
					ExcelUtils excel1=new ExcelUtils(excel_path[excelArray],"TestCases");
					System.out.println("Current Excel is::"+excel_path[excelArray]);
					int j=excel1.getTotalRows();
					System.out.println("Total Rows in the Excel::"+j);
					//Thread.sleep(5000);
					for(int i=1;i<=j;i++)
					{ExcelUtils excel=new ExcelUtils(excel_path[excelArray],"TestCases");
					System.out.println("Inside Row:"+i);
					String url=excel.getCellData(i, 8);
					String type=excel.getCellData(i, 9);
					String response=excel.getCellData(i, 12);
					String request=excel.getCellData(i, 6);
					String feature_name=excel.getCellData(i, 3);
					System.out.println("url is::"+url);
					System.out.println("TestcaseID:"+excel.getCellData(i, 2));

					Thread.sleep(3000);

					FileInputStream fin1;
					try {
				//Local Executions
												fin1 = new FileInputStream("Parameter/Properties.properties");
						
												Properties prop1=new Properties();
						
												prop1.load(fin1);
						
												url = prop1.getProperty("URL")+url;

				//Pipeline Executions
						//fin1 = new FileInputStream("/opt/service-props/service-properties.props");

//						Properties prop1=new Properties();
//						prop1.load(fin1);

//						if(url.contains("/catalogviewer"))
//						{
//							String url_update=prop1.getProperty("catalog-viewer");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/dsa"))
//						{
//							String url_update=prop1.getProperty("dsa-ui");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/uac"))
//						{
//							String url_update=prop1.getProperty("uac");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/dmp"))
//						{
//							String url_update=prop1.getProperty("data-marketplace");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/dfcsapi"))
//						{
//							String url_update=prop1.getProperty("dfcscore");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/dcaf"))
//						{
//							String url_update=prop1.getProperty("dcaf");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/catalog/"))
//						{
//							String url_update=prop1.getProperty("data-foundation");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/ina/"))
//						{
//							String url_update=prop1.getProperty("ina");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//						}
//						else if(url.contains("/appdataservice/"))
//						{
//							String url_update=prop1.getProperty("app-data-service");
//							System.out.println("URL_UPDATED="+url_update);
//							url = url_update+url;
//							System.out.println("combined "+ url);
//						}
//						else
//						{
//							System.out.println("ERROR, No matching service url found for given url, Please check");
//						}

						System.out.println("URL afer Adding URI is!!!!!!!!!!!!!---"+url);}
					catch(Exception e)
					{
						e.printStackTrace();
					}


					ParamRead prop=new ParamRead();
					String remoteuser=prop.ParamRead(6);
					String cookie=prop.ParamRead(0);

					if(type.equals("GET")) {
						System.out.println("!!Inside Get Request!!");
						System.out.println("ResponseExcel::"+response);
						Get getresponse=new Get();
						getresponse.Get(url,remoteuser,i,response,excel_path[excelArray],count,Path,cookie);
					}
					else if(type.equals("POST"))
					{System.out.println("!!Inside Post Request!!");
					System.out.println("ResponseExcel::"+response);
					Post  postresponse=new Post();
					postresponse.Post(url,remoteuser,i,response,request,excel_path[excelArray],count,Path,cookie);	
					}
					else if(type.equals("DELETE"))
					{   if(feature_name=="Rule")
					{  System.out.println("Inside Rule Delete");
					Delete  deleteresponse=new Delete();
					deleteresponse.Delete(url,remoteuser,i,response,request,excel_path[excelArray],count,Path,cookie);	

					}else {
						System.out.println("!!Inside Delete Request!!");
						System.out.println("ResponseExcel::"+response);
						Delete  deleteresponse=new Delete();
						deleteresponse.Delete(url,remoteuser,i,response,request,excel_path[excelArray],count,Path,cookie);	
					}
					}
					else if(type.equals("PUT"))
					{
						System.out.println("!!Inside Put Request!!");
						System.out.println("ResponseExcel::"+response);
						Put  putresponse=new Put();
						putresponse.Put(url,remoteuser,i,response,request,excel_path[excelArray],count,Path,cookie); 		
					}

					count++;
					}

				}
			}}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		count=0;
	}
}
