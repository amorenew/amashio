package co.mazeed.smsprojects.model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by neolptp on 7/18/2015.
 */
public class Country {
    @JsonProperty("name")
    String name;
    @JsonProperty("code")
    String code;

    public static Country FromJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        Country country = null;

        try {
            country = mapper.readValue(jsonText, Country.class);
            // display to console
            System.out.println(country);
        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
