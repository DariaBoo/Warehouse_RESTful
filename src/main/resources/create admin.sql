CREATE USER admin WITH PASSWORD '1234' CREATEDB;

GRANT ALL ON DATABASE warehouse TO admin;   
GRANT ALL PRIVILEGES ON DATABASE warehouse TO admin;