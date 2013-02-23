package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class Debug {
	
	private boolean active, clearList;
	
	private String dumpFileBase, dumpFileName;
	
	public Debug (JSONObject jsonObj) {
		active = (Boolean) jsonObj.get("active");
		clearList = (Boolean) jsonObj.get("clearList");
		
		dumpFileBase = (String) jsonObj.get("dumpFileBase");
		dumpFileName = (String) jsonObj.get("dumpFileName");
	}

	public boolean isActive() {
		return active;
	}

	public boolean shouldClearList() {
		return clearList;
	}

	public String getDumpFileBase() {
		return dumpFileBase;
	}

	public String getDumpFileName() {
		return dumpFileName;
	}

	

}
