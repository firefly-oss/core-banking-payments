-- V4__Enhance_Data_Model.sql

-- ============================================================================
-- ENUM: payment_priority_enum
-- ============================================================================
CREATE TYPE payment_priority_enum AS ENUM (
    'NORMAL',
    'HIGH',
    'URGENT'
);

-- ============================================================================
-- ENUM: payment_purpose_enum
-- ============================================================================
CREATE TYPE payment_purpose_enum AS ENUM (
    'INVOICE_PAYMENT',
    'SALARY_PAYMENT',
    'SUPPLIER_PAYMENT',
    'TAX_PAYMENT',
    'UTILITY_PAYMENT',
    'LOAN_PAYMENT',
    'INVESTMENT',
    'DONATION',
    'OTHER'
);

-- ============================================================================
-- ENUM: payment_channel_enum
-- ============================================================================
CREATE TYPE payment_channel_enum AS ENUM (
    'ONLINE_BANKING',
    'MOBILE_APP',
    'BRANCH',
    'ATM',
    'API',
    'BATCH'
);

-- ============================================================================
-- ENUM: fee_type_enum
-- ============================================================================
CREATE TYPE fee_type_enum AS ENUM (
    'TRANSACTION_FEE',
    'PROCESSING_FEE',
    'CURRENCY_CONVERSION_FEE',
    'INTERNATIONAL_TRANSFER_FEE',
    'URGENT_PROCESSING_FEE',
    'CORRESPONDENT_BANK_FEE'
);

-- ============================================================================
-- ENUM: beneficiary_type_enum
-- ============================================================================
CREATE TYPE beneficiary_type_enum AS ENUM (
    'INDIVIDUAL',
    'BUSINESS',
    'GOVERNMENT'
);

-- ============================================================================
-- ENUM: payment_category_enum
-- ============================================================================
CREATE TYPE payment_category_enum AS ENUM (
    'DOMESTIC',
    'INTERNATIONAL',
    'INTERNAL'
);

-- ============================================================================
-- Create casts for new enums
-- ============================================================================
CREATE CAST (varchar AS payment_priority_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS payment_purpose_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS payment_channel_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS fee_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS beneficiary_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS payment_category_enum) WITH INOUT AS IMPLICIT;

-- ============================================================================
-- Enhance payment_order table
-- ============================================================================
ALTER TABLE payment_order
    ADD COLUMN IF NOT EXISTS reference_number VARCHAR(50),
    ADD COLUMN IF NOT EXISTS end_to_end_id VARCHAR(35),
    ADD COLUMN IF NOT EXISTS priority payment_priority_enum DEFAULT 'NORMAL',
    ADD COLUMN IF NOT EXISTS purpose payment_purpose_enum,
    ADD COLUMN IF NOT EXISTS purpose_code VARCHAR(4),
    ADD COLUMN IF NOT EXISTS channel payment_channel_enum,
    ADD COLUMN IF NOT EXISTS category payment_category_enum,
    ADD COLUMN IF NOT EXISTS exchange_rate NUMERIC(18, 6),
    ADD COLUMN IF NOT EXISTS charge_bearer VARCHAR(4), -- e.g., 'DEBT', 'CRED', 'SHAR', 'SLEV'
    ADD COLUMN IF NOT EXISTS beneficiary_type beneficiary_type_enum,
    ADD COLUMN IF NOT EXISTS beneficiary_bank_name VARCHAR(140),
    ADD COLUMN IF NOT EXISTS beneficiary_bank_address VARCHAR(255),
    ADD COLUMN IF NOT EXISTS beneficiary_bank_country_code CHAR(2),
    ADD COLUMN IF NOT EXISTS intermediary_bank_name VARCHAR(140),
    ADD COLUMN IF NOT EXISTS intermediary_bank_bic VARCHAR(11),
    ADD COLUMN IF NOT EXISTS intermediary_bank_account VARCHAR(34),
    ADD COLUMN IF NOT EXISTS regulatory_reporting TEXT,
    ADD COLUMN IF NOT EXISTS tax_information TEXT,
    ADD COLUMN IF NOT EXISTS batch_id VARCHAR(50),
    ADD COLUMN IF NOT EXISTS value_date DATE,
    ADD COLUMN IF NOT EXISTS requested_execution_date DATE;

