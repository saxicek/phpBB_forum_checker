package com.skysteve.phpBB.forum.config.children;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Ignore {
	
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<Integer> posts = new ArrayList<Integer>();
	
	private boolean stickyPosts;
	
	public Ignore (JSONObject jsonObj) {
		JSONArray usersArr = (JSONArray) jsonObj.get("users");
		
		if (usersArr != null) {
			@SuppressWarnings("unchecked")
			Iterator<String> ui = usersArr.iterator();
			
			while(ui.hasNext()) {
				users.add(ui.next());
			}
		}
		JSONArray postsArr = (JSONArray) jsonObj.get("posts");
		
		if (postsArr != null) {
			@SuppressWarnings("unchecked")
			Iterator<Integer> pi = postsArr.iterator();
			
			while(pi.hasNext()) {
				posts.add(pi.next());
			}
		}
		stickyPosts = (Boolean) jsonObj.get("stickyPosts");
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public ArrayList<Integer> getPosts() {
		return posts;
	}

	public boolean isStickyPosts() {
		return stickyPosts;
	}
	
	

}
