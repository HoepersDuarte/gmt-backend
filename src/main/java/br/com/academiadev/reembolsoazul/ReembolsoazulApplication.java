package br.com.academiadev.reembolsoazul;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.academiadev.reembolsoazul.repository.EmailConfigRepository;

@SpringBootApplication
public class ReembolsoazulApplication {
	
	@Autowired
	private EmailConfigRepository emailConfigRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ReembolsoazulApplication.class, args);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
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
	     
	    return mailSender;
	}
}
