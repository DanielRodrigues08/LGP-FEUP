
INSERT INTO onboardees (name, phone_number, email, gender, nationality, annual_salary, payroll_number, start_date, state) VALUES
('João Silva', '+351912345678', 'joao.silva@example.com', 'male', 'Portugal', '55000', 'ABC123', '2024-05-03', 'INCOMING'),
('Marta Santos', '+351923456789', 'marta.santos@example.com', 'female', 'Portugal', '50000', 'DEF456', '2024-05-03', 'INCOMING'),
('Pedro Costa', '+351934567890', 'pedro.costa@example.com', 'male', 'Portugal', '60000', 'GHI789', '2024-05-03', 'INCOMING'),
('Sofia Oliveira', '+351945678901', 'sofia.oliveira@example.com', 'female', 'Portugal', '58000', 'JKL012', '2024-05-03', 'ONGOING'),
('Rui Fonseca', '+351956789012', 'rui.fonseca@example.com', 'male', 'Portugal', '62000', 'MNO345', '2024-05-03', 'ONGOING'),
('Alice Smith', '+447890123456', 'alice.smith@example.com', 'female', 'UK', '55000', 'DEF456', '2024-05-03', 'ONGOING'),
('Sophie Dubois', '+33123456789', 'sophie.dubois@example.com', 'female', 'France', '45000', 'PQR678', '2024-05-03', 'ONGOING'),
('Juan Martinez', '+541112345678', 'juan.martinez@example.com', 'male', 'Spain', '62000', 'STU901', '2024-05-03', 'COMPLETED'),
('Maria Garcia', '+34911234567', 'maria.garcia@example.com', 'female', 'Spain', '58000', 'VWX234', '2024-05-03', 'COMPLETED'),
( 'Lucas Garcia', '+351911234567', 'lucas.garcia@example.com', 'female', 'Portugal', '15000', 'ABC124', '2024-05-03', 'ABORTED');


INSERT INTO users (email, password, name, phone_number, permission_level) VALUES
('user1@example.com', '$2a$12$QNmvz/MnZMpAN5eBUjMATuXFIhzV0jKiQ7dQu86tq9fihiZgaBi12', 'João Silva', '+351911111111', 'ADMIN'),
('user2@example.com', '$2a$12$6pe.T5UbCUarKQwMyfdT/urhWhR8RkFKY87vOxdfNT1UnuTBkNOUW', 'Marta Santos', '+351922222222', 'ADMIN'),
('user3@example.com', '$2a$12$2vdAdQRzUyopkMVbwdEzo.BVXZR4khHYLv0QL.05BAd3Q/DAnFuku', 'Pedro Costa', '+351933333333', 'HR'),
('user4@example.com', '$2a$12$vJq2WX8Ixzqc/E5H82tlp.xyc.g7NNOV0r1H50YTCEoasHl2sQxji', 'Sofia Oliveira', '+351944444444', 'HR'),
('user5@example.com', '$2a$12$VVqTV7LxWrem1e/1TCWaGe3JgA9pO/sClAquoIvwKmMTT1DDHcCFq', 'Rui Fonseca', '+351955555555', 'HR'),
('user6@example.com', '$2a$12$5aKfWLyMQp5HzpGHQywE9Okh4v.Rw0kZaz9ThEqh4h9bil9vVpxP2', 'Ana Rodrigues', '+351966666666', 'HR'),
('user7@example.com', '$2a$12$C1dOJE5ruIPhavdFkH.j9.E1z9G9HpSReoEZjtsGGko/sKVzSACui', 'Diogo Martins', '+351977777777', 'HR'),
('user8@example.com', '$2a$12$AohktM3NXoDr8bXXLbb3w.jlO1vn.m.0QdJ7NWy2qoI4mVwJyQAte', 'Carla Silva', '+351988888888', 'HR'),
('user9@example.com', '$2a$12$bQbwYWoxSTVTj5hP455UrOSsJCWfIJ7kRhmMl5bZLCdMulSLCT2km', 'Ricardo Santos', '+351999999999', 'EMPLOYEE'),
('user10@example.com', '$2a$12$l3W5A31kGu23BX.d9spHk.T8nwbVNsTnk7B0y.0VxAxfTNANkhpwS', 'Inês Oliveira', '+351900000000', 'EMPLOYEE');


