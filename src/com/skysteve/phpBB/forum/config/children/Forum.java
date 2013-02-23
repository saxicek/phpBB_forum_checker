package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class Forum {
	
	private int pageID;
	private String baseURL;
	
	public Forum (JSONObject jsonObj) {
		pageID = ((Long) jsonObj.get("pageID")).intValue();
		baseURL = (String) jsonObj.get("baseURL");
	}

	public int getPageID() {
		return pageID;
	}

	public String getBaseURL() {
		return baseURL;
	}
	
	

}
