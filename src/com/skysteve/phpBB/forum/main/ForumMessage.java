package com.skysteve.phpBB.forum.main;

import com.skysteve.phpBB.forum.config.Config;

public class ForumMessage {
	
	private Config config;
	
	private String URL;
	private String Title;
	
	public ForumMessage (Config config) {
		this.config = config;
	}
	
	public String getUrl() {
		return URL;
	}
	public void setUrl(String url) {
		this.URL = url;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	
	public String getBody() {
		String body = "<p>New Forum Post!</p>";
		body = body + "<a href=\"" + URL + "\">" + URL + "</a></br>";
		return body;
	}
	
	public String getSubject() {
		return config.getMailTo().getSubject() + this.Title;
	}

}
