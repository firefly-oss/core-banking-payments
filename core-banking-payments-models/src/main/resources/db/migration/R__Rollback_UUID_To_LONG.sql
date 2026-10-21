-- This repeatable name stays so databases that already recorded it still validate.
-- The emergency rollback is not applied on startup. Payment keys stay UUIDs,
-- which is what the payment entities store. The manual script is db/manual/Rollback_UUID_To_LONG.sql.
SELECT 1;
