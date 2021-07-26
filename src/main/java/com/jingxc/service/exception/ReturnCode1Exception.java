package com.jingxc.service.exception;

public class ReturnCode1Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReturnCode1Exception() {
    }

    public ReturnCode1Exception(String msg) {
        super(msg);
    }

}