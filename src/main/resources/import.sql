INSERT INTO USUARIO (id, usuario, senha, nome, sobrenome, email, telefone, ativo, ultima_troca_de_senha) VALUES (1, 'user', '$2a$10$k5zQIzDZFujGfJXsdxtEOet1yplg1ZR0Dl8kKORhfDoN/WNSAjBFu', 'Nome do User', 'Sobrenome do User', 'user@example.com', '+1234567890', 'true', '2017-10-01');
INSERT INTO USUARIO (id, usuario,  senha, nome, sobrenome, email, telefone, ativo, ultima_troca_de_senha) VALUES (2, 'admin', '$2a$10$k5zQIzDZFujGfJXsdxtEOet1yplg1ZR0Dl8kKORhfDoN/WNSAjBFu', 'Nome do Admin', 'Sobrenome do Admin', 'admin@example.com', '+0987654321', 'true', '2017-10-01');

INSERT INTO AUTORIZACAO (id, nome) VALUES (1, 'ROLE_USER');
INSERT INTO AUTORIZACAO (id, nome) VALUES (2, 'ROLE_ADMIN');

INSERT INTO USUARIO_AUTORIZACAO (usuario_id, autorizacao_id) VALUES (1, 1);
INSERT INTO USUARIO_AUTORIZACAO (usuario_id, autorizacao_id) VALUES (2, 1);
INSERT INTO USUARIO_AUTORIZACAO (usuario_id, autorizacao_id) VALUES (2, 2);


SELECT * FROM USERI;
SELECT * FROM AUTORIZACAO;
SELECT * FROM COMPANY;

INSERT INTO AUTORIZACAO (id, nome) VALUES (1, 'ROLE_USER');
INSERT INTO AUTORIZACAO (id, nome) VALUES (2, 'ROLE_ADMIN');

INSERT INTO COMPANY (id, company_admin_code, company_user_code, name) VALUES
(10, 'admin-code', 'user-code', 'Company name');

INSERT INTO USERI (id, email,  name, password, user_type, company_id, last_password_change) VALUES 
(2, 'admin', 'Admin name', '$2a$10$k5zQIzDZFujGfJXsdxtEOet1yplg1ZR0Dl8kKORhfDoN/WNSAjBFu', 1, 10, '2017-10-01');