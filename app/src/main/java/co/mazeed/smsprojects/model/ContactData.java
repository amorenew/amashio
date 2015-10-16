package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by neolptp on 9/2/2015.
 */
public class ContactData implements Serializable {
    @JsonProperty("ContactID")
    int contactID;
    @JsonProperty("contactName")
    String contactName;
    @JsonProperty("ContactEmail")
    String contactEmail;
    @JsonProperty("contactMobileNumber")
    String contactMobileNumber;
    @JsonProperty("groupName")
    String groupName;
    @JsonProperty("groupID")
    int groupID;
    @JsonProperty("contactValid")
    String contactValid;
    @JsonProperty("contactCreatedDate")
    String contactCreatedDate;
    @JsonProperty("contactStatus")
    int contactStatus;


    public ContactData()
    {

    }
    public ContactData(String contactName,String contactMobileNumber,int contactID ,String contactEmail)
    {
        this.contactID=contactID;
        this.contactName=contactName;
        this.contactMobileNumber=contactMobileNumber;
        this.contactEmail=contactEmail;

    }

    public static ContactData fromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        ContactData contactData = null;
        try {
            contactData = mapper.readValue(jsonText, ContactData.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactData;
    }


    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        contactEmail = contactEmail;
    }

    public String getContactMobileNumber() {
        return contactMobileNumber;
    }

    public void setContactMobileNumber(String contactMobileNumber) {
        this.contactMobileNumber = contactMobileNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getContactValid() {
        return contactValid;
    }

    public void setContactValid(String contactValid) {
        this.contactValid = contactValid;
    }

    public String getContactCreatedDate() {
        return contactCreatedDate;
    }

    public void setContactCreatedDate(String contactCreatedDate) {
        this.contactCreatedDate = contactCreatedDate;
    }

    public int getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(int contactStatus) {
        this.contactStatus = contactStatus;
    }

    public static Comparator<ContactData> conatctNameComparator = new Comparator<ContactData>() {

        public int compare(ContactData s1, ContactData s2) {
            String name1 = s1.contactName.toUpperCase();
            String name2 = s2.contactName.toUpperCase();
            //ascending order
            return name1.compareTo(name2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
