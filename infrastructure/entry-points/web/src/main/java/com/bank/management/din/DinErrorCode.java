package com.bank.management.din;

public enum DinErrorCode {

    //GENERIC
    SUCCESS("0", "1000", "Operation successful"),
    OPERATION_FAILED("1", "1001", "Operation failed"),
    NOT_FOUND("404", "1004", "Not found"),
    INTERNAL_SERVER_ERROR("500", "1005", "Internal server error"),

    //CUSTOMER
    CUSTOMER_CREATED("1000", "1000", "Customer created successfully"),
    CUSTOMER_NOT_FOUND("404", "1002", "Customer not found"),
    CUSTOMER_DELETED("1000", "1003", "Customer deleted successfully"),

    //ACCOUNT
    ACCOUNT_CREATED("0", "2000", "Bank account created successfully"),
    ACCOUNT_NOT_FOUND("404", "2001", "Bank account not found"),
    ACCOUNT_DELETED("0", "2002", "Bank account deleted successfully"),
    ERROR_CREATING_ACCOUNT("1", "2003", "Error creating bank account"),
    ERROR_DELETING_ACCOUNT("500", "2004", "Error deleting bank account"),

    //TRANSACTIONS
    DEPOSIT_SUCCESS("0", "3000", "Deposit successful"),
    DEPOSIT_FAILED("1", "3001", "Deposit failed"),
    PURCHASE_SUCCESS("0", "3002", "Purchase successful"),
    PURCHASE_FAILED("1", "3003", "Purchase failed"),
    WITHDRAWAL_SUCCESS("0", "3004", "Withdrawal successful"),
    WITHDRAWAL_FAILED("1", "3005", "Withdrawal failed");

    private final String code;
    private final String errorCodeProveedor;
    private final String message;

    DinErrorCode(String code, String errorCodeProveedor, String message) {
        this.code = code;
        this.errorCodeProveedor = errorCodeProveedor;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getErrorCodeProveedor() {
        return errorCodeProveedor;
    }

    public String getMessage() {
        return message;
    }
}
