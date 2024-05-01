DELETE FROM hr_members;
DELETE FROM onboardees;
DELETE FROM users;

INSERT INTO users (user_id, username, password, name, phone_number, email) VALUES
(1, 'user1', 'password1', 'John Doe', '+351912345678', 'john.doe@example.com'),
(2, 'user2', 'password2', 'Jane Smith', '+351923456789', 'jane.smith@example.com'),
(3, 'user3', 'password3', 'Michael Johnson', '+351934567890', 'michael.johnson@example.com'),
(4, 'user4', 'password4', 'Emily Brown', '+351945678901', 'emily.brown@example.com'),
(5, 'user5', 'password5', 'William Taylor', '+351956789012', 'william.taylor@example.com'),
(6, 'user6', 'password6', 'Alex Johnson', '+351967890123', 'alex.johnson@example.com'),
(7, 'user7', 'password7', 'Emma Wilson', '+351978901234', 'emma.wilson@example.com'),
(8, 'user8', 'password8', 'Daniel Martinez', '+351989012345', 'daniel.martinez@example.com'),
(9, 'user9', 'password9', 'Olivia Anderson', '+351900123456', 'olivia.anderson@example.com'),
(10, 'user10', 'password10', 'James Garcia', '+351911234567', 'james.garcia@example.com');

INSERT INTO hr_members (user_id, role) VALUES (1, 'ADMIN');
INSERT INTO onboardees (user_id) VALUES (2);
INSERT INTO onboardees (user_id) VALUES (3);
INSERT INTO onboardees (user_id) VALUES (4);
INSERT INTO onboardees (user_id) VALUES (5);
INSERT INTO onboardees (user_id) VALUES (6);
INSERT INTO onboardees (user_id) VALUES (7);
INSERT INTO onboardees (user_id) VALUES (8);
INSERT INTO onboardees (user_id) VALUES (9);
INSERT INTO onboardees (user_id) VALUES (10);