-- ============================================================================
-- Enhance payment_fee table
-- ============================================================================
ALTER TABLE payment_fee
    ADD COLUMN IF NOT EXISTS fee_type fee_type_enum,
    ADD COLUMN IF NOT EXISTS fee_description TEXT,
    ADD COLUMN IF NOT EXISTS fee_calculation_method VARCHAR(50),
    ADD COLUMN IF NOT EXISTS fee_rate NUMERIC(10, 6),
    ADD COLUMN IF NOT EXISTS fee_tax_amount NUMERIC(18, 2),
    ADD COLUMN IF NOT EXISTS fee_tax_rate NUMERIC(10, 6),
    ADD COLUMN IF NOT EXISTS fee_tax_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS fee_waived BOOLEAN DEFAULT FALSE;

-- ============================================================================
-- Enhance payment_method table
-- ============================================================================
ALTER TABLE payment_method
    ADD COLUMN IF NOT EXISTS processing_time_hours INTEGER,
    ADD COLUMN IF NOT EXISTS min_amount NUMERIC(18, 2),
    ADD COLUMN IF NOT EXISTS max_amount NUMERIC(18, 2),
    ADD COLUMN IF NOT EXISTS supported_currencies TEXT[],
    ADD COLUMN IF NOT EXISTS requires_intermediary_bank BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS requires_regulatory_reporting BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS requires_purpose_code BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS default_priority payment_priority_enum;

-- ============================================================================
-- Enhance payment_instruction table
-- ============================================================================
ALTER TABLE payment_instruction
    ADD COLUMN IF NOT EXISTS external_reference VARCHAR(100),
    ADD COLUMN IF NOT EXISTS instruction_priority payment_priority_enum,
    ADD COLUMN IF NOT EXISTS instruction_notes TEXT,
    ADD COLUMN IF NOT EXISTS retry_count INTEGER DEFAULT 0,
    ADD COLUMN IF NOT EXISTS last_retry_date TIMESTAMP,
    ADD COLUMN IF NOT EXISTS error_code VARCHAR(50),
    ADD COLUMN IF NOT EXISTS error_description TEXT;

-- ============================================================================
-- Enhance payment_schedule table
-- ============================================================================
ALTER TABLE payment_schedule
    ADD COLUMN IF NOT EXISTS end_date DATE,
    ADD COLUMN IF NOT EXISTS max_executions INTEGER,
    ADD COLUMN IF NOT EXISTS current_execution_count INTEGER DEFAULT 0,
    ADD COLUMN IF NOT EXISTS next_execution_date DATE,
    ADD COLUMN IF NOT EXISTS day_of_month INTEGER,
    ADD COLUMN IF NOT EXISTS day_of_week INTEGER,
    ADD COLUMN IF NOT EXISTS week_of_month INTEGER,
    ADD COLUMN IF NOT EXISTS month_of_year INTEGER,
    ADD COLUMN IF NOT EXISTS schedule_notes TEXT;

-- ============================================================================
-- Enhance payment_provider table
-- ============================================================================
ALTER TABLE payment_provider
    ADD COLUMN IF NOT EXISTS provider_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS provider_url VARCHAR(255),
    ADD COLUMN IF NOT EXISTS provider_api_key VARCHAR(255),
    ADD COLUMN IF NOT EXISTS provider_username VARCHAR(100),
    ADD COLUMN IF NOT EXISTS provider_account_id VARCHAR(100),
    ADD COLUMN IF NOT EXISTS provider_fee NUMERIC(18, 2),
    ADD COLUMN IF NOT EXISTS provider_fee_currency_code CHAR(3),
    ADD COLUMN IF NOT EXISTS provider_response_code VARCHAR(50),
    ADD COLUMN IF NOT EXISTS provider_response_message TEXT,
    ADD COLUMN IF NOT EXISTS provider_transaction_id VARCHAR(100);

