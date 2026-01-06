CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            parent_id BIGINT,
                            CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES categories(id),
                            CONSTRAINT unique_name_per_level UNIQUE (name, parent_id)
);