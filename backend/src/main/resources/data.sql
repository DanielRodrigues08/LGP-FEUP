DELETE FROM step_info;
DELETE FROM onboardees;
DELETE FROM steps_in_process;
DELETE FROM steps;
DELETE FROM processes;
DELETE FROM users;

-- Insert users
INSERT INTO users (user_id, email, password, name, phone_number, permission_level)
VALUES (1, 'user1@example.com', 'password1', 'João Silva', '+351911111111', 'ADMIN'),
       (2, 'user2@example.com', 'password2', 'Marta Santos', '+351922222222', 'ADMIN'),
       (3, 'user3@example.com', 'password3', 'Pedro Costa', '+351933333333', 'HR'),
       (4, 'user4@example.com', 'password4', 'Sofia Oliveira', '+351944444444', 'HR'),
       (5, 'user5@example.com', 'password5', 'Rui Fonseca', '+351955555555', 'HR'),
       (6, 'user6@example.com', 'password6', 'Ana Rodrigues', '+351966666666', 'HR'),
       (7, 'user7@example.com', 'password7', 'Diogo Martins', '+351977777777', 'HR'),
       (8, 'user8@example.com', 'password8', 'Carla Silva', '+351988888888', 'HR'),
       (9, 'user9@example.com', 'password9', 'Ricardo Santos', '+351999999999', 'EMPLOYEE'),
       (10, 'user10@example.com', 'password10', 'Inês Oliveira', '+351900000000', 'EMPLOYEE');

-- Insert processes
INSERT INTO process (title, description)
VALUES ('Portugal', 'Process for onboarding employees in Portugal'),
       ('UK', 'Process for onboarding employees in the UK'),
       ('France', 'Process for onboarding employees in France'),
       ('Spain', 'Process for onboarding employees in Spain');

-- Insert steps
INSERT INTO steps (step_id, title, description, deadline, duration, owner, backup)
VALUES (1, 'Step 1', 'Step 1 description', 5, 1, 3, 1),
       (2, 'Step 2', 'Step 2 description', 6, 3, 3, 2),
       (3, 'Step 3', 'Step 3 description', 7, 0, 3, 2),
       (4, 'Step 4', 'Step 4 description', 8, 0, 3, 2),
       (5, 'Step 5', 'Step 5 description', 9, 0, 3, 2),
       (6, 'Step 6', 'Step 6 description', 10, 0, 3, 2),
       (7, 'Step 7', 'Step 7 description', 11, 0, 3, 2),
       (8, 'Step 8', 'Step 8 description', 12, 0, 3, 2),
       (9, 'Step 9', 'Step 9 description', 13, 0, 3, 2),
       (10, 'Step 10', 'Step 10 description', 14, 0, 3, 2);

-- Insert steps in process
INSERT INTO steps_in_process (process_id, step_id, position)
VALUES (1, 1, 1),
       (1, 2, 1),
       (2, 1, 1),
       (2, 3, 2),
       (3, 1, 1),
       (3, 4, 2),
       (4, 1, 1);

-- Insert onboardees
INSERT INTO onboardees (onboardee_id, name, phone_number, email, gender, nationality, annual_salary, payroll_number, start_date, state, process_id)
VALUES (1, 'João Silva', '+351912345678', 'joao.silva@example.com', 'male', 'Portugal', '55000', 'ABC123', '2024-05-10', 'INCOMING', 1),
       (2, 'Marta Santos', '+351923456789', 'marta.santos@example.com', 'female', 'Portugal', '50000', 'DEF456', '2024-05-03', 'INCOMING', 2),
       (3, 'Pedro Costa', '+351934567890', 'pedro.costa@example.com', 'male', 'Portugal', '60000', 'GHI789', '2024-05-03', 'INCOMING', 3),
       (4, 'Sofia Oliveira', '+351945678901', 'sofia.oliveira@example.com', 'female', 'Portugal', '58000', 'JKL012', '2024-05-03', 'ONGOING', 4),
       (5, 'Rui Fonseca', '+351956789012', 'rui.fonseca@example.com', 'male', 'Portugal', '62000', 'MNO345', '2024-05-03', 'ONGOING', 1),
       (6, 'Alice Smith', '+447890123456', 'alice.smith@example.com', 'female', 'UK', '55000', 'DEF456', '2024-05-03', 'ONGOING', 2),
       (7, 'Sophie Dubois', '+33123456789', 'sophie.dubois@example.com', 'female', 'France', '45000', 'PQR678', '2024-05-03', 'ONGOING', 3),
       (8, 'Juan Martinez', '+541112345678', 'juan.martinez@example.com', 'male', 'Spain', '62000', 'STU901', '2024-05-03', 'COMPLETED', 4),
       (9, 'Maria Garcia', '+34911234567', 'maria.garcia@example.com', 'female', 'Spain', '58000', 'VWX234', '2024-05-03', 'COMPLETED', 2),
       (10, 'Lucas Garcia', '+351911234567', 'lucas.garcia@example.com', 'other', 'Portugal', '15000', 'ABC124', '2024-05-03', 'ABORTED', 2);

-- Insert step info
INSERT INTO step_info (step_id, onboardee_id, status)
VALUES (1, 1, 'NOT_STARTED');