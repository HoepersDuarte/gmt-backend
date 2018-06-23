package br.com.academiadev.reembolsoazul.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.academiadev.reembolsoazul.model.EmailConfig;

public interface EmailConfigRepository extends CrudRepository<EmailConfig, Long>{
	public EmailConfig findByKey(String key);
}
