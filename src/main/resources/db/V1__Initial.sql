CREATE TABLE contact_information
(
    id               UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL,

    first_name       VARCHAR(255)             NOT NULL,
    last_name        VARCHAR(255)             NOT NULL
);
