package com.company;

import java.util.HashMap;

public abstract class Result {
    public int code;
    public Type errorType;
    protected enum Type {
        SUCCESS,
        ERROR_SYSTEM,
        ERROR_TRANSACTION_EXCEEDS_LIMITS,
        ERROR_NOT_ENOUGH_MONEY
    }

    public static final HashMap<Integer, Type> ERROR_TYPES = new HashMap();
    static {
        ERROR_TYPES.put(0, Type.SUCCESS);
        ERROR_TYPES.put(6, Type.ERROR_SYSTEM);
        ERROR_TYPES.put(55, Type.ERROR_NOT_ENOUGH_MONEY);
        ERROR_TYPES.put(65, Type.ERROR_TRANSACTION_EXCEEDS_LIMITS);
    }

    public int getCode() {
        return code;
    }

    public Type getErrorType() {
        return errorType;
    }
}

