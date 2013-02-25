package com.skysteve.phpBB.forum.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.skysteve.phpBB.forum.config.Config;

public class ForumMessage {
	
	private static Logger logger = Logger.getLogger(ForumMessage.class.getName());
	
	private Config config;
	private HashMap<String, Object> mustacheScopes;

	
	//TODO remove URL & Title, instead pass in a HashMap object
	
	public ForumMessage (Config config, HashMap<String, Object> mustacheScopes) {
		this.config = config;
		this.mustacheScopes = mustacheScopes;
	}
	
	
	public String getBody() {
		File f = new File(config.getMailTo().getBody());
		
		//try to load the mustache as if it were a file
		try {
			Scanner scanner = new Scanner(new FileReader(f));
			String mustacheTemplate = "";
			while (scanner.hasNext()) {
				mustacheTemplate += scanner.nextLine();
			}
			scanner.close();
			return compileMustache(mustacheTemplate);
		} catch (FileNotFoundException e) {
			//if we have a file not found, treat the mustache as a string
			
			if (config.getDebug().isActive()) {
				logger.info("Treating body text as a string");
			}
			
			return compileMustache(config.getMailTo().getBody());
		}
		
	}
	
	public String getSubject() {
		return compileMustache(config.getMailTo().getSubject());
	}
	
	
	private String compileMustache(String input) {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(new StringReader(input), "");
		StringWriter writer = new StringWriter();

		mustache.execute(writer, mustacheScopes);
		writer.flush();
		return(writer.toString());
		
	}

}
