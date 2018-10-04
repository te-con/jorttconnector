package com.getehour.connector.jortt;

public class ApiException extends Exception {
    private Integer httpStatusCode;

    ApiException(Integer httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}