-- ISO currency is VARCHAR(3), not a blank-padded CHAR(3).

DO $$
DECLARE
    r RECORD;
BEGIN
    FOR r IN
        SELECT table_name, column_name
        FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND data_type = 'character'
          AND character_maximum_length = 3
          AND (column_name = 'currency' OR column_name LIKE '%currency%')
    LOOP
        EXECUTE format(
            'ALTER TABLE %I ALTER COLUMN %I TYPE VARCHAR(3)',
            r.table_name, r.column_name);
    END LOOP;
END $$;
