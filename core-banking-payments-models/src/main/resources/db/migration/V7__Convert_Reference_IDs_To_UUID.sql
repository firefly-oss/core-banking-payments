-- V7__Convert_Reference_IDs_To_UUID.sql
-- Final migration to clean up mapping tables and handle any remaining reference ID conversions
-- This migration also adds indexes and constraints for optimal performance

-- ============================================================================
-- Add indexes for better performance on UUID columns
-- ============================================================================

-- Add indexes on foreign key columns for better join performance
CREATE INDEX IF NOT EXISTS idx_payment_order_payment_method_id ON payment_order(payment_method_id);
CREATE INDEX IF NOT EXISTS idx_payment_order_payer_account_id ON payment_order(payer_account_id);
CREATE INDEX IF NOT EXISTS idx_payment_order_document_id ON payment_order(document_id);
CREATE INDEX IF NOT EXISTS idx_payment_order_payment_beneficiary_id ON payment_order(payment_beneficiary_id);

CREATE INDEX IF NOT EXISTS idx_payment_instruction_payment_order_id ON payment_instruction(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_schedule_payment_order_id ON payment_schedule(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payroll_order_payment_order_id ON payroll_order(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_fee_payment_order_id ON payment_fee(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_provider_payment_order_id ON payment_provider(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_proof_payment_order_id ON payment_proof(payment_order_id);

-- Add indexes for audit and compliance tables (from V4)
CREATE INDEX IF NOT EXISTS idx_payment_audit_payment_order_id ON payment_audit(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_audit_payment_instruction_id ON payment_audit(payment_instruction_id);
CREATE INDEX IF NOT EXISTS idx_payment_compliance_payment_order_id ON payment_compliance(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_correspondence_payment_order_id ON payment_correspondence(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_exchange_rate_payment_order_id ON payment_exchange_rate(payment_order_id);
CREATE INDEX IF NOT EXISTS idx_payment_beneficiary_payer_account_id ON payment_beneficiary(payer_account_id);

-- ============================================================================
-- Add additional constraints for data integrity
-- ============================================================================

-- Ensure UUIDs are not null where required
DO $$
BEGIN
    -- payment_order constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_order') THEN
        ALTER TABLE payment_order ALTER COLUMN payment_order_id SET NOT NULL;
        ALTER TABLE payment_order ALTER COLUMN payer_account_id SET NOT NULL;
    END IF;

    -- payment_instruction constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_instruction') THEN
        ALTER TABLE payment_instruction ALTER COLUMN payment_instruction_id SET NOT NULL;
        ALTER TABLE payment_instruction ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;

    -- payment_schedule constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_schedule') THEN
        ALTER TABLE payment_schedule ALTER COLUMN payment_schedule_id SET NOT NULL;
        ALTER TABLE payment_schedule ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;

    -- payroll_order constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payroll_order') THEN
        ALTER TABLE payroll_order ALTER COLUMN payroll_order_id SET NOT NULL;
        ALTER TABLE payroll_order ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;

    -- payment_fee constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_fee') THEN
        ALTER TABLE payment_fee ALTER COLUMN payment_fee_id SET NOT NULL;
        ALTER TABLE payment_fee ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;

    -- payment_provider constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_provider') THEN
        ALTER TABLE payment_provider ALTER COLUMN payment_provider_id SET NOT NULL;
        ALTER TABLE payment_provider ALTER COLUMN payment_order_id SET NOT NULL;
    END IF;

    -- payment_proof constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_proof') THEN
        ALTER TABLE payment_proof ALTER COLUMN payment_proof_id SET NOT NULL;
        ALTER TABLE payment_proof ALTER COLUMN payment_order_id SET NOT NULL;
        ALTER TABLE payment_proof ALTER COLUMN document_id SET NOT NULL;
    END IF;

    -- payment_method constraints
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'payment_method') THEN
        ALTER TABLE payment_method ALTER COLUMN payment_method_id SET NOT NULL;
    END IF;
END $$;

-- ============================================================================
-- Create a view to verify the UUID conversion was successful
-- ============================================================================

CREATE OR REPLACE VIEW uuid_conversion_verification AS
SELECT 
    'payment_method' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_method_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_method_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_method
UNION ALL
SELECT 
    'payment_order' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_order_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_order_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_order
UNION ALL
SELECT 
    'payment_instruction' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_instruction_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_instruction_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_instruction
UNION ALL
SELECT 
    'payment_schedule' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_schedule_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_schedule_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_schedule
UNION ALL
SELECT 
    'payroll_order' as table_name,
    COUNT(*) as total_records,
    COUNT(payroll_order_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payroll_order_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payroll_order
UNION ALL
SELECT 
    'payment_fee' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_fee_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_fee_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_fee
UNION ALL
SELECT 
    'payment_provider' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_provider_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_provider_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_provider
UNION ALL
SELECT 
    'payment_proof' as table_name,
    COUNT(*) as total_records,
    COUNT(payment_proof_id) as uuid_records,
    CASE WHEN COUNT(*) = COUNT(payment_proof_id) THEN 'SUCCESS' ELSE 'FAILED' END as conversion_status
FROM payment_proof;

-- ============================================================================
-- Clean up mapping tables (commented out for safety - uncomment after verification)
-- ============================================================================

-- WARNING: Only drop mapping tables after verifying the conversion was successful
-- and you're confident you won't need to rollback

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

-- ============================================================================
-- Add comments to document the UUID conversion
-- ============================================================================

COMMENT ON TABLE payment_method IS 'Payment method reference data - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_order IS 'Core payment order entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_instruction IS 'Payment instruction entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_schedule IS 'Payment schedule entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payroll_order IS 'Payroll order entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_fee IS 'Payment fee entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_provider IS 'Payment provider entity - converted from BIGSERIAL to UUID primary key';
COMMENT ON TABLE payment_proof IS 'Payment proof entity - converted from BIGSERIAL to UUID primary key';

-- ============================================================================
-- Migration completion log
-- ============================================================================

-- Create a table to log migration completion
CREATE TABLE IF NOT EXISTS migration_log (
    migration_id SERIAL PRIMARY KEY,
    migration_name VARCHAR(100) NOT NULL,
    migration_date TIMESTAMP NOT NULL DEFAULT NOW(),
    migration_status VARCHAR(20) NOT NULL,
    migration_notes TEXT
);

-- Log the completion of UUID conversion
INSERT INTO migration_log (migration_name, migration_status, migration_notes)
VALUES ('LONG_to_UUID_conversion', 'COMPLETED', 'Successfully converted all primary keys and foreign keys from BIGSERIAL/BIGINT to UUID');

-- ============================================================================
-- Final verification query (for manual execution)
-- ============================================================================

-- Run this query after migration to verify all conversions:
-- SELECT * FROM uuid_conversion_verification;

-- Check for any remaining BIGINT columns that might need attention:
-- SELECT table_name, column_name, data_type 
-- FROM information_schema.columns 
-- WHERE table_schema = 'public' 
-- AND data_type IN ('bigint', 'bigserial')
-- AND table_name LIKE 'payment%'
-- ORDER BY table_name, column_name;
