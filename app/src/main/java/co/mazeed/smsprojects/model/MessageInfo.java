package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by neolptp on 8/29/2015.
 */
public class MessageInfo {
    String body;
    String number;
    String contactId;
   String groupId;
   String senderName;
   String sendDate;

    public static MessageInfo fromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        MessageInfo msg = null;
        try {
            msg = mapper.readValue(jsonText, MessageInfo.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
