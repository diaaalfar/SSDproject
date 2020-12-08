package ssdPackage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Users {

	String user;
	String pass; 
	String apiKey ;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	
	
}
