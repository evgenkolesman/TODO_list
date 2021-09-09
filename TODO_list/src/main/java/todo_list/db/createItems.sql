DROP TABLE items;

CREATE TABLE IF NOT EXISTS itemstable (
                                     id SERIAL PRIMARY KEY,
                                     description TEXT,
                                     created TIMESTAMP,
                                     isDone BOOLEAN
);