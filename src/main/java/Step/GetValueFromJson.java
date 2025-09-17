package Step;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

public class GetValueFromJson {

	
	static Object FinalKey="";


	public static String GetValueJson(String Json, String key) throws JSONException
	{

		JSONObject obj = new JSONObject(Json);
		String Keyvalue = getKey(obj, key);
		
		System.out.println("For the Given Key the value is :" +Keyvalue);
		
		return Keyvalue;


	}



	public static void main(String[] args) throws JSONException {
		String Json = "{\r\n"
				+ "    \"code\": \"DIH0000\",\r\n"
				+ "    \"body\": {\r\n"
				+ "        \"DSData\": [\r\n"
				+ "            {\r\n"
				+ "                \"dsId\": \"1594\",\r\n"
				+ "                \"dsName\": \"FOUNDATION_DATA_STORE\",\r\n"
				+ "                \"dsDesc\": \"Data Store for DFCS Extracts\",\r\n"
				+ "                \"dsType\": \"FILE\",\r\n"
				+ "                \"dbCon\": \"\",\r\n"
				+ "                \"dsFileLoc\": \"/home/oracle/data/erp_files/extracts\",\r\n"
				+ "                \"dsStatus\": \"Saved\",\r\n"
				+ "                \"dsMBy\": \"dfcsqa\",\r\n"
				+ "                \"dsMDate\": \"2024-02-15 14:00:00\",\r\n"
				+ "                \"dsUsed\": \"Y\",\r\n"
				+ "                \"typeId\": \"1\",\r\n"
				+ "                \"typeCd\": \"FILE\",\r\n"
				+ "                \"superType\": \"FILE\",\r\n"
				+ "                \"dsStatusVal\": \"S\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "}";


		//JSONObject inputJSONOBject = new JSONObject(Json);
		//getKey(inputJSONOBject, "code");


		String Json1 = "{\r\n"
				+ "    \"code\": \"DIH0000\",\r\n"
				+ "    \"body\": {\r\n"
				+ "        \"DSData\": [\r\n"
				+ "            {\r\n"
				+ "                \"dsId\": \"1592\",\r\n"
				+ "                \"dsName\": \"FOUNDATION_DATA_STORE\",\r\n"
				+ "                \"dsDesc\": \"Data Store for DFCS Extracts\",\r\n"
				+ "                \"dsType\": \"FILE\",\r\n"
				+ "                \"dbCon\": \"\",\r\n"
				+ "                \"dsFileLoc\": \"/home/oracle/data/erp_files/extracts\",\r\n"
				+ "                \"dsStatus\": \"Saved\",\r\n"
				+ "                \"dsMBy\": \"dfcsqa\",\r\n"
				+ "                \"dsMDate\": \"2024-02-15 14:00:00\",\r\n"
				+ "                \"dsUsed\": \"Y\",\r\n"
				+ "                \"typeId\": \"1\",\r\n"
				+ "                \"typeCd\": \"FILE\",\r\n"
				+ "                \"superType\": \"FILE\",\r\n"
				+ "                \"dsStatusVal\": \"S\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "}";


		String Json2 = "{\r\n"
				+ "    \"code\": \"DIH0000\",\r\n"
				+ "    \"body\": {\r\n"
				+ "        \"id\": \"3921\"\r\n"
				+ "    }\r\n"
				+ "}";


		//		JSONObject inputJSONOBject = new JSONObject(Json2);
		//
		//		JSONObject inputJSONOBject1 = new JSONObject(inputJSONOBject.get("body").toString());
		//		System.out.println(inputJSONOBject1.get("id").toString());
		//
		//
		//		System.out.println(inputJSONOBject.get("body"));
		//
		//
		//		String[] responseArray=Json.split(":");
		//
		//		for(int i=0;i<responseArray.length;i++)
		//			System.out.println(i+" is: "+responseArray[i]);


		//String dsid = GetValueJsonDirect(Json, "dsId");

		//System.out.println("dsId is : "+dsid);
		
//		JSONObject obj = new JSONObject(Json);
//		String Id = getKey(obj, "dsId");
//		
//		System.out.println("dsid :" +Id);
		
		
		String Jsonid1 = "{}";
		
		String Jsonid2 = "{}";
		
		JSONAssert.assertEquals(Jsonid1, Jsonid2, JSONCompareMode.LENIENT);
		
		
		//System.out.println(Jsonid2.replace("]", ""));
		
