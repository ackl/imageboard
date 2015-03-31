CREATE TABLE users (
	username VARCHAR(255) NOT NULL PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
	image_url VARCHAR(255),
	keycode VARCHAR(255)
);

CREATE TABLE user_roles (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  ROLE VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (ROLE,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users(username,password,enabled)
VALUES ('admin','$2a$10$3sIUO2HU57fxIAhrmU9ij.wpyMHjBwHBx4iSwtdRlkYHhaDoGlGte', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('andrew','$2a$10$3sIUO2HU57fxIAhrmU9ij.wpyMHjBwHBx4iSwtdRlkYHhaDoGlGte', TRUE);

INSERT INTO user_roles (username, ROLE)
VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, ROLE)
VALUES ('andrew', 'ROLE_USER');
