CREATE TABLE skill
(
    id               UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted       BOOLEAN                  NOT NULL,

    name             VARCHAR(255)             NOT NULL,
    skill_level      VARCHAR(255)             NOT NULL,

    resume_id        UUID                     NOT NULL REFERENCES resume (id)
);