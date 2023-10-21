INSERT INTO account (username, password, remarks, permissions)
SELECT 'admin', 'cyberflux', 'administrator', ARRAY['ROOT']
FROM DUAL WHERE NOT EXISTS (SELECT *FROM account);