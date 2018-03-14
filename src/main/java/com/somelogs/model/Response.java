package com.somelogs.model;

import lombok.Data;

import java.util.Objects;

/**
 * response
 *
 * @author LBG - 2018/1/5 0005
 */
@Data
public class Response<T> {

    /**
     * response status code
     */
    private Integer code;

    /**
     * response message
     */
    private String message;

    /**
     * response data
     */
    private T data;

    public Response() {
        this.code = ResponseStatus.SUCCESS.getCode();
        this.message = ResponseStatus.SUCCESS.getMessage();
    }

    public Boolean isSuccess() {
        return Objects.equals(code, ResponseStatus.SUCCESS.getCode());
    }
}
