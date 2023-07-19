package com.project.enderman.dtos;

import java.io.Serializable;

public class ResponseDTO<T> {

    private String errorMsg;

    private T result; //T must be either a serializable class or primitive type

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
