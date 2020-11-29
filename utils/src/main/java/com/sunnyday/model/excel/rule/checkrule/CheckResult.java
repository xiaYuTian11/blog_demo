package com.sunnyday.model.excel.rule.checkrule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 校核结果
 *
 * @author TMW
 * @since 2020/11/29 12:32
 */
@Data
@ToString
@AllArgsConstructor
public class CheckResult {
    /**
     * 工作薄序号
     */
    private Integer sheetIndex;
    /**
     * 行号
     */
    private Integer rowIndex;
    /**
     * 列号
     */
    private Integer cellIndex;
    /**
     * 错误信息
     */
    private String errMsg;
}
