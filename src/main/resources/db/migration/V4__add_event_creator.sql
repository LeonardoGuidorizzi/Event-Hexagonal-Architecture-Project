ALTER TABLE tb_event
    ADD COLUMN creator_id UUID REFERENCES tb_user(id);
