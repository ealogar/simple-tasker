CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    description VARCHAR(256),
    dueDate TIMESTAMP
);