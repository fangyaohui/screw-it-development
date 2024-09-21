package com.fang.screw.communal.utils;

import com.fang.screw.domain.enums.CodeMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName R
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@Data
public class R<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private long currentTimeMillis = System.currentTimeMillis();


    public R() {
        this.code = 200;
    }

    public R(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public R(T data) {
        this.code = 200;
        this.data = data;
    }

    public R(String message) {
        this.code = 500;
        this.msg = message;
    }


    public static <T> R<T> ok() {
        return restResult(null,0 , (String)null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, 0, (String)null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, 0, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, 1, (String)null);
    }

    public static <T> R<T> failed(CodeMsg codeMsg) {
        return new R(codeMsg.getCode(), codeMsg.getMsg());
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, 1, msg);
    }

    public static <T> R<T> fail(String msg) {
        return new R(msg);
    }


    public static <T> R<T> failed(T data) {
        return restResult(data, 1, (String)null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, 1, msg);
    }

    static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
