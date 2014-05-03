package com.wdeanmedical.pm.util;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.entity.Patient;

public class MailHandler {

    protected final Logger logUtil = Logger.getLogger(MailHandler.class.getName());
    String host = Core.smtphost;
    String port = Core.smtpport;

    protected boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public void sendMimeMessage(String to, String cc,String from, String text, String subject,boolean isHtmlText) throws Exception {    
        sendMimeMessage(to, cc,from, text, subject, null, new byte[0], null, new byte[0],isHtmlText);
    }

    public void sendMimeMessage(String to, String from, String text, String subject,boolean isHtmlText) throws Exception {
        sendMimeMessage(to, null,from, text, subject, null, new byte[0], null, new byte[0],isHtmlText);
    }

    public void sendMimeMessage(String to, String cc,String from, String text, String subject,String attachmentName,
            byte[] attachment,boolean isHtmlText) throws Exception {
        sendMimeMessage(to, cc,from, text, subject, attachmentName, attachment, null, new byte[0],isHtmlText);
    }

    public void sendMimeMessage(String to, String cc, String bcc, String from, String text, String subject,
            boolean isHtmlText) throws Exception {
        sendMimeMessage(to, cc, bcc, from, text, subject, null, new byte[0], null, new byte[0],isHtmlText);
    }

    public void sendMimeMessage(String to, String from, String text, String subject) throws Exception {
        sendMimeMessage(to, from, text, subject, null, new byte[0], null, new byte[0]);
    }

    public void sendMimeMessage(String to, String from, String text, String subject, String attachmentName, 
            String attachmentContents) throws Exception {
        byte[] attachment = attachmentContents.getBytes();
        sendMimeMessage(to, from, text, subject, attachmentName, attachment, null, new byte[0]);
    }

    public void sendMimeMessage(String to, String from, String text, String subject, String attachmentName, 
            String attachmentContents, String attachmentName2, byte[] attachment2) throws Exception { 
        byte[] attachment = attachmentContents.getBytes();
        sendMimeMessage(to, from, text, subject, attachmentName, attachment, attachmentName2, attachment2);
    }

    public void sendMimeMessage(String to, String from, String text, String subject, String attachmentName, byte[] attachment1) throws Exception {
        sendMimeMessage(to, from, text, subject, attachmentName, attachment1, null, new byte[0]);
    }

    public void sendMimeMessage(String to, String from, String text, String subject, String attachmentName, byte[] attachment1, 
            String attachmentName2, byte[] attachment2) throws Exception {
        sendMimeMessage(to, from, text, subject, attachmentName, attachment1, null, new byte[0], host);
    }

    public void sendMimeMessage(String to, String from, String text, String subject, String attachmentName, byte[] attachment1, 
            String attachmentName2, byte[] attachment2, String host) throws Exception {
        from = (from==null || from.equals("")) ? "AnonymousSender@wdeanmedical.com" : from;
        String[] toArray = to.split(";");
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.getSession().setDebug(true);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);
        mimeMessageHelper.setFrom(Core.mailFrom);
        mimeMessageHelper.setTo(toArray);
        mimeMessageHelper.setSubject(subject);

