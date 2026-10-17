CREATE TABLE orders (
    id           BIGSERIAL PRIMARY KEY,
    customer     VARCHAR(120)   NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL CHECK (total_amount > 0),
    created_at   TIMESTAMPTZ    NOT NULL DEFAULT now()
);
