package pl.lodz.p.it.ssbd2021.ssbd03.services;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.EmailServiceException;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.PropertiesReader;
import pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors.TrackingInterceptor;

import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.EMAIL_SERVICE_INACCESSIBLE;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.EMAIL_SERVICE_INCORRECT_EMAIL;

/**
 * The type Email service.
 */
@Interceptors(TrackingInterceptor.class)
public class EmailService {

    private static final Properties emailProperties = PropertiesReader.getSecurityProperties();
    private static String EMAIL_USER = getAccount();
    private static String PASSWD = getPasswd();


    /**
     * Wysyła email dla podanego użytkownika
     *
     * @param recipientEmail email odbiorcy
     * @param subject        podmiot
     * @param contentHtml    zawartość w postaci HTML
     * @throws EmailServiceException wyjątek jest rzucany gdy email jest niepoprawny albo gdy wystąpił problem podłączenia się do serwisu smtp
     */
    public static void sendEmailWithContent(String recipientEmail, String subject, String contentHtml) throws EmailServiceException {
        //todo uncomment it when needed
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
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setFrom(new InternetAddress(EMAIL_USER));

            message.setSubject(subject);
            message.setContent(contentHtml, "text/html; charset=UTF-8");

            Transport transport = session.getTransport("smtp");

            transport.connect(host, EMAIL_USER, PASSWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) { // todo
            throw new EmailServiceException(EMAIL_SERVICE_INCORRECT_EMAIL);
        } catch (MessagingException me) { // todo
            throw new EmailServiceException(EMAIL_SERVICE_INACCESSIBLE);
        }
    }

    /**
     * Wysyła mail dla podanych odbiorców
     *
     * @param recipients lista emaili odbiorców
     * @param subject    temat maila
     * @param body       zawartość maila
     */
    public static void sendFromGMail(String[] recipients, String subject, String body) {
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
            for (int i = 0; i < recipients.length; i++) {
                toAddress[i] = new InternetAddress(recipients[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport transport = session.getTransport("smtp");

            transport.connect(host, EMAIL_USER, PASSWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
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