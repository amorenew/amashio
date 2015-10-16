package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by neolptp on 7/10/2015.
 */
public class TemplateCategory {
//    "categoryID": 32,
//            "lang": "eng",
//            "categoryName": "Best wishes",
//            "smsTemplateNo": 51,
//            "addDate": "2011-12-04 17:23:46"
    @JsonProperty("categoryID")
    int categoryID;
    @JsonProperty("lang")
    String lang = "eng";
    @JsonProperty("categoryName")
    String categoryName;
    @JsonProperty("smsTemplateNo")
    int smsTemplateNo;
    @JsonProperty("addDate")
    String addDate;
    public static TemplateCategory fromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        TemplateCategory data = null;
        try {
            data = mapper.readValue(jsonText, TemplateCategory.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSmsTemplateNo() {
        return smsTemplateNo;
    }

    public void setSmsTemplateNo(int smsTemplateNo) {
        this.smsTemplateNo = smsTemplateNo;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
