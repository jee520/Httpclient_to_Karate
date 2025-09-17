package Step;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ApexUpdateCatalog {

	public static String getCellData(XSSFSheet sheet,int rowNum, int colNum) {
		DataFormatter formatter = new DataFormatter();
		Object value = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
		return (String) value;
	}
	//argument 1 : unique id and argument 2: Result status PASSED/FAILED 
	//get the date 	String timeStamp = new SimpleDateFormat("dd-MM-YYYY").format(Calendar.getInstance().getTime());

	public ApexUpdateCatalog(String UiniqueID, String Eresult, String Aresult, String Eresponse, String Aresponse, String Exestatus, String CuDate,String Pip_Uniqueid, String Result) throws Exception  {
		String filePath = new File("").getAbsolutePath();
		int row;
		Cell cell3 = null;
		FileInputStream PhysicanBindingToRead = new FileInputStream(filePath+"/Output/Catalog_API_Report.xlsx");
		XSSFWorkbook wbPhysical = new XSSFWorkbook(PhysicanBindingToRead);
		XSSFSheet sheetReadPhysical =wbPhysical.getSheet("API");
		int col_length=sheetReadPhysical.getRow(0).getPhysicalNumberOfCells();
		for (int col1 = 0; col1 < col_length; ++col1) {
			Cell cell = sheetReadPhysical.getRow(0).getCell(col1);
			String Excel_Col=cell.getRichStringCellValue().getString();
			if(Excel_Col.contentEquals(("Test_Case_Id"))){
				for(row=0;row<sheetReadPhysical.getPhysicalNumberOfRows(); ++row) {
					String Unique_id=getCellData(sheetReadPhysical,row, col1);
//					System.out.println("Test_Case_Id is : "+Unique_id);
//					System.out.println("Unique ID is : "+UiniqueID);
					if(Unique_id.contentEquals(UiniqueID))
					{break;}	            			
				}
				for (int col2 = 0; col2 < col_length; ++col2) {
					Cell cell1 = sheetReadPhysical.getRow(0).getCell(col2);
					String Excel_Col2=cell1.getRichStringCellValue().getString();
					if(Excel_Col2.contentEquals(("Expected_Result"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);							
						cell3.setCellValue(Eresult);

					}
					
					if(Excel_Col2.contentEquals(("Actual_Result"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);							
						cell3.setCellValue(Aresult);

					}
					
					if(Excel_Col2.contentEquals(("Expected Response"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);						
						cell3.setCellValue(Eresponse);

					}
					
					if(Excel_Col2.contentEquals(("Actual Response"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);									
						cell3.setCellValue(Aresponse);

					}
					
					if(Excel_Col2.contentEquals(("Execution_Status"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);									
						cell3.setCellValue(Exestatus);

					}
					
					if(Excel_Col2.contentEquals(("Execution_date"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);									
						cell3.setCellValue(CuDate);

					}

					if(Excel_Col2.contentEquals(("Pipeline_Id"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);									
						cell3.setCellValue(Pip_Uniqueid);

					}

					if(Excel_Col2.contentEquals(("EXECUTION_STATUS"))){
						cell3 = sheetReadPhysical.getRow(row).createCell(col2);									
						cell3.setCellValue(Result);

					}

				}	

				FileOutputStream output_file =new FileOutputStream(new File(filePath+"/Output/Catalog_API_Report.xlsx"));  
				wbPhysical.write(output_file);
				output_file.close();
			}	            	
		}	    	      		
	}


}	





