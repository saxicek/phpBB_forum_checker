package com.skysteve.phpBB.forum.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.skysteve.phpBB.forum.config.children.Alerts;
import com.skysteve.phpBB.forum.config.children.Debug;
import com.skysteve.phpBB.forum.config.children.Forum;
import com.skysteve.phpBB.forum.config.children.MailFrom;
import com.skysteve.phpBB.forum.config.children.MailTo;
import com.skysteve.phpBB.forum.config.children.Storage;

public class Config {

	private Alerts alerts;
	private Debug debug;
	private Forum forum;
	private MailFrom mailFrom;
	private MailTo mailTo;
	private Storage storage;
	
	public Config(String filePath) throws FileNotFoundException {
		parseConfig(filePath);
	}
	
	public void parseConfig (String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		JSONObject jsonObj = (JSONObject) JSONValue.parse(new FileReader(file));

		alerts = new Alerts((JSONObject) jsonObj.get("alerts"));
		debug = new Debug((JSONObject) jsonObj.get("debug"));
		forum = new Forum((JSONObject) jsonObj.get("forum"));
		mailFrom = new MailFrom((JSONObject) jsonObj.get("mailFrom"));
		mailTo = new MailTo((JSONObject) jsonObj.get("mailTo"));
		storage = new Storage((JSONObject) jsonObj.get("storage"));
	}

	public Alerts getAlerts() {
		return alerts;
	}

	public Debug getDebug() {
		return debug;
	}

	public Forum getForum() {
		return forum;
	}

	public MailFrom getMailFrom() {
		return mailFrom;
	}

	public MailTo getMailTo() {
		return mailTo;
	}

	public Storage getStorage() {
		return storage;
	}
	
	
}
