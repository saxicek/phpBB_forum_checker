package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class Posts {
	
	private boolean details, original, repliesActive, repliesDetails;
	
	public Posts (JSONObject jsonObj) {
		details = (Boolean) jsonObj.get("details");
		original = (Boolean) jsonObj.get("original");
		
		JSONObject repliesObj = (JSONObject) jsonObj.get("replies");
		
		repliesActive = (Boolean) repliesObj.get("active");
		repliesDetails = (Boolean) repliesObj.get("extractDetails");
	}

	public boolean isDetails() {
		return details;
	}

	public boolean isOriginal() {
		return original;
	}

	public boolean isRepliesActive() {
		return repliesActive;
	}

	public boolean isRepliesDetails() {
		return repliesDetails;
	}
}
