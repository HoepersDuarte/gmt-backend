package br.com.academiadev.reembolsoazul.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.EmailConfig;

@Repository
public interface EmailConfigRepository extends CrudRepository<EmailConfig, Long> {
	public EmailConfig findByKey(String key);
}
