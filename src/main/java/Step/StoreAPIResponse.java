package Step;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class StoreAPIResponse {

	public static String filePath = new File("").getAbsolutePath();

	public static String modulenamevalues = "";


	public static void StoreResponse(String Response,String path,String filename, String url) throws IOException

	{
		FileInputStream fin1;

		fin1 = new FileInputStream("Parameter/Properties.properties");

		Properties prop1=new Properties();

		prop1.load(fin1);

		String createresponse = prop1.getProperty("storeResponse");

		if(createresponse.equals("Y"))
		{

			if(path.equals("BusinessTerms"))
			{
				modulenamevalues = prop1.getProperty("bt");
			}
			else if(path.equals("Entity"))
			{
				modulenamevalues = prop1.getProperty("entity");
			}
			else if(path.equals("ExternalGlossary"))
			{
				modulenamevalues = prop1.getProperty("eg");
			}
			else if(path.equals("BIRDMapper"))
			{
				modulenamevalues = prop1.getProperty("bird");
			}
			else if(path.equals("DQChecks"))
			{
				modulenamevalues = prop1.getProperty("dqchecks");
			}
			else if(path.equals("DQGroups"))
			{
				modulenamevalues = prop1.getProperty("dqgroups");
			}
			else if(path.equals("DQException"))
			{
				modulenamevalues = prop1.getProperty("dqexceptionreport");
			}
			else if(path.equals("EDD"))
			{
				modulenamevalues = prop1.getProperty("edd");
			}
			else if(path.equals("Connectors"))
			{
				modulenamevalues = prop1.getProperty("connectors");
			}
			else if(path.equals("UAC"))
			{
				modulenamevalues = prop1.getProperty("uac");
			}
			else if(path.equals("RunExecParam"))
			{
				modulenamevalues = prop1.getProperty("runexeparam");
			}
			else if(path.equals("ReportConfiguration"))
			{
				modulenamevalues = prop1.getProperty("reportconfiguration");
			}
			else if(path.equals("FileUpload"))
			{
				modulenamevalues = prop1.getProperty("fileupload");
			}
			else if(path.equals("FiscalPeriod"))
			{
				modulenamevalues = prop1.getProperty("fiscalperiod");
			}
			else if(path.equals("LegalEntity"))
			{
				modulenamevalues = prop1.getProperty("legalentity");
			}
			else if(path.equals("Parameter"))
			{
				modulenamevalues = prop1.getProperty("parameter");
			}
			else if(path.equals("HomePage"))
			{
				modulenamevalues = prop1.getProperty("homepage");
			}
			else if(path.equals("GlossaryMapping"))
			{
				modulenamevalues = prop1.getProperty("GlossaryMapping");
			}
			else if(path.equals("CatalogSummary"))
			{
				modulenamevalues = prop1.getProperty("CatalogSummary");
			}
			else if(path.equals("CatalogDataModel"))
			{
				modulenamevalues = prop1.getProperty("CatalogDataModel");
			}
			else if(path.equals("CatalogBusinessTerm"))
			{
				modulenamevalues = prop1.getProperty("CatalogBusinessTerm");
			}
			else if(path.equals("INA"))
			{
				modulenamevalues = prop1.getProperty("INA");
			}
			else if(path.equals("BusinessAnalyst"))
			{
				modulenamevalues = prop1.getProperty("BusinessAnalyst");
			}
			else if(path.equals("DataGovernance"))
			{
				modulenamevalues = prop1.getProperty("DataGovernance");
			}
			else if(path.equals("CatalogConnector"))
			{
				modulenamevalues = prop1.getProperty("CatalogConnector");
			}
			else if(path.equals("ADS"))
			{
				modulenamevalues = prop1.getProperty("ADS");
			}
			else
			{
				System.out.println("Check given property is added or not");
			}

			if(modulenamevalues.equals("all"))
			{

				PrintWriter actualoutput = new PrintWriter(filePath+"/Output/"+path+"/ActualResponse_"+filename+"");

				actualoutput.print(Response);

				actualoutput.close();
			}
			else
			{
				String[] modulevalues = modulenamevalues.split(",");

				for(int loop=0;loop<modulevalues.length;loop++)
				{
					if(url.contains(modulevalues[loop]))
					{
						PrintWriter actualoutput = new PrintWriter(filePath+"/Output/"+path+"/ActualResponse_"+filename+"");

						actualoutput.print(Response);

						actualoutput.close();
					}
				}
			}
		}





	}


}
