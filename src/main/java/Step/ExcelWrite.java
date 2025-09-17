package Step;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelWrite extends Get{

	static XSSFWorkbook workbook;
	static XSSFWorkbook workbookOut;
	static XSSFSheet sheet;
	static XSSFSheet sheetOut;
	static int r=1;
	public static String filePath = new File("").getAbsolutePath();

	//Pipeline Execution
//	public static String TENANT_ID=System.getenv("TENANT_ID");
//	public static String PIPELINE_ID=System.getenv("PIPELINE_ID").replace("P","");
//	public static String TENANT_ID_PIPELINE_ID=TENANT_ID+"_"+PIPELINE_ID;


	//Local Execution
		public static String TENANT_ID="aq38v5-prd";
		public static String PIPELINE_ID="P864396".replace("P","");
		public static String TENANT_ID_PIPELINE_ID=TENANT_ID+"_"+PIPELINE_ID;


	public void ExcelWritePass(String excelPath, String sheetName,int row,String response,int count) {
		try {
			log.info("Excel pass Path filestream:"+excelPath);
			log.info("Cuurent Row value in Pass to update is : "+r);
			log.info("Input file updating Started for : "+r);
			FileInputStream file = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);

			//Cell testcaseidCell=sheet.getRow(row).getCell(2);
			//String testCaseId=testcaseidCell.toString();

			Cell jsonid=sheet.getRow(row).getCell(15);
			String jsonidfinal=jsonid.toString();

			log.info("Row:"+row);
			Cell cell=sheet.getRow(row).createCell(11);
			Cell cell1=sheet.getRow(row).createCell(13);
			cell.setCellValue("status=200");

			long lenght = response.length();

			log.info("The actual Response lenght is: "+lenght);
			//			if(lenght>=32767)
			//			{
			Cell cell12=sheet.getRow(row).createCell(12);
			cell12.setCellValue(filePath+"/Output/ExpectedResponse_"+jsonidfinal+"");
			cell1.setCellValue(filePath+"/Output/ActualResponse_"+jsonidfinal+"");
			//			}
			//			else
			//			{
			//				cell1.setCellValue(response);
			//			}
			Cell statusCell=sheet.getRow(row).createCell(14);
			statusCell.setCellValue("Passed");

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();  
			String datetime = formatter.format(date);

			log.info(datetime);  
			Cell cellno30=sheet.getRow(row).createCell(30);
			cellno30.setCellValue(datetime);

			Cell cellno31=sheet.getRow(row).createCell(31);
			cellno31.setCellValue(TENANT_ID_PIPELINE_ID);

			Cell cellno32=sheet.getRow(row).createCell(32);
			cellno32.setCellValue("PASSED");

			FileOutputStream outFile =new FileOutputStream(new File(excelPath));
			workbook.write(outFile);
			file.close();

			log.info("Input file updating ENS for : "+r);



			//Writing Output file
			//			FileInputStream fileout = new FileInputStream(excelPath);
			//			workbookOut = new XSSFWorkbook(fileout);
			//			sheet = workbookOut.getSheet(sheetName);
			//
			//			log.info("Excepth Path "+excelPath);
			//
			//
			//			FileOutputStream outputFile =new FileOutputStream(new File(filePath+"/Output/Report_Summary.xlsx"));
			//			workbookOut.write(outputFile);
			//			fileout.close();



			//Copying the whole Excel Details into the output excel//
			//Copying

			log.info("Copying the whole Excel Details into the output excel - STARTS ");

			FileInputStream file1 = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(file1);
			sheet = workbook.getSheet(sheetName);
			String testCaseIdapex = ApexUpdateCatalog.getCellData(sheet,row, 2);
			//			Cell testcaseidCell1=sheet.getRow(row).getCell(2);
			//			String testCaseIdapex=testcaseidCell1.toString();
			Cell expectedresultCell=sheet.getRow(row).getCell(10);
			String ExpectedResult=expectedresultCell.toString();
			Cell actualresultCell=sheet.getRow(row).getCell(11);
			String ActualResult=actualresultCell.toString();
			Cell expectedresponseCell=sheet.getRow(row).getCell(12);
			String ExpectedResponse=expectedresponseCell.toString();
			Cell actualresponseCell=sheet.getRow(row).getCell(13);
			String ActualResponse=actualresponseCell.toString();
			Cell executionstatusCell=sheet.getRow(row).getCell(14);
			String Executionstatus=executionstatusCell.toString();

			Cell executiondatesCell=sheet.getRow(row).getCell(30);
			String ExecutionDate=executiondatesCell.toString();

			Cell pipelineidCell=sheet.getRow(row).getCell(31);
			String PipelineID=pipelineidCell.toString();

			Cell executionstatusapex=sheet.getRow(row).getCell(32);
			String ApexExecutionStatus=executionstatusapex.toString();
			log.info("Copying the whole Excel Details into the output excel - ENDS ");


			//Ending

			//end
			//End of Copying//

			log.info("Updating Apex Template output excel - STARTS ");

			ApexUpdateCatalog apex = new ApexUpdateCatalog(testCaseIdapex,ExpectedResult,ActualResult,ExpectedResponse,ActualResponse,Executionstatus,
					ExecutionDate,PipelineID,ApexExecutionStatus);

			log.info("Updating Apex Template output excel - ENDS ");

			//			FileInputStream outputfile=new FileInputStream("Output//Report_Summary.xlsx");
			//			workbookOut=new XSSFWorkbook(outputfile);
			//			sheetOut=workbookOut.getSheet("API");
			//			Cell cellno1=sheetOut.getRow(r).createCell(10);
			//			cellno1.setCellValue(ExpectedResult);
			//
			//			Cell cellno2=sheetOut.getRow(r).createCell(11);
			//			cellno2.setCellValue(ActualResult);
			//
			//			Cell cellno3=sheetOut.getRow(r).createCell(12);
			//			cellno3.setCellValue(ExpectedResponse);
			//
			//			Cell cellno4=sheetOut.getRow(r).createCell(13);
			//			cellno4.setCellValue(ActualResponse);
			//
			//			Cell cellno5=sheetOut.getRow(r).createCell(14);
			//			cellno5.setCellValue(Executionstatus);
			//
			//			Cell cellno6=sheetOut.getRow(r).createCell(30);
			//			cellno6.setCellValue(ExecutionDate);
			//
			//			Cell cellno7=sheetOut.getRow(r).createCell(31);
			//			cellno7.setCellValue(PipelineID);
			//
			//			Cell cellno8=sheetOut.getRow(r).createCell(32);
			//			cellno8.setCellValue(ApexExecutionStatus);
			//
			//			FileOutputStream outFileout =new FileOutputStream(new File("Output//Report_Summary.xlsx"));
			//			workbookOut.write(outFileout);			
			//			outputfile.close();




		}
		catch(Exception exp)
		{
			log.error(exp.getMessage());
			log.error(exp.getCause());
			exp.printStackTrace();
		}

		r++;
	}

	public void ExcelWriteFail(String excelPath, String sheetName,int row,String ActualResponse,String response,String url,int count) {
		try {
			log.info("Excel  fail Path in filestream:"+excelPath);
			log.info("Cuurent Row value in Fail to update is : "+r);
			log.info("Input Excel Failed Status Update - STARTS : "+r);
			FileInputStream file = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);

			//Cell testcaseidCell=sheet.getRow(row).getCell(2);
			//String testCaseId=testcaseidCell.toString();

			Cell jsonid=sheet.getRow(row).getCell(15);
			String jsonidfinal=jsonid.toString();

			Cell cell=sheet.getRow(row).createCell(11);

			Cell cell1=sheet.getRow(row).createCell(13);
			cell.setCellValue("status=failed");

			long lenght = ActualResponse.length();

			log.info("The actual Response lenght is: "+lenght);

			//			if(lenght>=32767)
			//			{

			Cell cell12=sheet.getRow(row).createCell(12);
			cell12.setCellValue(filePath+"/Output/ExpectedResponse_"+jsonidfinal+"");
			cell1.setCellValue(filePath+"/Output/ActualResponse_"+jsonidfinal+"");

			//			}
			//			else
			//			{
			//				cell1.setCellValue(ActualResponse);
			//			}

			Cell statusCell=sheet.getRow(row).createCell(14);
			statusCell.setCellValue("Failed");


			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();  
			String datetime = formatter.format(date);

			log.info(datetime);  
			Cell cellno30=sheet.getRow(row).createCell(30);
			cellno30.setCellValue(datetime);

			Cell cellno31=sheet.getRow(row).createCell(31);
			cellno31.setCellValue(TENANT_ID_PIPELINE_ID);

			Cell cellno32=sheet.getRow(row).createCell(32);
			cellno32.setCellValue("FAILED");


			FileOutputStream outFile =new FileOutputStream(new File(excelPath));
			workbook.write(outFile);
			file.close();

			log.info("Input Excel Failed Status Update - ENDS : "+r);


			//			FileInputStream fileout = new FileInputStream(excelPath);
			//			workbookOut = new XSSFWorkbook(fileout);
			//			sheet = workbookOut.getSheet(sheetName);
			//
			//
			//			FileOutputStream outputFile =new FileOutputStream(new File(filePath+"/Output/Report_Summary.xlsx"));
			//			workbookOut.write(outputFile);
			//			fileout.close();




			//Copying the whole Excel Details into the output excel//
			//Copying

			log.info("Copying the whole Excel Details into the output excel - STARTS : ");

			FileInputStream file1 = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(file1);
			sheet = workbook.getSheet(sheetName);
			String testCaseIdapex = ApexUpdateCatalog.getCellData(sheet,row, 2);
			Cell expectedresultCell=sheet.getRow(row).getCell(10);
			String ExpectedResult=expectedresultCell.toString();
			Cell actualresultCell=sheet.getRow(row).getCell(11);
			String ActualResult=actualresultCell.toString();
			Cell expectedresponseCell=sheet.getRow(row).getCell(12);
			String ExpectedResponse=expectedresponseCell.toString();
			Cell actualresponseCell=sheet.getRow(row).getCell(13);
			String ActualResponse1=actualresponseCell.toString();
			Cell executionstatusCell=sheet.getRow(row).getCell(14);
			String Executionstatus=executionstatusCell.toString();

			Cell executiondatesCell=sheet.getRow(row).getCell(30);
			String ExecutionDate=executiondatesCell.toString();

			Cell pipelineidCell=sheet.getRow(row).getCell(31);
			String PipelineID=pipelineidCell.toString();

			Cell executionstatusapex=sheet.getRow(row).getCell(32);
			String ApexExecutionStatus=executionstatusapex.toString();

			log.info("Copying the whole Excel Details into the output excel - ENDS : ");


			//Ending

			//end
			//End of Copying//

			log.info("Updating Apex Template output excel - STARTS ");

			ApexUpdateCatalog apex = new ApexUpdateCatalog(testCaseIdapex,ExpectedResult,ActualResult,ExpectedResponse,ActualResponse1,Executionstatus,
					ExecutionDate,PipelineID,ApexExecutionStatus);

			log.info("Updating Apex Template output excel - ENDS ");

			//			FileInputStream outputfile=new FileInputStream("Output//Report_Summary.xlsx");
			//			workbookOut=new XSSFWorkbook(outputfile);
			//			sheetOut=workbookOut.getSheet("API");
			//			Cell cellno1=sheetOut.getRow(r).createCell(10);
			//			cellno1.setCellValue(ExpectedResult);
			//
			//			Cell cellno2=sheetOut.getRow(r).createCell(11);
			//			cellno2.setCellValue(ActualResult);
			//
			//			Cell cellno3=sheetOut.getRow(r).createCell(12);
			//			cellno3.setCellValue(ExpectedResponse);
			//
			//			Cell cellno4=sheetOut.getRow(r).createCell(13);
			//			cellno4.setCellValue(ActualResponse1);
			//
			//			Cell cellno5=sheetOut.getRow(r).createCell(14);
			//			cellno5.setCellValue(Executionstatus);
			//
			//			Cell cellno6=sheetOut.getRow(r).createCell(30);
			//			cellno6.setCellValue(ExecutionDate);
			//
			//			Cell cellno7=sheetOut.getRow(r).createCell(31);
			//			cellno7.setCellValue(PipelineID);
			//
			//			Cell cellno8=sheetOut.getRow(r).createCell(32);
			//			cellno8.setCellValue(ApexExecutionStatus);
			//
			//			FileOutputStream outFileout =new FileOutputStream(new File("Output//Report_Summary.xlsx"));
			//			workbookOut.write(outFileout);			
			//			outputfile.close();








			//	----------------------------------------------------------------------------------------//
			//------Code for Log File-----------------------------------//




            log.info("Text written to Output.txt file -- STARTS");
			String[] responseArray=response.split(",");
			String[] ActualResponseArray=ActualResponse.split(",");
			ArrayList<String> arr_res=new ArrayList<String>();
			ArrayList<String> arr_act=new ArrayList<String>();
			for(int i=0;i<responseArray.length;i++)
				arr_res.add(responseArray[i]);
			ArrayList<String> arr_res_temp=new ArrayList<String>(arr_res);
			for(int i=0;i<ActualResponseArray.length;i++)
				arr_act.add(ActualResponseArray[i]);
			log.info("response:"+arr_res);
			log.info("Actual:"+arr_act);
			arr_res.removeAll(arr_act);
			arr_act.removeAll(arr_res_temp);
			log.info("After Removal:"+arr_res);
			log.info("After Removal:"+arr_act);
			//-------------------------------------------------//

			FileWriter fw = new FileWriter(filePath+"/Output/ErrorDesc.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write("-------------------------------------------------------------------------------");
			bw.newLine();
			bw.write("API URL-->>"+url);
			bw.newLine();
			bw.write("Response does not match!!->ActualResponse:"+ActualResponse);
			bw.newLine();
			bw.write("Response does not match!!->ExpectedResponse:"+response);
			bw.newLine();
			bw.newLine();
			bw.write("Difference from Actual Response -> "+arr_act);
			bw.newLine();
			bw.write("Difference from Expected Response -> "+arr_res);
			bw.close();


			log.info("Text written successfully into the file.");

			log.info("Text written to Output.txt file -- Ends");

		}catch(Exception exp) {
			log.error(exp.getMessage());
			log.error(exp.getCause());
			exp.printStackTrace();
		}
		r++;}


}
