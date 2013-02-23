package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class MailFrom {
	
	private String address, host, name, username, password;
	private int port;
	
	public MailFrom (JSONObject jsonObj) {
		address = (String) jsonObj.get("address");
		host = (String) jsonObj.get("host");
		name = (String) jsonObj.get("name");
		username = (String) jsonObj.get("username");
		password = (String) jsonObj.get("password");
		
		port = ((Long) jsonObj.get("port")).intValue();
	}

	public String getAddress() {
		return address;
	}

	public String getHost() {
		return host;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}
	
	

}
