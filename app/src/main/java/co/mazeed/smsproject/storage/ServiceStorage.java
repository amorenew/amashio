package co.mazeed.smsproject.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mazeed.smsprojects.model.GroupData;
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
	}
