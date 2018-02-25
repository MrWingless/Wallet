package com.company;

public abstract class Result {
    public int code;
    public Type errorType;

    public enum Type {
        SUCCESS,
        ERROR_SYSTEM,
        ERROR_NOT_ENOUGH_MONEY
    }
}

