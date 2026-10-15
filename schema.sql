-- HireSense schema, reconstructed from the queries in the DAO classes.
-- Run once:  mysql -u root -p < schema.sql
CREATE DATABASE IF NOT EXISTS hiresense_db;
USE hiresense_db;

CREATE TABLE IF NOT EXISTS users (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(150) NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,
  role        VARCHAR(20)  NOT NULL DEFAULT 'user',      -- user | employer | admin
  status      VARCHAR(20)  NOT NULL DEFAULT 'active',    -- active | blocked
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS jobs (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  title        VARCHAR(150) NOT NULL,
  description  TEXT,
  skills       VARCHAR(500),                              -- comma separated
  company      VARCHAR(150),
  location     VARCHAR(150),
  experience   VARCHAR(50),
  package_lpa  VARCHAR(50),
  vacancies    INT NOT NULL DEFAULT 1,
  employer_id  INT NOT NULL,
  status       VARCHAR(20) NOT NULL DEFAULT 'active',     -- active | inactive
  created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (employer_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS applications (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL,
  job_id       INT NOT NULL,
  resume_path  VARCHAR(500),
  score        FLOAT,
  status       VARCHAR(20) NOT NULL DEFAULT 'applied',    -- applied | shortlisted | rejected
  applied_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (job_id)  REFERENCES jobs(id)
);

CREATE TABLE IF NOT EXISTS resume_analysis_logs (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL,
  result_json  LONGTEXT,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Registration only creates 'user' and 'employer' accounts, so seed one admin.
-- Local development only: change this password.
INSERT INTO users (name, email, password, role)
SELECT 'Admin', 'admin@hiresense.local', 'admin123', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@hiresense.local');
