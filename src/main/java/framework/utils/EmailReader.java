package framework.utils;

import framework.models.Mail;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

import static framework.utils.LoggerUtil.LOGGER;

public class EmailReader {

    public static void deleteAllMails(Mail mail) {
        try {
            Session emailSession = Session.getInstance(PropertyManager.getPropertiesForMail(), new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail.getUser(), mail.getPassword());
                }
            });
            Store store = emailSession.getStore(mail.getProtocol());
            store.connect(mail.getHost(), mail.getUser(), mail.getPassword());

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }
            emailFolder.close(true);
            store.close();
        } catch (MessagingException e) {
            LOGGER.error("Error in deleting messages from email", e);
        }
    }

    public static String getFirstMail(Mail mail) {
        try {
            Session emailSession = Session.getInstance(PropertyManager.getPropertiesForMail(), new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail.getUser(), mail.getPassword());
                }
            });
            Store store = emailSession.getStore(mail.getProtocol());
            store.connect(mail.getHost(), mail.getUser(), mail.getPassword());

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            if (messages.length > 0) {
                String result = getTextFromMessage(messages[0]);
                emailFolder.close(false);
                store.close();
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("Error in checking messages from email", e);
        }
        throw new IllegalStateException("Mail wasn't send yet");
    }

    public static boolean isMailSend(Mail mail) {
        try {
            getFirstMail(mail);
            return true;
        } catch (IllegalStateException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}
