package com.api.Timesheets.services;

import com.api.Timesheets.models.EmailDTO;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  @Value("${spring.mail.host}")
  private String EMAIL_SMTP_HOST;
  @Value("${spring.mail.port}")
  private Integer EMAIL_SMTP_PORT;
  @Value("${spring.mail.username}")
  private String EMAIL_SMTP_USERNAME;
  @Value("${spring.mail.password}")
  private String EMAIL_SMTP_PASSWORD;
  @Value("${spring.mail.username}")
  private String EMAIL_SENDER_ADDRESS;

  private static final Splitter EMAIL_LIST_SPLITTER =
      Splitter.on(CharMatcher.anyOf(",;"))
          .omitEmptyStrings()
          .trimResults();

  private static Properties properties;

  static {
    properties = new Properties();
    properties.put("mail.transport.protocol", "smtps");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.auth", "true");
  }

  public void sendEmail(EmailDTO emailDTO) throws Exception {
    if (!Strings.isNullOrEmpty(emailDTO.getRecepient())) {
      Session session = Session.getDefaultInstance(properties);
      Transport transport = session.getTransport();
      try {
        MimeMessage msg = getMessage(emailDTO, session);
        transport.connect(EMAIL_SMTP_HOST,
            EMAIL_SMTP_PORT,
            EMAIL_SMTP_USERNAME,
            EMAIL_SMTP_PASSWORD);
        transport.sendMessage(msg, msg.getAllRecipients());
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
        transport.close();
      }
    }
  }

  private MimeMessage getMessage(EmailDTO emailDTO, Session session) throws Exception {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(EMAIL_SENDER_ADDRESS));
    message.setRecipients(Message.RecipientType.TO, getRecipients(emailDTO.getRecepient()));
    message.setSubject(emailDTO.getSubject());
    message.setContent(emailDTO.getContent(), "text/html");
    return message;
  }

  private Address[] getRecipients(String recipients) throws AddressException {
    List<String> tokens = EMAIL_LIST_SPLITTER.splitToList(Strings.nullToEmpty(recipients)).stream()
        .map(token -> token.trim())
        .filter(t -> !Strings.isNullOrEmpty(t))
        .collect(Collectors.toList());
    List<InternetAddress> addresses = new ArrayList<>();
    for (String token : tokens) {
      addresses.add(new InternetAddress(token));
    }
    return addresses.toArray(new Address[addresses.size()]);
  }

}