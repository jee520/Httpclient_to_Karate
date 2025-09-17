package Step;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class PathReader {
	public String GetPath(String reader) throws IOException
	{
		FileInputStream fin1=new FileInputStream("Parameter/ExcelProperties.properties");
		Properties prop1=new Properties();
		prop1.load(fin1);
				
		String Prepay_Rule=prop1.getProperty("Prepay_Rule");
		String Reprice_Pattern=prop1.getProperty("Reprice_Pattern");
		String Eco_Indicator=prop1.getProperty("Eco_Indicator");
		String Prepay_Model=prop1.getProperty("Prepay_Model");
		String Discount_Method=prop1.getProperty("Discount_Method");
		String Forecast_Rate=prop1.getProperty("Forecast_Rate");
		String Behavior_Pattern=prop1.getProperty("Behavior_Pattern");
		String Currency_Data=prop1.getProperty("Currency_Data");
		String Currency_Rates=prop1.getProperty("Currency_Rates");
		String Holiday_Calender=prop1.getProperty("Holiday_Calender");
		String CFE_Edit=prop1.getProperty("CFE_Edit");
		String CFE_Process=prop1.getProperty("CFE_Process");
		String CFE_Report=prop1.getProperty("CFE_Report");
		String IRC_Detail=prop1.getProperty("IRC_Detail");
		String CFE_Config=prop1.getProperty("CFE_Config");
		String CFE_EditConfig=prop1.getProperty("CFE_EditConfig");
		String Prerequisite_Details=prop1.getProperty("Prerequisite_Details");
		String CFE_ALLAPI=prop1.getProperty("CFE_ALLAPI");
		String GenerateCashFlow=prop1.getProperty("GenerateCashFlow");
		String AnyPath=prop1.getProperty(reader);
//		System.out.println("CFE Services::"+CFE_ALLAPI);
		
		
		
		System.out.println("PathReader:"+reader);
		if (reader.equals("Prepay_Rule"))
			return Prepay_Rule;
		else if (reader.equals("Reprice_Pattern"))
			return Reprice_Pattern;
		else if (reader.equals("Eco_Indicator"))
			return Eco_Indicator;
		else if (reader.equals("Prepay_Model"))
			return Prepay_Model;
		else if (reader.equals("Discount_Method"))
			return Discount_Method;
		else if (reader.equals("Forecast_Rate"))
			return Forecast_Rate;	
		else if (reader.equals("Behavior_Pattern"))
			return Behavior_Pattern;
		else if (reader.equals("Currency_Data"))
			return Currency_Data;
		else if (reader.equals("Currency_Rates"))
			return Currency_Rates;
		else if (reader.equals("Holiday_Calender"))
			return Holiday_Calender;
		else if (reader.equals("CFE_Edit"))
			return CFE_Edit;
		else if (reader.equals("CFE_Process"))
			return CFE_Process;
		else if (reader.equals("CFE_Report"))
			return CFE_Report;
		else if (reader.equals("IRC_Detail"))
			return IRC_Detail;
		else if (reader.equals("CFE_Config"))
			return CFE_Config;
		else if (reader.equals("CFE_EditConfig"))
			return CFE_EditConfig;
		else if (reader.equals("Prerequisite_Details"))
			return Prerequisite_Details;
		else if (reader.equals("CFE_ALLAPI"))
			return CFE_ALLAPI;
		else if(reader.equals("GenerateCashFlow"))
			return GenerateCashFlow;
		else
			return AnyPath;
				
		
	}
}