//		JSONAssert.assertEquals(Jsonid1, Jsonid2, new CustomComparator(JSONCompareMode.LENIENT,
//				new Customization("createdAt", (o1, o2) -> true),
//				new Customization("updatedAt", (o1, o2) -> true)
//				));
		
		System.out.println("PASSED");
		
		
		



	}




	public static Object parseObject(JSONObject json, String key) throws JSONException {
		System.out.println("Key : "+key+" has value : "+json.get(key));

		return json.get(key);
	}

	public static String getKey(JSONObject json, String key) throws JSONException {

		boolean exists = json.has(key);
		System.out.println("Exists: "+exists);
		Iterator<?> keys;
		String nextKeys;
		if (!exists) {
			keys = json.keys();
			while (keys.hasNext()) {
				nextKeys = (String) keys.next();
				System.out.println("nextKeys : "+nextKeys);
				try {
					if (json.get(nextKeys) instanceof JSONObject) {
						System.out.println("instanceof JSONObject");
						if (exists == false) {
							System.out.println("Exists in the JSONObject :"+exists);
							getKey(json.getJSONObject(nextKeys), key);	
						}
					} else if (json.get(nextKeys) instanceof JSONArray) {
						System.out.println("instanceof JSONArray");
						System.out.println("json.get(nextKeys : "+json.get(nextKeys));
						JSONArray jsonarray = json.getJSONArray(nextKeys);
						for (int i = 0; i < jsonarray.length(); i++) {
							System.out.println("Inside Json Array: "+i);
							String jsonarrayString = jsonarray.get(i).toString();
							System.out.println("jsonarrayString : "+jsonarrayString);
							JSONObject innerJSOn = new JSONObject(jsonarrayString);
							if (exists == false) {
								System.out.println("Exists in the JSONArray :"+exists);
								getKey(innerJSOn, key);
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}

			}
		} else {
			FinalKey = json.get(key);
			System.out.println("Final key value: "+FinalKey.toString());
		}


		return FinalKey.toString();


	}



	public static String getKey_bkp(JSONObject json, String key) throws JSONException {

		Object FinalKey="";
		boolean exists = json.has(key);
		System.out.println("Exists: "+exists);
		Iterator<?> keys;
		String nextKeys;
		if (!exists) {
			keys = json.keys();
			while (keys.hasNext()) {
				nextKeys = (String) keys.next();
				try {
					if (json.get(nextKeys) instanceof JSONObject) {
						if (exists == false) {
							getKey(json.getJSONObject(nextKeys), key);
						}
					} else if (json.get(nextKeys) instanceof JSONArray) {
						JSONArray jsonarray = json.getJSONArray(nextKeys);
						for (int i = 0; i < jsonarray.length(); i++) {
							String jsonarrayString = jsonarray.get(i).toString();
							JSONObject innerJSOn = new JSONObject(jsonarrayString);
							if (exists == false) {
								getKey(innerJSOn, key);
							}
						}
					}
				} catch (Exception e) {
				}}
		} else {
			FinalKey = json.get(key);
		}


		return FinalKey.toString();


	}



public static String getSummaryID(String json, String Catlogid) throws JSONException {

	 JSONObject jsonObject = new JSONObject(json);
     JSONArray bodyArray = jsonObject.getJSONArray("body");
     String summaryid = null;
     for (int i = 0; i < bodyArray.length(); i++) {
         JSONObject catalog = bodyArray.getJSONObject(i);
         if (Catlogid.equals(catalog.getString("ctlgId"))) {
        	 summaryid = catalog.getString("id");
             System.out.println("ID of ctlgId " +Catlogid +" : " + summaryid);
             break;
         }
     }
	return summaryid;
 }

public static String getversionIDtest(String json, String keyval) throws JSONException {
		
		 JSONObject jsonObject = new JSONObject(json);
	     JSONArray bodyArray = jsonObject.getJSONArray("body");
	     if (bodyArray.length() > 0) { 
	    	    JSONObject catalog = bodyArray.getJSONObject(0); // Access the first element
	    	    String version = catalog.getString(keyval);  
	    	    System.out.println("VERSION: " + version);
	    	    return version;
	    	} 

	    return null; 
	 }

public static String getversionID(String json, String keyval) throws JSONException {
    JSONObject jsonObject = new JSONObject(json);
    
    if (jsonObject.has("body") && jsonObject.get("body") instanceof JSONArray) {
        JSONArray bodyArray = jsonObject.getJSONArray("body");
        
        // If the "body" array has elements, extract the version from the first element
        if (bodyArray.length() > 0) {
            JSONObject catalog = bodyArray.getJSONObject(0);  // Access the first element
            if (catalog.has(keyval)) {
                String version = catalog.getString(keyval);
                System.out.println("VERSION from body array: " + version);
                return version;
            }
        }
    }
    
    if (jsonObject.has(keyval)) {
        String version = jsonObject.getString(keyval);  // Directly fetch the version if present
        System.out.println("VERSION from top-level object: " + version);
        return version;
    }

    System.out.println("No version found for key: " + keyval);
    return null;
}




}
