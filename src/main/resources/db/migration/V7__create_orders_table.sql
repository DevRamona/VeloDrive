CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        total_amount DECIMAL(19, 2) NOT NULL,
                        placed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             variant_id BIGINT NOT NULL,
                             quantity INT NOT NULL,
                             price_at_purchase DECIMAL(19, 2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (variant_id) REFERENCES variants(id)
);