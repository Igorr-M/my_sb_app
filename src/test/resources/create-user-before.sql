DELETE FROM user_role;
DELETE FROM usr;

INSERT INTO usr (id, username, password, active) VALUES
    (1, 'admin', '$2a$08$9py2HfWJg/Yk9zesuLuBLOrNe5o8U1DCdK1iFNZJ/nCCddl8AUeNq', 1),
    (2, 'Igor', '$2a$08$9py2HfWJg/Yk9zesuLuBLOrNe5o8U1DCdK1iFNZJ/nCCddl8AUeNq', 1);

INSERT INTO user_role (user_id, roles) VALUES
    (1, 'ADMIN'), (1, 'USER'),
    (2, 'USER');
