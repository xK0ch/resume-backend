CREATE TABLE resume
(
    id               UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted       BOOLEAN                  NOT NULL,

    first_name       VARCHAR(255)             NOT NULL,
    last_name        VARCHAR(255)             NOT NULL,
    sex              VARCHAR(255)             NOT NULL,
    date_of_birth    DATE                     NOT NULL,
    address          VARCHAR(255)             NOT NULL,
    postal_code      VARCHAR(255)             NOT NULL,
    city             VARCHAR(255)             NOT NULL,
    country          VARCHAR(255)             NOT NULL,
    phone_number     VARCHAR(255) NULL,
    mobile_number    VARCHAR(255) NULL,
    email            VARCHAR(255) NULL,
    description      TEXT                     NOT NULL,
    linkedin         VARCHAR(255) NULL,
    github           VARCHAR(255) NULL,
    status           VARCHAR(255)             NOT NULL
);
