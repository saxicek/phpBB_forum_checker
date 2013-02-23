package com.skysteve.phpBB.forum.config.children;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MailTo {

	private String subject, body;
	private List<String> addresses = new ArrayList<String>();
	
	public MailTo (JSONObject jsonObj) {
		subject = (String) jsonObj.get("subject");
		body = (String) jsonObj.get("body");

		JSONArray addrs = (JSONArray) jsonObj.get("addresses");
		
		@SuppressWarnings("unchecked")
		Iterator<String> i = addrs.iterator();
		
		while(i.hasNext()) {
			addresses.add(i.next());
		}
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public List<String> getAddresses() {
		return addresses;
	}
	
	
}
