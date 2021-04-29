package pl.lodz.p.it.ssbd2021.ssbd03.services;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;

public class EmailService {

    private static final Properties emailProperties = PropertiesReader.getSecurityProperties();
    private static String EMAIL_USER = getAccount();
    private static String PASSWD = getPasswd();

    public static void sendFromGMail( String[] recipients, String subject, String body) {
        Properties properties = System.getProperties();

        String host = "smtp.gmail.com";
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.user", EMAIL_USER);
        properties.put("mail.smtp.password", PASSWD);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(EMAIL_USER));
            InternetAddress[] toAddress = new InternetAddress[recipients.length];

            // To get the array of addresses
            for( int i = 0; i < recipients.length; i++ ) {
                toAddress[i] = new InternetAddress(recipients[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);

            Transport transport = session.getTransport("smtp");

            transport.connect(host, EMAIL_USER, PASSWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    private static String getAccount() {
        return emailProperties.getProperty("email.user");
    }
    private static String getPasswd() {
        return emailProperties.getProperty("email.passwd");
    }
}