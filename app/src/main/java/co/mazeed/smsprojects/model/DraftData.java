package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by neolptp on 7/20/2015.
 */
public class DraftData {
    @JsonProperty("draftContent")
    String draftContent;
    @JsonProperty("createdAt")
    long createdAt;
    @JsonProperty("Id")
    int id;

   public DraftData()
   {

   }
    public DraftData(String draftContent,long createdAt)
    {
        this.draftContent=draftContent;
        this.createdAt=createdAt;

    }
    public static DraftData FromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        DraftData draft = null;

        try {
            draft = mapper.readValue(jsonText, DraftData.class);
            // display to console
            System.out.println(draft);
        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return draft;
    }

    public String getDraftContent() {
        return draftContent;
    }

    public void setDraftContent(String draftContent) {
        this.draftContent = draftContent;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
