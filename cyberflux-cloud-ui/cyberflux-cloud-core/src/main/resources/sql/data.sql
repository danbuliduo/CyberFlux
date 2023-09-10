INSERT INTO
    account (username, password)
SELECT 'admin', 'cyberflux'
FROM DUAL
WHERE NOT EXISTS (
        SELECT *
        FROM account
    );