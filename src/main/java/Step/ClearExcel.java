package Step;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClearExcel {
public static void main(String[] args) throws IOException {
	FileInputStream file = new FileInputStream("Parameter/Report_Summary.xlsx");
	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheet("TestCases");
	int size=sheet.getLastRowNum();
	for(int i=1;i<=size;i++)
	{
		sheet.removeRow(sheet.getRow(i));
	}

FileOutputStream outFile=new FileOutputStream("Parameter/Report_Summary.xlsx");
workbook.write(outFile);
file.close();
}
}
