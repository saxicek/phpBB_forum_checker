package com.skysteve.phpBB.forum.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.skysteve.phpBB.forum.config.Config;

/**
 * This class is designed to pull out details from the post
 * @author skysteve
 *
 */
public class PostReader {

	private static Logger logger = Logger.getLogger(PostReader.class.getName());
	
	public static HashMap<String, Object> getPostDetails (Config config, String url) throws ClientProtocolException, IOException {
		HashMap<String, Object> detailsHash = new HashMap<String, Object>();
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		if (config.getDebug().isActive()) {
			logger.info("Getting post data : " + url);
		}
    	HttpGet httpget = new HttpGet(url);
    	HttpResponse response = httpclient.execute(httpget);
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
    	
    	String line = "";
    	
    	
    	while((line=rd.readLine())!=null) {
		
    		while(line != null && !line.contains("class=\"postdetails\"")) {
				line = rd.readLine();
			}
    		
    		if (line == null) {
				break;
			}
    		
    		detailsHash.put("Username", extractUser(line));
    		detailsHash.put("Location", extractLocation(line));
    		
    		do {
    			line = rd.readLine();
    		} while (line != null && !line.contains("class=\"postdetails\""));
    		
    		if (line == null) {
				break;
			}
    		
    		detailsHash.put("Datestamp", extractDatestamp(line));
    		
    		while(line != null && !line.contains("class=\"postbody\"")) {
				line = rd.readLine();
			}
    		
    		if (line == null) {
				break;
			}
    		
    		String message = extractMessage(line);
    		
    		while (!(line=rd.readLine()).contains("</tr>")) {
    			message += line;
    		}
    		
    		detailsHash.put("Message", message);
    		
		}
    	
		return detailsHash;
	}
	
	private static String extractUser(String line) {
		Pattern p = Pattern.compile("<b>.*<\\/b>");
		Matcher m = p.matcher(line);
		
		if (m.find()) {
			return line.substring(m.start() + 3, m.end() - 4);
		}
		
		return null;
	}
	
	private static String extractLocation(String line) {
		line = line.substring(line.indexOf("Location:") + 9).trim();
		return line.substring(0, line.indexOf("<"));
	}
	
	private static Date extractDatestamp(String line) {
		Pattern p = Pattern.compile(":\\s\\w.*\\s\\d+,\\s\\d+\\s\\d+:\\d+\\s[a-z]+");
		Matcher m = p.matcher(line);
		
		
		//Sun Apr 10, 2011 12:45 pm
		DateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy kk:mm a");
		
		if (m.find()) {
			try {
				return sdf.parse(line.substring(m.start()+2, m.end()));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
	private static String extractMessage(String line) {
		return line.substring(line.indexOf("postbody") + 10).trim();
	}

}
