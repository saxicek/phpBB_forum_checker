/**
 * 
 */
package com.skysteve.phpBB.forum.main;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import com.skysteve.phpBB.forum.config.Config;
import com.skysteve.phpBB.forum.redis.StorageManager;
import com.skysteve.phpBB.forum.web.EmailClient;
import com.skysteve.phpBB.forum.web.PageChecker;

/**
 * @author Steve
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws MessagingException, FileNotFoundException {	
		Config config = new Config(args[0]);
		StorageManager storageManager = new StorageManager(config);
		
		List<ForumMessage> messages = PageChecker.checkPage(config, storageManager);
				
		if(messages.size() > 0 && config.getAlerts().isActive()) {
			EmailClient mail = new EmailClient(config);
			
			int max = 1;
			
			if (config.getAlerts().getType().toLowerCase().compareTo("all") == 0) {
				max = messages.size();
			}
			
			for(int i = 0; i < max; i++) {
				mail.sendMessage(messages.get(i));
			}			
			
			System.out.println("Found " + messages.size() + " new topics. Sent " + max + " notificaitons to " + config.getMailTo().getAddresses() + " recipients - exiting");
			System.exit(0);
		}
		
		System.out.println("Found 0 new messages - exiting");
		
	}
	
	

}
