package com.jxk.database.es.domian;


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA
 * Description:
 * Author：cutes
 * Date:2020-02-23
 * Time:10:04
 * E-mail:cutesimba@163.com
 */
@Data
public class ResultJson<T> implements Serializable {
    private static final long serialVersionUID = 4996569398572460381L;

    private int code;
    private String msg;
    private T data;

    public ResultJson(ResultCode resultCode) {
        setResultCode(resultCode);
    }


    public ResultJson(ResultCode resultCode, T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public ResultJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = (T) new HashMap(1);
    }

    public static ResultJson ok() {
        return ok(new HashMap(1));
    }

    public static ResultJson ok(Object o) {
        if (o == null) {
            return ok();
        }
        return new ResultJson(ResultCode.SUCCESS, o);
    }

    public static ResultJson failure(ResultCode code) {
        return failure(code, "");
    }

    public static ResultJson failure(int code, String msg) {
        return new ResultJson(code, msg);
    }


    public static ResultJson failure(ResultCode code, Object o) {
        return new ResultJson(code, o);
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }


}
