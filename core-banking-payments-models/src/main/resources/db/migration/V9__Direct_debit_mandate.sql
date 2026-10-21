-- A direct-debit mandate. There is no scheme clearing file.

CREATE TABLE direct_debit_mandate (
    direct_debit_mandate_id UUID PRIMARY KEY,
    debtor_account_id       UUID NOT NULL,
    creditor_party_id       UUID NOT NULL,
    status                  VARCHAR(16) NOT NULL,
    amount_limit            NUMERIC(19, 4) NOT NULL,
    currency                VARCHAR(3) NOT NULL,
    reference               VARCHAR(64) NOT NULL,
    date_created            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_direct_debit_mandate_status CHECK (status IN ('ACTIVE', 'SUSPENDED', 'CANCELLED')),
    CONSTRAINT ck_direct_debit_mandate_currency CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT ck_direct_debit_mandate_limit CHECK (amount_limit >= 0)
);
