package com.sunnyday.model.excel.rule.checkrule;

/**
 * @author TMW
 * @since 2020/11/29 12:02
 */
public class CheckRuleFactory {

    /**
     * 获取具体校核规则
     *
     * @param rule
     * @return
     */
    public static CheckRule getCheckRule(Rule rule) {
        switch (rule) {
            case NOT_BLANK:
                return new NotBlankRule();
            case ID_CARD:
                return new IdCardRule();
            default:
                System.out.println("没有匹配到相应校核规则,默认进行非空校核！");
                return new NotBlankRule();
        }
    }

}
