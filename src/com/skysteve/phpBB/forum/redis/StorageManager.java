package com.skysteve.phpBB.forum.redis;

import java.util.Set;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;

import com.skysteve.phpBB.forum.config.Config;

public class StorageManager {
	
	private static Logger logger = Logger.getLogger(StorageManager.class.getName());
	
	private String KEY;
	private Jedis redisClient;
	
	public StorageManager (Config config) {
		KEY = config.getStorage().getRedisKey();
		redisClient = new Jedis(config.getStorage().getClient());

		redisClient.connect();
		
		if (!redisClient.isConnected()) {
			logger.warning("Could not connect to Redis client " + config.getStorage().getClient());
			System.exit(1);
		} else {
			if (config.getDebug().isActive()) {
				logger.info("Redis connected to " + config.getStorage().getClient() + " (KEY=" + KEY + ")");
				
				if (config.getDebug().shouldClearList()) {
					this.deleteSet();
				}
			}
		}
	}
	

	private void deleteSet() {
		logger.info("Clearing redis list (KEY=" + KEY + ")");
		redisClient.del(KEY);
	}
	
	public void storeTopic (Topic topic) {
		redisClient.zadd(KEY, topic.getTopicId(), topic.toJSON().toString());
	}

/*	public Set<Topic> getTopics () {
		Set<String> topicsStrSet = redisClient.zrange(KEY, 0, -1);
		Set<Topic> topicsObjSet = new HashSet<Topic>();
		Iterator<String> i = topicsStrSet.iterator();
		
		while(i.hasNext()) {
			topicsObjSet.add(new Topic(i.next()));
		}
		
		return topicsObjSet;
	}
*/	
	public Topic getTopic (int topicID) {
		Set<String> setStr = redisClient.zrangeByScore(KEY, topicID, topicID);
		if (setStr.size() < 1) {
			return null;
		}
		
		return new Topic(setStr.iterator().next());
	}
}
