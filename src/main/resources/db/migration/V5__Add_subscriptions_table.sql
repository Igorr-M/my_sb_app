CREATE TABLE user_subscriptions (
    channel_id BIGINT NOT NULL,
    subscriber_id BIGINT NOT NULL,
    PRIMARY KEY (channel_id, subscriber_id),
    CONSTRAINT fk_channel FOREIGN KEY (channel_id) REFERENCES usr(id),
    CONSTRAINT fk_subscriber FOREIGN KEY (subscriber_id) REFERENCES usr(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
