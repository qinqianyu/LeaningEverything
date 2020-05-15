package com.jxk.database.es.domian;

/**
 * Created with IntelliJ IDEA
 * Description:
 * Author：cutes
 * Date:2020-02-23
 * Time:9:59
 * E-mail:cutesimba@163.com
 */
public enum ResultCode {
    SUCCESS(200, "请求成功"),
    NOT_FOUND(201, "未查询到登记信息"),
    NOT_MATCH_CARD(202, "手机号与身份证号码不匹配"),
    NOT_MATCH(203, "证件号码与姓名不匹配"),
    CHECK_FAILED(204, "审核未通过"),
    CHECKING(205, "信息审核中"),
    BAD_REQUEST(401, "参数异常"),
    SERVER_ERROR(500, "系统异常，请与管理员联系");
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
