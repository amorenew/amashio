package co.mazeed.smsproject.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import co.mazeed.smsproject.controller.communication.Communication;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.RequestHeader;
import co.mazeed.smsproject.controller.communication.ResponseObject;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.controller.communication.Task.TaskID;
import co.mazeed.smsproject.storage.ServiceStorage;

public class Signup extends Task {

	private String url;
	Context mxontext;
	String result;
	String httpBody;
	private ResponseObject response;
	public static String CONTENT_TYPE_KEY = "Content-type";
	public static String ACCESS_TOKEN_KEY = "accessToken";
	public static String CONTENT_TYPE_VALUE = "application/json";

	public Signup(DataRequestor requestor, Context context, HashMap<String, String>inputs, String mobile) {
		setRequestor(requestor);
		setId(TaskID.SignupTask);
		this.mxontext = context;
		url = Communication.Common_Base_URL + "signup/?";
		url+=buildHttpBody(inputs);;
//		httpBody=buildHttpBody(inputs);
//		httpBody+=",\"mobileNumber\":"+mobile+ "}";

	}
	String buildHttpBody(Map<String, String> inputs) {
		String concat ="" ;
		for (String key : inputs.keySet()) {
			if (concat.length() == 0) {
				concat =  key + "=" + inputs.get(key) ;
			} else {
				concat = concat + "&" + key + "=" + inputs.get(key)+"";

			}
		}
		return concat ;
	}
	@Override
	public void execute() {
		response = (ResponseObject) Communication.postMethodWithoutBody(url,
				getHeadersList(), mxontext);
		System.out.println("url" + url);
		mapServerError(response.getStatusCode());
		String r = response.getResponseString();
		JSONObject mainObject;
		if (response.getStatusCode() == 200) {
			try {
				mainObject = new JSONObject(r);
				String status=mainObject.getString("status");
				if(status.equalsIgnoreCase("Success"))
				{
					result=mainObject.getString("massage");
					String tokenkey=mainObject.getString("userID");
					ServiceStorage.userID=tokenkey;
					
				}
				else if(status.equalsIgnoreCase("failure"))
				{
				JSONObject errorobj=mainObject.getJSONObject("errors")	;
//				for (int i = 0; i < errorsArr.length(); i++) {
//					JSONObject errorobj=errorsArr.getJSONObject(i);
					result+= errorobj.get("message");
					
	
//				}
				}
				
			} catch (JSONException e) {

			}
		}
	}

	public ArrayList<RequestHeader> getHeadersList() {
		ArrayList<RequestHeader> headers = new ArrayList<RequestHeader>();
		RequestHeader header = new RequestHeader(CONTENT_TYPE_KEY,
				CONTENT_TYPE_VALUE);
		headers.add(header);

		return headers;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

}

