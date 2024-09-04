CREATE TABLE IF NOT EXISTS files (
                            id SERIAL PRIMARY KEY,
                            title VARCHAR(255),
                           creation_date TIMESTAMP,
                           description TEXT,
                           file_data TEXT
);