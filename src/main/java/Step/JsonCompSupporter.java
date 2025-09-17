package Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public class JsonCompSupporter {

	public static void removeFieldBTgcd(JSONObject jsonObject, String keyToRemove) throws JSONException {
		if (jsonObject.has("body")) {
			JSONArray bodyArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < bodyArray.length(); i++) {
				JSONObject obj = bodyArray.getJSONObject(i);
				obj.remove(keyToRemove);
			}
		}
	}


	public static boolean getStatusCompare(String ActualResponseindented,String ExpectedResponseindented) throws JSONException
	{

		boolean compstatus= false;

		try
		{
			JSONObject obj1 = new JSONObject(ActualResponseindented);
			JSONObject obj2 = new JSONObject(ExpectedResponseindented);
			removeFieldBTgcd(obj1, "gcd");
			removeFieldBTgcd(obj2, "gcd");
			JSONAssert.assertEquals(obj1.toString(), obj2.toString(), new CustomComparator(JSONCompareMode.LENIENT));
			compstatus=true;
		}
		catch(AssertionError e)
		{
			compstatus=false;
		}
		catch(JSONException e1)
		{
			compstatus=false;
		}
		return compstatus;

	}

}
