CREATE SEQUENCE msg_seq START 1 INCREMENT 1;

CREATE TABLE messages (
    id BIGINT PRIMARY KEY DEFAULT nextval('msg_seq'),
    content TEXT,
    state VARCHAR(50),
    type VARCHAR(50),
    chat_id VARCHAR(255) NOT NULL,
    sender_id VARCHAR(255) NOT NULL,
    receiver_id VARCHAR(255) NOT NULL,
    media_file_path VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (chat_id) REFERENCES chat(id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);