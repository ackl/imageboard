ALTER TABLE users DROP COLUMN keycode;

CREATE TABLE registration_keycodes (
    keycode VARCHAR(255) NOT NULL PRIMARY KEY,
    valid TINYINT NOT NULL DEFAULT 1,
    expiry BIGINT NOT NULL
);
