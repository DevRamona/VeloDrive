CREATE TABLE product_collections (
                                     product_id BIGINT NOT NULL,
                                     collection_id BIGINT NOT NULL,
                                     PRIMARY KEY (product_id, collection_id),
                                     FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                     FOREIGN KEY (collection_id) REFERENCES collections(id) ON DELETE CASCADE
);