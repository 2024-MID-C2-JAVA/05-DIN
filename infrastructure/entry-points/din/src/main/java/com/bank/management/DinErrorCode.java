package com.bank.management;

public enum DinErrorCode {

    //GENERIC
    SUCCESS("0", "0000", "Successful operation", "SUCCESS"),
    OPERATION_FAILED("1", "1001", "Operation failed", "ERROR"),
    NOT_FOUND("2", "1004", "Not found", "WARNING"),
    INTERNAL_SERVER_ERROR("1", "1005", "Internal server error", "ERROR"),
    UNKNOWN_ERROR("1", "1006", "Unknown error", "ERROR"),

    //CUSTOMER
    CUSTOMER_CREATED("0", "1000", "Customer created successfully", "SUCCESS"),
    CUSTOMER_NOT_FOUND("2", "1002", "Customer not found", "WARNING"),
    CUSTOMER_DELETED("0", "1003", "Customer deleted successfully", "SUCCESS"),

    //ACCOUNT
    ACCOUNT_CREATED("0", "2000", "Bank account created successfully", "SUCCESS"),
    ACCOUNT_NOT_FOUND("2", "1002", "The account does not exist", "WARNING"),
    ACCOUNT_DELETED("0", "2002", "Bank account deleted successfully", "SUCCESS"),
    ERROR_CREATING_ACCOUNT("1", "2003", "Error creating bank account", "ERROR"),
    ERROR_DELETING_ACCOUNT("1", "2004", "Error deleting bank account", "ERROR"),

    //TRANSACTIONS
    DEPOSIT_SUCCESS("0", "3000", "Deposit successful", "SUCCESS"),
    DEPOSIT_FAILED("1", "3001", "Deposit failed", "ERROR"),
    PURCHASE_SUCCESS("0", "3002", "Purchase successful", "SUCCESS"),
    PURCHASE_FAILED("1", "3003", "Purchase failed", "ERROR"),
    WITHDRAWAL_SUCCESS("0", "3004", "Withdrawal successful", "SUCCESS"),
    WITHDRAWAL_FAILED("1", "3005", "Withdrawal failed", "ERROR");

    private final String code;
    private final String errorCodeProvider;
    private final String message;
    private final String type;

    DinErrorCode(String code, String errorCodeProveedor, String message, String type) {
        this.code = code;
        this.errorCodeProvider = errorCodeProveedor;
        this.message = message;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getErrorCodeProvider() {
        return errorCodeProvider;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
