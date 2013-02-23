package com.skysteve.phpBB.forum.redis;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Topic {
	
	private int topicId = 0;
	
	private String title = "";
	
	private Date lastPost = new Date();

	public Topic(int topicId, String title, Date lastPost) {
		this.topicId = topicId;
		this.title = title;
		this.lastPost = lastPost;
	}
	
	public Topic(String next) {
		JSONObject obj = (JSONObject) JSONValue.parse(next);
		
		topicId = ((Long) obj.get("id")).intValue();
		title = (String) obj.get("title");
		lastPost = new Date((Long) obj.get("lastPost"));
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getLastPost() {
		return lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON () {
		JSONObject obj = new JSONObject();
		obj.put("id", topicId);
		obj.put("title", title);
		obj.put("lastPost", lastPost.getTime());
		
		return obj;
		
	}

}
