package com.wyy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by wangyaoyao on 2018/8/14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRequestBody<T> implements Serializable {
    private static final long serialVersionUID = -4185151304730685014L;

    private String token;
    @Valid
    private T data;
    public BaseRequestBody(){}
    public BaseRequestBody(String token, T data) {
        this.token = token;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseRequestBody [token=" + token + ", data=" + data +"]";
    }
}
