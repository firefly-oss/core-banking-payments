-- R__Rollback_UUID_To_LONG.sql
-- Rollback script to revert UUID conversion back to BIGSERIAL/BIGINT
-- WARNING: This script should only be used in emergency situations
-- and will result in data loss if external systems have been updated to use UUIDs

-- ============================================================================
-- IMPORTANT WARNINGS AND PREREQUISITES
-- ============================================================================

-- WARNING: This rollback script will:
-- 1. Convert all UUID primary keys back to BIGSERIAL
-- 2. Convert all UUID foreign keys back to BIGINT
-- 3. Lose the UUID values permanently
-- 4. Break any external system integrations that depend on UUIDs
-- 5. Require coordination with other microservices

-- PREREQUISITES:
-- 1. Ensure mapping tables from V5 migration still exist
-- 2. Stop all application instances that use this database
-- 3. Create a full database backup before running this script
-- 4. Coordinate with all dependent services

-- ============================================================================
-- Verify mapping tables exist before proceeding
-- ============================================================================

DO $$
BEGIN
    -- Check if mapping tables exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_method_id_mapping') THEN
        RAISE EXCEPTION 'Mapping table payment_method_id_mapping does not exist. Cannot perform rollback.';
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_order_id_mapping') THEN
        RAISE EXCEPTION 'Mapping table payment_order_id_mapping does not exist. Cannot perform rollback.';
    END IF;
    
    -- Add checks for other mapping tables
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_instruction_id_mapping') THEN
        RAISE EXCEPTION 'Mapping table payment_instruction_id_mapping does not exist. Cannot perform rollback.';
    END IF;
    
    RAISE NOTICE 'All required mapping tables found. Proceeding with rollback...';
END $$;

-- ============================================================================
-- Drop dependent objects first (views, indexes, etc.)
-- ============================================================================

-- Drop the verification view created in V7 that depends on UUID columns
DROP VIEW IF EXISTS uuid_conversion_verification;

-- Drop UUID indexes created in V7
DROP INDEX IF EXISTS idx_payment_order_payment_method_id;
DROP INDEX IF EXISTS idx_payment_order_payer_account_id;
DROP INDEX IF EXISTS idx_payment_order_document_id;
DROP INDEX IF EXISTS idx_payment_order_payment_beneficiary_id;
DROP INDEX IF EXISTS idx_payment_instruction_payment_order_id;
DROP INDEX IF EXISTS idx_payment_schedule_payment_order_id;
DROP INDEX IF EXISTS idx_payroll_order_payment_order_id;
DROP INDEX IF EXISTS idx_payment_fee_payment_order_id;
DROP INDEX IF EXISTS idx_payment_provider_payment_order_id;
DROP INDEX IF EXISTS idx_payment_proof_payment_order_id;
DROP INDEX IF EXISTS idx_payment_audit_payment_order_id;
DROP INDEX IF EXISTS idx_payment_audit_payment_instruction_id;
DROP INDEX IF EXISTS idx_payment_compliance_payment_order_id;
DROP INDEX IF EXISTS idx_payment_correspondence_payment_order_id;
DROP INDEX IF EXISTS idx_payment_exchange_rate_payment_order_id;
DROP INDEX IF EXISTS idx_payment_beneficiary_payer_account_id;

-- Drop foreign key constraints from V4 tables that depend on primary keys we need to modify
-- These tables were created in V4 and reference the primary keys we're about to change
ALTER TABLE payment_audit DROP CONSTRAINT IF EXISTS payment_audit_payment_order_id_fkey;
ALTER TABLE payment_audit DROP CONSTRAINT IF EXISTS payment_audit_payment_instruction_id_fkey;
ALTER TABLE payment_compliance DROP CONSTRAINT IF EXISTS payment_compliance_payment_order_id_fkey;
ALTER TABLE payment_correspondence DROP CONSTRAINT IF EXISTS payment_correspondence_payment_order_id_fkey;
ALTER TABLE payment_exchange_rate DROP CONSTRAINT IF EXISTS payment_exchange_rate_payment_order_id_fkey;
ALTER TABLE payment_order DROP CONSTRAINT IF EXISTS payment_order_payment_beneficiary_id_fkey;

