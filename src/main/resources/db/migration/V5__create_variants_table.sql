CREATE TABLE variants (
                          id BIGSERIAL PRIMARY KEY,
                          product_id BIGINT NOT NULL,
                          color VARCHAR(50),
                          price DECIMAL(19, 2),
                          sku VARCHAR(100) UNIQUE NOT NULL,
                          quantity INT NOT NULL DEFAULT 0,
                          CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);