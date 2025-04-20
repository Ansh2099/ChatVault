<<<<<<< HEAD
CREATE TABLE chat (
    id VARCHAR(255) PRIMARY KEY,
    sender_id VARCHAR(255) NOT NULL,
    recipient_id VARCHAR(255) NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id)
=======
CREATE TABLE chat (
    id VARCHAR(255) PRIMARY KEY,
    sender_id VARCHAR(255) NOT NULL,
    recipient_id VARCHAR(255) NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id)
>>>>>>> 0e1f62a (Created more sql files fors flyway, for reliable table creation.)
);