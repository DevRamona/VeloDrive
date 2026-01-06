CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          base_price DECIMAL(19, 2) NOT NULL,
                          material VARCHAR(100),
                          brand_id BIGINT NOT NULL,
                          category_id BIGINT NOT NULL,
                          CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brands(id),
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
);