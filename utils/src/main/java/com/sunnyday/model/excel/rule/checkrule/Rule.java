package com.sunnyday.model.excel.rule.checkrule;

/**
 * 规则
 *
 * @author TMW
 * @date 2019/10/16 17:31
 */

public enum Rule {
    /**
     * 不能为空
     */
    NOT_BLANK("不能为空"),
    /**
     * 身份证格式
     */
    ID_CARD("应为身份证");

    Rule(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public Rule setMessage(String message) {
        this.message = message;
        return this;
    }
}
