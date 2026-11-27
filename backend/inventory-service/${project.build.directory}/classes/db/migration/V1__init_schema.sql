CREATE TABLE warehouses (
                            id          BIGSERIAL PRIMARY KEY,
                            name        VARCHAR(150) NOT NULL UNIQUE,
                            location    VARCHAR(255),
                            created_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE stock (
                       id            BIGSERIAL PRIMARY KEY,
                       product_id    BIGINT  NOT NULL,
                       warehouse_id  BIGINT  NOT NULL REFERENCES warehouses(id),
                       quantity      INTEGER NOT NULL,
                       version       BIGINT  NOT NULL DEFAULT 0,
                       CONSTRAINT uk_stock_product_warehouse UNIQUE (product_id, warehouse_id),
                       CONSTRAINT ck_stock_quantity_non_negative CHECK (quantity >= 0)
);

CREATE TABLE stock_movements (
                                 id                        BIGSERIAL PRIMARY KEY,
                                 product_id                BIGINT       NOT NULL,
                                 type                      VARCHAR(20)  NOT NULL,
                                 quantity                  INTEGER      NOT NULL,
                                 source_warehouse_id       BIGINT REFERENCES warehouses(id),
                                 destination_warehouse_id  BIGINT REFERENCES warehouses(id),
                                 created_by                VARCHAR(100) NOT NULL,
                                 created_at                TIMESTAMP WITH TIME ZONE NOT NULL,
                                 CONSTRAINT ck_movement_quantity_positive CHECK (quantity > 0)
);

CREATE INDEX idx_movements_product    ON stock_movements(product_id);
CREATE INDEX idx_movements_created_at ON stock_movements(created_at DESC);