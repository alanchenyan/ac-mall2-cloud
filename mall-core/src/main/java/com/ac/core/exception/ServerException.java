package com.ac.core.exception;

/**
 * @author Alan Chen
 * @description 业务异常
 * @date 2023/04/27
 */
public class ServerException extends RuntimeException {

    private int code = 500;

    private String[] placeholder;

    public ServerException() {
    }

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServerException(String message, String... placeholder) {
        super(message);
        this.placeholder = placeholder;
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String[] getPlaceholder() {
        return placeholder;
    }
}
