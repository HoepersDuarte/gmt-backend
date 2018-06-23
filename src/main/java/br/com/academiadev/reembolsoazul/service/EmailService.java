package br.com.academiadev.reembolsoazul.service;

import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.repository.EmailConfigRepository;

@Service
public class EmailService {
	
	@Autowired
	EmailConfigRepository emailConfigRepository;

	public void send(String to, String subject, String text) throws MessagingException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(emailConfigRepository.findByKey("email").getValue());
		mailSender.setPassword(emailConfigRepository.findByKey("password").getValue());
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setText(text);
		message.setSubject(subject);
		mailSender.send(message);
	}
}
