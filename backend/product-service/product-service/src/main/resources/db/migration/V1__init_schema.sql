CREATE TABLE categories (
                            id    BIGSERIAL PRIMARY KEY,
                            name  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
                          id           BIGSERIAL PRIMARY KEY,
                          sku          VARCHAR(50)   NOT NULL UNIQUE,
                          name         VARCHAR(200)  NOT NULL,
                          description  VARCHAR(1000),
                          category_id  BIGINT REFERENCES categories(id),
                          min_stock    INTEGER       NOT NULL,
                          created_at   TIMESTAMP WITH TIME ZONE NOT NULL
);