-- ============================================================================
-- Rollback foreign keys first (reverse order of V6)
-- ============================================================================

-- Rollback payment_proof foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_proof' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        -- Drop foreign key constraint
        ALTER TABLE payment_proof DROP CONSTRAINT IF EXISTS payment_proof_payment_order_id_fkey;
        ALTER TABLE payment_proof DROP CONSTRAINT IF EXISTS payment_proof_payment_order_id_unique;
        
        -- Add new BIGINT column
        ALTER TABLE payment_proof ADD COLUMN payment_order_id_old BIGINT;
        
        -- Map back to original BIGINT values
        UPDATE payment_proof 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_proof.payment_order_id = m.new_id;
        
        -- Drop UUID column
        ALTER TABLE payment_proof DROP COLUMN payment_order_id;
        
        -- Rename column back
        ALTER TABLE payment_proof RENAME COLUMN payment_order_id_old TO payment_order_id;
        
        -- Set constraints
        ALTER TABLE payment_proof ALTER COLUMN payment_order_id SET NOT NULL;
        ALTER TABLE payment_proof ADD CONSTRAINT payment_proof_payment_order_id_unique UNIQUE (payment_order_id);
    END IF;
    
    -- Handle document_id (external reference)
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_proof'
        AND column_name = 'document_id'
        AND data_type = 'uuid'
    ) THEN
        -- For external references, we'll use sequential numbers since original mapping is lost
        ALTER TABLE payment_proof ADD COLUMN document_id_old BIGINT;

        -- Use a subquery with row_number to avoid window function in UPDATE
        WITH numbered_rows AS (
            SELECT payment_proof_id, row_number() OVER (ORDER BY date_created) as seq_num
            FROM payment_proof
        )
        UPDATE payment_proof
        SET document_id_old = nr.seq_num
        FROM numbered_rows nr
        WHERE payment_proof.payment_proof_id = nr.payment_proof_id;

        ALTER TABLE payment_proof DROP COLUMN document_id;
        ALTER TABLE payment_proof RENAME COLUMN document_id_old TO document_id;
        ALTER TABLE payment_proof ALTER COLUMN document_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_provider foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_provider' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_provider DROP CONSTRAINT IF EXISTS payment_provider_payment_order_id_fkey;
        ALTER TABLE payment_provider ADD COLUMN payment_order_id_old BIGINT;
        
        UPDATE payment_provider 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_provider.payment_order_id = m.new_id;
        
        ALTER TABLE payment_provider DROP COLUMN payment_order_id;
        ALTER TABLE payment_provider RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_provider ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_fee foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_fee' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_fee DROP CONSTRAINT IF EXISTS payment_fee_payment_order_id_fkey;
        ALTER TABLE payment_fee ADD COLUMN payment_order_id_old BIGINT;
        
        UPDATE payment_fee 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_fee.payment_order_id = m.new_id;
        
        ALTER TABLE payment_fee DROP COLUMN payment_order_id;
        ALTER TABLE payment_fee RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_fee ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payroll_order foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payroll_order' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payroll_order DROP CONSTRAINT IF EXISTS payroll_order_payment_order_id_fkey;
        ALTER TABLE payroll_order ADD COLUMN payment_order_id_old BIGINT;
        
        UPDATE payroll_order 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payroll_order.payment_order_id = m.new_id;
        
        ALTER TABLE payroll_order DROP COLUMN payment_order_id;
        ALTER TABLE payroll_order RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payroll_order ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_schedule foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_schedule' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_schedule DROP CONSTRAINT IF EXISTS payment_schedule_payment_order_id_fkey;
        ALTER TABLE payment_schedule ADD COLUMN payment_order_id_old BIGINT;
        
        UPDATE payment_schedule 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_schedule.payment_order_id = m.new_id;
        
        ALTER TABLE payment_schedule DROP COLUMN payment_order_id;
        ALTER TABLE payment_schedule RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_schedule ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_instruction foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_instruction' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_instruction DROP CONSTRAINT IF EXISTS payment_instruction_payment_order_id_fkey;
        ALTER TABLE payment_instruction ADD COLUMN payment_order_id_old BIGINT;
        
        UPDATE payment_instruction 
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_instruction.payment_order_id = m.new_id;
        
        ALTER TABLE payment_instruction DROP COLUMN payment_order_id;
        ALTER TABLE payment_instruction RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_instruction ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_order foreign keys
