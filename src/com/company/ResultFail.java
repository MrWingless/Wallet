package com.company;

public class ResultFail extends Result {
    public int code;
    public Type type;

    public ResultFail(int code, Type type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public Type getType() {
        return type;
    }
}
