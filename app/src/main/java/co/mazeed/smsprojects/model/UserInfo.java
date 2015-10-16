package co.mazeed.smsprojects.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties({"status","massage"})
public class UserInfo {
//	{"status":"Success","tokenKey":"761f5b8c7c3e99979436a5f6904f5400","first_name":"","last_name":"","full_name"
	//:"","username":"EsraaM","email":"esraamohammed@gmail.com","mobile":"201068200566","balance":4,"massage":"Member Login in successfully"}
	String status;
	String massage;
	@JsonProperty("tokenKey")
	String tokenKey;
	@JsonProperty("first_name")
	String firstName;
	@JsonProperty("last_name")
	int lastName;
	@JsonProperty("full_name")
	String fullName;
	@JsonProperty("username")
	String userName;
	@JsonProperty("email")

	String email;
	@JsonProperty("mobile")
	String mobile;
	@JsonProperty("balance")
	int balance;

	@JsonProperty("photo")
	String photo;
	@JsonProperty("created")
	String created;

	public static UserInfo fromJson(String jsonText) {
		ObjectMapper mapper = new ObjectMapper();
		UserInfo user = null;
		try {
			user = mapper.readValue(jsonText, UserInfo.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getLastName() {
		return lastName;
	}

	public void setLastName(int lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
}
