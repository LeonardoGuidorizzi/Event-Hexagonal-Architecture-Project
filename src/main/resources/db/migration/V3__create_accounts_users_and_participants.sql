CREATE TABLE tb_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(320) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_account (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES tb_user(id),
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    birthdate DATE NOT NULL
);

CREATE TABLE tb_event_participant (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL REFERENCES tb_event(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES tb_user(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_event_participant_user UNIQUE (event_id, user_id)
);

CREATE INDEX idx_event_participant_user ON tb_event_participant(user_id);

CREATE TABLE tb_revoked_token (
    token_id VARCHAR(36) PRIMARY KEY,
    expires_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_revoked_token_expires_at ON tb_revoked_token(expires_at);
