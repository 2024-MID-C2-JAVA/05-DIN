package co.com.sofka.shared.business.usecases;

public final class Error {
    private final String code;
    private final String message;
    public static final Error NONE = new Error("", "");

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
