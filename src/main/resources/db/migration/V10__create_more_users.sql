INSERT INTO users(username, password, enabled)
VALUES ('trampy','$2a$10$3sIUO2HU57fxIAhrmU9ij.wpyMHjBwHBx4iSwtdRlkYHhaDoGlGte', TRUE);
INSERT INTO users(username, password, enabled)
VALUES ('falafel','$2a$10$3sIUO2HU57fxIAhrmU9ij.wpyMHjBwHBx4iSwtdRlkYHhaDoGlGte', TRUE);
INSERT INTO users(username, password, enabled)
VALUES ('neo','$2a$10$3sIUO2HU57fxIAhrmU9ij.wpyMHjBwHBx4iSwtdRlkYHhaDoGlGte', TRUE);

INSERT INTO user_roles (username, ROLE)
VALUES ('trampy', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('falafel', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('neo', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('neo', 'ROLE_THEONE');
