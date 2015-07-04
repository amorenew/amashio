package co.mazeed.smsprojects.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

public class GroupData {

	@JsonProperty("groupID")
	int groupID;
	@JsonProperty("groupName")
	String groupName;
	@JsonProperty("contactsNo")
	int contactsNo;
	@JsonProperty("addDate")
	String addDate;

	public static GroupData fromJson(String jsonText) {
		ObjectMapper mapper = new ObjectMapper();
		GroupData group = null;
		try {
			group = mapper.readValue(jsonText, GroupData.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return group;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getContactsNo() {
		return contactsNo;
	}

	public void setContactsNo(int contactsNo) {
		this.contactsNo = contactsNo;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

}
