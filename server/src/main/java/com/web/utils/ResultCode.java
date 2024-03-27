package com.web.utils;

public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "成功！"),
    SIGNATURE_NOT_MATCH(403, "服务器拒绝！"),
    NOT_FOUND(404, "未找到该资源!"),
    URL_NOT_FOUND(404, "URL不存在！"),

    /**
     * 失败
     */
    FAILURE(500, "服务器内部错误！");


    /**
     * 状 态 码
     */

    private final int code;


    private final String message;


    ResultCode(int code, String message) {

        this.code = code;

        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
