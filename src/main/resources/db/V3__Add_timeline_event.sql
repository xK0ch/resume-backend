CREATE TABLE timeline_event
(
    id               UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted       BOOLEAN                  NOT NULL,

    job_position     VARCHAR(255)             NOT NULL,
    institution      VARCHAR(255)             NOT NULL,
    description      TEXT                     NOT NULL,
    date_of_event    DATE                     NOT NULL,

    resume_id        UUID                     NOT NULL REFERENCES resume (id)
);