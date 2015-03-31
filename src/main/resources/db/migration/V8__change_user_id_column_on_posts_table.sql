ALTER TABLE posts DROP COLUMN user_id;
ALTER TABLE posts ADD user_id VARCHAR(255);
ALTER TABLE posts ADD FOREIGN KEY (user_id) REFERENCES users(username)
