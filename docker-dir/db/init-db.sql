CREATE USER wallet_user WITH PASSWORD 'password' CREATEDB CREATEROLE;
CREATE DATABASE wallet WITH OWNER wallet_user ENCODING = 'UTF-8' LOCALE = 'en_US.utf8';