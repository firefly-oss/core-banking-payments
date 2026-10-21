-- The legacy payment schedule key is not a UUID in every database.
-- Keep the run's schedule id as a UUID column and do not force a foreign key onto that legacy key.

ALTER TABLE standing_order_run
    DROP CONSTRAINT IF EXISTS standing_order_run_payment_schedule_id_fkey;
