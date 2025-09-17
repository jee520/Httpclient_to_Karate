package Step;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ParamRead {
	
	public String ParamRead(int i) throws IOException {
	
	FileInputStream fin1=new FileInputStream("Parameter/Properties.properties");
	Properties prop1=new Properties();
	prop1.load(fin1);
	
	String Cookie1=prop1.getProperty("Cookie");
	String path=prop1.getProperty("Path");
	String tanent1=prop1.getProperty("Tanent");
	String workspace1=prop1.getProperty("Workspace");
	String service1=prop1.getProperty("Service");
	String locale1=prop1.getProperty("Locale");
	String OfsUser1=prop1.getProperty("OfsUser");
	String[] props=new String[] {Cookie1,path,tanent1,workspace1,service1,locale1,OfsUser1};
	
	return props[i];

}
	
	
}
