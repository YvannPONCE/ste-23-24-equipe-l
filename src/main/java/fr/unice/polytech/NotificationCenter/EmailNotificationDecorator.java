package fr.unice.polytech.NotificationCenter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailNotificationDecorator implements NotificationDecoratorInterface {
    private String email;

    public EmailNotificationDecorator(String email) {
        this.email = email;
    }

    @Override
    public void sendNotification(String message) {

     //   System.out.println("Email sent to " + email + ": " + message);
    }

    public void sendEmail(String to, String subject, String text) {

        try {
            Session session = setUpSession();
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("Sophiatecheats@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            try {
                attachmentPart.attachFile(new File("img.png"));
                textPart.setText(text);
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            message.setContent(multipart);

            Transport.send(message);


        } catch (MessagingException mex) {
            System.out.println(mex);
        }
    }

    private Session setUpSession(){
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Sophiatecheats@gmail.com", "ggggg");
            }

        });
        session.setDebug(true);

        return session;

    }
}