DO $$
BEGIN
    -- Rollback payment_method_id
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'payment_method_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_order DROP CONSTRAINT IF EXISTS payment_order_payment_method_id_fkey;
        ALTER TABLE payment_order ADD COLUMN payment_method_id_old BIGINT;

        UPDATE payment_order
        SET payment_method_id_old = m.old_id
        FROM payment_method_id_mapping m
        WHERE payment_order.payment_method_id = m.new_id;

        ALTER TABLE payment_order DROP COLUMN payment_method_id;
        ALTER TABLE payment_order RENAME COLUMN payment_method_id_old TO payment_method_id;
    END IF;

    -- Rollback payer_account_id (external reference)
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'payer_account_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_order ADD COLUMN payer_account_id_old BIGINT;

        -- Use a subquery with row_number to avoid window function in UPDATE
        WITH numbered_rows AS (
            SELECT payment_order_id, row_number() OVER (ORDER BY date_created) as seq_num
            FROM payment_order
        )
        UPDATE payment_order
        SET payer_account_id_old = nr.seq_num
        FROM numbered_rows nr
        WHERE payment_order.payment_order_id = nr.payment_order_id;

        ALTER TABLE payment_order DROP COLUMN payer_account_id;
        ALTER TABLE payment_order RENAME COLUMN payer_account_id_old TO payer_account_id;
        ALTER TABLE payment_order ALTER COLUMN payer_account_id SET NOT NULL;
    END IF;

    -- Rollback document_id (external reference)
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'document_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_order ADD COLUMN document_id_old BIGINT;

        -- Use a subquery with row_number to avoid window function in UPDATE
        WITH numbered_rows AS (
            SELECT payment_order_id, row_number() OVER (ORDER BY date_created) as seq_num
            FROM payment_order
            WHERE document_id IS NOT NULL
        )
        UPDATE payment_order
        SET document_id_old = nr.seq_num
        FROM numbered_rows nr
        WHERE payment_order.payment_order_id = nr.payment_order_id;

        ALTER TABLE payment_order DROP COLUMN document_id;
        ALTER TABLE payment_order RENAME COLUMN document_id_old TO document_id;
    END IF;
END $$;

-- ============================================================================
-- Rollback foreign keys in V4 tables (payment_audit, payment_compliance, etc.)
-- ============================================================================

-- Rollback payment_audit foreign keys
DO $$
BEGIN
    -- Rollback payment_order_id
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_audit'
        AND column_name = 'payment_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_audit ADD COLUMN payment_order_id_old BIGINT;

        UPDATE payment_audit
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_audit.payment_order_id = m.new_id;

        ALTER TABLE payment_audit DROP COLUMN payment_order_id;
        ALTER TABLE payment_audit RENAME COLUMN payment_order_id_old TO payment_order_id;
    END IF;

    -- Rollback payment_instruction_id
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_audit'
        AND column_name = 'payment_instruction_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_audit ADD COLUMN payment_instruction_id_old BIGINT;

        UPDATE payment_audit
        SET payment_instruction_id_old = m.old_id
        FROM payment_instruction_id_mapping m
        WHERE payment_audit.payment_instruction_id = m.new_id;

        ALTER TABLE payment_audit DROP COLUMN payment_instruction_id;
        ALTER TABLE payment_audit RENAME COLUMN payment_instruction_id_old TO payment_instruction_id;
    END IF;
