INSERT INTO COMPANY (id, company_admin_code, company_user_code, name) VALUES (10, 'admin-code', 'user-code', 'Company name');

INSERT INTO USERI (id, email,  name, password, user_type, company_id, last_password_change) VALUES (2, 'admin', 'Admin name', '$2a$10$nW9TstAiJ0Eiewy3wJ4pk.WjM4lQFj41GakDNU4MqHdvAfC1fz9YG', 1, 10, '2017-10-01');

INSERT INTO USERI (id, email,  name, password, user_type, company_id, last_password_change) VALUES (3, 'user', 'User name', '$2a$10$Y6JAKuyBHEjg..ybDiJ0HucjW0ZVLZj2f7/7ij350L0lGQUfQDk0C', 0, 10, '2017-10-01');