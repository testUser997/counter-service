CREATE SCHEMA IF NOT EXISTS counter;


CREATE TABLE IF NOT EXISTS counter.user
(
    login varchar(255) NOT NULL,
    request_count BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT counter_user_login_pkey PRIMARY KEY (login)
);
