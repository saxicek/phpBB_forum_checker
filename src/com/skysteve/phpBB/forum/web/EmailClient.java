package com.skysteve.phpBB.forum.web;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.skysteve.phpBB.forum.config.Config;
import com.skysteve.phpBB.forum.main.ForumMessage;

public class EmailClient {
	
	private Config config;
	
	public EmailClient(Config config) {
		this.config = config;
	}

	private void sendMessageInternal(String to, String subject, String body) throws MessagingException {
		Message message = new MimeMessage(getSession());

		message.addRecipient(RecipientType.TO, new InternetAddress(to));
		InternetAddress from = new InternetAddress(config.getMailFrom().getAddress());
		try {
			from.setPersonal(config.getMailFrom().getName());
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}
		message.setFrom(from);
	
		message.setSubject(subject);
		message.setContent(body, "text/html");

		Transport.send(message);
	}

	private Session getSession() {
		Authenticator authenticator = new Authenticator();

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.starttls.enable","true");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(config.getMailFrom().getPort()));
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", config.getMailFrom().getHost());
		properties.setProperty("mail.smtp.port", Integer.toString(config.getMailFrom().getPort()));

		return Session.getInstance(properties, authenticator);
	}
	
	
	public void sendMessage (ForumMessage msg) throws MessagingException {
		Iterator<String> addressIterator = config.getMailTo().getAddresses().iterator();
		while(addressIterator.hasNext()) {
			sendMessageInternal(addressIterator.next(), msg.getSubject(),msg.getBody());
		}	
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator() {
			String username = config.getMailFrom().getUsername();
			String password = config.getMailFrom().getPassword();
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}