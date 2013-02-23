package com.skysteve.phpBB.forum.config.children;

import org.json.simple.JSONObject;

public class Storage {

	private String redisKey, client;
	private int maxTopic;
	
	public Storage (JSONObject jsonObj) {
		redisKey = (String) jsonObj.get("redisKey");
		client = (String) jsonObj.get("client");
		
		maxTopic = ((Long) jsonObj.get("maxTopics")).intValue();
	}

	public String getRedisKey() {
		return redisKey;
	}

	public String getClient() {
		return client;
	}

	public int getMaxTopic() {
		return maxTopic;
	}
	
	
}
