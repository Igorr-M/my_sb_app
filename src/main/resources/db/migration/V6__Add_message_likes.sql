CREATE TABLE message_likes (
    user_id BIGINT NOT NULL,
    message_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, message_id),
    FOREIGN KEY (user_id) REFERENCES usr(id),
    FOREIGN KEY (message_id) REFERENCES message(id)
);
