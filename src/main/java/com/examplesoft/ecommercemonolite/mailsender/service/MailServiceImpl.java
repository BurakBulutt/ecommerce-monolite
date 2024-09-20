package com.examplesoft.ecommercemonolite.mailsender.service;

import com.examplesoft.ecommercemonolite.mailsender.dto.MailDto;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl {
    @Value("${mailSender.username}")
    private String username;
    @Value("${mailSender.password}")
    private String password;
    @Value("${mailSender.from}")
    private String from;

    public JavaMailSender getMailSender() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        return javaMailSender;
    }

    public void sendMail(MailDto mail) {
        MimeMessage mimeMessage = getMailSender().createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, mail.isMultipart(),"UTF-8");

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getBody(), mail.isHtml());

            getMailSender().send(mimeMessage);

            log.info("Mail sent successfully");

        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
