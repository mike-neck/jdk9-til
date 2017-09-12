DROP TABLE IF EXISTS affiliations;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
  id BIGINT NOT NULL,
  name VARCHAR(45) NOT NULL,
  CONSTRAINT PRIMARY KEY (id),
  INDEX employees_name_idx (name ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE companies (
  id BIGINT NOT NULL,
  name VARCHAR(45) NOT NULL,
  CONSTRAINT PRIMARY KEY (id),
  INDEX companies_name_idx (name ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE departments (
  id BIGINT NOT NULL,
  company_id BIGINT NOT NULL,
  name VARCHAR(45) NOT NULL,
  CONSTRAINT PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (company_id) REFERENCES companies(id),
  INDEX departments_name_idx (name ASC),
  INDEX departments_companies_name_idx (company_id ASC, name ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE affiliations (
  employee_id BIGINT NOT NULL,
  department_id BIGINT NOT NULL,
  CONSTRAINT PRIMARY KEY (employee_id, department_id),
  INDEX affiliations_department_user_idx (department_id ASC, employee_id ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
