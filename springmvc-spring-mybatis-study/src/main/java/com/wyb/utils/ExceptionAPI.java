package com.wyb.utils;

/**
 * Created by wyb on 2018/6/2.
 */
public class ExceptionAPI extends RuntimeException {
    public ExceptionAPI() {
        super();
    }

    public ExceptionAPI(String message) {
        super(message);
    }

    public ExceptionAPI(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionAPI(Throwable cause) {
        super(cause);
    }

}
