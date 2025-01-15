-- V1__Create_Enums.sql

-- ============================================================================
-- ENUM: payment_type_enum
-- ============================================================================
CREATE TYPE payment_type_enum AS ENUM (
    'SEPA_SCT',
    'SEPA_ICT',
    'SWIFT',
    'Payroll'
);

-- ============================================================================
-- ENUM: payment_order_status_enum (for payment_order.status)
-- ============================================================================
CREATE TYPE payment_order_status_enum AS ENUM (
    'Initiated',
    'Completed',
    'Failed'
);

-- ============================================================================
-- ENUM: instruction_type_enum (for payment_instruction.instruction_type)
-- ============================================================================
CREATE TYPE instruction_type_enum AS ENUM (
    'Immediate',
    'Scheduled'
);

-- ============================================================================
-- ENUM: instruction_status_enum (for payment_instruction.instruction_status)
-- ============================================================================
CREATE TYPE instruction_status_enum AS ENUM (
    'Pending',
    'Executed',
    'Cancelled'
);

-- ============================================================================
-- ENUM: frequency_enum (for payment_schedule.frequency)
-- ============================================================================
CREATE TYPE frequency_enum AS ENUM (
    'One-time',
    'Daily',
    'Weekly',
    'Monthly',
    'Yearly'
);

-- ============================================================================
-- ENUM: schedule_status_enum (for payment_schedule.schedule_status)
-- ============================================================================
CREATE TYPE schedule_status_enum AS ENUM (
    'Scheduled',
    'Completed',
    'Cancelled'
);

-- ============================================================================
-- ENUM: payroll_status_enum (for payroll_order.payroll_status)
-- ============================================================================
CREATE TYPE payroll_status_enum AS ENUM (
    'Initiated',
    'Processed',
    'Failed'
);

-- ============================================================================
-- ENUM: provider_status_enum (for payment_provider.status)
-- ============================================================================
CREATE TYPE provider_status_enum AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'PENDING',
    'SUSPENDED'
);
