CREATE TABLE users (
  id BIGINT NOT NULL,
  name VARCHAR(30) NOT NULL,
  hire_date DATE NOT NULL,
  CONSTRAINT PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE todo (
  id BIGINT NOT NULL,
  title VARCHAR(127) NOT NULL,
  description VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL,
  CONSTRAINT PRIMARY KEY (id),
  INDEX todo_title_idx (title)
) ENGINE = InnoDB;

CREATE TABLE todo_reporters (
  user_id BIGINT NOT NULL,
  todo_id BIGINT NOT NULL,
  CONSTRAINT PRIMARY KEY (user_id, todo_id)
) ENGINE = InnoDB;

CREATE TABLE todo_history (
  id BIGINT NOT NULL,
  todo_id BIGINT NOT NULL,
  operator BIGINT NOT NULL ,
  state INT NOT NULL,
  created_at DATETIME NOT NULL,
  CONSTRAINT PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (todo_id) REFERENCES todo (id),
  CONSTRAINT FOREIGN KEY (operator) REFERENCES users (id),
  INDEX todo_history_todo_created_idx (todo_id ASC, created_at DESC)
) ENGINE = InnoDB;

CREATE TABLE generated_ids(
  pk_name VARCHAR(63) NOT NULL PRIMARY KEY,
  generated_id BIGINT NOT NULL
) ENGINE = InnoDB;

INSERT INTO generated_ids (pk_name, generated_id) VALUES
  ('user_id', 0)
  , ('todo_id', 0)
  , ('todo_history_id', 0)
;
