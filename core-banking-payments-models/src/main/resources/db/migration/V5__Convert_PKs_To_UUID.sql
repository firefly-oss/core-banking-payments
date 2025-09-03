-- V5__Convert_PKs_To_UUID.sql
-- Migration to convert primary keys from BIGSERIAL to UUID
-- This migration preserves existing data by creating a mapping table

-- Enable UUID generation functions (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================================================================
-- Create ID mapping tables to preserve relationships during conversion
-- ============================================================================

-- Mapping table for payment_method IDs
CREATE TABLE IF NOT EXISTS payment_method_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_order IDs
CREATE TABLE IF NOT EXISTS payment_order_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_instruction IDs
CREATE TABLE IF NOT EXISTS payment_instruction_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_schedule IDs
CREATE TABLE IF NOT EXISTS payment_schedule_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payroll_order IDs
CREATE TABLE IF NOT EXISTS payroll_order_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_fee IDs
CREATE TABLE IF NOT EXISTS payment_fee_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_provider IDs
CREATE TABLE IF NOT EXISTS payment_provider_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- Mapping table for payment_proof IDs
CREATE TABLE IF NOT EXISTS payment_proof_id_mapping (
    old_id BIGINT PRIMARY KEY,
    new_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

-- ============================================================================
-- Populate mapping tables with existing data
-- ============================================================================

-- Only populate if tables exist and have BIGINT primary keys
DO $$
BEGIN
    -- Check if payment_method table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_method' 
        AND column_name = 'payment_method_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_method_id_mapping (old_id)
        SELECT payment_method_id FROM payment_method
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_order table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_order' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_order_id_mapping (old_id)
        SELECT payment_order_id FROM payment_order
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_instruction table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_instruction' 
        AND column_name = 'payment_instruction_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_instruction_id_mapping (old_id)
        SELECT payment_instruction_id FROM payment_instruction
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_schedule table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_schedule' 
        AND column_name = 'payment_schedule_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_schedule_id_mapping (old_id)
        SELECT payment_schedule_id FROM payment_schedule
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payroll_order table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payroll_order' 
        AND column_name = 'payroll_order_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payroll_order_id_mapping (old_id)
        SELECT payroll_order_id FROM payroll_order
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_fee table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_fee' 
        AND column_name = 'payment_fee_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_fee_id_mapping (old_id)
        SELECT payment_fee_id FROM payment_fee
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_provider table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_provider' 
        AND column_name = 'payment_provider_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_provider_id_mapping (old_id)
        SELECT payment_provider_id FROM payment_provider
        ON CONFLICT (old_id) DO NOTHING;
    END IF;

    -- Check if payment_proof table exists with BIGINT primary key
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_proof' 
        AND column_name = 'payment_proof_id' 
        AND data_type = 'bigint'
    ) THEN
        INSERT INTO payment_proof_id_mapping (old_id)
        SELECT payment_proof_id FROM payment_proof
        ON CONFLICT (old_id) DO NOTHING;
    END IF;
END $$;

-- ============================================================================
-- Convert primary keys from BIGSERIAL to UUID
-- ============================================================================

-- Convert payment_method primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_method' 
        AND column_name = 'payment_method_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_method ADD COLUMN payment_method_id_new UUID;
        
        -- Update with mapped UUIDs
        UPDATE payment_method 
        SET payment_method_id_new = m.new_id
        FROM payment_method_id_mapping m
        WHERE payment_method.payment_method_id = m.old_id;
        
        -- Drop old primary key constraint
        ALTER TABLE payment_method DROP CONSTRAINT payment_method_pkey;
        
        -- Drop old column
        ALTER TABLE payment_method DROP COLUMN payment_method_id;
        
        -- Rename new column
        ALTER TABLE payment_method RENAME COLUMN payment_method_id_new TO payment_method_id;
        
        -- Add new primary key constraint
        ALTER TABLE payment_method ADD PRIMARY KEY (payment_method_id);
        
        -- Set default value for new records
        ALTER TABLE payment_method ALTER COLUMN payment_method_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_order primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'payment_order_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_order ADD COLUMN payment_order_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_order
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_order.payment_order_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_order DROP CONSTRAINT payment_order_pkey;

        -- Drop old column
        ALTER TABLE payment_order DROP COLUMN payment_order_id;

        -- Rename new column
        ALTER TABLE payment_order RENAME COLUMN payment_order_id_new TO payment_order_id;

        -- Add new primary key constraint
        ALTER TABLE payment_order ADD PRIMARY KEY (payment_order_id);

        -- Set default value for new records
        ALTER TABLE payment_order ALTER COLUMN payment_order_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_instruction primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_instruction'
        AND column_name = 'payment_instruction_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_instruction ADD COLUMN payment_instruction_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_instruction
        SET payment_instruction_id_new = m.new_id
        FROM payment_instruction_id_mapping m
        WHERE payment_instruction.payment_instruction_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_instruction DROP CONSTRAINT payment_instruction_pkey;

        -- Drop old column
        ALTER TABLE payment_instruction DROP COLUMN payment_instruction_id;

        -- Rename new column
        ALTER TABLE payment_instruction RENAME COLUMN payment_instruction_id_new TO payment_instruction_id;

        -- Add new primary key constraint
        ALTER TABLE payment_instruction ADD PRIMARY KEY (payment_instruction_id);

        -- Set default value for new records
        ALTER TABLE payment_instruction ALTER COLUMN payment_instruction_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_schedule primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_schedule'
        AND column_name = 'payment_schedule_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_schedule ADD COLUMN payment_schedule_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_schedule
        SET payment_schedule_id_new = m.new_id
        FROM payment_schedule_id_mapping m
        WHERE payment_schedule.payment_schedule_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_schedule DROP CONSTRAINT payment_schedule_pkey;

        -- Drop old column
        ALTER TABLE payment_schedule DROP COLUMN payment_schedule_id;

        -- Rename new column
        ALTER TABLE payment_schedule RENAME COLUMN payment_schedule_id_new TO payment_schedule_id;

        -- Add new primary key constraint
        ALTER TABLE payment_schedule ADD PRIMARY KEY (payment_schedule_id);

        -- Set default value for new records
        ALTER TABLE payment_schedule ALTER COLUMN payment_schedule_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payroll_order primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payroll_order'
        AND column_name = 'payroll_order_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payroll_order ADD COLUMN payroll_order_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payroll_order
        SET payroll_order_id_new = m.new_id
        FROM payroll_order_id_mapping m
        WHERE payroll_order.payroll_order_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payroll_order DROP CONSTRAINT payroll_order_pkey;

        -- Drop old column
        ALTER TABLE payroll_order DROP COLUMN payroll_order_id;

        -- Rename new column
        ALTER TABLE payroll_order RENAME COLUMN payroll_order_id_new TO payroll_order_id;

        -- Add new primary key constraint
        ALTER TABLE payroll_order ADD PRIMARY KEY (payroll_order_id);

        -- Set default value for new records
        ALTER TABLE payroll_order ALTER COLUMN payroll_order_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_fee primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_fee'
        AND column_name = 'payment_fee_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_fee ADD COLUMN payment_fee_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_fee
        SET payment_fee_id_new = m.new_id
        FROM payment_fee_id_mapping m
        WHERE payment_fee.payment_fee_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_fee DROP CONSTRAINT payment_fee_pkey;

        -- Drop old column
        ALTER TABLE payment_fee DROP COLUMN payment_fee_id;

        -- Rename new column
        ALTER TABLE payment_fee RENAME COLUMN payment_fee_id_new TO payment_fee_id;

        -- Add new primary key constraint
        ALTER TABLE payment_fee ADD PRIMARY KEY (payment_fee_id);

        -- Set default value for new records
        ALTER TABLE payment_fee ALTER COLUMN payment_fee_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_provider primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_provider'
        AND column_name = 'payment_provider_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_provider ADD COLUMN payment_provider_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_provider
        SET payment_provider_id_new = m.new_id
        FROM payment_provider_id_mapping m
        WHERE payment_provider.payment_provider_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_provider DROP CONSTRAINT payment_provider_pkey;

        -- Drop old column
        ALTER TABLE payment_provider DROP COLUMN payment_provider_id;

        -- Rename new column
        ALTER TABLE payment_provider RENAME COLUMN payment_provider_id_new TO payment_provider_id;

        -- Add new primary key constraint
        ALTER TABLE payment_provider ADD PRIMARY KEY (payment_provider_id);

        -- Set default value for new records
        ALTER TABLE payment_provider ALTER COLUMN payment_provider_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- Convert payment_proof primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_proof'
        AND column_name = 'payment_proof_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_proof ADD COLUMN payment_proof_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_proof
        SET payment_proof_id_new = m.new_id
        FROM payment_proof_id_mapping m
        WHERE payment_proof.payment_proof_id = m.old_id;

        -- Drop old primary key constraint
        ALTER TABLE payment_proof DROP CONSTRAINT payment_proof_pkey;

        -- Drop old column
        ALTER TABLE payment_proof DROP COLUMN payment_proof_id;

        -- Rename new column
        ALTER TABLE payment_proof RENAME COLUMN payment_proof_id_new TO payment_proof_id;

        -- Add new primary key constraint
        ALTER TABLE payment_proof ADD PRIMARY KEY (payment_proof_id);

        -- Set default value for new records
        ALTER TABLE payment_proof ALTER COLUMN payment_proof_id SET DEFAULT gen_random_uuid();
    END IF;
END $$;

-- ============================================================================
-- Note: Mapping tables are kept for the next migration (V6) to convert foreign keys
-- They will be dropped in V7 after all conversions are complete
-- ============================================================================