END $$;

-- Rollback payment_compliance foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_compliance'
        AND column_name = 'payment_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_compliance ADD COLUMN payment_order_id_old BIGINT;

        UPDATE payment_compliance
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_compliance.payment_order_id = m.new_id;

        ALTER TABLE payment_compliance DROP COLUMN payment_order_id;
        ALTER TABLE payment_compliance RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_compliance ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_correspondence foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_correspondence'
        AND column_name = 'payment_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_correspondence ADD COLUMN payment_order_id_old BIGINT;

        UPDATE payment_correspondence
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_correspondence.payment_order_id = m.new_id;

        ALTER TABLE payment_correspondence DROP COLUMN payment_order_id;
        ALTER TABLE payment_correspondence RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_correspondence ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_exchange_rate foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_exchange_rate'
        AND column_name = 'payment_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_exchange_rate ADD COLUMN payment_order_id_old BIGINT;

        UPDATE payment_exchange_rate
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_exchange_rate.payment_order_id = m.new_id;

        ALTER TABLE payment_exchange_rate DROP COLUMN payment_order_id;
        ALTER TABLE payment_exchange_rate RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_exchange_rate ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_beneficiary foreign keys
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_beneficiary'
        AND column_name = 'payer_account_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_beneficiary ADD COLUMN payer_account_id_old BIGINT;

        -- For external references, use sequential numbers since original mapping is lost
        WITH numbered_rows AS (
            SELECT payment_beneficiary_id, row_number() OVER (ORDER BY date_created) as seq_num
            FROM payment_beneficiary
        )
        UPDATE payment_beneficiary
        SET payer_account_id_old = nr.seq_num
        FROM numbered_rows nr
        WHERE payment_beneficiary.payment_beneficiary_id = nr.payment_beneficiary_id;

        ALTER TABLE payment_beneficiary DROP COLUMN payer_account_id;
        ALTER TABLE payment_beneficiary RENAME COLUMN payer_account_id_old TO payer_account_id;
        ALTER TABLE payment_beneficiary ALTER COLUMN payer_account_id SET NOT NULL;
    END IF;
END $$;

-- Rollback payment_order.payment_beneficiary_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'payment_beneficiary_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_order ADD COLUMN payment_beneficiary_id_old BIGINT;

        -- For this relationship, we'll set to NULL since it was added in V4
        UPDATE payment_order SET payment_beneficiary_id_old = NULL;

        ALTER TABLE payment_order DROP COLUMN payment_beneficiary_id;
        ALTER TABLE payment_order RENAME COLUMN payment_beneficiary_id_old TO payment_beneficiary_id;
    END IF;
END $$;

-- ============================================================================
-- Rollback primary keys (reverse order of V5)
-- ============================================================================

-- Rollback payment_proof primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_proof'
        AND column_name = 'payment_proof_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_proof DROP CONSTRAINT payment_proof_pkey;
        ALTER TABLE payment_proof ADD COLUMN payment_proof_id_old BIGSERIAL;

        UPDATE payment_proof
        SET payment_proof_id_old = m.old_id
        FROM payment_proof_id_mapping m
        WHERE payment_proof.payment_proof_id = m.new_id;

        ALTER TABLE payment_proof DROP COLUMN payment_proof_id;
        ALTER TABLE payment_proof RENAME COLUMN payment_proof_id_old TO payment_proof_id;
        ALTER TABLE payment_proof ADD PRIMARY KEY (payment_proof_id);
    END IF;
END $$;

