package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single payroll line entry within a Norma 34 batch (nómina).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Norma34PayrollLineDTO {

    /**
     * A sequential number for this line in the overall Norma 34 batch file.
     */
    private Integer lineNumber;

    /**
     * The operation type code for Norma 34:
     * <ul>
     *   <li>"N" - Normal transaction</li>
     *   <li>"R" - Return or refund</li>
     *   <li>"I" - Other special codes if applicable</li>
     * </ul>
     *
     * Typically "N" for standard payroll.
     */
    private String operationType;

    /**
     * An internal ID for the employee in the company’s HR system.
     * This is not mandated by Norma 34 but can be useful for reconciliation.
     */
    private String employeeId;

    /**
     * The employee’s full name.
     */
    private String employeeName;

    /**
     * The employee’s Spanish tax ID (NIF/NIE).
     * For example, "12345678A".
     */
    private String employeeNif;

    /**
     * The employee’s Social Security Number (if applicable).
     * Not always required, but often used in payroll contexts.
     */
    private String employeeSocialSecurityNumber;

    /**
     * The IBAN where the payroll amount should be credited.
     * Typically starts with "ES" for Spain;
     * e.g. "ES76 2100 0418 4502 0005 1332".
     */
    private String employeeIban;

    /**
     * (Optional) Bank entity code, typically the first 4 digits
     * of the Spanish CCC. For example, "2100".
     *
     * Used in some legacy Norma 34 flows if you’re not exclusively
     * relying on IBAN.
     */
    private String bankEntityCode;

    /**
     * (Optional) Bank office code, typically the next 4 digits of the CCC.
     */
    private String bankOfficeCode;

    /**
     * (Optional) Control digits (2 digits) in the CCC (after the office code).
     */
    private String accountControlDigits;

    /**
     * (Optional) The last 10 digits of the CCC.
     *
     * If you’re only using IBAN, you may omit or leave this blank.
     */
    private String accountNumber;

    /**
     * The amount to be credited to the employee.
     * This is typically the net salary (nómina).
     */
    private BigDecimal paymentAmount;

    /**
     * The start date of the payroll period for which
     * this payment is being made. (Optional for some banks.)
     */
    private LocalDate payrollPeriodStart;

    /**
     * The end date of the payroll period for which
     * this payment is being made. (Optional for some banks.)
     */
    private LocalDate payrollPeriodEnd;

    /**
     * A concept or short description that appears in the payroll transfer
     * (e.g. "Nómina Enero 2025").
     */
    private String concept;

    /**
     * Additional free-text information or remarks
     * that you wish to include in the line record.
     */
    private String additionalInformation;

    /**
     * Street address of the employee (optional).
     *
     * Even though not strictly required by Norma 34,
     * some banks or compliance flows might request it.
     */
    private String employeeAddressLine;

    /**
     * City or locality of the employee’s address (optional).
     */
    private String employeeCity;

    /**
     * Province or region of the employee’s address (optional).
     */
    private String employeeProvince;

    /**
     * Postal code of the employee’s address (optional).
     */
    private String employeePostalCode;

    /**
     * Country code in ISO 3166-1 alpha-2 format (e.g. "ES").
     * Optional for domestic Spanish payroll but might be used
     * for foreign employees.
     */
    private String employeeCountry;

    /**
     * (Optional) Contact phone number of the employee.
     */
    private String employeePhone;

    /**
     * (Optional) Contact email address of the employee.
     */
    private String employeeEmail;
}