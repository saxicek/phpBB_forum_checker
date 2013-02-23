package com.skysteve.phpBB.forum.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.skysteve.phpBB.forum.config.Config;
import com.skysteve.phpBB.forum.main.ForumMessage;
import com.skysteve.phpBB.forum.redis.StorageManager;
import com.skysteve.phpBB.forum.redis.Topic;

public class PageChecker {
	
	private static Logger logger = Logger.getLogger(PageChecker.class.getName());
	
	public static List<ForumMessage> checkPage (Config config, StorageManager storageManager) {
		String prefix = config.getForum().getBaseURL(),
				url =  prefix + "viewforum.php?f=" + config.getForum().getPageID();
		List<ForumMessage> messages = new ArrayList<ForumMessage>();
		
    	try {			
    		DefaultHttpClient httpclient = new DefaultHttpClient();
    		if (config.getDebug().isActive()) {
    			logger.info("Getting forumn data : " + url);
    		}
        	HttpGet httpget = new HttpGet(url);
        	HttpResponse response = httpclient.execute(httpget);
        	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        	
        	String outputFile = config.getDebug().getDumpFileBase();
        	
        	//if file is null, debug isn't active or we couldn't create all the correct directories
        	if (outputFile == null || !config.getDebug().isActive()) {
        		outputFile = "/dev/null";
        		if (config.getDebug().isActive()) {
        			logger.warning("output file not specified redirecting output to /dev/null");
        		}
        	} else {
        		
        		if (outputFile.endsWith("/")) {
        			outputFile = outputFile.substring(0, outputFile.length() -1);
        		}
        		
        		File f = new File(outputFile);
        		
        		if (!f.exists()) {
        			if (!f.mkdirs()) {
        				logger.warning("could not create path \"" + outputFile + "\" redirecting output to /dev/null");
        				outputFile = "/dev/null";
        			}else {
        				outputFile = outputFile + "/" + config.getDebug().getDumpFileName() + ".html";
        			}
        		} else {
        			outputFile = outputFile + "/" + config.getDebug().getDumpFileName() + ".html";
        		}
        		
        		
        	}
        	
        	
        	
        	
        	FileWriter fw = new FileWriter(outputFile);
        	
        	String line = "";
        	
        	
        	while((line=rd.readLine())!=null)
			{
				fw.write(line + "\n\r");
				while(line != null && !line.contains("class=\"topictitle\"")) {
					line = rd.readLine();
					fw.write(line + "\n\r");
				}
				
				if (line == null) {
					break;
				}
				
				int topicID = extractTopic(line);
				String title = extractTitle(line);
				
				while(!line.contains("class=\"row3Right\"")) {
					line = rd.readLine();
					fw.write(line + "\n\r");
				}
				
				Date date = extractDate(line);
				Topic newTopic = new Topic(topicID, title, date);
				Topic oldTopic = storageManager.getTopic(topicID);
				
				//if we don't already know about this topic, or there's a new reply
				if (oldTopic == null || newTopic.getLastPost().compareTo(oldTopic.getLastPost()) != 0) {
					storageManager.storeTopic(newTopic);
					
					if (config.getDebug().isActive()) {
		    			logger.info("Found new topic " + title);
		    		}
					
					ForumMessage msg = new ForumMessage(config);
					msg.setUrl(prefix + "viewtopic.php?t=" + topicID);
					msg.setTitle(title);
					messages.add(msg);
				}

				fw.write(line + "\n\r");
			}
        	
        	fw.close();
        	
    	}catch(Exception ex) {
    		logger.warning(ex.getMessage());
    	}
    	return messages;
	}
	
	private static Date extractDate(String line) {
		Pattern p = Pattern.compile(">\\w.*\\s\\d+,\\s\\d+\\s\\d+:\\d+\\s[a-z]+");
		Matcher m = p.matcher(line);
		
		
		//Sun Apr 10, 2011 12:45 pm
		DateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy kk:mm a");
		
		if (m.find()) {
			try {
				return sdf.parse(line.substring(m.start()+1, m.end()));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}

	private static int extractTopic(String line) {
		Pattern p = Pattern.compile("\\?t=\\d+");
		Matcher m = p.matcher(line);
		
		if (m.find()) {
			return Integer.parseInt(line.substring(m.start() + 3, m.end()));
		}
		
		return 0;
	}
	
	private static String extractTitle(String line) {
		String name = line.substring(line.indexOf("<a"));
		name = name.substring(name.indexOf("topictitle")+12).trim();
		return name.substring(0,name.indexOf("<")).trim();
	}	
}