-- Rollback payment_provider primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_provider'
        AND column_name = 'payment_provider_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_provider DROP CONSTRAINT payment_provider_pkey;
        ALTER TABLE payment_provider ADD COLUMN payment_provider_id_old BIGSERIAL;

        UPDATE payment_provider
        SET payment_provider_id_old = m.old_id
        FROM payment_provider_id_mapping m
        WHERE payment_provider.payment_provider_id = m.new_id;

        ALTER TABLE payment_provider DROP COLUMN payment_provider_id;
        ALTER TABLE payment_provider RENAME COLUMN payment_provider_id_old TO payment_provider_id;
        ALTER TABLE payment_provider ADD PRIMARY KEY (payment_provider_id);
    END IF;
END $$;

-- Rollback payment_fee primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_fee'
        AND column_name = 'payment_fee_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_fee DROP CONSTRAINT payment_fee_pkey;
        ALTER TABLE payment_fee ADD COLUMN payment_fee_id_old BIGSERIAL;

        UPDATE payment_fee
        SET payment_fee_id_old = m.old_id
        FROM payment_fee_id_mapping m
        WHERE payment_fee.payment_fee_id = m.new_id;

        ALTER TABLE payment_fee DROP COLUMN payment_fee_id;
        ALTER TABLE payment_fee RENAME COLUMN payment_fee_id_old TO payment_fee_id;
        ALTER TABLE payment_fee ADD PRIMARY KEY (payment_fee_id);
    END IF;
END $$;

-- Rollback payroll_order primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payroll_order'
        AND column_name = 'payroll_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payroll_order DROP CONSTRAINT payroll_order_pkey;
        ALTER TABLE payroll_order ADD COLUMN payroll_order_id_old BIGSERIAL;

        UPDATE payroll_order
        SET payroll_order_id_old = m.old_id
        FROM payroll_order_id_mapping m
        WHERE payroll_order.payroll_order_id = m.new_id;

        ALTER TABLE payroll_order DROP COLUMN payroll_order_id;
        ALTER TABLE payroll_order RENAME COLUMN payroll_order_id_old TO payroll_order_id;
        ALTER TABLE payroll_order ADD PRIMARY KEY (payroll_order_id);
    END IF;
END $$;

-- Rollback payment_schedule primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_schedule'
        AND column_name = 'payment_schedule_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_schedule DROP CONSTRAINT payment_schedule_pkey;
        ALTER TABLE payment_schedule ADD COLUMN payment_schedule_id_old BIGSERIAL;

        UPDATE payment_schedule
        SET payment_schedule_id_old = m.old_id
        FROM payment_schedule_id_mapping m
        WHERE payment_schedule.payment_schedule_id = m.new_id;

        ALTER TABLE payment_schedule DROP COLUMN payment_schedule_id;
        ALTER TABLE payment_schedule RENAME COLUMN payment_schedule_id_old TO payment_schedule_id;
        ALTER TABLE payment_schedule ADD PRIMARY KEY (payment_schedule_id);
    END IF;
END $$;

-- Rollback payment_instruction primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_instruction'
        AND column_name = 'payment_instruction_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_instruction DROP CONSTRAINT payment_instruction_pkey;
        ALTER TABLE payment_instruction ADD COLUMN payment_instruction_id_old BIGSERIAL;

        UPDATE payment_instruction
        SET payment_instruction_id_old = m.old_id
        FROM payment_instruction_id_mapping m
        WHERE payment_instruction.payment_instruction_id = m.new_id;

        ALTER TABLE payment_instruction DROP COLUMN payment_instruction_id;
        ALTER TABLE payment_instruction RENAME COLUMN payment_instruction_id_old TO payment_instruction_id;
        ALTER TABLE payment_instruction ADD PRIMARY KEY (payment_instruction_id);
    END IF;
END $$;

