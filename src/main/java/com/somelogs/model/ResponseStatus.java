package com.somelogs.model;

/**
 * reponse status enum
 *
 * @author LBG - 2018/1/5 0005
 */
public enum ResponseStatus {

    SUCCESS(0, "成功"),
    INTERNAL_SERVER_ERROR(500, "系统内部错误");

    private Integer code;
    private String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
