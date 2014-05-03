package com.wdeanmedical.pm.util;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.wdeanmedical.pm.core.Core;


public class AuthenticatedMail {
	
	static String className = "AuthenticatedMail";	
	
	/**
	 * Private Constructor
	 *
	 */
	private AuthenticatedMail(){
	}
	
	/**
	 * sendItem
	 * @param to
	 * @param from
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	public static final void mailItem(String to, String from, String subject, String body) throws MessagingException {
		
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", Core.mailServer);
        props.put("mail.smtp.auth", "true");

        SendMailAuthenticator authenticator = new SendMailAuthenticator();
        Session session = Session.getInstance(props, authenticator);

        Message message = new MimeMessage(session);

        // Fill its headers
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        MimeMultipart content = new MimeMultipart("alternative");

        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setText(body);
        
        MimeBodyPart htmlBody = new MimeBodyPart();
        htmlBody.setContent(body, "text/html");
        
        content.addBodyPart(textBody);
        content.addBodyPart(htmlBody);
        message.setContent(content); 
        Transport.send(message);
		
	}
	
	/**
	 * sendItem to a list of recipients
	 * @param to
	 * @param from
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	public static final void mailItem(List to, String from, String subject, String body) throws MessagingException {
		
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", Core.mailServer);
        props.put("mail.smtp.auth", "true");

        SendMailAuthenticator authenticator = new SendMailAuthenticator();
        Session session = Session.getInstance(props, authenticator);

        Message message = new MimeMessage(session);

        // Fill its headers
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        InternetAddress dests[] = new InternetAddress[to.size()];
        
        Iterator it = to.iterator();
        int i = 0;
        while (it.hasNext()) {
          dests[i] = new InternetAddress((String)it.next());
          i++;
        }
        
        message.setRecipients(Message.RecipientType.TO, dests); 

        MimeMultipart content = new MimeMultipart("alternative");
        
        MimeBodyPart htmlBody = new MimeBodyPart();
        htmlBody.setContent(body, "text/html");
        
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setText(body);
        
        content.addBodyPart(textBody);
        content.addBodyPart(htmlBody);
        message.setContent(content); 
        Transport.send(message);
		
	}
	
	

}

class SendMailAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication(Core.mailAuthenticationUser, Core.mailAuthenticationPassword));
    }
}

