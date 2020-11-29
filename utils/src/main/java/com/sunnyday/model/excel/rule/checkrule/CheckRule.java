package com.sunnyday.model.excel.rule.checkrule;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 检查规则
 *
 * @author TMW
 * @since 2020/11/29 11:04
 */
public interface CheckRule {
    /**
     * 校验规则
     *
     * @param cell 单元格
     * @return 返回是否校验通过
     */
    boolean check(Cell cell);

}