-- ============================================================================
-- Create payment_audit table
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_audit (
    payment_audit_id        BIGSERIAL PRIMARY KEY,
    payment_order_id        BIGINT REFERENCES payment_order(payment_order_id),
    payment_instruction_id  BIGINT REFERENCES payment_instruction(payment_instruction_id),
    action                  VARCHAR(50) NOT NULL,
    action_date             TIMESTAMP NOT NULL DEFAULT NOW(),
    action_by               VARCHAR(100),
    previous_status         VARCHAR(50),
    new_status              VARCHAR(50),
    ip_address              VARCHAR(45),
    user_agent              VARCHAR(255),
    additional_info         JSONB,
    date_created            TIMESTAMP NOT NULL DEFAULT NOW(),
    date_updated            TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- Create payment_compliance table
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_compliance (
    payment_compliance_id   BIGSERIAL PRIMARY KEY,
    payment_order_id        BIGINT NOT NULL REFERENCES payment_order(payment_order_id),
    screening_status        VARCHAR(50) NOT NULL,
    screening_date          TIMESTAMP,
    screening_reference     VARCHAR(100),
    risk_score              INTEGER,
    risk_level              VARCHAR(20),
    compliance_notes        TEXT,
    aml_check_status        VARCHAR(50),
    sanctions_check_status  VARCHAR(50),
    pep_check_status        VARCHAR(50),
    kyc_check_status        VARCHAR(50),
    approved_by             VARCHAR(100),
    approval_date           TIMESTAMP,
    rejection_reason        TEXT,
    date_created            TIMESTAMP NOT NULL DEFAULT NOW(),
    date_updated            TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- Create payment_correspondence table
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_correspondence (
    payment_correspondence_id BIGSERIAL PRIMARY KEY,
    payment_order_id        BIGINT NOT NULL REFERENCES payment_order(payment_order_id),
    correspondence_type     VARCHAR(50) NOT NULL,
    correspondence_date     TIMESTAMP NOT NULL,
    correspondence_channel  VARCHAR(50),
    correspondence_direction VARCHAR(10) NOT NULL, -- 'INBOUND' or 'OUTBOUND'
    correspondent_bank      VARCHAR(140),
    correspondent_reference VARCHAR(100),
    message_content         TEXT,
    attachment_id           BIGINT,
    date_created            TIMESTAMP NOT NULL DEFAULT NOW(),
    date_updated            TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- Create payment_exchange_rate table
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_exchange_rate (
    payment_exchange_rate_id BIGSERIAL PRIMARY KEY,
    payment_order_id        BIGINT NOT NULL REFERENCES payment_order(payment_order_id),
    source_currency         CHAR(3) NOT NULL,
    target_currency         CHAR(3) NOT NULL,
    rate                    NUMERIC(18, 6) NOT NULL,
    rate_date               TIMESTAMP NOT NULL,
    rate_provider           VARCHAR(100),
    rate_type               VARCHAR(50), -- 'SPOT', 'FORWARD', 'FIXED'
    markup_percentage       NUMERIC(10, 6),
    original_amount         NUMERIC(18, 2) NOT NULL,
    converted_amount        NUMERIC(18, 2) NOT NULL,
    date_created            TIMESTAMP NOT NULL DEFAULT NOW(),
    date_updated            TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============================================================================
-- Create payment_beneficiary table
-- ============================================================================
CREATE TABLE IF NOT EXISTS payment_beneficiary (
    payment_beneficiary_id  BIGSERIAL PRIMARY KEY,
    payer_account_id        BIGINT NOT NULL,
    beneficiary_name        VARCHAR(140) NOT NULL,
    beneficiary_type        beneficiary_type_enum,
    beneficiary_account_number VARCHAR(50),
    beneficiary_iban        VARCHAR(34),
    beneficiary_bic         VARCHAR(11),
    beneficiary_address     VARCHAR(255),
    beneficiary_city        VARCHAR(100),
    beneficiary_postal_code VARCHAR(20),
    beneficiary_country_code CHAR(2),
    beneficiary_email       VARCHAR(255),
    beneficiary_phone       VARCHAR(20),
    beneficiary_tax_id      VARCHAR(50),
    beneficiary_bank_name   VARCHAR(140),
    beneficiary_bank_address VARCHAR(255),
    beneficiary_bank_country_code CHAR(2),
    is_favorite             BOOLEAN DEFAULT FALSE,
    nickname                VARCHAR(100),
    date_created            TIMESTAMP NOT NULL DEFAULT NOW(),
    date_updated            TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Add relationship between payment_order and payment_beneficiary
ALTER TABLE payment_order
    ADD COLUMN IF NOT EXISTS payment_beneficiary_id BIGINT REFERENCES payment_beneficiary(payment_beneficiary_id);
