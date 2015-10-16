package co.mazeed.smsproject.storage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.Country;
import co.mazeed.smsprojects.model.DraftData;
import co.mazeed.smsprojects.model.GroupData;
import co.mazeed.smsprojects.model.MessageInfo;
import co.mazeed.smsprojects.model.TemplateCategory;
import co.mazeed.smsprojects.model.TemplateData;
import co.mazeed.smsprojects.model.UserInfo;

	public class ServiceStorage {

		public static String tokenKey;
		public static String userID;
		public static String expireDate;
		public static String pointsBalance;
		public static ArrayList<GroupData> contactsGroupList;
		public static UserInfo currentUser;
		public static HashMap<Integer, Map<Integer, TemplateData>> templatesMap;
		public static HashMap<Integer,TemplateCategory> templatesCategoryList;
		public static ArrayList<ContactData> contactsList;
		public static ArrayList<Country> countries;
//		public static ArrayList<DraftData> drafts;
		public static ArrayList<DraftData> drafts=new ArrayList<DraftData>();


		public static String[] contactsNumbersList;


		public static void clearSetting()
		{
			tokenKey=null;
			userID=null;
			expireDate=null;
			pointsBalance=null;
			contactsGroupList=null;
			currentUser=null;
			templatesMap=null;
			templatesCategoryList=null;
			contactsList=null;
			countries=null;
			drafts=null;
			contactsNumbersList=null;


	}
		public void  mReadJsonData(String fileName) {
			try {
				//"/data/data/" + getPackageName() + "/" + params
				File f = new File(fileName);
				FileInputStream is = new FileInputStream(f);
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				String mResponse = new String(buffer);
				Log.d("Drafts", mResponse);
				JSONArray draftsList=new JSONArray(mResponse)	;
				for (int i = 0; i < draftsList.length(); i++) {
					DraftData draftData = DraftData.FromJson(mResponse.toString());
					ServiceStorage.drafts.add(draftData);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
