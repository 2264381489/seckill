package com.xiaoyan.eurekaclient.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 13
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCod;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCod = resultCode;
        this.message = message;
    }



    @Override
    public String toString() {
        return '{' +
                "data= {" +
                "resultCode=" + resultCod +
                ", message='" + message + '\'' +
                ", list=" + data +
                '}'+
                '}'
                ;
    }
}