-- Rollback payment_order primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_order'
        AND column_name = 'payment_order_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_order DROP CONSTRAINT payment_order_pkey;
        ALTER TABLE payment_order ADD COLUMN payment_order_id_old BIGSERIAL;

        UPDATE payment_order
        SET payment_order_id_old = m.old_id
        FROM payment_order_id_mapping m
        WHERE payment_order.payment_order_id = m.new_id;

        ALTER TABLE payment_order DROP COLUMN payment_order_id;
        ALTER TABLE payment_order RENAME COLUMN payment_order_id_old TO payment_order_id;
        ALTER TABLE payment_order ADD PRIMARY KEY (payment_order_id);
    END IF;
END $$;

-- Rollback payment_method primary key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_method'
        AND column_name = 'payment_method_id'
        AND data_type = 'uuid'
    ) THEN
        ALTER TABLE payment_method DROP CONSTRAINT payment_method_pkey;
        ALTER TABLE payment_method ADD COLUMN payment_method_id_old BIGSERIAL;

        UPDATE payment_method
        SET payment_method_id_old = m.old_id
        FROM payment_method_id_mapping m
        WHERE payment_method.payment_method_id = m.new_id;

        ALTER TABLE payment_method DROP COLUMN payment_method_id;
        ALTER TABLE payment_method RENAME COLUMN payment_method_id_old TO payment_method_id;
        ALTER TABLE payment_method ADD PRIMARY KEY (payment_method_id);
    END IF;
END $$;

-- ============================================================================
-- Re-add foreign key constraints with BIGINT references
-- ============================================================================

-- Re-add foreign key constraints
ALTER TABLE payment_order ADD CONSTRAINT payment_order_payment_method_id_fkey
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(payment_method_id);

ALTER TABLE payment_instruction ADD CONSTRAINT payment_instruction_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_schedule ADD CONSTRAINT payment_schedule_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payroll_order ADD CONSTRAINT payroll_order_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_fee ADD CONSTRAINT payment_fee_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_provider ADD CONSTRAINT payment_provider_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_proof ADD CONSTRAINT payment_proof_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

-- Re-add foreign key constraints for V4 tables
ALTER TABLE payment_audit ADD CONSTRAINT payment_audit_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_audit ADD CONSTRAINT payment_audit_payment_instruction_id_fkey
    FOREIGN KEY (payment_instruction_id) REFERENCES payment_instruction(payment_instruction_id);

ALTER TABLE payment_compliance ADD CONSTRAINT payment_compliance_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_correspondence ADD CONSTRAINT payment_correspondence_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

ALTER TABLE payment_exchange_rate ADD CONSTRAINT payment_exchange_rate_payment_order_id_fkey
    FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);

-- Note: payment_beneficiary and payment_order.payment_beneficiary_id relationships
-- are not re-added as they were new in V4 and may not be needed in the original schema

-- ============================================================================
-- Clean up UUID-related objects (already done at the beginning)
-- ============================================================================

-- Log the rollback
INSERT INTO migration_log (migration_name, migration_status, migration_notes)
VALUES ('UUID_to_LONG_rollback', 'COMPLETED', 'Successfully rolled back UUID conversion to BIGSERIAL/BIGINT');

-- ============================================================================
-- Final cleanup (optional - uncomment if you want to remove mapping tables)
-- ============================================================================

/*
DROP TABLE IF EXISTS payment_method_id_mapping;
DROP TABLE IF EXISTS payment_order_id_mapping;
DROP TABLE IF EXISTS payment_instruction_id_mapping;
DROP TABLE IF EXISTS payment_schedule_id_mapping;
DROP TABLE IF EXISTS payroll_order_id_mapping;
DROP TABLE IF EXISTS payment_fee_id_mapping;
DROP TABLE IF EXISTS payment_provider_id_mapping;
DROP TABLE IF EXISTS payment_proof_id_mapping;
*/

-- Final completion notice
DO $$
BEGIN
    RAISE NOTICE 'UUID to LONG rollback completed successfully. Please verify data integrity and restart applications.';
END $$;
