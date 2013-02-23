package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class Alerts {
	
	private boolean active, replies;
	
	private String type, allType;
	
	
	public Alerts (JSONObject jsonObj) {
		active = (Boolean) jsonObj.get("active");
		replies = (Boolean) jsonObj.get("replies");
		
		allType = (String) jsonObj.get("allType");
		type = (String) jsonObj.get("type");
	}

	public boolean isActive() {
		return active;
	}

	public boolean isReplies() {
		return replies;
	}

	public String getType() {
		return type;
	}

	public String getAllType() {
		return allType;
	}
	
	

}
