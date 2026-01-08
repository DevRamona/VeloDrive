ALTER TABLE variants ADD COLUMN fuel_type VARCHAR(50);
UPDATE variants SET fuel_type = 'Unspecified' WHERE fuel_type IS NULL;
ALTER TABLE variants ALTER COLUMN fuel_type SET NOT NULL;