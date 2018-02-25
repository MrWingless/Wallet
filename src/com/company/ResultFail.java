package com.company;

public class ResultFail extends Result {

    public ResultFail(int code, Type type) {
        if (type != Result.ERROR_TYPES.get(code)){
            Logger.log(Logger.LogType.FAILURE, "Wrong Error code was assigned with the wrong Error Type :O");
        }
        super.code = code;
        super.errorType = type;
    }

    public ResultFail(int code) {
        super.code = code;
        super.errorType = Result.ERROR_TYPES.get(code);
    }
}
