-- V3__Create_Casts.sql
-- Postgres casts between varchar and the enumerations using built-in IN/OUT functions.

-------------------------
-- payment_type_enum
-------------------------
CREATE CAST (varchar AS payment_type_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- payment_order_status_enum
-------------------------
CREATE CAST (varchar AS payment_order_status_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- instruction_type_enum
-------------------------
CREATE CAST (varchar AS instruction_type_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- instruction_status_enum
-------------------------
CREATE CAST (varchar AS instruction_status_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- frequency_enum
-------------------------
CREATE CAST (varchar AS frequency_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- schedule_status_enum
-------------------------
CREATE CAST (varchar AS schedule_status_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- payroll_status_enum
-------------------------
CREATE CAST (varchar AS payroll_status_enum)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- provider_status_enum
-------------------------
CREATE CAST (varchar AS provider_status_enum)
    WITH INOUT
    AS IMPLICIT;
