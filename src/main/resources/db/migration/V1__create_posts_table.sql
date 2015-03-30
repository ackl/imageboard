CREATE TABLE posts (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	parent_id INT,
	date BIGINT NOT NULL,
	image_url VARCHAR(255),
	content VARCHAR(255),
	subject VARCHAR(255)
);