        if (!isEmpty(text)) { 
            mimeMessageHelper.setText(text);
        }
        if (attachmentName != null && !attachmentName.equals("undefined") && attachment1 != null && attachment1.length > 0) {
            Resource resource = new ByteArrayResource(attachment1);
            mimeMessageHelper.addAttachment(attachmentName, resource);
        }
        if (attachmentName2 != null && !attachmentName2.equals("undefined") && attachment2 != null && attachment2.length > 0) {
            Resource resource2 = new ByteArrayResource(attachment2);
            mimeMessageHelper.addAttachment(attachmentName2, resource2);
        }
        if (Core.sendMail) {
            try {
                sender.send(message);
            } 
            catch (Exception e) {
                logUtil.info("ERROR: sending mail to: " + to + ", from: " + from + ", with subject: " + subject);
            }
        }
        else {
            logUtil.info("Trigger to send email has been set to: " + Core.sendMail.toString());
        }
        return;  
    }

    public void sendMimeMessage(String to,String cc, String from, String text, String subject, String attachmentName, byte[] attachment1, 
            String attachmentName2, byte[] attachment2, boolean isHtmlText) throws Exception {
        from = (from==null || from.equals("")) ? "AnonymousSender@wdeanmedical.com" : from;

        String[] toArray = to.split(";");
        String[] ccList = null;

        if(cc!=null && cc.toString().trim().length()>0) {
            ccList = cc.split(";"); 
        }  
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        sender.setJavaMailProperties(props);
       

        sender.setHost(host);
        sender.getSession().setDebug(true);
        sender.setUsername(Core.mailAuthenticationUser);
        sender.setPassword(Core.mailAuthenticationPassword);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);
        mimeMessageHelper.setFrom(Core.mailFrom);
        mimeMessageHelper.setTo(toArray);
        mimeMessageHelper.setSubject(subject);


        if(cc!=null && cc.toString().trim().length()>0) {
            mimeMessageHelper.setCc(ccList);
        }
        if (!isEmpty(text)) { 
            mimeMessageHelper.setText(text,isHtmlText);
        }
        if (attachmentName != null && !attachmentName.equals("undefined") && attachment1 != null && attachment1.length > 0) {
            Resource resource = new ByteArrayResource(attachment1);
            mimeMessageHelper.addAttachment(attachmentName, resource);
        }
        if (attachmentName2 != null && !attachmentName2.equals("undefined") && attachment2 != null && attachment2.length > 0) {
            Resource resource2 = new ByteArrayResource(attachment2);
            mimeMessageHelper.addAttachment(attachmentName2, resource2);
        }
        if (Core.sendMail) {
            try {
                sender.send(message);
            } 
            catch (Exception e) {
                logUtil.info("ERROR: sending mail to: " + to + ", from: " + from + ", with subject: " + subject);
                logUtil.info("EXCEPTION: " + e.getMessage());
            }
        }
        else {
            logUtil.info("Trigger to send email has been set to: " + Core.sendMail.toString());
        }
        return;
    }    


    public void sendMimeMessage(String to,String cc, String bcc, String from, String text, String subject, String attachmentName, byte[] attachment1, 
            String attachmentName2, byte[] attachment2, boolean isHtmlText) throws Exception {
        from = (from==null || from.equals("")) ? "AnonymousSender@wdeanmedical.com" : from;

        String[] toArray = to.split(";");
        String[] ccList = null;

        if(cc!=null && cc.toString().trim().length()>0) {
            ccList = cc.split(";"); 
        }  

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.getSession().setDebug(true);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);
        mimeMessageHelper.setFrom(Core.mailFrom);
        mimeMessageHelper.setTo(toArray);
        mimeMessageHelper.setSubject(subject);


        if(cc!=null && cc.toString().trim().length()>0) {
            mimeMessageHelper.setCc(ccList);
        }
        
        if(bcc!=null) {
            mimeMessageHelper.setBcc(bcc);
        }
        
        if (!isEmpty(text)) { 
            mimeMessageHelper.setText(text,isHtmlText);
        }
        if (attachmentName != null && !attachmentName.equals("undefined") && attachment1 != null && attachment1.length > 0) {
            Resource resource = new ByteArrayResource(attachment1);
            mimeMessageHelper.addAttachment(attachmentName, resource);
        }
        if (attachmentName2 != null && !attachmentName2.equals("undefined") && attachment2 != null && attachment2.length > 0) {
            Resource resource2 = new ByteArrayResource(attachment2);
            mimeMessageHelper.addAttachment(attachmentName2, resource2);
        }
        if (Core.sendMail) {
            try {
                sender.send(message);
            } 
            catch (Exception e) {
                logUtil.info("ERROR: sending mail to: " + to + ", from: " + from + ", with subject: " + subject);
            }
        }
        else {
            logUtil.info("Trigger to send email has been set to: " + Core.sendMail.toString());
        }
        return;
    }    

    public  void sendPasswordReset(Patient user, String newPassword) throws Exception {
        String from = Core.mailFrom;
        String subject = "Password Reset for " + user.getCred().getFirstName() + " " + user.getCred().getLastName();

        StringBuffer mailContentSB = new StringBuffer();

        mailContentSB
        .append("<html>")
        .append("<head>")
        .append("<title>" + subject + "</title>")
        .append("<style>")
        .append("</style>\n")
        .append("</head>\n")
        .append("<body>");

        mailContentSB.append("<p><strong>" + user.getCred().getFirstName() + " " + user.getCred().getLastName() + "<strong></p>\n");
        mailContentSB.append("<p>Your password has been reset to: " + newPassword + "</p>\n");

        mailContentSB.append("</body>\n").append("</html>\n");

        sendMimeMessage(user.getCred().getEmail(), null,from, mailContentSB.toString(), subject, null,null, null, null,true);
    }


    public  void sendNewUserMessage(Patient user, String url) throws Exception {
        String from = Core.mailFrom;
        String subject = "Welcome " + user.getCred().getFirstName() + " " + user.getCred().getLastName();

        StringBuffer mailContentSB = new StringBuffer();

        mailContentSB
        .append("<html>")
        .append("<head>")
        .append("<title>" + subject + "</title>")
        .append("<style>")
        .append("</style>\n")
        .append("</head>\n")
        .append("<body>");

        mailContentSB.append("<p><strong>" + user.getCred().getFirstName() + " " + user.getCred().getLastName() + "<strong></p>\n");
        mailContentSB.append("<p>Welcome to the CRC Scheduler.</p>\n");
        mailContentSB.append("<p>Go to " + url +" and click on the \"forgot password\" link to create a new random password.</p>\n");
        mailContentSB.append("<p>This will be emailed to you and then you may change your password once you have logged in.</p>\n");

        mailContentSB.append("</body>\n").append("</html>\n");

        sendMimeMessage(user.getCred().getEmail(), null,from, mailContentSB.toString(), subject, null,null, null, null,true);
    }
    
    public  void sendNewUserRegistrationMessage(Patient user, String url, List<Patient> superAdmins) throws Exception {
        String from = Core.mailFrom;
        String subject = "Welcome " + user.getCred().getFirstName() + " " + user.getCred().getLastName();

        String superAdminsubject = "New User Registered: " + user.getCred().getFirstName() + " " + user.getCred().getLastName();
        //Super Admin
        StringBuffer mailContentadminSB = new StringBuffer();

        mailContentadminSB
        .append("<html>")
        .append("<head>")
        .append("<title>" + superAdminsubject + "</title>")
        .append("<style>")
        .append("</style>\n")
        .append("</head>\n")
        .append("<body>");

        mailContentadminSB.append("<p><strong>" + user.getCred().getFirstName() + " " + user.getCred().getLastName() + " has registered." + "<strong></p>\n");
        mailContentadminSB.append("<p>Please review the user information and activate the user, to allow access to the system.</p>\n");
        mailContentadminSB.append("<p> You can go to " + url +" and activate the user.</p>\n");
        mailContentadminSB.append("<p>The user will notified of their access to the system once the account is activated.</p>\n");

        mailContentadminSB.append("</body>\n").append("</html>\n");
        
        
        // User
        StringBuffer mailContentSB = new StringBuffer();

        mailContentSB
        .append("<html>")
        .append("<head>")
        .append("<title>" + subject + "</title>")
        .append("<style>")
        .append("</style>\n")
        .append("</head>\n")
        .append("<body>");

        mailContentSB.append("<p>Welcome to the CRC Scheduler.</p>\n");
        mailContentSB.append("<p><strong>" + user.getCred().getFirstName() + " " + user.getCred().getLastName() + " ,<strong></p>\n");
        mailContentSB.append("<p>You will have access to the system once approved by the Super Admin. Please wait for the final confirmation.</p>\n");
        mailContentSB.append("<p> You can go to " + url +" and click on the \"forgot password\" link to create a new random password.</p>\n");
        mailContentSB.append("<p>This will be emailed to you and then you may change your password once you have logged in.</p>\n");

        mailContentSB.append("</body>\n").append("</html>\n");
        
        for (Patient u: superAdmins){
            sendMimeMessage(u.getCred().getEmail(), null,from, mailContentadminSB.toString(), superAdminsubject, null,null, null, null,true); 
        }
        
        sendMimeMessage(user.getCred().getEmail(), null,from, mailContentSB.toString(), subject, null,null, null, null,true);
    }
    
    public  void sendNewUserActivationMessage(Patient user, String url) throws Exception {
        String from = Core.mailFrom;
        String subject = "Welcome " + user.getCred().getFirstName() + " " + user.getCred().getLastName();

        StringBuffer mailContentSB = new StringBuffer();

        mailContentSB
        .append("<html>")
        .append("<head>")
        .append("<title>" + subject + "</title>")
        .append("<style>")
        .append("</style>\n")
        .append("</head>\n")
        .append("<body>");

        mailContentSB.append("<p><strong>" + user.getCred().getFirstName() + " " + user.getCred().getLastName() + "<strong></p>\n");
        mailContentSB.append("<p>Welcome to the CRC Scheduler.</p>\n");
        mailContentSB.append("<p>Your account have been activated. You can now access the system.</p>\n");
        mailContentSB.append("<p> You can go to " + url +" and click on the \"forgot password\" link to create a new random password.</p>\n");
        mailContentSB.append("<p>This will be emailed to you and then you may change your password once you have logged in.</p>\n");

        mailContentSB.append("</body>\n").append("</html>\n");

        sendMimeMessage(user.getCred().getEmail(), null,from, mailContentSB.toString(), subject, null,null, null, null,true);
    }
    
}
