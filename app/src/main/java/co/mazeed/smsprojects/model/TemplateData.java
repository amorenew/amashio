package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by neolptp on 7/3/2015.
 */
public class TemplateData {
    @JsonProperty("smsTemplateID")
    int smsTemplateID;
    @JsonProperty("lang")
    String lang = "eng";
    @JsonProperty("categoryID")
    int categoryID;
    @JsonProperty("smsTemplateContent")
    String smsTemplateContent;
    @JsonProperty("smsNo")
    int smsNo;

    public static TemplateData fromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        TemplateData data = null;
        try {
            data = mapper.readValue(jsonText, TemplateData.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getSmsTemplateID() {
        return smsTemplateID;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getSmsTemplateContent() {
        return smsTemplateContent;
    }

    public void setSmsTemplateContent(String smsTemplateContent) {
        this.smsTemplateContent = smsTemplateContent;
    }

    public int getSmsNo() {
        return smsNo;
    }

    public void setSmsNo(int smsNo) {
        this.smsNo = smsNo;
    }

    public void setSmsTemplateID(int smsTemplateID) {
        this.smsTemplateID = smsTemplateID;
    }
}
