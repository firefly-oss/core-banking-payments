-- V2__Create_Tables.sql

-- Enable UUID generation functions
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================================================================
-- TABLE: payment_method (Reference Data)
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_method (
                                payment_method_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                method_name         VARCHAR(100)   NOT NULL,
                                description         TEXT,
                                active_flag         BOOLEAN        NOT NULL DEFAULT TRUE,
                                date_created        TIMESTAMP      NOT NULL DEFAULT NOW(),
                                date_updated        TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_order (Core Entity)
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_order (
                               payment_order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               payer_account_id UUID               NOT NULL,
                               payment_method_id UUID               REFERENCES payment_method(payment_method_id),
                               beneficiary_name            VARCHAR(255)         NOT NULL,
                               beneficiary_account_number  VARCHAR(50),
                               beneficiary_iban            VARCHAR(34),
                               beneficiary_bic             VARCHAR(11),
                               beneficiary_address         VARCHAR(255),
                               beneficiary_country_code    CHAR(2),
                               aba_routing_number          VARCHAR(9),
                               payment_type                payment_type_enum    NOT NULL,
                               order_date                  TIMESTAMP            NOT NULL DEFAULT NOW(),
                               status                      payment_order_status_enum NOT NULL,
                               amount                      NUMERIC(18, 2)       NOT NULL,
                               currency_code               CHAR(3)              NOT NULL,
                               swift_bic_code              VARCHAR(11),
                               remittance_information      TEXT,
                               document_id UUID,
                               date_created                TIMESTAMP            NOT NULL DEFAULT NOW(),
                               date_updated                TIMESTAMP            NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_instruction
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_instruction (
                                     payment_instruction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     payment_order_id UUID               NOT NULL REFERENCES payment_order(payment_order_id),
                                     instruction_id          VARCHAR(100),
                                     instruction_type        instruction_type_enum    NOT NULL,
                                     instruction_date        TIMESTAMP            NOT NULL DEFAULT NOW(),
                                     instruction_status      instruction_status_enum  NOT NULL,
                                     date_created            TIMESTAMP            NOT NULL DEFAULT NOW(),
                                     date_updated            TIMESTAMP            NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_schedule
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_schedule (
                                  payment_schedule_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  payment_order_id UUID                  NOT NULL REFERENCES payment_order(payment_order_id),
                                  scheduled_date      TIMESTAMP               NOT NULL,
                                  amount              NUMERIC(18,2)           NOT NULL,
                                  frequency           frequency_enum          NOT NULL,
                                  schedule_status     schedule_status_enum    NOT NULL,
                                  date_created        TIMESTAMP               NOT NULL DEFAULT NOW(),
                                  date_updated        TIMESTAMP               NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payroll_order
-- ============================================================================
CREATE TABLE IF NOT EXISTS payroll_order (
                               payroll_order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               payment_order_id UUID                  NOT NULL REFERENCES payment_order(payment_order_id),
                               payroll_reference   VARCHAR(100),
                               payroll_date        TIMESTAMP               NOT NULL DEFAULT NOW(),
                               total_amount        NUMERIC(18,2)           NOT NULL,
                               payroll_status      payroll_status_enum     NOT NULL,
                               date_created        TIMESTAMP               NOT NULL DEFAULT NOW(),
                               date_updated        TIMESTAMP               NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_fee
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_fee (
                             payment_fee_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             payment_order_id UUID         NOT NULL REFERENCES payment_order(payment_order_id),
                             fee_type            VARCHAR(100)   NOT NULL,
                             fee_amount          NUMERIC(18,2)  NOT NULL,
                             fee_currency_code   CHAR(3)        NOT NULL,
                             date_created        TIMESTAMP      NOT NULL DEFAULT NOW(),
                             date_updated        TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_provider
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_provider (
                                  payment_provider_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  payment_order_id UUID                  NOT NULL REFERENCES payment_order(payment_order_id),
                                  provider_name       VARCHAR(100)            NOT NULL,
                                  external_reference  VARCHAR(255),
                                  status              provider_status_enum    NOT NULL,
                                  date_created        TIMESTAMP               NOT NULL DEFAULT NOW(),
                                  date_updated        TIMESTAMP               NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- TABLE: payment_proof (One-to-One with payment_order)
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_proof (
                               payment_proof_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               payment_order_id UUID                  NOT NULL UNIQUE REFERENCES payment_order(payment_order_id),
                               document_id UUID                  NOT NULL,
                               proof_type          VARCHAR(100)            NOT NULL,
                               proof_date          TIMESTAMP               NOT NULL DEFAULT NOW(),
                               date_created        TIMESTAMP               NOT NULL DEFAULT NOW(),
                               date_updated        TIMESTAMP               NOT NULL DEFAULT NOW()
);
