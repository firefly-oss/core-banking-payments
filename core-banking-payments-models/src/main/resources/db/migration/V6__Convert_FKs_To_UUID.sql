-- V6__Convert_FKs_To_UUID.sql
-- Migration to convert foreign keys from BIGINT to UUID
-- This migration uses the mapping tables created in V5 to preserve relationships

-- ============================================================================
-- Convert foreign keys in payment_order table
-- ============================================================================

-- Convert payment_method_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_order' 
        AND column_name = 'payment_method_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_order DROP CONSTRAINT IF EXISTS payment_order_payment_method_id_fkey;
        
        -- Add new UUID column
        ALTER TABLE payment_order ADD COLUMN payment_method_id_new UUID;
        
        -- Update with mapped UUIDs
        UPDATE payment_order 
        SET payment_method_id_new = m.new_id
        FROM payment_method_id_mapping m
        WHERE payment_order.payment_method_id = m.old_id;
        
        -- Drop old column
        ALTER TABLE payment_order DROP COLUMN payment_method_id;
        
        -- Rename new column
        ALTER TABLE payment_order RENAME COLUMN payment_method_id_new TO payment_method_id;
        
        -- Add foreign key constraint back
        ALTER TABLE payment_order ADD CONSTRAINT payment_order_payment_method_id_fkey 
            FOREIGN KEY (payment_method_id) REFERENCES payment_method(payment_method_id);
    END IF;
END $$;

-- Convert payer_account_id (external reference - no FK constraint needed)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_order' 
        AND column_name = 'payer_account_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_order ADD COLUMN payer_account_id_new UUID;
        
        -- For external references, we'll generate new UUIDs since we don't have mapping
        -- In a real scenario, you'd need to coordinate with the accounts service
        UPDATE payment_order SET payer_account_id_new = gen_random_uuid();
        
        -- Drop old column
        ALTER TABLE payment_order DROP COLUMN payer_account_id;
        
        -- Rename new column
        ALTER TABLE payment_order RENAME COLUMN payer_account_id_new TO payer_account_id;
        
        -- Set NOT NULL constraint
        ALTER TABLE payment_order ALTER COLUMN payer_account_id SET NOT NULL;
    END IF;
END $$;

-- Convert document_id (external reference - no FK constraint needed)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_order' 
        AND column_name = 'document_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_order ADD COLUMN document_id_new UUID;
        
        -- For external references, we'll generate new UUIDs since we don't have mapping
        -- In a real scenario, you'd need to coordinate with the document service
        UPDATE payment_order SET document_id_new = gen_random_uuid() WHERE document_id IS NOT NULL;
        
        -- Drop old column
        ALTER TABLE payment_order DROP COLUMN document_id;
        
        -- Rename new column
        ALTER TABLE payment_order RENAME COLUMN document_id_new TO document_id;
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payment_instruction table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_instruction' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_instruction DROP CONSTRAINT IF EXISTS payment_instruction_payment_order_id_fkey;
        
        -- Add new UUID column
        ALTER TABLE payment_instruction ADD COLUMN payment_order_id_new UUID;
        
        -- Update with mapped UUIDs
        UPDATE payment_instruction 
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_instruction.payment_order_id = m.old_id;
        
        -- Drop old column
        ALTER TABLE payment_instruction DROP COLUMN payment_order_id;
        
        -- Rename new column
        ALTER TABLE payment_instruction RENAME COLUMN payment_order_id_new TO payment_order_id;
        
        -- Set NOT NULL constraint
        ALTER TABLE payment_instruction ALTER COLUMN payment_order_id SET NOT NULL;
        
        -- Add foreign key constraint back
        ALTER TABLE payment_instruction ADD CONSTRAINT payment_instruction_payment_order_id_fkey 
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payment_schedule table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payment_schedule' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_schedule DROP CONSTRAINT IF EXISTS payment_schedule_payment_order_id_fkey;
        
        -- Add new UUID column
        ALTER TABLE payment_schedule ADD COLUMN payment_order_id_new UUID;
        
        -- Update with mapped UUIDs
        UPDATE payment_schedule 
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_schedule.payment_order_id = m.old_id;
        
        -- Drop old column
        ALTER TABLE payment_schedule DROP COLUMN payment_order_id;
        
        -- Rename new column
        ALTER TABLE payment_schedule RENAME COLUMN payment_order_id_new TO payment_order_id;
        
        -- Set NOT NULL constraint
        ALTER TABLE payment_schedule ALTER COLUMN payment_order_id SET NOT NULL;
        
        -- Add foreign key constraint back
        ALTER TABLE payment_schedule ADD CONSTRAINT payment_schedule_payment_order_id_fkey 
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payroll_order table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'payroll_order' 
        AND column_name = 'payment_order_id' 
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payroll_order DROP CONSTRAINT IF EXISTS payroll_order_payment_order_id_fkey;
        
        -- Add new UUID column
        ALTER TABLE payroll_order ADD COLUMN payment_order_id_new UUID;
        
        -- Update with mapped UUIDs
        UPDATE payroll_order 
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payroll_order.payment_order_id = m.old_id;
        
        -- Drop old column
        ALTER TABLE payroll_order DROP COLUMN payment_order_id;
        
        -- Rename new column
        ALTER TABLE payroll_order RENAME COLUMN payment_order_id_new TO payment_order_id;
        
        -- Set NOT NULL constraint
        ALTER TABLE payroll_order ALTER COLUMN payment_order_id SET NOT NULL;
        
        -- Add foreign key constraint back
        ALTER TABLE payroll_order ADD CONSTRAINT payroll_order_payment_order_id_fkey
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payment_fee table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_fee'
        AND column_name = 'payment_order_id'
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_fee DROP CONSTRAINT IF EXISTS payment_fee_payment_order_id_fkey;

        -- Add new UUID column
        ALTER TABLE payment_fee ADD COLUMN payment_order_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_fee
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_fee.payment_order_id = m.old_id;

        -- Drop old column
        ALTER TABLE payment_fee DROP COLUMN payment_order_id;

        -- Rename new column
        ALTER TABLE payment_fee RENAME COLUMN payment_order_id_new TO payment_order_id;

        -- Set NOT NULL constraint
        ALTER TABLE payment_fee ALTER COLUMN payment_order_id SET NOT NULL;

        -- Add foreign key constraint back
        ALTER TABLE payment_fee ADD CONSTRAINT payment_fee_payment_order_id_fkey
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payment_provider table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_provider'
        AND column_name = 'payment_order_id'
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_provider DROP CONSTRAINT IF EXISTS payment_provider_payment_order_id_fkey;

        -- Add new UUID column
        ALTER TABLE payment_provider ADD COLUMN payment_order_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_provider
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_provider.payment_order_id = m.old_id;

        -- Drop old column
        ALTER TABLE payment_provider DROP COLUMN payment_order_id;

        -- Rename new column
        ALTER TABLE payment_provider RENAME COLUMN payment_order_id_new TO payment_order_id;

        -- Set NOT NULL constraint
        ALTER TABLE payment_provider ALTER COLUMN payment_order_id SET NOT NULL;

        -- Add foreign key constraint back
        ALTER TABLE payment_provider ADD CONSTRAINT payment_provider_payment_order_id_fkey
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- ============================================================================
-- Convert foreign keys in payment_proof table
-- ============================================================================

