package com.farmer.exception;

/**
 * 参数缺少异常
 * @author farmer
 * @version 1.0
 * @data 2019/4/25 20:27
 **/
public class ParamsMissingException extends Exception {

    private String message;

    public ParamsMissingException(String message) {
        super(message) ;
        this.message = message ;
    }

}
