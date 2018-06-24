package br.com.academiadev.reembolsoazul.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.repository.EmailConfigRepository;

@Service
public class EmailService {
	
	@Autowired
	EmailConfigRepository emailConfigRepository;
	
	@Autowired
	JavaMailSender mailSender;

	public void send(String to, String subject, String text) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setText(text);
		message.setSubject(subject);
		mailSender.send(message);
	}
}
