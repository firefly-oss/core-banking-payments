-- A collected direct debit and a standing-order run.
-- Neither table is a scheme clearing file. The collection hangs off the mandate.
-- The run records the advanced next date. The schedule id is not a foreign key:
-- a repeatable rollback rewrites payment_schedule's primary key.

CREATE TABLE direct_debit_collection (
    direct_debit_collection_id UUID PRIMARY KEY,
    direct_debit_mandate_id    UUID NOT NULL REFERENCES direct_debit_mandate (direct_debit_mandate_id) ON DELETE CASCADE,
    amount                     NUMERIC(19, 4) NOT NULL,
    currency                   VARCHAR(3) NOT NULL,
    value_date                 DATE NOT NULL,
    status                     VARCHAR(16) NOT NULL,
    date_created               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_direct_debit_collection_status CHECK (status = 'CONFIRMED'),
    CONSTRAINT ck_direct_debit_collection_amount CHECK (amount > 0),
    CONSTRAINT ck_direct_debit_collection_currency CHECK (currency ~ '^[A-Z]{3}$')
);

CREATE TABLE standing_order_run (
    standing_order_run_id UUID PRIMARY KEY,
    payment_schedule_id   UUID NOT NULL,
    amount                NUMERIC(19, 4) NOT NULL,
    currency              VARCHAR(3) NOT NULL,
    value_date            DATE NOT NULL,
    next_execution_date   DATE NOT NULL,
    execution_count       INTEGER NOT NULL,
    date_created          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_standing_order_run_currency CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT ck_standing_order_run_amount CHECK (amount > 0),
    CONSTRAINT ck_standing_order_run_count CHECK (execution_count >= 0)
);
