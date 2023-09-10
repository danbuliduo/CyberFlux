CREATE TABLE IF NOT EXISTS account (
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);
INSERT INTO
    account (username, password)
SELECT 'admin', 'cyberflux'
FROM DUAL
WHERE NOT EXISTS (
        SELECT *
        FROM account
    );