-- Convert payment_order_id foreign key
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_proof'
        AND column_name = 'payment_order_id'
        AND data_type = 'bigint'
    ) THEN
        -- Drop foreign key constraint first
        ALTER TABLE payment_proof DROP CONSTRAINT IF EXISTS payment_proof_payment_order_id_fkey;

        -- Add new UUID column
        ALTER TABLE payment_proof ADD COLUMN payment_order_id_new UUID;

        -- Update with mapped UUIDs
        UPDATE payment_proof
        SET payment_order_id_new = m.new_id
        FROM payment_order_id_mapping m
        WHERE payment_proof.payment_order_id = m.old_id;

        -- Drop old column
        ALTER TABLE payment_proof DROP COLUMN payment_order_id;

        -- Rename new column
        ALTER TABLE payment_proof RENAME COLUMN payment_order_id_new TO payment_order_id;

        -- Set NOT NULL and UNIQUE constraints
        ALTER TABLE payment_proof ALTER COLUMN payment_order_id SET NOT NULL;
        ALTER TABLE payment_proof ADD CONSTRAINT payment_proof_payment_order_id_unique UNIQUE (payment_order_id);

        -- Add foreign key constraint back
        ALTER TABLE payment_proof ADD CONSTRAINT payment_proof_payment_order_id_fkey
            FOREIGN KEY (payment_order_id) REFERENCES payment_order(payment_order_id);
    END IF;
END $$;

-- Convert document_id (external reference - no FK constraint needed)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'payment_proof'
        AND column_name = 'document_id'
        AND data_type = 'bigint'
    ) THEN
        -- Add new UUID column
        ALTER TABLE payment_proof ADD COLUMN document_id_new UUID;

        -- For external references, we'll generate new UUIDs since we don't have mapping
        -- In a real scenario, you'd need to coordinate with the document service
        UPDATE payment_proof SET document_id_new = gen_random_uuid();

        -- Drop old column
        ALTER TABLE payment_proof DROP COLUMN document_id;

        -- Rename new column
        ALTER TABLE payment_proof RENAME COLUMN document_id_new TO document_id;

        -- Set NOT NULL constraint
        ALTER TABLE payment_proof ALTER COLUMN document_id SET NOT NULL;
    END IF;
END $$;

-- ============================================================================
-- Note: Mapping tables are kept for potential rollback scenarios
-- They can be dropped in V7 after verification that all conversions are successful
-- ============================================================================
