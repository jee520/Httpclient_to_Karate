package Step;
import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class jsonCompare {
//	public void jsonCompare(String response,String ActualResponse)
	public static void main(String[] args) 
	{	String response="abbc,jdfgsa,saded=910,kdla=0";
		String ActualResponse="abbc,jdfgsa,saded=910,kdla=1";
		String[] responseArray=response.split(",");
		String[] ActualResponseArray=ActualResponse.split(",");
		ArrayList<String> arr_res=new ArrayList<String>();
		ArrayList<String> arr_act=new ArrayList<String>();
		for(int i=0;i<responseArray.length;i++)
				arr_res.add(responseArray[i]);
		ArrayList<String> arr_res_temp=new ArrayList<String>(arr_res);
		for(int i=0;i<ActualResponseArray.length;i++)
			arr_act.add(ActualResponseArray[i]);
		System.out.println("response:"+arr_res);
		System.out.println("Actual:"+arr_act);
		arr_res.removeAll(arr_act);
		arr_act.removeAll(arr_res_temp);
		System.out.println("After Removal:"+arr_res);
		System.out.println("After Removal:"+arr_act);
		
		
		
	}

}
