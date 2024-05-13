DELETE
FROM onboardees;
DELETE
FROM users;
DELETE
FROM steps_in_process;
DELETE
FROM steps;
DELETE
FROM processes;
DELETE
FROM onboardees;
DELETE
FROM step_info;


INSERT INTO users (email, password, name, phone_number, permission_level)
VALUES ('user1@example.com', 'password1', 'João Silva', '+351911111111', 'ADMIN'),
       ('user2@example.com', 'password2', 'Marta Santos', '+351922222222', 'ADMIN'),
       ('user3@example.com', 'password3', 'Pedro Costa', '+351933333333', 'HR'),
       ('user4@example.com', 'password4', 'Sofia Oliveira', '+351944444444', 'HR'),
       ('user5@example.com', 'password5', 'Rui Fonseca', '+351955555555', 'HR'),
       ('user6@example.com', 'password6', 'Ana Rodrigues', '+351966666666', 'HR'),
       ('user7@example.com', 'password7', 'Diogo Martins', '+351977777777', 'HR'),
       ('user8@example.com', 'password8', 'Carla Silva', '+351988888888', 'HR'),
       ('user9@example.com', 'password9', 'Ricardo Santos', '+351999999999', 'EMPLOYEE'),
       ('user10@example.com', 'password10', 'Inês Oliveira', '+351900000000', 'EMPLOYEE');

INSERT INTO processes (title, description)
VALUES ('Portugal', 'Process for onboarding employees in Portugal'),
       ('UK', 'Process for onboarding employees in the UK'),
       ('France', 'Process for onboarding employees in France'),
       ('Spain', 'Process for onboarding employees in Spain');

INSERT INTO steps (title, description, deadline, owner, backup)
VALUES ('Step 1', 'Step 1 description', 5, 3, 1),
       ('Step 2', 'Step 2 description', 6, 3, 2),
       ('Step 3', 'Step 3 description', 7, 3, 2),
       ('Step 4', 'Step 4 description', 8, 3, 2),
       ('Step 5', 'Step 5 description', 9, 3, 2),
       ('Step 6', 'Step 6 description', 10, 3, 2),
       ('Step 7', 'Step 7 description', 11, 3, 2),
       ('Step 8', 'Step 8 description', 12, 3, 2),
       ('Step 9', 'Step 9 description', 13, 3, 2),
       ('Step 10', 'Step 10 description', 14, 3, 2);

INSERT INTO steps_in_process(process_id, step_id, position)
VALUES (1, 1, 1),
       (1, 2, 1),
       (2, 1, 1),
       (2, 3, 2),
       (3, 1, 1),
       (3, 4, 2),
       (4, 1, 1);

INSERT INTO onboardees (name, phone_number, email, gender, nationality, annual_salary, payroll_number, start_date,
                        state, process_id)
VALUES ('João Silva', '+351912345678', 'joao.silva@example.com', 'male', 'Portugal', '55000', 'ABC123', '2024-05-03',
        'INCOMING', 1),
       ('Marta Santos', '+351923456789', 'marta.santos@example.com', 'female', 'Portugal', '50000', 'DEF456',
        '2024-05-03', 'INCOMING', 2),
       ('Pedro Costa', '+351934567890', 'pedro.costa@example.com', 'male', 'Portugal', '60000', 'GHI789', '2024-05-03',
        'INCOMING', 3),
       ('Sofia Oliveira', '+351945678901', 'sofia.oliveira@example.com', 'female', 'Portugal', '58000', 'JKL012',
        '2024-05-03', 'ONGOING', 4),
       ('Rui Fonseca', '+351956789012', 'rui.fonseca@example.com', 'male', 'Portugal', '62000', 'MNO345', '2024-05-03',
        'ONGOING', 1),
       ('Alice Smith', '+447890123456', 'alice.smith@example.com', 'female', 'UK', '55000', 'DEF456', '2024-05-03',
        'ONGOING', 2),
       ('Sophie Dubois', '+33123456789', 'sophie.dubois@example.com', 'female', 'France', '45000', 'PQR678',
        '2024-05-03', 'ONGOING', 3),
       ('Juan Martinez', '+541112345678', 'juan.martinez@example.com', 'male', 'Spain', '62000', 'STU901', '2024-05-03',
        'COMPLETED', 4),
       ('Maria Garcia', '+34911234567', 'maria.garcia@example.com', 'female', 'Spain', '58000', 'VWX234', '2024-05-03',
        'COMPLETED', 2),
       ('Lucas Garcia', '+351911234567', 'lucas.garcia@example.com', 'female', 'Portugal', '15000', 'ABC124',
        '2024-05-03', 'ABORTED', 2);

INSERT INTO step_info (step_id, onboardee_id, status) VALUES (1, 1, 'NOT_STARTED');


