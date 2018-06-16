INSERT INTO AUTORIZACAO (id, nome) VALUES (1, 'ROLE_USER');
INSERT INTO AUTORIZACAO (id, nome) VALUES (2, 'ROLE_ADMIN');

INSERT INTO COMPANY (id, company_admin_code, company_user_code, name) VALUES (10, 'admin-code', 'user-code', 'Company name');

INSERT INTO USERI (id, email,  name, password, user_type, company_id, last_password_change) VALUES (2, 'admin', 'Admin name', '$2a$10$nW9TstAiJ0Eiewy3wJ4pk.WjM4lQFj41GakDNU4MqHdvAfC1fz9YG', 1, 10, '2017-10-01');